package fr.insalyon.chess;

import java.util.Arrays;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.LittlePawn;
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
			board[1][i] = new LittlePawn(Team.Black, new Location(1, i));
		}
		for(int i = 0; i < 8; i++) {
			board[6][i] = new LittlePawn(Team.White, new Location(6, i));
		}
	}
	public Team getCurrentPlayer() {
		return Team.values()[currentPlayer];
	}
	public void rotatePlayer() {
		currentPlayer = (currentPlayer + 1) % 2;
	}
	public static boolean isEmpty(AbstractPawn[][] board, Location location) {
		return board[location.getRow()][location.getCol()] == null;
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
