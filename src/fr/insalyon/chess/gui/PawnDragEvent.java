package fr.insalyon.chess.gui;

import java.util.Arrays;

import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PawnDragEvent implements EventHandler<MouseEvent> {

	private GameApplication gameApplication;
	private ImageView imageView;
	private AbstractPawn pawn;
	
	public PawnDragEvent(AbstractPawn pawn, ImageView imageView, GameApplication gameApplication) {
		this.imageView = imageView;
		this.gameApplication = gameApplication;
		this.pawn = pawn;
	}
	
	@Override
	public void handle(MouseEvent event) {
		// Check player turn
		if (pawn.getTeam() == gameApplication.getGame().getCurrentPlayer()) {
			Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
			db.setDragView(imageView.getImage());
			ClipboardContent content = new ClipboardContent();
			content.put(gameApplication.getLocationFormat(), pawn.getLocation());
			db.setContent(content);

			// Show reachable cells
			Location[] locs = pawn.getMovement(gameApplication.getGame().getBoard(), pawn.getLocation());
			for (int i = 0; i < locs.length; i++) {
				Circle circle = new Circle(10);
				circle.setFill(Color.GREEN);
				circle.setTranslateX(26.0);
				circle.setDisable(true);
				Location loc = locs[i];
				if (loc == null) continue; // Avoid NullPointerException
				gameApplication.getBoardGrid().add(circle, loc.getCol(), loc.getRow());
			}
		}
	}

}
