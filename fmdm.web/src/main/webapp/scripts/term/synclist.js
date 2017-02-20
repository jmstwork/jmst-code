$(function(){
	$("#cleanBtn").click(function(){
		$("#dictId").val("");
		$("#dictName").val("");
	});
})

function doSearch(){
	var form = document.synclistForm;
	form.action =  "./synclist.html";
	form.submit();
}
document.onkeydown = function(e){
    if(!e) e = window.event;
    if((e.keyCode || e.which) == 13){
 	   doSearch();
    }
}
function checkParent(){
    var children = document.getElementsByName("childrenBox");
	var parent = document.getElementById("parentBox");
	var tempState=parent.checked;
	for(i=0;i<children.length;i++) {
	  	if(children[i].checked!=tempState)
	  		children[i].click();
	 }
}
function checkChildren() {
	 var children = document.getElementsByName("childrenBox");
	 var parent = document.getElementById("parentBox");
	 for(var i=0; i<children.length; i++) {
	     if(!children[i].checked) {
	    	 parent.checked = false;
	     return;
	     }
	 }
	 parent.checked = true;
}
function updateDictStatus(status){
	//1. 首先判断是不是全选
	var parent = document.getElementById("parentBox");
	var children = document.getElementsByName("childrenBox");
	var syncIds="" ;
	var statusCh="";
	if(status==1){
		statusCh="启用";
	}else{
		statusCh="未启用";
	}
	if(parent.checked){
		 for(var i=0; i<children.length; i++) {
			 if(children[i].parentElement.parentElement.children[7].innerText != statusCh){
				 if(syncIds ==null || syncIds ==""){
					 syncIds  = children[i].parentElement.parentElement.children[1].id;
				}else{
					syncIds =syncIds+"@"+children[i].parentElement.parentElement.children[1].id;
				}
			 }else{
	    		 if(statusCh=="启用"){
	    			 asyncbox.alert("请选择状态为未启用的服务进行启用！","提示信息");
	    			 return;
	    		 }else{
	    			 asyncbox.alert("请选择状态为启用的服务进行停用！","提示信息");
		    		 return;
	    		 }
	    	 }
		 }
	}else{
		for(var i=0; i<children.length; i++) {
		     if(children[i].checked) {
		    	 if(children[i].parentElement.parentElement.children[7].innerText != statusCh){
		    		 if(syncIds ==null || syncIds ==""){
			    		 syncIds  = children[i].parentElement.parentElement.children[1].id;
					}else{
						syncIds =syncIds+"@"+children[i].parentElement.parentElement.children[1].id;
					}
		    	 }else{
		    		 if(statusCh=="启用"){
		    			 asyncbox.alert("请选择状态为未启用的服务进行启用！","提示信息");
		    			 return;
		    		 }else{
		    			 asyncbox.alert("请选择状态为启用的服务进行停用！","提示信息");
			    		 return;
		    		 }
		    	 }
		     }
		}
	}
	if(syncIds=="" && status ==1){
		asyncbox.alert("请选择要启用的服务！","提示信息");
		return;
	}else if(syncIds=="" && status ==0){
		asyncbox.alert("请选择要停用的服务！","提示信息");
		return;
	}
	var testUrl = "./testDictSql.html";
	jQuery.post(testUrl,{"syncIds":syncIds,"status":status},function(e){
		var sqlId= e.testResult;
		if(e.testResult=="pass"){
			var url_ ="./updateDictStatus.html";
			jQuery.post(url_,{"syncIds":syncIds,"status":status},function(e){
				if(e.backStatus==1){
					asyncbox.alert("更新成功！","提示信息",function(action){
						if(action='ok'){
							doSearch();
						}
					});
				}else{
					asyncbox.alert("更新失败！","提示信息");
				}
			});
		}else if(status==1){
			asyncbox.alert("术语"+sqlId+" Sql校验未通过，不可启用","提示信息");
		}else{
			asyncbox.alert("术语"+sqlId+" 同步执行中，不可停用","提示信息");
		}
		
	});
	
}
function updateSyncTerm(obj){
	var form = document.synclistForm;
	form.action =  "./syncedit.html?editSyncId="+obj;
	form.submit();

}
function deleteSyncTerm(obj,aObj){
	var trObj = $(aObj).parent().parent();
	trObj.css("background","#fab5b5");
	asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
		 if(action == "ok"){
		var url_ ="./deleteSyncTerm.html";
		jQuery.post(url_,{"syncId":obj},function(e){
			if(e.status==1){
				asyncbox.alert("删除成功","提示信息");
				doSearch();
			}else{
				asyncbox.alert("删除失败","提示信息");
			}
		});
	}else{
		trObj.css("background","");
	}
	})
}

function checkReason(obj){
	var url_ ="./faultReason.html";
	jQuery.post(url_,{"syncId":obj},function(e){
		asyncbox.alert(e.logTxt);
	});
}