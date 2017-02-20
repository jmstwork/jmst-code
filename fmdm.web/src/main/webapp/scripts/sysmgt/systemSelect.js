$(function(){
	$("#seachBtn").click(function(){
		$("#sysForm").submit();
	})
	
	$("#cleanBtn").click(function(){
		$("#searchSysID").val("");
		$("#searchSysName").val("");
		$("#sysForm").submit();
	})
})

function checkThis(obj,flag){
	if(flag=="tr"){
		if(!$(obj).children()[0].lastChild.checked){
			$(obj).children()[0].lastChild.checked=true;
		}else{
			$(obj).children()[0].lastChild.checked=false;
		}
	}else if(flag=="td"){
		if(!$(obj)[0].checked){
			$(obj)[0].checked=true;
		}else{
			$(obj)[0].checked=false;
		}
	}
	
	if($('input[name="childrenBox"]:checked').length!=$('input[name="childrenBox"]').length){
		$("#parentBox").removeAttr("checked");
	}else{
		$("#parentBox").attr("checked",true);
	}
}