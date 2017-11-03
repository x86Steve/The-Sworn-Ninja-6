package edu.cpp.cs.cs141.prog_assgmnt_3;

import java.awt.*;

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

public class Player
{
    private Grid.entityType myType;

    private boolean isAlive;
    private boolean hasBullet;
    private boolean hasBriefcase;
    private int invincibilityCount;
    private Point position;

    Player()
    {
        this.isAlive = true;
        this.hasBullet = true;
        this.hasBriefcase = false;
        this.invincibilityCount = 0;
        this.myType = Grid.entityType.PLAYER;

        /** Starting point for player */
        this.position = new Point(0, 8);
    }

    public boolean isPlayerAlive()
    {
        return this.hasBullet;
    }

    public boolean isHasBullet()
    {
        return this.hasBullet;
    }

    public boolean isHasBriefcase()
    {
        return this.hasBriefcase;
    }

    public int getInvincibilityCount()
    {
        return this.invincibilityCount;
    }

    public Grid.entityType getMyType()
    {
        return myType;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public void move()
    {

    }
}
