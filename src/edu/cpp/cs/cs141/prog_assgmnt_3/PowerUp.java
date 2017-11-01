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

public class PowerUp
{
    public enum enhancementType {BULLET, RADAR, INVINCIBILITY}

    private final int totalEnhancementTypes = 3;

    private Grid.entityType myType;
    private enhancementType T;
    private Point position;

    PowerUp()
    {
        this.myType = Grid.entityType.POWERUP;
        generateEnhancement();
    }

    private void generateEnhancement()
    {
        int num = GameEngine.generateRandNum(this.totalEnhancementTypes);

        switch (num)
        {
            case 1:
            {
                this.T = enhancementType.BULLET;
                TextUserInterface.printGameString("Randomly Generated: BULLET PowerUp!",true);
                break;
            }
            case 2:
            {
                this.T = enhancementType.RADAR;
                TextUserInterface.printGameString("Randomly Generated: RADAR PowerUp!",true);

                break;
            }
            case 3:
            {
                this.T = enhancementType.INVINCIBILITY;
                TextUserInterface.printGameString("Randomly Generated: Invincibility PowerUp!",true);

                break;
            }
            default:
            {
                TextUserInterface.printGameString("Error: Number generator in PowerUp.generateEnhancement() " +
                "generated a number out of scope!",true);
            }
        }
    }

    public enhancementType getEnhancement()
    {
        return T;
    }

    public Point getPoint ()
    {
        return position;
    }
}
