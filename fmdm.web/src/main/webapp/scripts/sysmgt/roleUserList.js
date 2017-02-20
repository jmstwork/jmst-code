jQuery(function($){
	var roleName = $( "#roleName" ),
	memo=$("#memo"),
	allFields = $( [] ).add( roleName ),
    tips = $( ".validateTips" );

	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请选择一条记录操作！","提示");
			return ;
		}
		
		asyncbox.confirm("确认要删除选中记录吗?","确认信息",function(action){
			if(action == "ok"){
				var ids = new Array();
				selectes.each(function(i){
					ids.push($(this).parent().parent().children()[2].innerText);
				})
				
				var userAccounts = ids.join(",");
				
				$.post("../role/deleteRoleUser.html",{"roleId":$("#roleId").val(),"userIds":userAccounts},function(data){
					if(data.status == '1'){
						asyncbox.success("删除成功！","执行结果",function(){
							var url_ = "../role/userList.html?roleId="+$("#roleId").val();
							location.href=url_;
						});
					}else{
						asyncbox.error("删除失败！","执行结果",function(){
							var url_ = "../role/userList.html?roleId="+$("#roleId").val();
							location.href=url_;
						});
					}
				});
			}
		})
	});
	
	$("#backBtn").click(function(){
		var url_ = "../role/roleList.html";
		location.href=url_;
	})
	
	$("#searchBtn").click(function(){
		$("#userForm").submit();
	})
	
	$("#cleanBtn").click(function(){
		var url_ = "../role/userList.html?roleId="+$("#roleId").val();
		location.href=url_;
	})
	
	function updateTips( o, t ) {
		//tips.text(t).addClass( "ui-state-highlight" );
		//o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		//tips.text(t).addClass( "ui-state-highlight" );
		$("#tip" + id).text(t).addClass( "ui-state-highlight" );
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
	
	function checkUserExist(o){
		var b = true;
		var url_ = "../user/checkUserExist.html";
		$.ajaxSetup({ async : false }); 
		var userAccount = o.val();
		$.post(url_,{"userAccount":userAccount},function(e){
			if(e.status == '0'){
				updateTips(o,"该账号未被注册");
				b = false;
			}
		});
		return b;
	}
	
	
	$("#saveBtn").click(function(){
		var roleId = $( "#roleId" ).val();
		var selects = $(".tbody input[type='checkbox']");
		var ids = new Array();
		for(var i=0;i<selects.length;i++){
			var id=selects[i].parentNode.id;
			ids.push(id);
		}
		
		var userIds = ids.join(',');
		var url  = "../role/saveRoleUserList.html";
		$.post(url,{"roleId":roleId,"userIds":userIds},function(data){
			if(data.status == '1'){
				asyncbox.success("保存成功！","执行结果",function(){
					var url_ = "../role/roleList.html";
					location.href=url_;
				})
				
			}else{
				asyncbox.error("保存失败！","执行结果");
			}
		});
	});
	
	$("#resetBtn").click(function(){
		allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		$("#resetBtnH").click();
		$("input[type='text']").removeAttr("readonly");
		$("#userAccount").val('');
		
	});
	
	$("#returnBtn").click(function(){
		var url_ = "../role/roleList.html";
		location.href=url_;
	});
	
	$("#selectBtn").click(function(){
		asyncbox.open({
			id : "userList",
			modal : true,
			drag : true,
			scrolling :"no",
			title : "",
			url : "../role/selectUserAccount.html?roleId=" + $("#roleId").val(),
			width : 800,
			height : 404,
			btnsbar: [{
			     text    : '选择', 
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
//						var id = obj[i].parentElement.parentElement.childNodes[5].innerText;
						var id = obj[i].id;
						ids.push(id);
					})
					var userAccounts = ids.join(',');
					if(userAccounts==""){
						asyncbox.alert("请至少选择一个账户！","提示");
						return false;
					}else{
						var url  = "../role/saveRoleUser.html";
						$.post(url,{"roleId":$("#roleId").val(),"userAccounts":userAccounts},function(data){
							if(data.status == '1'){
								asyncbox.success("保存成功！","执行结果",function(){
									var url_ = "../role/userList.html?roleId="+$("#roleId").val();
									location.href=url_;
								});
							}else{
								asyncbox.error("保存失败！","执行结果",function(){
									var url_ = "../role/userList.html?roleId="+$("#roleId").val();
									location.href=url_;
								});
							}
						});
					}
				}
			}
		})
		
		/*allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		var url_ = "../role/selectUserAccount.html";
		//location.href=url_;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
		jQuery("#dialog-form").load(url_, {}, function(e) {});
		$("#dialog-form").dialog("option", "title", "角色增加账户列表").dialog("open");*/
		
	});
	
	$("#addBtn").click(function(){		
		//1.检查是否输入了登录账号
		var userAccount = $("#userAccount1").val().replace(/(^\s*)|(\s*$)/g, "");
		if(userAccount==""){
			asyncbox.alert("请输入登录账号","提示");
		}else{
			//2.检查登录账号是否有效
			var url  = "../user/checkUserExist.html";
			$.post(url,{"userAccount":userAccount},function(data){
				if(data.status == '0'){
					asyncbox.alert("该账号未注册，请重新输入！","提示");
					$("#userAccount1").val("");
				}else{
					//3.检查登录账号是否已经添加
					var url  = "../role/checkRoleUser.html";
					$.post(url,{"userAccount":userAccount,"roleId":$("#roleId").val()},function(data){
						if(data.status == '1'){
							asyncbox.alert("该角色下已存在此账号，请勿重复添加！","提示");
							$("#userAccount1").val("");
						}else{
							//4.成功添加
							var url  = "../role/saveRoleUser.html";
							$.post(url,{"roleId":$("#roleId").val(),"userAccounts":userAccount},function(data){
								if(data.status == '1'){
									asyncbox.success("保存成功！","执行结果",function(){
										var url_ = "../role/userList.html?roleId="+$("#roleId").val();
										location.href=url_;
									});
								}else{
									asyncbox.error("保存失败！","执行结果");
								}
							});
						}
					});
				}
			});
		}
		
		
	});
})

	function setUser(ids){
		var selects = $(".tbody input[type='checkbox']");
		var repeat=""
		for(var i=0;i<selects.length;i++){
			for(var j=0;j<ids.length;j++){
				var userId=selects[i].parentNode.id;
				var userAccount = selects.parent().parent()[i].childNodes[2].innerText;
				if(userId == ids[j]){
					if(repeat ==""){
						repeat=userAccount;
					}else{
						repeat+=","+userAccount;
					}
				}
			}
		}
			
		if(repeat!=""){
			//alert("已选择用户列表中已存在以下用户："+repeat+"  请重新确认");
			asyncbox.alert("已选择用户列表中已存在以下用户："+repeat+"  请重新确认","提示");
			return;
		}
		
		var url  = "../role/selectUsersById.html?userIds="+ids;
		$.post(url,function(data){
			var userList=data.userList;
			for(var i=0;i<userList.length;i++){
							
				var user = userList[i];						
				var rowcnt = $("table[id$='userTable']>tbody").children("tr").length+1;
				var r = jQuery(".tbody").children().length;
				//cursor: pointer;
				//var tr = "<tr class='tbody'>";
				var tr = "<tr style='cursor: pointer;'>";
				var td = "";						
				td = td +"<td align='center' id="+user.userId+">"+"<input type='checkbox' id='childrenBox' name='childrenBox' onclick='javascript:checkChildren();'/>"+"</td>";
				td = td +"<td align='center'>"+rowcnt+"</td>";
				td = td +"<td>"+user.userAccount+"</td>";
				td = td +"<td>"+user.userName+"</td>";
				if(user.userMobile==null){
					user.userMobile="";
					}	
				td = td +"<td>"+user.userMobile+"</td>";
				if(user.userMail==null){
					user.userMail="";
					}	
				td = td +"<td>"+user.userMail+"</td>";
				if(user.enableFlag=='1'){
					user.enableFlag="启用";
					}else{
						user.enableFlag="停用";	
						
					}
				td = td +"<td>"+user.enableFlag+"</td>";
				if(user.roleName==null){
					user.roleName="";
					}				
				
				td = td +"<td title='"+ user.roleName+"'>"+"<div style='white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width:200px;'><nobr><label for='user.roleName'}'></label></nobr>"+ user.roleName+"</div></td>";
				
			//	td = td +"<td>"+user.roleName+"</td>";
				if(user.memo==null){
					user.memo="";
					}
				td = td +"<td title='"+ user.memo+"'>"+"<div style='white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width:200px;'><nobr><label for='user.memo'}'></label></nobr>"+ user.memo+"</div></td>";
				td = td +"<td align='center'><a href='#' onclick='javascript:deleteUser(this);' style='color:blue;text-decoration:underline;course:hand ' class='Linkblue' target='_self'>删除</a></td>";
				tr = tr + td + "</tr>";
				jQuery(".tbody").append(tr);
			}
			asyncbox.alert("已选择成功！","提示");
		});
	}

	function deleteUser(obj){
		//parent().parent().remove();
		obj.parentNode.parentNode.remove();
	}




