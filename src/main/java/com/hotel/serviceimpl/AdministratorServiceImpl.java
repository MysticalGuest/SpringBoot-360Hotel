package com.hotel.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.AdministratorDao;
import com.hotel.entity.Administrator;
import com.hotel.service.IAdministratorService;

@Service // 标记当前类是service
public class AdministratorServiceImpl implements IAdministratorService {
	
	@Autowired
	private AdministratorDao administratorDao;

	@Override
	public Administrator login(Administrator administrator) {
		administrator = administratorDao.login(administrator);
		return administrator;
	}

	@Override
	public List<Administrator> getAllAdministrator() {
		return administratorDao.getAllAdministrator();
	}

	@Override
	public int insert(Administrator administrator) {
		return administratorDao.insert(administrator);
	}

//	@Override
//	public int removeCustomerById(int userid) {
//		return userDao.removeUserById(userid);
//	}

	@Override
	public int updateAdm(Administrator administrator) {
		return administratorDao.updateAdm(administrator);
	}

	@Override
	public Administrator getAdministratorById(Administrator administrator) {
		return administratorDao.getAdministratorById(administrator);
	}

}
