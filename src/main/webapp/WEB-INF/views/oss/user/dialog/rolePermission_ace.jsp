<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/ztree/jquery.ztree.all.js"></script>
<script src="js/ztree/jquery.ztree.core.js"></script>
<script src="js/ztree/jquery.ztree.excheck.js"></script>
<link href="js/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">

<script type="text/javascript">
   var zTreeObj;
   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
   var setting = {
		   check:{
			   enable: true
			   }
   };
   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
   var zNodes = <%=request.getAttribute("permission_json")%>;
   $(document).ready(function(){
      zTreeObj = $.fn.zTree.init($("#ztree1"), setting, zNodes);
      zTreeObj.expandAll(true);
   });
  </script>
<script type="text/javascript">
	// 更新
	function M_Menu_update(roleId) {
		var tree = $.fn.zTree.getZTreeObj("ztree1")
		var menus = tree.getNodes();

		// 提交当前节点状态至后台
		$.ajax({ // 无需跨域，直接使用即可.
			type : 'post',
			url : "role/permission/update?roleId="+roleId,
			data : {
				"menus" : JSON.stringify(menus)
			},
			success : function(result) {
				$('#success').show();
				$('#permissionDialog').modal('hide');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus);
			}
		})
	}
</script>
<div class="modal-body" style="height: 100%">
	<div class="row" style="text-align: center">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">权限配置</h4>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<div style="padding: 10px;">
		<div class="clearfix">
			<div class="col-xs-12"
				style="float: left; height: 500px; overflow: auto; border: 1px #DDD solid;">
				<ul id="ztree1" class="ztree">
				</ul>
			</div>
		</div>
	</div>
	<div class="hr hr16 hr-dotted"></div>
	<div style="width: 100%; absolute; bottom: 0; text-align: center;">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button class="btn btn-info" onclick="M_Menu_update(${roleId});">点击保存</button>
	</div>
</div>
