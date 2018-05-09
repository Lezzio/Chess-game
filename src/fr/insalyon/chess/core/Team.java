package fr.insalyon.chess.core;

public enum Team {
	
	WHITE("white", 0), BLACK("black", 1);
	
	private String name;
	private int id;
	
	private Team(String name, int id) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}

}
