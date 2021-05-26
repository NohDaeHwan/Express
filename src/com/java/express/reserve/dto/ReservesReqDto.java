package com.java.express.reserve.dto;

import java.sql.Timestamp;

public class ReservesReqDto {
	private int bookId;
	private String userId;
	private String reserveState;
	private Timestamp departDate;
	private String depart;
	private String arrive;
	private String company;
	private String rating;
	private int buying;
	private int price;
	private String seat;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReserveState() {
		return reserveState;
	}
	public void setReserveState(String reserveState) {
		this.reserveState = reserveState;
	}
	public Timestamp getDepartDate() {
		return departDate;
	}
	public void setDepartDate(Timestamp departDate) {
		this.departDate = departDate;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getBuying() {
		return buying;
	}
	public void setBuying(int buying) {
		this.buying = buying;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
}
