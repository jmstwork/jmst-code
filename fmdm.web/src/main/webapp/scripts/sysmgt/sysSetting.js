$(function($){
	$("#saveButton").click(function(){
		var url  = "./sysSettingSave.html";
		$.post(url,{
			"pwdCreateRule":$("input[type=radio]:checked").val(),
			"messageMode":$('input[name="messageMode"]:checked').val(),
			"emailMode":$('input[name="emailMode"]:checked').val(),
			"mqExceptionPath":$("#mqExceptionPath").val()
		},function(data){
			if(data.status == '1'){
				$(".panel").html("<img src='../images/msg/success.png' style='vertical-align: top;' /> 保存成功！");
			}else if(data.status == '2'){
				$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> MQ消息发送异常XML存储路径不存在！");
			}else{
				$(".panel").html("<img src='../images/msg/error.png' style='vertical-align: top;'/> 保存失败！");
			}
			$(".panel").slideToggle("slow");
			$(".panel").delay(2000).slideToggle("slow");
		});
		
		if($("#backMsg").val()!=null&&$("#backMsg").val()!=""){
			$(".panel").slideToggle("slow");
		}
	});
})
