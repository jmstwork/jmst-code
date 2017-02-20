$(function(){
	$("#agreeBtn").click(function(){
		var ruleId = $( "#ruleId" ).val();
		var url_ ="../rmlg/checkStatus1.html?ruleIds="+ruleId;
		 $.getJSON(url_, function(data) {
			 if (data == '1') {
				 asyncbox.alert("已审批,已驳回,已发布的规则不能再次审批！","提示信息");
				 return ;
			 }else{
					asyncbox.open({
						id:"ruleExamApprove",
						modal:true,
						drag:true,
						title:"",
						url:"../rmlg/agreeView.html?flag=release&ruleIds="+ruleId,
						btnsbar:$.btn.OKCANCEL,
						callback : function(action){
							if(action == 'ok'){
								var ruleIds = asyncbox.opener('ruleExamApprove').document.getElementById("ruleIds").value;
								var content = asyncbox.opener('ruleExamApprove').document.getElementById("content").value;
								if(content == ""){
									asyncbox.alert("审批意见不能为空，请重新操作！","提示信息");
									return ;
								}
								var url_ = "../rmlg/changeStatus.html?ruleIds=" + ruleId
								+ "&content=" + content;
								$.getJSON(url_, "", function(data) {
									if (data == '1') {
										asyncbox.success("操作成功","",function(action){
											if(action=="ok"){
												asyncbox.close('ruleRelease');
												if($("#ruleName").val() != null){
													$("#ruleName").val("");
												}
												var form = document.dataForm;
												form.action = "../rmlg/examApproveView.html";
												form.submit();
											}
										});
									} else {
										asyncbox.error('操作失败！', '执行失败');
									}
								});
								}
							},
						width:550,
						height:270
					});
			 }
		 })
	});
	$("#rejectBtn").click(function(){
		var ruleId = $( "#ruleId" ).val();
		var url_ ="../rmlg/checkStatus2.html?ruleIds="+ruleId;
		$.getJSON(url_, function(data) {
			if (data == '1') {
				asyncbox.alert("已驳回,已发布的规则不能驳回！","提示信息");
				return ;
			}else{
				asyncbox.open({
					id:"ruleExamApprove",
					modal:true,
					drag:true,
					title:"",
					url:"../rmlg/rejectView.html?flag=release&ruleIds="+ruleId,
					btnsbar:$.btn.OKCANCEL,
					callback : function(action){
						if(action == 'ok'){
							var ruleIds = asyncbox.opener('ruleExamApprove').document.getElementById("ruleIds").value;
							var content = asyncbox.opener('ruleExamApprove').document.getElementById("content").value;
							if(content == ""){
								asyncbox.alert("审批意见不能为空，请重新操作！","提示信息");
								return ;
							}
							var url_ = "../rmlg/changeStatus1.html?ruleIds=" + ruleId
							+ "&content=" + content;
							$.getJSON(url_, "", function(data) {
								if (data == '1') {
									asyncbox.success("操作成功","",function(action){
										if(action=="ok"){
											asyncbox.close('ruleRelease');
											if($("#ruleName").val() != null){
												$("#ruleName").val("");
											}
											var form = document.dataForm;
											form.action = "../rmlg/examApproveView.html";
											form.submit();
										}
										
									});
								} else {
									asyncbox.success("操作失败","");
								}
							});
						}
					},
					width:550,
					height:270
				});
			}
		})
	});

	$("#returnBtn").click(function(){
		if($("#ruleName").val() != null){
			$("#ruleName").val("");
		}
		var form = document.dataForm;
		form.action = "../rmlg/examApproveView.html";
		form.submit();
	});
	
	$("#seachBtn").click(function(){
		var form = document.dataForm;
		form.action = "../rmlg/ruleDataDetailView.html";
		form.submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#description").val("");
	});
})
function nextPage(form){
	var cp = parseInt(form.currentPage.value, 10);
    var tp = parseInt(form.totalPage.value, 10);
    form.reset();
    if(cp >= tp){
        form.jumpToPage.value = tp;
    	form.currentPage.value = tp;
    }
    else{
        form.jumpToPage.value = cp + 1;
        form.currentPage.value = cp + 1;
    }
    form.submit();
}
function prevPage(form){
	var cp = parseInt(form.currentPage.value, 10);
    form.reset();
    if(cp <= 1){
        form.jumpToPage.value = 1;
        form.currentPage.value = 1;
    }
    else{
        form.jumpToPage.value = cp - 1;
        form.currentPage.value = cp - 1;
    }
    form.submit();
}
function firstPage(form){
	form.reset();
    form.jumpToPage.value = 1;
    form.currentPage.value = 1;
    form.submit();
}
function lastPage(form){
    form.reset();
    form.jumpToPage.value = form.totalPage.value;
    form.currentPage.value = form.totalPage.value;
    form.submit();
}
function jumpToPage(form, pageNo){
	   // form.reset();
	    //alert(form.currentPage.value);
	    var tp = parseInt(form.totalPage.value, 10);
	    try
	    {
	    	var inpVal = $( "#jumpToInput" ).val();
	        var iinpVal = parseInt(inpVal, 10);
	        
//	        alert(inpVal);
	        if(isNaN(iinpVal) || iinpVal < 1)
	        {
	            var msg = "请输入正确的页码！";
	            var err = new Error(msg);
	            if(!err.message)
	            {
	                err.message = msg;
	            }
	            throw err;
	        }
	        else if(iinpVal > tp)
	        {
	            var msg = "输入的页码超过总页数，请重新输入！";
	            var err = new Error(msg);
	            if(!err.message)
	            {
	                err.message = msg;
	            }
	            throw err;
	        }
	        else
	        {
	            //form.reset();
	            form.jumpToPage.value = iinpVal;
	            form.currentPage.value = iinpVal;
	            form.submit();
	            form.reset();
	        }
	    }
	    catch(e)
	    {
	        //alert(e.message);
	    	alert(e);
//	        pageNo.focus();
//	        pageNo.select();
	    }

}