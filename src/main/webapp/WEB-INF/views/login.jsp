<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"> 
<title>${sysName}</title>
<script type="text/javascript">
	if (window != top)
		top.location.href = location.href;
</script>
<script type="text/javascript" src="B-JUI/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="B-JUI/js/jquery.cookie.js"></script>
<script type="text/javascript" src="B-JUI/js/bjui-all.js"></script>
<link type="text/css" href="B-JUI/doc/doc.css" rel="stylesheet">
<link type="text/css" href="B-JUI/themes/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="B-JUI/themes/css/style.css" rel="stylesheet">
<link type="text/css" href="B-JUI/themes/blue/core.css" rel="stylesheet">
<link type="text/css" href="B-JUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
* {
	font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei",
		"Hiragino Sans GB", sans-serif;
}

body {
	background: url(image/loginbg_01.jpg) no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}

a:link {
	color: #285e8e;
}

.main_box {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -260px;
	margin-left: -300px;
	padding: 30px;
	width: 600px;
	height: 460px;
	background: #FAFAFA;
	background: rgba(255, 255, 255, 0.5);
	border: 1px #DDD solid;
	border-radius: 5px;
	-webkit-box-shadow: 1px 5px 8px #888888;
	-moz-box-shadow: 1px 5px 8px #888888;
	box-shadow: 1px 5px 8px #888888;
}

.main_box .setting {
	position: absolute;
	top: 5px;
	right: 10px;
	width: 10px;
	height: 10px;
}

.main_box .setting a {
	color: #FF6600;
}

.main_box .setting a:hover {
	color: #555;
}

.login_logo {
	margin-bottom: 20px;
	height: 45px;
	text-align: center;
}

.login_logo img {
	height: 45px;
}

.login_msg {
	text-align: center;
	font-size: 16px;
}

.login_form {
	padding-top: 20px;
	font-size: 16px;
}

.login_box .form-control {
	display: inline-block;
	*display: inline;
	zoom: 1;
	width: auto;
	font-size: 18px;
}

.login_box .form-control.x319 {
	width: 319px;
}

.login_box .form-control.x164 {
	width: 164px;
}

.login_box .form-group {
	margin-bottom: 20px;
}

.login_box .form-group label.t {
	width: 120px;
	text-align: right;
	cursor: pointer;
}

.login_box .form-group.space {
	padding-top: 15px;
	border-top: 1px #FFF dotted;
}

.login_box .form-group img {
	margin-top: 1px;
	height: 32px;
	vertical-align: top;
}

.login_box .m {
	cursor: pointer;
}

.bottom {
	text-align: center;
	font-size: 12px;
}

.btn {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: normal;
	line-height: 1.428571429;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	-o-user-select: none;
	user-select: none;
}

.btn-primary {
	color: #ffffff;
	background-color: #428bca;
	border-color: #357ebd;
}

.btn-lg {
	padding: 10px 16px;
	font-size: 18px;
	line-height: 1.33;
	border-radius: 6px;
}
</style>

</head>
<body>
	<form id="loginForm" name="loginForm" data-toggle="ajaxsearch"
		action="./oss/login" method="POST" onsubmit="">
		<div class="main_box">
			<div class="login_box">
				<div class="login_logo">
<!-- 					<img src="image/logo.png"> -->
					<h3>${sysName}</h3>
				</div>
				<div class="login_form">
					<div class="form-group">
						<label for="username" class="t">用户名：</label> <input
							data-rule="required" data-tip="请输入账号" id="username"
							name="username" type="text" class="form-control x319 in"
							autocomplete="off">
					</div>
					<div class="form-group">
						<label for="password" class="t">密 码：</label> <input id="password"
							data-rule="required" data-tip="请输入密码" name="password"
							type="password" class="form-control x319 in">
					</div>

					<c:if test="${not empty message}">
						<div STYLE="color: red">
							<label id="messageId">${message}</label>
						</div>
					</c:if>

					<div class="form-group space" style="text-align: center">
						<input type="submit" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;"
							class="btn btn-primary btn-lg" style="width: 70%">
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>