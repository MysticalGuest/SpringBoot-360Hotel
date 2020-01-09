package com.hotel.service;

import java.util.List;

import com.hotel.entity.Customer;

public interface ICustomerService {

	int insert(Customer customer);

	Customer login(Customer customer);

	List<Customer> getAllCustomer();
	
	List<Customer> doSearch(Customer customer);
	
	int removeCustomerById(Customer CustomerId);

	Customer getCustomerById(Customer customer);
	
	int getNumOfBillPerDay();
	
	int getNumOfBill();
	
	List<String> getNumOfRoomPerDay();
	
	List<String> getNumOfRoom();
	
	int getSumOfFeePerDay();
	
	int getSumOfFee();
	
	int profit(Customer customer);
}
