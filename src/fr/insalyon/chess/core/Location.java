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
	public boolean equals(Location location) {
		return this.col == location.col && this.row == location.row;
	}
	@Override
	public String toString() {
		return "[Location] col:" + col + " row:" + row;
	}
	public static boolean locationArrayContains(Location[] array, Location location) {
		boolean contained = false;
		int i = 0;
		while(!contained && i < array.length) {
			if(location.equals(array[i++])) {
				contained = true;
			}
		}
		return contained;
	}
}
