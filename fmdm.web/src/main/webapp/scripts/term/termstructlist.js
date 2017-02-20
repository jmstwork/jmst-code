$(function() {
	$("#addTS").bind("click", function() {
		var url_ = "../term/addTermStruct.html";
		location.href=url_;
	});
	
	$("#addMC").bind("click", function() {
		var url_ = "../term/addMappingCode.html";
		location.href=url_;
	});
	
	// 按条件搜索术语结构信息
	$("#search").bind("click", function() {
		var form = document.termStructForm;
		form.action = "./termStructList.html";
		form.submit();
	});
	
	$("#cleanBtn").bind("click", function() {
		var url_ = "../term/termStructList.html";
		location.href=url_;
	});
	
	$("#cleanBtn2").bind("click", function() {
		var url_ = "../term/termMappingList.html";
		location.href=url_;
	});
	
	$("#mapsearch").bind("click", function() {
		var form = document.termStructForm;
		form.action = "./termMappingList.html";
		form.submit();
	});

	if ($("#backMsg").val() != "" && $("#backMsg").val() != null) {
		asyncbox.alert($("#backMsg").val(),"提示信息");
		$("#backMsg").val("");
	}

	$("#dialog-compare").dialog({
		autoOpen : false,
		height : 350,
		width : 550,
		resizable : false,
		modal : true,
		draggable : true,
		buttons : {
			"关闭" : function() {
				$("#jumpToPage").val($("#currentPage").val());
				$(this).dialog("close");
				var form = document.termStructForm;
				form.action = "./termStructList.html";
				form.submit();
			}
		},
		"close" : function() {

		}
	});

})

function compare(dictId) {

	$("#dialog-compare").dialog("open");
	$("#dialog-compare").load("./compareStruct.html", {
		"dictId" : dictId
	}, function(e) {

	});
	$("#dialog-compare").dialog("open");

}

function editTermStruct(dictId,obj) {

	var url_ = "./authorityConfirm.html";
	// 发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
	jQuery.post(url_, {
		"dictId" : dictId,
		"operation" : "edit"
	}, function(e) {
		if (e.result == 1) {
			$("#dictId").val(dictId);
			var form = document.termStructForm;
			form.action = "./editTermStruct.html";
			form.submit();
		} else {
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.alert("正在参与同步操作！不能进行编辑!","提示信息",function(action){
				if(action=='ok'){
					trObj.css("background","");
				}
			});
		}
	});
}

function editTermMap(sourceTable,sourceName,targetTable,targetName){
//	var url_ = "./addMappingCode.html?sourceTable="+sourceTable+"&sourceName="
//	+sourceName+"&targetTable="+targetTable+"&targetName="+targetName;
	$("#sourceTable").val(sourceTable);
	$("#sourceName").val(sourceName);
	$("#targetTable").val(targetTable);
	$("#targetName").val(targetName);
	var url_ = "./addMappingCode.html?operation=fix";
	termStructForm.action = url_;
	termStructForm.submit();
}

function deleteTermStruct(dictId, tableName,dictName,obj) {
	var url_ = "./authorityConfirm.html";
	// 发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
	jQuery.post(url_, {
		"dictId" : dictId,
		"tableName" : tableName,
		"operation" : "delete"
	}, function(e) {
		$("#dictId").val(dictId);
		if (e.result == 1) {
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.confirm("确定删除该术语结构吗？删除后所有角色下的与该术语结构关联的视图将被删除！","提示",function(action){
				 if(action == "ok"){
					 asyncbox.confirm("该数据结构存在物理表，是否删除?","提示",function(action){
						 if(action == "ok"){
							 $("#operation").val("deleteAll");
						 }else {
								$("#operation").val("delete");
							}})
				var form = document.termStructForm;
				form.action = "./deleteTermStruct.html";
				form.submit();
			}else{
				 trObj.css("background","");
			 }})
		} else if (e.result == 2) {
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.confirm("确定删除该术语结构吗？","提示",function(action){
				 if(action == "ok"){
					 $("#operation").val("delete");
						var form = document.termStructForm;
						form.action = "./deleteTermStruct.html";
						form.submit();
				 }else{
					 trObj.css("background","");
				 }})
		}else if (e.result == 3) {
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.alert("该术语用于术语映射！不能删除!","提示信息",function(action){
				if(action=='ok'){
					trObj.css("background","");
				}
			});
		} else {
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.alert("正在参与同步操作！不能进行删除,请稍后操作!","提示信息",function(action){
				if(action=='ok'){
					trObj.css("background","");
				}
			});
		}
	});
}

function deleteMappingCode(sourceTable,targetTable,obj){
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
	asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
		 if(action == "ok") {
	var form = document.termStructForm;
	form.action = "./deleteMappingCode.html?sourceTable1="+sourceTable+"&targetTable1="+targetTable+"&all=all";
	form.submit();
  }else{
		 trObj.css("background","");
  }
})}

function add() {

	$(".saveDiv").show();
	$(".dipslayDiv").hide();

}

function addorfix(record, e) {
	var form = document.termStructForm;
	var url = "./templateAddOrFix.html";
	if (record == "fix") {
		url += "?tempalteId=" + e;
	}
	form.action = url;
	form.submit();
}
function insertJs() {
	if ($("#templateId").val() !="") {
		if ($("#templateInfo").val() == "") {
			asyncbox.alert("","提示信息");
		} else if ($("#templateCode").val() == "") {
			asyncbox.alert("模板编码不能为空！","提示信息");
		} else if ($("#templateName").val() == "") {
			asyncbox.alert("模板名称不能为空！","提示信息");
		} else if ($("#templateVersion").val() == "") {
			asyncbox.alert("模板版本不能为空！","提示信息");
		} else {
			document.saveForm.action = "./updateTemplate.html";
			document.saveForm.submit();
		}
	} else if ($("#templateId").val() =="") {
		if ($("#templateInfo").val() == "") {
			asyncbox.alert("模板内容不能为空！","提示信息");
		} else if ($("#templateCode").val() == "") {
			asyncbox.alert("模板编码不能为空！","提示信息");
		} else if ($("#templateName").val() == "") {
			asyncbox.alert("模板名称不能为空！","提示信息");
		} else if ($("#templateVersion").val() == "") {
			asyncbox.alert("模板版本不能为空！","提示信息");
		} else {
			document.saveForm.action = "./templateSave.html";
			document.saveForm.submit();
		}

	}
  }
function deleteTemplate(templateId){
	asyncbox.confirm("确定要删除吗？","提示信息",function(action){
		 if(action == "ok"){
			 var form = document.termStructForm;
				var url = "./deleteTemplate.html?templateId="+templateId;
				form.action = url;
				form.submit();
		 }else{
				return false;
			}})
}

function upload(input) {
	  //支持chrome IE10
	  if (window.FileReader) {
	    var file = input.files[0];
	    filename = file.name.split(".")[0];
	    var reader = new FileReader();
	    reader.onload = function() {
	       $("#templateInfo").val(this.result);
	    }
	    reader.readAsText(file);
	  }else{
		  asyncbox.alert("请选择IE10或者Chrome浏览器！","提示信息");
	  } 
	}
function off() {
	var form = document.saveForm;
	var url = "./templateSearch.html";
	form.action = url;
	form.submit();
}


function linkPath(templateId){
	var form = document.termStructForm;
	var url = "../data/dataMapping.html?templateId="+templateId;
	form.action = url;
	form.submit();
}
