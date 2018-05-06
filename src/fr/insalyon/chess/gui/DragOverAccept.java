package fr.insalyon.chess.gui;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class DragOverAccept implements EventHandler<DragEvent> {

		@Override
		public void handle(DragEvent event) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		
}
