package edu.cpp.cs.cs141.prog_assgmnt_3;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

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

public class GameEngine
{

    public static Point[] invalidPoints;
    public static boolean debugMode;
    public static Scanner scanner;
    private static Random rand;
    private Enemy[] Ninja;
    private PowerUp[] PowerUps;
    private Player Player1;
    // We can dynamically assign the amount of powerups and enemies. Hardcoding is bad, m'kay. - Steve
    private int enemySpawn;
    private int powerUpSpawn;
    private Grid gameGrid;
    private TextUserInterface UI;
    /**
     * Initilize game engine with Enemies spawned, and debug mode.
     *
     * @param debugMode
     * @param enemySpawn
     * @param powerUpSpawn
     */
    GameEngine(boolean debugMode, int enemySpawn, int powerUpSpawn)
    {
        GameEngine.debugMode = debugMode;
        scanner = new Scanner(System.in);
        this.invalidPoints = new Point[10];
        setInvalidPoints();

        TextUserInterface.printGameString("DebugMode enabled!", true);

        this.enemySpawn = enemySpawn;
        this.powerUpSpawn = powerUpSpawn;
        rand = new Random();
        this.Player1 = new Player();
        this.UI = new TextUserInterface();


        this.PowerUps = new PowerUp[this.powerUpSpawn];
        initPowerUps(this.PowerUps);

        this.Ninja = new Enemy[enemySpawn];
        initNinjas(this.Ninja);

        this.gameGrid = new Grid(this.Ninja, this.PowerUps, this.Player1);
    }

    /**
     * Generate a number between 1 and bound inclusive
     *
     * @param bound the maximum value a random number can be output
     * @return number generated
     */
    public static int generateRandNum(int bound)
    {
        int num = rand.nextInt(bound) + 1;
        TextUserInterface.printGameString("Random Number Generated: " + num, true);
        return num;
    }

    /**
     * Main routine that starts the game.
     */
    public void start()
    {
        // Display retarded text, maybe show game controls etc, haven't decided. - Steve
        this.UI.welcomeScreen();

        // Game plays until either briefcase is found, or player dies
        while (!Player1.isHasBriefcase() && Player1.isPlayerAlive())
        {
            // Draws out the current grid, you know, like, with all the enemies hidden, etc.
            UI.drawGrid(this.gameGrid);

            // Runs the game loop where the player is constantly asked for next actions
            gameLoop();

        }
    }

    private void gameLoop()
    {
        //Grab user choice, and execute that command.
        switch (UI.getPlayerOption())
        {
            case MOVEMENT:
            {
                break;
            }
            case LOOK:
            {
                break;
            }
            case SHOOT:
            {
                break;
            }
            case SAVE:
            {
                saveGame();
                break;
            }
            case LOAD:
            {
                loadGame();
                break;
            }
            case NULL:
            {
                TextUserInterface.printGameString("If we are seeing this, something in the switch statement of " +
                        "GameEngine.gameLoop() is returning NULL.", true);
                break;
            }
            default:
                break;
        }
    }

    public void saveGame()
    {
        //Saves all items into binary form in a file
    }

    public void loadGame()
    {
        //Loads all items from a binary file and edits all variables inside game engine.
    }

    /**
     * Generate position for a powerup. Keeping in mind we cannot place the powerup where Ninjas currently reside, where
     * the player spawns [8,0], a building is (static position, haven't drawn it out yet), or where another powerup resides.
     */
    private void generatePosition()
    {

        // this.position.x = randNum
        // this.position.y = randNum
    }

    /**
     * Since I don't know how to use an Array Param Constructor with the Constructor as the array, here's a hacky way.
     *
     * @param PU
     */
    private void initPowerUps(PowerUp[] PU)
    {
        int size = PU.length;

        for (int x = 0; x < size; x++)
            PU[x] = new PowerUp();
    }

    /**
     * Since I don't know how to use an Array Param Constructor with the Constructor as the array, here's a hacky way.
     *
     * @param EN
     */
    private void initNinjas(Enemy[] EN)
    {
        int size = EN.length;

        for (int x = 0; x < size; x++)
            EN[x] = new Enemy();
    }

    public enum Action
    {
        MOVEMENT, SHOOT, LOOK, SAVE, LOAD, NULL
    }

    //** Sets all the room locations so that the ninja won't spawn here. Todo Do this better */
    private void setInvalidPoints()
    {
        invalidPoints[0] = new Point(1,1);
        invalidPoints[1] = new Point(4,1);
        invalidPoints[2] = new Point(7,1);
        invalidPoints[3] = new Point(1,4);
        invalidPoints[4] = new Point(4,4);
        invalidPoints[5] = new Point(7,4);
        invalidPoints[6] = new Point(1,7);
        invalidPoints[7] = new Point(4,7);
        invalidPoints[8] = new Point(7,7);
        invalidPoints[9] = new Point(0,8);

    }

    public static boolean isValidPoint(Point point)
    {
        for (int x = 0; x < 10; x++)
            if (point.x == GameEngine.invalidPoints[x].x && point.y == GameEngine.invalidPoints[x].y)
                return false;
        return true;
    }

}
