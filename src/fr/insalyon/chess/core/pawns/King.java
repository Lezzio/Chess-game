package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
import fr.insalyon.chess.core.Team;

public class King extends AbstractPawn {

	private final String NAME = "king";
	
	public King(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public King(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		MovementBuilder movementBuilder = new MovementBuilder(board);
		movementBuilder.setCollide(true);
		movementBuilder.setTeam(this.team);
		movementBuilder.add(MovementType.SINGLE, location.add(1, 0));
		movementBuilder.add(MovementType.SINGLE, location.add(1, 1));
		movementBuilder.add(MovementType.SINGLE, location.add(1, -1));
		movementBuilder.add(MovementType.SINGLE, location.add(0, 1));
		movementBuilder.add(MovementType.SINGLE, location.add(0, -1));
		movementBuilder.add(MovementType.SINGLE, location.add(-1, 0));
		movementBuilder.add(MovementType.SINGLE, location.add(-1, 1));
		movementBuilder.add(MovementType.SINGLE, location.add(-1, -1));
		
		return movementBuilder.build();
	}

}
