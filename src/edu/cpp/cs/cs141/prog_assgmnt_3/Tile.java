package testpack;

import java.awt.*;
import java.io.Serializable;

/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodriguez
 *
 * Programming Assignment #3 (Final)
 *
 * Sworn Ninja 6
 *
 * Team Red
 *
 *  Kevin @kscroggs  - kscrogginsjr@gmail.com
 *  Albert @alberthwang  - albert.is.hwang@gmail.com
 *  Steve @Steve | C0deFlex  - scoseguera@cpp.edu
 *  Jimmy @jimmy  - jdojimmy@gmail.com
 *  Min @minimineral  -  minoook@hotmail.com
 *  Dan @Dan L. - Djluoma@cpp.edu
 */

public class Tile implements Serializable
{
    public enum entity { PLAYER, NINJA, POWERUP, ROOM, BRIEFCASE, UNKNOWN, EMPTY}

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

    private final String Player = "[ PLR ]";
    private final String Ninja = "[ >:D ]";
    private final String PowerUp = "[ PWR ]";
    private final String Room = "[ RMM ]";
    private final String Briefcase = "[ BRF ]";
    private final String Unknown = "[ *** ]";
    private final String Empty = "[     ]";

    private String tileStringRepresentation;

    Tile(Point location)
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
    public boolean isUnknownToPlayer()
    {
        return isUnknownToPlayer;
    }

    /**
     * A ninja can move into a square if it's empty, OR a powerup resides.
     * @return True - A ninja movement is possible. False - Otherwise. Wall, other ninja, etc.
     */
    public boolean isAvailableForNinja()
    {
        return this.isPowerUp || this.isEmpty;
    }

    public void setTileType(entity Type)
    {
        this.tileType = Type;

        switch (Type)
        {
            case NINJA:
            {
                this.isNinja = true;
                this.tileStringRepresentation = this.Ninja;
                break;
            }
            case PLAYER:
            {
                this.isPlayer = true;
                this.isUnknownToPlayer = false;
                this.tileStringRepresentation = this.Player;
                break;
            }
            case POWERUP:
            {
                this.isPowerUp = true;
                this.tileStringRepresentation = this.PowerUp;
                break;
            }
            case ROOM:
            {
                this.isRoom = true;
                this.isRoomEnterable = false;
                this.isUnknownToPlayer = false;
                this.tileStringRepresentation = this.Room;
                break;
            }
            case EMPTY:
            {
                this.isEmpty = true;
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
        // If a ninja resides here, the powerup will "hide" that the ninja is there.
        // So, this.isNinja should be true as well as isPowerUp
        if (!GameEngine.debugMode)
        {
            if (this.isUnknownToPlayer)
                return this.Unknown;

            return this.tileStringRepresentation;
        }


        return this.tileStringRepresentation;
    }
}
