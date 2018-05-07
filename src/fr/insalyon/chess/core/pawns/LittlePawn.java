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
	public Location[] getMovement(Game game, Location location, boolean check) {
		
		final AbstractPawn[][] board = game.getBoard();
		MovementBuilder movementBuilder = new MovementBuilder(game, check);
		movementBuilder.setCollide(false);
		movementBuilder.setTeam(this.team);
		
		int a = 0;
		switch(this.team) {
		case WHITE:
			a = -1;
			break;
		case BLACK:
			a = 1;
			break;
		}
		
		//Location one cell ahead
		movementBuilder.add(MovementType.SINGLE, location, location.add(a, 0));
		//Location two cells ahead if initial position
		if(location.getRow() == 6 || location.getRow() == 1) {
			movementBuilder.add(MovementType.SINGLE, location, location.add(2 * a, 0));
		}
		
		//Diagonal movements if enemy pawn
		Location wDiagonal1 = location.add(a, 1);
		if(wDiagonal1.isInside(board) && !Game.isEmpty(board, wDiagonal1) && game.getPawnByLocation(wDiagonal1).getTeam() != this.team) {
			movementBuilder.add(location, wDiagonal1);
		}
		Location wDiagonal2 = location.add(a, -1);
		if(wDiagonal2.isInside(board) && !Game.isEmpty(board, wDiagonal2) && game.getPawnByLocation(wDiagonal2).getTeam() != this.team) {
			movementBuilder.add(location, wDiagonal2);
			
		}
		
		return movementBuilder.build();
	}

}
