// 手机号码验证
    $.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^1[3-9]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    // IP验证
    $.validator.addMethod("isIP", function(value , element) {
        var ipTest = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        return (ipTest.test(value));
    }, "IP地址校验不通过");
$(document).ready(function() {
  $("#userChangeForm").validate({
	  rules:{
		  mobile:{
			  isMobile: true
		  }
	  },
	  messages:{
		  mobile:{
			  isMobile: '手机号格式不正确'
		  }
	  },
    errorElement : 'span',
    errorClass : 'help-block',
    //自定义错误消息放到哪里
    errorPlacement : function(error, element) {
      element.next().remove();//删除显示图标
      element.closest('.form-group').append(error);//显示错误消息提示
    },
    //给未通过验证的元素进行处理
    highlight : function(element) {
      $(element).closest('.form-group').addClass('has-error has-feedback');
    },
    //验证通过的处理
    success : function(label) {
      var el=label.closest('.form-group').find("input");
      el.next().remove();//与errorPlacement相似
      label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
      label.remove();
    },
  });
});
