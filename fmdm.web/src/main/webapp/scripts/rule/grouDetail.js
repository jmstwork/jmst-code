function updateTips(o, t) {
	var id = $(o).attr("id");
	$("#tip" + id).text(t).addClass("ui-state-highlight");
}
function checkLength(o, n, min, max) {
	if (o.val() == "" || o.val() == null) {
		updateTips(o, n + "不能为空 ");
		//asyncbox.error(n + "不能为空 ", '验证结果');
		return false;

	}
	var l = o.val().length;
	if (l > max || l < min) {
		updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间");
		//asyncbox.error(n + "的长度必须在【" + min + " ~ " + max + "】之间 ", '验证结果');
		return false;
	} else {
		return true;
	}
}

function chechEnglish(o, n, min, max) {
	if (o.val() == "" || o.val() == null) {
		updateTips(o, n + "不能为空 ");
		//asyncbox.error(n + "不能为空 ", '验证结果');
		return false;
	}

	var string = o.val();

	var parent = /^[A-Za-z]+$/;
	if (!parent.test(string)) {
		updateTips(o, n + "必须为纯英文字母");
		//asyncbox.error(n + "必须为纯英文字母", '验证结果');
		return false;
	}
	var l = o.val().length;
	if (l > max || l < min) {
		updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间");
		//asyncbox.error(n + "的长度必须在【" + min + " ~ " + max + "】之间", '验证结果');
		return false;
	} else {
		return true;
	}
}

/**
 * list页面中“添加”按钮响应事件
 */
function save() {
	
	var bValid = true;
	var condGroName = $("#condGroName"), condGroNameEn = $("#condGroNameEn");
	bValid = checkLength(condGroName, "规则组名", 1, 30) && bValid;
	bValid = chechEnglish(condGroNameEn, "规则组英文名", 1, 30) && bValid;

	if (bValid) {

		asyncbox.confirm('确认修改此规则组吗?', '提示', function(action) {
			if ("ok" == action) {
				var url = "../groupManager/updateGro.html";
				$.post(url, {
					"condGroName" : condGroName.val(),
					"condGroNameEn" : condGroNameEn.val(),
					"groupId" : $("#rulegroupId").val()
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('保存成功！', '执行结果', function() {
							var url_ = "../groupManager/groupList.html";
							location.href = url_;
						});
					} else {
						asyncbox.error("保存失败！", '执行结果');
					}
				});
			}

		})
	}
}



/**
 * “关闭”按钮响应事件
 */
function closePage() {
	var url_ = "../groupManager/groupList.html";
	location.href = url_;
}
