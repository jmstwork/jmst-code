function closeFunction() {
	var form = document.auditManageForm;
	form.action = "../auditManage/selectAudit.html";
	form.submit();
}

function saveFunction() {

	var eventName = $("#eventName");

	var bValid = true;
	$(".validateTips").each(function() {
		$("#" + this.id).removeClass("ui-state-highlight");
		$("#" + this.id).text('');
	});

	asyncbox.confirm('确定要保存这条记录吗？', '提示', function(action) {
		if ('ok' == action) {
			bValid = checkLength(eventName, "事件名称", 1, 25) && bValid;
			if (bValid) {
				var url = "../auditManage/saveAuditEvent.html";
				$.post(url, {
					"eventName" : eventName.val()
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('保存成功！', '执行结果', function() {
							var url_ = "../auditManage/selectAudit.html";
							location.href = url_;
						})
					} else {
						asyncbox.error('保存失败！', '执行失败');
					}
				})
			}
		}
	});

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
	$("#tipoldPasswd").text(t).addClass("ui-state-highlight");
}