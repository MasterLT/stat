<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<%@ include file="../../inc/ace_head.inc"%>
<script src="js/form.js" type="text/javascript"></script>
<style>
body {
	background-color: Transparent;
}
.table{
	font-size: 14px;
}
.pagination>li>a>i{
	line-height:18px;
}
</style>
<div class="col-xs-12" style="padding-top: 32px;">
<!-- 	<h3 class="header smaller lighter blue">用户列表</h3> -->
	<c:if test="${msg != null}">
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="icon-remove"></i>
			</button>
			<i class="icon-ok green"></i>${msg}
		</div>
	</c:if>
	<c:if test="${failMsg != null}">
		<div class="alert alert-block alert-danger">
			<button type="button" class="close" data-dismiss="alert">
				<i class="icon-remove"></i>
			</button>
			<i class="icon-remove"></i>${failMsg}
		</div>
	</c:if>
<!-- 	<div class="row" style="padding-top: 32px;"> -->
<!-- 		<div class="col-xs-12"> -->
<!-- 			<a href="user/userChangeDialog?activeType=1" data-toggle="modal" -->
<!-- 				class="btn btn-sm btn-info" data-target="#userDialog">添加用户</a> <a -->
<!-- 				href="user/district/init" class="btn btn-sm btn-success">重置|初始化区域</a> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!-- 					<div class="hr hr16 hr-dotted"></div> -->
	<div class="table-responsive">
		<div id="user-table_wrapper" class="dataTables_wrapper" role="grid">
			<table id="user-table"
				class="table table-striped table-bordered table-hover dataTable"
				aria-describedby="user-table_info">
				<thead>
					<tr role="row">
						<th class="sorting"><small style="font-size: small;">id</small></th>
						<th class="sorting"><small style="font-size: small;">账号</small></th>
						<th class="sorting"><small style="font-size: small;">姓名</small></th>
						<th class="sorting"><small style="font-size: small;">昵称</small></th>
						<th class="sorting"><small style="font-size: small;">手机号</small></th>
						<th class="sorting"><small style="font-size: small;">email</small></th>
						<th class="sorting_disabled"><small style="font-size: small;">操作</small></th>
					</tr>
				</thead>

				<tbody role="alert" aria-live="polite" aria-relevant="all">

					<c:forEach var="user" items="${userList}">
						<tr>
							<td>${user.id}</td>
							<td>${user.account}</td>
							<td>${user.name}</td>
							<td>${user.nickname}</td>
							<td>${user.mobile}</td>
							<td>${user.email}</td>
							<td><a href="user/roleDialog?id=${user.id}"
								data-toggle="modal" class="btn btn-xs btn-info"
								data-target="#roleDialog">角色分配</a> 
								
								<a href="user/districtDialog?userId=${user.id}"
								data-toggle="modal" class="btn btn-xs btn-info"
								data-target="#districtDialog">区域分配</a> 
								
								<a href="user/userChangeDialog?activeType=2&id=${user.id}"
								data-toggle="modal" class="btn btn-xs btn-warning"
								data-target="#userDialog">信息修改</a>
								<a href="#"
								data-href="user/resetPassword?id=${user.id}" data-toggle="modal"
								class="btn btn-xs btn-danger" data-target="#confirm-reset-password">重置密码</a>
								</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- /.main-container -->
<!-- 模态框（Modal） -->
<form id="registerForm" method="post" action="user/userChange"
	class="form-horizontal" role="form">
	<div class="modal fade" id="userDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 800px; background-color: white; margin: 0 auto; min-height: 200px;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</form>
<form method="post" action="user/roleChange" class="form-horizontal"
	role="form" onsubmit="return ">
	<div class="modal fade" id="roleDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 800px; background-color: white; margin: 0 auto; min-height: 200px;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</form>
<form method="post" action="user/districtChange"
	class="form-horizontal" role="form" onsubmit="return ">
	<div class="modal fade" id="districtDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 800px; background-color: white; margin: 0 auto; min-height: 200px;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</form>
<div class="modal fade" id="confirm-reset-password" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">重置密码</div>
			<div class="modal-body">确认重置该账号密码为<small>123456</small>吗？</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<a class="btn btn-danger btn-ok">确认</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("#userDialog").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
	$("#roleDialog").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
	$("#districtDialog").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
	$('#confirm-reset-password').on('show.bs.modal', function(e) {
		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#user-table').dataTable({
			"oLanguage" : {
				"sProcessing" : "处理中...",
				"sLengthMenu" : "显示 _MENU_ 项结果",
				"sZeroRecords" : "没有匹配结果",
				"sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索:",
				"sUrl" : "",
				"sEmptyTable" : "表中数据为空",
				"sLoadingRecords" : "载入中...",
				"sInfoThousands" : ",",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上页",
					"sNext" : "下页",
					"sLast" : "末页"
				},
				"oAria" : {
					"sSortAscending" : ": 以升序排列此列",
					"sSortDescending" : ": 以降序排列此列"
				},
			},
			"aoColumns" : [ null, null, null, null, null, null, {
				"bSortable" : false
			} ],
			"bPaginate" : true, //翻页功能
			"bLengthChange" : false, //改变每页显示数据数量
			"bFilter" : false,
			"bAutoWidth": false,
			"sDom": "<'row'<'#mytool.col-sm-6'><'col-sm-6'f>r>" +"t" +"<'row'<'col-sm-6'i><'col-sm-6'p>>",
			"fnInitComplete": function() {
				$("#mytool").append('<a href="user/userChangeDialog?activeType=1" data-toggle="modal" '
						+'class="btn btn-sm btn-info" data-target="#userDialog">添加用户</a> ');
			}
		});

		$('table th input:checkbox').on(
				'click',
				function() {
					var that = this;
					$(this).closest('table').find(
							'tr > td:first-child input:checkbox').each(
							function() {
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});

				});

		$('[data-rel="tooltip"]').tooltip({
			placement : tooltip_placement
		});
		function tooltip_placement(context, source) {
			var $source = $(source);
			var $parent = $source.closest('table')
			var off1 = $parent.offset();
			var w1 = $parent.width();

			var off2 = $source.offset();
			var w2 = $source.width();

			if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
				return 'right';
			return 'left';
		}
	})
</script>


