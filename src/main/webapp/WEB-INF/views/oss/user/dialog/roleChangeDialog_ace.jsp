<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 模态框（Modal） -->

<!-- <div class="modal-header"> -->
<!-- </div> -->
<div class="modal-body" style="height: 100%">
	<div class="row" style="text-align:center">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">
			<c:if test="${activeType==1}">添加角色</c:if>
			<c:if test="${activeType==2}">修改角色信息</c:if>
		</h4>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<form id="registerForm" class="form-horizontal" role="form">
		<input class="form-control ace" type="hidden" name="id" value="${id}">
		<input class="form-control ace" type="hidden" name="activeType"
			value="${activeType}">
		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">*角色名：</label>
			<div class="col-sm-5">
				<input class="form-control ace" type="text" id="roleName"
					name="roleName" value="${role.role}" maxlength="10"
					data-rule-required="true" data-msg-required="角色名不能为空" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">自定义权限等级：</label>
			<div class="col-sm-5">
				<input class="form-control ace" maxlength="3" min="0" type="number"
					id="level" name="level" value="${role.level}" />
			</div>
		</div>
	</form>
	<div class="hr hr16 hr-dotted"></div>
	<div style="width: 100%; absolute; bottom: 0; text-align: center;">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">提交更改</button>
	</div>
</div>
<!-- <div class="modal-footer" > -->
<!-- </div> -->


