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
				});
				$("#closeBtn").click(function(){
					window.parent.$("#dialog-form").dialog("close");
				});
				$("#selectBtn").click(function(){
					var selectes = $(".tbody input[type='checkbox']:checked");
					if(selectes.length<1){
						alert("请选择一条记录操作");
						return ;
					}
					var ids= new Array();
					for(var i=0;i<selectes.length;i++){
						ids.push(selectes[i].id);
					}
				
					parent.setUser(ids);
				});
	});
