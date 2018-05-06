package fr.insalyon.chess;

import java.util.Arrays;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.Rook;

public class Game {
	
	private AbstractPawn[][] board;
	private int currentPlayer = 0;
	
	public Game() {
		board = new AbstractPawn[8][8];
	}
	
	/**
	 * Initialize the board by default
	 */
	public void init() {
		for(int i = 0; i < 8; i++) {
			board[0][i] = new Rook(Team.White, new Location(0, i));
		}
		/*
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = new Rook(Team.White);
			}
		}
		*/
	}
	
	public AbstractPawn[][] getBoard() {
		return board;
	}
	public AbstractPawn getPawnByLocation(Location location) {
		return board[location.getRow()][location.getCol()];
	}
	public void movePawn(Location from, Location to) {
		board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
		board[from.getRow()][from.getCol()] = null;
		board[to.getRow()][to.getCol()].setLocation(to);
	}
	public void display() {
		System.out.println(Arrays.deepToString(board));
	}
	
	//The game is over
	public boolean checkmate() {
		return false;
	}
	
	//The king is in threat
	public boolean check() {
		return false;
	}

}
