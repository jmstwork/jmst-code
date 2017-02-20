$(function(){
	$("#seachBtn").click(function(e){
		$("#sysId").val($("#paramSysId").val());
		$("#serviceForm").submit();
	});
	
	$("#cleanBtn").click(function(e){
		$("#serviceId").val("");
		$("#serviceName").val("");
		$("#hospitalCode option[value='000']").attr("selected",true);
		var form = $("#serviceForm");
		form.submit();
	});
	
	//返回按钮-返回到应用系统一览画面
	$("#backBtn").click(function(e){
		var form = $("#serviceForm");
		form.attr("action","../subscribe/sysList.html");
		form.submit();
	});
	
	
	$("#addBtn").click(function(e){
		var form = $("#serviceForm");
		form.attr("action","../subscribe/add.html");
		form.submit();
	});
	
	//删除订阅操作
	$("#deleteBtn").click(function(e){
		var cnt = $(".tbody input[type='checkbox']:checked");
		if(cnt.length<1){
			asyncbox.alert("请至少选择一条记录！","提示信息");
			return ;
		}else{
			var ids = new Array();
			
			for(var i=0;i<cnt.length;i++){
				ids.push(cnt[i].value);
			}
			var subsIds = ids.join(',');
			
			asyncbox.confirm('确认要删除选中记录吗？', '提示', function(action) {
				if ('ok' == action) {
					var url  = "../subscribe/delete.html";
					$.post(url,{"subsIds":subsIds},function(data){
						if(data.result == "1"){
							asyncbox.alert("删除成功!同时需重启IE平台才能生效新的订阅关系。", '提示',function(action){
								if(action == 'ok'){  
									$("#seachBtn").click();
								}
							});
						}else{
							asyncbox.alert("删除失败!", '提示',function(action){
								if(action == 'ok'){  
									$("#seachBtn").click();
								}
							});
						}
//						$("#seachBtn").click();
					});
				}})
//			if(confirm("确认要删除选中记录吗?")){
//				var url  = "../subscribe/delete.html";
//				$.post(url,{"subsIds":subsIds},function(data){
//					if(data.result == "1"){
//						asyncbox.alert("删除成功!同时需重启IE平台才能生效新的订阅关系。");
//					}else{
//						asyncbox.alert("删除失败!");
//					}
//					$("#seachBtn").click();
//				});
//			}
		}
	});
	$("#dialog-form" ).dialog({
		autoOpen: false,
		title:"科室组详细列表",
		height: 480,
		width: 800,
		resizable: false,
		modal: true,
		draggable: false
	});

});
function unitGroupDetail(unitGroupId,hospitalCode){
	var url_ = "../subscribe/unitGroupDetail.html?unitGroupId="+unitGroupId+"&hospitalCode="+hospitalCode;
	//location.href=url_;
	$("#dataIframe").attr("src",url_);
	$("#dialog-form").dialog("open");
}