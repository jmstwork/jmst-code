function closeBut() {
	var form = document.auditManageForm;
	form.action = "../auditManage/selectAudit.html";
	form.submit();
}

function saveBut() {
	var form = document.auditManageForm;
	var eventName = $("#eventName");
	var bValid = true;
	$(".validateTips").each(function() {
		$("#" + this.id).removeClass("ui-state-highlight");
		$("#" + this.id).text('');
	});
	bValid = checkLength(eventName, "事件名称", 1, 25) && bValid;
	/*if (null == eventName || 0 == eventName.length) {
		asyncbox.error('事件名称不能为空！', '验证失败', function() {
			return;
		})
	}*/

	if (bValid) {
		asyncbox.confirm('确定要保存该条记录吗？', '提示', function(action) {
			if ('ok' == action) {
				var url = "../auditManage/editAuditSave.html";
				$.post(url, {
					"eventName" : eventName.val(),
					"eventCode" : $("#eventCode").attr("value")
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('修改成功！', '执行结果', function() {
							var url_ = "../auditManage/selectAudit.html";
							location.href = url_;
						})
					} else {
						asyncbox.error('修改失败！', '执行结果', function() {
							return;
						})
					}
				})
			}
		});
	}

}

function checkLength(o, n, min, max) {
	if (o.val() == "" || o.val() == null) {
		updateTips(o, n + "不能为空 ");
		return false;
	}
	var l = o.val().length;
	if (l > max || l < min) {
		updateTips(o, n + "的长度必须在【" + min + " ~ " + max + "】之间");
		return false;
	} else {
		return true;
	}
}

function updateTips(o, t) {
	var id = $(o).attr("id");
	$("#tipeventName").text(t).addClass("ui-state-highlight");
}
