package com.hotel.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
//import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.custommethods.MyClass;
//import com.hotel.entity.Administrator;
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
//import com.hotel.serviceimpl.AdministratorServiceImpl;
import com.hotel.serviceimpl.ApartmentServiceImpl;
import com.hotel.serviceimpl.CustomerServiceImpl;
import com.hotel.serviceimpl.ExpenseServiceImpl;
import com.hotel.serviceimpl.BillServiceImpl;

@Controller
@RequestMapping("front")
public class FrontController {
	private static final Logger LOG = Logger.getLogger(FrontController.class);
	
	@Autowired
	private CustomerServiceImpl customerServiceimpl;
	
	@Autowired
	private ApartmentServiceImpl apartmentServiceimpl;
	
	@Autowired
	private BillServiceImpl billServiceimpl;
	
	@Autowired
	private ExpenseServiceImpl expenseServiceimpl;
	
	//首页界面
	@RequestMapping(value="/Home",produces="text/plain;charset=UTF-8",method = RequestMethod.GET)
	public String Home(HttpSession session) throws JsonProcessingException{
		LOG.info("front/Home...");
		return "Home";
	}
	
	//顾客信息管理界面
	@RequestMapping(value="/CustomerInfoForFront",method = RequestMethod.GET)
	public String CustomerInfoForFront(HttpServletRequest request,HttpSession session) throws JsonProcessingException, ParseException{
		LOG.info("front/CustomerInfoForFront...");
		List<Customer> customerList = customerServiceimpl.getAllCustomer();
		session.setAttribute("customerList", customerList);
		System.out.println("customerList:"+customerList);
		return "CustomerInfoForFront";
	}
	
	//打印发票界面
	@RequestMapping(value="/Bill",method = RequestMethod.GET)
	public String Bill(HttpServletRequest request,HttpSession session) throws JsonProcessingException{
		LOG.info("front/Bill...");
		//房号下拉框
		List<Apartment> apartmentList = apartmentServiceimpl.getSpareApartment();
		session.setAttribute("apartmentList", apartmentList);
		
		//钟点房房价
		int hourRoomPrice = expenseServiceimpl.getHourRoom();
		request.setAttribute("hourRoomPrice", hourRoomPrice);
		return "Bill";
	}
	
	//发票打印后接收前台数据数据
	@RequestMapping(value="/Bill",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String BillPOST(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		LOG.info("front/BillPOST...");
		Customer customer = new Customer();
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate=dateFormat.format(date);
		customer.setinTime(strDate);
		System.out.println(strDate);
		
		String paymentMethod = request.getParameter("paymentMethod");
		customer.setPaymentMethod(paymentMethod);
		System.out.println("paymentMethod:"+paymentMethod);
		
		String cardID = request.getParameter("cardID");
		
		System.out.println("cardID:"+cardID);
		//如果cardID里带有英文,比如最后一位为x,直接传到前台会出错,现将其处理一下
		if(cardID==""){
			cardID=null;
		}
		else{
			if(((cardID.charAt(cardID.length()-1)=='x')||(cardID.charAt(cardID.length()-1)=='X')))
				cardID="\""+cardID+"\"";
		}
		customer.setcardID(cardID);
		System.out.println("cardID:"+cardID);
		
		String cName = request.getParameter("cName");
		customer.setcName(cName);
		System.out.println("cName:"+cName);
		
		String room = request.getParameter("roomNum");
		customer.setroomNum(room);
		System.out.println("Room is :"+room);
		
		String chargeAndDeposit = request.getParameter("chargeAndDeposit");
		int fee = Integer.parseInt(chargeAndDeposit);
		customer.setChargeAndDeposit(fee);
		System.out.println("fee:"+fee);
		
		List<Apartment> apartmentList = apartmentServiceimpl.getAllApartment();
		String[] roomArray = room.split(","); // 用,分割
		for(String roomNum:roomArray){
			System.out.println(roomNum);
			
			//对开出的房间进行开房处理
			LOG.info("CheckIn...");
			Apartment thisapartment = new Apartment();
			thisapartment.setroomNum(roomNum);
			apartmentServiceimpl.checkIn(thisapartment);
			System.out.println(thisapartment);
		}
		System.out.println(customer);
		customerServiceimpl.insert(customer);
		for(String roomNum:roomArray){
			//对每个开出的房间进行bill登记
			LOG.info("recordBill...");
			Bill bill = new Bill();
			bill.setRoomNum(roomNum);
			bill.setInTime(strDate);
			billServiceimpl.insert(bill);
		}
		//房号树形下拉框
		apartmentList = apartmentServiceimpl.getSpareApartment();
		response.getWriter().print(apartmentList);
		return null;
	}
	
	//客房管理界面
	@RequestMapping(value="/ApartmentManagement",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String ApartmentManagement(HttpSession session) throws JsonProcessingException{
		LOG.info("front/ApartmentManagement...");
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
		
		return "ApartmentManagement";
	}
	
	//账目管理界面
	@RequestMapping(value="/AccountOfPerDay",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String AccountOfPerDay(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, ParseException{
		LOG.info("front/AccountOfPerDay...");
		//开出的发票数量
		int numOfBill = customerServiceimpl.getNumOfBillPerDay();
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
			List<String> roomList = customerServiceimpl.getNumOfRoomPerDay();
			System.out.println("roomList:"+roomList);
			String roomString=roomList.toString();
			String[] roomArray = roomString.split(","); // 用,分割
			numOfRoom=roomArray.length;
			//截止目前的营业额
			SumOfFee = customerServiceimpl.getSumOfFeePerDay();
			//截止目前的净营业额
			Customer allRoomPerDay = new Customer();
			roomString=roomString.replace("[", "(");//替换前roomString是[6012,6011, 6016,8014,8023, 6008,8012]
			roomString=roomString.replace("]", ")");//数据库读不出[]所以()替换
			allRoomPerDay.setroomNum(roomString);
			System.out.println("allRoomPerDay:"+allRoomPerDay.getroomNum());
			profit = customerServiceimpl.profit(allRoomPerDay);
		}
		
		System.out.println("numOfRoom:"+numOfRoom+"间");
		request.setAttribute("numOfRoom", numOfRoom);
		
		System.out.println("SumOfFee:"+SumOfFee+"元");
		request.setAttribute("ChargeAndDeposit", SumOfFee);
		
		System.out.println("profit:"+profit+"元");
		request.setAttribute("profit", profit);
		//押金
		int deposit = SumOfFee-profit;
		System.out.println("deposit:"+deposit+"元");
		request.setAttribute("deposit", deposit);
		//其他消费
		//其他消费单价列表
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
//		System.out.println("expenseList:"+expenseList);
		Map<String, Integer> priceList = new HashMap<String, Integer>();
		for (int i = 0; i < expenseList.size(); i++){
			//一下获得方法获得的都是价格
			priceList.put(expenseList.get(i).getKinds(), expenseList.get(i).getPrice());
		}
//		System.out.println("priceList:"+priceList);
		
		//其他消费数量列表
		Map<String, Integer> sumList = billServiceimpl.getPerKindsSumPerDay();
		System.out.println("sumList:"+sumList);
		
		int total = MyClass.getExpenseTotalConsumption(sumList, priceList);
		System.out.println("total:"+total+"元");
		request.setAttribute("total", total);
		
		//算账每个客人的房费
		List<Map<String, Integer>> roomChargeList = billServiceimpl.getRoomChargePerCustomerPerDay();
		System.out.println("roomChargeList:"+roomChargeList);
		//将数据转为JSON格式,易于获取
		JSONObject jsonCharge = new JSONObject();
		for(int i=0;i<roomChargeList.size();i++){
			jsonCharge.put(roomChargeList.get(i).get("inTime"), roomChargeList.get(i).get("sumPrice"));
		}
		System.out.println("jsonCharge:"+jsonCharge);
		
		//表格billList
		List<Bill> billList = billServiceimpl.getBillPerDay();

		MyClass.mergenceOfTotalConsumptionPerCustomer(billList,priceList,jsonCharge);
		session.setAttribute("billList", billList);
		System.out.println("billList:"+billList);
		
		
		return "AccountOfPerDay";
	}
	
	//账目管理界面编辑其他消费并计算消费额
	@RequestMapping(value="/doAccounts",method = RequestMethod.POST)
	public String doAccounts(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		LOG.info("front/doAccounts...");
		Bill bill = new Bill();
		
		String roomNum = request.getParameter("roomNum");
		System.out.println("roomNum:"+roomNum);
		bill.setRoomNum(roomNum);
		
		String mineral = request.getParameter("mineralNum");
		if(mineral.equals(""))
			mineral=null;
		System.out.println("mineral:"+mineral);
		bill.setMineral(mineral);
		
		String pulsation = request.getParameter("pulsationNum");
		if(pulsation.equals(""))
			pulsation=null;
		System.out.println("pulsation:"+pulsation);
		bill.setPulsation(pulsation);
		
		String greenTea = request.getParameter("greenTeaNum");
		if(greenTea.equals(""))
			greenTea=null;
		System.out.println("greenTea:"+greenTea);
		bill.setGreenTea(greenTea);
		
		String tea = request.getParameter("teaNum");
		if(tea.equals(""))
			tea=null;
		System.out.println("tea:"+tea);
		bill.setTea(tea);
		
		String noodles = request.getParameter("noodlesNum");
		if(noodles.equals(""))
			noodles=null;
		System.out.println("noodles:"+noodles);
		bill.setNoodles(noodles);
		
		String WLJJDB = request.getParameter("WLJJDBNum");
		if(WLJJDB.equals(""))
			WLJJDB=null;
		System.out.println("WLJJDB:"+WLJJDB);
		bill.setWLJJDB(WLJJDB);
		billServiceimpl.updateExpense(bill);
		
		//其他消费单价列表
		List<Expense> expenseList = expenseServiceimpl.getAllKinds();
		Map<String, Integer> priceList = new HashMap<String, Integer>();
		for (int i = 0; i < expenseList.size(); i++){
			//以下获得方法获得的都是价格
			priceList.put(expenseList.get(i).getKinds(), expenseList.get(i).getPrice());
		}
//		System.out.println("priceList:"+priceList);
		
		//其他消费数量列表
		Map<String, Integer> sumList = billServiceimpl.getPerKindsSumPerDay();
//		System.out.println("sumList:"+sumList);
		
		//算账每个客人的房费
		List<Map<String, Integer>> roomChargeList = billServiceimpl.getRoomChargePerCustomerPerDay();
		System.out.println("roomChargeList:"+roomChargeList);
		//将数据转为JSON格式,易于获取
		JSONObject jsonCharge = new JSONObject();
		for(int i=0;i<roomChargeList.size();i++){
			jsonCharge.put(roomChargeList.get(i).get("inTime"), roomChargeList.get(i).get("sumPrice"));
		}
//		System.out.println("jsonCharge:"+jsonCharge);
		
		List<Bill> billList = billServiceimpl.getBillPerDay();
//		System.out.println("billList:"+billList);
		
		MyClass.mergenceOfTotalConsumptionPerCustomer(billList,priceList,jsonCharge);
		
		int total = MyClass.getExpenseTotalConsumption(sumList, priceList);
		
		String jsonBill="{\"Info\":"+billList+",\"total\":"+total+"}";
		JSONObject billJSONObject = JSONObject.fromObject(jsonBill);
		
//		System.out.println("billJSONObject:"+billJSONObject);
		response.getWriter().print(billJSONObject);
		return null;
		
	}
	
	//账目统计界面
	@RequestMapping(value="/Statistics",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String Statistics(HttpSession session) throws JsonProcessingException{
		LOG.info("front/Statistics...");
		//定向到CommonOperationController层
		session.setAttribute("flag", "front");
		return "redirect:../commonOperation/Statistics";
	}

}
