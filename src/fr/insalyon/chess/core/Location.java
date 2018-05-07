package fr.insalyon.chess.core;

import java.io.Serializable;

//Serializable for the DataFormat transfer
public class Location implements Serializable {

	private int col;
	private int row;
	
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getCol() {
		if(col < 0) {
			return 0;
		} else {
			return col;
		}
	}
	public int getRow() {
		if(row < 0) {
			return 0;
		} else {
			return row;
		}
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public Location add(int row, int col) {
		return new Location(this.row + row, this.col + col);
	}
	public boolean equals(Location location) {
		return this.col == location.col && this.row == location.row;
	}
	public boolean isInside(Object[][] array) {
		return row >= 0 && col >= 0 && row < array.length && col < array[0].length;
	}
	@Override
	public String toString() {
		return "[Location] row:" + row + " col:" + col;
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
	public static Location[] addLocation(Location[] locations, Location location) {
		Location[] newLocations = new Location[locations.length + 1];
		for(int i = 0; i < locations.length; i++) {
			newLocations[i] = locations[i];
		}
		newLocations[locations.length] = location;
		return newLocations;
	}
	public static Location[] concat(Location[] array1, Location[] array2) {
		int a = 0;
		Location[] merged = new Location[array1.length + array2.length];
		for(int i = 0; i < array1.length; i++) {
			merged[a++] = array1[i];
		}
		for(int i = 0; i < array2.length; i++) {
			merged[a++] = array2[i];
		}
		return merged;
	}
}
