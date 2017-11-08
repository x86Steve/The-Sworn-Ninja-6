package testpack;

import java.awt.*;
import java.io.Serializable;

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

public class Tile implements Serializable
{
	private final String Player = "[ PLR ]";
	private final String Ninja = "[ >:D ]";
	private final String PowerUp = "[ PWR ]";
	private final String Room = "[ RMM ]";
	private final String Briefcase = "[ BRF ]";
	private final String Unknown = "[ *** ]";
	private final String Empty = "[     ]";
	private entity tileType;
	private boolean isPlayer;
	private boolean isNinja;
	private boolean isPowerUp;
	private boolean isRoom;
	private boolean isBriefcase;
	private boolean isUnknownToPlayer;
	private boolean isEmpty;
	private boolean isRoomEnterable;
	private Point position;
	private String tileStringRepresentation;

	Tile (Point location)
	{
		//** Although this will set the tiles to "Blank," as we fill tiles, we will be able to see the true entities. */
		if (GameEngine.debugMode)
		{
			this.isUnknownToPlayer = false;
			this.tileStringRepresentation = Empty;
		}
		else
		{
			this.isUnknownToPlayer = true;
			this.tileStringRepresentation = this.Unknown;
		}


		this.isPlayer = false;
		this.isBriefcase = false;
		this.isNinja = false;
		this.isPowerUp = false;
		this.isRoom = false;
		this.isBriefcase = false;
		this.isEmpty = true;
		this.tileType = entity.EMPTY;

		this.position = location;
	}

	public Point getPoint ()
	{
		return position;
	}

	public boolean isBriefcase ()
	{
		return isBriefcase;
	}

	public boolean isRoom ()
	{
		return isRoom;
	}

	public boolean isNinja ()
	{
		return isNinja;
	}

	public boolean isPlayer ()
	{
		return isPlayer;
	}

	public boolean isPowerUp ()
	{
		return isPowerUp;
	}

	public boolean isUnknownToPlayer ()
	{
		return isUnknownToPlayer;
	}

	public void setIsNinja(boolean bool)
	{
		this.isNinja = bool;
	}

	public void setIsPowerUp(boolean bool)
	{
		this.isPowerUp  = bool;
	}

	public void setIsUnknownToPlayer(boolean bool)
	{
		//setting it false
		if (!bool)
		{
			if (this.isPowerUp)
				this.tileStringRepresentation = PowerUp;

			else if (this.isNinja)
				this.tileStringRepresentation = Ninja;

			else if (this.isEmpty)
				this.tileStringRepresentation = Empty;
		}

		this.isUnknownToPlayer = bool;
	}

	/**
	 * A ninja can move into a square if it's empty, OR a powerup resides.
	 *
	 * @return True - A ninja movement is possible. False - Otherwise. Wall, other ninja, etc.
	 */
	public boolean isAvailableForNinja ()
	{
		return this.isPowerUp || this.isEmpty;
	}

	public void setTileType (entity Type)
	{
		this.tileType = Type;

		switch (Type)
		{
			case NINJA:
			{
				tileType = entity.NINJA;
				this.isNinja = true;
				this.isEmpty = false;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.Ninja;
				break;
			}
			case PLAYER:
			{

				this.isEmpty = false;
				this.isPowerUp = false;
				this.isPlayer = true;
				tileType = entity.PLAYER;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.Player;
				break;
			}
			case POWERUP:
			{
				tileType = entity.POWERUP;
				this.isEmpty = false;
				this.isPowerUp = true;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.PowerUp;
				break;
			}
			case ROOM:
			{
				tileType = entity.ROOM;
				this.isRoom = true;
				this.isRoomEnterable = false;
				this.isUnknownToPlayer = false;
				this.isEmpty = false;
				this.tileStringRepresentation = this.Room;
				break;
			}
			case EMPTY:
			{
				tileType = entity.EMPTY;
				this.isEmpty = true;
				this.isPlayer = false;
				this.isNinja = false;
				this.tileStringRepresentation = this.Empty;
				break;
			}
			case BRIEFCASE:
			{

				this.isBriefcase = true;
				this.tileStringRepresentation = this.Briefcase;
				break;
			}
			case UNKNOWN:
			{
				tileType = entity.UNKNOWN;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.Unknown;
				break;
			}
		}
	}

	public void setPosition (Point position)
	{
		this.position = position;
	}

	@Override
	public String toString ()
	{
		// Game NOT in debug mode
		if (!GameEngine.debugMode)
		{
			if (this.isPlayer)
				return this.Player;

			if (this.isUnknownToPlayer)
				return this.Unknown;

			return this.tileStringRepresentation;
		}

		if (this.isEmpty)
			return this.Empty;

		return this.tileStringRepresentation;
	}

	public enum entity
	{
		PLAYER, NINJA, POWERUP, ROOM, BRIEFCASE, UNKNOWN, EMPTY
	}

	public entity getTileType ()
	{
		return tileType;
	}
}
