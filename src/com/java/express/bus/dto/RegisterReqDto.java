package com.java.express.bus.dto;

import java.sql.Date;
import java.sql.Time;

public class RegisterReqDto {
	private String company;
	private Date departDate;
	private Time departTime;
	private String depart;
	private String arrive;
	private String rating;
	private int seatId;
	private int seat;
	private int price;
	
	public RegisterReqDto(String company, Date departDate, Time departTime, String depart, String arrive, String rating, int seatId, int seat, int price) {
		this.company = company;
		this.departDate = departDate;
		this.departTime = departTime;
		this.depart = depart;
		this.arrive = arrive;
		this.rating = rating;
		this.seatId = seatId;
		this.seat = seat;
		this.price = price;
	}
	
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getDepartDate() {
		return departDate;
	}
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}
	public Time getDepartTime() {
		return departTime;
	}
	public void setDepartTime(Time departTime) {
		this.departTime = departTime;
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
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
