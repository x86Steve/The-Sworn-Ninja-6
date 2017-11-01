package edu.cpp.cs.cs141.prog_assgmnt_3;
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

    private int enemyCount;
    private int powerUpCount;
    private String[][] grid;


    public Grid(int enemyCount, int powerUpCount)
    {
        TextUserInterface.printGameString("Grid constructed with Enemy Count: " + enemyCount + " PowerUpCount: " + powerUpCount, true);
        this.enemyCount = enemyCount;
        this.powerUpCount = powerUpCount;
        this.grid = new String [9][9];
        this.initGrid();
    }

    private void insertPowerUp()
    {

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
                this.grid[x][j] = "[ * ]";
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
