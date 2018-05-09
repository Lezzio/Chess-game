package fr.insalyon.chess.ai;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.King;

public class ChessAI2 {
	
	public static void bestStrike(Game game, Team team) {
		
		System.out.println("Launch AI play");
		
		AbstractPawn[][] board = game.getBoard();
		
		
		//Best movement possible
		int bestValue = 0;
		AbstractPawn bestPawn = null;
		Location bestTo = null;

		//For putting in check variables
		int leastKingMovs = 8;
		int enemyPieces = 0;

		//Attack
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn == null) continue;
				
				//Playable pawn ?
				if(pawn.getTeam() == team) {
					Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
					//Evaluate the highest bounty target
					for(int k = 0; k < targets.length; k++) {
						Location targetedLoc = targets[k];
						AbstractPawn target = game.getPawnByLocation(targetedLoc);
						if(target == null) continue;
						int value = target.getValue();

						//Choose the most valuable play with the least valuable piece
						if((value > bestValue) || (bestPawn != null && value == bestValue && pawn.getValue() < bestPawn.getValue())) {
							bestValue = value;
							bestTo = targetedLoc;
							bestPawn = pawn;
						}
					}
				}
			}
		}
		//If few pieces, try to put the king with the least movements possible
		if(enemyPieces == 1) {
			System.out.println("Looking for putting in check");
			King enemyKing = game.getKing(Team.values()[(team.getId() + 1) % 2]);
			leastKingMovs = enemyKing.getMovement(game, enemyKing.getLocation(), true).length;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					AbstractPawn pawn = board[i][j];
					if(pawn == null) continue;
					
					//Playable pawn ?
					if(pawn.getTeam() == team) {
						Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
						//Evaluate target putting in the worst position the enemy king
						for(int k = 0; k < targets.length; k++) {
							Location targetedLoc = targets[k];
							AbstractPawn target = game.getPawnByLocation(targetedLoc);

							//Simulate movement
							Location from = pawn.getLocation();
							game.movePawn(from, targetedLoc);
							
							//Evaluate
							Location[] possibleMovs = enemyKing.getMovement(game, enemyKing.getLocation(), true);
							//Choose the play putting the enemy king with the fewest movs possible out of his range
							if(possibleMovs.length < leastKingMovs && !Location.locationArrayContains(enemyKing.getMovement(game, enemyKing.getLocation(), true), targetedLoc)) {
								System.out.println("NO targeted just find best mov with " + possibleMovs.length);
								bestTo = targetedLoc;
								bestPawn = pawn;
								leastKingMovs = possibleMovs.length;
							}
							//Undo simulation
							game.movePawn(targetedLoc, from);
							board[targetedLoc.getRow()][targetedLoc.getCol()] = target;
						}
					}
				}
			}
		}
		Location[] allTargetedLocations = new Location[0];
		//Defense
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn == null) continue;
				
				//Enemy pawn ?
				if(pawn.getTeam() != team) {
					//If attacked location is the enemy pawn, no need to defend against him in the evaluation
					if(bestTo != null && bestTo.equals(pawn.getLocation())) continue;
					enemyPieces++;
					Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
					allTargetedLocations = Location.concat(allTargetedLocations, targets);
					//Evaluate the highest bounty target
					for(int k = 0; k < targets.length; k++) {
						Location targetedLoc = targets[k];
						AbstractPawn target = game.getPawnByLocation(targetedLoc);
						if(target == null) continue;
						int value = target.getValue();
						System.out.println("Defensive play : " + value + " against " + bestValue);

						//Is this enemy play more valuable than the attack one ?
						if((value > bestValue) || (bestPawn != null && value == bestValue && pawn.getValue() < bestPawn.getValue())) {
							System.out.println("Condition passed");
							//Then escape
							Location[] escapeMovements = target.getMovement(game, target.getLocation(), true);
							for(int l = 0; l < escapeMovements.length; l++) {
								if(!Location.locationArrayContains(allTargetedLocations, escapeMovements[l])) {
									System.out.println("Defensive chose");
									bestTo = escapeMovements[l];
									bestPawn = target;
								}
							}
						}
					}
				}
			}
		}

		//If no optimal play, random one
		if(bestPawn == null || bestTo == null) {
			while(bestTo == null) {
			int randomNumber1 = (int) (8 * Math.random());
			int randomNumber2 = (int) (8 * Math.random());
			AbstractPawn randomPawn = board[randomNumber1][randomNumber2];
			if(randomPawn != null && randomPawn.getTeam() == team) {
				Location[] movs = randomPawn.getMovement(game, randomPawn.getLocation(), true);
				if(movs.length > 0) {
					bestTo = movs[0];
					bestPawn = randomPawn;
				}
			 }
			}
		}
		//Make the play
		game.movePawn(bestPawn.getLocation(), bestTo);
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
		
		int bestValue = -10000;
		Location bestTo = null;
		AbstractPawn bestPawn = null;
		
		int depth = 3;
		
		AbstractPawn[][] board = game.getBoard();
		System.out.println("INITIAL STATE " + evalState(board, Team.BLACK));
		
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
						System.out.println("Start min max for " + pawn.getName() + " to " + targetedLoc);
						//Recursive tree
						int value = min(game, game.getCurrentPlayer(), depth);
						System.out.println("MIN VALUE FOR STRIKE " + (a++) + " is " + value);
						
						//Is it the best one ?
						if(value > bestValue) {
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
		
		System.out.println("Best move " + bestPawn.getName() + " to " + bestTo + " with score : " + bestValue);
		game.movePawn(bestPawn.getLocation(), bestTo);
		System.out.println("__________________________");
	}

	private int min(Game game, Team team, int depth) {

		int minValue = 10000;
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
						
						if(value < minValue) {
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

		int maxValue = -10000;
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
						
						if(value > maxValue) {
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
