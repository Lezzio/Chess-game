package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
import fr.insalyon.chess.core.Team;

public class Knight extends AbstractPawn {

	private final String NAME = "knight";
	
	public Knight(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public Knight(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		MovementBuilder movementBuilder = new MovementBuilder(board);
		movementBuilder.setCollide(true);
		movementBuilder.setTeam(this.team);
		movementBuilder.add(MovementType.SINGLE, location.add(2, 1));
		movementBuilder.add(MovementType.SINGLE, location.add(2, -1));
		movementBuilder.add(MovementType.SINGLE, location.add(-2, 1));
		movementBuilder.add(MovementType.SINGLE, location.add(-2, -1));
		movementBuilder.add(MovementType.SINGLE, location.add(1, 2));
		movementBuilder.add(MovementType.SINGLE, location.add(-1, 2));
		movementBuilder.add(MovementType.SINGLE, location.add(1, -2));
		movementBuilder.add(MovementType.SINGLE, location.add(-1, -2));
		
		return movementBuilder.build();
	}

}
