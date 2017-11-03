package edu.cpp.cs.cs141.prog_assgmnt_3;

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

public class Enemy
{
    private Grid.entityType myType;
    private Point position;
    private boolean isAlive;

    Enemy()
    {
        this.myType = Grid.entityType.NINJA;
        this.position = new Point(0,0);

        setNinjaPosition();
    }

    private void setNinjaPosition()
    {
        Point generated = new Point();

        while (true)
        {
            // 0 - 8
            generated.x = GameEngine.generateRandNum(9) - 1;
            generated.y = GameEngine.generateRandNum(9) - 1;

            // My terrible way of covering surface
            //GOOD AREA
            if (GameEngine.isValidPoint(generated))
            {
                if (generated.y < 5)
                    break;

                if (generated.y >= 5 && generated.x >= 4)
                    break;
            }

        }

        this.position = generated;
    }

    private boolean killPlayer()
    {
        //Check adjacent squares, if player is there, game over IF no invincibility , else invincibility - 1
        return false;
    }

    public void moveUnit(boolean isAI)
    {
        // Moves the ninja in random direction if AI disabled.
        /* If AI Enabled:
        Check if player is in sight within entire column ( up and down ) or row ( left and right )
        if so, travel towards player, else randomize movement.
         */

        //Todo AI
        if (isAI)
        {
            //...
        }
    }

    public boolean getIsAlive()
    {
        return this.isAlive;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }
}
