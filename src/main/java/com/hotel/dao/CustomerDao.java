package com.hotel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.hotel.entity.Customer;

@Mapper // 标记当前类为功能映射文件
public interface CustomerDao {
	@Insert("insert into customer (inTime,cName,cardID,roomNum,chargeAndDeposit,paymentMethod) "
			+ "values (#{inTime},#{cName},#{cardID},#{roomNum},#{chargeAndDeposit},#{paymentMethod})")
	@Options(useGeneratedKeys = true, keyProperty = "inTime")
	int insert(Customer customer);

	@Select("select * from customer order by inTime desc")
	List<Customer> getAllCustomer();
	
	@Select("select * from customer where inTime like '%${inTime}%' and cName like '%${cName}%' and roomNum like '%${roomNum}%' order by inTime desc")
	List<Customer> doSearch(Customer customer);

	@Delete("delete from customer where inTime=#{inTime}")
	int removeCustomerById(Customer Customer);
	
	@Select("select * from customer where inTime=#{inTime}")
	Customer getCustomerById(Customer customer);
	
	@Select("select count(*) from customer where date(inTime) = curdate();")
	int getNumOfBillPerDay();
	
	@Select("select count(*) from customer;")
	int getNumOfBill();
	
	@Select("select roomNum from customer where date(inTime) = curdate();")
	List<String> getNumOfRoomPerDay();
	
	@Select("select roomNum from customer;")
	List<String> getNumOfRoom();
	
	@Select("select sum(chargeAnddeposit) from customer where date(inTime)=curdate();")
	int getSumOfFeePerDay();
	
	@Select("select sum(chargeAnddeposit) from customer;")
	int getSumOfFee();
	
	@Select("select sum(price) from apartment where roomNum in ${roomNum};")
	int profit(Customer customer);

}

