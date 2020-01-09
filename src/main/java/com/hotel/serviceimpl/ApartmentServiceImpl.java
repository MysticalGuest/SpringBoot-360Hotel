package com.hotel.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ApartmentDao;
import com.hotel.entity.Apartment;
import com.hotel.service.IApartmentService;

@Service // 标记当前类是service
public class ApartmentServiceImpl implements IApartmentService {
	@Autowired
	private ApartmentDao apartmentDao;

	@Override
	public Apartment login(Apartment apartment) {
		return null;
	}

	@Override
	public List<Apartment> getAllApartment() {
		return apartmentDao.getAllApartment();
	}
	
	@Override
	public List<Apartment> getSpareApartment() {
		return apartmentDao.getSpareApartment();
	}
	
	@Override
	public List<Apartment> getPrice() {
		return apartmentDao.getPrice();
	}
	
	@Override
	public List<Apartment> searchApartment(Apartment apartment) {
		return apartmentDao.searchApartment(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByRoomNum(Apartment apartment) {
		return apartmentDao.getApartmentByRoomNum(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByPrice(Apartment apartment) {
		return apartmentDao.getApartmentByPrice(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByState(Apartment apartment) {
		return apartmentDao.getApartmentByState(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByPriceAndState(Apartment apartment) {
		return apartmentDao.getApartmentByPriceAndState(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByRoomAndState(Apartment apartment) {
		return apartmentDao.getApartmentByRoomAndState(apartment);
	}
	
	@Override
	public List<Apartment> getApartmentByRoomAndPrice(Apartment apartment) {
		return apartmentDao.getApartmentByRoomAndPrice(apartment);
	}

	@Override
	public int insert(Apartment apartment) {
		return apartmentDao.insert(apartment);
	}

//	@Override
//	public int removeCustomerById(int userid) {
//		return userDao.removeUserById(userid);
//	}
//
	@Override
	public int ResetPrice(Apartment apartment) {
		return apartmentDao.ResetPrice(apartment);
	}
	
	@Override
	public int checkOut(Apartment apartment) {
		return apartmentDao.checkOut(apartment);
	}
	
	@Override
	public int checkIn(Apartment apartment) {
		return apartmentDao.checkIn(apartment);
	}
	
	@Override
	public int allCheckOut() {
		return apartmentDao.allCheckOut();
	}

//	@Override
//	public Customer getCustomerById(Customer customer) {
//		return customerDao.getCustomerById(customer);
//	}

}
