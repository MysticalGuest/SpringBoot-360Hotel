package com.hotel.custommethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotel.entity.Bill;

import net.sf.json.JSONObject;

public class MyClass {
	
	
	public static Map<String, Integer> getExpenseForMapList(List<Map<String, Integer>> mapList,Map<String, Integer> priceList) {
		//将计算后的账单存起来
	  	Map<String, Integer> profitList = new HashMap<String, Integer>();
			
		for(int i = 0; i < mapList.size(); i++){
			//为解决java.math.BigDecimal cannot be cast to java.lang.Integer问题
			//将取出的数据转为String型,然后再转为int型
			int numOfTea = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfTea")));
			int numOfWLJJDB = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfWLJJDB")));
			int numOfGreenTea = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfGreenTea")));
			int numOfNoodles = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfNoodles")));
			int numOfMineral = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfMineral")));
			int numOfPulsation = Integer.parseInt(String.valueOf(mapList.get(i).get("numOfPulsation")));
			int tempOtherCost=numOfMineral*priceList.get("mineral")
					+numOfPulsation*priceList.get("pulsation")
					+numOfGreenTea*priceList.get("greenTea")
					+numOfTea*priceList.get("tea")
					+numOfNoodles*priceList.get("noodles")
					+numOfWLJJDB*priceList.get("WLJJDB");
			
			int turnover = Integer.parseInt(String.valueOf(mapList.get(i).get("turnover")));
			int profit = turnover+tempOtherCost;
			profitList.put(String.valueOf(mapList.get(i).get("inDate")), profit);
		}
		return profitList;
	}
	
	//这是'账目详计页面'界面都会用到的计算'其他消费'额的方法
	//其他消费数量列表sumList
	public static int getExpenseTotalConsumption(Map<String, Integer> sumList,Map<String, Integer> priceList) {
		//为解决java.math.BigDecimal cannot be cast to java.lang.Integer问题
		//将取出的数据转为String型,然后再转为int型
		int numOfTea = Integer.parseInt(String.valueOf(sumList.get("numOfTea")));
		int numOfWLJJDB = Integer.parseInt(String.valueOf(sumList.get("numOfWLJJDB")));
		int numOfGreenTea = Integer.parseInt(String.valueOf(sumList.get("numOfGreenTea")));
		int numOfNoodles = Integer.parseInt(String.valueOf(sumList.get("numOfNoodles")));
		int numOfMineral = Integer.parseInt(String.valueOf(sumList.get("numOfMineral")));
		int numOfPulsation = Integer.parseInt(String.valueOf(sumList.get("numOfPulsation")));
		
		//算账其他消费
		int total = priceList.get("tea")*numOfTea
				+priceList.get("WLJJDB")*numOfWLJJDB
				+priceList.get("greenTea")*numOfGreenTea
				+priceList.get("noodles")*numOfNoodles
				+priceList.get("mineral")*numOfMineral
				+priceList.get("pulsation")*numOfPulsation;
		return total;
	}
	
	//在考虑顾客开多间房,并且多间房分别都有消费的情况时,处理计算其他消费的方法
	//这是AdministratorController和FrontController层'账目详计页面'界面都会用到的处理'其他消费'额的方法
	public static void mergenceOfTotalConsumptionPerCustomer
	(List<Bill> billList,Map<String, Integer> priceList,JSONObject jsonCharge) {
		//其他消费
		int otherCost=0;
		//为了减少计算'应退押金'时的循环次数,我给每个顾客设个ID
		int customerId=1;
		for (int i = 0; i < billList.size(); i++){
			//每个客人的房费
			int sumRoomCharge;
//			//记下这条bill的时间,下面会与之前的对比
			String inDate = billList.get(i).getInTime();
			int tempOtherCost=myParseInt(billList.get(i).getMineral())*priceList.get("mineral")
					+myParseInt(billList.get(i).getPulsation())*priceList.get("pulsation")
					+myParseInt(billList.get(i).getGreenTea())*priceList.get("greenTea")
					+myParseInt(billList.get(i).getTea())*priceList.get("tea")
					+myParseInt(billList.get(i).getNoodles())*priceList.get("noodles")
					+myParseInt(billList.get(i).getWLJJDB())*priceList.get("WLJJDB");
			
			//为解决java.math.BigDecimal cannot be cast to java.lang.Integer问题
			//将取出的数据转为String型,然后再转为int型
			sumRoomCharge = Integer.parseInt(String.valueOf(jsonCharge.get(billList.get(i).getInTime().toString())));
			
			if(i>0){
				//考虑到一个顾客可能会开多个房间,所以我将两房间的开房时间进行对比,如果时间相同就是同一个顾客，统计每个房间的消费,算在总费用中
				if(billList.get(i).getInTime().toString().equals(billList.get(i-1).getInTime().toString())){
					otherCost+=tempOtherCost;
					//将统计的其他费用后算出的'应退押金',改动这个顾客上个房间的'应退押金',统一'应退押金'
					for(int j=customerId-1;j<i;j++){
						if(billList.get(j).getInTime().equals(inDate))
							billList.get(j).setRefund(billList.get(i).getChargeAndDeposit()-sumRoomCharge-otherCost);
					}
				}
				else{
					//说明这条信息的客户与上条信息不是同一个客户,就对其id加1
					customerId++;
					otherCost=tempOtherCost;
				}	
			}
			else{
				otherCost=tempOtherCost;
			}

			billList.get(i).setRefund(billList.get(i).getChargeAndDeposit()-sumRoomCharge-otherCost);
		}
	}
	
	//mergenceOfTotalConsumptionPerCustomer方法需要的函数
	private static int myParseInt(String str) {
		if(str==null)
			return 0;
		return Integer.parseInt(str);
	}

}
