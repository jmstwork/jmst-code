$(function(){

	$("#releaseBtn").click(function(){
		var ruleIds = "";
	    $("input[name='childrenBox']").each(function(args) {
	    	if (this.type == "checkbox" && this.checked)
	    		ruleIds += this.value + ",";
	    });
			//判断是否存在不合法的数据
			if(ruleIds.length>0){
				ruleIds = ruleIds.substring(0, ruleIds.length - 1);
				 var url_ ="../rmlg/checkStatus.html?ruleIds="+ruleIds;
				 $.getJSON(url_, function(data) {
					 if (data == '1') {
						 asyncbox.alert("待审批和已驳回的记录不能进行发布！","提示信息");
						 return ;
					 }else{
								asyncbox.open({
									id:"ruleRelease",
									modal:true,
									drag:true,
									title:"",
									url:"../rmlg/releaseAgreeRejectView.html?flag=release&ruleIds="+ruleIds,
									btnsbar:$.btn.OKCANCEL,
									callback : function(action){
										if(action == 'ok'){
											var ruleIds = asyncbox.opener('ruleRelease').document.getElementById("ruleIds").value;
											var content = asyncbox.opener('ruleRelease').document.getElementById("content").value;
											if(content == ""){
												asyncbox.alert("发布说明不能为空，请重新操作！","提示信息");
												return ;
											}
											var url_ = "../rmlg/saveRuleVersion.html?ruleIds=" + ruleIds
											+ "&content=" + content;
											$.getJSON(url_, "", function(data) {
												
												if (data == '1') {
													asyncbox.success("保存成功","",function(action){
														if(action=="ok"){
															asyncbox.close('ruleRelease');
															var url_ = "../rmlg/versionView.html";
															location.href=url_;
														}
													});
												} else {
													asyncbox.error('保存失败！', '执行失败');
												}
											});
											}
										},
									width:550,
									height:270
								});
					 }
				 });
			}else{
				asyncbox.alert("请选择要发布的记录！","提示信息");
				return ;
			}
	});
	
	$("#seachBtn").click(function() {
//		var ruleName = $("#ruleName").val();
//		alert(ruleName);
		var form = document.ruleForm;
		form.action = "../rmlg/examApproveView.html";
		form.submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#ruleName").val("");
		$("#status").val("");
	});
	
})

function ruleDetails(ruleId,ruleName){
	if (ruleId == null)
		return;
	else {
		$("input[id='ruleId']").each(function() {
			this.value = ruleId;
		});
	}
	if (ruleName == null)
		return;
	else {
		$("input[id='ruleName1']").each(function() {
			this.value = ruleName;
		});
	}
	var form = document.ruleForm;
	form.action =  "../rmlg/ruleDataDetailView.html";
	form.submit();
}