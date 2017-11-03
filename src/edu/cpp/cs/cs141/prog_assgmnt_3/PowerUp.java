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

public class PowerUp
{
    private final int totalEnhancementTypes = 3;
    private Grid.entityType myType;
    private enhancementType T;

    private Point position;

    PowerUp()
    {
        this.myType = Grid.entityType.POWERUP;
        generateEnhancement();
        this.position = new Point();

        setPowerUpPosition();
    }

    private void generateEnhancement()
    {
        int num = GameEngine.generateRandNum(this.totalEnhancementTypes);

        switch (num)
        {
            case 1:
            {
                this.T = enhancementType.BULLET;
                TextUserInterface.printGameString("Randomly Generated: BULLET PowerUp!", true);
                break;
            }
            case 2:
            {
                this.T = enhancementType.RADAR;
                TextUserInterface.printGameString("Randomly Generated: RADAR PowerUp!", true);

                break;
            }
            case 3:
            {
                this.T = enhancementType.INVINCIBILITY;
                TextUserInterface.printGameString("Randomly Generated: Invincibility PowerUp!", true);

                break;
            }
            default:
            {
                TextUserInterface.printGameString("Error: Number generator in PowerUp.generateEnhancement() " +
                        "generated a number out of scope!", true);
            }
        }
    }

    private void setPowerUpPosition()
    {
        Point generated = new Point();

        while (true)
        {
            // 0 - 8
            generated.x = GameEngine.generateRandNum(9) - 1;
            generated.y = GameEngine.generateRandNum(9) - 1;

            //** Powerups can be anywhere. */
            if (GameEngine.isValidPoint(generated))
                break;
        }

        this.position = generated;
    }

    public enhancementType getEnhancement()
    {
        return T;
    }

    public Point getPosition()
    {
        return position;
    }

    public enum enhancementType
    {
        BULLET, RADAR, INVINCIBILITY
    }
}
