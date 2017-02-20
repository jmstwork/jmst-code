$(function(){
	$("#cleanBtn").click(function(){
		$("#condRuleName").val("");
		$("#condStatus").val("-1");
		$("#condModelName").val("");
		$("#condRuleGroName").val("");
	});
	
	
	$("#approveBtn").click(function(){
		var ruleIds = "";
	    $("input[name='childrenBox']").each(function(args) {
	    	if (this.type == "checkbox" && this.checked)
	    		ruleIds += this.value + ",";
	    });
			//判断是否存在不合法的数据
			if(ruleIds.length>0){
				ruleIds = ruleIds.substring(0, ruleIds.length - 1);
				 var url_ ="../rule/checkStatus.html?ruleIds="+ruleIds;
				 $.getJSON(url_, function(data) {
					 if (data == '1') {
						 asyncbox.alert("只有未提交状态，才能进行规则审批！","提示信息");
						 return ;
					 }else{
						        	var url  = "../rule/approveRules.html";
									$.post(url,{"ruleIds":ruleIds},function(data){
										if(data == '1'){
											asyncbox.success("规则审批提交成功！","执行结果",function(){
												var url_ = "../rule/ruleList.html";
												location.href=url_;
											})
										}else{
											asyncbox.error("规则审批提交失败！","执行结果");
										}
									});
					 }
				 });
			}else{
				asyncbox.alert("请选择要提交审批的记录！","提示信息");
				return ;
			}
	});
})

function deleteRule(ruleId,obj){
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
		asyncbox.confirm('确定要删除该条数据吗？','规则一览',function(action){
			if(action == 'ok'){
				var url_ ="../rule/deleteRule.html";
				jQuery.post(url_,{"ruleId":ruleId},function(e){
					if(e.status=='1'){
						asyncbox.success('刪除成功！','规则一览',function(){
							var url_ = "../rule/ruleList.html";
							location.href=url_;
						});						
					}else{
						asyncbox.error('刪除失败！','规则一览');
					}
				});
			}else{
				trObj.css("background","");
			}
		});
	}