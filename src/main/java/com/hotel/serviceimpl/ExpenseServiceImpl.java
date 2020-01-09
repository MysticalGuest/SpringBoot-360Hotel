package com.hotel.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ExpenseDao;
import com.hotel.entity.Expense;
import com.hotel.service.IExpenseService;

@Service // 标记当前类是service
public class ExpenseServiceImpl implements IExpenseService {
	@Autowired
	private ExpenseDao expenseDao;
	
	@Override
	public int insert(Expense expense) {
		return expenseDao.insert(expense);
	}
	
	@Override
	public List<Expense> getAllKinds() {
		return expenseDao.getAllKinds();
	}
	
	@Override
	public int updatePrice(Expense expense) {
		return expenseDao.updatePrice(expense);
	}
	
	@Override
	public int getHourRoom() {
		return expenseDao.getHourRoom();
	}
	
	@Override
	public int updateHourRoomPrice(Expense expense) {
		return expenseDao.updateHourRoomPrice(expense);
	}

}
