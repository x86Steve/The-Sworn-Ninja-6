
package testpack;

import java.awt.*;
import java.util.InputMismatchException;

public class TextUserInterface
{
    /*
    Error Strings
     */

	private final String incorretInput = "Yo, can we just, you know, follow directions and enter a number please," + " or whatever the correct input SHOULD be. These Java sanitation shiz are annoying" + " Thanks.";

	/**
	 * Constructor
	 */
	TextUserInterface ()
	{
		printGameString("TextUserInterface constructed!", true);
	}

	/**
	 * @param s           String to print, works just like System.out.println(), just shorter to type
	 * @param debugString True - Appends "DEBUG" on the string, else, prints normally. debugMode will be a global within GameEngine.
	 */
	public static void printGameString (Object s, boolean debugString)
	{
		if (GameEngine.debugMode && debugString)
			System.out.println("DEBUG: " + s.toString());
		else if (debugString && !GameEngine.debugMode)
			return;
		else
			System.out.println(s.toString());
	}

	/**
	 * Prints out welcome screen complete with controls and story? Lol?
	 */
	public void welcomeScreen ()
	{
		printGameString("This will be the welcome screen!", false);
	}

	public void printPlayerCommands ()
	{
		printGameString("1. Look", false);
		printGameString("2. Move", false);
		printGameString("3. Shoot", false);
		printGameString("4. Save Game", false);
		printGameString("5. Load Game", false);
	}

	/**
	 * Takes grid and prints out it's current state.
	 *
	 * @param grid This is the grid that was instantiated at game start. The grid class handles the states,
	 *             this prints them out.
	 */
	public void drawGrid (Grid grid, Player player)
	{
		int size = grid.getGridTiles().length;

		for (int x = 0; x < size; x++)
		{
			if (x > 0)
				printGameString("", false);
			for (int j = 0; j < size; j++)
				System.out.print(grid.getGridTiles()[x][j].toString(player));

			printPlayerInfo(x, player);
		}
		// Newline
		printGameString("", false);
	}

	private void printPlayerInfo (int stat, Player player)
	{
		switch (stat)
		{
			case 4:
			{
				System.out.print("     Player lives: " + player.getLives());
				break;
			}
			case 5:
			{
				System.out.print("     Bullet: " + (player.isHasBullet() ? "Armed" : "No Ammo"));
				break;
			}
			case 6:
			{
				System.out.print("     Invincibility: " + (player.getInvincibilityCount() > 0 ? player.getInvincibilityCount() + " stabs" : "No"));
				break;
			}
			case 7:
			{
				System.out.print("     Radar: " + (player.isHasRadar() ? "Enabled" : "Disabled"));
				break;
			}
		}
	}

	/**
	 * This will print out the commands associated with regular gameplay.
	 *
	 * @return Returns the value according to menu choices to then be used in game engine
	 */
	public GameEngine.Action getPlayerOption ()
	{
		//need to fix this variable.
		int playerMenuSize = 5;
		int userChoice = 0;

		printGameString("Please enter your Choice", false);

		// This needs to be redone, ugly while statement. I want the menu size to be dynamic, you know, we might add on
		// more options.
		while (userChoice == 0 || userChoice > playerMenuSize || userChoice < 0)
		{
			printPlayerCommands();

			try
			{
				userChoice = Integer.parseInt(GameEngine.scanner.nextLine());
			}
			catch (InputMismatchException e)
			{
				printGameString(incorretInput, false);
				printGameString("Java Error: " + e, true);
				userChoice = 0;
			}
			catch (NumberFormatException e)
			{
				printGameString(incorretInput, false);
				printGameString("Java Error: " + e, true);
				userChoice = 0;
			}

			switch (userChoice)
			{
				case 1:
					return GameEngine.Action.LOOK;
				case 2:
					return GameEngine.Action.MOVEMENT;
				case 3:
					return GameEngine.Action.SHOOT;
				case 4:
					return GameEngine.Action.SAVE;
				case 5:
					return GameEngine.Action.LOAD;
				default:
					TextUserInterface.printGameString("Error: Enter a number within the menu!", false);
			}

		}
		return GameEngine.Action.NULL;
	}

	public boolean textCheckRoom (Grid gameGrid, Point position)
	{
		if (gameGrid.getGridTile(position.y, position.x).isBriefcase())
		{
			printGameString("You win! You found the briefcase!", false);
			return true;
		}

		else
		{
			printGameString("Unfortunate. No briefcase here", false);
			return false;
		}
	}

	public void printKilledNinja ()
	{
		printGameString("You killed a Ninja!", false);
	}

	public void printLostALife ()
	{
		printGameString("You were stabbed! You've lost a life and are returned to the start!", false);
	}

	public void printLostInvincibility ()
	{
		printGameString("You defended against an attack! Your invincibility has been decremented!", false);
	}

	public void printUsedInvincibilityUp ()
	{
		printGameString("Used invincibility power up! Immune to 5 stabs!", false);
	}

	public void printUsedBulletPowerUp ()
	{
		printGameString("Used Bullet PowerUp! If you did NOT have a bullet, it has been replenished!", false);
	}

	public void printUsedRadarPowerUp ()
	{
		printGameString("Used Radar PowerUp! Briefcase location has been revealed!", false);
	}

	public void printOutOfAmmo ()
	{
		printGameString("You have no bullets!", false);
	}

	public String askPlayerDirection (Player player, Grid grid)
	{
		String input = "NULL";

		while (true)
		{
			printGameString("You have the following choices for " + (player.isHasBullet() ? "shooting" : "movement") + ":", false);

			if (player.canMove("up", grid))
				printGameString("W. Up", false);

			if (player.canMove("down", grid))
				printGameString((grid.getGridTile(player.getPosition().y + 1, player.getPosition().x).getTileType() == Tile.entity.ROOM && !player.isShooting()) ? "S. Check Room ( Consumes Turn )" : "S. Down", false);

			if (player.canMove("left", grid))
				printGameString("A. Left", false);

			if (player.canMove("right", grid))
				printGameString("D. Right", false);

			input = GameEngine.scanner.nextLine();

			if (player.canMove(input, grid))
				return input;
		}
	}
}
