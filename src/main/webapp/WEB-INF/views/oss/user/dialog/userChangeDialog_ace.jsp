<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 模态框（Modal） -->

<!-- <div class="modal-header" style="height: 10%;"> -->
<!-- </div> -->
<div class="modal-body" style="height: 100%;">
	<div class="row" style="text-align: center">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">
			<c:if test="${activeType==1}">添加用户</c:if>
			<c:if test="${activeType==2}">修改用户</c:if>
		</h4>
	</div>
	<div class="hr hr16 hr-dotted"></div>

	<input class="form-control ace" type="hidden" name="id"
		value="${user.id}"> <input class="form-control ace"
		type="hidden" name="activeType" value="${activeType}">
	<c:if test="${activeType==1}">
		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">*登录名：</label>
			<div class="col-sm-5">
				<input class="form-control ace" type="text" id="account"
					name="account" value="${user.account}" maxlength="10"
					data-rule-required="true" data-rule-rangelength="[3,10]"
					data-msg-required="用户名不能为空" data-msg-rangelength="用户名长度必须是3到10个字符" /><span
					id="userText"></span>
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">*密码：</label>
			<div class="col-sm-5">
				<input class="form-control ace" type="password" id="password"
					name="password" maxlength="12" data-rule-minlength="6"
					data-rule-required="true" data-msg-required="密码不能为空"
					data-msg-minlength="至少设置6位密码" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">*重复确认密码：</label>
			<div class="col-sm-5">
				<input class="form-control ace" type="password" id="password2"
					name="password2" data-rule-required="true"
					data-msg-required="确认密码不能为空" maxlength="12"
					data-rule-equalTo="#password" data-msg-equalTo="两次密码不一致" />
			</div>
		</div>

	</c:if>
	<c:if test="${activeType==1 && isAdmin == 1}">
		<div class="form-group">
			<label class="control-label col-sm-4 no-padding-right">最高权限：</label>
			<div class="col-sm-5">
				<div class="checkbox">
					<label> <input class="ace" type="checkbox" name="type"
						value="true" id="type"> <span class="lbl">是否设置为最高管理员</span>
					</label>
				</div>
			</div>
		</div>
	</c:if>
	<div class="form-group">
		<label class="control-label col-sm-4 no-padding-right">姓名：</label>
		<div class="col-sm-5">
			<input class="form-control ace" type="text" id="name" name="name"
				value="${user.name}" maxlength="10" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 no-padding-right">昵称：</label>
		<div class="col-sm-5">
			<input class="form-control ace" type="text" id="nickName"
				name="nickName" value="${user.nickname}" maxlength="10" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 no-padding-right">手机：</label>
		<div class="col-sm-5">
			<input class="form-control ace" type="text" id="mobile" name="mobile"
				value="${user.mobile}" maxlength="11" data-rule-isMobile="true"/>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 no-padding-right">email：</label>
		<div class="col-sm-5">
			<input class="form-control ace" type="text" id="email" name="email"
				value="${user.email}" maxlength="10" data-rule-email="true" data-msg-email="请输入有效的邮箱地址"/>
		</div>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<div style="width: 100%; absolute; bottom: 0; text-align: center;">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">提交更改</button>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		//表单验证JS
		$("#registerForm")
				.validate(
						{
							//出错时添加的标签
							errorElement : "span",
							rules : {
								account : {
									required : true,
									minlength : 3,
									maxlength : 10,
									remote : {
										type : "post",
										url : "user/checkAccount",
										data : {
											account : function() {
												return $("#account").val();
											}
										},
										dataType : "html",
										dataFilter : function(data, type) {
											if (data == "true")
												return true;
											else
												return false;
										}
									}
								}
							},
							success : function(label) {
								//正确时的样式
								label.text(" ").addClass("success");
							},
							messages : {
								account : {
									remote : "用户名不可用"
								}
							},
							errorElement : 'span',
							errorClass : 'help-block',
							//自定义错误消息放到哪里
							errorPlacement : function(error, element) {
								element.next().remove();//删除显示图标
								element
										.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
								element.closest('.form-group').append(error);//显示错误消息提示
							},
							//给未通过验证的元素进行处理
							highlight : function(element) {
								$(element).closest('.form-group').addClass(
										'has-error has-feedback');
							},
							//验证通过的处理
							success : function(label) {
								var el = label.closest('.form-group').find(
										"input");
								el.next().remove();//与errorPlacement相似
								el
										.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
								label.closest('.form-group').removeClass(
										'has-error').addClass(
										"has-feedback has-success");
								label.remove();
							},

						});
	});
</script>

