package fr.insalyon.chess.core;

import fr.insalyon.chess.Game;

public class MovementBuilder {

	private Game game;
	private Team team;
	private Location[] locations;
	private AbstractPawn[][] board;
	private boolean collide;
	private boolean check;

	public MovementBuilder(Game game, boolean check) {
		this.game = game;
		this.board = game.getBoard();
		this.locations = new Location[0];
		this.check = check;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	public void add(MovementType movementType, Location from, Location to) {
		
		//If used locations are outside don't pursue
		if((from != null && !from.isInside(board)) || !to.isInside(board)) {
			return;
		}
		
		switch(movementType) {
		case SINGLE:
			if(Game.isEmpty(board, to)) {
				if(allowedMovement(from, to)) locations = Location.addLocation(locations, to);
			} else if(collide && game.getPawnByLocation(to).getTeam() != this.team) {
				if(allowedMovement(from, to)) locations = Location.addLocation(locations, to);
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
				//Check collision and if allowed or not ? (for little pawn purpose)
				if(collide) {
					noCollision = Game.isEmpty(board, newLocation);
				}
				if(noCollision) {
					if(allowedMovement(from, newLocation)) locations = Location.addLocation(locations, newLocation);
				} else if(game.getPawnByLocation(newLocation).getTeam() != this.team) {
					if(allowedMovement(from, newLocation)) locations = Location.addLocation(locations, newLocation);
				}
			}
			break;
		}
	}
	private boolean allowedMovement(Location from, Location to) {
		if(check) {
		AbstractPawn movedPawn = game.getPawnByLocation(from);
		//Simulate movement
		AbstractPawn buffer = game.getPawnByLocation(to);
		game.movePawn(from, to);
		boolean allowed = !game.check(movedPawn.getTeam()); //Check check condition
		game.movePawn(to, from);
		board[to.getRow()][to.getCol()] = buffer;
		
		return allowed;
		} else {
			return true;
		}
	}
	public void add(Location from, Location to) {
		if(allowedMovement(from, to)) {
			locations = Location.addLocation(locations, to);
		}
	}
	public Location[] build() {
		return locations;
	}
			
}
