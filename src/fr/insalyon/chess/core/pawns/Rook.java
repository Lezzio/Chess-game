package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class Rook extends AbstractPawn {

	public Rook(Team team) {
		super.name = "rook";
		super.team = team;
	}
	public Rook(Team team, Location location) {
		super.name = "rook";
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		return null;
	}

}
