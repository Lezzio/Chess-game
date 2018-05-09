package fr.insalyon.chess.core;

import fr.insalyon.chess.Game;

public abstract class AbstractPawn {

	protected Team team;
	protected Location location;
	protected String name;
	protected int value;
	
	/**
	 * Get the current location of the pawn
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * Set a new location for the pawn
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * Get the name of the pawn
	 */
	public String getName() {
		return name;
	}
	/**
	 * Get the team of the pawn
	 */
	public Team getTeam() {
		return team;
	}
	/**
	 * Get the value of the pawn
	 */
	public int getValue() {
		return value;
	}
	/**
	 * Get the locations to which the pawn can move from the given location
	 */
	public abstract Location[] getMovement(Game game, Location location, boolean check);

}
