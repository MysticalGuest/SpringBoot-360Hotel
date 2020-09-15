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
   		<!-- <form class="form" id="searchForm" action="/administrator/ApartmentManagement" method="post"> -->
			<div id="search" style="padding:3px;background-color: #00B4FA;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;">
				<input id="roomNum" name="roomNum" type="text" oninput="doSearch()"
				style="height: 30px;border:1px solid #ccc;border-radius: 5px;" placeholder='根据房号查询:'></input>
			</div>
			<div id="search" style="padding:3px;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
				<input id="price" name="price" class="easyui-combogrid" data-options="
	          		width:'90%',
					panelHeight:'auto',
					idField:'num',
					textField:'price',
					mode:'remote',
					fitColumns:true,
					editable : false,
					columns:[[
						{field:'num',title:'序号',width:'50%'},
						{field:'price',title:'房价',width:'50%'}
					]],
					prompt:'根据价格查询:',
	          		">
			</div>
			<div id="search" style="color:white;padding:3px;background-color: #00B4FA;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
				根据房间状态查询:<br>
				已开出<input name="state" type="radio" value="true">
				未开出<input name="state" type="radio" value="false">
			</div>
			<div id="search" style="padding:3px;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
				<button class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch();" style="width: 95%;">搜索</button>
			</div>
			<div id="showAll" style="padding:3px;background-color: #00B4FA;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
				<button class="easyui-linkbutton"  onclick="showAll()" style="width: 95%;">显示全部</button>
			</div>
			
		
           	<script>
           		//房价树形下拉框-
           		var dataPrice='<%=request.getAttribute("jsonPrice")%>';
   				price= JSON.parse(dataPrice);
				$(function(){
		    		$("#price").combogrid({
		                data: price,
		            });
		    	});
		    	
		    	//搜索
				function doSearch(){
					roomNum=document.getElementById('roomNum').value;
					var num=$("#price").combogrid("getValues");
			    	var selectPrice="";
					for(var i=0; i<price.length; i++){
						if(num==price[i].num){
							selectPrice=price[i].price;
							break;
						}
					}
					//radio未选时undefine会影响后台
					var state=$('input:radio:checked').val();
					if(state!='true'&&state!='false'){
						var state="";
					}
					
					$.ajax({
						url: 'commonOperation/SearchApartmentManagement',
						type: 'post',
						data:{'roomNum':roomNum,'price':selectPrice,'state':state},
						dataType:'json',
						traditional:true,//用传统方式序列化数据
						success:function(data){
							var state='';
							var rows = [];
							for(var i=0; i<data.length; i++){
								if(data[i].state==false)
									state="未开出";
								else if(data[i].state==true)
									state="已开出";
								rows.push({
									roomNum: data[i].roomNum,
									price: data[i].price,
									state: state,
								});
							}
							$(function(){
								$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
							});
						},
						error:function(xhr,type,errorThrown){
							$.messager.alert('提示','搜索失败！','info',
								function(){location.href='administrator/ApartmentManagement'});
							console.log(errorThrown);
						}
					});
		    	}
		    	
		    	//显示全部,将所有输入置空
				function showAll(){
					//将搜索框的输入清空,还原input的状态
					$(" #roomNum").val("");
					var state = $("input[name=state]");
					state.attr("checked", false);
					$("#price").combogrid("setValue","");
					
					$.ajax({
						url: 'commonOperation/SearchApartmentManagement',
						type: 'post',
						data:{'roomNum':"",'price':"",'state':""},
						dataType:'json',
						traditional:true,//用传统方式序列化数据
						success:function(data){
							var state='';
							var rows = [];
							for(var i=0; i<data.length; i++){
								if(data[i].state==false)
									state="未开出";
								else if(data[i].state==true)
									state="已开出";
								rows.push({
									roomNum: data[i].roomNum,
									price: data[i].price,
									state: state,
								});
							}
							$(function(){
								$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
							});
						},
						error:function(xhr,type,errorThrown){
							$.messager.alert('提示','显示失败！','info',
								function(){location.href='administrator/ApartmentManagement'});
							console.log(errorThrown);
						}
					});
		    	}
			</script>
		<!-- </form> -->
		<div style="padding:3px;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<button class="easyui-linkbutton"  onclick="checkOutChecked();" style="width: 95%;">多选退房</button>
		</div>
		<div style="padding:3px;background-color: #00B4FA;border: 1px solid #B2D7F2;border-radius: 5px;width: 80%;padding-left:5%;margin-left: 10%;margin-top: 10%;">
			<button class="easyui-linkbutton"  onclick="allCheckOut();" style="width: 95%;">全部退房</button>
		</div>
		<script>
			//多选退房
			function checkOutChecked(){
				var flag="front";
				var checkedItems = $('#apartment').datagrid('getChecked');
				var rooms = [];
				$.each(checkedItems, function (index, item) {
					rooms.push(item.roomNum);
				});
				//将rooms数组转为字符串传到后台
				var strRoom=rooms.join(",")
				if(rooms==""){
					$.messager.alert('提示','您未选中任何房间！','warning') 
				}
				else{
					$.ajax({
						url: 'commonOperation/checkOutChecked',
						type: 'post',
						data: {'flag':flag,'strRoom':strRoom},
						dataType:'json',
						traditional:true,//用传统方式序列化数据
						success:function(data){
							$.messager.alert('提示','选中退房成功！','info');
							var state='';
							var rows = [];
							for(var i=0; i<data.length; i++){
								if(data[i].state==false)
									state="未开出";
								else if(data[i].state==true)
									state="已开出";
								rows.push({
									roomNum: data[i].roomNum,
									price: data[i].price,
									state: state,
								});
							}
							$(function(){
								$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
							});
							$("#apartment").datagrid('clearSelections');	
						},
						error:function(xhr,type,errorThrown){
							$.messager.alert('提示','退房失败！','info',
								function(){location.href='administrator/ApartmentManagement'});
							console.log(errorThrown);
						}
					});
				}
			}
			//全部退房调用函数
	    	function allCheckOut(){
	    		var flag="front";
				$.ajax({
                   	url: 'commonOperation/allCheckOut',
                   	type: 'post',
                   	data: {'flag':flag},
					dataType:'json',
                   	traditional:true,//用传统方式序列化数据
                   	success:function(data){
						var state='';
						var rows = [];
						for(var i=0; i<data.length; i++){
							if(data[i].state==false)
								state="未开出";
							else if(data[i].state==true)
								state="已开出";
							rows.push({
								roomNum: data[i].roomNum,
								price: data[i].price,
								state: state,
							});
						}
						$(function(){
							$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
						});    
                   	},
					error:function(xhr,type,errorThrown){
						$.messager.alert('提示','退房失败！','info',
							function(){location.href='administrator/ApartmentManagement'});
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
 	<div data-options="region:'center',border:true,title:'客房管理'" style="background:#fff;" >
   		<div style="width:100%;height:100%;float: right;padding: 5px;">
   			<table id="apartment" class="easyui-datagrid" style="width:100%;height:100%"
   				fitColumns="true" idField="roomNum" data-options="
					rownumbers:true,
					autoRowHeight:true,
					pagination:true,
					pageSize:10">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="roomNum" width="50" align="center">房号</th>
						<th field="price" width="50" align="center" editor="numberbox">价格</th>
						<th field="state" width="50" align="center" editor="{type:'checkbox',options:{on:'已开出',off:'未开出'}}">房间状态</th>
						<th field="action" width="50" align="center" formatter="formatAction">修改</th>
						
					</tr>
				</thead>
			</table>
			
			<script type="text/javascript">
				function formatAction(value,row,index){
						var checkOut = '<button onclick="checkOut(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">退房</button>';
						if(row.state=="未开出")
							return ;
						else if(row.state=="已开出")
							return checkOut;
				}
				
				$(function(){
					$('#apartment').datagrid({
						onBeforeEdit:function(index,row){
							$(this).datagrid('updateRow', {index:index,row:{editing:true}})
						},
						onAfterEdit:function(index,row){
							$(this).datagrid('updateRow', {index:index,row:{editing:false}})
						},
						onCancelEdit:function(index,row){
							$(this).datagrid('updateRow', {index:index,row:{editing:false}})
						}
					});
				});
				function getRowIndex(target){
					var tr = $(target).closest('tr.datagrid-row');
					return parseInt(tr.attr('datagrid-row-index'));
				}
				function editrow(target){
					$('#apartment').datagrid('beginEdit', getRowIndex(target));
				}
				function checkOut(target){
					var flag="front";
					var row = $('#apartment').datagrid('getSelected');
					var roomNum=row.roomNum;
					if (row){
						$.ajax({
	                    	url: 'commonOperation/checkOut',
	                    	type: 'post',
	                    	data: {'roomNum':roomNum,'flag':flag},
							dataType:'json',
	                    	traditional:true,//用传统方式序列化数据
	                    	success:function(data){
		                    	$.messager.alert('提示','退房成功！','info');
		                    	var state='';
		                    	var rows = [];
		                    	for(var i=0; i<data.length; i++){
		                    		if(data[i].state==false)
		                    			state="未开出";
		                    		else if(data[i].state==true)
		                    			state="已开出";
		                    		rows.push({
		                    			roomNum: data[i].roomNum,
		                    			price: data[i].price,
		                    			state: state,
		                    		});
		                    	}
		                    	$(function(){
		                    		$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
		                    	});
		                    	$("#apartment").datagrid('clearSelections');          
	                    	},
							error:function(xhr,type,errorThrown){
								$.messager.alert('提示','退房失败！','info',
									function(){location.href='administrator/ApartmentManagement'});
								console.log(errorThrown);
							}
	                	});
					}
				}
				function saverow(target){
					$('#apartment').datagrid('endEdit', getRowIndex(target));
				}
				function cancelrow(target){
					$('#apartment').datagrid('cancelEdit', getRowIndex(target));
				}
				// $('#apartment').datagrid('selectRow',index);
				// $('#apartment').datagrid('beginEdit',index);
			</script>
			
			<script>
				function getData(){
					var state='';
					var rows = [];
					var data = <%=request.getAttribute("apartmentList")%>;
					for(var i=0; i<data.length; i++){
						if(data[i].state==false)
							state="未开出";
						else if(data[i].state==true)
							state="已开出";
						rows.push({
							roomNum: data[i].roomNum,
							price: data[i].price,
							state: state,
						});
					}
					return rows;
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
					$('#apartment').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
				});
			</script>
		</div>
   	</div>
</body>
</html>
