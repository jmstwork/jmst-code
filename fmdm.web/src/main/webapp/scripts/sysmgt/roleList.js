$(function(){
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
		$("#roleForm").attr("action","../role/roleList.html").submit();
	});
	
	$("#addBtn").click(function(){
		var url = "../role/roleEdit.html";
		roleOperate(url);
	});
	
});

//角色术语权限设置
//function editRoleDictAuthority(){
//	var roleId = $("input[type=radio]:checked").val();
//	if(roleId==undefined){
//		asyncbox.alert("请选择一个角色","提示");
//	}else{
//		var url  = "../role/setRoleView.html?roleId="+roleId;
//		$("#roleForm").attr("action",url);
//		$("#roleForm").submit();
//	}
//}


//function editRoleSysAuthority(){
//	var roleId = $("input[type=radio]:checked").val();
//	if(roleId==undefined){
//		asyncbox.alert("请选择一个角色","提示");
//	}else{
//		var url ="../authority/setRoleSysAuthority.html?roleId="+roleId;
//		showTreeWindow(url);
//	}
//}
//系统权限设置
function editRoleSysAuthority(roleId){
	var url = "../authority/setRoleSysAuthority.html?roleId="+roleId
	showTreeWindow(url);
}

//术语权限设置
function editRoleDictAuthority(roleId){
	var flag = "returnRole";
	var url  = "../role/setRoleView.html?roleId="+roleId+"&flag="+flag;
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}
function delOperate(){
	var roleIds="";
	$("input[name=childrenBox]:checked").each(function(index,entry){
		roleIds+=entry.value+",";
	});
	roleIds=roleIds.substring(0, roleIds.length-1);
	
	if(roleIds.length<1){
		asyncbox.alert("请至少选择一条记录操作","提示");
		return false;
	}
	
	if(roleIds!=undefined&&roleIds!=null&&roleIds!=""){
		deleteRole(roleIds,null)
	}
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
		btnsbar: [{
		     text    : '保存', 
		     action  : 'save'
		},{
			 text    : '关闭', 
		     action  : 'close_1'
		}],
		callback : function(action,opener){
			if(action == 'save'){
				var url="../authority/saveRoleSysAuthority.html";
				saveRoleAuthority(opener,url);
			}
		}
	});
}

function saveRoleAuthority(opener,url){
	//被选择节点的参数
	var param="param=";
	var roleId = opener.$("#roleId").val();
	//获取ztree对象
	var treeObj = opener.$.fn.zTree.getZTreeObj("treeDemo");
	//便利tree寻找被选择的节点并拼接
	var checkCount = treeObj.getCheckedNodes(true);
	for(var i = 0; i < checkCount.length; i++){
		param+=checkCount[i].id+"@";
	}
	//重新拼接url，param=。。&roleId=..
	url+="?"+param.substring(0, param.length-1)+"&roleId="+roleId;
	//ajax提交
	$.post(url,function(data){
		if(data=="0"){
			asyncbox.alert("保存成功","提示");
		}else{
			asyncbox.alert("保存失败","提示");
		}
	});
}


function roleOperate(url){
	asyncbox.open({
		id : "roleOperate",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : url,
		width : 500,
		height : 280,
		btnsbar: [{
		     text    : '保存', 
		     action  : 'save'
		},{
			 text    : '关闭', 
		     action  : 'close_1'
		}],
		callback : function(action,opener){
			if(action == 'save'){
				var roleName = opener.$("#roleName").val().replace(/(^\s*)|(\s*$)/g,"");
				if(roleName == null || roleName == ""){
					opener.$("#roleNameDesc").css("color","red").text("角色名不能为空！");
					return false;
				}else{
					opener.$("#roleNameDesc").text("");
				}
				
				if(roleName.length > 30){
					opener.$("#roleNameDesc").css("color","red").text("角色名长度过长(1~30)");
					return false;
				}
				
				var roleMemo = opener.$("#memo").val().replace(/(^\s*)|(\s*$)/g,"");
				
				if(roleMemo.length > 50){
					opener.$("#roleMemoDesc").css("color","red").text("备注长度过长(1~50)");
					return false;
				}
				
				
				var roleId = opener.$("#roleId").val().replace(/(^\s*)|(\s*$)/g,"");
				var url  = "../role/saveRole.html";
				$.post(url,{"roleId":roleId,"roleName":roleName,"memo":roleMemo},function(data){
					if(data.status == '1'){
						asyncbox.success("保存成功！","执行结果",function(){
							var url_ = "../role/roleList.html";
							location.href=url_;
						})
						
					}else{
						asyncbox.error("保存失败！","执行结果");
					}
				});
				}
			}
	});
}

function editRole(roleId){
	var url  = "../role/roleEdit.html?roleId="+roleId;
	roleOperate(url);
}

function deleteRole(roleId,obj){
	var trObj = $(obj).parent().parent();
//	trObj.css("background","#fab5b5");
	var msg = ""; 
	var url  = "../role/checkUserExit.html";
	$.post(url,{"roleId":roleId},function(data){
		if(data.allowDel == "allow"){
			msg = "您选中的角色没有任何关联，确定要删除记录吗？";
		}else{
			msg = data.msg+"确认要删除记录吗？";
		}
		asyncbox.confirm(msg,"提示",function(action){
			if(action == "ok"){
				var url_  = "../role/deleteRoles.html";
				$.post(url_,{"roleId":roleId},function(e){
					if(e.status == 1){
						asyncbox.success("删除成功","执行结果",function(){
							var url_ = "../role/roleList.html";
							location.href=url_;
						});
					}else{
						asyncbox.error("删除失败！","执行结果",function(){
							trObj.css("background","");
						});
					}
				});
			}
		});
	});
}

function authSet(roleId){
	var url  = "../role/setRoleView.html?roleId="+roleId;
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}

function roleUserList(roleId){
	var url  = "../role/userList.html?roleId="+roleId;
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}

function checkLength( o, n, min, max ) {
	if(o.val()=="" || o.val()==null){
		updateTips(o, n + "不能为空 ");
		return false;
	}
	var l = o.val().length;
	if ( l > max || l < min ) {
		updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间" );
		return false;
	} else {
		return true;
	}
}