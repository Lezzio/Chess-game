package fr.insalyon.chess.gui;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
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
		if(Location.locationArrayContains(selectedPawn.getMovement(gameApplication.getGame().getBoard(), selectedPawn.getLocation()), to)) {
			gameApplication.getGame().movePawn(from, to);
			event.setDropCompleted(true);
		} else {
			event.setDropCompleted(false);
		}
		gameApplication.refresh();
	}

}
