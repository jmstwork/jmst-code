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
