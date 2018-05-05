package fr.insalyon.chess;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.gui.GameGUI;
import javafx.application.Application;

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
			for(int j = 0; j < 8; j++) {
				
			}
		}
	}
	
	public void start() {
		Application.launch(GameGUI.class);
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
