package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class Queen extends AbstractPawn {

	private final String NAME = "queen";
	
	public Queen(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public Queen(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		Location[] locs = new Location[0];
		
		return locs;
	}

}
