//初始化执行切换创建方式
$(document).ready(function(){ 
	if($("#operFlg").val()!=""){
		$("#copy").attr("checked","checked");
		radioClick(1);
	}else{
		$("#self").attr("checked","checked");
	}});
//切换创建方式
function radioClick(v){
		//alert(v);
		if(v==0){
			jQuery("#ruleListDiv").hide()
		}else if(v==1){
			document.getElementById("operFlg").value= "-1";
			jQuery("#ruleListDiv").show();
		}
	}
//搜素
function searchClick(){
	var form = document.getElementById("ruleForm");
	form.submit();
}
//清空
function cleanBtnClick(){
	$("#condRuleName").attr("value","");
	$("#condStatus").attr("value","-1");
	$("#condModelName").attr("value","");
	$("#condRuleGroName").attr("value","");
}
//确认
function okBtnClick(){
	if($("#copy").attr("checked")){
		if($("#ruleId").val()==""){
			asyncbox.error("请选择一条规则");
			return false;
		}else{
			var url_ = "../rule/initCopyRulePage.html?ruleId="+$("#ruleId").val() + "&type=copy";
			$("#creadeRuleForm").attr("action",url_);
			$("#submitBtn").click();
		}
	}else{
		$("#creadeRuleForm").attr("action","../rule/initCreateRulePage.html");
		$("#submitBtn").click();
	}
}
//设置所选复制规则ID
function checkRule(ruleId){
	jQuery("#ruleId").val(ruleId);
}


//var noAuth = "您无权限执行此操作。请联系系统管理员，获得相应的权限。";
$.ajaxSetup({
    error: function (jqXHR, textStatus, errorThrown) {
        if(jqXHR.status == 403){
        	alert("您无权限执行此操作。请联系系统管理员，获得相应的权限。");
        };
        if(jqXHR.status == 401){
        	var sheight = screen.availHeight-160;
       	 	var swidth = screen.availwidth-10;
       	    var winoption    ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
       	    window.open(getRootPath()+"/j_spring_security_logout",'',winoption);
       	    window.top.open('','_self');
       	    window.top.opener = null;
       	    window.top.close();
        };
        return false;
    }
});

function callback() {
	//var sheight = screen.availHeight-160;
 	//var swidth = screen.availwidth-10;
    //var winoption    ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
    //window.open(getRootPath()+"/j_spring_security_logout",'_self',winoption);
    //window.top.open('','_self');
    //window.top.opener = null;
    //window.top.close();
    window.top.location.href = getRootPath()+"/j_spring_security_logout";
}

function errorCallback(){
	window.top.location.href = getRootPath()+"/j_spring_security_logout";
}
