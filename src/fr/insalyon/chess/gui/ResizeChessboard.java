package fr.insalyon.chess.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ResizeChessboard extends Application {
    GridPane root = new GridPane();
    final int size = 8;

    public void start(Stage primaryStage) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.WHITE;
                else color = Color.BLACK;
                square.setFill(color);
                root.add(square, col, row);
                square.setWidth(680.0 / 8.0);
                square.setHeight(680.0 / 8.0);
            }
        }
        primaryStage.setScene(new Scene(root, 680, 680));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}