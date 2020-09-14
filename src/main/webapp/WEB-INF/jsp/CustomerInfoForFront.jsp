<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="content-type" content="text/html" charset="utf-8">
		<title>尊品360宾馆管理系统</title>
		
		<link rel="shortcut icon" href="img/fire.ico"  type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="css/font-style.css"/>
		<link rel="stylesheet" type="text/css" href="css/customer-info-style.css"/>
		
		<link rel="stylesheet" type="text/css" href="webjars/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="webjars/jquery-easyui/themes/icon.css">
		
		<script type="text/javascript" src="webjars/jquery-easyui/jquery.min.js"></script>
		<script type="text/javascript" src="webjars/jquery-easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="webjars/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

	</head>
  
<body class="easyui-layout">
    <!--上-->
 	<div data-options="region:'north',border:false," style="background:#fff;height:20%;" >
		<div style="position:absolute; left:45%; top:20%;">
			<font style="font-family:webfont;font-size:50px;color: black;">
				尊品360宾馆服务平台
			</font>
		</div>
		<div style="position:absolute; left:18%; top:8%;">
			<img src="img/360black.png" width="65%">
		</div>
	</div>
 	<!--西-->
   	<div data-options="region:'west',border:false," style="background:#fff;width:20%;" >
		<div id="search" style="padding:3px;background-color: #00B4FA;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;">
			<input id="datetime" name="datetime" class="easyui-datetimebox" type="text"
			style="height: 30px;border:1px solid #ccc;border-radius: 5px;" data-options="prompt:'根据入住时间查询:'"></input>
		</div>
		<div id="search" style="padding:3px;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<button class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()" style="width: 95%;">根据时间搜索</button>
		</div>
		
		<div id="search" style="padding:3px;background-color: #00B4FA;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<input id="roomNum" name="roomNum" type="text"
			style="height: 30px;border:1px solid #ccc;border-radius: 5px;" oninput="doSearch()" placeholder='根据入住房间查询:'></input>
		</div>
		<div id="search" style="padding:3px;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<input id="cName" name="cName" type="text"
			style="height: 30px;border:1px solid #ccc;border-radius: 5px;" oninput="doSearch()"  placeholder='根据房客姓名查询:'></input>
		</div>
		<div id="search" style="padding:3px;background-color: #00B4FA;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<button class="easyui-linkbutton" onclick="showAll()" style="width: 95%;">显示全部</button>
		</div>
		<script>
			//搜索函数
			function doSearch(){
				var datetime=document.getElementById('datetime').value;
				var cName=document.getElementById('cName').value;
				var roomNum=document.getElementById('roomNum').value;
				$.ajax({
					url: 'commonOperation/SearchCustomerInfo',
					type: 'post',
					data: {'datetime':datetime,'cName':cName,'roomNum':roomNum},
					dataType:'json',
					traditional:true,//用传统方式序列化数据
					success:function(data){
						$(function(){
							$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
						});
					},
					error:function(xhr,type,errorThrown){
						$.messager.alert('提示','搜索失败！','info',
							function(){location.href='front/CustomerInfoForFront'});
						console.log(errorThrown);
					}
				});
			}
			
			//显示全部调用函数
			function showAll(){
				//将搜索框的输入清空,还原input的状态
				$(" #datetime").datetimebox('setValue','');
				$(" #cName").val("");
				$(" #roomNum").val("");
				$.ajax({
					url: 'commonOperation/ShowAllCustomerInfo',
					data: {},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					// timeout:10000,//超时时间设置为10秒；
					traditional:true,//用传统方式序列化数据
					success:function(data){
						$(function(){
							$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
						});
					},
					error:function(xhr,type,errorThrown){
						$.messager.alert('提示','显示失败！','info',
							function(){location.href='front/CustomerInfoForFront'});
						console.log(errorThrown);
					}
				});
			}
		</script>
	</div>
	<!--东-->
	<div data-options="region:'east',border:false," style="background:#fff;width:20%;" >
		<div class="main-box">
			<ul class="index-tserver">
				<li class="tserver-list1">
					返回首页
					<p class="animated zoomin">
						<a href="front/Home">返回首页，执行其他选项操作</a>
					</p>
				</li>
				<li class="tserver-list2">
					敬请期待
					<p class="animated zoomin">
						<a href="#"></a>
					</p>
				</li>
			</ul>
		</div>
	</div>
   	
   	<!--中-->
   	<div data-options="region:'center',border:true,title:'顾客信息'" style="background:#fff;" >
   		<div id="info" style="width:100%;height:100%;float: right;padding: 5px;">
   			<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
   				fitColumns="true" data-options="
					rownumbers:true,
					singleSelect:true,
					autoRowHeight:true,
					pagination:true,
					pageSize:10">
				<thead>
					<tr>
						<th field="inTime" align="center">入住时间</th>
						<th field="cName" width="40" align="center">姓名</th>
						<th field="cardID" align="center" >身份证号</th>
						<th field="roomNum" width="60" align="center" formatter="linefeed" >房号</th>
						<th field="chargeAndDeposit" width="20" align="center">收款</th>
						<th field="paymentMethod" width="25" align="center">支付方式</th>
					</tr>
				</thead>
			</table>
			
			<script>
				var jsondata=<%=request.getAttribute("customerList")%>;
				
				//对‘房间’列换行
				function linefeed(value,row,index) {
				    return "<div style='width=250px;word-break:break-all;word-wrap:break-word;white-space:pre-wrap;'>"
				    +value+"</div>";
				}
	
				function pagerFilter(data){
					if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
						data = {
							total: data.length,
							rows: data
						}
					}
					var dg = $(this);
					var opts = dg.datagrid('options');
					var pager = dg.datagrid('getPager');
					pager.pagination({
						onSelectPage:function(pageNum, pageSize){
							opts.pageNumber = pageNum;
							opts.pageSize = pageSize;
							pager.pagination('refresh',{
								pageNumber:pageNum,
								pageSize:pageSize
							});
							dg.datagrid('loadData',data);
						}
					});
					if (!data.originalRows){
						data.originalRows = (data.rows);
					}
					var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
					var end = start + parseInt(opts.pageSize);
					data.rows = (data.originalRows.slice(start, end));
					return data;
				}
	
				$(function(){
					$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', jsondata);
				});
			</script>
		</div>
   	</div>
</body>
</html>
