package com.java.express.seat.dto;

public class Seat {
	private int id;
	private int row;
	private int col;
	
	public Seat() {}

	public Seat(int id, int row, int col) {
		this.id = id;
		this.row = row;
		this.col = col;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
}

