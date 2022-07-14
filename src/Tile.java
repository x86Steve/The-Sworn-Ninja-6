
import java.awt.*;
import java.io.Serializable;

/**
 * This class handles the tiles that can hold a player, dead ninjas, living ninjas, powerups, etc.
 * Essentially, the grid is a 2 dimensional array of tiles
 * Each tile has a bunch of boolean parameters of what thing (if anything) resides on it.
 */
public class Tile implements Serializable
{
	private final String Player = "[ ME ]";
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
	
	/**
	 * Empty tile construction
	 * @param location Point object with an x and y postion
	 */
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

	/**
	 * Constructor for a tile that contains a power up
	 * @param location Point object with and x and y position
	 * @param powerUp PowerUp object that the tile will contain
	 */
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

	/**
	 * Returns the enum type for the tile
	 * @return 'entity' enum classifying the tile.
	 */
	public entity getTileType ()
	{
		return tileType;
	}
	
	/**
	 * Sets the tile type
	 * @param Type entity enum
	 */
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
	
	/** 
	 * Returns the point of the tile.
	 */
	public Point getPoint ()
	{
		return position;
	}
	
	/**
	 * Returns whether or not the tile contains a briefcase.
	 */
	public boolean isBriefcase ()
	{
		return isBriefcase;
	}
	
	/**
	 * Sets the tile to contain a briefcase.
	 * @baram isBriefcase whether or not the tile will contain a briefcase.
	 */
	public void setBriefcase (boolean isBriefcase)
	{
		this.isBriefcase = isBriefcase;
	}
	
	/**
	 * Returns whether or not the tile is a room.
	 * @return returns true if tile is room
	 */
	public boolean isRoom ()
	{
		return isRoom;
	}
	
	/**
	 * Returns whether the tile contains a ninja
	 * @return returns true if this tile is a ninja
	 */
	public boolean isNinja ()
	{
		return isNinja;
	}
	
	/**
	 * Returns whether the tile is a player.
	 * @return returns true if the tile is a player.
	 */
	public boolean isPlayer ()
	{
		return isPlayer;
	}
	
	/**
	 * Returns whether the tile contains a power up.
	 * @return returns true if tile contains a power up
	 */
	public boolean isPowerUp ()
	{
		return isPowerUp;
	}
	/**
	 * Returns whether of not the tile can be seen by the player.
	 * @return reuturns true if the tile is not visible to player.
	 */
	public boolean isUnknownToPlayer ()
	{
		return isUnknownToPlayer;
	}
	
	/**
	 * Sets whether the tile will contain a ninja.
	 * @param bool whether this tile has a ninja or not.
	 */
	public void setIsNinja (boolean bool)
	{
		this.isNinja = bool;
	}
	
	/**
	 * Sets whether the tile will contain a power up.
	 * @param bool whether this tile has a power up or not.
	 */
	public void setIsPowerUp (boolean bool)
	{
		this.isPowerUp = bool;
	}
	
	/**
	 * Gets the power up on the tile
	 * @return returnsthe power up on the tile
	 */
	public PowerUp getPowerUp ()
	{
		return this.powerUp;
	}
	
	/**
	 * Set the power up type on the tile.
	 */
	public void setPowerUp (PowerUp pwr)
	{
		this.powerUp = pwr;
	}
	
	/**
	 * Set the tile visibility for the player
	 * @param bool whether the player can see the tile or not.
	 */
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
	
	/**@param position- a point object with an x and y postition
	 * Sets position of tile, no return
 	*/
	public void setPosition (Point position)
	{
		this.position = position;
	}

	/**
	 * @param player player object
	 * @return Returns the string representation of what the player can see on a tile, 
	 * differentiating from room, briefcase, unknown, player or dead ninja. 
	 */
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
