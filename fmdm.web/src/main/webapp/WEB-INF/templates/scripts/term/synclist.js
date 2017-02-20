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
		    	 }
		     }
		}
	}
	if(syncIds=="" && status ==1){
		alert("请选择要启用的服务！");
		return;
	}else if(syncIds=="" && status ==0){
		alert("请选择要停用的服务！");
		return;
	}
	var testUrl = "./testDictSql.html";
	jQuery.post(testUrl,{"syncIds":syncIds,"status":status},function(e){
		var sqlId= e.testResult;
		if(e.testResult=="pass"){
			var url_ ="./updateDictStatus.html";
			jQuery.post(url_,{"syncIds":syncIds,"status":status},function(e){
				if(e.backStatus==1){
					alert("更新成功");
					doSearch();
				}else{
					alert("更新失败");
				}
			});
		}else if(status==1){
			alert("术语"+sqlId+" Sql校验未通过，不可启用");
		}else{
			alert("术语"+sqlId+" 同步执行中，不可停用");
		}
		
	});
	
}
function updateSyncTerm(obj){
	var form = document.synclistForm;
	form.action =  "./syncedit.html?editSyncId="+obj;
	form.submit();

}
function deleteSyncTerm(obj,count){
	var con = confirm("确定要删除吗？");
	if(con==true){
		var url_ ="./deleteSyncTerm.html";
		jQuery.post(url_,{"syncId":obj},function(e){
			if(e.status==1){
				alert("删除成功");
				doSearch();
			}else{
				alert("删除失败");
			}
		});
	}else{
		return false;
	}
}

function checkReason(obj){
	var url_ ="./faultReason.html";
	jQuery.post(url_,{"syncId":obj},function(e){
		alert(e.logTxt);
	});
}