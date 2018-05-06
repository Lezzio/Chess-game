package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class Rook extends AbstractPawn {

	private final String NAME = "rook";
	
	public Rook(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public Rook(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		Location location1 = null;
		switch(this.team) {
		case White: 
			location1 = new Location(location.getRow() - 1, location.getCol());
			break;
		case Black:
			location1 = new Location(location.getRow() + 1, location.getCol());
			break;
		}
		Location[] locs = {location1};
		return locs;
	}

}
