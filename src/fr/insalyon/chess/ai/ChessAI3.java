package fr.insalyon.chess.ai;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;

public class ChessAI3 {
	
	/*
	 * Return an integer giving a perspective of the current board for a given team
	 */
	public int evalState(Game game, Team team) {
		
		AbstractPawn[][] board = game.getBoard();
		
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

	int cases = 0;
	int b = 0;
	
	//Min max tree implementation
	public void play(Game game, Team team) {
		
		long firstTime = System.currentTimeMillis();
		
		AbstractPawn[][] board = game.getBoard();

		int initialValue = evalState(game, Team.BLACK);
		int bestValue = Integer.MIN_VALUE;
		//The size is wide enough to be able to store all equal possible moves (the max possible moves should be 218)
		Location[] bestTos = new Location[512];
		AbstractPawn[] bestPawns = new AbstractPawn[512];
		int p = 0; //To current index of bestTo and bestPawn
		
		int depth = 3;
		
		System.out.println("INITIAL SCORE " + initialValue);
		
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
						//Recursive tree
						int value = miniMax(game, game.getCurrentPlayer(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
						
						//Is it the best one ?
						if(value > bestValue) {
							bestValue = value;
							//Reset bestTo and bestPawn
							bestTos = new Location[512];
							bestPawns = new AbstractPawn[512];
							p = 0;

							bestTos[p] = targetedLoc;
							bestPawns[p++] = pawn;
						} else if (value == bestValue) { //Just another good one ? Add it
							bestTos[p] = targetedLoc;
							bestPawns[p++] = pawn;
						}
						
						//Undo simulation
						game.movePawn(targetedLoc, from);
						board[targetedLoc.getRow()][targetedLoc.getCol()] = buffer;
						game.rotatePlayer();
						
				}
			}
		}
		// Select random equal best moves to create opportunities and avoid repetitive playing.
		int selectedPlay = (int) (Math.random() * p);
		Location bestTo = bestTos[selectedPlay];
		AbstractPawn bestPawn = bestPawns[selectedPlay];
		
		if(bestTo != null && bestPawn != null) {
		System.out.println("Best move " + bestPawn.getName() + " to " + bestTo + " with score : " + bestValue + " and done " + cases + " cases ");
		game.movePawn(bestPawn.getLocation(), bestTo);
		System.out.println("Alpha beta triggered " + b);
		long timeDif = System.currentTimeMillis() - firstTime;
		System.out.println("Time taken = " + timeDif + "ms");
		System.out.println("__________________________");
		}
	}
	private int miniMax(Game game, Team team, int depth, int alpha, int beta, boolean maximize) {

		cases++;
		
		AbstractPawn[][] board = game.getBoard();

		if(depth == 0) {
			return evalState(game, Team.BLACK);
		}
		
		int limitValue = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		
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
						int value = miniMax(game, game.getCurrentPlayer(), (depth - 1), alpha, beta, !maximize);
						
						if(!maximize && value <= limitValue) {
							limitValue = value;
						} else if(maximize && value >= limitValue) {
							limitValue = value;
						}

						//Undo simulation
						game.movePawn(targetedLoc, from);
						board[targetedLoc.getRow()][targetedLoc.getCol()] = buffer;
						game.rotatePlayer();
						
						//Update alpha and beta
						if(maximize) {
							alpha = Math.max(alpha, limitValue);
						} else {
							beta = Math.min(beta, limitValue);
						}
						if(beta <= alpha) {
							b++;
							return limitValue;
						}
						
						
				}
			}
		}
		
		return limitValue;
		
	}

}
