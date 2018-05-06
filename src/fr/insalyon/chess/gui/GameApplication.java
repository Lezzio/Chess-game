package fr.insalyon.chess.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameGUI extends Application {
	
	public final static int WIDTH = 680;
	public final static int HEIGHT = 680;
	
	public GameGUI() {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jeu d'échecs");
		
		//Chess board
		
		GridPane boardGrid = new GridPane();
		Scene board = new Scene(boardGrid, WIDTH, HEIGHT);
		
		int colorInt = 0;
		for(int i = 0; i < 8; i++) {
			colorInt = i;
			for(int j = 0; j < 8; j++) {
				double xWidth = board.getWidth() / 8.0;
				double yHeight = board.getHeight() / 8.0;
				Rectangle rectangle = new Rectangle(xWidth, yHeight);
				if(colorInt++ % 2 == 0) {
					rectangle.setFill(Color.BEIGE);
				} else {
					rectangle.setFill(Color.SIENNA);
				}
				boardGrid.add(rectangle, i, j);
			}
		}
		primaryStage.setScene(board);
		primaryStage.show();
		//primaryStage.setResizable(false); doesn't work, make the whole board to shift
	}
}
