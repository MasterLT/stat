<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 模态框（Modal） -->

<div class="modal-body" style="max-height: 100%;">
	<div class="row" style="text-align: center">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">用户区域分配</h4>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<form class="form-horizontal" role="form">
		<input class="form-control ace" type="hidden" name="userId"
			value="${userId}">
		<c:forEach var="d" items="${districtList}">
			<div class="col-sm-12">
				<label> <input type="checkbox" name="user_district"
					id="${d.id}" class="form-control ace" value="${d.id}"
					<c:if test="${d.userId != 0}">checked</c:if>><span
					class="lbl">${d.districtName}</span></label>
			</div>
		</c:forEach>
	</form>
	<div class="hr hr16 hr-dotted"></div>
	<div style="text-align: center;">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">提交更改</button>
	</div>
</div>

