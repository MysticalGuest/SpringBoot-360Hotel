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
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<style type="text/css">
			.styled-input .fa{
			    position: absolute;
			    right: 10px;
			    top:5px;
			    font-size: 20px;
			    cursor: pointer;
			}
		    .fa-eye-slash{
		        margin-top: 6px;
		        margin-left: 7px;
		        width: 20px;
		        height: 20px;
		        background-image: url(img/eye-slash.png);
		        background-repeat: no-repeat;
		        background-size: 20px 17px;
		    }
		    .fa-eye{
		        margin-top: 6px;
		        width: 20px;
		        margin-left: 7px;
		        height: 20px;
		        background-image: url(img/eye.png);
		        background-repeat: no-repeat;
		        background-size: 20px 17px;
		
		    }
		</style>
		<title></title>
	</head>
	<body style="background-image : url(img/background.jpg);background-repeat : no-repeat;background-size:100% 100%; background-attachment: fixed;">
		<div style="position:absolute; left:40%; top:10%;">
			<font style="font-family:HYChaoJiZhanJiaW;font-size:50px;color: black;">
				尊品360宾馆服务平台
			</font>
		</div>
		<div style="position:absolute; left:18%; top:8%;">
			<img src="img/360.png" width="65%">
		</div>
		<div style="position:absolute; left:12%; top:41%;">
			<img src="img/图标1.png" width="7%">
		</div>
		<div style="position:absolute; left:12%; top:51%;">
			<img src="img/图标2.png" width="7%">
		</div>
		<div style="position:absolute; left:12%; top:61%;">
			<img src="img/图标3.png" width="4%">
		</div>
		<form class="form" id="LoginForm" action="login" method="post">
			<div class="form__content">
				<h1>账号登录</h1>
				<div class="styled-input">
					<input type="text" class="styled-input__input" name="AdmId" id="AdmId">
					<div class="styled-input__placeholder"> <span class="styled-input__placeholder-text">账号</span> </div>
					<div class="styled-input__circle"></div>
				</div>
				<div class="styled-input">
					<input type="password" class="styled-input__input" name="aPassword" id="aPassword"><i class="fa fa-eye-slash"></i>
					<div class="styled-input__placeholder"> <span class="styled-input__placeholder-text">密码</span> </div>
					<div class="styled-input__circle"></div>
				</div>
				<div style="float: left;">
					<b style="font-size: 20px;font-family: YouYuan;margin-top: 1%;position: absolute;">前台:</b>
					<input type="radio" name="limits" value="front" checked="checked" style="margin-left: 140% ;margin-top: 23%;cursor:pointer;width: 20px;zoom:150%;"></input>
				</div>
				<div style="padding-left: 50%;">
					<b style="font-size: 20px;font-family: YouYuan;margin-top: 1%;position: absolute;">管理员:</b>
					<input type="radio" name="limits" value="administrator" style="margin-left: 60% ;margin-top:6%;cursor:pointer;width: 20px;zoom:150%;"></input>
				</div>
				<div id="demo" style="position:absolute; left:23%; top:67%;color:orange;font-size: 75%;"></div>
			</div>
		</form>
		<script type="text/javascript">
			function login() {
				var limit=$("input[name='limits']:checked").val();//必须放在第一句
				var Id=document.forms["LoginForm"]["AdmId"].value;
				var password=document.forms["LoginForm"]["aPassword"].value;
				if(Id==""||Id==null){
					document.getElementById("demo").innerHTML = "请输入用户名！";
					return false;
				}
				else if(password==""||Id==null){
					document.getElementById("demo").innerHTML = "请输入密码！";
					return false;
				}
				else{
					$.ajax({
						url: '../DistinguishedQuality360Hotel/loginVerification',
						data:{"Id":Id,"password":password,"limits":limit},
						type:'post',//HTTP请求类型
						success:function(data){
							if(data=="errorOfLimit"){
								document.getElementById("demo").innerHTML = "权限错误！";
							}
							else if(data=="errorOfEmpty"){
								document.getElementById("demo").innerHTML = "用户名或密码错误！";
							}
							else{
								document.getElementById("demo").innerHTML = '';
								$.messager.alert('提示','登录成功！','info',
										function(){
											$('#LoginForm').submit();//提交表单
										});
							}
						},
						error:function(xhr,type,errorThrown){
							$.messager.alert('提示','登录失败！','info',
								function(){location.href='../DistinguishedQuality360Hotel/login'});
							console.log(errorThrown);
						}
					});
					return false;
				}
			}
		</script>
		<button onclick="login()" class="styled-button">
			<span class="styled-button__real-text-holder">
				<span class="styled-button__real-text">登 录</span>
				<span class="styled-button__moving-block face">
					<span class="styled-button__text-holder">
						<span class="styled-button__text">登 录</span>
					</span>
				</span>
				<span class="styled-button__moving-block back">
					<span class="styled-button__text-holder">
						<span class="styled-button__text">登 录</span>
					</span>
				</span>
			</span>
		</button>
		
		<script type="text/javascript">
		    $(".styled-input").on("click", ".fa-eye-slash", function () {
		        $(this).removeClass("fa-eye-slash").addClass("fa-eye");
		        $(this).prev().attr("type", "text");
		    });
		     
		    $(".styled-input").on("click", ".fa-eye", function () {
		        $(this).removeClass("fa-eye").addClass("fa-eye-slash");
		        $(this).prev().attr("type", "password");
		    });
		</script>
		<script  src="js/login.js"></script>
	</body>
</html>
