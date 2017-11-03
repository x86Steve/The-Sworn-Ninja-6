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

package testpack;

import java.util.InputMismatchException;

public class TextUserInterface
{
    /*
    Error Strings
     */

    private final String incorretInput = "Yo, can we just, you know, follow directions and enter a number please," +
            " or whatever the correct input SHOULD be. These Java sanitation shiz are annoying" +
            " Thanks.";

    /**
     * Constructor
     */
    TextUserInterface()
    {
        printGameString("TextUserInterface constructed!", true);
    }

    /**
     *
     * @param s String to print, works just like System.out.println(), just shorter to type
     * @param debugString True - Appends "DEBUG" on the string, else, prints normally. debugMode will be a global within GameEngine.
     */
    public static void printGameString(Object s, boolean debugString)
    {
        if (GameEngine.debugMode && debugString)
            System.out.println("DEBUG: " + s.toString());
        else if (debugString && !GameEngine.debugMode)
            return;
        else
            System.out.println(s.toString());
    }

    /**
     * Prints out welcome screen complete with controls and story? Lol?
     */
    public void welcomeScreen()
    {
        printGameString("This will be the welcome screen!", false);
    }

    public void printPlayerCommands()
    {
        printGameString("1. Look", false);
        printGameString("2. Move", false);
        printGameString("3. Shoot", false);
        printGameString("4. Save Game", false);
        printGameString("5. Load Game", false);
    }

    /**
     * Takes grid and prints out it's current state.
     * @param grid This is the grid that was instantiated at game start. The grid class handles the states,
     *             this prints them out.
     */
    public void drawGrid(Grid grid)
    {
        int size = grid.getGridTiles().length;

        for (int x = 0; x < size; x++)
        {
            if (x > 0)
                printGameString("", false);
            for (int j = 0; j < size; j++)
                System.out.print(grid.getGridTiles()[x][j]);
        }
        // Newline
        printGameString("", false);
    }

    /**
     * This will print out the commands associated with regular gameplay.
     * @return Returns the value according to menu choices to then be used in game engine
     */
    public GameEngine.Action getPlayerOption()
    {
        //need to fix this variable.
        int playerMenuSize = 5;
        int userChoice = 0;

        printGameString("Please enter your Choice", false);

        // This needs to be redone, ugly while statement. I want the menu size to be dynamic, you know, we might add on
        // more options.
        while (userChoice == 0 || userChoice > playerMenuSize || userChoice < 0)
        {
            printPlayerCommands();

            try
            {
                userChoice = Integer.parseInt(GameEngine.scanner.nextLine());
            }
            catch (InputMismatchException e)
            {
                printGameString(incorretInput, false);
                printGameString("Java Error: " + e, true);
                userChoice = 0;
            }

            switch (userChoice)
            {
                case 1:
                    return GameEngine.Action.LOOK;
                case 2:
                    return GameEngine.Action.MOVEMENT;
                case 3:
                    return GameEngine.Action.SHOOT;
                case 4:
                    return GameEngine.Action.SAVE;
                case 5:
                    return GameEngine.Action.LOAD;
                default:
                    TextUserInterface.printGameString("Error: Enter a number within the menu!", false);
            }

        }
        return GameEngine.Action.NULL;
    }

}
