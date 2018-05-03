package testpack;

import java.awt.*;

public class PowerUp implements java.io.Serializable
{
	private final int totalEnhancementTypes = 3;
	private enhancementType T;

	private Point position;

	PowerUp ()
	{
		generateEnhancement();
		this.position = new Point();

		setPowerUpPosition();
	}
	
	/**
	 * Uses random numbers to make powerups.
	 * Amount of powerups can be changed by changing totalEnhancementTypes
	 */
	private void generateEnhancement ()
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
				TextUserInterface.printGameString("Error: Number generator in PowerUp.generateEnhancement() " + "generated a number out of scope!", true);
			}
		}
	}
	
	/**
	 * Randomly places powerups onto the grid.
	 */
	private void setPowerUpPosition ()
	{
		Point generated = new Point();

		while (true)
		{
			// 0 - 8
			generated.x = GameEngine.generateRandNum(9) - 1;
			generated.y = GameEngine.generateRandNum(9) - 1;

			//** Powerups can be anywhere. */
			if (GameEngine.isValidPoint(generated) && !GameEngine.getPointsUsedByPowerUps().contains(generated))
				break;
		}

		GameEngine.getPointsUsedByPowerUps().add(generated);
		this.position = generated;
	}

	public enhancementType getEnhancement ()
	{
		return T;
	}

	public Point getPosition ()
	{
		return position;
	}
	
	/**
	 * When the player walks onto a powerup, the game engine sends it over to this class
	 * Then this method figures out which powerup it is, then applices the enhancement.
	 */
	public void usePowerUp (Player player, TextUserInterface UI)
	{
		switch (this.T)
		{
			case INVINCIBILITY:
			{
				player.useInvincibilityPowerUp();
				UI.printUsedInvincibilityUp();
				break;
			}
			case BULLET:
			{
				player.useBulletPowerUp();
				UI.printUsedBulletPowerUp();
				break;
			}
			case RADAR:
			{
				player.useRadarPowerUp();
				UI.printUsedRadarPowerUp();
				break;
			}
		}
	}

	@Override
	public String toString ()
	{
		switch (this.T)
		{
			case RADAR:
				return "[ RAD ]";
			case BULLET:
				return "[ BLT ]";
			case INVINCIBILITY:
				return "[ INV ]";
		}

		return "NULL";
	}

	public enum enhancementType
	{
		BULLET, RADAR, INVINCIBILITY
	}
}
