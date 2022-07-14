import java.awt.*;
/**
 * Class that handles everything related to the enemy.
 */
public class Enemy implements java.io.Serializable
{
	private Point position;
	private boolean isAlive;
	private boolean noMoveThisTurn;

	Enemy ()
	{
		this.position = new Point(0, 0);
		this.isAlive = true;
		this.noMoveThisTurn = false;

		setNinjaPosition();
	}
	
	/**
	 * Method that checks up, down, left, right to see if the next tile over is the player.
	 * If it is, then the player is killed.
	 */

	public boolean killedPlayer (Grid gameGrid)
	{
		if (this.position.y + 1 <= 8)
			if (gameGrid.getGridTile(position.y + 1, position.x).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.y - 1 >= 0)
			if (gameGrid.getGridTile(position.y - 1, position.x).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.x + 1 <= 8)
			if (gameGrid.getGridTile(position.y, position.x + 1).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.x - 1 >= 0)
			if (gameGrid.getGridTile(position.y, position.x - 1).getTileType() == Tile.entity.PLAYER)
				return true;
		return false;
	}

	/**
	 * Method that checks if the ninja can move in a specific direction.
	 * Checks in up, down, left, right to see if the tile that the ninja wants to move is a room, another ninja, or a player.
	 */
	public boolean canMoveDirection (String direction, Grid gameGrid)
	{
		switch (direction.toLowerCase())
		{
			case "up":
			{
				if (this.position.y - 1 >= 0)
					if (gameGrid.getGridTile(this.position.y - 1, this.position.x).getTileType() != Tile.entity.ROOM && gameGrid.getGridTile(this.position.y - 1, this.position.x).getTileType() != Tile.entity.NINJA && gameGrid.getGridTile(this.position.y - 1, this.position.x).getTileType() != Tile.entity.PLAYER)
						return true;
				break;
			}
			case "down":
			{
				if (this.position.y + 1 <= 8)
					if (gameGrid.getGridTile(this.position.y + 1, this.position.x).getTileType() != Tile.entity.ROOM && gameGrid.getGridTile(this.position.y + 1, this.position.x).getTileType() != Tile.entity.NINJA && gameGrid.getGridTile(this.position.y + 1, this.position.x).getTileType() != Tile.entity.PLAYER)
						return true;
				break;
			}
			case "left":
			{
				if (this.position.x - 1 >= 0)
					if (gameGrid.getGridTile(this.position.y, this.position.x - 1).getTileType() != Tile.entity.ROOM && gameGrid.getGridTile(this.position.y, this.position.x - 1).getTileType() != Tile.entity.NINJA && gameGrid.getGridTile(this.position.y, this.position.x - 1).getTileType() != Tile.entity.PLAYER)
						return true;
				break;
			}
			case "right":
			{
				if (this.position.x + 1 <= 8)
					if (gameGrid.getGridTile(this.position.y, this.position.x + 1).getTileType() != Tile.entity.ROOM && gameGrid.getGridTile(this.position.y, this.position.x + 1).getTileType() != Tile.entity.NINJA && gameGrid.getGridTile(this.position.y, this.position.x + 1).getTileType() != Tile.entity.PLAYER)
						return true;
				break;
			}
		}

		return false;
	}
	
	/**
	 * Find the ninja a valid tile to move on, randomly chooses the direction.
	 * If the ninja is surrounded by invalid tiles, then this method will check all the directions first and then make the ninja
	 * not move during its turn.
	 */
	public String findValidMovement (Grid gameGrid)
	{
		int lazyStrikeSystem = 0;
		this.noMoveThisTurn = false;
		String[] directions = {"up", "down", "left", "right"};

		int randNumber = (GameEngine.generateRandNum(4) - 1);

		// Will be infinite loop if Ninja cannot move.
		// Todo Possible strike system? Attempt all valid movements, if none work, ninja stays put for the turn?
		while (!this.canMoveDirection(directions[randNumber], gameGrid) && lazyStrikeSystem <= 3)
		{
			randNumber = GameEngine.generateRandNum(3) - 1;
			lazyStrikeSystem++;
		}

		if (lazyStrikeSystem >= 3)
		{
			TextUserInterface.printGameString("This Ninja will stay put this turn!", true);
			this.noMoveThisTurn = true;
		}


		return directions[randNumber];
	}


	private void setNinjaPosition ()
	{
		Point generated = new Point();

		while (true)
		{
			// 0 - 8
			generated.x = GameEngine.generateRandNum(9) - 1;
			generated.y = GameEngine.generateRandNum(9) - 1;

			// My terrible way of covering surface
			//GOOD AREA
			if (GameEngine.isValidPoint(generated) && !GameEngine.getPointsUsedByNinja().contains(generated))
			{
				if (generated.y < 5)
					break;

				if (generated.y >= 5 && generated.x >= 4)
					break;
			}

		}

		GameEngine.getPointsUsedByNinja().add(generated);
		this.position = generated;
	}

	public void moveUnit (boolean isAI)
	{
		if (isAI)
		{
			//...
		}
	}

	public void killSelf ()
	{
		this.isAlive = false;
	}

	public boolean getIsAlive ()
	{
		return this.isAlive;
	}

	public Point getPosition ()
	{
		return position;
	}

	public void setPosition (Point position)
	{
		this.position = position;
	}

	public boolean getNoMoveThisTurn ()
	{
		return this.noMoveThisTurn;
	}
}
