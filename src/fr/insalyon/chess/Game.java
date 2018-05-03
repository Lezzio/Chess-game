package fr.insalyon.chess;

import java.util.concurrent.Future;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;

public class Game {
	
	private AbstractPawn[][] board = new AbstractPawn[8][8];
	private int currentPlayer = 0;
	
	public Game() {
		
	}
	
	/**
	 * Initialize the board by default
	 */
	public void init() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
			}
		}
	}
	
	public void start() {
		//GUI Init
		/*
		while(!checkmate()) {
		}
		*/
		
		//Faire la logique dans l'événement
	}
	
	//The game is over
	private boolean checkmate() {
		return false;
	}
	
	//The king is in threat
	private boolean check() {
		return false;
	}

}
