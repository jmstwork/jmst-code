//onload = function() {
//	
//	var e, i = 0;
//	var o,id;
//	//最外层tab
//	while (e = document.getElementById('authority').getElementsByTagName ('div') [i++]) {
//		if (e.className == 'on' || e.className == 'off') {
//			e.onclick = function () {
//				var getEls = document.getElementsByTagName('div');
//					for (var z=0; z<getEls.length; z++) {
//					getEls[z].className=getEls[z].className.replace('show', 'hide');
//					getEls[z].className=getEls[z].className.replace('on', 'off');
//					}
//				this.className = 'on';
//				var max = this.getAttribute('name');
//				document.getElementById(max).className = "show";
//				if($(this).attr("name")=="rolePage"){
//					o = document.getElementById('rolePage').getElementsByTagName ('div') [0];
//					o.className="on";
//					document.getElementById('roleTab').className = "show";
//					
//					
//					
//				}else if($(this).attr("name")=="accountPage"){
//					
//					o = document.getElementById('accountPage').getElementsByTagName ('div') [0];
//					o.className="on";
//					document.getElementById('accountTab').className = "show";
//					
//					doUserSearch();
//					
//				}
//			}
//		}
//	}
//	
//}

$(document).ready(function(){
	$("#rolePageChange").click(function(){
		doRoleSearch();
	});
	$("#accountPageChange").click(function(){
		doAccountSearch();
	});
	showTab();
});

//根据后台传来的option，显示相应分页
function showTab(){
	var option = $("#option").val();
	//账户
	if(option=="user"){
		$("#rolePage").hide();
		$("#accountPage").show();
	}else{//角色
		$("#accountPage").hide();
		$("#rolePage").show();
	}
}

//角色术语权限设置
function authSet(roleId){
	var url  = "../role/setRoleView.html?roleId="+roleId;
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}

function doRoleSearch(){
	var url="../authority/showRoleInAuthority.html";
	$("#roleForm").attr("action",url);
	$("#roleForm").submit();
}

function doAccountSearch(){
	var url = "../authority/showUserInAuthority.html";
	$("#accountForm").attr("action",url);
	$("#accountForm").submit();
}

//角色系统权限设置，原来叫editRole
function editRoleSysAuthority(roleId){
	var url ="../authority/setRoleSysAuthority.html?roleId="+roleId;
	roleOperate(url);
}

//会把两个选项卡的搜索框都清空
function cleanSearch(){
	//role
	$("#roleName").val("");
	//account
	$("#userName").val("");
	$("#userAccount").val("");
}

function roleOperate(url,roleId){
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

//编辑角色，会根据roleid跳转
function editRole(roleId){
	var url_ = "../authority/roleEdit.html?roleId="+roleId;
	location.href=url_;
}

//删除一个或多个角色
function delRole(){
	var param="";
	$("input[name=childrenBox]:checked").each(function(index,entry){
		param+=entry.value+",";
	});
	if(param==""){
		asyncbox.alert("请至少选择一个角色","提示");
	}else{
		var url = "../role/checkUserExit.html";
		var msg = ""; 
		$.post(url,{"roleId":param},function(data){
			if(data.allowDel=="allow"){//角色没有任何关联
				msg = "您选中的角色没有任何关联，确定要删除记录吗？";
			}else{
				msg = data.msg+"确认要删除记录吗？";
			}
			asyncbox.confirm(msg,"提示",function(action){
				if(action == "ok"){
					var url_  = "../role/deleteRoles.html";
					$.post(url_,{"roleId":param},function(e){
						if(e.status == 1){
							asyncbox.success("删除成功","执行结果",function(){
								var url_ = "../authority/authorityMain.html";
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
		})
		
	}
		
}