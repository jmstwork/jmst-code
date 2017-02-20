jQuery(function($){
	$("#searchBtn").click(function(){
		$("#roleForm").submit();
	})
	
	$("#cleanBtn").click(function(){
		var url_ = "../user/selectUsersRolesList.html?userAccount=" + $("#userAccount").val();
		location.href=url_;
	})
	
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请至少选择一条记录操作","提示");
			return false;
		}
		
		asyncbox.confirm("确认要删除选中记录吗?","确认信息",function(action){
			if(action == "ok"){
				var ids = new Array();
				selectes.each(function(i){
					ids.push(selectes[i].value);
				})
				
				var roleIds = ids.join(",");
				$.post("../user/deleteUserRole.html",{"userAccount":$("#userAccount").val(),"roleIds":roleIds},function(data){
					if(data.status == '1'){
						asyncbox.success("删除成功！","执行结果",function(){
							var url_ = "../user/selectUsersRolesList.html?userAccount="+$("#userAccount").val();
							location.href=url_;
						});
					}else{
						asyncbox.error("删除失败！","执行结果",function(){
							var url_ = "../user/selectUsersRolesList.html?userAccount="+$("#userAccount").val();
							location.href=url_;
						});
					}
				});
			}
		})
	});
	
	$("#resetBtn").click(function(){
		$("#resetBtnH").click();
		$("input[type='text']").removeAttr("readonly");
	});
	
	$("#returnBtn").click(function(){
		var url_ = "../user/userList.html";
		location.href=url_;
	});
	
	$("#addBtn").click(function(){
		asyncbox.open({
			id : "userList",
			modal : true,
			drag : true,
			scrolling :"no",
			title : "",
			url : "../user/selectRole.html?userAccount="+$("#userAccount").val(),
			width : 600,
			height : 404,
			btnsbar: [{
			     text    : '保存', 
			     action  : 'save'
			},{
				 text    : '关闭', 
			     action  : 'close_1'
			}],
			callback : function(action,opener){
				if(action == 'save'){
					var obj = opener.$('input[name="childrenBox"]:checked');
					var ids = new Array();
					obj.each(function(i){
						var id = obj[i].value;
						ids.push(id);
					})
					
					var roleIds = ids.join(',');
					if(roleIds==""){
						asyncbox.alert("请至少选择一个角色！","提示");
						return false;
					}else{
						var url  = "../user/saveUserRole.html";
						$.post(url,{"userAccount":$("#userAccount").val(),"roleIds":roleIds},function(data){
							if(data.status == '1'){
								asyncbox.success("保存成功！","执行结果",function(){
									var url_ = "../user/selectUsersRolesList.html?userAccount="+$("#userAccount").val();
									location.href=url_;
								});
							}else{
								asyncbox.error("保存失败！","执行结果",function(){
									var url_ = "../user/selectUsersRolesList.html?userAccount="+$("#userAccount").val();
									location.href=url_;
								});
							}
						});
					}
				}
		    }
		})
	});
	
});
//系统权限查看
function editRoleSysAuthority(roleId){
	var flag = 'returnUserRole';
	var url = "../authority/setRoleSysAuthority.html?roleId="+roleId+"&flag="+flag;
	showTreeWindow(url);
}
function showTreeWindow(url,roleId){
	asyncbox.open({
		id : "roleOperate",
		modal : true,
		drag : true,
		scrolling :"yes",
		title : "",
		url : url,
		width : 500,
		height : 380,
//		btnsbar: [{
//		     text    : '保存', 
//		     action  : 'save'
//		},{
//			 text    : '关闭', 
//		     action  : 'close_1'
//		}],
		callback : function(action,opener){
			if(action == 'save'){
				var url="../authority/saveRoleSysAuthority.html";
				saveRoleAuthority(opener,url);
			}
		}
	});
}
//术语权限查看
function editRoleDictAuthority(roleId){
	var userAccount = $("#userAccount").val();
	var flag = 'returnUserRole,'+userAccount;
	var url  = "../role/setRoleView.html?roleId="+roleId+"&flag="+flag;
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}