$(document).ready(function(){
	showAuthorityTree();
});


///动态设置zTree的所有节点的checkbox不可编辑
function DynamicUpdateNodeCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    //获得zTree的所有节点            
    var nodes = zTree.getNodes();
    //遍历每一个节点然后动态更新nocheck属性值
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        node.chkDisabled = true; //表示显示checkbox
        zTree.updateNode(node);
        getAllChildrenNodes(node);
    }
}

//递归，得到叶子节点的数据，并将所有节点的chkDisabled属性变为true。
function getAllChildrenNodes(node){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if (node.isParent) {
      var childrenNodes = node.children; 
      if (childrenNodes) {
          for (var i = 0; i < childrenNodes.length; i++) {
           if(childrenNodes[i].isParent){
				childrenNodes[i].chkDisabled = true;
	            zTree.updateNode(childrenNodes[i]);
            	getAllChildrenNodes(childrenNodes[i]); 
           }else{
				childrenNodes[i].chkDisabled = true;
            	zTree.updateNode(childrenNodes[i]); 
           }
          } 
      } 
  } 
}

function showAuthorityTree(){
	var roleId = $("#roleId").val();
	var setting = {
			async:{
					enable: true,
					url:"../authority/getRoleAuthorityTree.html?roleId="+roleId,
					autoParam:["id", "name", "level=lv"],
					dataFilter:filter
			},
			check:{
				enable:true
			},
			data : {
				simpleData : {
					enable : true,
					rootPId: -1
				}
			},
			callback:{
				//异步加载全部数据，成功后，展开第一节点
				onAsyncSuccess:showFirstNode,
				//失败
				onAsyncError:asyncError,
				//单击节点
				onClick:onClick
			},
			view: {
				//取消双击展开父节点的功能
				dblClickExpand: false
			}
		};
	$.fn.zTree.init($("#treeDemo"),setting);
}

//展开第一层节点
function showFirstNode(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
	var flag = $("#flag").val();
	if(flag == 'returnUserRole'){
		DynamicUpdateNodeCheck();
	}
}

//异步加载失败
function asyncError(){
	alert("加载权限树失败");
}

function onClick(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if(treeNode.children==undefined){//没有子节点，即自己就是叶子节点
		//单击叶子节点，会选中或取消选中该节点
		treeObj.checkNode(treeNode,null,true);	
	}else{//有子节点
		//单击该节点，会展开其孩子节点
		treeObj.expandNode(treeNode,null,false,true);
	}
}

//用于把异步加载的节点的url变成“”，否则，单击节点，会跳转链接
function filter(treeid, parentNode, childNodes){
	if(childNodes){
		for(var i = 0; i < childNodes.length; i++){
			childNodes[i].url="";
		}
	}
	return childNodes;
}
