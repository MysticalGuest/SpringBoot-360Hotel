<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<meta http-equiv="content-type" content="text/html" charset="utf-8">
		
		<title>尊品360宾馆服务平台</title>
		
		<link rel="shortcut icon" href="img/fire.ico"  type="image/x-icon"/>
		<link rel="stylesheet" href="css/chart.css">
		<link rel="stylesheet" type="text/css" href="css/font-style.css"/>
		
		<script type="text/javascript" src="webjars/jquery-easyui/jquery.min.js"></script>
		<script src="js/echarts.min.js"></script>
		<script src="js/chart.js"></script>
		<style type="text/css">
			.font-title{font-weight: 70;font-family: webfont;}
		</style>
	</head>
	<body>
		<!--头部-->
		<div class="header">
			尊品360宾馆账目统计
			<a onclick="JumpPage()" class="a-access">
				<button class="button type1">
					返回首页
				</button>
			</a>
		</div>
		<script>
			function JumpPage(){
				var flag='<%=request.getAttribute("flag")%>';
				if(flag=="front"){
					location.href='front/Home';
				}
				else if(flag=="administrator"){
					location.href='administrator/HomeForAdm';
				}
				else{}
			}
		</script>
		<!--主体-->
		<div class="main clearfix">
			<div class="main-left">
				<div class="border-container">
					<div class="name-title">
						===<font class="font-title">周营业额比较</font>===
					</div>
					<div id="doubleWeeks"></div>
					<span class="top-left border-span"></span>
					<span class="top-right border-span"></span>
					<span class="bottom-left border-span"></span>
					<span class="bottom-right border-span"></span>
				</div>
			</div>
			<div class="main-middle">
				<div class="border-container">
					<ul class="teacher-pie clearfix">
						<li>
							<div class="name-title">
								===<font class="font-title">上月营业额比较</font>===
							</div>
							<div id="lastMonth"></div>
						</li>
						<li>
							<div class="name-title">
							   ===<font class="font-title">本月营业额比较</font>===
							</div>
							<div id="thisMonth"></div>
						</li>
					</ul>
	
					<div class="name-title">
						===<font class="font-title">近年营业额比较</font>===
					</div>
					<div id="changedetail"></div>
					<span class="top-left border-span"></span>
					<span class="top-right border-span"></span>
					<span class="bottom-left border-span"></span>
					<span class="bottom-right border-span"></span>
				</div>
			</div>
			<div class="main-right">
				<div class="border-container">
					<div class="name-title">
						===<font class="font-title">本季度营业额比较</font>===
					</div>
					<div id="quarter"></div>
					<span class="top-left border-span"></span>
					<span class="top-right border-span"></span>
					<span class="bottom-left border-span"></span>
					<span class="bottom-right border-span"></span>
				</div>
			</div>
		</div>
		<script>
			// Java后台使用EL传值给JS文件
			//方案一：对全局变量赋值。EL表达式必须放在""里
			//本周
			var profitPerDayListThisWeek = <%=request.getAttribute("profitPerDayListThisWeek")%>;
			//上周
			var profitPerDayListLastWeek = <%=request.getAttribute("profitPerDayListLastWeek")%>;
			//上月
			var JSONListLastMonth = <%=request.getAttribute("JSONListLastMonth")%>;
			//本月
			var JSONListThisMonth = <%=request.getAttribute("JSONListThisMonth")%>;
			//今年每季度
			var profitPerQuarterListThisYear = <%=request.getAttribute("profitPerQuarterListThisYear")%>;
			//近几年年份
			var yearList = <%=request.getAttribute("yearList")%>;
			//近几年营业额
			var profitPerYearList = <%=request.getAttribute("profitPerYearList")%>;
			// console.log('1212',JSONListThisMonth);
		</script>
	</body>
</html>
