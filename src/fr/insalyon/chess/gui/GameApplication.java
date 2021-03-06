package fr.insalyon.chess.gui;

import fr.insalyon.chess.Game;
import fr.insalyon.chess.ai.ChessAI2;
import fr.insalyon.chess.ai.ChessAI3;
import fr.insalyon.chess.core.AbstractPawn;
import fr.insalyon.chess.core.End;
import fr.insalyon.chess.core.Team;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameApplication extends Application {

	public final static int WIDTH = 600;
	public final static int HEIGHT = 600;

	private DataFormat locationFormat = new DataFormat("location");

	private Game game;
	private Scene boardScene;
	private GridPane boardGrid;
	private boolean computerTurn = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jeu d'�checs");

		// Generate the game object
		this.game = new Game();
		game.init();

		// Draw the chess board
		this.boardGrid = new GridPane();
		this.boardScene = new Scene(boardGrid, WIDTH, HEIGHT);

		drawBoard();
		drawPieces();

		primaryStage.setScene(boardScene);
		primaryStage.show();
		// primaryStage.setResizable(false); doesn't work, make the whole board shift
		refresh();
	}

	public void refresh() {
		boardGrid.getChildren().clear();
		drawBoard();
		drawPieces();
	}

	public void drawBoard() {

		int colorInt = 0;
		for (int i = 0; i < 8; i++) {
			colorInt = i;
			for (int j = 0; j < 8; j++) {
				double xWidth = boardScene.getWidth() / 8.0;
				double yHeight = boardScene.getHeight() / 8.0;
				Rectangle rectangle = new Rectangle(xWidth, yHeight);
				rectangle.setOnDragOver(new DragOverAccept());
				rectangle.setOnDragDropped(new TakePlaceHandler(this));
				if (colorInt++ % 2 == 0) {
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
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				AbstractPawn pawn = board[row][col];
				if (pawn == null)
					continue; // Avoid NullPointerException
				ImageView pieceImage = createPawnNode(pawn);
				boardGrid.add(pieceImage, col, row);
			}
		}
	}

	private ImageView createPawnNode(AbstractPawn pawn) {
		// Retrieve the proper pawn image
		Image image = new Image("resources/" + pawn.getTeam().getName() + "_" + pawn.getName() + ".png");
		ImageView pieceImage = new ImageView(image);

		// Define drag and drop event
		pieceImage.setOnDragDetected(new PawnDragEvent(pawn, pieceImage, this));

		// Piece need to support being replaced too
		pieceImage.setOnDragOver(new DragOverAccept());
		pieceImage.setOnDragDropped(new TakePlaceHandler(this));
		// Center the image view
		pieceImage.setTranslateX(11);
		return pieceImage;
	}

	public DataFormat getLocationFormat() {
		return locationFormat;
	}

	public Game getGame() {
		return game;
	}

	public GridPane getBoardGrid() {
		return boardGrid;
	}

	public boolean isComputerTurn() {
		return computerTurn;
	}
	public void setComputerTurn(boolean computerTurn) {
		this.computerTurn = computerTurn;
	}
}
