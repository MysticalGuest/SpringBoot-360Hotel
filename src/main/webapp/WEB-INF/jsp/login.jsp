<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<title>尊品360宾馆服务平台</title>
	<link rel="shortcut icon" href="img/fire.ico"  type="image/x-icon"/>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link href="css/login.css" rel="stylesheet" type="text/css">
	<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="demo form-bg">
		<div class="container">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<form class="form-horizontal" name="loginForm" action="passForm" method="post" onsubmit="return validate()">
						<span class="heading">尊品360宾馆服务平台</span>
						<h2>用户登录</h2>
						<div class="form-group">
							<input type="text" class="form-control" name="AdmId" id="AdmId" placeholder="账号">
							<i class="fa fa-user"></i>
						</div>
						<div class="form-group help">
							<input type="password" class="form-control" name="aPassword" id="aPassword" placeholder="密码">
							<a href="javascript:void(0)" class="fa fa-eye-slash"></a>
							<i class="fa fa-lock"></i>
							
							<!--  -->
						</div>
						<div class="form-group">
							<!--  
							<div class="main-checkbox">
								<input type="checkbox" checked="checked" value="front" id="checkbox1" name="check"/>
								<label for="checkbox1"></label>
							</div>
							<span class="text">前台</span>
							<div class="main-checkbox">
								<input type="checkbox" value="administrator" id="checkbox2" name="check"/>
								<label for="checkbox2"></label>
							</div>
							<span class="text">管理员</span>-->

						    <div class="radio-list row">
						    	<div class="col-md-4">
							    	<label>
							    		<input type="radio" value="front" name="limit" checked/> 前台
							        </label>
							        <label>
							             <input type="radio" value="administrator" name="limit"/> 管理员
							        </label>
						    	</div>
							</div>
							<div class="form-inline row">
						         <p id="waring"></p>
						         <button type="submit" class="btn btn-default">立刻登录</button>
						    </div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div style="text-align:center;margin:10px 0; font:normal 14px/24px 'MicroSoft YaHei';color:#ffffff">
		<p>地址：湖北省襄阳市襄州区航空路铁十一局旁</p>
		<p>服务电话：0710-2919966</p>
		<!-- <p>来源：<a href="#" target="_blank">尊品360宾馆</a></p> -->
	</div>
	
	<script>
		// 密码可见
		$(".help").on("click", ".fa-eye-slash", function () {
		    $(this).removeClass("fa-eye-slash").addClass("fa-eye");
		    $(this).prev().attr("type", "text");
		});
		 
		$(".help").on("click", ".fa-eye", function () {
		    $(this).removeClass("fa-eye").addClass("fa-eye-slash");
		    $(this).prev().attr("type", "password");
		});
		
		function validate(){
			let check = $("input[name='limit']:checked").val();
			let AdmId = $("input[name='AdmId']").val();
			let aPassword = $("input[name='aPassword']").val();
			let obj = document.getElementById("waring");
			if(AdmId===''){
			    obj.innerHTML= "请输入账号！";
			    return false;
			}
			if(aPassword===''){
			    obj.innerHTML= "请输入密码！";
			    return false;
			}
			let booldata = '';
			$.ajax({
				url: 'login_verification',
				type: 'post',
				data:{'AdmId':AdmId,"aPassword":aPassword,"limit":check},
				dataType:'json',
				async:false,// 同步请求
				success:function(data){
					booldata=data;
				},
				error:function(xhr,type,errorThrown){
					console.log(errorThrown);
				}
			});
			if (booldata === false) {
				obj.innerHTML= "用户名或密码错误！";
		      	return false;
		    }
			return true;
		}
	</script>
</body>
</html>

