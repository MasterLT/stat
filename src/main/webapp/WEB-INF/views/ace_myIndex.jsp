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
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>${sysName}</title>
<%@ include file="inc/ace_head.inc"%>
<style type="text/css">
body {
	font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 微软雅黑, Tahoma, Arial, sans-serif;
}
</style>
</head>

<body>
	<%@ include file="/temp/ace_header.html"%>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<%@ include file="/temp/ace_content.html"%>

			<div class="main-content">
				<script type="text/javascript">
					try {
						ace.settings.check('main-container', 'fixed')
					} catch (e) {
					}
				</script>
				<div class="main-container-inner">
					<a class="menu-toggler" id="menu-toggler" href="#"> <span
						class="menu-text"></span>
					</a>
<!-- 					<div id="jzts" -->
<!-- 						style="display: none; width: 100%; position: fixed; z-index: 99999999;"> -->
<!-- 						<div class="commitopacity" id="bkbgjz"></div> -->
<!-- 						<div style="padding-left: 70%; padding-top: 1px;"> -->
<!-- 							<div style="float: left; margin-top: 3px;"> -->
<!-- 								<img src="image/loadingi.gif" /> -->
<!-- 							</div> -->
<!-- 							<div style="margin-top: 5px;"> -->
<!-- 								<h4 class="lighter block red">&nbsp;加载中 ...</h4> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<iframe name="mainFrame" id="mainFrame" frameborder="0"
						src="temp/static/tab.jsp"
						style="margin: 0 auto; width: 100%; height: 100%; background-color: white;" allowtransparency="true"></iframe>

				</div>
				<%@ include file="/temp/ace_setting.html"%>
			</div>
			<a href="#" id="btn-scroll-up"
				class="btn-scroll-up btn btn-sm btn-inverse"> <i
				class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div>
	</div>
	
	<script type="text/javascript">
		jzts(); //清除加载进度			
		function hangge() {
			$("#jzts").hide();
		}
		//显示加载进度
		function jzts() {
			$("#jzts").show();
		}
		// iframe高度设置
		function cmainFrame() {
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain.style.width = '100%';
			hmain.style.height = (bheight - 51) + 'px';
		}
		cmainFrame();
		window.onresize = function() {
			cmainFrame();
		};
		// 菜单状态切换
		var fmid = "bindex";
		var mid = "bindex";
		var ffmid = "bindex"
		function siMenu(id, fid, MENU_NAME, MENU_URL) {
			if (id != mid) {
				$("#" + mid).removeClass();
				mid = id;
			}
// 			if (fid != fmid) {
// 				$("#" + fmid).removeClass();
// 				fmid = fid;
// 			}
// 			if (ffmid != "bindex"){
// 				$("#" + ffmid).removeClass();
// 			}
// 			$("#" + fid).addClass("active");
			$("#" + id).addClass("active");
			top.mainFrame.tabAddHandler(id, MENU_NAME, MENU_URL);
			jzts();
		}
		function siMenu2(id, fid, ffid, MENU_NAME, MENU_URL) {
			if (id != mid) {
				$("#" + mid).removeClass();
				mid = id;
			}
// 			if (fid != fmid) {
// 				$("#" + fmid).removeClass();
// 				fmid = fid;
// 			}
// 			if(ffid != ffmid){
// 				$("#" + ffmid).removeClass();
// 				ffmid = ffid;
// 			}
			
// 			$("#" + ffid).addClass("active");
// 			$("#" + fid).addClass("active");
			$("#" + id).addClass("active");
			top.mainFrame.tabAddHandler(id, MENU_NAME, MENU_URL);
			jzts();
		}
	</script>

</body>
</html>