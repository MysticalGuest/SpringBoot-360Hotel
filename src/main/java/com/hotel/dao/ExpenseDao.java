package com.hotel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hotel.entity.Expense;

@Mapper // 标记当前类为功能映射文件
public interface ExpenseDao {
	
	@Insert("insert into expense(kinds,price) values (#{kinds},#{price})")
	@Options(useGeneratedKeys = true, keyProperty = "kinds")
	int insert(Expense expense);

	//为了可以修改钟点房价格
	//我用expense表存了一个钟点房价格,所以查询其他商品时,我将钟点房除去
	@Select("select * from expense where kinds not in ('HourRoom');")
	List<Expense> getAllKinds();
	
	@Update("update expense set price=#{price} where kinds=#{kinds}")
	int updatePrice(Expense expense);
	
	//我用expense表存了一个钟点房价格,查询钟点房
	@Select("select price from expense where kinds = 'HourRoom';")
	int getHourRoom();
	
	@Update("update expense set price=#{price} where kinds = 'HourRoom'")
	int updateHourRoomPrice(Expense expense);

}
