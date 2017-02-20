$(function(){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	
	$("#seachBtn").click(function(){
		$("#userForm").attr("action","../user/userList.html").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#userAccount").val("");
		$("#userName").val("");
		$("#enableFlg").val("");
		$("#userForm").attr("action","../user/userList.html").submit();
	});
	
	$("#addBtn").click(function(){
		var url  = "../user/userEdit.html";
		$("#userForm").attr("action",url);
		$("#userForm").submit();
	});
	
	$("#updateBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请先选择一条操作记录！","提示信息");
			return ;
		}
		if(selectes.length>1){
			asyncbox.alert("您选择的多条记录，默认处理第一条！","提示信息");
		}
		var url  = "../user/userEdit.html?userId="+selectes[0].id;
		$("#userForm").attr("action",url);
		$("#userForm").submit();
	});
	
	$("#startBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请先选择一条操作记录！","提示信息");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var userId = ids.join(',');
		
		asyncbox.confirm("确认要启用所选记录吗？","提示信息",function(action){
	        if(action == "ok"){
	        	var url  = "../user/operateUsers.html";
				$.post(url,{"userId":userId,"operateFlg":"start"},function(data){
					if(data.status == '1'){
						asyncbox.success("启用成功！","执行结果",function(){
							var url_ = "../user/userList.html";
							location.href=url_;
						})
					}else{
						asyncbox.error("启用失败！","执行结果");
					}
				});
	        }
		})
		
	});
	
	$("#stopBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请先选择一条操作记录！","提示信息");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var userId = ids.join(',');
		
		asyncbox.confirm("确认要停用所选记录吗？","提示信息",function(action){
	        if(action == "ok"){
	        	var url  = "../user/operateUsers.html";
				$.post(url,{"userId":userId,"operateFlg":"stop"},function(data){
					if(data.status == '1'){
						asyncbox.success("停用成功！","执行结果",function(){
							var url_ = "../user/userList.html";
							location.href=url_;
						})
					}else{
						asyncbox.error("停用失败！","执行结果");
					}
				});
	        }
		})
	});
});

function rmtAuthSet(userAccount,userName){
		 asyncbox.open({
				id:"userAuth",
				modal:true,
				align:"center",
				drag:true,
				title:"",
				url:"../user/rmtAuthSet.html?userAccount="+userAccount+"&userName="+encodeURI(encodeURI(userName)),
				btnsbar:$.btn.OKCANCEL,
				btnsbar: [{
				     text    : '保存', 
				     action  : 'save'
				},{
					 text    : '关闭', 
				     action  : 'close_1'
				}],
				callback : function(action,opener){
					if(action == 'save'){
							var userAccount = opener.$('#userAccount').val();
							var objName = opener.$("#authList input[type=checkbox]");
//						var userAccount = opener.$('userAccount').value;	
//						var objName = opener.$('setAuthForm');
							var resourceIds;
							for (i = 0; i < objName.length; i++) {
								if (objName[i].id == "resourceId"  && objName[i].checked) {
									if (resourceIds == null) {
										resourceIds = objName[i].name;
									} else {
										resourceIds = resourceIds + "@" + objName[i].name;
									}
								}
							}
							//alert(resourceIds);
							var url_ = "../user/saveUserAuth.html?userAccount=" + userAccount
							+ "&resourceIds=" + resourceIds;
							$.getJSON(url_, "", function(data) {
								if (data.resultSet == 1) {
									asyncbox.success("保存成功","执行结果",function(action){
										if(action=="ok"){
											window.$.close("userAuth");
										}
									});
								} else {
									asyncbox.success("保存失败","执行结果");
								}
							});
							return false;}
				},
				width:600,
				height:345
				
			});
}

function updateUserStatus(status){
	//1. 首先判断是不是全选
	var parent = document.getElementById("parentBox");
	var children = document.getElementsByName("childrenBox");
	var userIds="" ;
	var statusCh="";
	if(status==1){
		statusCh="启用";
	}else{
		statusCh="未启用";
	}
	if(parent.checked){
		 for(var i=0; i<children.length; i++) {
			 if(children[i].parentElement.parentElement.children[7].innerText != statusCh){
				 if(userIds ==null || userIds ==""){
					 userIds  = children[i].parentElement.parentElement.children[1].id;
				}else{
					userIds =userIds+"@"+children[i].parentElement.parentElement.children[1].id;
				}
			 }
		 }
	}else{
		for(var i=0; i<children.length; i++) {
		     if(children[i].checked) {
		    	 if(children[i].parentElement.parentElement.children[7].innerText != statusCh){
		    		 if(userIds ==null || userIds ==""){
			    		 userIds  = children[i].parentElement.parentElement.children[1].id;
					}else{
						userIds =userIds+"@"+children[i].parentElement.parentElement.children[1].id;
					}
		    	 }
		     }
		}
	}
	if(userIds=="" && status ==1){
		asyncbox.alert("请选择要启用的用户！","提示信息");
		return;
	}else if(userIds=="" && status ==0){
		asyncbox.alert("请选择要停用的用户！","提示信息");
		return;
	}
	
	var url_ ="./updateUserStatus.html";
	jQuery.post(url_,{"userIds":userIds,"status":status},function(e){
		if(e.backStatus==1){
			asyncbox.success("更新成功！","执行结果");
			$("#seachBtn").click();
		}else{
			asyncbox.error("更新失败！","执行结果");
		}
	});
}

function updateAccount(userAccount){
	var url_ = "../user/userEdit.html?userAccount="+userAccount;
	location.href=url_;
}



function roleAuthSet(userAccount){
	var url_ = "../user/selectUsersRolesList.html?userAccount="+userAccount;
	location.href=url_;
}

function pwdRset(userId){
	var url_ ="./pwdRset.html";
	jQuery.post(url_,{"userId":userId},function(e){
		if(e.status==1){
			asyncbox.success("密码已重置！","执行结果");
		}else{
			asyncbox.error("重置密码失败！","执行结果");
		}
	});
}