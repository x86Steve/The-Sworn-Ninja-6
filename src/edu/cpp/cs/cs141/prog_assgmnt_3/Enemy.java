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

public class Enemy
{
    private Grid.entityType myType;
    private Point position;
    private boolean isAlive;

    Enemy()
    {
        this.myType = Grid.entityType.NINJA;
        this.position = new Point();
    }

    private void generatePosition()
    {

    }

    private boolean killPlayer()
    {
        //Check adjacent squares, if player is there, game over IF no invincibility , else invincibility - 1
        return false;
    }

    public void moveUnit()
    {
        // Moves the ninja in random direction if AI disabled.
        /* If AI Enabled:
        Check if player is in sight within entire column ( up and down ) or row ( left and right )
        if so, travel towards player, else randomize movement.
         */
    }

    public boolean getIsAlive()
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
}
