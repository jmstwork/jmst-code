var dialogTest;
jQuery(function($){
	dialogTest = $( "#dialog-viewShow" );
	
	dialogTest.dialog({
		autoOpen: false,
		height: 550,
		width: 680,
		resizable: false,
		title:"新增视图",
		modal: true,
		draggable: false
	});	
	$("#seachBtn").click(function(){
		var url  = "../view/viewlist.html";
		$("#viewForm").attr("action",url);
		$("#viewForm").submit();
	});
	
	$("#cleanBtn").click(function(){
		$("#userAccount").val('');
		$("#userName").val('');
	});

	$("#addBtn").click(function(){
		jQuery("#dialog-viewShow").load("../view/viewshow.html", {"operation":'add'}, function(e) {
		});	
		
		dialogTest.dialog("option","title", "新增视图").dialog("open");		
	});
	
	$("#updateBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(selectes.length>1){
			alert("您选择的多条记录，默认处理第一条");
		}

		jQuery("#dialog-viewShow").load("./viewshow.html", {"viewId":selectes[0].id,"operation":'edit'}, function(e) {
		});
		dialogTest.dialog("option","title", "修改视图").dialog("open");
	});
	
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		var ids = new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
		var viewId = ids.join(',');
		if(confirm("确认要删除选中记录吗")){
			var url  = "../view/deleteViews.html";
			$.post(url,{"viewId":viewId},function(data){
				if(data.status == '1'){
					alert("删除成功");
				}else{
					alert("删除失败")
				}
				var url_ = "../view/viewlist.html";
				location.href=url_;
			});
		}
	});
/*			 $("#Table_View tr").dblclick(function() {
		     alert("aa");  
		    });*/
});
function viewShow(viewId,viewType,viewName,dictName,dictId,addFlg,deleteFlg,approveFlg,releaseFlg){
	jQuery("#dialog-viewShow").load("./viewshow.html", {"viewId":viewId,"viewType":viewType,"viewName":viewName,"dictId":dictId,"addFlg":addFlg,
	"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg,"operation":'show'}, function(e) {
	});
	//dialogTest.dialog("open");
	dialogTest.dialog("option","title", "查看视图").dialog("open");
};


function doSearch(){
	var form = document.viewForm;
	form.action =  "./viewList.html";
	form.submit();
}