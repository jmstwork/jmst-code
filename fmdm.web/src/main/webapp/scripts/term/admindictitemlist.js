$(function(){
	$("#searchBtn1").click(function(){
		var form = document.DataForm;
		$("[name='export']").val("");
		form.action = "./admindictitemlist.html";
		form.submit();
	});
	
	$("#cleanBtn1").click(function(){
		var title = $("#titleFlag").val();
		if(title == '0'){
			var url_ = "./admindictitemlist.html?title="+$("#titleFlag").val()+"&dictId="+$("#dictId").val()+
			"&dictName="+$("#dictName").val()+"&tableName="+$("#tableName").val()+
			"&searchAll=";
		}else{
			var url_ = "./admindictitemlist.html?title="+$("#titleFlag").val()+"&dictId="+$("#dictId").val()+
			"&dictName="+$("#dictName").val()+"&tableName="+$("#tableName").val()+
			"&searchAll=on";
		}
		location.href=url_;
	});
	
	$("#exportBtn").click(function(){
    	var form = document.DataForm;
    	$("[name='export']").val("export");
    	form.action = "./admindictitemlist.html";
    	form.submit();
	});
})