//清空按钮
function cleanFunction() {
	$("#evneName").attr("value", '');
	$("#eventCode").attr("value", '');
	var url_ = "../auditManage/selectAudit.html";
	location.href = url_;
}

// 搜索按钮
function doSearch() {
	var form = document.auditManageForm;
	form.action = "../auditManage/selectAudit.html";
	form.submit();
}

function editEvent(evenCode) {
	/*var form = document.auditManageForm;
	form.action = "../auditManage/editAuditInit.html?evenCode=" + evenCode;
	form.submit();*/
	var url = "../auditManage/editAuditInit.html?evenCode=" + evenCode;
	operate(url,"edit");
}

function deleteEvent(eventCode,obj) {
	
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
	
	asyncbox.confirm('确定要删除该条记录吗？','提示',function(action){
		if('ok' == action){
			var url = "../auditManage/deleteAuditInit.html";
			$.post(url, {
				"eventCode" : eventCode
			}, function(data) {
				if (data.status == '1') {
					asyncbox.success('删除成功!', '执行结果', function() {
						var url_ = "../auditManage/selectAudit.html";
						location.href = url_;
					})
				} else if (data.status == '2') {
					// alert("该审计事件已被应用，无法删除！");
					asyncbox.error('该审计事件已被应用，无法删除！', '执行结果');
				}
			})
		}else{
			trObj.css("background","");
		}
	})
}

function closeBut() {
	var form = document.auditManageForm;
	form.action = "../auditManage/selectAudit.html";
	form.submit();
}

function addEvent() {
	/*var form = document.auditManageForm;
	form.action = "../auditManage/addAuditEvent.html";
	form.submit();*/
	var url = "../auditManage/addAuditEvent.html";
	operate(url,"0");
}





function operate(url,flag){
	asyncbox.open({
		id : "chooseCard",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : url,
		width : 530,
		height : 200,
		btnsbar: [{
		     text    : '保存', 
		     action  : 'save'
		},{
			 text    : '关闭', 
		     action  : 'close_1'
		}],
		callback : function(action,opener){
			if(action == 'save'){
				
				var eventName = opener.$("#eventName");
				var bValid = true;
				if (eventName.val() == "" || eventName.val() == null) {
					opener.$("#tipoldPasswd").css("color","red").text("事件名称不能为空 ");
					bValid = false;
					return false;
				}
				var l = eventName.val().length;
				if (l > 25 || l < 1) {
					opener.$("#tipoldPasswd").css("color","red").text("事件名称 " + "的长度必须在" + 1 + " ~ " + 25 + "之间");
					bValid = false;
				} 			
				
				if(bValid){
					var url = null;
					if (flag == "edit") {
						url = "../auditManage/editAuditSave.html";
					}else{
						url = "../auditManage/saveAuditEvent.html";
					}
					$.post(url, {
						"eventName" : eventName.val(),
						"eventCode" : opener.$("#eventCode").val()
					}, function(data) {
						if (data.status == '1') {
							asyncbox.success('保存成功！', '执行结果', function() {
								var url_ = "../auditManage/selectAudit.html";
								location.href = url_;
							})
						} else {
							asyncbox.error('保存失败！', '执行失败');
						}
					});
				}else{
					return false;
				}
				
			}
		}
	})
}