import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class GameEngine implements java.io.Serializable
{

	public static Point[] invalidPoints;
	public static boolean debugMode;
	public static Scanner scanner;
	private static Random rand;
	private static Vector<Point> pointsUsedByNinja;
	private static Vector<Point> pointsUsedByPowerUps;
	private int lineOfSight;
	private Enemy[] Ninja;
	private PowerUp[] PowerUps;
	private Player Player1;
	// We can dynamically assign the amount of powerups and enemies. Hardcoding is bad, m'kay. - Steve
	private int enemySpawn;
	private int powerUpSpawn;
	private Grid gameGrid;
	private TextUserInterface UI;

	/**
	 * Initilize game engine with Enemies spawned, and debug mode.
	 *
	 * @param debugMode
	 * @param enemySpawn
	 * @param powerUpSpawn
	 */
	GameEngine (boolean debugMode, int enemySpawn, int powerUpSpawn, int lineOfSight)
	{
		GameEngine.debugMode = debugMode;
		scanner = new Scanner(System.in);
		invalidPoints = new Point[10];
		setInvalidPoints();
		this.lineOfSight = lineOfSight;

		TextUserInterface.printGameString("DebugMode enabled!", true);

		this.enemySpawn = enemySpawn;
		this.powerUpSpawn = powerUpSpawn;
		rand = new Random();
		this.Player1 = new Player();
		this.UI = new TextUserInterface();


		pointsUsedByNinja = new Vector<Point>();
		pointsUsedByPowerUps = new Vector<Point>();

		this.PowerUps = new PowerUp[this.powerUpSpawn];
		initPowerUps(this.PowerUps);

		this.Ninja = new Enemy[enemySpawn];
		initNinjas(this.Ninja);

		this.gameGrid = new Grid(this.Ninja, this.PowerUps, this.Player1);
	}

	/**
	 * Generate a number between 1 and bound inclusive
	 *
	 * @param bound the maximum value a random number can be output
	 * @return number generated
	 */
	public static int generateRandNum (int bound)
	{
		int num = rand.nextInt(bound) + 1;
		//TextUserInterface.printGameString("Random Number Generated: " + num, true);
		return num;
	}

	public static boolean isValidPoint (Point point)
	{
		for (int x = 0; x < 10; x++)
			if (point.x == GameEngine.invalidPoints[x].x && point.y == GameEngine.invalidPoints[x].y)
				return false;
		return true;
	}

	public static Vector<Point> getPointsUsedByNinja ()
	{
		return pointsUsedByNinja;
	}

	public static Vector<Point> getPointsUsedByPowerUps ()
	{
		return pointsUsedByPowerUps;
	}

	/**
	 * Main routine that starts the game.
	 */
	public void start ()
	{
		// Display retarded text, maybe show game controls etc, haven't decided. - Steve
		this.UI.welcomeScreen();

		Player1.revealSquares(this.gameGrid, this.lineOfSight);

		// Game plays until either briefcase is found, or player dies
		while (!Player1.isHasBriefcase() && Player1.isPlayerAlive())
		{
			// Draws out the current grid, you know, like, with all the enemies hidden, etc.
			UI.drawGrid(this.gameGrid, this.Player1);

			// Runs the game loop where the player is constantly asked for next actions
			gameLoop();
		}

		//GAMEOVER - Interesting little effect. Shows everything on map, kinda like minesweeper after winning / losing
		GameEngine.debugMode = true;
		UI.drawGrid(this.gameGrid, this.Player1);
	}

	private void enemyAction ()
	{
		if (!Player1.isHasBriefcase())
		{
			actionNinjaAttemptKill();
			ninjaMove();
			Player1.revealSquares(this.gameGrid, this.lineOfSight);
		}
	}

	private void gameLoop ()
	{
		//Grab user choice, and execute that command.
		switch (UI.getPlayerOption())
		{
			case MOVEMENT:
			{
				movePlayer(UI.askPlayerDirection(Player1, gameGrid));
				enemyAction();
				break;
			}
			case LOOK:
			{
				enemyAction();
				break;
			}
			case SHOOT:
			{
				if (!Player1.isHasBullet())
				{
					UI.printOutOfAmmo();
					break;
				}
				Point temp = Player1.shoot(this.gameGrid, this.UI);

				if (temp != null)
					ninjaDeath(temp);

				enemyAction();
				break;
			}
			case SAVE:
			{
				saveGame();
				break;
			}
			case LOAD:
			{
				loadGame();
				break;
			}
			case NULL:
			{
				TextUserInterface.printGameString("If we are seeing this, something in the switch statement of " + "GameEngine.gameLoop() is returning NULL.", true);
				break;
			}
			default:
				break;
		}

	}

	private void ninjaDeath (Point position)
	{
		for (Enemy A : Ninja)
			if (A.getPosition().equals(position))
			{
				A.killSelf();
				break;
			}
	}

	private void actionNinjaAttemptKill ()
	{
		// Attn: All Ninja, check surroundings and slaughter anyone within proximity.
		for (Enemy A : Ninja)
			if (A.getIsAlive())
				if (A.killedPlayer(this.gameGrid))
				{
					if (this.Player1.getInvincibilityCount() > 0)
					{
						this.Player1.decrementInvincibility();
						UI.printLostInvincibility();
						continue;
					}
					if (this.Player1.getLives() > 1)
					{
						this.Player1.decrementLives();
						UI.printLostALife();
						this.gameGrid.getGridTile(this.Player1.getPosition().y, this.Player1.getPosition().x).setTileType(Tile.entity.EMPTY);

						this.Player1.setPosition(new Point(0, 8));
						this.gameGrid.getGridTile(8, 0).setTileType(Tile.entity.PLAYER);
						Player1.revealSquares(this.gameGrid, this.lineOfSight);
					}
					else
						this.Player1.setIsAlive(false);
				}
	}

	void ninjaMove ()
	{
		if (!this.Player1.isPlayerAlive())
			return;

		for (Enemy A : Ninja)
		{
			String movement = A.findValidMovement(this.gameGrid);

			this.UI.printGameString("NINJA MOVES: " + movement, true);

			// Skip movement if the ninja is dead, or failing the infinite loop check for movement
			if (!A.getIsAlive() || A.getNoMoveThisTurn())
				continue;

			// Current tile, if it was a powerup, just remove the fact a ninja was hiding on it, so that that grid displays PWR still.
			// Else the only other option is it being empty
			if (gameGrid.getGridTile(A.getPosition().y, A.getPosition().x).getTileType() == Tile.entity.POWERUP)
				gameGrid.getGridTile(A.getPosition().y, A.getPosition().x).setIsNinja(false);
			else
				gameGrid.getGridTile(A.getPosition().y, A.getPosition().x).setTileType(Tile.entity.EMPTY);

			switch (movement)
			{
				case "up":
				{
					// If the target tile is a powerup, add the fact that a ninja is hiding under it
					if (gameGrid.getGridTile(A.getPosition().y - 1, A.getPosition().x).getTileType() == Tile.entity.POWERUP)
						gameGrid.getGridTile(A.getPosition().y - 1, A.getPosition().x).setIsNinja(true);
					else
						gameGrid.getGridTile(A.getPosition().y - 1, A.getPosition().x).setTileType(Tile.entity.NINJA);

					// Update ninja new position
					A.setPosition(new Point(A.getPosition().x, A.getPosition().y - 1));
					break;
				}
				case "down":
				{
					// If the target tile is a powerup, add the fact that a ninja is hiding under it
					if (gameGrid.getGridTile(A.getPosition().y + 1, A.getPosition().x).getTileType() == Tile.entity.POWERUP)
						gameGrid.getGridTile(A.getPosition().y + 1, A.getPosition().x).setIsNinja(true);
					else
						gameGrid.getGridTile(A.getPosition().y + 1, A.getPosition().x).setTileType(Tile.entity.NINJA);

					// Update ninja new position
					A.setPosition(new Point(A.getPosition().x, A.getPosition().y + 1));
					break;
				}
				case "left":
				{
					// If the target tile is a powerup, add the fact that a ninja is hiding under it
					if (gameGrid.getGridTile(A.getPosition().y, A.getPosition().x - 1).getTileType() == Tile.entity.POWERUP)
						gameGrid.getGridTile(A.getPosition().y, A.getPosition().x - 1).setIsNinja(true);
					else
						gameGrid.getGridTile(A.getPosition().y, A.getPosition().x - 1).setTileType(Tile.entity.NINJA);

					// Update ninja new position
					A.setPosition(new Point(A.getPosition().x - 1, A.getPosition().y));
					break;
				}
				case "right":
				{
					// If the target tile is a powerup, add the fact that a ninja is hiding under it
					if (gameGrid.getGridTile(A.getPosition().y, A.getPosition().x + 1).getTileType() == Tile.entity.POWERUP)
						gameGrid.getGridTile(A.getPosition().y, A.getPosition().x + 1).setIsNinja(true);
					else
						gameGrid.getGridTile(A.getPosition().y, A.getPosition().x + 1).setTileType(Tile.entity.NINJA);

					// Update ninja new position
					A.setPosition(new Point(A.getPosition().x + 1, A.getPosition().y));
					break;
				}
				default:
					return;
			}
		}
	}

	private void movePlayer (String where)
	{
		if (where.toLowerCase().equals("s"))
		{
			if (this.gameGrid.getGridTile(this.Player1.getPosition().y + 1, this.Player1.getPosition().x).getTileType() == Tile.entity.ROOM)
			{
				if (UI.textCheckRoom(this.gameGrid, new Point(this.Player1.getPosition().x, this.Player1.getPosition().y + 1)))
					this.Player1.setHasBriefcase(true);
				return;
			}
		}

		this.gameGrid.getGridTile(Player1.getPosition().y, Player1.getPosition().x).setTileType(Tile.entity.EMPTY);
		this.gameGrid.getGridTile(Player1.getPosition().y, Player1.getPosition().x).setIsUnknownToPlayer(false);

		switch (where.toLowerCase())
		{
			case "w":
				this.Player1.setPosition(new Point(this.Player1.getPosition().x, this.Player1.getPosition().y - 1));
				break;
			case "s":
				this.Player1.setPosition(new Point(this.Player1.getPosition().x, this.Player1.getPosition().y + 1));
				break;
			case "a":
				this.Player1.setPosition(new Point(this.Player1.getPosition().x - 1, this.Player1.getPosition().y));
				break;
			case "d":
				this.Player1.setPosition(new Point(this.Player1.getPosition().x + 1, this.Player1.getPosition().y));
				break;
			default:
				TextUserInterface.printGameString("Error: You must select a valid movement!", false);
		}

		if (this.gameGrid.getGridTile(this.Player1.getPosition().y, this.Player1.getPosition().x).getTileType() == Tile.entity.POWERUP)
		{
			this.gameGrid.getGridTile(this.Player1.getPosition().y, this.Player1.getPosition().x).getPowerUp().usePowerUp(this.Player1, this.UI);

			if (!this.Player1.isHasRadar())
				this.gameGrid.revealBriefcase();
		}

		this.gameGrid.getGridTile(Player1.getPosition().y, Player1.getPosition().x).setTileType(Tile.entity.PLAYER);
	}

	//** Saves all items into binary form in a file*/
	public void saveGame ()
	{
		String save;
		try
		{
			TextUserInterface.printGameString("Enter name of save: ", false);
			save = scanner.nextLine();

			save += ".ninja";
			FileOutputStream fileOut = new FileOutputStream(save);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(this.gameGrid);
			objOut.writeObject(this.Ninja);
			objOut.writeObject(this.Player1);
			objOut.writeObject(this.PowerUps);
			objOut.close();
			fileOut.close();
			TextUserInterface.printGameString("Game saved successfully to " + save, false);
		}
		catch (IOException e)
		{
			TextUserInterface.printGameString("Error saving the game!", false);
			TextUserInterface.printGameString("Java Error: " + e, true);
		}
	}

	//Loads all items from a binary file and edits all variables inside game engine.
	public void loadGame ()
	{
		String load;

		try
		{
			TextUserInterface.printGameString("Type the name of the game save ending with \".ninja\": ", false);
			load = scanner.nextLine();

			FileInputStream fileIn = new FileInputStream(load);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			gameGrid = (Grid) objIn.readObject();
			Ninja = (Enemy[]) objIn.readObject();
			Player1 = (Player) objIn.readObject();
			PowerUps = (PowerUp[]) objIn.readObject();

			objIn.close();
			fileIn.close();

		}
		catch (IOException e)
		{
			TextUserInterface.printGameString("Error loading the game!", false);
			TextUserInterface.printGameString("Java Error: " + e, true);
		}
		catch (ClassNotFoundException e)
		{
			TextUserInterface.printGameString("Error: Something went wrong with loading!", false);
			TextUserInterface.printGameString("Java Error: " + e, true);
		}

	}

	/**
	 * Generate position for a powerup. Keeping in mind we cannot place the powerup where Ninjas currently reside, where
	 * the player spawns [8,0], a building is (static position, haven't drawn it out yet), or where another powerup resides.
	 */
	private void generatePosition ()
	{

		// this.position.x = randNum
		// this.position.y = randNum
	}

	/**
	 * Since I don't know how to use an Array Param Constructor with the Constructor as the array, here's a hacky way.
	 *
	 * @param PU
	 */
	private void initPowerUps (PowerUp[] PU)
	{
		int size = PU.length;

		for (int x = 0; x < size; x++)
			PU[x] = new PowerUp();
	}

	/**
	 * Since I don't know how to use an Array Param Constructor with the Constructor as the array, here's a hacky way.
	 *
	 * @param EN
	 */
	private void initNinjas (Enemy[] EN)
	{
		int size = EN.length;

		for (int x = 0; x < size; x++)
			EN[x] = new Enemy();
	}

	//** Sets all the room locations so that the ninja won't spawn here. Todo Do this better */
	private void setInvalidPoints ()
	{
		invalidPoints[0] = new Point(1, 1);
		invalidPoints[1] = new Point(4, 1);
		invalidPoints[2] = new Point(7, 1);
		invalidPoints[3] = new Point(1, 4);
		invalidPoints[4] = new Point(4, 4);
		invalidPoints[5] = new Point(7, 4);
		invalidPoints[6] = new Point(1, 7);
		invalidPoints[7] = new Point(4, 7);
		invalidPoints[8] = new Point(7, 7);
		invalidPoints[9] = new Point(0, 8);
	}

	public enum Action
	{
		MOVEMENT, SHOOT, LOOK, SAVE, LOAD, NULL
	}

}


//TODO Output all variables.
/*
 int i = 42;
 DataOutputStream os = new DataOutputStream(new FileOutputStream("C:\\binout.dat"));
 os.writeInt(i);
 os.close();
 */
