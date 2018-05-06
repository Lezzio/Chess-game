package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
import fr.insalyon.chess.core.Team;

public class Bishop extends AbstractPawn {

	private final String NAME = "bishop";
	
	public Bishop(Team team) {
		super.name = NAME;
		super.team = team;
	}
	public Bishop(Team team, Location location) {
		super.name = NAME;
		super.team = team;
		super.location = location;
	}
	
	@Override
	public Location[] getMovement(AbstractPawn[][] board, Location location) {
		MovementBuilder movementBuilder = new MovementBuilder(board);
		movementBuilder.setCollide(false);
		movementBuilder.setTeam(this.team);

		int upperRow = location.getRow();
		int lowerRow = 7 - location.getRow();
		int rightCol = 7 - location.getCol();
		int leftCol = location.getCol();
		
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
