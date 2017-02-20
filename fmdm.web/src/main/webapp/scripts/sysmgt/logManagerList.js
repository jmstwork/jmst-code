//清空按钮
function doClear() {
	$("#operorId").val("");
	$("#operDt1").attr("value", '');
	$("#operDt2").attr("value", '');
	$("#operModu").attr("value", '');
	$("#operObj").attr("value", '');
	/*
	 * var url = "../logManager/ajaxLinkage.html"; $.post(url, { "resrCode" :
	 * obj.value }, function(data) { if (data.listObj) {
	 * appendOperObj(data.listObj); } else { appendOperObj(''); } })
	 */
	var form = document.logForm;
	form.action = "../logManager/list.html";
	form.submit();
}

// 搜索按钮
function doSearch() {
	if (null != $("#operDt1").val() && "" != $("#operDt1").val()
			&& null != $("#operDt2").val() && "" != $("#operDt2").val()) {
		var optDt1 = new Date($("#operDt1").val());
		var optDt2 = new Date($("#operDt2").val());
		if ($("#operDt1").val() > $("#operDt2").val()) {
			asyncbox.alert("结束时间必须大于起始时间！", "提示");
			return;
		}
	}
	if ((null == $("#operModu").val() || "" == $("#operModu").val())  && (null == $("#operObj").val() || "" == $("#operObj").val())
			&& (null == $("#operorId").val() || "" == $("#operorId").val() ) && (null == $("#operDt1").val() || "" == $("#operDt1").val())
			&& (null == $("#operDt2").val() || "" == $("#operDt2").val())) {
		asyncbox.alert("至少有有一项选择条件！", "提示");
		return;
	}
	var form = document.logForm;
	form.action = "../logManager/list.html";
	form.submit();
}

function refreshOperObj(obj) {

	var url = "../logManager/ajaxLinkage.html";
	$.post(url, {
		"resrCode" : obj.value
	}, function(data) {
		if (data.listObj) {
			appendOperObj(data.listObj);
		} else {
			appendOperObj('');
		}
	})

}


function appendOperObj(result) {
	if (result == '') {
		$("select[id='operObj']").html(getDefaultHtml("operObj"));
	} else {
		var operObj = getDefaultHtml("operObj");
		for (var i = 0; i < result.length; i++) {
			operObj = operObj + "<option value='" + result[i].optCode + "'>"
					+ result[i].optName + "</option>"
		}
		$("select[id='operObj']").html(operObj);
	}
}

function getDefaultHtml(tagId) {
	var strDefaultValue = $.trim($("#" + tagId + " option[value='']").html());
	return "<option value='' selected='selected'>" + strDefaultValue
			+ "</option>";
}

function display() {
	WdatePicker({
		skin : 'whyGreen',
		startDate : '%y-%M-%d %H:00:00',
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		alwaysUseStartDate : true
	});
}