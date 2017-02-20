jQuery(function($){
				$("input[type='text']").keyup(function(event){
					if(event.keyCode=='32'){
						var v = $(this).val();
						v = v.replace(/(^\s*)|(\s*$)/g, "");
						$(this).val(v);
					}
				});
				$("#seachBtn").click(function(){
					$("#submitBtn").click();
				});
				$("#cleanBtn").click(function(){
					$("#userAccount").val('');
					$("#userName").val('');	
					$("#submitBtn").click();
					//asyncbox.alert("请选择一条记录操作","提示信息");
				});
				$("#closeBtn").click(function(){
					window.parent.$("#dialog-form").dialog("close");
				});
				$("#selectBtn").click(function(){
					//alert(32482378);
					var selectes = $(".tbody input[type='checkbox']:checked");
					if(selectes.length<1){
						asyncbox.alert("请选择一条记录操作","提示信息");
						return ;
					}
					var ids= new Array();
					for(var i=0;i<selectes.length;i++){
						ids.push(selectes[i].id);
					}
				
					parent.setUser(ids);
					window.parent.$("#dialog-form").dialog("close");
				});
	});


/*function selectBtnFun(){
	//alert(32482378);
	var selectes = $(".tbody input[type='checkbox']:checked");
	if(selectes.length<1){
		asyncbox.alert("请选择一条记录操作","提示信息");
		return ;
	}
	var ids= new Array();
	for(var i=0;i<selectes.length;i++){
		ids.push(selectes[i].id);
	}

	parent.setUser(ids);
	window.parent.$("#dialog-form").dialog("close");
}

function closeBtnFun(){
	alert(123456789);
	window.parent.$("#dialog-form").dialog("close");
}*/
