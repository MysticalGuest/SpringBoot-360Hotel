package com.hotel.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Apartment implements Serializable {
	private static final long serialVersionUID = 1L;
	private String roomNum;
	private int price;
	private boolean state;
	
	public Apartment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Apartment(String roomNum, int price, boolean state) {
		super();
		this.roomNum = roomNum;
		this.price=price;
		this.state = state;
	}

	public String getroomNum() {
		return roomNum;
	}

	public void setroomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

//	@Override
//	public String toString() {
//		return "Apartments [roomNum="+roomNum+",price="+price+",state="+state+"]";
//	}
	
	@Override
	public String toString() {
		return "{\"roomNum\":\""+roomNum+"\",\"price\":"+price+",\"state\":"+state+"}";
	}

	

}
