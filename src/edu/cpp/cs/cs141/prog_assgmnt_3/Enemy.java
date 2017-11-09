package testpack;

import java.awt.*;

/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodriguez
 * <p>
 * Programming Assignment #3 (Final)
 * <p>
 * Sworn Ninja 6
 * <p>
 * Team Red
 * <p>
 * Kevin @kscroggs  - kscrogginsjr@gmail.com
 * Albert @alberthwang  - albert.is.hwang@gmail.com
 * Steve @Steve | C0deFlex  - scoseguera@cpp.edu
 * Jimmy @jimmy  - jdojimmy@gmail.com
 * Min @minimineral  -  minoook@hotmail.com
 * Dan @Dan L. - Djluoma@cpp.edu
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

	public boolean killedPlayer(Grid gameGrid)
	{
		if (this.position.y + 1 <= 8)
			if (gameGrid.getGridTile(position.y + 1,position.x).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.y - 1 >= 0)
			if (gameGrid.getGridTile(position.y - 1,position.x).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.x + 1 <= 8)
			if (gameGrid.getGridTile(position.y,position.x + 1).getTileType() == Tile.entity.PLAYER)
				return true;

		if (position.x - 1 >= 0)
			if (gameGrid.getGridTile(position.y,position.x - 1).getTileType() == Tile.entity.PLAYER)
				return true;
		return false;
	}

	public boolean canMoveDirection(String direction, Grid gameGrid)
	{
		switch (direction.toLowerCase())
		{
			case "up":
			{
				if (this.position.y - 1 >= 0)
					if (gameGrid.getGridTile(this.position.y - 1,this.position.x).getTileType() != Tile.entity.ROOM &&
							gameGrid.getGridTile(this.position.y - 1,this.position.x).getTileType() != Tile.entity.NINJA)
						return true;
				break;
			}
			case "down":
			{
				if (this.position.y + 1 <= 8)
					if (gameGrid.getGridTile(this.position.y + 1,this.position.x).getTileType() != Tile.entity.ROOM &&
							gameGrid.getGridTile(this.position.y + 1,this.position.x).getTileType() != Tile.entity.NINJA)
						return true;
				break;
			}
			case "left":
			{
				if (this.position.x - 1 >= 0)
					if (gameGrid.getGridTile(this.position.y ,this.position.x - 1).getTileType() != Tile.entity.ROOM &&
							gameGrid.getGridTile(this.position.y,this.position.x - 1).getTileType() != Tile.entity.NINJA)
						return true;
				break;
			}
			case "right":
			{
				if (this.position.x + 1 <= 8)
					if (gameGrid.getGridTile(this.position.y ,this.position.x + 1).getTileType() != Tile.entity.ROOM &&
							gameGrid.getGridTile(this.position.y ,this.position.x + 1).getTileType() != Tile.entity.NINJA)
						return true;
				break;
			}
		}

		return false;
	}

	public String findValidMovement(Grid gameGrid)
	{
		int lazyStrikeSystem = 0;
		this.noMoveThisTurn = false;
		String[] directions = {"up","down","left","right"};

		int randNumber = ( GameEngine.generateRandNum(4) - 1 );

		// Will be infinite loop if Ninja cannot move.
		// Todo Possible strike system? Attempt all valid movements, if none work, ninja stays put for the turn?
		while(!this.canMoveDirection(directions[randNumber],gameGrid) && lazyStrikeSystem <= 3)
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

	public boolean getNoMoveThisTurn()
	{
		return this.noMoveThisTurn;
	}
}
