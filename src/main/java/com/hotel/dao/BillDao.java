package com.hotel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hotel.entity.Bill;

@Mapper // 标记当前类为功能映射文件
public interface BillDao {
	
	@Insert("insert into bill(roomNum,inTime) values (#{roomNum},#{inTime})")
	@Options(useGeneratedKeys = true, keyProperty = "billId")
	int insert(Bill bill);

	@Delete("delete from bill where inTime=#{inTime}")
	int delete(Bill bill);

	@Select("select cName,chargeAndDeposit,bill.* from bill,customer "
			+ "where date(bill.inTime)=curdate() and bill.inTime=customer.inTime order by bill.inTime desc;")
	List<Bill> getBillPerDay();
	
	@Select("select cName,chargeAndDeposit,bill.* from bill,customer where bill.inTime=customer.inTime order by bill.inTime desc;")
	List<Bill> getBill();
	
	@Update("update bill set mineral=#{mineral},pulsation=#{pulsation},"
			+ "greenTea=#{greenTea},tea=#{tea},noodles=#{noodles},WLJJDB=#{WLJJDB} "
			+ "where roomNum=#{roomNum}")
	int updateExpense(Bill bill);
	
	@Select("select "
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB "
			+ "from bill where date(inTime)=curdate()")
	Map<String, Integer> getPerKindsSumPerDay();
	
	@Select("select "
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB "
			+ "from bill")
	Map<String, Integer> getPerKindsSum();
	
	@Select("select inTime,sum(price) sumPrice from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and date(inTime)=curdate() group by inTime")
	List<Map<String, Integer>> getRoomChargePerCustomerPerDay();
	
	@Select("select inTime,sum(price) sumPrice from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum group by inTime")
	List<Map<String, Integer>> getRoomChargePerCustomer();
	
	@Select("select "
			+ "date_format(inTime,'%Y-%m-%d') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and yearweek(date_format(inTime,'%Y-%m-%d'),1)=yearweek(now(),1) "
			+ "group by date_format(inTime,'%Y-%m-%d');")
	List<Map<String, Integer>> getTurnoverPerDayThisWeek();
	
	@Select("select "
			+ "date_format(inTime,'%Y-%m-%d') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and yearweek(date_format(inTime,'%Y-%m-%d'),1)=yearweek(now(),1)-1 "
			+ "group by date_format(inTime,'%Y-%m-%d');")
	List<Map<String, Integer>> getTurnoverPerDayLastWeek();
	
	@Select("select "
			+ "date_format(inTime,'%Y-%m-%d') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and date_format(inTime,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m') "
			+ "group by date_format(inTime,'%Y-%m-%d');")
	List<Map<String, Integer>> getTurnoverPerDayLastMonth();
	
	@Select("select "
			+ "date_format(inTime,'%Y-%m-%d') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and date_format(inTime,'%Y-%m')=date_format(CURDATE(),'%Y-%m') "
			+ "group by date_format(inTime,'%Y-%m-%d');")
	List<Map<String, Integer>> getTurnoverPerWeekThisMonth();
	
	@Select("select "
			+ "date_format(inTime,'%Y-%m') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment "
			+ "where bill.roomNum=apartment.roomNum and date_format(inTime,'%Y')>year(now())-1 "
			+ "group by concat(date_format(inTime, '%Y'),FLOOR((date_format(inTime, '%m')+2)/3)) "
			+ "order by inTime asc;")
	List<Map<String, Integer>> getTurnoverPerQuarterThisYear();
	
	@Select("select "
			+ "date_format(inTime,'%Y') inDate,"
			+ "case when sum(tea) is null then 0 else sum(tea) end as numOfTea,"
			+ "case when sum(greenTea) is null then 0 else sum(greenTea) end as numOfGreenTea,"
			+ "case when sum(mineral) is null then 0 else sum(mineral) end as numOfMineral,"
			+ "case when sum(noodles) is null then 0 else sum(noodles) end as numOfNoodles,"
			+ "case when sum(pulsation) is null then 0 else sum(pulsation) end as numOfPulsation,"
			+ "case when sum(WLJJDB) is null then 0 else sum(WLJJDB) end as numOfWLJJDB,"
			+ "case when sum(price) is null then 0 else sum(price) end as turnover "
			+ "from bill,apartment where bill.roomNum=apartment.roomNum and date_format(inTime,'%Y')>year(now())-5 "
			+ "group by concat(date_format(inTime, '%Y'));")
	List<Map<String, Integer>> getTurnoverTheseYears();
	
}
