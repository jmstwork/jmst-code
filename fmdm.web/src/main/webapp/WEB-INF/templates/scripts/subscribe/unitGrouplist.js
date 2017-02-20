jQuery(function($){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("#seachBtn").click(function(){
		var url  = "../subscribe/unitGroupList.html";
		$("#DataForm").attr("action",url);
		$("#DataForm").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#unitGroupId").val('');
		$("#unitGroupName").val('');
		$('#hospitalCode').val('');
	});
	
	$("#addBtn").click(function(){
		var url  = "../subscribe/unitGroupEdit.html";
		$("#DataForm").attr("action",url);
		$("#DataForm").submit();
	});
	$("#updateBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(selectes.length>1){
			alert("您选择的多条记录，默认处理第一条");
		}
		var url  = "../subscribe/unitGroupEdit.html?editUnitGroupId="+selectes[0].id;
		$("#DataForm").attr("action",url);
		$("#DataForm").submit();
	});
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var unitGroupIds = ids.join(',');
		//校验科室组Id是否在订阅中订阅
		var url  = "../subscribe/checkRepeatUnitGroup.html";
		$.post(url,{"unitGroupIds":unitGroupIds},function(data){
			if(data.repeatId != ""){
				alert("科室组"+data.repeatId+"在订阅管理中有服务订阅，不允许删除");
					return ;
			}else{
				if(confirm("确认要删除选中记录吗")){
					var url  = "../subscribe/deleteUnitGroup.html";
					$.post(url,{"unitGroupIds":unitGroupIds},function(data){
						if(data.status == '1'){
							alert("删除成功");
						}else{
							alert("删除失败")
						}
						var url_ = "../subscribe/unitGroupList.html";
						location.href=url_;
					});
				}
			}
		});
		
	});
	$("#dialog-form" ).dialog({
		autoOpen: false,
		title:"科室组详细列表",
		height: 480,
		width: 820,
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
