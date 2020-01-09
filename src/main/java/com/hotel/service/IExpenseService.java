package com.hotel.service;

import java.util.List;

import com.hotel.entity.Expense;

public interface IExpenseService {
	
	int insert(Expense expense);
	
	List<Expense> getAllKinds();
	
	int updatePrice(Expense expense);
	
	int getHourRoom();
	
	int updateHourRoomPrice(Expense expense);

}
