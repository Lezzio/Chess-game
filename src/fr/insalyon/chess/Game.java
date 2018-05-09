package fr.insalyon.chess;

import java.util.Arrays;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.End;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import fr.insalyon.chess.core.pawns.Bishop;
import fr.insalyon.chess.core.pawns.King;
import fr.insalyon.chess.core.pawns.Knight;
import fr.insalyon.chess.core.pawns.LittlePawn;
import fr.insalyon.chess.core.pawns.Queen;
import fr.insalyon.chess.core.pawns.Rook;

public class Game implements Cloneable {
	
	private AbstractPawn[][] board;
	private int currentPlayer = 0;
	private King blackKing;
	private King whiteKing;
	
	public Game() {
		board = new AbstractPawn[8][8];
	}
	
	/**
	 * Initialize the board by default
	 */
	public void init() {
		//Little pawns
		for(int i = 0; i < 8; i++) {
			board[1][i] = new LittlePawn(Team.BLACK, new Location(1, i));
		}
		for(int i = 0; i < 8; i++) {
			board[6][i] = new LittlePawn(Team.WHITE, new Location(6, i));
		}
		//Rooks
		board[0][0] = new Rook(Team.BLACK, new Location(0, 0));
		board[0][7] = new Rook(Team.BLACK, new Location(0, 7));
		board[7][0] = new Rook(Team.WHITE, new Location(7, 0));
		board[7][7] = new Rook(Team.WHITE, new Location(7, 7));
		//Knights
		board[0][1] = new Knight(Team.BLACK, new Location(0, 1));
		board[0][6] = new Knight(Team.BLACK, new Location(0, 6));
		board[7][1] = new Knight(Team.WHITE, new Location(7, 1));
		board[7][6] = new Knight(Team.WHITE, new Location(7, 6));
		//Bishops
		board[0][2] = new Bishop(Team.BLACK, new Location(0, 2));
		board[0][5] = new Bishop(Team.BLACK, new Location(0, 5));
		board[7][2] = new Bishop(Team.WHITE, new Location(7, 2));
		board[7][5] = new Bishop(Team.WHITE, new Location(7, 5));
		//Queens
		board[0][3] = new Queen(Team.BLACK, new Location(0, 3));
		board[7][3] = new Queen(Team.WHITE, new Location(7, 3));
		//Kings
		blackKing = new King(Team.BLACK, new Location(0, 4));
		board[0][4] = blackKing;
		whiteKing = new King(Team.WHITE, new Location(7, 4));
		board[7][4] = whiteKing;
		
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
	public End gameOver(Team team) {

		Location[] escapeMoves = new Location[0];

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn != null && pawn.getTeam() == team) {
					escapeMoves = Location.concat(escapeMoves, pawn.getMovement(this, pawn.getLocation(), true));
				}
			}
		}

		if(escapeMoves.length == 0) {
			if(check(team)) {
				return End.CHECKMATE;
			} else {
				return End.PAT;
			}
		}
		return End.NONE;
	}
	
	//The king is in threat
	public boolean check(Team team) {
		King king = getKing(team);
		
		Location[] targetedLocations = new Location[0];
		//Find all targeted location by enemy team
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AbstractPawn pawn = board[i][j];
				if(pawn != null && pawn.getTeam() != king.getTeam()) {
					targetedLocations = Location.concat(targetedLocations, pawn.getMovement(this, pawn.getLocation(), false));
				}
			}
		}
		return Location.locationArrayContains(targetedLocations, king.getLocation());
	}
	public King getKing(Team team) {
		King king = null;
		switch(team) {
		case BLACK:
			king = blackKing;
			break;
		case WHITE:
			king = whiteKing;
			break;
		}
		return king;
	}

  	public Object clone() {
    	Object clonedGame = null;
    	try {
    		clonedGame = super.clone();
    	} catch(CloneNotSupportedException exception) {
    		exception.printStackTrace();
	    }
	    return clonedGame;
  	}
}
