//清空按钮
function doClear() {
	$("#hospitalCode").attr("value", '');
	$("#eventCode").attr("value", '');
	$("#optDt1").attr("value", '');
	$("#optDt2").attr("value", '');
	$("#sysId").attr("value", '');
	$("#deptName").attr("value", '');
	$("#deptCode").attr("value", '');
	$("#userNo").val("");
	$("#userName").val("");
	var form = document.DataForm;
	form.action = "../auditInfor/auditInformation.html";
	form.submit();
}

// 搜索按钮
function doSearch() {
	if (null != $("#optDt1").val() && "" != $("#optDt1").val()
			&& null != $("#optDt2").val() && "" != $("#optDt2").val()) {
		var optDt1 = new Date($("#optDt1").val());
		var optDt2 = new Date($("#optDt2").val());
		if ($("#optDt1").val() > $("#optDt2").val()) {
			asyncbox.alert("结束时间必须大于起始时间！", "提示");
			return;
		}
	}
	var form = document.DataForm;
	form.action = "../auditInfor/auditInformation.html";
	form.submit();
}

function display() {
	WdatePicker({
		skin : 'whyGreen',
		startDate : '%y-%M-%d %H:00:00',
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		alwaysUseStartDate : true
	});
}

function searchDetail(td) {
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 300,
		width : 600,
		resizable : false,
		modal : true,
		draggable : false,
		"close" : function() {
			$(this).dialog("close");
		}
	});
	var openUrl = "../auditInfor/auditDetailView.html";
	jQuery("#dialog-form").load(openUrl, {
		"id" : td
	}, function(response, status, xhr) {
		if (status == "success") {
			$("#dialog-form").dialog("open");
		}
	});

}

