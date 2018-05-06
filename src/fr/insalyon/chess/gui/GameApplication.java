package fr.insalyon.chess.gui;

import java.text.DateFormat;
import java.util.Arrays;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.Location;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameApplication extends Application {
	
	public final static int WIDTH = 600;
	public final static int HEIGHT = 600;
	
	private DataFormat locationFormat = new DataFormat("location");
	
	private Game game;	
	private Scene boardScene;
	private GridPane boardGrid;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jeu d'échecs");
		
		
		//Chess board
		this.game = new Game();
		game.init();
		
		this.boardGrid = new GridPane();
		this.boardScene = new Scene(boardGrid, WIDTH, HEIGHT);
		
		drawBoard();
		drawPieces();
		
		primaryStage.setScene(boardScene);
		primaryStage.show();
		//primaryStage.setResizable(false); doesn't work, make the whole board shift
	}
	
	public void refresh() {
		boardGrid.getChildren().clear();
		drawBoard();
		drawPieces();
	}
	
	public void drawBoard() {
		
		int colorInt = 0;
		for(int i = 0; i < 8; i++) {
			colorInt = i;
			for(int j = 0; j < 8; j++) {
				double xWidth = boardScene.getWidth() / 8.0;
				double yHeight = boardScene.getHeight() / 8.0;
				Rectangle rectangle = new Rectangle(xWidth, yHeight);
				rectangle.setOnDragOver(new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {
						event.acceptTransferModes(TransferMode.MOVE);
					}
				});
				rectangle.setOnDragDropped(new TakePlaceHandler(this));
				if(colorInt++ % 2 == 0) {
					rectangle.setFill(Color.BEIGE);
				} else {
					rectangle.setFill(Color.SIENNA);
				}
				boardGrid.add(rectangle, i, j);
			}
		}
		
	}

	public void drawPieces() {
		AbstractPawn[][] board = game.getBoard();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				AbstractPawn pawn = board[row][col];
				if(pawn == null) continue; //Avoid NullPointerException
				ImageView pieceImage = createPawnNode(pawn);
				boardGrid.add(pieceImage, col, row);
			}
		}
	}
	private ImageView createPawnNode(AbstractPawn pawn) {
		//Retrieve the proper pawn image
		Image image = new Image(pawn.getTeam().getName() + "_" + pawn.getName() + ".png");
		ImageView pieceImage = new ImageView(image);
		
		//Define drag and drop event
		pieceImage.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		    	//Check player turn
		    	if(pawn.getTeam() == game.getCurrentPlayer()) {
		    		Dragboard db = pieceImage.startDragAndDrop(TransferMode.MOVE);
		    		db.setDragView(image);
		    		ClipboardContent content = new ClipboardContent();
		    		content.put(locationFormat, pawn.getLocation());
		        	db.setContent(content);
		        
		        	//Show reachable cells
		        	Location[] locs = pawn.getMovement(game.getBoard(), pawn.getLocation());
		        	for(int i = 0; i < locs.length; i++) {
		        		Circle circle = new Circle(10);
		        		circle.setFill(Color.GREEN);
		        		circle.setTranslateX(26.0);
		        		circle.setDisable(true);
		        		boardGrid.add(circle, locs[i].getCol(), locs[i].getRow());
		        	}
		        	System.out.println(Arrays.toString(locs));
		    	}
		    }
		});
		
		//Piece need to support being replaced too
		pieceImage.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
		});
		pieceImage.setOnDragDropped(new TakePlaceHandler(this));
		//Center the image view
		pieceImage.setTranslateX(11);
		return pieceImage;
	}

	public DataFormat getLocationFormat() {
		return locationFormat;
	}
	public Game getGame() {
		return game;
	}
}
