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
			if(Game.isEmpty(board, to)) {
				locations = Location.addLocation(locations, to);
			} else if(collide && board[to.getRow()][to.getCol()].getTeam() != this.team) {
				locations = Location.addLocation(locations, to);
			}
			break;
		case LINE_OR_DIAGONAL:
			//To get directions : Delta row / abs delta row and Delta col / abs delta row
			int rowInc = 0;
			int colInc = 0;
			if(to.getRow() - from.getRow() != 0) {
				rowInc = (to.getRow() - from.getRow()) / Math.abs(to.getRow() - from.getRow());
			}	
			if(to.getCol() - from.getCol() != 0) {
				colInc = (to.getCol() - from.getCol()) / Math.abs(to.getCol() - from.getCol());
			}
			int row = from.getRow();
			int col = from.getCol();
			boolean noCollision = true;
			while(noCollision && ((rowInc > 0 ? row < to.getRow() : row > to.getRow()) || (colInc > 0 ? col < to.getCol() : col > to.getCol()))) {
				row += rowInc;
				col += colInc;
				Location newLocation = new Location(row, col);
				if(collide) {
					noCollision = Game.isEmpty(board, newLocation);
				}
				if(noCollision) {
					locations = Location.addLocation(locations, newLocation);
				} else if(board[newLocation.getRow()][newLocation.getCol()].getTeam() != this.team) {
					locations = Location.addLocation(locations, newLocation);
				}
			}
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
