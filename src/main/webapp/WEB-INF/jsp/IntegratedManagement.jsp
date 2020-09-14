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
			<font style="font-family:HYChaoJiZhanJiaW;font-size:50px;color: black;">
				尊品360宾馆服务平台
			</font>
		</div>
		<div style="position:absolute; left:18%; top:8%;">
			<img src="img/360black.png" width="65%">
		</div>
	</div>
 	<!--西-->
   	<div data-options="region:'west',border:false," style="background:#fff;width:20%;" >
		<div id="search" style="padding:3px;font-size:26px;background-color: #00B4FA;border-radius: 5px;width: 80%;padding-left:2%;margin-left: 10%;">
			钟点房价格:<span style="font-size:26px;height: 30px;" id="forHourRoom"></span>元<br>
			<input id="hourRoomPrice" name="hourRoomPrice" type="number" autocomplete="off"
				style="width: 45%;height: 30px;border:1px solid #ccc;border-radius: 5px;" placeholder='钟点房房价:'></input>
				<style>
					/* 去掉输入框的上下箭头 */
				    input::-webkit-outer-spin-button,
				    input::-webkit-inner-spin-button {
				        -webkit-appearance: none;
				    }
				    input[type="number"]{
				        -moz-appearance: textfield;
				    }
				</style>
			<button class="easyui-linkbutton"  onclick="resetHourRoomPrice();" style="width: 40%;margin-top: 7px;">修改房价</button>
			<script>
				//动态改变页面上的数值
				$(function(){
					document.getElementById('forHourRoom').innerHTML = <%=request.getAttribute("hourRoomPrice")%>;
				})
				//调用函数
				function resetHourRoomPrice(){
					var hourRoomPrice=document.getElementById('hourRoomPrice').value;
					if(hourRoomPrice!=""){
						$.ajax({
							url: 'administrator/hourRoomPrice',
							type: 'post',
							data: {'hourRoomPrice':hourRoomPrice},
							// dataType:'json',
							// traditional:true,//用传统方式序列化数据
							success:function(data){
								//提交修改后,动态改变页面上的数值
								document.getElementById('forHourRoom').innerHTML = data;
								$(" #hourRoomPrice").val("");
							},
							error:function(xhr,type,errorThrown){
								$.messager.alert('提示','修改失败！','info',
									function(){location.href='administrator/IntegratedManagement'});
								console.log(errorThrown);
							}
						});
					}
					
				}
			</script>
		</div>
		
	</div>
	<!--东-->
	<div data-options="region:'east',border:false," style="background:#fff;width:20%;" >
		<div class="main-box">
			<ul class="index-tserver">
				<li class="tserver-list1">
					返回首页
					<p class="animated zoomin">
						<a href="administrator/HomeForAdm">返回首页，执行其他选项操作</a>
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
   	<div data-options="region:'center',border:true,title:'前台管理与商品管理'" style="background:#fff;" >
   		<div style="width:100%;height:100%;float: right;padding: 5px;">
   			<table id="front" class="easyui-datagrid" style="width:100%;height:50%"
   				fitColumns="true" idField="admId" data-options="
					rownumbers:true,
					singleSelect:true,
					autoRowHeight:false,
					pagination:true,
					pageSize:10">
				<thead>
					<tr>
						<th field="AdmId" width="50" align="center">账号</th>
						<th field="aName" width="50" align="center" editor="{type:'textbox',options:{required: true}}">姓名</th>
						<th field="aPassword" width="50" align="center" editor="{type:'textbox',options:{required: true}}">密码</th>
						<th field="aSex" width="50" align="center" formatter="formatSex">性别</th>
						<th field="action" width="50" align="center" formatter="formatAction">修改</th>
					</tr>
				</thead>
			</table>
			
			<table id="expense" class="easyui-datagrid" style="width:100%;height:50%"
				fitColumns="true" idField="admId" data-options="
					rownumbers:true,
					singleSelect:true,
					autoRowHeight:false">
				<thead>
					<tr>
						<th field="name" width="50" align="center">商品名称</th>
						<th field="price" width="25" align="center" editor="numberspinner">价格</th>
						<th field="action" width="50" align="center" formatter="expenseAction">修改</th>
					</tr>
				</thead>
			</table>
			
			<script type="text/javascript">
				//front表格的操作函数
				function formatSex(value,row,index) {
					var data;
					//由于每次在调用saverow(target)函数时,保存后后台数据刷新了,但前台数据还是上一次的数据,所以这里我用到ajax
					$.ajax({
						// 我是从@RequestMapping(value="/IntegratedManagement")中拿的当前数据库最新数据
						url: 'administrator/IntegratedManagement',
						type: 'post',
						data: {},
						dataType:'json',
						async: false,//同步
						// traditional:true,//用传统方式序列化数据
						success:function(result){
							data=result;
						},
						error:function(xhr,type,errorThrown){
							console.log(errorThrown);
						}
					});
					if (row.editing) {
					    var s = '男<input name="sex" type="radio" checked="checked" value="man"/> '+
					    '女<input name="sex" type="radio" value="woman"/>';
					}
					else {
						for(var i=0; i<data.length; i++){
							if(row.AdmId==data[i].AdmId){
								var s=data[i].aSex;
								break;
							}
						}
					}
					return s;		
                }
				
				function formatAction(value,row,index){
					if (row.editing){
						var save = '<button onclick="saverow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">保存</button> ';
						var cancel = '<button onclick="cancelrow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">取消</button>';
						return save+cancel;
					} else {
						var edit = '<button onclick="editrow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">编辑</button> ';
						return edit;
					}
					
				}
				
				$(function(){
					$('#front').datagrid({
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
					$('#front').datagrid('beginEdit', getRowIndex(target));
				}
				
				function saverow(target){
					var selectSex=$("input[name='sex']:checked").val();//必须放在第一句
					
					$('#front').datagrid('endEdit', getRowIndex(target));
					var row = $('#front').datagrid('getSelected');
					var admId=row.AdmId;
					var aName=row.aName;
					var aPassword=row.aPassword;
					if (row){
						$.ajax({
	                    	url: 'administrator/ResetFrontInfo',
	                    	type: 'post',
	                    	data: {'admId':admId,'aName':aName,'aPassword':aPassword,'aSex':selectSex},
							dataType:'json',
	                    	traditional:true,//用传统方式序列化数据
	                    	success:function(data){
		                    	$.messager.alert('提示','修改成功！','info');
								$(function(){
									$('#front').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
								});
								$("#front").datagrid('clearSelections');
	                    	},
							error:function(xhr,type,errorThrown){
								$.messager.alert('提示','保存失败！','info',
									function(){location.href='administrator/IntegratedManagement'});
								console.log(errorThrown);
							}
	                	});
					}
				}
				function cancelrow(target){
					$('#front').datagrid('cancelEdit', getRowIndex(target));
				}
				// $('#front').datagrid('selectRow',index);
				// $('#front').datagrid('beginEdit',index);
			</script>
			
			<script type="text/javascript">
				//expense表格的操作函数
				function expenseAction(value,row,index){
					if (row.editing){
						var save = '<button onclick="saveExpenserow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">保存</button> ';
						var cancel = '<button onclick="cancelExpenserow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">取消</button>';
						return save+cancel;
					} else {
						var edit = '<button onclick="editExpenserow(this)" style="cursor:pointer;border:1px solid #ccc;border-radius: 2px;">编辑</button> ';
						return edit;
					}
					
				}
				
				$(function(){
					$('#expense').datagrid({
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
				function editExpenserow(target){
					$('#expense').datagrid('beginEdit', getRowIndex(target));
				}
				
				function saveExpenserow(target){
					
					$('#expense').datagrid('endEdit', getRowIndex(target));
					var selectedItems = $('#expense').datagrid('getSelected');
					
					var kind=selectedItems.kinds;
					var price=selectedItems.price;
					//如果行选中
					if (selectedItems){
						$.ajax({
			            	url: 'administrator/ResetExpense',
			            	type: 'post',
			            	data: {'kind':kind,'price':price},
							dataType:'json',
			            	traditional:true,//用传统方式序列化数据
			            	success:function(data){
			                	$.messager.alert('提示','修改成功！','info');
								$(function(){
									$('#expense').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
								});
								$("#expense").datagrid('clearSelections');
			            	},
							error:function(xhr,type,errorThrown){
								$.messager.alert('提示','保存失败！','info',
									function(){location.href='administrator/IntegratedManagement'});
								console.log(errorThrown);
							}
			        	});
					}
				}
				function cancelExpenserow(target){
					$('#expense').datagrid('cancelEdit', getRowIndex(target));
				}
			</script>
				
			<script>
				function getData(){
					var rows = [];
					var data=<%=session.getAttribute("admList")%>;
					for(var i=0; i<data.length; i++){
						rows.push({
							AdmId: data[i].AdmId,
							aName: data[i].aName,
							aPassword: data[i].aPassword,
						});
					}
					return rows;
				}
				
				expenseData=<%=session.getAttribute("expenseList")%>;
				
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
					$('#front').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
					$('#expense').datagrid({loadFilter:pagerFilter}).datagrid('loadData', expenseData);
				});
			</script>
		</div>
   	</div>
</body>
</html>
