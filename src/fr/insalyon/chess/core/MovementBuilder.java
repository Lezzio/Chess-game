package fr.insalyon.chess.core;

import fr.insalyon.chess.Game;

public class MovementBuilder {
	
	private Team team;
	private Location[] locations;
	private AbstractPawn[][] board;
	private boolean collide;

	public MovementBuilder(AbstractPawn[][] board) {
		this.board = board;
		this.locations = new Location[0];
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	public void add(MovementType movementType, Location to) {
		add(movementType, null, to);
	}
	public void add(MovementType movementType, Location from, Location to) {
		
		//If used locations are outside don't pursue
		if((from != null && !from.isInside(board)) || !to.isInside(board)) {
			return;
		}
		
		switch(movementType) {
		case SINGLE:
			if(collide) {
				locations = Location.addLocation(locations, to);
			} else if(Game.isEmpty(board, to)) {
				locations = Location.addLocation(locations, to);
			}
			break;
		case LINE:
			break;
		case DIAGONAL:
			break;
		}
	}
	public void add(Location location) {
		locations = Location.addLocation(locations, location);
	}
	public Location[] build() {
		return locations;
	}
			
}
