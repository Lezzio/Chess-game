package fr.insalyon.chess.core;

public abstract class AbstractPawn {

	private Location location;
	
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
	 * Get the locations to which the pawn can move from the given location
	 */
	public abstract Location[] getMovement(Location location);

}
