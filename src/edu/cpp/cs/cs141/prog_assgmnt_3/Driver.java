package testpack;

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

public class Driver
{
	public static void main (String args[])
	{
		GameEngine GE = new GameEngine(false, 6, 0, 2);
		// Test Code Below

		GE.start();
	}
}
