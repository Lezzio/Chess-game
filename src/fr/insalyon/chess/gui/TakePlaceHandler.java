package fr.insalyon.chess.gui;

import fr.insalyon.chess.ai.ChessAI;
import fr.insalyon.chess.ai.ChessAI2;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.End;
import fr.insalyon.chess.core.Location;
import fr.insalyon.chess.core.Team;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class TakePlaceHandler implements EventHandler<DragEvent> {
	
	GameApplication gameApplication;
	
	public TakePlaceHandler(GameApplication gameApplication) {
		this.gameApplication = gameApplication;
	}
	
	@Override
	public void handle(DragEvent event) {
		event.acceptTransferModes(TransferMode.MOVE);
		
		Dragboard db = event.getDragboard();
		Location from = (Location) db.getContent(gameApplication.getLocationFormat());
		Location to = new Location(GridPane.getRowIndex((Node) event.getTarget()), GridPane.getColumnIndex((Node) event.getTarget()));
		AbstractPawn selectedPawn = gameApplication.getGame().getPawnByLocation(from);
		//Complete the movement only if accepted one
		if(Location.locationArrayContains(selectedPawn.getMovement(gameApplication.getGame(), selectedPawn.getLocation(), true), to)) {
			gameApplication.getGame().movePawn(from, to);
			gameApplication.getGame().rotatePlayer(); //Change player turn
			gameApplication.refresh();
			event.setDropCompleted(true);
			if(!checkOver()) {
				AsyncComputerPlay asyncComputerPlay = new AsyncComputerPlay(gameApplication);
				asyncComputerPlay.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				    @Override
				    public void handle(WorkerStateEvent t) {
				    	gameApplication.refresh();
				    	gameApplication.getGame().rotatePlayer();
				    	checkOver();
				    }
				});
				Thread thread = new Thread(asyncComputerPlay);
				thread.start();
			}
		} else {
			gameApplication.refresh();
			event.setDropCompleted(false);
		}
	}
	
	public boolean checkOver() {
		//We check both at the same time for particular types of pat
		End endBlack = gameApplication.getGame().gameOver(Team.BLACK);
		End endWhite = gameApplication.getGame().gameOver(Team.WHITE);
		if(endBlack == End.PAT || endWhite == End.PAT) {
			System.out.println("Egalité! PAT");
			return true;
		} else if (endBlack != End.NONE) {
			System.out.println("Les blancs gagnent! : " + endBlack);
			return true;
		} else if(endWhite != End.NONE) {
			System.out.println("Les noir gagnent! : " + endWhite);
			return true;
		} 
		return false;
	}
	ChessAI2 ai = new ChessAI2();
	public void computerPlay(Team team) {
		if(gameApplication.getGame().getCurrentPlayer() == team) {
			ai.play(gameApplication.getGame(), team);
			gameApplication.getGame().rotatePlayer();
			gameApplication.refresh();
		}
	}

}
