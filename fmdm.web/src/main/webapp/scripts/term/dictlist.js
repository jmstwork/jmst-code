$(function(){
	if("0"!=$("#title").val()){
		$("#status").attr("disabled", "disabled");
	}
	
	$("#searchBtn").click(function(){
    	var form = document.DataForm;
    	form.action = "./dictlist.html";
    	form.submit();
	});
	
	$("#cleanBtn").click(function(){
		var url_ = "./dictlist.html?title="+$("#titleFlag").val();
		location.href=url_;
	});
	
	$(window).keydown(function (e) { 
		if (e.which == 13) { 
			$("#searchBtn").click();
		} 
	});
	
});

