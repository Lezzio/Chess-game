package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class LittlePawn extends AbstractPawn {

	private final String NAME = "pawn";
	
	public LittlePawn(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public LittlePawn(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		Location[] locs = new Location[0];
		double a = 0.0;
		switch(this.team) {
		case White:
			a = -1.0;
			//+ Black enemy in top diagonal
			Location wDiagonal1 = location.add(-1, 1);
			if(!Game.isEmpty(board, wDiagonal1) && board[wDiagonal1.getRow()][wDiagonal1.getCol()].getTeam() != Team.White) {
				locs = Location.addLocation(locs, wDiagonal1);
			}
			Location wDiagonal2 = location.add(-1, -1);
			if(!Game.isEmpty(board, wDiagonal2) && board[wDiagonal2.getRow()][wDiagonal2.getCol()].getTeam() != Team.White) {
				locs = Location.addLocation(locs, wDiagonal2);
				
			}
			break;
		case Black:
			a = 1.0;
			//+ White enemy in bot diagonal
			Location bDiagonal1 = location.add(1, 1);
			if(!Game.isEmpty(board, bDiagonal1) && board[bDiagonal1.getRow()][bDiagonal1.getCol()].getTeam() != Team.Black) {
				locs = Location.addLocation(locs, bDiagonal1);
			}
			Location bDiagonal2 = location.add(1, -1);
			if(!Game.isEmpty(board, bDiagonal2) && board[bDiagonal2.getRow()][bDiagonal2.getCol()].getTeam() != Team.Black) {
				locs = Location.addLocation(locs, bDiagonal2);
				
			}
			break;
		}

		//Location one cell ahead
		Location location1 = new Location((int) (location.getRow() + a), location.getCol());
		if(Game.isEmpty(board, location1)) {
			locs = Location.addLocation(locs, location1);
		}
		//Location two cells ahead if initial position
		if(location.getRow() == 6 || location.getRow() == 1) {
			Location location2 = new Location((int) (location.getRow() + (2.0 * a)), location.getCol());
			if(Game.isEmpty(board, location2)) {
				locs = Location.addLocation(locs, location2);
			}
		}
		return locs;
	}

}
