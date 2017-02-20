jQuery(function($){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("textarea").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).text();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).text(v);
		}
	});
	var roleName = $( "#roleName" ),
		memo=$("#memo"),
		allFields = $( [] ).add( roleName ),
	    tips = $( ".validateTips" );

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
	function checkRoleExist(o){
		var b = true;
		var url_ = "../role/checkRoleExist.html";
		$.ajaxSetup({ async : false }); 
		var roleName = o.val();
		$.post(url_,{"roleName":roleName},function(e){
			if(e.status == '1'){
				updateTips(o,"该角色已被建立");
				b = false;
			}
		});
		return b;
	}
	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			updateTips( o,n );
			return false;
		} else {
			return true;
		}
	}
	function checkNull(o,n,val){
		if(o.val()=="" || o.val()==null){
			updateTips(o, n + "不能为空 ");
			return false;
		}
	}
	function checkLength( o, n, min, max ) {
		var l = o.val().length;
		if ( l > max || l < min ) {
			updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间" );
			return false;
		} else {
			return true;
		}
	}
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请选择一条记录操作！","提示信息");
			return ;
		}
		if(confirm("确认要删除选中记录吗")){
			selectes.parent().parent().remove();
			if($("#parentBox")[0].checked){
				$("#parentBox")[0].checked=false;
			}
		}
	});
	$("#saveBtn").click(function(){
		var bValid = true;
		allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		
		var roleName = $( "#roleName" ),
			memo=$("#memo");				
		if($("#roleId").val()==''){
			bValid = checkLength( roleName, "角色名",2,10 ) && bValid;
			if(bValid){
				bValid = checkRoleExist( roleName) && bValid;
			}
		}
		bValid = checkLength(memo, "备注",0,150 ) && bValid;
		if(bValid){
			var roleId = $( "#roleId" ).val(),
			    roleName = $( "#roleName" ).val(),
				memo=$("#memo").val();
			selects = $(".tbody input[type='checkbox']");
			var ids = new Array();
			for(var i=0;i<selects.length;i++){
				var id=selects[i].parentNode.id;
				ids.push(id);
			}
			var userIds = ids.join(',');
			var url  = "../role/saveRole.html";
			$.post(url,{"roleId":roleId,"roleName":roleName,"memo":memo,"userIds":userIds},function(data){
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
		allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		var url_ = "../role/selectUserAccount.html";
		//location.href=url_;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
	});
	$("#dialog-form" ).dialog({
		autoOpen: false,
		height: 520,
		width: 950,
		resizable: false,
		modal: true,
		draggable: false
	});
	$("#addBtn").click(function(){		
		allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		//检验是否列表中已添加了用户
		var userAccount = $("#userAccount").val();
		var selects = $(".tbody input[type='checkbox']");
		var repeat=""
		for(var i=0;i<selects.length;i++){
		var userAccountSelect = selects.parent().parent()[i].childNodes[2].innerText;
		if(userAccountSelect == userAccount){
			if(repeat ==""){
				repeat=userAccountSelect;
			}else{
				repeat+=","+userAccountSelect;
			}
		  }
		}
		if(repeat!=""){
			asyncbox.alert("已选择用户列表中已存在以下用户："+repeat+"  请重新确认","提示信息");
			return;
		}
		
		//验证输入账户是否符合要求及是否被注册
		var bValid = true;
		var userAccount = $("#userAccount");				  		
			bValid = bValid && checkLength( userAccount, "登录账号", 4, 20 );
			if(bValid){
				bValid = checkUserExist( userAccount) && bValid;
			}
			
	if(bValid){
		var userAccount = $("#userAccount").val();
		var url  = "../role/selectUserByUserAccount.html?userAccount="+userAccount;
		$.post(url,function(data){
			var user=data.user;							
				var rowcnt = $("table[id$='userTable']>tbody").children("tr").length+1;
				var r = jQuery(".tbody").children().length;
				var tr = "<tr>";
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
				//td = td +"<td>"+user.roleName+"</td>";
				if(user.memo==null){
					user.memo="";
					}
				td = td +"<td title='"+ user.memo+"'>"+"<div style='white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width:200px;'><nobr><label for='user.memo'}'></label></nobr>"+ user.memo+"</div></td>";
				//td = td +"<td>"+user.memo+"</td>";
				tr = tr + td + "</tr>";
				jQuery(".tbody").append(tr);
			    asyncbox.success("添加成功！","执行结果");
		});
	  }
	});
});

	
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
		asyncbox.alert("已选择用户列表中已存在以下用户："+repeat+"  请重新确认","提示信息");
		return;
	}
	var url  = "../role/selectUsersById.html?userIds="+ids;
	$.post(url,function(data){
		var userList=data.userList;
		for(var i=0;i<userList.length;i++){
						
			var user = userList[i];						
			var rowcnt = $("table[id$='userTable']>tbody").children("tr").length+1;
			var r = jQuery(".tbody").children().length;
			var tr = "<tr class='tbody'>";
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
			td = td +"<td align='center'><a th:onclick='\'javascript:deleteUser(\'' + ${item.userId} +'\',this);\'' style='color:blue;text-decoration:underline;course:hand ' class='Linkblue' target='_self'>删除</a></td>";
			tr = tr + td + "</tr>";
			jQuery(".tbody").append(tr);
		}
		asyncbox.alert("已选择成功！","提示信息");
	});
}




