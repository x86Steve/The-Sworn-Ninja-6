import java.awt.*;
import java.util.Vector;

/**
 * Player java class that handles all things related to the player, including lives, bullets, powerups, etc 
 */

public class Player implements java.io.Serializable
{

	private int lives;

	private boolean isShooting; //Hacky text override for askPlayerDirection()
	private boolean isAlive;
	private boolean hasBullet;
	private boolean hasBriefcase;
	private boolean hasRadar;
	private int invincibilityCount;
	private Point position;
	private Vector<Point> tilesVisable;

	Player ()
	{
		this.lives = 3;
		this.isAlive = true;
		this.hasBullet = true;
		this.hasBriefcase = false;
		this.hasRadar = false;
		this.invincibilityCount = 0;
		tilesVisable = new Vector<>();

		/** Starting point for player */
		this.position = new Point(0, 8);
	}

	public void setIsAlive (boolean alive)
	{
		this.isAlive = alive;

		if (!alive)
			TextUserInterface.printGameString("Oh no! You were slain!", false);
	}

	public boolean isPlayerAlive ()
	{
		return this.isAlive;
	}

	public boolean isHasBullet ()
	{
		return this.hasBullet;
	}

	public boolean isHasBriefcase ()
	{
		return this.hasBriefcase;
	}

	public void setHasBriefcase (boolean hasBriefcase)
	{
		this.hasBriefcase = hasBriefcase;
	}

	public int getInvincibilityCount ()
	{
		return this.invincibilityCount;
	}

	public Point getPosition ()
	{
		return position;
	}

	public void setPosition (Point position)
	{
		this.position = position;
	}

	public void decrementLives ()
	{
		this.lives--;
	}

	public int getLives ()
	{
		return this.lives;
	}

	public boolean isShooting ()
	{
		return isShooting;
	}

	public boolean isHasRadar ()
	{
		return hasRadar;
	}
	
	/**
	 * Shoot command works by having the player choose a direction, then continuously checking to see if there are any
	 * objects (ninjas, rooms, the edge of the map) are in the way and reacts accordingly.
	 */

	public Point shoot (Grid gameGrid, TextUserInterface UI)
	{
		this.isShooting = true;
		int size = gameGrid.getGridTiles().length;
		this.hasBullet = false;

		switch (UI.askPlayerDirection(this, gameGrid).toLowerCase())
		{
			case "w":
			{
				for (int counter = 1; counter < size; counter++)
				{
					if (this.getPosition().y - counter >= 0)
						if (gameGrid.getGridTile(this.position.y - counter, this.position.x).getTileType() == Tile.entity.ROOM)
							break;
						else if (gameGrid.getGridTile(this.position.y - counter, this.position.x).getTileType() == Tile.entity.NINJA)
						{
							gameGrid.getGridTile(this.position.y - counter, this.position.x).setTileType(Tile.entity.DEADNINJA);
							UI.printKilledNinja();
							this.isShooting = false;
							return new Point(this.position.x, this.position.y - counter);
						}
				}
				break;
			}

			case "s":
			{
				for (int counter = 1; counter < size; counter++)
				{
					if (this.getPosition().y + counter <= 8)
						if (gameGrid.getGridTile(this.position.y + counter, this.position.x).getTileType() == Tile.entity.ROOM)
							break;
						else if (gameGrid.getGridTile(this.position.y + counter, this.position.x).getTileType() == Tile.entity.NINJA)
						{
							gameGrid.getGridTile(this.position.y + counter, this.position.x).setTileType(Tile.entity.DEADNINJA);
							UI.printKilledNinja();
							this.isShooting = false;
							return new Point(this.position.x, this.position.y + counter);
						}
				}
				break;
			}

			case "a":
			{
				for (int counter = 1; counter < size; counter++)
				{
					if (this.getPosition().x - counter >= 0)
						if (gameGrid.getGridTile(this.position.y, this.position.x - counter).getTileType() == Tile.entity.ROOM)
							break;
						else if (gameGrid.getGridTile(this.position.y, this.position.x - counter).getTileType() == Tile.entity.NINJA)
						{
							gameGrid.getGridTile(this.position.y, this.position.x - counter).setTileType(Tile.entity.DEADNINJA);
							UI.printKilledNinja();
							this.isShooting = false;
							return new Point(this.position.x - counter, this.position.y);
						}
				}
				break;
			}

			case "d":
			{
				for (int counter = 1; counter < size; counter++)
				{
					if (this.getPosition().x + counter <= 8)
						if (gameGrid.getGridTile(this.position.y, this.position.x + counter).getTileType() == Tile.entity.ROOM)
							break;
						else if (gameGrid.getGridTile(this.position.y, this.position.x + counter).getTileType() == Tile.entity.NINJA)
						{
							gameGrid.getGridTile(this.position.y, this.position.x + counter).setTileType(Tile.entity.DEADNINJA);
							UI.printKilledNinja();
							this.isShooting = false;
							return new Point(this.position.x + counter, this.position.y);
						}
				}
				break;
			}
		}
		this.isShooting = false;

		return null;
	}

	/**
	 * This block of code handles the powerups, keeping track of if the player has the powerup, and how much of a powerup.
	 * Powerup has its own class that handles spawning and positioning.
	 */
	public void decrementInvincibility ()
	{
		if (invincibilityCount > 0)
			this.invincibilityCount--;
	}

	public void useInvincibilityPowerUp ()
	{
		this.invincibilityCount = 5;
	}

	public void useBulletPowerUp ()
	{
		if (!this.hasBullet)
			this.hasBullet = true;
	}

	public void useRadarPowerUp ()
	{
		if (!this.hasRadar)
			this.hasRadar = true;
	}

	/**
	 * Checks to see if the player can move onto a particular tile.
	 * Works by checkign to see if the tile is empty, has a powerup, dead (or soon to be dead) ninja.
	 */
	public boolean canMove (String direction, Grid grid)
	{
		switch (direction.toLowerCase())
		{
			case "w":
			case "up":
			{
				if (this.position.y - 1 >= 0)
					if (grid.getGridTile(this.position.y - 1, this.position.x).getTileType() == Tile.entity.EMPTY || grid.getGridTile(this.position.y - 1, this.position.x).getTileType() == Tile.entity.POWERUP || grid.getGridTile(this.position.y - 1, this.position.x).getTileType() == Tile.entity.DEADNINJA || (grid.getGridTile(this.position.y - 1, this.position.x).getTileType() == Tile.entity.NINJA && this.isShooting))
						return true;
				return false;
			}
			case "s":
			case "down":
			{
				if (this.position.y + 1 <= 8)
					if (grid.getGridTile(this.position.y + 1, this.position.x).getTileType() == Tile.entity.EMPTY || grid.getGridTile(this.position.y + 1, this.position.x).getTileType() == Tile.entity.ROOM || grid.getGridTile(this.position.y + 1, this.position.x).getTileType() == Tile.entity.POWERUP || grid.getGridTile(this.position.y + 1, this.position.x).getTileType() == Tile.entity.DEADNINJA || (grid.getGridTile(this.position.y + 1, this.position.x).getTileType() == Tile.entity.NINJA && this.isShooting))
						return true;
				return false;
			}
			case "a":
			case "left":
			{
				if (this.position.x - 1 >= 0)
					if (grid.getGridTile(this.position.y, this.position.x - 1).getTileType() == Tile.entity.EMPTY || grid.getGridTile(this.position.y, this.position.x - 1).getTileType() == Tile.entity.POWERUP || grid.getGridTile(this.position.y, this.position.x - 1).getTileType() == Tile.entity.DEADNINJA || (grid.getGridTile(this.position.y, this.position.x - 1).getTileType() == Tile.entity.NINJA && this.isShooting))
						return true;
				return false;
			}
			case "d":
			case "right":
			{
				if (this.position.x + 1 <= 8)
					if (grid.getGridTile(this.position.y, this.position.x + 1).getTileType() == Tile.entity.EMPTY || grid.getGridTile(this.position.y, this.position.x + 1).getTileType() == Tile.entity.POWERUP || grid.getGridTile(this.position.y, this.position.x + 1).getTileType() == Tile.entity.DEADNINJA || (grid.getGridTile(this.position.y, this.position.x + 1).getTileType() == Tile.entity.NINJA && this.isShooting))
						return true;
				return false;
			}
			default:
				TextUserInterface.printGameString("Error: You must select a valid movement!", false);
		}

		return false;
	}
	
	

	public void revealSquares (Grid gameGrid, int lineOfSight)
	{
		if (GameEngine.debugMode)
			return;

		// Save all tiles that the player can see, so that we can set other tiles as unknown, as he loses sight.
		tilesVisable.clear();

		// Reveal all 4 directions
		for (int counter = 1; counter <= lineOfSight; counter++)
		{
			// Reveal UP
			if (this.position.y - counter >= 0)
			{
				if (gameGrid.getGridTile(this.position.y - counter, this.position.x).getTileType() == Tile.entity.ROOM)
					break;
				else
				{
					gameGrid.getGridTile(this.position.y - counter, this.position.x).setIsUnknownToPlayer(false);
					tilesVisable.add(new Point(this.position.x, this.position.y - counter));
				}
			}
		}

		for (int counter = 1; counter <= lineOfSight; counter++)
		{
			// Reveal DOWN
			if (this.position.y + counter <= 8)
			{
				if (gameGrid.getGridTile(this.position.y + counter, this.position.x).getTileType() == Tile.entity.ROOM)
					break;
				else
				{
					gameGrid.getGridTile(this.position.y + counter, this.position.x).setIsUnknownToPlayer(false);
					tilesVisable.add(new Point(this.position.x, this.position.y + counter));
				}
			}
		}

		for (int counter = 1; counter <= lineOfSight; counter++)
		{
			// Reveal LEFT
			if (this.position.x - counter >= 0)
			{
				if (gameGrid.getGridTile(this.position.y, this.position.x - counter).getTileType() == Tile.entity.ROOM)
					break;
				else
				{
					gameGrid.getGridTile(this.position.y, this.position.x - counter).setIsUnknownToPlayer(false);
					tilesVisable.add(new Point(this.position.x - counter, this.position.y));
				}
			}
		}

		for (int counter = 1; counter <= lineOfSight; counter++)
		{
			// Reveal RIGHT
			if (this.position.x + counter <= 8)
			{
				if (gameGrid.getGridTile(this.position.y, this.position.x + counter).getTileType() == Tile.entity.ROOM)
					break;
				else
				{
					gameGrid.getGridTile(this.position.y, this.position.x + counter).setIsUnknownToPlayer(false);
					tilesVisable.add(new Point(this.position.x + counter, this.position.y));
				}
			}
		}

		// Now, lets iterate through squares that were revealed previously, and set them as unknown.
		int size = gameGrid.getGridTiles().length;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				if (!tilesVisable.contains(new Point(x, y)) && gameGrid.getGridTile(y, x).getTileType() != Tile.entity.ROOM && gameGrid.getGridTile(y, x).getTileType() != Tile.entity.PLAYER)
					gameGrid.getGridTile(y, x).setIsUnknownToPlayer(true);
	}
}
