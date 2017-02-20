$(function(){
	$("#searchBtn").click(function(obj){
		var form = $("#serviceForm");
		$("#sysId").val(window.parent.$("#paramSysId").val());
		form.submit();
	});
	
	$("#cleanBtn").click(function(obj){
		$("#hospitalCode option[value='000']").attr("selected",true);
		$("#serviceName").val("");
		$("#serviceId").val("");
		var form = $("#serviceForm");
		$("#sysId").val(window.parent.$("#paramSysId").val());
		form.submit();
	});
})

function subscribeWrite(subsId,subscribeId,serviceId,serviceName,hospitalName,applyUnitGroupName,execUnitGroupName,visitTypeName,msgHeaderType,orderExecName,extendSubId,sendSysId,sendSysName){
		var o = window.parent;
		//为父页面表单赋值
		o.$("#subsIds").val(subsId); 
		o.$("#subscribeId").val(subscribeId); 
		o.$("#serviceId").val(serviceId); 
		o.$("#serviceName").val(serviceName);
		var flag = true;
		o.$("#hospitalName").children("option").each(function(){
			if($(this).text()==hospitalName){
				$(this).attr("selected",true);
				flag = false;
			}
			if(flag){
				o.$("#hospitalName option[value='000']").attr("selected","true");
			}
		})
		o.$("#applyUnitGroupName").children("option").each(function(){
			if($(this).text()==applyUnitGroupName){
				$(this).attr("selected",true);
				flag = false;
			}
			if(flag){
				o.$("#applyUnitGroupName option[value='000']").attr("selected","true");
			}
		})
		
		o.$("#execUnitGroupName").children("option").each(function(){
			if($(this).text()==execUnitGroupName){
				$(this).attr("selected",true);
				flag = false;
			}
			if(flag){
				o.$("#execUnitGroupName option[value='000']").attr("selected","true");
			}
		})
		
		o.$("#sendSysId").children("option").each(function(){
			if($(this).text()==sendSysName){
				$(this).attr("selected",true);
				flag = false;
			}
			if(flag){
				o.$("#sendSysId option[value='000']").attr("selected","true");
			}
		})
		
		o.$("#visitTypeName").children("option").each(function(){
			if($(this).text()==visitTypeName){
				$(this).attr("selected",true);
				flag = false;
			}
			if(flag){
				o.$("#visitTypeName option[value='000']").attr("selected","true");
			}
		})
		if(msgHeaderType == '08'){
			o.$("#orderExecId").children("option").each(function(){
				if($(this).text()==orderExecName){
					$(this).attr("selected",true);
					flag = false;
				}
				if(flag){
					o.$("#orderExecId option[value='000']").attr("selected","true");
				}
			});
			o.$("#orderExecId").attr("disabled","true");
			o.$("#orderExecId").css("background","#cbcbcb");
		}
		o.$("#extendSubId").val(extendSubId);
		//将父页面控件设值不可编辑
		o.$("#serviceId").attr("readOnly","true");
		o.$("#serviceName").attr("readOnly","true");
		
		
		o.$("#hospitalName").attr("disabled","true");
		o.$("#applyUnitGroupName").attr("disabled","true");
		o.$("#execUnitGroupName").attr("disabled","true");
		o.$("#visitTypeName").attr("disabled","true");
		o.$("#sendSysId").attr("disabled","true");
		
		o.$("#extendSubId").attr("readOnly","true");
		
		o.$("#serviceId").css("background","#cbcbcb");
		o.$("#serviceName").css("background","#cbcbcb");
		o.$("#hospitalName").css("background","#cbcbcb");
		o.$("#applyUnitGroupName").css("background","#cbcbcb");
		o.$("#execUnitGroupName").css("background","#cbcbcb");
		o.$("#visitTypeName").css("background","#cbcbcb");
		o.$("#extendSubId").css("background","#cbcbcb");
		o.$("#sendSysId").css("background","#cbcbcb");
		if(serviceId.indexOf("MS")<0){
			o.$("#outputQueueName").val("OUT."+o.$("#paramSysId").val()+"."+serviceId+".LQ");
		}else{
			o.$("#outputQueueName").val("OUT."+o.$("#paramSysId").val()+"."+"MS000.LQ");
		}
		
		o.$("#serviceDialog").dialog("close");
	}