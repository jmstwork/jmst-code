	function getRootPath() {
		//获取主机地址之后的目录，如：ggfw/app/iam/userList.jsp    
		var pathName = window.document.location.pathname;
		//获取带"/"的项目名，如：/ggfw/  
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		return projectName;
	}

	function mouseOver(obj) {
		if (obj.className == "css1") {
			obj.className = "css2";
		} else if (obj.className == "css0") {
			obj.className = "css3";
		}
		// 循环表格 
		/* for ( var i = 0; i < obj.children.length; i++) {
			var cells = obj.children[i].getElementsByTagName("td");
			obj.children[i].style.color = "blue";
			obj.children[i].style.fontWeight = "bold";
		} */
	}
	function mouseOut(obj) {
		if (obj.className == "css2") {
			obj.className = "css1";
		} else if (obj.className == "css3") {
			obj.className = "css0";
		}
		/* // 循环表格 
		for ( var i = 0; i < obj.children.length; i++) {
			var cells = obj.children[i].getElementsByTagName("td");
			obj.children[i].style.color = "#000";
			obj.children[i].style.fontWeight = "";
		} */
	}

jQuery(function($){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("#seachBtn").click(function(){
		var url  = "../user/userList.html";
		$("#userForm").attr("action",url);
		$("#userForm").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#userAccount").val('');
		$("#userName").val('');
	});
	
	$("#addBtn").click(function(){
		var url  = "../user/userEdit.html";
		$("#userForm").attr("action",url);
		$("#userForm").submit();
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
		var url  = "../user/userEdit.html?userId="+selectes[0].id;
		$("#userForm").attr("action",url);
		$("#userForm").submit();
	});
	
	$("#startBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作！");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var userId = ids.join(',');
		if(confirm("确认要启用选中记录吗？")){
			var url  = "../user/operateUsers.html";
			$.post(url,{"userId":userId,"operateFlg":"start"},function(data){
				if(data.status == '1'){
					alert("启用成功");
				}else{
					alert("启用失败")
				}
				var url_ = "../user/userList.html";
				location.href=url_;
			});
		}
	});
	
	$("#stopBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作！");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var userId = ids.join(',');
		if(confirm("确认要停用选中记录吗？")){
			var url  = "../user/operateUsers.html";
			$.post(url,{"userId":userId,"operateFlg":"stop"},function(data){
				if(data.status == '1'){
					alert("停用成功");
				}else{
					alert("停用失败")
				}
				var url_ = "../user/userList.html";
				location.href=url_;
			});
		}
	});
});
function rmtAuthSet(userAccount,userName){
		 asyncbox.open({
				id:"userAuth",
				modal:true,
				align:"center",
				drag:true,
				title:"",
				url: getRootPath() +"/user/rmtAuthSet.html?userAccount="+userAccount+"&userName="+encodeURI(encodeURI(userName)),
				btnsbar:$.btn.OKCANCEL,
				callback : function(action){
					if(action == 'ok'){
							var userAccount = asyncbox.opener('userAuth').document.getElementById('userAccount').value;
							var objName =asyncbox.opener('userAuth'). document.getElementById("setAuthForm");
							var resourceIds;
							for (i = 0; i < objName.length; i++) {
								if (objName[i].id == "resourceId" && objName[i].checked) {
									if (resourceIds == null) {
										resourceIds = objName[i].name;
									} else {
										resourceIds = resourceIds + "@" + objName[i].name;
									}
								}
							}
							var url_ = "../user/saveUserAuth.html?userAccount=" + userAccount
							+ "&resourceIds=" + resourceIds;
							$.getJSON(url_, "", function(data) {
								
								if (data.resultSet == 1) {
									asyncbox.success("保存成功","",function(action){
										if(action=="ok"){
											asyncbox.close('userAuth');
										}
									});
								} else {
									asyncbox.success("保存失败","");
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
		alert("请选择要启用的用户！");
		return;
	}else if(userIds=="" && status ==0){
		alert("请选择要停用的用户！");
		return;
	}
	
	var url_ ="./updateUserStatus.html";
	jQuery.post(url_,{"userIds":userIds,"status":status},function(e){
		if(e.backStatus==1){
			alert("更新成功");
			$("#seachBtn").click();
		}else{
			alert("更新失败");
		}
	});
}