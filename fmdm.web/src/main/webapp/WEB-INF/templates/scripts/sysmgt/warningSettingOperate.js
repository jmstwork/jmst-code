$(function(){
	alert(888);
	$("#backBtn").click(function(){
		$("#wsOperateForm").attr("action","../sysmgt/wsView.html").submit();
		
	})
})