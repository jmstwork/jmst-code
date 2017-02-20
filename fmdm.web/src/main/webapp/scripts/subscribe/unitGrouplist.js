jQuery(function($){
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	
	$("#seachBtn").click(function(){
		var url  = "../subscribe/unitGroupList.html";
		$("#DataForm").attr("action",url);
		$("#DataForm").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#unitGroupId").val('');
		$("#unitGroupName").val('');
		$('#hospitalCode').val('');
		var url  = "../subscribe/unitGroupList.html";
		$("#DataForm").attr("action",url);
		$("#DataForm").submit();
	});
	
	$("#addBtn").click(function(){
		var url = "../subscribe/unitGroupEdit.html";
		operate(url,"0");
	});
	
});

function unitGroupDetail(unitGroupId,hospitalCode){
	var url_ = "../subscribe/unitGroupDetail.html?unitGroupId="+unitGroupId+"&hospitalCode="+hospitalCode;
	//location.href=url_;
	$("#dataIframe").attr("src",url_);
	$("#dialog-form").dialog("open");
}

function editUG(unitGroupId){
	var url  = "../subscribe/unitGroupEdit.html?editUnitGroupId="+unitGroupId;
	operate(url,"1");
}

function deleteUG(unitGroupId,obj){
	//校验科室组Id是否在订阅中订阅
	var url  = "../subscribe/checkRepeatUnitGroup.html";
	$.post(url,{"unitGroupIds":unitGroupId},function(data){
		if(data.repeatId != ""){
			asyncbox.alert("科室组"+data.repeatId+"在订阅管理中有服务订阅，不允许删除","提示信息");
			return;
		}else{
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5");
			asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
				if(action == "ok"){
					var url  = "../subscribe/deleteUnitGroup.html";
					$.post(url,{"unitGroupIds":unitGroupId},function(data){
						if(data.status == '1'){
							asyncbox.success("删除成功！","执行结果",function(){
								var url_ = "../subscribe/unitGroupList.html";
								location.href=url_;
							});
						}else{
							asyncbox.error("删除失败,请联系管理员！","执行结果",function(){
								var url_ = "../subscribe/unitGroupList.html";
								location.href=url_;
							});
						}
						
					});
				}else{
					trObj.css("background","");
				}
			});
		}
	});
}

function unitSet(unitGroupId){
	var url  = "../subscribe/unitSetting.html?editUnitGroupId="+unitGroupId;
	$("#DataForm").attr("action",url);
	$("#DataForm").submit();
}


function operate(url,flag){
	asyncbox.open({
		id : "chooseCard",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : url,
		width : 500,
		height : 280,
		btnsbar: [{
		     text    : '保存', 
		     action  : 'save'
		},{
			 text    : '关闭', 
		     action  : 'close_1'
		}],
		callback : function(action,opener){
			if(action == 'save'){
				var unitGroupId = opener.$("#unitGroupId").val().replace(/(^\s*)|(\s*$)/g,"");
				var isNull = false;
				if(unitGroupId==""&&unitGroupId!=null){
					opener.$("#unitGroupIdDesc").css("color","red").text("科室组ID不能为空！");
					isNull = true;
				}else{
					opener.$("#unitGroupIdDesc").text("");
					isNull = false;
				}
				
				var unitGroupName = opener.$("#unitGroupName").val().replace(/(^\s*)|(\s*$)/g,"");
				if(unitGroupName==""&&unitGroupName!=null){
					opener.$("#unitGroupNameDesc").css("color","red").text("科室组名称不能为空！");
					isNull = true;
				}else{
					opener.$("#unitGroupNameDesc").text("");
					isNull = false;
				}
				
				var hospitalCode = opener.$("#hospitalCode option:selected").val();
				if(hospitalCode==""&&hospitalCode!=null){
					opener.$("#hospitalCodeDesc").css("color","red").text("医疗结构不能为空！");
					isNull = true;
				}else{
					opener.$("#hospitalCodeDesc").text("");
					isNull = false;
				}
				
				var unitGroupType = opener.$("#unitGroupType option:selected").val();
				if(unitGroupType==""&&unitGroupType!=null){
					opener.$("#unitGroupTypeDesc").css("color","red").text("科室组类别不能为空！");
					isNull = true;
				}else{
					opener.$("#unitGroupTypeDesc").text("");
					isNull = false;
				}
				
				var unitGroupDesc = opener.$("#unitGroupDesc").val().replace(/(^\s*)|(\s*$)/g,"");
				if(isNull){
					return false;
				}
				
				var url  = "../subscribe/saveUnitGroup.html";
				$.post(url,{"flag":flag,"unitGroupId":unitGroupId,"unitGroupName":unitGroupName,"hospitalCode":hospitalCode,"unitGroupType":unitGroupType,"unitGroupDesc":unitGroupDesc},function(data){
					if(data.status == '1'){
						asyncbox.success("保存成功!","执行结果",function(){
							var url_ = "../subscribe/unitGroupList.html";
							location.href=url_;
						});
					}else{
						asyncbox.error("保存失败","执行结果",function(){
							var url_ = "../subscribe/unitGroupList.html";
							location.href=url_;
						});
					}
				});
			}
		}
	})
}