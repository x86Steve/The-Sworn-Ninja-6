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

public class Grid
{
    public enum entityType {PLAYER, NINJA, POWERUP, ROOM, BRIEFCASE}

    private String[][] grid;
    private Point Briefcase;


    /**
     * Initialize the grid, I would like to pass all the objects into it, so that it can call functions to write positions.
     */
    public Grid(Enemy[] Ninja, PowerUp[] PowerUps)
    {
       // TextUserInterface.printGameString("Grid constructed with Enemy Count: " + enemyCount + " PowerUpCount: " + powerUpCount, true);
        this.grid = new String [9][9];
        this.initGrid();

        generatePointsEnemy(Ninja);
        insertNinja(Ninja);
    }

    private void generatePointsEnemy(Enemy[] Ninja)
    {
        int size = Ninja.length;

        for (int x = 0, j = 2; x < size; x++, j++)
            Ninja[x].setPosition(new Point(x,j));
    }

    private void generatePointPowerUps(PowerUp[] PowerUps)
    {

    }

    private void insertPowerUp(PowerUp[] PowerUps)
    {
        int size = PowerUps.length;

        for (int x = 0; x < size; x++)
            // todo change the "P" to be in accordance to if it's a bullet / radar / etc
            this.grid[PowerUps[x].getPoint().x][PowerUps[x].getPoint().y] = "P";
    }

    private void insertNinja(Enemy[] Ninja)
    {
        int size = Ninja.length;

        for (int x = 0, j = 2; x < size; x++, j++)
            this.grid[Ninja[x].getPosition().x][Ninja[x].getPosition().y] = "[ NJA ]";
    }

    /**
     * Fills grid with Empty spaces
     * //Todo Function to generate the enemies into the grid, power ups, and briefcase location
     */
    private void initGrid()
    {
        int size = this.grid.length;
        for (int x = 0; x < size; x++)
            for(int j = 0; j < size; j++)
                this.grid[x][j] = "[ *** ]";
    }

    /**
     *
     * @return Grabs the string representation of the grid at it's current state.
     */
    public String[][] getGridString ()
    {
        return grid;
    }
}
