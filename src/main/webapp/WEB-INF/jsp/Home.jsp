<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>尊品360宾馆服务平台</title>
		<link rel="shortcut icon" href="img/fire.ico"  type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="css/fontStyle.css"/>
		<link rel="stylesheet" type="text/css" href="css/homeStyle.css"/>
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
		<script type="text/javascript" src="easyui/jquery.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
<body>
	<div class="auto-box">
		<div class="main-box">
			<div class="index-tit">
			<div style="position:absolute; left:30%; top:3%;">
				<font style="font-family:HYChaoJiZhanJiaW;font-size:50px;color: black;">
					尊品360宾馆服务平台
				</font>
			</div>
				<h1>${thisadministrator.aName}正在运行平台！比标准服务更多的，是精益求精的360°服务</h1>
				<p>千锤百炼打造面向顾客的全程服务</p>
			</div>
			<ul class="index-tserver">
				<li class="tserver-list1">
					顾客信息统计
					<p class="animated zoomin">
						<a href="front/CustomerInfoForFront">查询所以已打印发票的房客基本信息</a>
					</p>
				</li>
				<li class="tserver-list2">
					打印发票
					<p class="animated zoomin">
						<a href="front/Bill">点击打印发票</a>
					</p>
				</li>
				<li class="tserver-list3">
					客房管理
					<p class="animated zoomin">
						<a href="front/ApartmentManagement">查看，更改房间状态</a>
					</p>
				</li>
				<li class="tserver-list4">
					账目管理
					<p class="animated zoomin">
						<a href="front/AccountOfPerDay">宾馆每日统计账目</a>
					</p>
				</li>
				<li class="tserver-list5">
					账目统计
					<p class="animated zoomin">
						<a href="front/Statistics">宾馆每日统计账目，宾馆每周、每月、每季度、每年账目详计</a>
					</p>
				</li>
				<li class="tserver-list6">
					敬请期待
					<p class="animated zoomin">
						<a href="#"></a>
					</p>
				</li>
				<li class="tserver-list7">
					退出
					<p class="animated zoomin">
						<a href="logout">回退到尊品360服务平台登录界面</a>
					</p>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>

