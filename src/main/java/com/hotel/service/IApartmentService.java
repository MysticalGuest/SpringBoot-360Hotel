package com.hotel.service;

import java.util.List;

import com.hotel.entity.Apartment;

public interface IApartmentService {
	
	int insert(Apartment apartment);

	Apartment login(Apartment apartment);

	List<Apartment> getAllApartment();
	
	List<Apartment> getSpareApartment();
	
	List<Apartment> getPrice();
	
	List<Apartment> searchApartment(Apartment apartment);
	
	List<Apartment> getApartmentByRoomNum(Apartment apartment);
	
	List<Apartment> getApartmentByPrice(Apartment apartment);
	
	List<Apartment> getApartmentByState(Apartment apartment);
	
	List<Apartment> getApartmentByPriceAndState(Apartment apartment);
	
	List<Apartment> getApartmentByRoomAndState(Apartment apartment);
	
	List<Apartment> getApartmentByRoomAndPrice(Apartment apartment);

	int ResetPrice(Apartment apartment);
	
	int checkOut(Apartment apartment);
	
	int checkIn(Apartment apartment);
	
	int allCheckOut();

}
