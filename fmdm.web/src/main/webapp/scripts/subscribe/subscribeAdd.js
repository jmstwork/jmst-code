$(function(){
	//返回按钮-返回已订阅画面
	$("#backBtn").click(function(e){
		var form = $("#addForm");
		$("#method").val("back");
		form.attr("action","../subscribe/detail.html");
		form.submit();
	});
	
	//重置按钮-清空addForm中的所有数据
	$("#resetBtn").click(function(e){
		$("#serviceId").val("");
		$("#serviceName").val("");
		
		$("#hospitalName option[value='000']").attr("selected",true);
		$("#applyUnitGroupName option[value='000']").attr("selected",true);
		$("#execUnitGroupName option[value='000']").attr("selected",true);
		$("#sendSysId option[value='000']").attr("selected",true);
		$("#visitTypeName option[value='000']").attr("selected",true);
		if($("#msgHeaderType").val()=='08'){
			$("#orderExecId option[value='000']").attr("selected",true);
			$("#orderExecId").removeAttr("disabled");
			$("#orderExecId").removeAttr("style").css("width","250px");
		}
		
		$("#extendSubId option[value='000']").attr("selected",true);
		$("#outputQueueName").val("");
		$("#subsDesc").val("");
		$("input[name='goOn']").attr("checked",false);
		
		$("#serviceId").removeAttr("readOnly");
		$("#serviceName").removeAttr("readOnly");
		$("#hospitalName").removeAttr("disabled");
		$("#applyUnitGroupName").removeAttr("disabled");
		$("#execUnitGroupName").removeAttr("disabled");
		$("#visitTypeName").removeAttr("disabled");
		$("#extendSubId").removeAttr("disabled");
		$("#sendSysId").removeAttr("disabled");
		
		$("#serviceId").removeAttr("style");
		$("#serviceName").removeAttr("style").css("background","#cbcbcb").attr("readOnly",true);
		$("#hospitalName").removeAttr("style").css("width","250px");
		$("#applyUnitGroupName").removeAttr("style").css("width","250px");
		$("#execUnitGroupName").removeAttr("style").css("width","250px");
		$("#visitTypeName").removeAttr("style").css("width","250px");
		$("#extendSubId").removeAttr("style").css("width","250px");
		$("#sendSysId").removeAttr("style").css("width","250px");
	});
	
	$("#serviceDialog").dialog({
		title:$("#paramSysName").val()+"("+$("#paramSysId").val()+") - "+"服务列表",
		autoOpen: false,
		height: 480,
		width: 900,
		resizable: true,
		modal: true,
		draggable: true,
		buttons: {
		   "关闭": function(){
	    	   $(this).dialog("close");
	       }
		},"close": function() {
			
		}
	});
	
	$("#chooseBtn").click(function() {
		var url_ = "../subscribe/serviceList.html?sysId="+$("#paramSysId").val()+"&msgHeaderType="+$("#msgHeaderType").val();
		$("#serviceFrame").attr("src",url_);
		$("#serviceDialog").dialog("open");
	});
	
	$("#saveBtn").click(function() {
		var serviceId = $("#serviceId").val(),
			serviceName = $("#serviceName").val(),
			hospitalId = $("#hospitalName").find("option:selected").val(),
			applyUnitGroupId = $("#applyUnitGroupName").find("option:selected").val(),
			execUnitGroupId = $("#execUnitGroupName").find("option:selected").val(),
			visitTypeId = $("#visitTypeName").find("option:selected").val(),
		    outputQueueName = $("#outputQueueName").val(),
		    subsDesc = $("#subsDesc").val(),
		    sendSysId = $("#sendSysId").val(),
		    extendSubId = $("#extendSubId").find("option:selected").val();
	    var msgHeaderType = $("#msgHeaderType").val();
	    if(msgHeaderType == '08'){
	    	orderExecId = $("#orderExecId").find("option:selected").val();
	    }
		var bValid = true;
		$(".validateTips").each(function(){
			$("#"+this.id).removeClass("ui-state-highlight");
			$("#"+this.id).text('');
		});
		
		
				
		bValid = checkInput("serviceId",serviceId, "服务ID",0,5) && bValid;
		bValid = checkInput("serviceName",serviceName, "服务名称",2,20) && bValid;
		bValid = checkNull("hospitalName",hospitalId, "医疗机构") && bValid;
		bValid = checkNull("applyUnitGroupName",applyUnitGroupId,"申请科室组") && bValid;
		bValid = checkNull("execUnitGroupName",execUnitGroupId,"执行科室组") && bValid;
		bValid = checkNull("visitTypeName",visitTypeId,"就诊类型") && bValid;
		bValid = checkNull("sendSysId",sendSysId,"发送系统") && bValid;
	    if($("#msgHeaderType").val()=="08"){
	    	bValid = checkNull("orderExecId",orderExecId, "医嘱执行分类") && bValid;
	    }
	    bValid = checkNull("extendSubId",extendSubId,"扩展码") && bValid;
		bValid = checkInput("outputQueueName",outputQueueName,"输出队列",13,25) && bValid;
		var data={};
		if($("#msgHeaderType").val()=="08"){
			data={
					"subsIds":$("#subsIds").val(),
					"subscribeId":$("#subscribeId").val(),
					"serviceId":serviceId,
					"serviceName":serviceName,
					"visitTypeId":visitTypeId,
				    "hospitalId":hospitalId,
				    "applyUnitGroupId":applyUnitGroupId,
				    "execUnitGroupId":execUnitGroupId,
				    "orderExecId":orderExecId==""?'*':orderExecId,
				    "extendSubId":extendSubId==""?0:extendSubId,
				    "outputQueueName":outputQueueName,
				    "subsDesc":subsDesc,
				    "paramSysId":$("#paramSysId").val(),
				    "sendSysId":sendSysId
				};			
		}else{
			data={
					"subsIds":$("#subsIds").val(),
					"subscribeId":$("#subscribeId").val(),
					"serviceId":serviceId,
					"serviceName":serviceName,
					"visitTypeId":visitTypeId,
				    "hospitalId":hospitalId,
				    "applyUnitGroupId":applyUnitGroupId,
				    "execUnitGroupId":execUnitGroupId,
				    "orderExecId":'',
				    "extendSubId":extendSubId==""?0:extendSubId,
				    "outputQueueName":outputQueueName,
				    "subsDesc":subsDesc,
				    "paramSysId":$("#paramSysId").val(),
				    "sendSysId":sendSysId
				};			
		}

		if(bValid){
			var url_ = "../subscribe/save.html";
			$.post(url_,data
					,function(e){
						asyncbox.alert(e.result+"同时需重启IE平台才能生效新的订阅关系。",'提示',function(action){
							if(action = 'ok'){
								var checked = $("input[name='goOn']:checked").length;
								if(checked==1){
									$("#resetBtn").click();
								}else{
									var form = $("#addForm");
									$("#method").val("save");
									form.attr("action","../subscribe/detail.html");
									form.submit();
								}
							}
						});
			        });
		  }
	});
	
	$("#serviceId").bind("blur",function () { 
		if($.trim($(this).val())!=""){
			var url_ = "../subscribe/selectServiceName.html";
			$.post(url_,
					{
					    "serviceId":$(this).val()
					},function(e){
						$("#serviceName").val(e.serviceName);
						var serviceId= $("#serviceId").val()
						if(e.serviceName!=""&&e.serviceName!=null && serviceId.indexOf("MS")<0){
							$("#serviceIdMsg").removeClass("ui-state-highlight");
							$("#serviceIdMsg").text("");
							$("#outputQueueName").val("OUT."+$("#paramSysId").val()+"."+$("#serviceId").val()+".LQ");
						}else if(e.serviceName!=""&&e.serviceName!=null && serviceId.indexOf("MS")>=0){
							$("#serviceIdMsg").removeClass("ui-state-highlight");
							$("#serviceIdMsg").text("");
							$("#outputQueueName").val("OUT."+$("#paramSysId").val()+"."+"MS000.LQ");
						}else{
							$("#outputQueueName").val("");
							updateTips("serviceId", "服务ID输入错误!");
						}
			        });
		}
	});
	
	//根据所选的医疗机构代码，帅选对应的申请科室组和执行科室组
	$("#hospitalName").bind("change",function(){
		$("#applyUnitGroupName").empty();
		$("#execUnitGroupName").empty();
		//发送系统ID
		$("#sendSysId").empty();
		var url_ = "../subscribe/selectGroupByHospitalCode.html";
		$.post(url_,
				{
				    "hospitalCode":$(this).find("option:selected").val()
				},function(e){
					var jason1 = eval(e.applyGroup);
					for(var i=0;i<jason1.length;i++){
						$("#applyUnitGroupName").append("<option value='"+jason1[i].id+"'>"+jason1[i].name+"</option");
					}
					
					var jason2 = eval(e.execGroup);
					for(var j=0;j<jason2.length;j++){
						$("#execUnitGroupName").append("<option value='"+jason2[j].id+"'>"+jason2[j].name+"</option");
					}
					
					var jason3 = eval(e.sendSys);
					for(var j=0;j<jason3.length;j++){
						$("#sendSysId").append("<option value='"+jason3[j].id+"'>"+jason3[j].name+"</option");
					}
					/*$("#applyUnitGroupName").append(e.applyGroup);
					$("#execUnitGroupName").append(e.execGroup);*/
				});
	});
	
})

function checkNull(id,value,desc){
	if(value=="000"){
		updateTips(id, desc + "不能为空 ");
		return false;
	}
	return true;
}


function checkInput(id,value, desc, min, max ) {
	if(value=="" || value==null){
		updateTips(id, desc + "不能为空 ");
		return false;
	}
	var l = value.length;
	if ( l > max || l < min ) {
		updateTips(id, desc + "的长度必须在【" + min + " ~ " + max + "】之间" );
		return false;
	} else {
		return true;
	}
}

function updateTips(id, t) {
	$("#"+id+"Msg").text(t).addClass( "ui-state-highlight" );
}
