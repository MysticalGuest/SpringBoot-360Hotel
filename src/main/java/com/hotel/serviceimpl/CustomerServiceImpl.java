package com.hotel.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.CustomerDao;
import com.hotel.entity.Customer;
import com.hotel.service.ICustomerService;

@Service // 标记当前类是service
public class CustomerServiceImpl implements ICustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer login(Customer customer) {
		return null;
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerDao.getAllCustomer();
	}
	
	@Override
	public List<Customer> doSearch(Customer customer) {
		return customerDao.doSearch(customer);
	}

	@Override
	public int insert(Customer customer) {
		return customerDao.insert(customer);
	}

	@Override
	public int removeCustomerById(Customer customer) {
		return customerDao.removeCustomerById(customer);
	}

	@Override
	public Customer getCustomerById(Customer customer) {
		return customerDao.getCustomerById(customer);
	}
	
	@Override
	public int getNumOfBillPerDay() {
		return customerDao.getNumOfBillPerDay();
	}
	
	@Override
	public int getNumOfBill() {
		return customerDao.getNumOfBill();
	}
	
	@Override
	public List<String> getNumOfRoomPerDay() {
		return customerDao.getNumOfRoomPerDay();
	}
	
	@Override
	public List<String> getNumOfRoom() {
		return customerDao.getNumOfRoom();
	}
	
	@Override
	public int getSumOfFeePerDay() {
		return customerDao.getSumOfFeePerDay();
	}
	
	@Override
	public int getSumOfFee() {
		return customerDao.getSumOfFee();
	}
	
	@Override
	public int profit(Customer customer) {
		return customerDao.profit(customer);
	}

}
