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
	var userAccount = $( "#userAccount" ),
	    userName = $( "#userName" ),
		userPasswd = $( "#userPasswd" ),
		confirmPasswd = $( "#confirmPasswd" ),
		userMail = $( "#userMail" ),
		userMobile = $( "#userMobile" ),
		memo=$("#memo"),
		allFields = $( [] ).add( userAccount ).add( userName ).add( userPasswd ).add(confirmPasswd).add(userMail).add(userMobile),
		tips = $( ".validateTips" );

	function updateTips( o, t ) {
		//tips.text(t).addClass( "ui-state-highlight" );
		//o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		tips.text(t).addClass( "ui-state-highlight" );
	}
	
	function checkNull(o,n){
		if(o.val().replace(/(^\s*)|(\s*$)/g, "")=="" || o.val().replace(/(^\s*)|(\s*$)/g, "")==null){
			updateTips(o, n + "不能为空 ");
			return false;
		}else{
			return true;
		}
	}
	function checkLength( o, n, min, max ) {
		
		var l = o.val().replace(/(^\s*)|(\s*$)/g, "").length;
		if ( l > max || l < min ) {
			updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间" );
			return false;
		} else {
			return true;
		}
	}
		
	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			updateTips( o,n );
			return false;
		} else {
			return true;
		}
	}
	function checkPasswd(a,b){
		var m = a.val();
		var n = b.val();
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
	//密码复杂度校验
	function passwordStrengthCheck(a){
		var s=a.val();
	if(s.length < 8){
		updateTips("","密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种");
		return false;
		}
	var ls = 0;        
	if(s.match(/([a-z])+/)){
		ls++;        
		}        
	if(s.match(/([0-9])+/)){              
		ls++;      
		}        
	if(s.match(/([A-Z])+/)){                     
		ls++;        
		}      
	if(s.match(/[^a-zA-Z0-9]+/)){               
		ls++;              
		}
	if(ls<3){
		updateTips('',"密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种");
		return false;      
	 	}
	else{
		return true;
	}
	}
	
	function checkUserExist(o){
		var b = true;
		var url_ = "../user/checkUserExist.html";
		$.ajaxSetup({ async : false }); 
		var userAccount = o.val().replace(/(^\s*)|(\s*$)/g, "");
		$.post(url_,{"userAccount":userAccount},function(e){
			if(e.status == '1'){
				updateTips(o,"该账号已被注册");
				b = false;
			}
		});
		return b;
	}
	
	$("#saveBtn").click(function(){
		var bValid = true;
		allFields.removeClass( "ui-state-error" );
		$(".validateTips").each(function(){
			tips.removeClass("ui-state-highlight");
			tips.text('');
		});
		
		var userAccount = $( "#userAccount" ),
	    userName = $( "#userName" ),
		userPasswd = $( "#userPasswd" ),
		confirmPasswd = $( "#confirmPasswd" ),
		userMail = $( "#userMail" ),
		userMobile = $( "#userMobile" ),
		memo=$("#memo");
		bValid = checkLength( memo, "备注",0,80 ) && bValid;
		bValid = checkLength( userMail, "邮箱",0,80 ) && bValid;
		bValid = checkRegexp( userMobile, /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i, "请输入正确的手机号码" ) && bValid;
		bValid = checkRegexp( userMail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "请输入正确的邮箱，如eg.ui@jquery.com" ) && bValid;
		bValid = checkPasswd( userPasswd,confirmPasswd) && bValid;
		bValid = checkLength( confirmPasswd, "确认密码",8,30 ) && bValid;
		bValid = checkLength( userPasswd, "密码",8,30 ) && bValid;
		bValid = passwordStrengthCheck( userPasswd ) && bValid;
		if($("#userId").val()==''){
			bValid = checkLength( userName, "用户姓名",2,10 ) && bValid;
			bValid = bValid && checkLength( userAccount, "登录账号", 4, 20 );
			bValid = bValid && checkNull( userAccount, "登录账号");
			if(bValid){
				bValid = checkUserExist( userAccount) && bValid;
			}
		}
		//alert(bValid);
		if(bValid){
			var userId=$("#userId").val();
			var userAccount=$("#userAccount").val().replace(/(^\s*)|(\s*$)/g, "");
			var userName=$("#userName").val();
			var userPasswd=$("#userPasswd").val();
			var confirmPasswd=$("#confirmPasswd").val();
			var userMail=$("#userMail").val();
			var userMobile=$("#userMobile").val();
			var superUser=$("input[name='superUser']:checked").val();
			var userDesc=$("#memo").val();
			var updateCount = $("#updateCount").val() ==""?0:$("#updateCount").val();
			selects = $(".tbody input[type='checkbox']");
			var ids = new Array();
			for(var i=0;i<selects.length;i++){
				var id=selects[i].parentNode.id;
				ids.push(id);
			}
			var roleIds = ids.join(',');
			var url  = "../user/saveUser.html";
			$.post(url,{"userId":userId,"userAccount":userAccount,"userName":userName,"userPasswd":userPasswd,"userMail":userMail,"userMobile":userMobile,"superUser":superUser,"memo":userDesc,"roleIds":roleIds,"updateCount":updateCount},function(data){
				if(data.status == '1'){
					alert("保存成功！");
					var url_ = "../user/userList.html";
					location.href=url_;
				}else{
					alert("保存失败！");
				}
			});
		}
	});
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请至少选择一条记录操作");
			return ;
		}
		if(confirm("确认要删除选中记录吗")){
			selectes.parent().parent().remove();
			if($("#parentBox")[0].checked){
				$("#parentBox")[0].checked=false;
			}
		}
	});
	$("#resetBtn").click(function(){
		$("#resetBtnH").click();
		$("input[type='text']").removeAttr("readonly");
	});
	$("#returnBtn").click(function(){
		var url_ = "../user/userList.html";
		location.href=url_;
	});
	$("#selectBtn").click(function(){
		var url_ = "../user/selectUsers.html";
		//location.href=url_;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
	});
	$("#dialog-form" ).dialog({
		autoOpen: false,
		height: 420,
		width: 850,
		resizable: false,
		modal: true,
		draggable: false
	});
	$("#addBtn").click(function(){
		var url_ = "../user/selectRole.html";
		//location.href=url_;
		$("#roleIframe").attr("src",url_);
		$("#dialog-role").dialog("open");
	});
	$("#dialog-role" ).dialog({
		autoOpen: false,
		height: 520,
		width: 690,
		resizable: false,
		modal: true,
		draggable: false
	});
});

function setData(userNo,userName,mobile,email){
	$("#userAccount").val(userNo);
	$("#userName").val(userName);
	$("#userMail").val(email);
	$("#userMobile").val(mobile);
	$("#userAccount").attr("readOnly","readOnly");
	$("#userName").attr("readOnly","readOnly");
	$("#userPasswd").val('');
	$("#confirmPasswd").val('');
	if(email!='')
		$("#userMail").attr("readOnly","readOnly");
	if(mobile!='')
		$("#userMobile").attr("readOnly","readOnly");
	$("#dialog-form").dialog("close");
}
function setRole(ids){
	var selects = $(".tbody input[type='checkbox']");
	var repeat=""
	for(var i=0;i<selects.length;i++){
		for(var j=0;j<ids.length;j++){
			var roleId=selects[i].parentNode.id;
			var roleName = selects.parent().parent()[i].childNodes[2].innerText;
			if(roleId == ids[j]){
				if(repeat ==""){
					repeat=roleName;
				}else{
					repeat+=","+roleName;
				}
			}
		}
	}
	if(repeat!=""){
		alert("已选择角色列表中已存在以下角色："+repeat+"  请重新确认");
		return;
	}
	var url  = "../user/selectRoles.html?roleIds="+ids;
	$.post(url,function(data){
		var roleList=data.roleList;
		for(var i=0;i<roleList.length;i++){
			var role = roleList[i];
			var rowcnt = $("table[id$='roleTable']>tbody").children("tr").length+1;
			var r = jQuery(".tbody").children().length;
			var tr = "<tr>";
			var td = "";
			td = td +"<td align='center' id="+role.roleId+">"+"<input type='checkbox' id='childrenBox' name='childrenBox' onclick='javascript:checkChildren();'/>"+"</td>";
			td = td +"<td align='center'>"+rowcnt+"</td>";			
			if(role.memo==null){
				   role.memo="";
				}
			td = td +"<td>"+role.roleName+"</td>";
			td = td +"<td>"+role.memo+"</td>";
			tr = tr + td + "</tr>";
			jQuery(".tbody").append(tr);
		}
		alert("已选择成功。");
	});
}