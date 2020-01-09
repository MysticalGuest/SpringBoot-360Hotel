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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
		
		<script type="text/javascript" src="easyui/jquery.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
		<style type="text/css">
			body{text-align: center;}
			.main{width:77%;position:relative;margin:auto;}
			#layer02 > div{float:left;position:relative;}
			.layer02-data{font-family: "new york";position: absolute;width: auto;color: red;top: 45px;left: 65px;}
			
			.header{
				height: 80px;
				position: relative;
				padding-top: 13px;
				font-size: 36px;
				color: black;
				text-align: center;
				background: url(img/chartHeader.png) top center no-repeat;
				font-family: HYChaoJiZhanJiaW;
			}
			.header a.a-access{
				position: absolute;
				right: 3%;
				top: -18%;
			}
			.button {
				position: relative;
				padding: 0.5em 1.5em;
				border: none;
				background-color: transparent;
				cursor: pointer;
				outline: none;
				font-size: 18px;
				margin: 1em 0.8em;
			}
			.button.type1 {
				color: black;
				font-family: HYChaoJiZhanJiaW;
			}
			.button.type1.type1::after, .button.type1.type1::before {
				content: "";
				display: block;
				position: absolute;
				width: 20%;
				height: 20%;
				border: 2px solid;
				transition: all 0.6s ease;
				border-radius: 2px;
			}
			.button.type1.type1::after {
				bottom: 0;
				right: 0;
				border-top-color: transparent;
				border-left-color: transparent;
				border-bottom-color: #566473;
				border-right-color: #566473;
			}
			.button.type1.type1::before {
				top: 0;
				left: 0;
				border-bottom-color: transparent;
				border-right-color: transparent;
				border-top-color: #566473;
				border-left-color: #566473;
			}
			.button.type1.type1:hover:after, .button.type1.type1:hover:before {
				width: 100%;
				height: 100%;
			}
		</style>
		
		<script type="text/javascript">
			$(function(){
				drawLayer02Label($("#layer02_01 canvas").get(0),"截止目前开出的房间",60,200);
				drawLayer02Label($("#layer02_02 canvas").get(0),"截止目前开出的发票",60,200);
				drawLayer02Label($("#layer02_03 canvas").get(0),"截止目前的营业额",60,200);
				drawLayer02Label($("#layer02_04 canvas").get(0),"截止目前的净营业额",60,300);
				drawLayer02Label($("#layer02_05 canvas").get(0),"已收押金",60,200);
				drawLayer02Label($("#layer02_06 canvas").get(0),"其他消费",60,200);
			});

			function drawLayer02Label(canvasObj,text,textBeginX,lineEndX){
				var colorValue = 'grey';

				var ctx = canvasObj.getContext("2d");

				ctx.beginPath();
				ctx.arc(35,55,2,0,2*Math.PI);
				ctx.closePath();
				ctx.fillStyle = colorValue;
				ctx.fill();

				ctx.moveTo(35,55);
				ctx.lineTo(60,80);
				ctx.lineTo(lineEndX,80);
				ctx.lineWidth = 1;
				ctx.strokeStyle = colorValue;
				ctx.stroke();

				ctx.font='15px Georgia';
				ctx.fillStyle = colorValue;
				ctx.fillText(text,textBeginX,92);
			}
		</script>
	</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false,border:false" style="height:30%;">
		<div class="header">
			尊品360宾馆当日账目详计
			<a href="front/Home" class="a-access">
				<button class="button type1">
					返回首页
				</button>
			</a>
		</div>
		<div class="main">
			<div id="layer02" class="layer">
				<div id="layer02_01" style="width:18%;">
					<div class="layer02-data">
						<span style="font-size:26px;"><%=request.getAttribute("numOfRoom")%></span>
						<span style="font-size:16px;">间</span>
					</div>
					<canvas width="200" height="100"></canvas>
				</div>
				<div id="layer02_02" style="width:18%;">
					<div class="layer02-data">
						<span style="font-size:26px;"><%=request.getAttribute("numOfBill")%></span>
						<span style="font-size:16px;">张</span>
					</div>
					<canvas width="200" height="100"></canvas>
				</div>
				<div id="layer02_03" style="width:17%;">
					<div class="layer02-data">
						<span style="font-size:26px;"><%=request.getAttribute("ChargeAndDeposit")%></span>
						<span style="font-size:16px;">元</span>
					</div>
					<canvas width="180" height="100"></canvas>
				</div>
				<div id="layer02_04" style="width:17%;">
					<div class="layer02-data">
						<span style="font-size:26px;"><%=request.getAttribute("profit")%></span>
						<span style="font-size:16px;">元</span>
					</div>
					<canvas width="200" height="100"></canvas>
				</div>
				<!-- 押金 -->
				<div id="layer02_05" style="width:15%;">
					<div class="layer02-data">
						<span style="font-size:26px;"><%=request.getAttribute("deposit")%></span>
						<span style="font-size:16px;">元</span>
					</div>
					<canvas width="150" height="100"></canvas>
				</div>
				<!-- 其他消费 -->
				<div id="layer02_06" style="width:15%;">
					<div class="layer02-data">
						<span style="font-size:26px;" id="forTotal"></span>
						<span style="font-size:16px;">元</span>
					</div>
					<canvas width="130" height="100"></canvas>
				</div>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south',split:false,border:false" style="height: 70%;">
		<div style="height: 100%;width: 40%;float: left;margin-left: 10%;">
			<table id="billList" class="easyui-datagrid" style="height: 99%;"
				fitColumns="true" idField="roomNum" data-options="
					rownumbers:true,
					autoRowHeight:true,
					singleSelect:true,
					pagination:true">
				<thead>
					<tr>
						<th field="cName" width="15" rowspan="2" align="center">房客姓名</th>
						<th field="roomNum" width="15" rowspan="2" align="center">房号</th>
						<th field="chargeAndDeposit" rowspan="2" width="15" align="center">收款</th>
						<th field="inTime" width="40" rowspan="2" align="center">开房时间</th>
						<th field="refund" width="15" rowspan="2" align="center">应退押金</th>
						<th field="occupied" width="10" colspan="2" align="center"></th>
					</tr>
					<tr>
						<th field="occupied1" width="5" align="center"></th>
						<th field="occupied2" width="5" align="center"></th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="height: 100%;width: 40%;float: right;margin-right: 10%;">
			<table id="expense" class="easyui-datagrid" style="height: 99%;"
					fitColumns="true" idField="roomNum" data-options="
						autoRowHeight:true,
						singleSelect:true,
						pagination:true,">
					<thead>
						<tr>
							<th field="otherCost" colspan="6" width="70" align="center">其他消费(以数量计)</th>
							<th field="editer" rowspan="2" width="20" align="center" formatter="editer">编辑</th>
						</tr>
						<tr>
							<th field="mineral" width="10" align="center" editor="numberspinner">矿泉水</th>
							<th field="pulsation" width="10" align="center" editor="numberspinner">脉动</th>
							<th field="greenTea" width="10" align="center" editor="numberspinner">绿茶</th>
							<th field="tea" width="10" align="center" editor="numberspinner">茶叶</th>
							<th field="noodles" width="10" align="center" editor="numberspinner">泡面</th>
							<th field="wljjdb" width="20" align="center" editor="numberspinner">王老吉/加多宝</th>
							
						</tr>
					</thead>
				</table>
		</div>
		
		<script>
			//动态改变页面上的数值
			$(function(){
				document.getElementById('forTotal').innerHTML = <%=request.getAttribute("total")%>;
			})
			function editer(value,row,index){
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
				$('#expense').datagrid({
					onBeforeEdit:function(index,row){
						$(this).datagrid('updateRow', {index:index,row:{editing:true}});
						
					},
					onAfterEdit:function(index,row){
						$(this).datagrid('updateRow', {index:index,row:{editing:false}})
					},
					onCancelEdit:function(index,row){
						$(this).datagrid('updateRow', {index:index,row:{editing:false}});
					}
				});
			});
			function getRowIndex(target){
				var tr = $(target).closest('tr.datagrid-row');
				return parseInt(tr.attr('datagrid-row-index'));
			}
			function editrow(target){
				$('#expense').datagrid('beginEdit', getRowIndex(target));
			}
			function saverow(target){
				$('#expense').datagrid('endEdit', getRowIndex(target));
				var selectedItems = $('#expense').datagrid('getSelected');
				var mineralNum;
				var pulsationNum;
				var greenTeaNum;
				var teaNum;
				var noodlesNum;
				var WLJJDBNum;
				mineralNum=selectedItems.mineral;
				pulsationNum=selectedItems.pulsation;
				greenTeaNum=selectedItems.greenTea;
				teaNum=selectedItems.tea;
				noodlesNum=selectedItems.noodles;
				WLJJDBNum=selectedItems.wljjdb;
				//如果行选中
				if (selectedItems){
					$.ajax({
				    	url: '../DistinguishedQuality360Hotel/front/doAccounts',
				    	type: 'post',
				    	data: {'roomNum':selectedItems.roomNum,'mineralNum':mineralNum,'pulsationNum':pulsationNum,'greenTeaNum':greenTeaNum,
							'teaNum':teaNum,'noodlesNum':noodlesNum,'WLJJDBNum':WLJJDBNum},
						dataType:'json',
				    	traditional:true,//用传统方式序列化数据
				    	success:function(data){
							jsondata= data.Info;
							//提交其他消费的数量后,动态改变页面上的数值
							document.getElementById('forTotal').innerHTML = data.total;
							$(function(){
								$('#expense').datagrid({loadFilter:pagerFilter}).datagrid('loadData', jsondata);
								$('#billList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', jsondata);
								//将expense表格的分页栏操作取消
								$($('#expense').datagrid('getPager')).pagination({
									showPageList: false,
									displayMsg: '',
									layout:[],
								});
							});
							$("#expense").datagrid('clearSelections');
				    	},
						error:function(xhr,type,errorThrown){
							$.messager.alert('提示','保存失败！','info',
								function(){location.href='../DistinguishedQuality360Hotel/front/AccountOfPerDay'});
							console.log(errorThrown);
						}
					});
				}
			}
			function cancelrow(target){
				$('#expense').datagrid('cancelEdit', getRowIndex(target));
			}
		</script>
		
		<script>
			//合并单元格
			$(function(){
				$('#billList').datagrid({
					onLoadSuccess:function(data){
		            	var mark = 1;
		                for (var i=1; i <data.rows.length; i++) {
		                	if (data.rows[i]['cName'] == data.rows[i-1]['cName']) {
		                		mark += 1; 
		                		$(this).datagrid('mergeCells',{ 
		                			index: i+1-mark,    
		                			field: 'cName',  //需要合并的列
		                			rowspan:mark     //纵向合并的格数，如果想要横向合并，就使用colspan：mark
		                			
		                		});
								$(this).datagrid('mergeCells',{
		                			index: i+1-mark,    
		                			field: 'inTime',  //需要合并的列
		                			rowspan:mark     //纵向合并的格数，如果想要横向合并，就使用colspan：mark
		                			
		                		});
								$(this).datagrid('mergeCells',{
		                			index: i+1-mark,    
		                			field: 'chargeAndDeposit',  //需要合并的列
		                			rowspan:mark     //纵向合并的格数，如果想要横向合并，就使用colspan：mark
		                			
		                		});
								$(this).datagrid('mergeCells',{
		                			index: i+1-mark,    
		                			field: 'refund',  //需要合并的列
		                			rowspan:mark     //纵向合并的格数，如果想要横向合并，就使用colspan：mark
		                			
		                		});
		                	}else{
		                		mark=1;     //一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
		                	}
		            	}
						//利用换页时加载数据,对billList表换页的同时,对expense表进行换页
						var options = $('#billList').datagrid('getPager').data("pagination").options;
						var page = options.pageNumber;//当前页数
						$('#expense').datagrid('getPager').pagination('select', page);
					}
				});
			});
		</script>
		
		
		<script>
			var jsondata=<%=session.getAttribute("billList")%>;
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
					showPageList: false,
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
				$('#billList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', jsondata);
				$('#expense').datagrid({loadFilter:pagerFilter}).datagrid('loadData', jsondata);
				//将expense表格的分页栏操作取消
				$($('#expense').datagrid('getPager')).pagination({
					showPageList: false,
					displayMsg: '',
					layout:[],
				});
			});
		</script>
	</div>
</body>
</html>
