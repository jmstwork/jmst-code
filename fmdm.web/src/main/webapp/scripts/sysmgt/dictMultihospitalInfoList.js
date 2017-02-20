$(function(){
	$("#seachBtn").click(function(){
		$("#subsSysForm").attr("action","../dataManage/dictMultihospitalInfoDatas.html").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#hospitalCode").val("");
		$("#hospitalName").val("");
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
			asyncbox.confirm("关于医疗机构的删除请谨慎操作！ 确定要删除这"+delsize+"条记录吗？","提示",function(action){
				 if(action == "ok"){
					var url = "../dataManage/deleteDatas.html";
					$.post(url, {
						"uniKey" : ids,
						"tableName" : "DICT_HOSPITAL"
					}, function(data) {
						if (data.status == '1') {
							asyncbox.success('删除成功', '执行结果', function() {
								var url_ = "../dataManage/dictMultihospitalInfoDatas.html";
								location.href = url_;
							});
						}else if(data.status == '2'){
							asyncbox.alert("医疗机构["+data.hospitalId+"]被某个服务引用，不能删除！", '提示');
						}else {
							asyncbox.error("删除失败", '执行结果');
						}
					});
				 }
			})
		}
	});
})

function showEdit(titleName,uniKey){
	jQuery("#dialog-form").load("../dataManage/dataEditShow.html", {"uniKey":uniKey,"tableName":"DICT_HOSPITAL"}, function(e){
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
				var hospitalCode = $("#itemDataEditForm input[name='HOSPITAL_CODE']").eq(0).val();
				var hospitalName = $("#itemDataEditForm input[name='HOSPITAL_NAME']").eq(0).val();
				tips = $(".validateTips");
				if(hospitalCode == ''){
					tips.text("医疗机构编码不能为空").addClass("ui-state-highlight");
					return;
				}
				if(uniKey == null){
					if(checkUniOne(hospitalCode,"DICT_HOSPITAL")){
						tips.text("医疗机构编码已存在").addClass("ui-state-highlight");
						return;
					}
				}
				if(!checkLetterOrNum(hospitalCode)){
					tips.text("医疗机构编码只能是英文字母或者数字或者'-'或者'*'").addClass("ui-state-highlight");
					return;
				}
				var url = "../dataManage/saveHospitalData.html";
				$.post(url, {
					"uniKey":uniKey,
				    "hospitalCode":hospitalCode,
				    "hospitalName":hospitalName,
					"tableName" : "DICT_HOSPITAL"
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('保存成功', '执行结果', function() {
							var url_ = "../dataManage/dictMultihospitalInfoDatas.html";
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
function checkLetterOrNum(o){
	var reg = /^[A-Za-z0-9\-\*]+$/;
	if(reg.test(o)){
	    return true;
	}else
		return false;
}
function checkLetter(o){
	var reg = /^[A-Za-z_]+$/;
	if(reg.test(o)){
		return true;
	}else
		return false;
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