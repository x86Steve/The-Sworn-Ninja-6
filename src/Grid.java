import java.awt.*;

public class Grid implements java.io.Serializable
{
	private Tile[][] grid;
	private Point Briefcase;

	/**
	 * Initialize the grid, I would like to pass all the objects into it, so that it can call functions to write positions.
	 */
	public Grid (Enemy[] Ninja, PowerUp[] PowerUps, Player Player1)
	{
		TextUserInterface.printGameString("Grid constructed with Enemy Count: " + Ninja.length + " PowerUpCount: " + PowerUps.length, true);
		this.grid = new Tile[9][9];
		this.initGrid(Ninja, PowerUps, Player1);

		this.generateBriefcaseLocation();
	}

	private void insertPlayer (Point point)
	{
		this.grid[point.y][point.x].setTileType(Tile.entity.PLAYER);
	}

	private void insertPowerUp (PowerUp[] PowerUps)
	{
		int size = PowerUps.length;

		for (int x = 0; x < size; x++)
		{
			this.grid[PowerUps[x].getPosition().y][PowerUps[x].getPosition().x].setPowerUp(PowerUps[x]);
			this.grid[PowerUps[x].getPosition().y][PowerUps[x].getPosition().x].setTileType(Tile.entity.POWERUP);
		}

	}

	private void insertNinja (Enemy[] Ninja)
	{
		int size = Ninja.length;

		for (int x = 0; x < size; x++)
			this.grid[Ninja[x].getPosition().y][Ninja[x].getPosition().x].setTileType(Tile.entity.NINJA);
		// this.grid[Ninja[x].getPosition().x][Ninja[x].getPosition().y] = "[ NJA ]";
	}

	/**
	 * Fills grid with Empty spaces
	 */
	private void initGrid (Enemy[] Ninja, PowerUp[] PowerUps, Player Player1)
	{
		int size = this.grid.length;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				this.grid[y][x] = new Tile(new Point(y, x));
		// Set Rooms
		this.grid[1][1].setTileType(Tile.entity.ROOM);
		this.grid[1][4].setTileType(Tile.entity.ROOM);
		this.grid[1][7].setTileType(Tile.entity.ROOM);
		this.grid[4][1].setTileType(Tile.entity.ROOM);
		this.grid[4][4].setTileType(Tile.entity.ROOM);
		this.grid[4][7].setTileType(Tile.entity.ROOM);
		this.grid[7][1].setTileType(Tile.entity.ROOM);
		this.grid[7][4].setTileType(Tile.entity.ROOM);
		this.grid[7][7].setTileType(Tile.entity.ROOM);

		insertNinja(Ninja);
		insertPlayer(Player1.getPosition());
		insertPowerUp(PowerUps);
	}

	/**
	 * @return Grabs the string representation of the grid at it's current state.
	 */
	public Tile[][] getGridTiles ()
	{
		return grid;
	}

	public Tile getGridTile (int y, int x)
	{
		return this.grid[y][x];
	}

	private void generateBriefcaseLocation ()
	{
		Point roomLocations[] = new Point[9];

		roomLocations[0] = new Point(1, 1);
		roomLocations[1] = new Point(1, 4);
		roomLocations[2] = new Point(1, 7);
		roomLocations[3] = new Point(4, 1);
		roomLocations[4] = new Point(4, 4);
		roomLocations[5] = new Point(4, 7);
		roomLocations[6] = new Point(7, 1);
		roomLocations[7] = new Point(7, 4);
		roomLocations[8] = new Point(7, 7);

		int randomNum = GameEngine.generateRandNum(9) - 1;

		this.getGridTile(roomLocations[randomNum].y, roomLocations[randomNum].x).setBriefcase(true);
	}

	public void revealBriefcase ()
	{
		int size = this.grid.length;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				if (this.getGridTile(y, x).isBriefcase())
					this.getGridTile(y, x).setIsUnknownToPlayer(false);
	}

}
