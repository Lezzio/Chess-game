package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
import fr.insalyon.chess.core.Team;

public class Knight extends AbstractPawn {

	private final String NAME = "knight";
	
	public Knight(Team team) {
		this(team, null);
	}
	public Knight(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
		super.value = 3;
	}
	
	@Override
	public Location[] getMovement(Game game, Location location, boolean check) {
		MovementBuilder movementBuilder = new MovementBuilder(game, check);
		movementBuilder.setCollide(true);
		movementBuilder.setTeam(this.team);
		movementBuilder.add(MovementType.SINGLE, location, location.add(2, 1));
		movementBuilder.add(MovementType.SINGLE, location, location.add(2, -1));
		movementBuilder.add(MovementType.SINGLE, location, location.add(-2, 1));
		movementBuilder.add(MovementType.SINGLE, location, location.add(-2, -1));
		movementBuilder.add(MovementType.SINGLE, location, location.add(1, 2));
		movementBuilder.add(MovementType.SINGLE, location, location.add(-1, 2));
		movementBuilder.add(MovementType.SINGLE, location, location.add(1, -2));
		movementBuilder.add(MovementType.SINGLE, location, location.add(-1, -2));
		
		return movementBuilder.build();
	}

}
