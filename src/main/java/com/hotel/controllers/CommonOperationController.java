package com.hotel.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.custommethods.MyClass;
import com.hotel.entity.Apartment;
import com.hotel.entity.Customer;
import com.hotel.entity.Expense;

import net.sf.json.JSONObject;

import com.hotel.serviceimpl.ApartmentServiceImpl;
import com.hotel.serviceimpl.CustomerServiceImpl;
import com.hotel.serviceimpl.ExpenseServiceImpl;
import com.hotel.serviceimpl.BillServiceImpl;

@Controller
@RequestMapping("commonOperation")
public class CommonOperationController {
	private static final Logger LOG = Logger.getLogger(CommonOperationController.class);
	
	@Autowired
	private CustomerServiceImpl customerServiceimpl;
	
	@Autowired
	private ApartmentServiceImpl apartmentServiceimpl;
	
	@Autowired
	private BillServiceImpl billServiceimpl;
	
	@Autowired
	private ExpenseServiceImpl expenseServiceimpl;
	
	//顾客信息统计界面搜索功能
	@RequestMapping(value="/SearchCustomerInfo",method = RequestMethod.POST)
	public String SearchCustomerInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException, IOException{
		LOG.info("commonOperation/SearchCustomerInfo...");
		Customer customer = new Customer();
		String inTime = request.getParameter("datetime");
		System.out.println("inTime:"+inTime);
		customer.setinTime(inTime);
		String cName = request.getParameter("cName");
		System.out.println("cName:"+cName);
		customer.setcName(cName);
		String roomNum = request.getParameter("roomNum");
		System.out.println("roomNum:"+roomNum);
		customer.setroomNum(roomNum);
		
		List<Customer> customerList = customerServiceimpl.doSearch(customer);
		response.getWriter().print(customerList);
		return null;
	}
	
	//顾客信息统计界面显示全部功能
	@RequestMapping(value="/ShowAllCustomerInfo",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String ShowAllCustomerInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException, IOException{
		LOG.info("commonOperation/ShowAllCustomerInfo...");
		List<Customer> customerList = customerServiceimpl.getAllCustomer();
		response.getWriter().print(customerList);
		return null;
	}
	
	//客房管理搜索操作和显示全部操作
	@RequestMapping(value="/SearchApartmentManagement",method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public String SearchApartmentManagement(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		LOG.info("commonOperation/SearchApartmentManagement...");
		Apartment apartment = new Apartment();
		String roomNum = request.getParameter("roomNum");
		System.out.println("roomNum:"+roomNum);
		apartment.setroomNum(roomNum);
		//getParameter("")方法返回值为String类型
		String price = request.getParameter("price");
		System.out.println("priceStr:"+price);
		if(!price.equals("")){
			int aprice = Integer.parseInt(price);
			System.out.println("price:"+price);
			apartment.setPrice(aprice);
		}
		String state = request.getParameter("state");
		if(state.equals("false")||state.equals("true")){
			boolean booleanState = Boolean.parseBoolean(state); 
			System.out.println("state:"+booleanState);
			apartment.setState(booleanState);
		}
		System.out.println("stateStr:"+state);
		List<Apartment> apartmentList;
		if(!roomNum.equals("")&&!price.equals("")&&!state.equals("")){
			apartmentList = apartmentServiceimpl.searchApartment(apartment);
		}
		else if(roomNum.equals("")&&!price.equals("")&&!state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByPriceAndState(apartment);
		}
		else if(!roomNum.equals("")&&price.equals("")&&!state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByRoomAndState(apartment);
		}
		else if(!roomNum.equals("")&&!price.equals("")&&state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByRoomAndState(apartment);
		}
		else if(!roomNum.equals("")&&price.equals("")&&state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByRoomNum(apartment);
		}
		else if(roomNum.equals("")&&!price.equals("")&&state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByPrice(apartment);
		}
		else if(roomNum.equals("")&&price.equals("")&&!state.equals("")){
			apartmentList = apartmentServiceimpl.getApartmentByState(apartment);
		}
		else{
			apartmentList = apartmentServiceimpl.getAllApartment();
		}
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//客房管理表格中的退房操作
	@RequestMapping(value="/checkOut",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String checkOut(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("commonOperation/checkOut...");
		Apartment thisapartment = new Apartment();
		//区别前端界面是‘前台客房管理’还是‘管理员客房管理’
		String flag = request.getParameter("flag");
		System.out.println(flag);
		String roomNum = request.getParameter("roomNum");
		System.out.println(roomNum);
		
		thisapartment.setroomNum(roomNum);
		apartmentServiceimpl.checkOut(thisapartment);
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//客房管理多选退房操作
	@RequestMapping(value="/checkOutChecked",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String checkOutChecked(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("checkOutChecked...");
		//区别前端界面是‘前台客房管理’还是‘管理员客房管理’
		String flag = request.getParameter("flag");
		System.out.println(flag);
		String strRoom = request.getParameter("strRoom");
		System.out.println(strRoom);
		String[] roomArray = strRoom.split(","); // 用,分割
		for(String roomNum:roomArray){
			System.out.println(roomNum);
			LOG.info("CheckOut...");
			Apartment tempApartment = new Apartment();
			tempApartment.setroomNum(roomNum);
			apartmentServiceimpl.checkOut(tempApartment);
		}
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//客房管理全部退房操作
	@RequestMapping(value="/allCheckOut",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String allCheckOut(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("allCheckOut...");
		//区别前端界面是‘前台客房管理’还是‘管理员客房管理’
		String flag = request.getParameter("flag");
		System.out.println(flag);
		apartmentServiceimpl.allCheckOut();
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//账目统计界面
	@SuppressWarnings("deprecation")//为解决date.getDay()The method getDate() from the type Date is deprecated
	@RequestMapping(value="/Statistics",method = RequestMethod.GET)
	public String Statistics(HttpServletRequest request,HttpSession session) throws ParseException {
		LOG.info("commonOperation/Statistics...");
		Object flag = session.getAttribute("flag");
		request.setAttribute("flag", flag);
		System.out.println(flag);
		
		
		//其他消费单价列表
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
//		System.out.println("expenseList:"+expenseList);
		Map<String, Integer> priceList = new HashMap<String, Integer>();
		for (int i = 0; i < expenseList.size(); i++){
			//以下获得方法获得的都是价格
			priceList.put(expenseList.get(i).getKinds(), expenseList.get(i).getPrice());
		}
//		System.out.println("priceList:"+priceList);
		
		
		/***********************
			***===周营业额比较===**
			***********************/
		//获得本周内bill账单
		List<Map<String, Integer>> billThisWeekList = billServiceimpl.getTurnoverPerDayThisWeek();
		System.out.println("billThisWeekList:"+billThisWeekList);
		System.out.println("length:"+billThisWeekList.size());
		
		//将计算后的账单存起来
		Map<String, Integer> profitThisWeekList = MyClass.getExpenseForMapList(billThisWeekList, priceList);
		System.out.println("profitThisWeekList:"+profitThisWeekList);
		
		//将一周内日期存起来,与数据库取出来的对比
		List<String> dateList = new ArrayList<String>();
		//获得一周内7天日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        } 
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        for (int i=1;i<=7;i++) {
        	String tempDate = sdf.format(cal.getTime());
        	dateList.add(tempDate);
        	cal.add(Calendar.DATE, 1);
        }
        System.out.println("dateList:"+dateList);
        
        //获取每天的利润,并以这种[]形式存起来
        List<Integer> profitPerDayListThisWeek = new ArrayList<Integer>();
        //考虑到极端情况,某天的营业额为0,数据库不会有这天的记录,所以我要判断,将要传到前台的这天的数据置0,遍历本周内所有日期dateList,与数据库对比
        for(int i = 0; i < dateList.size(); i++){
        	if(profitThisWeekList.get(dateList.get(i))==null){
        		//极端情况,某天的营业额为0,数据库不会有这天的记录,我就加上记录,因为前台统计图表界面需要
        		profitPerDayListThisWeek.add(0);
        	}
        	else{
        		profitPerDayListThisWeek.add(profitThisWeekList.get(dateList.get(i)));
        	}
        }
        System.out.println("profitPerDayListThisWeek："+profitPerDayListThisWeek);
        //将本周每天营业额传到前台
        request.setAttribute("profitPerDayListThisWeek", profitPerDayListThisWeek);
        
        //获得上周bill账单
		List<Map<String, Integer>> billLastWeekList = billServiceimpl.getTurnoverPerDayLastWeek();
		System.out.println("billThisWeekList:"+billLastWeekList);
		System.out.println("length:"+billLastWeekList.size());
		
		//获得上周每一天
		cal = Calendar.getInstance();
		//调整到上周
        cal.add(Calendar.WEDNESDAY, -1);
        //调整到上周1
        cal.set(Calendar.DAY_OF_WEEK, 2);
        //将刚才存本周日期的列表变量清空来存上周日期
        dateList.clear();
        for (int i = 0; i < 7; i++) {
        	String tempDate = sdf.format(cal.getTime());
        	dateList.add(tempDate);
            cal.add(Calendar.DAY_OF_WEEK, 1);
        } 
        System.out.println("dateLastWeekList:"+dateList);
        
        //将计算后的账单存起来
      	Map<String, Integer> profitLastWeekList = MyClass.getExpenseForMapList(billLastWeekList, priceList);
		System.out.println("profitLastWeekList:"+profitLastWeekList);
		//获取每天的利润,并以这种[]形式存起来
        List<Integer> profitPerDayListLastWeek = new ArrayList<Integer>();
        //考虑到极端情况,某天的营业额为0,数据库不会有这天的记录,所以我要判断,将要传到前台的这天的数据置0,遍历本周内所有日期dateList,与数据库对比
        for(int i = 0; i < dateList.size(); i++){
        	if(profitLastWeekList.get(dateList.get(i))==null){
        		//极端情况,某天的营业额为0,数据库不会有这天的记录,我就加上记录,因为前台统计图表界面需要
        		profitPerDayListLastWeek.add(0);
        	}
        	else{
        		profitPerDayListLastWeek.add(profitLastWeekList.get(dateList.get(i)));
        	}
        }
        System.out.println("profitPerDayListLastWeek："+profitPerDayListLastWeek);
        //将本周每天营业额传到前台
        request.setAttribute("profitPerDayListLastWeek", profitPerDayListLastWeek);
        /***********************
			***===周营业额比较===结束**
			***********************/
        
        
        /***********************
			***===上月营业额比较===**
			***********************/
        List<Map<String, Integer>> billLastMonthList = billServiceimpl.getTurnoverPerDayLastMonth();
		System.out.println("billLastMonthList:"+billLastMonthList);
		System.out.println("billLastMonthListSize:"+billLastMonthList.size());
		
		//将计算后的账单存起来
      	Map<String, Integer> profitLastMonthList = MyClass.getExpenseForMapList(billLastMonthList, priceList);
		System.out.println("profitLastMonthList:"+profitLastMonthList);
		
		//得到billLastMonthList里面的日期,然后与一个月分成的1~7,8~15,16~23,24~31时间对比,计算出每个时间段内的营业额
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
		int profitOfFirstWeek=0;
		int profitOfSecondWeek=0;
		int profitOfThirdWeek=0;
		int profitOfFourthWeek=0;
		for(int i = 0 ;i<billLastMonthList.size(); i++){
			Date date = formatter.parse(String.valueOf(billLastMonthList.get(i).get("inDate")));
			switch(date.getDate()/8){
			case 0:profitOfFirstWeek+=profitLastMonthList.get(billLastMonthList.get(i).get("inDate"));
			break;
			case 1:profitOfSecondWeek+=profitLastMonthList.get(billLastMonthList.get(i).get("inDate"));
			break;
			case 2:profitOfThirdWeek+=profitLastMonthList.get(billLastMonthList.get(i).get("inDate"));
			break;
			case 3:profitOfFourthWeek+=profitLastMonthList.get(billLastMonthList.get(i).get("inDate"));
			break;
			}
		}
		//获取每周的利润,并以这种[]形式存起来
        List<Integer> profitPerWeekListLastMonth = new ArrayList<Integer>();
        profitPerWeekListLastMonth.add(profitOfFirstWeek);
        profitPerWeekListLastMonth.add(profitOfSecondWeek);
        profitPerWeekListLastMonth.add(profitOfThirdWeek);
        profitPerWeekListLastMonth.add(profitOfFourthWeek);
		System.out.println("profitPerWeekListLastMonth:"+ profitPerWeekListLastMonth);
		//将上月每周营业额传到前台
        //前台饼状图以JSON数据形式
        List<JSONObject>  JSONListLastMonth = new ArrayList<JSONObject>();
        for(int i=0;i<profitPerWeekListLastMonth.size();i++){
        	JSONObject jsonProfit = new JSONObject();
        	jsonProfit.put("value", profitPerWeekListLastMonth.get(i));
        	jsonProfit.put("name", "第"+(i+1)+"周");
        	JSONListLastMonth.add(jsonProfit);
        }
        System.out.println("JSONList:"+ JSONListLastMonth);
        request.setAttribute("JSONListLastMonth", JSONListLastMonth);
        /***********************
			***===上月营业额比较===结束**
			***********************/
        
        /***********************
			***===本月营业额比较===**
			***********************/
        List<Map<String, Integer>> billThisMonthList = billServiceimpl.getTurnoverPerWeekThisMonth();
		System.out.println("billThisMonthList:"+billThisMonthList);
		System.out.println("billThisMonthListSize:"+billThisMonthList.size());
		
		//将计算后的账单存起来
      	Map<String, Integer> profitThisMonthList = MyClass.getExpenseForMapList(billThisMonthList, priceList);
		System.out.println("profitThisMonthList:"+profitThisMonthList);
		
		//得到billLastMonthList里面的日期,然后与一个月分成的1~7,8~15,16~23,24~31时间对比,计算出每个时间段内的营业额
		profitOfFirstWeek=0;
		profitOfSecondWeek=0;
		profitOfThirdWeek=0;
		profitOfFourthWeek=0;
		for(int i = 0 ;i<billThisMonthList.size(); i++){
			Date date = formatter.parse(String.valueOf(billThisMonthList.get(i).get("inDate")));
			switch(date.getDate()/8){
			case 0:profitOfFirstWeek+=profitThisMonthList.get(billThisMonthList.get(i).get("inDate"));
			break;
			case 1:profitOfSecondWeek+=profitThisMonthList.get(billThisMonthList.get(i).get("inDate"));
			break;
			case 2:profitOfThirdWeek+=profitThisMonthList.get(billThisMonthList.get(i).get("inDate"));
			break;
			case 3:profitOfFourthWeek+=profitThisMonthList.get(billThisMonthList.get(i).get("inDate"));
			break;
			}
		}
		//获取每周的利润,并以这种[]形式存起来
        List<Integer> profitPerWeekListThisMonth = new ArrayList<Integer>();
        profitPerWeekListThisMonth.add(profitOfFirstWeek);
        profitPerWeekListThisMonth.add(profitOfSecondWeek);
        profitPerWeekListThisMonth.add(profitOfThirdWeek);
        profitPerWeekListThisMonth.add(profitOfFourthWeek);
		System.out.println("profitPerWeekListThisMonth:"+ profitPerWeekListThisMonth);
        //后台饼状图以JSON数据形式
        List<JSONObject>  JSONListThisMonth = new ArrayList<JSONObject>();
        for(int i=0;i<profitPerWeekListThisMonth.size();i++){
        	JSONObject jsonProfit = new JSONObject();
        	jsonProfit.put("value", profitPerWeekListThisMonth.get(i));
        	jsonProfit.put("name", "第"+(i+1)+"周");
        	JSONListThisMonth.add(jsonProfit);
        }
        System.out.println("JSONList:"+ JSONListThisMonth);
        //将本月每周营业额传到前台
        request.setAttribute("JSONListThisMonth", JSONListThisMonth);
        /***********************
			***===本月营业额比较===结束**
			***********************/
        
        /***********************
			***===今年每个季度营业额比较===**
			***********************/
        List<Map<String, Integer>> billThisYearList = billServiceimpl.getTurnoverPerQuarterThisYear();
        Map<String, Integer> profitPerQuarterThisYearList = MyClass.getExpenseForMapList(billThisYearList,priceList);
		System.out.println("billThisYearList:"+billThisYearList);
		System.out.println("billThisYearListSize:"+billThisYearList.size());
		System.out.println("profitPerQuarterThisYearList:"+profitPerQuarterThisYearList);
		//将计算后的账单与每个季度对应起来
	  	Map<String, Integer> profitPerQuarterMap = new HashMap<String, Integer>();
	  	//对其初始化,因为考虑到有的季度营业额为0
	  	profitPerQuarterMap.put("第1季度",0);
	  	profitPerQuarterMap.put("第2季度",0);
	  	profitPerQuarterMap.put("第3季度",0);
	  	profitPerQuarterMap.put("第4季度",0);
		//得到profitPerQuarterThisYearList里面的日期,然后与一个月分成的1~3,4~6,7~9,10~12月份对比,计算出每个季度内的营业额
		formatter = new SimpleDateFormat("yyyy-MM");
		for(int i = 0 ;i<billThisYearList.size(); i++){
			Date date = formatter.parse(String.valueOf(billThisYearList.get(i).get("inDate")));
			System.out.println("date:"+date);
			//这里getMonth方法得到的月份比现实月份小1,但我这里要把月份从0开始计,正好加1减1,就直接用这个方法了
			switch(date.getMonth()/3){
			case 0:profitPerQuarterMap.put("第"+(i+1)+"季度", profitPerQuarterThisYearList.get(billThisYearList.get(i).get("inDate")));
			break;
			case 1:profitPerQuarterMap.put("第"+(i+1)+"季度", profitPerQuarterThisYearList.get(billThisYearList.get(i).get("inDate")));
			break;
			case 2:profitPerQuarterMap.put("第"+(i+1)+"季度", profitPerQuarterThisYearList.get(billThisYearList.get(i).get("inDate")));
			break;
			case 3:profitPerQuarterMap.put("第"+(i+1)+"季度", profitPerQuarterThisYearList.get(billThisYearList.get(i).get("inDate")));
			break;
			}
		}
		System.out.println("profitPerQuarterMap:"+profitPerQuarterMap);
		//获取每季度的利润,并以这种[]形式存起来
        List<Integer> profitPerQuarterListThisYear = new ArrayList<Integer>();
        //考虑到极端情况,某天的营业额为0,数据库不会有这天的记录,所以我要判断,将要传到前台的这天的数据置0,遍历本周内所有日期dateList,与数据库对比
        for(int i = 0; i < profitPerQuarterMap.size(); i++){
        	profitPerQuarterListThisYear.add(profitPerQuarterMap.get("第"+(i+1)+"季度"));
        }
        System.out.println("profitPerQuarterListThisYear:"+profitPerQuarterListThisYear);
        //将今年每季度营业额传到前台
        request.setAttribute("profitPerQuarterListThisYear", profitPerQuarterListThisYear);
        /***********************
			***===今年每个季度营业额比较===结束**
			***********************/
		
		/***********************
			***===近年营业额比较===结束**
			***********************/
		List<Map<String, Integer>> billTheseYearsList = billServiceimpl.getTurnoverTheseYears();
		Map<String, Integer> profitTheseYearsList = MyClass.getExpenseForMapList(billTheseYearsList,priceList);
		System.out.println("billTheseYearsList:"+billTheseYearsList);
		System.out.println("billTheseYearsListSize:"+billTheseYearsList.size());
		System.out.println("profitTheseYearsList:"+profitTheseYearsList);
		//获取年号,并以这种[]形式存起来
        List<Integer> yearList = new ArrayList<Integer>();
        //考虑到极端情况,某天的营业额为0,数据库不会有这天的记录,所以我要判断,将要传到前台的这天的数据置0,遍历本周内所有日期dateList,与数据库对比
        for(int i = 0; i < billTheseYearsList.size(); i++){
        	yearList.add(Integer.parseInt(String.valueOf(billTheseYearsList.get(i).get("inDate"))));
        }
        //将年份从小到大排序
        yearList.sort(null);
        System.out.println("yearList:"+yearList);
        //将年份传到前台
        request.setAttribute("yearList", yearList);
        
        //获取每年利润,并以这种[]形式存起来
        List<Integer> profitPerYearList = new ArrayList<Integer>();
        //考虑到极端情况,某天的营业额为0,数据库不会有这天的记录,所以我要判断,将要传到前台的这天的数据置0,遍历本周内所有日期dateList,与数据库对比
        for(int i = 0; i < billTheseYearsList.size(); i++){
        	//yearList里的年份是Integer型,profitTheseYearsList.get()需要的参数是String型,所以用String.valueOf()先转为String型
        	profitPerYearList.add(profitTheseYearsList.get(String.valueOf(yearList.get(i))));
        }
        //将近几年营业额传到前台
        request.setAttribute("profitPerYearList", profitPerYearList);
        System.out.println("profitPerYearList:"+profitPerYearList);
		/***********************
			***===近年度营业额比较===结束**
			***********************/
		
		return "Statistics";
	}
	
}
