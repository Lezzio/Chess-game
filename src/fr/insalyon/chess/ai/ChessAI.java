package fr.insalyon.chess.ai;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.King;

public class ChessAI {

	
	/*
	 * ATTENTION
	 * 
	 * Ancienne version : regarder ChessAI3
	 * 
	 */

	//Avoid suicide variables
	private Location[] allTargetedLocations = new Location[0];
	
	//For putting in check variables
	private int leastKingMovs = 8;
	private int enemyPieces = 0;
	
	//Level 1 AI
	public void makePlay(Game game, Team team) {
		
		AbstractPawn[][] board = game.getBoard();
		
		
		//Best movement possible
		int bestValue = 0;
		AbstractPawn bestPawn = null;
		Location bestTo = null;

		//Set up useful variables
		setupVariables(game, team);
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
							
							//Simulate movement to see if targeted location
							Location from = pawn.getLocation();
							game.movePawn(from, targetedLoc);
							
							//Update
							setupVariables(game, team);
							
							//Worth suicide ?
							boolean inDanger = Location.locationArrayContains(allTargetedLocations, targetedLoc);
							if((inDanger && value >= pawn.getValue()) || !inDanger) {
								System.out.println("WORTH " + value + " against " + pawn.getValue());
								bestValue = value;
								bestTo = targetedLoc;
								bestPawn = pawn;
							}
							
							//Undo simulation
							game.movePawn(targetedLoc, from);
							board[targetedLoc.getRow()][targetedLoc.getCol()] = target;
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

							//Update variables
							setupVariables(game, team);
							
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
		//Update variables
		setupVariables(game, team);
		//Defense
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn == null) continue;
				
				//Enemy pawn ?
				if(pawn.getTeam() != team) {
					//If attacked location is the enemy pawn, no need to defend against him in the evaluation
					if(bestTo != null && bestTo.equals(pawn.getLocation())) continue;
					Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
					//Evaluate the highest bounty target
					for(int k = 0; k < targets.length; k++) {
						Location targetedLoc = targets[k];
						AbstractPawn target = game.getPawnByLocation(targetedLoc);
						if(target == null) continue;
						int value = target.getValue();
						System.out.println("Defensive play : " + value + " against " + bestValue);

						//Is this enemy play more valuable than the attack one ?
						if((value > bestValue) || (bestPawn != null && value == bestValue && pawn.getValue() < bestPawn.getValue())) {
							System.out.println("Condition passed for " + targetedLoc + " " + target.getName());
							//Then escape
							Location[] escapeMovements = target.getMovement(game, target.getLocation(), true);
							System.out.println("Possibilites : " + escapeMovements.length);
							for(int l = 0; l < escapeMovements.length; l++) {
								if(Game.isEmpty(game.getBoard(), escapeMovements[l]) && !Location.locationArrayContains(allTargetedLocations, escapeMovements[l])) {
									System.out.println("Not targeted ESCAPE zone " + escapeMovements[l]);
									bestTo = escapeMovements[l];
									bestPawn = target;
									bestValue = target.getValue();
								}
							}
						}
					}
				}
			}
		}

		//If no optimal play, random one without going too deep and in danger zone

		//Reset and find another strike for more aggressive play
		while(bestPawn == null && bestTo == null) {
			int randomNumber1 = (int) (8 * Math.random());
			int randomNumber2 = (int) (8 * Math.random());
			AbstractPawn randomPawn = board[randomNumber1][randomNumber2];
			if(randomPawn != null && randomPawn.getTeam() == team) {
				Location[] movs = randomPawn.getMovement(game, randomPawn.getLocation(), true);

				if(movs.length > 0) {
					int randomMov = (int) (movs.length * Math.random());
					//Simulate movement
					AbstractPawn buffer = game.getPawnByLocation(movs[randomMov]);
					Location from = randomPawn.getLocation();
					game.movePawn(from, movs[randomMov]);
					//Update targeted locations
					setupVariables(game, team);
					
					//Safe move ? Then make it
					if(!Location.locationArrayContains(allTargetedLocations, movs[randomMov])) {
						bestTo = movs[randomMov];
						bestPawn = randomPawn;
					}
					
					//Undo simulation
					game.movePawn(movs[randomMov], from);
					board[movs[randomMov].getRow()][movs[randomMov].getCol()] = buffer;
				}
			}
		}
		//Make the play
		game.movePawn(bestPawn.getLocation(), bestTo);
		System.out.println("____________________________________________________");
	}

	private void setupVariables(Game game, Team team) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = game.getBoard()[i][j];
				if(pawn == null) continue;
				if(pawn.getTeam() != team) {
					Location[] targets = pawn.getMovement(game, pawn.getLocation(), true);
					allTargetedLocations = Location.concat(allTargetedLocations, targets);
					enemyPieces++;
				}
			}
		}
	}

}
