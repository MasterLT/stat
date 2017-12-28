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
<script src="js/jquery.ztree.all-3.5.js"></script>
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
<div class="col-xs-12">
<!-- 	<h3 class="header smaller lighter blue">角色列表</h3> -->
	<c:if test="${msg != null}">
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="icon-remove"></i>
			</button>
			<i class="icon-ok green"></i>${msg}
		</div>
	</c:if>
	<div id="success" class="alert alert-block alert-success"
		style="display: none;">
		<button type="button" class="close" data-dismiss="alert">
			<i class="icon-remove"></i>
		</button>
		<i class="icon-ok green"></i>success
	</div>
<!-- 	<div class="row" style="padding-top: 32px;"> -->
<!-- 		<div class="col-xs-12"> -->
<!-- 			<a href="role/roleChangeDialog?activeType=1" data-toggle="modal" -->
<!-- 				class="btn btn-sm btn-info" data-target="#roleChangeDialog">添加角色</a> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div class="table-responsive" style="padding-top: 32px;">
		<div id="role-table_wrapper" class="dataTables_wrapper" role="grid">
			<table id="role-table"
				class="table table-striped table-bordered table-hover dataTable"
				aria-describedby="role-table_info">
				<thead>
					<tr role="row">
						<th class="sorting"><small style="font-size: small;">id</small></th>
						<th class="sorting"><small style="font-size: small;">角色名</small></th>
						<th class="sorting"><small style="font-size: small;">权限等级</small></th>
						<th class="sorting_disabled"><small style="font-size: small;">操作</small></th>
					</tr>
				</thead>

				<tbody role="alert" aria-live="polite" aria-relevant="all">

					<c:forEach var="r" items="${roleList}">
						<tr>
							<td>${r.id}</td>
							<td>${r.role}</td>
							<td>${r.level}</td>
							<td><a
								href="role/roleChangeDialog?id=${r.id}&activeType=2"
								data-toggle="modal" class="btn btn-xs btn-warning"
								data-target="#roleChangeDialog">修改角色信息</a> <a
								href="role/permission?id=${r.id}" data-toggle="modal"
								class="btn btn-xs btn-info" data-target="#permissionDialog">权限配置</a>
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
<form id="userChangeForm" method="post" action="role/roleChange"
	class="form-horizontal" role="form" onsubmit="return ">
	<div class="modal fade" id="roleChangeDialog" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 800px; background-color: white; margin: 0 auto; height: 400px;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</form>

<div class="modal fade" id="permissionDialog" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	style="width: 800px; background-color: white; margin: 0 auto; min-height: 200px;">
	<div class="modal-dialog">
		<div class="modal-content"></div>
	</div>
</div>

<script type="text/javascript">
	$("#roleChangeDialog").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
	$("#permissionDialog").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
</script>
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#role-table').dataTable({
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
				}
			},
			"aoColumns" : [ null, null, null, {
				"bSortable" : false
			} ],
			"bPaginate": true, //翻页功能
			"bLengthChange": false, //改变每页显示数据数量
			"bFilter": false,
			"sDom": "<'row'<'#mytool.col-sm-6'><'col-sm-6'f>r>" +"t" +"<'row'<'col-sm-6'i><'col-sm-6'p>>",
			"fnInitComplete": function() {
				$("#mytool").append('<a href="role/roleChangeDialog?activeType=1" data-toggle="modal" class="btn btn-sm btn-info" data-target="#roleChangeDialog">添加角色</a>');
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



