package com.hotel.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Expense implements Serializable{
	private static final long serialVersionUID = 1L;
	private String kinds;
	private String name;
	private int price;
	
	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Expense(String kinds, int price) {
		super();
		this.kinds=kinds;
		this.price=price;
	}

	public String getKinds() {
		return kinds;
	}

	public void setKinds(String kinds) {
		this.kinds = kinds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	//我重写了toString()方法,为了将从数据库拿出的数据直接格式化为JSON格式,可以直接传到前台
	@Override
	public String toString() {
		return "{\"kinds\":\"" + kinds +"\", \"name\":\"" +name +"\", \"price\":" + price + "}";
	}

}
