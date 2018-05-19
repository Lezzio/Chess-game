package fr.insalyon.chess.gui;

import fr.insalyon.chess.ai.ChessAI3;
import fr.insalyon.chess.core.Team;
import javafx.concurrent.Task;

public class AsyncComputerPlay extends Task<Object> {

	private GameApplication gameApplication;
	private	ChessAI3 ai = new ChessAI3();
	
	public AsyncComputerPlay(GameApplication gameApplication) {
		this.gameApplication = gameApplication;
	}
	@Override
	protected Object call() throws Exception {
		computerPlay(Team.BLACK);
		return null;
	}
	public void computerPlay(Team team) {
		if(gameApplication.getGame().getCurrentPlayer() == team) {
			ai.play(gameApplication.getGame(), team);
		}
	}

}