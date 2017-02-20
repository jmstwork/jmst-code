/**
 * 
 */

var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	async : {
		enable : true,
		type: "post",
		url : "../menus/getChildNodes.html",
		autoParam : [ "id", "name", "level=lv" ],
		otherParam : {
			//"otherParam" : "zTreeAsyncTest"
		}
	// dataFilter: filter
	},
	data : {
		simpleData : {
			enable : true,
			rootPId: -1
		}
	},
	callback : {
		onClick:onClick,
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : onRemove,
		onRename : onRename
	}
};
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		childNodes[i].isParent =childNodes[i].parent;
	}
	return childNodes;
}

var log, className = "dark";
function onClick(event, treeId, treeNode, clickFlag){
	cleanForm("#menuForm");
	$("#smbId").val(treeNode.id);
	$("#parentNode").val(treeNode.pId);
	$("#smbName").val(treeNode.name);
	$("#smbLink").val(treeNode.url);
	if(treeNode.otherAttr!= undefined){
		$("#updateCount").val(treeNode.otherAttr["updateCount"]);
	}
	disableForm("#menuForm");
}
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
//	className = (className === "dark" ? "":"dark");
//	showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	zTree.selectNode(treeNode);
	var flag = confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
	if(flag) enableForm("#menuForm");
	return false;
}
function beforeRemove(treeId, treeNode) {
	return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}
function onRemove(e, treeId, treeNode) {
	if(treeNode.id==""||treeNode.id == null){
		return ;
	}
	$.post("../menus/deleteNodes.html",{smbId:treeNode.id},function(data){
		if(data.success){
			asyncbox.success("删除成功","执行结果",function(){

			});
		}else{
			asyncbox.error("删除失败！","执行结果",function(){
				//trObj.css("background","");
			});
		}
	});
}
function beforeRename(treeId, treeNode, newName, isCancel) {
//	if (newName.length == 0) {
//		alert("节点名称不能为空.");
//		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//		setTimeout(function(){zTree.editName(treeNode)}, 10);
//		return false;
//	}
//	return true;
}
function onRename(e, treeId, treeNode, isCancel) {

}
function showRemoveBtn(treeId, treeNode) {
	return !(treeNode.id==="root");
}
function showRenameBtn(treeId, treeNode) {
	return !(treeNode.id==="root");
}

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	if(treeNode.level < 2){           //只有父菜单才会出现"增加"按钮
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var node = {id:"", pId:treeNode.id, name:"新节点"};
			if(treeNode.pId===-1){
				node.isParent = true;
			}
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			
			zTree.addNodes(treeNode, node);
			return false;
		});
	}
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};


function cleanForm(form){
	$(':input:not(:disabled)',form) 
	.not(':button, :submit, :reset, :hidden') 
	.val('') 
	.removeAttr('checked') 
	.removeAttr('selected');
}


function disableForm(form){
	$(':input',form) 
	.not(':button, :submit, :reset, :hidden') 
	.attr("disabled",true)
	.removeAttr('checked') 
	.removeAttr('selected');
}

function enableForm(form){
	$(':input',form) 
	.not(':button, :submit, :reset, :hidden') 
	.removeAttr('checked') 
	.removeAttr('disabled') 
	.removeAttr('selected');
}

$(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
	
	$("#cleanBtn").click(function(){
		cleanForm("#menuForm");
	});
	
	$("#saveBtn").click(function(){
		var url  = "../menus/handleNodes.html";
		var obj = {
			smbId : $("#smbId").val(),
			parentNode : $("#parentNode").val(),
			smbName : $("#smbName").val(),
			smbLink : $("#smbLink").val(),
			type : $("#type").val(),
			updateCount : $("#updateCount").val()
		}
		$.post(url,obj,function(data){
			if(data.success){
				asyncbox.success("保存成功","执行结果",function(){
					var url_ = "../menus/manage.html";
					location.href=url_;
				});
			}else{
				asyncbox.error("删除失败！","执行结果",function(){
					trObj.css("background","");
				});
			}
		});
	});
});