package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
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
	public Location[] getMovement(Game game, Location location, boolean check) {
		MovementBuilder movementBuilder = new MovementBuilder(game, check);
		movementBuilder.setCollide(true);
		movementBuilder.setTeam(this.team);
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, new Location(0, location.getCol())); //TOP
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, new Location(7, location.getCol())); //BOT
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, new Location(location.getRow(), 0)); //LEFT
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, new Location(location.getRow(), 7)); //RIGHT
		
		return movementBuilder.build();
	}

}
