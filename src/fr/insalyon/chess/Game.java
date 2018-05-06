package fr.insalyon.chess;

import java.util.Arrays;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.Bishop;
import fr.insalyon.chess.core.pawns.King;
import fr.insalyon.chess.core.pawns.Knight;
import fr.insalyon.chess.core.pawns.LittlePawn;
import fr.insalyon.chess.core.pawns.Queen;
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
		//Little pawns
		for(int i = 0; i < 8; i++) {
			board[1][i] = new LittlePawn(Team.Black, new Location(1, i));
		}
		for(int i = 0; i < 8; i++) {
			board[6][i] = new LittlePawn(Team.White, new Location(6, i));
		}
		//Rooks
		board[0][0] = new Rook(Team.Black, new Location(0, 0));
		board[0][7] = new Rook(Team.Black, new Location(0, 7));
		board[7][0] = new Rook(Team.White, new Location(7, 0));
		board[7][7] = new Rook(Team.White, new Location(7, 7));
		//Knights
		board[0][1] = new Knight(Team.Black, new Location(0, 1));
		board[0][6] = new Knight(Team.Black, new Location(0, 6));
		board[7][1] = new Knight(Team.White, new Location(7, 1));
		board[7][6] = new Knight(Team.White, new Location(7, 6));
		//Bishops
		board[0][2] = new Bishop(Team.Black, new Location(0, 2));
		board[0][5] = new Bishop(Team.Black, new Location(0, 5));
		board[7][2] = new Bishop(Team.White, new Location(7, 2));
		board[7][5] = new Bishop(Team.White, new Location(7, 5));
		//Queens
		board[0][3] = new Queen(Team.Black, new Location(0, 3));
		board[7][3] = new Queen(Team.White, new Location(7, 3));
		//Kings
		board[0][4] = new King(Team.Black, new Location(0, 4));
		board[7][4] = new King(Team.White, new Location(7, 4));
		
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
