package fr.insalyon.chess.core;

public enum Team {
	
	WHITE("white"), BLACK("black");
	
	private String name;
	
	private Team(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}
