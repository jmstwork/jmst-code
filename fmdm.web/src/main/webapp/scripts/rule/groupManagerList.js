function updateTips(o, t) {
	var id = $(o).attr("id");
	$("#tip" + id).text(t).addClass("ui-state-highlight");
}


function checkLength(o, n, min, max) {
	if (o.val().replace(/\s+/g,"") == "" || o.val() == null) {
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
 * list页面中“编辑”按钮响应事件
 */
function updateGroupManager(rulegroupId) {
	var form = document.groupForm;
	form.action = "../groupManager/editGro.html?groupId=" + rulegroupId;
	form.submit();
}

/**
 * list页面中“删除”按钮响应事件
 */
function deleteGroupManager(rulegroupId, obj) {

	//更改选中行的背景色
	var trObj = $(obj).parent().parent();
	trObj.css("background", "#fab5b5");

	asyncbox.confirm('确认要删除这条数据吗?', '提示', function(action) {
		if ("ok" == action) {
			var url = "../groupManager/deleteGro.html";
			$.post(url, {
				"groupId" : rulegroupId
			}, function(data) {
				if (data.status == '1') {
					// alert("规则组删除成功！");
					asyncbox.success('删除成功!', '执行结果', function() {
						var url_ = "../groupManager/groupList.html";
						location.href = url_;
					});
				} else {
					asyncbox.error(data.failMessage, '执行结果');
				}
			});
		} else {
			//取消背景色
			trObj.css("background", "");
		}

	})
}

function addGroup() {
	var bValid = true;
	var condGroName = $("#condGroName"), condGroNameEn = $("#condGroNameEn");
	bValid = checkLength(condGroName, "规则组名", 1, 30) && bValid;
	if(bValid){
		var id = $(condGroName).attr("id");
		$("#tip" + id).text("");
	}
	bValid = chechEnglish(condGroNameEn, "规则组英文名", 1, 30) && bValid;
	if(bValid){
		var id = $(condGroNameEn).attr("id");
		$("#tip" + id).text("");
	}
	if (bValid) {
		asyncbox.confirm('确认添加此规则组吗?', '提示', function(action) {
			if ("ok" == action) {
				var url = "../groupManager/saveGro.html";
				$.post(url, {
					"condGroName" : condGroName.val(),
					"condGroNameEn" : condGroNameEn.val()
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('保存成功！', '执行结果', function() {
							var url_ = "../groupManager/groupList.html";
							location.href = url_;
						});
					} else {
						asyncbox.error(data.failMessage, '执行结果');
					}
				});
			}
		})
	}
}