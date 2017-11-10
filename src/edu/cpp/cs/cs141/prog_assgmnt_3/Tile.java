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
	private final String Room = "[ RMM ]";
	private final String Briefcase = "[ BRF ]";
	private final String Unknown = "[ *** ]";
	private final String Empty = "[     ]";
	private final String deadNinja = "[ x_X ]";

	private entity tileType;

	private PowerUp powerUp;
	private boolean isDeadNinja;
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
		this.isPlayer = false;
		this.isBriefcase = false;
		this.isNinja = false;
		this.isPowerUp = false;
		this.isRoom = false;
		this.isBriefcase = false;
		this.isEmpty = true;
		this.isDeadNinja = false;
		this.tileType = entity.EMPTY;

		this.position = location;
	}

	Tile (Point location, PowerUp powerUp)
	{
		this.isPlayer = false;
		this.isBriefcase = false;
		this.isNinja = false;
		this.isPowerUp = true;
		this.isRoom = false;
		this.isBriefcase = false;
		this.isEmpty = true;
		this.tileType = entity.EMPTY;

		this.position = location;

		this.powerUp = powerUp;
	}

	public entity getTileType ()
	{
		return tileType;
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
				this.isPowerUp = false;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.Ninja;
				break;
			}
			case DEADNINJA:
			{
				this.tileType = entity.DEADNINJA;
				this.isEmpty = true;
				this.isNinja = false;
				this.isPowerUp = false;
				this.isUnknownToPlayer = false;
				this.isDeadNinja = true;
				this.tileStringRepresentation = this.deadNinja;
				break;
			}
			case PLAYER:
			{

				this.isEmpty = false;
				this.isPowerUp = false;
				this.isPlayer = true;
				tileType = entity.PLAYER;
				this.isUnknownToPlayer = false;
				this.tileStringRepresentation = this.Player;
				break;
			}
			case POWERUP:
			{
				tileType = entity.POWERUP;
				this.isEmpty = false;
				this.isPowerUp = true;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.powerUp.toString();
				break;
			}
			case ROOM:
			{
				tileType = entity.ROOM;
				this.isRoom = true;
				this.isRoomEnterable = false;
				this.isUnknownToPlayer = false;
				this.isEmpty = false;
				this.isBriefcase = false;
				this.tileStringRepresentation = this.Room;
				break;
			}
			case EMPTY:
			{
				tileType = entity.EMPTY;
				this.isEmpty = true;
				this.isPlayer = false;
				this.isNinja = false;
				this.isPowerUp = false;
				this.isUnknownToPlayer = true;
				this.tileStringRepresentation = this.Empty;
				break;
			}
			case BRIEFCASE:
			{
				this.isRoom = true;
				this.isUnknownToPlayer = true;
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

	public Point getPoint ()
	{
		return position;
	}

	public boolean isBriefcase ()
	{
		return isBriefcase;
	}

	public void setBriefcase (boolean isBriefcase)
	{
		this.isBriefcase = isBriefcase;
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

	public void setIsNinja (boolean bool)
	{
		this.isNinja = bool;
	}

	public void setIsPowerUp (boolean bool)
	{
		this.isPowerUp = bool;
	}

	public PowerUp getPowerUp ()
	{
		return this.powerUp;
	}

	public void setPowerUp (PowerUp pwr)
	{
		this.powerUp = pwr;
	}

	public void setIsUnknownToPlayer (boolean bool)
	{
		//setting it false
		if (!bool)
		{
			if (this.isPowerUp)
				this.tileStringRepresentation = powerUp.toString();

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

	public void setPosition (Point position)
	{
		this.position = position;
	}

	public String toString (Player player)
	{
		// Game NOT in debug mode
		if (!GameEngine.debugMode)
		{
			if (this.isPlayer)
				return this.Player;

			if (this.isUnknownToPlayer)
				return this.Unknown;

			if (!this.isUnknownToPlayer && this.isRoom && this.isBriefcase && player.isHasRadar())
				return this.Briefcase;

			if (this.isDeadNinja && !this.isUnknownToPlayer)
				return this.deadNinja;

			return this.tileStringRepresentation;
		}

		else
		{
			if (this.isEmpty && this.isDeadNinja)
				return this.deadNinja;
			else if (this.isEmpty)
				return this.Empty;

			if (this.isPowerUp && this.isNinja)
				return this.Ninja;

			if (this.isRoom && this.isBriefcase)
				return this.Briefcase;
		}

		return this.tileStringRepresentation;
	}


	public enum entity
	{
		PLAYER, NINJA, POWERUP, ROOM, BRIEFCASE, UNKNOWN, EMPTY, DEADNINJA
	}

}
