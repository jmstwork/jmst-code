jQuery(function($){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("#seachBtn").click(function(){
		var url  = "../role/roleList.html";
		$("#roleForm").attr("action",url);
		$("#roleForm").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#roleName").val('');
	});
	
	$("#addBtn").click(function(){
		var url  = "../role/roleEdit.html";
		$("#roleForm").attr("action",url);
		$("#roleForm").submit();
	});
	$("#updateBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(selectes.length>1){
			alert("您选择的多条记录，默认处理第一条");
		}
		var url  = "../role/roleEdit.html?roleId="+selectes[0].id;
		$("#roleForm").attr("action",url);
		$("#roleForm").submit();
	});
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请至少选择一条记录操作");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var roleId = ids.join(',');
		if(confirm("确认要删除选中记录吗")){
			var url  = "../role/checkUserExit.html";
			$.post(url,{"roleId":roleId},function(data){
				if(data.rolesName == ''){
					var url_  = "../role/deleteRoles.html";
					$.post(url_,{"roleId":roleId},function(e){
						if(e.status ==1){
							alert("删除成功");
							var url_ = "../role/roleList.html";
							location.href=url_;
						}else{
							alert("删除失败");
						}
					});
					
				}else{
					alert("角色"+data.rolesName +"中还存在用户，不可删除此角色！")
				}
			});
		}
	});
	$("#setBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(selectes.length>1){
			alert("您选择的多条记录，默认处理第一条");
		}
		var url  = "../role/setRoleView.html?roleId="+selectes[0].id;
		$("#roleForm").attr("action",url);
		$("#roleForm").submit();
	});
});