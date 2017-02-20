$(function(){
	$("#seachBtn").click(function(){
		$("#subsSysForm").attr("action","../dataManage/subsSysDatas.html").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#sysId").val("");
		$("#sysName").val("");
		$("#sysApply").val("");
		$("#hospitalId").val("");
	});
	
	$("#returnBtn").click(function(){
		var url_ = "../dataManage/dataManageList.html";
		location.href = url_;
	});
	
	$("#deleteBtn").click(function(){
		var childrenBox = document.getElementsByName("childrenBox");
		var ids=null;
		var delsize = 0;
		for(var i=0;i<childrenBox.length;i++){
			if(childrenBox[i].checked){
				if(ids==null){
					delsize++;
					ids = childrenBox[i].id;
				}else{
					delsize++;
					ids = ids + "," + childrenBox[i].id;
				}
			}
		}
		if(ids==null){
			asyncbox.error("至少选择一条要删除的记录！","提示");
		}else{
			asyncbox.confirm("确定要删除这"+delsize+"条记录吗？","提示",function(action){
				 if(action == "ok"){
					var url = "../dataManage/deleteDatas.html";
					$.post(url, {
						"uniKey" : ids,
						"tableName" : "SUBS_SYS"
					}, function(data) {
						if (data.status == '1') {
							asyncbox.success('删除成功', '执行结果', function() {
								var url_ = "../dataManage/subsSysDatas.html";
								location.href = url_;
							});
						}else{
							asyncbox.error("删除失败", '执行结果');
						}
					});
				 }
			})
		}
	});
});

function showEdit(titleName,uniKey){
	jQuery("#dialog-form").load("../dataManage/dataEditShow.html", {"uniKey":uniKey,"tableName":"SUBS_SYS"}, function(e){
	});
	$( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 550,
		width: 650,
		resizable: true,
		modal: true,
		draggable: true,
		title:titleName,
		buttons: {
			"提交": function() {
				var sysName = $("#itemDataEditForm input[name='SYS_NAME']").eq(0).val();
				var hospitalId = $("#itemDataEditForm select[name='HOSPITAL_ID']").eq(0).val();
				var sysId = $("#itemDataEditForm input[name='SYS_ID']").eq(0).val();
				var sysApply = $("#itemDataEditForm input[name='SYS_APPLY']").eq(0).val();
				tips = $(".validateTips");
				if(sysId == ''){
					tips.text("系统ID不能为空").addClass("ui-state-highlight");
					return;
				}
				if(uniKey == null){
					if(checkUniOne(sysId,"SUBS_SYS")){
						tips.text("系统ID已存在").addClass("ui-state-highlight");
						return;
					}
				}
				if(sysName == ''){
					tips.text("系统名称不能为空").addClass("ui-state-highlight");
					return;
				}
				var url = "../dataManage/saveSysData.html";
				$.post(url, {
					"uniKey" : uniKey,
					"sysName":sysName,
				    "hospitalId":hospitalId,
				    "sysId":sysId,
				    "sysApply":sysApply,
					"tableName" : "SUBS_SYS"
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('保存成功', '执行结果', function() {
							var url_ = "../dataManage/subsSysDatas.html";
							location.href = url_;
						});
					} else {
						asyncbox.error("保存失败", '执行结果');
					}
				});
			},
			"取消": function() {
				$(this).dialog( "close" );
			}
		},
	});
	$( "#dialog-form" ).dialog( "open" );	
}

//唯一性校验
function checkUniOne(code,tableName){
	var url_= "../dataManage/checkUniOne.html"
	var status=false;
	$.ajaxSetup({async:false});
    jQuery.post(url_,{"code":code,"tableName":tableName},function(e){ 
		 if(e.status==true){
			 status= true;
		 }else
			 status= false;
	});
    $.ajaxSetup({async:true});
	return status;
}
function linkage(uniKey,tableName){
	var url_ = "../dataManage/linkage.html";
	$.post(url_,{"uniKey":uniKey,"tableName":tableName},function(e){
		$("#itemDataEditForm select[name='HOSPITAL_ID']").eq(0).val(e.hospitalCode);
		$("#itemDataEditForm select[name='HOSPITAL_NAME']").eq(0).val(e.hospitalName);
	});
}