package com.hotel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hotel.entity.Apartment;

@Mapper // 标记当前类为功能映射文件
public interface ApartmentDao {
	@Insert("insert into apartment (roomNum,price) values (#{roomNum},#{price})")
	@Options(useGeneratedKeys = true, keyProperty = "roomNum")
	int insert(Apartment apartment);

	@Select("select * from apartment where roomNum=#{roomNum}")
	Apartment login(Apartment apartment);

	@Select("select * from apartment")
	List<Apartment> getAllApartment();
	
	@Select("select * from apartment where state=false")
	List<Apartment> getSpareApartment();
	
	@Select("select distinct price from apartment order by price asc")
	List<Apartment> getPrice();
	
	@Select("select * from apartment where roomNum like '%${roomNum}%' and price=#{price} and state=#{state}")
	List<Apartment> searchApartment(Apartment apartment);
	
	@Select("select * from apartment where roomNum like '%${roomNum}%'")
	List<Apartment> getApartmentByRoomNum(Apartment apartment);
	
	@Select("select * from apartment where price=#{price}")
	List<Apartment> getApartmentByPrice(Apartment apartment);
	
	@Select("select * from apartment where state=#{state}")
	List<Apartment> getApartmentByState(Apartment apartment);
	
	@Select("select * from apartment where price=#{price} and state=#{state}")
	List<Apartment> getApartmentByPriceAndState(Apartment apartment);
	
	@Select("select * from apartment where roomNum like '%${roomNum}%' and state=#{state}")
	List<Apartment> getApartmentByRoomAndState(Apartment apartment);
	
	@Select("select * from apartment where roomNum like '%${roomNum}%' and price=#{price}")
	List<Apartment> getApartmentByRoomAndPrice(Apartment apartment);

	@Update("update apartment set price=#{price} where roomNum=#{roomNum}")
	int ResetPrice(Apartment apartment);
	
	@Update("update apartment set state=false where roomNum=#{roomNum}")
	int checkOut(Apartment apartment);
	
	@Update("update apartment set state=true where roomNum=#{roomNum}")
	int checkIn(Apartment apartment);
	
	@Update("update apartment set state=false")
	int allCheckOut();

}

