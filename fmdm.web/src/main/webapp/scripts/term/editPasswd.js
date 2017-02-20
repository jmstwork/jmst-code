jQuery(function($){
	var oldPasswd = $( "#oldPasswd" ),
	newPasswd = $( "#newPasswd" ),
	confirmNewPasswd = $( "#confirmNewPasswd" ),
	allFields = $( [] ).add( oldPasswd ).add( newPasswd ).add( confirmNewPasswd ),
	tips = $( ".validateTips" );
	
	function updateTips( o, t ) {
		//tips.text(t).addClass( "ui-state-highlight" );
		//o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		$("#tip"+id).text(t).addClass( "ui-state-highlight" );
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
	function checkOld(o,n){
		if(o.val()=="" || o.val()==null){
			updateTips(o, n + "不能为空 ");
			return false;
		}
		if(o.val()!= $("#oldP").val()){
			updateTips(o, n + "不正确 ");
			return false;
		}
		return true;
	}
	
	function checkPasswd(a,b,c){
		var m = a.val();
		var n = b.val();
		var l = c.val();
		if(m == l){
			updateTips(a,"新密码与旧密码重复");
			return false;
		}
		if(m.length != n.length){
			updateTips(b,"密码长度不一致");
			return false;
		}
		if(m!=n){
			updateTips(b,"两次密码不一致");
			return false;
		}
		return true;
	}
	
	$("#dialog-passwd" ).dialog({
		autoOpen: false,
		height: 300,
		width: 450,
		resizable: false,
		modal: true,
		draggable: false,
		close:function(e){
			$("#oldPasswd").val('');
			$("#newPasswd").val('');
			$("#confirmNewPasswd").val('');
			$(".validateTips").each(function(){
				$("#"+this.id).removeClass("ui-state-highlight");
				$("#"+this.id).text('');
			});
		}
	});
	$("#confirmBtn").click(function(){
		
		var bValid = true;
		//allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			$("#"+this.id).removeClass("ui-state-highlight");
			$("#"+this.id).text('');
		});
		var oldPasswd = $("#oldPasswd"),
		newPasswd = $("#newPasswd"),
		confirmNewPasswd = $( "#confirmNewPasswd" );
		
		bValid = checkOld( oldPasswd, "旧密码") && bValid;
		bValid = checkLength( newPasswd, "新密码",8,30 ) && bValid;
		bValid = checkLength( confirmNewPasswd, "确认密码",8,30 ) && bValid;
		bValid = checkPasswd( newPasswd,confirmNewPasswd,oldPasswd) && bValid;
		if(bValid){
			var url_ = "../user/updatePasswd.html";
			var userAccount = $("#loginName").val();
			var userName = $("#realName").val();
			$.post(url_,{"userAccount":userAccount,"userName":userName,"userPasswd":newPasswd.val()},function(e){
				if(e.status=='1'){
					alert("操作成功,请重新登录系统");
					$("#dialog-passwd" ).dialog("close");
					location.href = "../j_spring_security_logout";
				}else{
					alert("操作失败");
				}
			});
		}
	});
	$("#cancleBtn").click(function(){
		$("#dialog-passwd" ).dialog("close");
	});
	
});

function modifyPasswd(){
	asyncbox.open({
		id : "editPassword",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : "../user/editPassword.html",
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
				var oldPasswd = opener.$("#oldPasswd").val().replace(/(^\s*)|(\s*$)/g,"");
				var isNull = false;
				if(oldPasswd==""&&oldPasswd!=null){
					opener.$("#oldPasswdDesc").css("color","red").text("旧密码不能为空！");
					isNull = true;
				}else{
					if(opener.$("#oldPasswdDesc").text()=="旧密码不能为空！"){
						opener.$("#oldPasswdDesc").text("");
					}
					isNull = false;
				}
				
				var newPasswd = opener.$("#newPasswd").val().replace(/(^\s*)|(\s*$)/g,"");
				if(newPasswd==""&&newPasswd!=null){
					opener.$("#newPasswdDesc").css("color","red").text("新密码不能为空！");
					isNull = true;
				}else{
					opener.$("#newPasswdDesc").text("");
					isNull = false;
				}
				
				var confirmNewPasswd = opener.$("#confirmNewPasswd").val().replace(/(^\s*)|(\s*$)/g,"");
				if(confirmNewPasswd==""&&confirmNewPasswd!=null){
					opener.$("#confirmNewPasswdDesc").css("color","red").text("确认密码不能为空！");
					isNull = true;
				}else{
					opener.$("#confirmNewPasswdDesc").text("");
					isNull = false;
				}
				
				if(isNull){
					return false;
				}else{
					if(newPasswd!=confirmNewPasswd){
						opener.$("#newPasswdDesc").css("color","red").text("新密码与确认密码不一致！");
						opener.$("#confirmNewPasswdDesc").css("color","red").text("新密码与确认密码不一致！");
						return false
					}else{
						opener.$("#newPasswdDesc").text("");
						opener.$("#confirmNewPasswdDesc").text("");
					}
				}
				
				var url  = "../user/savePasswd.html";
				$.post(url,{"userAccount":opener.$("#userAccount")[0].innerText,"userPasswd":newPasswd},function(data){
					if(data.status == '1'){
						asyncbox.success("密码修改成功!","执行结果");
					}else{
						asyncbox.error("密码修改失败！","执行结果");
					}
				});
			}
		}
	})
}
