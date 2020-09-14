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
    
    <meta http-equiv="content-type" content="text/html" charset="utf-8">
    <title>尊品360宾馆服务平台</title>
	<link rel="shortcut icon" href="img/fire.ico"  type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="css/font-style.css"/>
	<link rel="stylesheet" type="text/css" href="css/home-style.css"/>
		
  </head>
  
  <body>

<div class="auto-box">
	<div class="main-box">
		<div class="index-tit">
		<div style="position:absolute; left:30%; top:10px;">
			<font style="font-family:webfont;font-size:50px;color: black;">
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
					<a href="administrator/CustomerInfoForAdm">查询所以已打印发票的房客基本信息</a>
				</p>
			</li>
			<li class="tserver-list2">
				综合管理
				<p class="animated zoomin">
					<a href="administrator/IntegratedManagement">前台信息进行管理，对其他消费的一些商品和钟点房做价格调整</a>
				</p>
			</li>
			<li class="tserver-list3">
				客房管理
				<p class="animated zoomin">
					<a href="administrator/ApartmentManageAdm">查看，更改房间状态</a>
				</p>
			</li>
			<li class="tserver-list4">
				账目详计
				<p class="animated zoomin">
					<a href="administrator/Account">每日统计账目</a>
				</p>
			</li>
			<li class="tserver-list5">
				账目统计
				<p class="animated zoomin">
					<a href="administrator/Statistics">宾馆每日统计账目，宾馆每周、每月、每季度、每年账目详计</a>
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
					<a href="login">回退到尊品360服务平台登录界面</a>
				</p>
			</li>
		</ul>
	</div>
</div>

</body>
</html>

