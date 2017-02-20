var panelShow = false;
jQuery(function($) {
	$("input[type='text']").keyup(function(event) {
		if (event.keyCode == '32') {
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("textarea").keyup(function(event) {
		if (event.keyCode == '32') {
			var v = $(this).text();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).text(v);
		}
	});
	var userAccount = $("#userAccount"), userName = $("#userName"), userPasswd = $("#userPasswd"), confirmPasswd = $("#confirmPasswd"), userMail = $("#userMail"), userMobile = $("#userMobile"), memo = $("#memo"), allFields = $(
			[]).add(userAccount).add(userName).add(userPasswd).add(
			confirmPasswd).add(userMail).add(userMobile), tips = $(".validateTips");

	function updateTips(o, t) {
		// tips.text(t).addClass( "ui-state-highlight" );
		// o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		// tipoldPasswd
		// tips.text(t).addClass( "ui-state-highlight" );
		alert(id);
		$("#tip" + id).text(t).addClass("ui-state-highlight");
	}

	function checkNull(o, n) {
		if (o.val().replace(/(^\s*)|(\s*$)/g, "") == ""
				|| o.val().replace(/(^\s*)|(\s*$)/g, "") == null) {
			updateTips(o, n + "不能为空 ");
			return false;
		} else {
			return true;
		}
	}
	function checkLength(o, n, min, max) {

		var l = o.val().replace(/(^\s*)|(\s*$)/g, "").length;
		if (l > max || l < min) {
			// updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间" );
			// return false;
			return n + "的长度必须在【" + min + " ~ " + max + "】之间";
		} else {
			// return true;
			return null;
		}
	}

	function checkRegexp(o, regexp, n) {
		if (!(regexp.test(o.val()))) {
			// updateTips(o, n);
			return n;
		} else {
			return null;
		}
	}
	function checkPasswd(a, b) {
		var m = a.val();
		var n = b.val();
		/*if (m.length != n.length) {
			// updateTips(b, "密码长度不一致");
			return "密码长度不一致";
		}*/
		if (m != n) {
			// updateTips(b, "两次密码不一致");
			return "两次密码不一致";
		}
		return null;
	}
	// 密码复杂度校验
	function passwordStrengthCheck(a) {
		var s = a.val();
		if (s.length < 8) {
			// updateTips("", "密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种");
			return "密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种";
		}
		var ls = 0;
		if (s.match(/([a-z])+/)) {
			ls++;
		}
		if (s.match(/([0-9])+/)) {
			ls++;
		}
		if (s.match(/([A-Z])+/)) {
			ls++;
		}
		if (s.match(/[^a-zA-Z0-9]+/)) {
			ls++;
		}
		if (ls < 3) {
			// updateTips('', "密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种");
			return "密码长度必须8位以上，且要求密码包含数字、大写字母、小写字母、特殊字符4种中的三种";
		} else {
			return null;
		}
	}

	function checkUserExist(o) {
		var b = true;
		var url_ = "../user/checkUserExist.html";
		$.ajaxSetup({
			async : false
		});
		var userAccount = o.val().replace(/(^\s*)|(\s*$)/g, "");
		$.post(url_, {
			"userAccount" : userAccount
		}, function(e) {
			if (e.status == '1') {
				// updateTips(o,"该账号已被注册");
				// b = false;
				return "该账号已被注册";
			}
		});
		return null;
	}

	$("#saveBtn").click(function() {
		var userAccount = $("#userAccount"), 
			userName = $("#userName"), 
			userPasswd = $("#userPasswd"), 
			confirmPasswd = $("#confirmPasswd"), 
			userMail = $("#userMail"), 
			userMobile = $("#userMobile"),
			memo = $("#memo");

		var empty = false;
		var errMsg = "";

		// 判断非空
		if(null == $("#userId").val() ){
			if (null == userAccount.val()
					|| "" == userAccount.val()) {
				errMsg += "[登录账号]";
				empty = true;
			}

			if (null == userName.val() || "" == userName.val()) {
				errMsg += "[账户名称]";
				empty = true;
			}
		}

		if (null == userPasswd.val() || "" == userPasswd.val()) {
			errMsg += "[密码]";
			empty = true;
		}

		if (null == confirmPasswd.val()
				|| "" == confirmPasswd.val()) {
			errMsg += "[确认密码]";
			empty = true;
		}

		if (null == userMail.val() || "" == userMail.val()) {
			errMsg += "[邮箱]";
			empty = true;
		}

		if (null == userMobile.val() || "" == userMobile.val()) {
			errMsg += "[手机号码]";
			empty = true;
		}

		if (empty) {
			errMsg += "不能为空!";
			$(".panel").html(
					"<img src='../images/msg/warn.png' style='vertical-align: top;'/> "
							+ errMsg);
			$(".panel").css("background-color", "#fefcda");
			$(".panel").slideDown("slow");
			state = true;
			return;
		}

		var verificationResult = "";

		if ($("#userId").val() == '') {

			verificationResult = checkLength(userName, "账户姓名", 2, 10);
			if (null != verificationResult) {
				showWarnMsg(empty, verificationResult);
				userName.val("");
				userAccount.val("");
				userName.focus();
				//clearValue();
				return;
				//empty = true;
			}

			verificationResult = checkLength(userAccount, "登录账号", 4, 20);
			if (null != verificationResult) {
				showWarnMsg(empty, verificationResult);
				userName.val("");
				userAccount.val("");
				userAccount.focus();
				return;
			}

			verificationResult = checkUserExist(userAccount);
			if (null != verificationResult) {
				showWarnMsg(empty, verificationResult);
				userName.val("");
				userAccount.val("");
				userAccount.focus();
				return;
			}
		}

		verificationResult = checkLength(userMail, "邮箱", 0, 80);
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			userMail.val("");
			userMail.focus();
			return;
		}

		if(userPasswd.val()!=$("#pwdOriginal").val()){
			verificationResult = checkLength(userPasswd, "密码", 8,32);
			if (null != verificationResult) {
				showWarnMsg(empty, verificationResult);
				userPasswd.val("");
				confirmPasswd.val("");
				userPasswd.focus();
				return;
			}
			
			verificationResult = passwordStrengthCheck(userPasswd);
			if (null != verificationResult) {
				showWarnMsg(empty, verificationResult);
				userPasswd.val("");
				confirmPasswd.val("");
				userPasswd.focus();
				return;
			}
		}
		
		verificationResult = checkPasswd(userPasswd,confirmPasswd);
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			//clearValue();
			userPasswd.val("");
			confirmPasswd.val("");
			userPasswd.focus();
			return;
			//empty = true;
		}

		verificationResult = checkLength(userMail, "邮箱", 0, 80);
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			//clearValue();
			userMail.val("");
			userMail.focus();
			return;
			//empty = true;
		}

		verificationResult = checkRegexp(
				userMail,
				/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
				"请输入正确的邮箱，如eg.ui@jquery.com");
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			userMail.val("");
			userMail.focus();
			return;
		}
		
		verificationResult = checkRegexp(userMobile,
				/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i,
				"请输入正确的手机号码");
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			userMobile.val("");
			userMobile.focus();
			return;
		}
		
		verificationResult = checkLength(memo, "备注", 0, 30);
		if (null != verificationResult) {
			showWarnMsg(empty, verificationResult);
			memo.val("");
			memo.focus();
			return;
		}
		
		if(empty){
			return;
		}

		var userId = $("#userId").val();
		var userAccount = $("#userAccount").val().replace(
				/(^\s*)|(\s*$)/g, "");
		var userName = $("#userName").val();
		var userPasswd = $("#userPasswd").val();
		var confirmPasswd = $("#confirmPasswd").val();
		var userMail = $("#userMail").val();
		var userMobile = $("#userMobile").val();
		var superUser = $("input[name='superUser']:checked")
				.val();
		var userDesc = $("#memo").val();
		var updateCount = $("#updateCount").val() == "" ? 0
				: $("#updateCount").val();
		selects = $(".tbody input[type='checkbox']");
		var ids = new Array();
		for (var i = 0; i < selects.length; i++) {
			var id = selects[i].parentNode.id;
			ids.push(id);
		}
		var roleIds = ids.join(',');
		var url = "../user/saveUser.html";
		
		$.post(url, {
			"userId" : userId,
			"userAccount" : userAccount,
			"userName" : userName,
			"userPasswd" : userPasswd,
			"userMail" : userMail,
			"userMobile" : userMobile,
			"superUser" : superUser,
			"memo" : userDesc,
			"roleIds" : roleIds,
			"updateCount" : updateCount,
			"operateFlg" : $("#operateFlg").val()
		}, function(data) {
			if (data.status == '1') {
				asyncbox.success('保存成功！', '执行结果', function() {
					var url_ = "../user/userList.html";
					location.href = url_;
				});
			} else {
				asyncbox.error('保存失败！', '执行结果');
			}
		});

	});

	$("#deleteBtn").click(function() {
		var selectes = $(".tbody input[type='checkbox']:checked");
		if (selectes.length < 1) {
			asyncbox.alert("请至少选择一条记录操作","提示");
			return;
		}
		
		asyncbox.confirm("确认要删除选中记录吗","确认信息",function(action){
			if('ok' == action){
				selectes.parent().parent().remove();
				if ($("#parentBox")[0].checked) {
					$("#parentBox")[0].checked = false;
				}
			}
		});
	});
	
	$("#resetBtn").click(function() {
		var url = "";
		if("add"==$("#operateFlg").val()){
			url  = "../user/userEdit.html";
		}else if("edit"==$("#operateFlg").val()){
			url  = "../user/userEdit.html?userAccount="+$("#userId").val()
		}
		location.href = url;
	});
	
	$("#returnBtn").click(function() {
		var url_ = "../user/userList.html";
		location.href = url_;
	});
	
	$("#selectBtn").click(function() {
		asyncbox.open({
			id : "userList",
			modal : true,
			drag : true,
			scrolling :"no",
			title : "",
			url : "../user/selectUsers.html",
			width : 800,
			height : 404
		})
	});
	
	$("#addBtn").click(function() {
		var url_ = "../user/selectRole.html";
		$("#roleIframe").attr("src", url_);
		$("#dialog-role").dialog("open");
	});

});

	function setData(userNo, userName, mobile, email) {
		$("#userId").val(userNo);
		$("#userAccount").val(userNo);
		$("#userName").val(userName);
		$("#userMail").val(email);
		$("#userMobile").val(mobile);
		$("#userName").css("background-color","#cbcbcb").attr("readOnly", "readOnly");
		$("#userPasswd").val('');
		$("#confirmPasswd").val('');
		if (email != '')
			$("#userMail").css("background-color","#cbcbcb").attr("readOnly", "readOnly");
		if (mobile != '')
			$("#userMobile").css("background-color","#cbcbcb").attr("readOnly", "readOnly");
		
		checkAccount(userNo);
	}

	function showWarnMsg(state, errMsg) {
		if (state) {
			$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + errMsg + "<br/>");
		} else {
			$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + errMsg);
			$(".panel").css("background-color", "#fefcda");
			$(".panel").slideDown("slow");
			panelShow = true;
		}
	}

	function clearValue(){
		$("#userPasswd").val("");
		$("#confirmPasswd").val("");
		$("#userMail").val("");
		$("#userMobile").val("");
		$("#memo").val("");
	}

	function checkAccount(userNo){
		if(userNo.replace(/(^\s*)|(\s*$)/g,"")==""){
			return false;
		}
		//校验登录账号是否存在
		var url  = "../user/checkUserAccount.html";
		$.post(url,{"userAccount":userNo},function(data){
			if(data.status == '1'){
				$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> 该登录账号已存在，请更换登录账号！");
				$(".panel").css("background-color", "#fefcda");
				$(".panel").slideDown("slow");
				$("#accountOK").css("display","none");
				panelShow = true;
			}else{
				if(panelShow){
					$(".panel").slideUp("show");
				}
				$("#accountOK").css("display","block");
			}
		});
	}