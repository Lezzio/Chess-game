package fr.insalyon.chess.ai;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class ChessAI2 {

	private Location[] allTargetedLocations = new Location[0];
	private int pieces = 0;
	private int enemyPieces = 0;
	
	/*
	 * ATTENTION
	 * 
	 * Ancienne version : regarder ChessAI3
	 * 
	 */
	
	private void setupVariables(Game game, Team team) {
		pieces = 0;
		enemyPieces = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = game.getBoard()[i][j];
				if(pawn == null) continue;
				pieces++;
				if(pawn.getTeam() != team) {
					Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
					allTargetedLocations = Location.concat(allTargetedLocations, targets);
					enemyPieces++;
				}
			}
		}
	}
	
	/*
	 * Return an integer giving a perspective of the current board for a given team
	 */
	public int evalState(AbstractPawn[][] board, Team team) {
		int value = 0;

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn != null) {
					if(pawn.getTeam() == team) {
						value += pawn.getValue();
					} else {
						value -= pawn.getValue();
					}
				}
			}
		}
		
		return value;
	}
	
	//Min max tree implementation
	public void play(Game game, Team team) {
		
		AbstractPawn[][] board = game.getBoard();

		int initialValue = evalState(board, Team.BLACK);
		int bestValue = -10000;
		Location bestTo = null;
		AbstractPawn bestPawn = null;
		
		setupVariables(game, team);
		int depth = 3;
		System.out.println(pieces);
		if(enemyPieces <= 3) {
			depth = 3;
		} else if(enemyPieces == 1) {
			depth = 4;
		}
		
		System.out.println("INITIAL STATE " + initialValue);
		
		int a = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
					AbstractPawn pawn = board[i][j];
					if(pawn == null) continue;
					if(pawn.getTeam() != team) continue;
					Location[] locs = pawn.getMovement(game, pawn.getLocation(), true);
					if(locs.length == 0) continue;
					//Iterate all possible moves
					for(int k = 0; k < locs.length; k++) {
						Location targetedLoc = locs[k];
					
						//Simulate movement
						AbstractPawn buffer = game.getPawnByLocation(targetedLoc);
						Location from = pawn.getLocation();
						game.movePawn(from, targetedLoc);

						game.rotatePlayer();
//						System.out.println("Start min max for " + pawn.getName() + " to " + targetedLoc);
						//Recursive tree
						int value = max(game, game.getCurrentPlayer(), depth);
//						System.out.println("MIN VALUE FOR STRIKE " + (a++) + " is " + value);
						
						//Is it the best one ?
						if(value >= bestValue) {
							bestValue = value;
							bestTo = targetedLoc;
							bestPawn = pawn;
						}
						
						//Undo simulation
						game.movePawn(targetedLoc, from);
						board[targetedLoc.getRow()][targetedLoc.getCol()] = buffer;
						game.rotatePlayer();
						
				}
			}
		}
		if(bestTo != null && bestPawn != null) {
		System.out.println("Best move " + bestPawn.getName() + " to " + bestTo + " with score : " + bestValue);
		game.movePawn(bestPawn.getLocation(), bestTo);
		System.out.println("__________________________");
		}
	}

	private int min(Game game, Team team, int depth) {

		int minValue = Integer.MAX_VALUE;
		AbstractPawn[][] board = game.getBoard();

		if(depth == 0) {
			return evalState(board, Team.BLACK);
		}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
					AbstractPawn pawn = board[i][j];
					if(pawn == null) continue;
					if(pawn.getTeam() != team) continue;
					Location[] locs = pawn.getMovement(game, pawn.getLocation(), true);
					if(locs.length == 0) continue;
					//Iterate all possible moves
					for(int k = 0; k < locs.length; k++) {
						Location targetedLoc = locs[k];
					
						//Simulate movement
						AbstractPawn buffer = game.getPawnByLocation(targetedLoc);
						Location from = pawn.getLocation();
						game.movePawn(from, targetedLoc);
						
						game.rotatePlayer();
						//Recursive tree
						int value = max(game, game.getCurrentPlayer(), (depth - 1));
						
						if(value <= minValue) {
							minValue = value;
						}
						
						//Undo simulation
						game.movePawn(targetedLoc, from);
						board[targetedLoc.getRow()][targetedLoc.getCol()] = buffer;
						game.rotatePlayer();
						
						
				}
			}
		}
		return minValue;
	}

	private int max(Game game, Team team, int depth) {

		int maxValue = Integer.MIN_VALUE;
		AbstractPawn[][] board = game.getBoard();

		if(depth == 0) {
			return evalState(board, Team.BLACK);
		}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
					AbstractPawn pawn = board[i][j];
					if(pawn == null) continue;
					if(pawn.getTeam() != team) continue;
					Location[] locs = pawn.getMovement(game, pawn.getLocation(), true);
					if(locs.length == 0) continue;
					//Iterate all possible moves
					for(int k = 0; k < locs.length; k++) {
						Location targetedLoc = locs[k];
					
						//Simulate movement
						AbstractPawn buffer = game.getPawnByLocation(targetedLoc);
						Location from = pawn.getLocation();
						game.movePawn(from, targetedLoc);
						
						game.rotatePlayer();
						//Recursive tree
						int value = min(game, game.getCurrentPlayer(), (depth - 1));

						if(value >= maxValue) {
							maxValue = value;
						}
						
						//Undo simulation
						game.movePawn(targetedLoc, from);
						board[targetedLoc.getRow()][targetedLoc.getCol()] = buffer;
						game.rotatePlayer();
				}
			}
		}
		return maxValue;
	}

}
