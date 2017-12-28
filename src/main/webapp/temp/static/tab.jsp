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
<link href="assets/styles/plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css" />
<!-- <link rel="stylesheet" type="text/css" id="skin" href="assets/styles/plugins/tab/skins/sky/style.css" /> -->
<link rel="stylesheet" type="text/css" id="skin" prePath="assets/styles/plugins/tab/" />
<script type="text/javascript" src="assets/styles/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="assets/styles/plugins/tab/js/framework.js"></script>
<script type="text/javascript" charset="utf-8" src="assets/styles/plugins/tab/js/tab.js"></script>
</head>
<body>
	<div id="tab_menu" style="height: 30px;"></div>
	<div style="width:100%;">
		<div id="page" style="width: 100%; height: 100%;"></div>
	</div>
</body>
<script type="text/javascript">
	var tab;
	$(function() {
		tab = new TabView({
			containerId : 'tab_menu',
			pageid : 'page',
			cid : 'tab1',
			position : "top"
		});
		tab.add({
			id : 'm_index',
			title : "首页数据图",
			url : "disAnalysis",
			isClosed : false
		});
	});

	function tabAddHandler(mid, mtitle, murl) {
		tab.update({
			id : mid,
			title : mtitle,
			url : murl,
			isClosed : true
		});
		tab.add({
			id : mid,
			title : mtitle,
			url : murl,
			isClosed : true
		});

		tab.activate(mid);
	}

	function cmainFrameT() {
		var hmainT = document.getElementById("page");
		var bheightT = document.documentElement.clientHeight;
		hmainT.style.width = '100%';
		hmainT.style.height = (bheightT - 51) + 'px';
	}
	cmainFrameT();
	window.onresize = function() {
		cmainFrameT();
	};
</script>
</html>

