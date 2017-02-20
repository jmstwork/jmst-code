$(function(){
	if("0"!=$("#title").val()){
		$("#status").attr("disabled", "disabled");
	}
	
	
	$("#searchBtn").click(function(){
    	var form = document.DataForm;
    	form.action = "./dictlist.html";
    	form.submit();
	});
});

