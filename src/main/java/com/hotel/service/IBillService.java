package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.entity.Bill;

public interface IBillService {
	
	int insert(Bill bill);
	
	int delete(Bill bill);
	
	List<Bill> getBillPerDay();
	
	List<Bill> getBill();
	
	int updateExpense(Bill bill);
	
	Map<String, Integer> getPerKindsSumPerDay();
	
	Map<String, Integer> getPerKindsSum();
	
	List<Map<String, Integer>> getRoomChargePerCustomerPerDay();
	
	List<Map<String, Integer>> getRoomChargePerCustomer();
	
	List<Map<String, Integer>> getTurnoverPerDayThisWeek();
	
	List<Map<String, Integer>> getTurnoverPerDayLastWeek();
	
	List<Map<String, Integer>> getTurnoverPerDayLastMonth();
	
	List<Map<String, Integer>> getTurnoverPerWeekThisMonth();
	
	List<Map<String, Integer>> getTurnoverPerQuarterThisYear();
	
	List<Map<String, Integer>> getTurnoverTheseYears();

}
