$(function(){
	$("#seachBtn").click(function(){
		$("#userForm").submit();
	})
	
	$("#cleanBtn").click(function(){
		$("#code").val("");
		$("#name").val("");
		$("#userForm").submit();
	})
})

function chooseUser(code,name){
	window.parent.$("#userNo").val(code);
	window.parent.$("#userName").val(name);
	window.parent.$.close("chooseCard");
}

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