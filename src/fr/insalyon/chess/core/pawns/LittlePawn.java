package fr.insalyon.chess.core.pawns;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.MovementBuilder;
import fr.insalyon.chess.core.MovementType;
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
		
		MovementBuilder movementBuilder = new MovementBuilder(board);
		movementBuilder.setCollide(false);
		movementBuilder.setTeam(this.team);
		
		int a = 0;
		switch(this.team) {
		case White:
			a = -1;
			break;
		case Black:
			a = 1;
			break;
		}
		
		//Location one cell ahead
		System.out.println(location.add(a, 0));
		movementBuilder.add(MovementType.SINGLE, location.add(a, 0));
		//Location two cells ahead if initial position
		if(location.getRow() == 6 || location.getRow() == 1) {
			movementBuilder.add(MovementType.SINGLE, location.add(2 * a, 0));
		}
		
		Location wDiagonal1 = location.add(a, 1);
		if(!Game.isEmpty(board, wDiagonal1) && board[wDiagonal1.getRow()][wDiagonal1.getCol()].getTeam() != this.team) {
			movementBuilder.add(wDiagonal1);
		}
		Location wDiagonal2 = location.add(a, -1);
		if(!Game.isEmpty(board, wDiagonal2) && board[wDiagonal2.getRow()][wDiagonal2.getCol()].getTeam() != this.team) {
			movementBuilder.add(wDiagonal2);
			
		}
		
		return movementBuilder.build();
	}

}
