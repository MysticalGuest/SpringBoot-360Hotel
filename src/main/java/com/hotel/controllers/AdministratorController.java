package com.hotel.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.entity.Administrator;
import com.hotel.entity.Apartment;
import com.hotel.entity.Customer;
import com.hotel.entity.Expense;
import com.hotel.entity.Bill;
//import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.hotel.controllers.AdministratorController;
import com.hotel.custommethods.MyClass;
import com.hotel.serviceimpl.AdministratorServiceImpl;
import com.hotel.serviceimpl.ApartmentServiceImpl;
import com.hotel.serviceimpl.CustomerServiceImpl;
import com.hotel.serviceimpl.ExpenseServiceImpl;
import com.hotel.serviceimpl.BillServiceImpl;

@Controller
@RequestMapping("administrator")
public class AdministratorController {
	private static final Logger LOG = Logger.getLogger(AdministratorController.class);
	@Autowired
	private AdministratorServiceImpl administratorServiceimpl;
	
	@Autowired
	private CustomerServiceImpl customerServiceimpl;
	
	@Autowired
	private ApartmentServiceImpl apartmentServiceimpl;
	
	@Autowired
	private BillServiceImpl billServiceimpl;
	
	@Autowired
	private ExpenseServiceImpl expenseServiceimpl;
	
	@RequestMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		System.out.println("hello...");
		return "hello";
	}
	
	//首页
	@GetMapping("/HomeForAdm")
	public String HomeForAdm(HttpSession session) throws JsonProcessingException{
		LOG.info("administrator/HomeForAdm...");
		return "HomeForAdm";
	}
	
	//顾客信息管理界面
	@GetMapping("/CustomerInfoForAdm")
	public String CustomerInfoForAdm(HttpSession session) {
		LOG.info("administrator/CustomerInfoForAdm...");
		List<Customer> customerList = customerServiceimpl.getAllCustomer();
		session.setAttribute("customerList", customerList);
		return "CustomerInfoForAdm";
	}
	
	//顾客信息管理界面接收数据
	@RequestMapping(value="/CustomerInfoForAdm",method = RequestMethod.POST)
	public String CustomerInfoForAdmPOST(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("administrator/CustomerInfoForAdmPOST...");
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
		System.out.println(customerList);
		return null;
	}
	
	//管理员顾客信息统计界面删除选中功能
	@RequestMapping(value="/deleteChecked",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String deleteChecked(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, ParseException {
		LOG.info("administrator/deleteChecked...");
		String strInTime = request.getParameter("strInTime");
		System.out.println(strInTime);
		String[] strInTimeArray = strInTime.split(","); // 用,分割
		for(String inTime:strInTimeArray){
			System.out.println(strInTime);
			LOG.info("Delete...");
			//bill表的inTime是customer的外键，所以先删bill表的inTime
			Bill bill = new Bill();
			bill.setInTime(inTime);
			billServiceimpl.delete(bill);
			
			Customer customer = new Customer();
			customer.setinTime(inTime);;
			customerServiceimpl.removeCustomerById(customer);
		}
		
		List<Customer> customerList = customerServiceimpl.getAllCustomer();
		response.getWriter().print(customerList);
		return null;
	}
	
	//综合管理界面
	@GetMapping("/IntegratedManagement")
	public String FrontManagement(HttpServletRequest request,HttpSession session) {
		LOG.info("administrator/IntegratedManagement...");
		//front表格数据
		List<Administrator> admList = administratorServiceimpl.getAllAdministrator();
		session.setAttribute("admList", admList);
		//钟点房价格
		int hourRoomPrice = expenseServiceimpl.getHourRoom();
		System.out.println("hourRoomPrice:"+hourRoomPrice);
		request.setAttribute("hourRoomPrice", hourRoomPrice);
		
		//expense表格数据
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
		session.setAttribute("expenseList", expenseList);
		System.out.println("expenseList:"+expenseList);
		return "IntegratedManagement";
	}
	
	//综合管理界面,这个是前台formatSex(value,row,index)函数需要的后台映射方法，为了解决管理员在改变性别时,后台数据收到
	//但改变后的数据前台界面未能及时收到的情况,用了这个方法用ajax实时接收数据
	@PostMapping("/IntegratedManagement")
	public String IntegratedManagementPOST(HttpSession session,HttpServletResponse response) throws IOException{
		LOG.info("administrator/IntegratedManagementPOST...");
		List<Administrator> admList = administratorServiceimpl.getAllAdministrator();
		response.getWriter().print(admList);
		System.out.println(admList);
		return null;
	}
	
	//综合管理界面,表格左边栏,对钟点房价格进行编辑
	@PostMapping("/hourRoomPrice")
	@ResponseBody
	public int hourRoomPrice(HttpServletRequest request,HttpServletResponse response) {
		LOG.info("administrator/hourRoomPrice...");
		Expense expense = new Expense();
		String hourRoomPrice = request.getParameter("hourRoomPrice");
		int aprice = Integer.parseInt(hourRoomPrice);
		System.out.println("aprice:"+aprice);
		expense.setPrice(aprice);
		
		//更改价格
		expenseServiceimpl.updateHourRoomPrice(expense);
		//再重新从数据库拿数据
		int newhourRoomPrice = expenseServiceimpl.getHourRoom();
		//传到前台
		return newhourRoomPrice;
	}
	
	//综合管理界面,编辑其他消费的价格
	@PostMapping("/ResetExpense")
	public List<Expense> ResetExpense(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException{
		LOG.info("administrator/ResetExpense...");
		String kind = request.getParameter("kind");
		System.out.println(kind);
		
		String price = request.getParameter("price");
		int aprice = Integer.parseInt(price);
		System.out.println(price);
		
		Expense expense = new Expense();
		expense.setKinds(kind);
		expense.setPrice(aprice);
		expenseServiceimpl.updatePrice(expense);
		
		//expense表格数据
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
//		response.getWriter().print(expenseList);
		return expenseList;
//		return null;
	}
	
	//客房管理界面
	@GetMapping("/ApartmentManageAdm")
	public String ApartmentManageAdm(HttpSession session) throws JsonProcessingException{
		LOG.info("administrator/ApartmentManageAdm...");
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		session.setAttribute("apartmentList", apartmentList);
		System.out.println("apartmentList:"+apartmentList);
		
		//房价搜索框
		List<Apartment> priceList = apartmentServiceimpl.getPrice();
		List<JSONObject> priceJSON = new ArrayList<JSONObject>();
		
		for (int i = 0; i < priceList.size(); i++){
			JSONObject aPrice=new JSONObject();
			aPrice.put("price",priceList.get(i).getPrice());
			aPrice.put("num",i+1);
			priceJSON.add(aPrice);
		}
		ObjectMapper mapperPrice = new ObjectMapper();
		String jsonPrice = mapperPrice.writeValueAsString(priceJSON);
		session.setAttribute("jsonPrice", jsonPrice);
		System.out.println("Price"+jsonPrice);
		return "ApartmentManageAdm";
	}
	
	//客房管理界面多选修改房价操作
	@PostMapping("/resetPriceChecked")
	public String resetPriceChecked(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("administrator/resetPriceChecked...");
		String strRoom = request.getParameter("strRoom");
		System.out.println(strRoom);
		String price = request.getParameter("price");
		int aprice = Integer.parseInt(price);
		System.out.println(price);
		String[] roomArray = strRoom.split(","); // 用,分割
		for(String roomNum:roomArray){
			System.out.println(roomNum);
			LOG.info("resetPriceChecked...");
			Apartment tempApartment = new Apartment();
			tempApartment.setroomNum(roomNum);
			tempApartment.setPrice(aprice);
			apartmentServiceimpl.ResetPrice(tempApartment);
		}
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//客房管理界面重置房间价格操作
	@PostMapping("/ResetPrice")
	public String ResetPrice(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("administrator/ResetPrice...");
		Apartment thisapartment = new Apartment();
		String price = request.getParameter("price");
		int aprice = Integer.parseInt(price);
		System.out.println(price);
		String roomNum = request.getParameter("roomNum");
		System.out.println(roomNum);
		
		thisapartment.setroomNum(roomNum);
		thisapartment.setPrice(aprice);
		apartmentServiceimpl.ResetPrice(thisapartment);
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//前台管理界面修改前台信息操作
	@PostMapping("/ResetFrontInfo")
	public String ResetFrontInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		LOG.info("administrator/ResetFrontInfo...");
		Administrator thisadministrator = new Administrator();
		String admId = request.getParameter("admId");
		System.out.println(admId);
		String aName = request.getParameter("aName");
		System.out.println(aName);
		String aPassword = request.getParameter("aPassword");
		String aSex = request.getParameter("aSex");
		if(aSex.equals("man"))
			aSex="男";
		else if(aSex.equals("woman"))
			aSex="女";
		
		thisadministrator.setAdmId(admId);
		thisadministrator.setaName(aName);
		thisadministrator.setaPassword(aPassword);
		thisadministrator.setaSex(aSex);
		administratorServiceimpl.updateAdm(thisadministrator);
		
		List<Administrator> admList = administratorServiceimpl.getAllAdministrator();
		response.getWriter().print(admList);
		return null;
	}
	
	//账目详计页面
	@RequestMapping(value="/Account",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@GetMapping()
	public String Account(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		LOG.info("administrator/Account...");
		
		/***********************
			***===页面的统计数据===**
			***********************/
		//开出的发票数量
		int numOfBill = customerServiceimpl.getNumOfBill();
		System.out.println("numOfBill:"+numOfBill+"张");
		request.setAttribute("numOfBill", numOfBill);
		int numOfRoom;
		int SumOfFee;
		int profit;
		//如果开出的房间为空，SumOfFee = customerServiceimpl.getSumOfFeePerDay()会出错
		//select语句返回为空,'空'并不是int型
		if(numOfBill==0){
			numOfRoom=0;
			SumOfFee=0;
			profit=0;
		}
		else{
			//开出的房间数量
			List<String> roomList = customerServiceimpl.getNumOfRoom();
			System.out.println("roomList:"+roomList);
			String roomString=roomList.toString();
			String[] roomArray = roomString.split(","); // 用,分割
			numOfRoom=roomArray.length;
			//截止目前的营业额
			SumOfFee = customerServiceimpl.getSumOfFee();
			//截止目前的净营业额
			Customer allRoom = new Customer();
			roomString=roomString.replace("[", "(");//替换前roomString是[6012,6011, 6016,8014,8023, 6008,8012]
			roomString=roomString.replace("]", ")");//数据库读不出[]所以()替换
			allRoom.setroomNum(roomString);
			System.out.println("allRoom:"+allRoom.getroomNum());
			profit = customerServiceimpl.profit(allRoom);
		}
		
		System.out.println("numOfRoom:"+numOfRoom+"间");
		request.setAttribute("numOfRoom", numOfRoom);
		
		System.out.println("SumOfFee:"+SumOfFee+"元");
		request.setAttribute("ChargeAndDeposit", SumOfFee);
		//营业额
		System.out.println("profit:"+profit+"元");
		request.setAttribute("profit", profit);
		//押金
		int deposit = SumOfFee-profit;
		System.out.println("deposit:"+deposit+"元");
		request.setAttribute("deposit", deposit);
		//其他消费
		//其他消费单价列表
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
		
		Map<String, Integer> priceList = new HashMap<String, Integer>();
		for (int i = 0; i < expenseList.size(); i++){
			//一下获得方法获得的都是价格
			priceList.put(expenseList.get(i).getKinds(), expenseList.get(i).getPrice());
		}
		
		//其他消费数量列表
		Map<String, Integer> sumList = billServiceimpl.getPerKindsSum();
		System.out.println("sumList:"+sumList);
		
		int total = MyClass.getExpenseTotalConsumption(sumList, priceList);
		System.out.println("total:"+total+"元");
		request.setAttribute("total", total);
		/***********************
			***===页面的统计数据===结束**
			***********************/
		
		
		/***********************
			***===表格数据===**
			***********************/
		//算账每个客人的房费
		List<Map<String, Integer>> roomChargeList = billServiceimpl.getRoomChargePerCustomer();
		System.out.println("roomChargeList:"+roomChargeList);
		//将数据转为JSON格式,易于获取,jsonCharge中存的是每个顾客开的所有房间的费用之和
		JSONObject jsonCharge = new JSONObject();
		for(int i=0;i<roomChargeList.size();i++){
			jsonCharge.put(roomChargeList.get(i).get("inTime"), roomChargeList.get(i).get("sumPrice"));
		}
		System.out.println("jsonCharge:"+jsonCharge);
		
		//表格billList
		List<Bill> billList = billServiceimpl.getBill();
		
		//调用我写的公共方法
		MyClass.mergenceOfTotalConsumptionPerCustomer(billList,priceList,jsonCharge);
		System.out.println("billList:"+billList);
		session.setAttribute("billJSONObject", billList);
		/***********************
			***===页面的统计数据===结束**
			***********************/
		
		
		return "Account";
	}
	
	// 账目统计界面
	@GetMapping("/Statistics")
	public String Statistics(HttpSession session) {
		LOG.info("administrator/Statistics...");
		// 定向到CommonOperationController层
		session.setAttribute("flag", "administrator");
		return "redirect:../commonOperation/Statistics";
	}
	
}
