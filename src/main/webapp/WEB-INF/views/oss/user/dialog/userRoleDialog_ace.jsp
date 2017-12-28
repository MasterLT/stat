<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 模态框（Modal） -->

<div class="modal-body" style="min-height: 100%;">
	<div class="row" style="text-align: center">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">用户角色分配</h4>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<form class="form-horizontal" role="form">
		<input class="form-control ace" type="hidden" name="userId"
			value="${userId}">
		<table
			class="table table-striped table-bordered table-hover dataTable">
			<tbody>
				<c:forEach var="r" items="${roleList}">
					<tr>
						<td><input type="checkbox" name="user_role" id="${r.id}"
							class="form-control ace" value="${r.id}"
							<c:if test="${r.userId != 0}">checked</c:if>><span
							class="lbl">${r.role}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<div class="hr hr16 hr-dotted"></div>
	<div style="width: 100%; absolute; bottom: 0; text-align: center;">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">提交更改</button>
	</div>
</div>

