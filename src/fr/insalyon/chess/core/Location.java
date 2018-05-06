package fr.insalyon.chess.core;

import java.io.Serializable;

public class Location implements Serializable {

	private int col;
	private int row;
	
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	public void setRow(int row) {
		this.row = row;
	}
	@Override
	public String toString() {
		return "[Location] col:" + col + " row:" + row;
	}
}
