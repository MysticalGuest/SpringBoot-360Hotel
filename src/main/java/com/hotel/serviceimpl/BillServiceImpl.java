package com.hotel.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.BillDao;
import com.hotel.entity.Bill;
import com.hotel.service.IBillService;

@Service // 标记当前类是service
public class BillServiceImpl implements IBillService {
	@Autowired
	private BillDao billDao;
	
//	@Override
//	public Bill login(Bill bill) {
//		return null;
//	}
	
	@Override
	public int insert(Bill bill) {
		return billDao.insert(bill);
	}
	
	@Override
	public int delete(Bill bill) {
		return billDao.delete(bill);
	}
	
	@Override
	public List<Bill> getBillPerDay() {
		return billDao.getBillPerDay();
	}
	
	@Override
	public List<Bill> getBill() {
		return billDao.getBill();
	}
	
	@Override
	public int updateExpense(Bill bill) {
		return billDao.updateExpense(bill);
	}
	
	@Override
	public Map<String, Integer> getPerKindsSumPerDay() {
		return billDao.getPerKindsSumPerDay();
	}
	
	@Override
	public Map<String, Integer> getPerKindsSum() {
		return billDao.getPerKindsSum();
	}

	@Override
	public List<Map<String, Integer>> getRoomChargePerCustomerPerDay() {
		return billDao.getRoomChargePerCustomerPerDay();
	}
	
	@Override
	public List<Map<String, Integer>> getRoomChargePerCustomer() {
		return billDao.getRoomChargePerCustomer();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverPerDayThisWeek() {
		return billDao.getTurnoverPerDayThisWeek();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverPerDayLastWeek() {
		return billDao.getTurnoverPerDayLastWeek();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverPerDayLastMonth() {
		return billDao.getTurnoverPerDayLastMonth();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverPerWeekThisMonth() {
		return billDao.getTurnoverPerWeekThisMonth();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverPerQuarterThisYear() {
		return billDao.getTurnoverPerQuarterThisYear();
	}
	
	@Override
	public List<Map<String, Integer>> getTurnoverTheseYears() {
		return billDao.getTurnoverTheseYears();
	}

}
