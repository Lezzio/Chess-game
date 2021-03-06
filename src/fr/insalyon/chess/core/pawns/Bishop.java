package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
import fr.insalyon.chess.core.Team;

public class Bishop extends AbstractPawn {

	private final String NAME = "bishop";
	
	public Bishop(Team team) {
		this(team, null);
	}
	public Bishop(Team team, Location location) {
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

		final int upperRow = location.getRow();
		final int lowerRow = 7 - location.getRow();
		final int rightCol = 7 - location.getCol();
		final int leftCol = location.getCol();
		
		//Upper right diagonal
		Location upperRightDiagonal = upperRow >= rightCol ? location.add(-rightCol, rightCol) : location.add(-upperRow, upperRow);
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, upperRightDiagonal);

		//Upper left diagonal
		Location upperLeftDiagonal = upperRow >= leftCol ? location.add(-leftCol, -leftCol) : location.add(-upperRow, -upperRow);
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, upperLeftDiagonal);

		//Lower right diagonal
		Location lowerRightDiagonal = lowerRow >= rightCol ? location.add(rightCol, rightCol) : location.add(lowerRow, lowerRow);
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, lowerRightDiagonal);

		//Lower left diagonal
		Location lowerLeftDiagonal = lowerRow >= leftCol ? location.add(leftCol, -leftCol) : location.add(lowerRow, -lowerRow);
		movementBuilder.add(MovementType.LINE_OR_DIAGONAL, location, lowerLeftDiagonal);
		
		return movementBuilder.build();
	}
	
}
