$(function(){
	$("#seachBtn").click(function(){
		$("#dataManagerForm").attr("action","../dataManage/dataManageList.html").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#dataName").val("");
		$("#tableName").val("");
	});
	
	
});

function manageData(dataId){
	var url  = "../dataManage/dataInfo.html?dataId="+dataId;
	$("#dataManagerForm").attr("action",url);
	$("#dataManagerForm").submit();
}