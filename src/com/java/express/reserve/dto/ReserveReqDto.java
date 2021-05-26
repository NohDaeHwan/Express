package com.java.express.reserve.dto;

public class ReserveReqDto {
	private int userId;
	private int busId;
	private String reserveState;
	private int buying;
	private String seat;
	private int price;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getReserveState() {
		return reserveState;
	}
	public void setReserveState(String reserveState) {
		this.reserveState = reserveState;
	}
	public int getBuying() {
		return buying;
	}
	public void setBuying(int buying) {
		this.buying = buying;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
