$(function(){
	$("#seachBtn").click(function() {
		var form = document.ruleForm;
		form.action = "../rmlg/versionView.html";
		form.submit();
	});
	$("#cleanBtn").click(function(){
		$("#versionNo").val("");
	});
})