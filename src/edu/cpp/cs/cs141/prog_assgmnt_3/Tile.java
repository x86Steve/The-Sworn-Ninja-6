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

package edu.cpp.cs.cs141.prog_assgmnt_3;

public class Tile {
	//private Object inTile[];
	private Enemy ninjas[];
	private boolean hasPlayer;
	private boolean isRoom;
	private boolean hasBriefcase;
	private boolean canAccessRoom;
	private PowerUp powerUp;
	//private Postion accisibleRooms[]
	private int final maxEnemy = 6;
	
	public Tile(){
		ninjas = new Enemy[6];
	}

	public Tile(PowerUp hasPU){
		powerUp = hasPU;
		
	}
	
	public Tile(boolean isRoom, boolean hasCase, boolean canAccess){
		isRoom = true;
		hasBriefcase = hasCase;
		canAccessRoom = canAccess;
	}
	
	
	
	public boolean containsBriefcase(){
		return hasBriefcase;
	}
	
	public void setCase(){
		hasBriefcase = true;
	}
	
	public void setEnemy(Enemy enemy){
		for(int i = 0; i <= ninjas.length(); i++){
			if(ninjas[i] = null){
				ninjas[i] = enemy;
			}
			
		}
	}
	public void killEnemy(){
		for(int i= 0; i <= ninjas.length(); i++){
			if(ninjas[i] != null){
				ninjas[i]= null;
				break;
			}
		}
	}
	
	public Enemy[] getEnemy(){
		return ninjas;
	}
	
	public void setPlayer(boolean player){
		hasPlayer = player;
	}
	
	public void getPlayer(){
		return hasPlayer;
	}
	
	
}
