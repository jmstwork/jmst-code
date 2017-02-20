$(function(){
		$("#search").bind("click",function(){
			var form = document.termStructForm;
			form.action = "./termstructlist.html";
			form.submit();
		});
})

