var dialogTest;
jQuery(function($){
  
	dialogTest = $( "#dialog-viewShow" );	
	dialogTest.dialog({
		autoOpen: false,
		height: 550,
		width: 680,
		resizable: false,
		modal: true,
		draggable: false
	});	
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	$("#seachBtn").click(function(){
		$("#submitBtn").click();
	});
	$("#cleanBtn").click(function(){
		$("#viewName").val('');
		$("#viewType").val('');	
		$("#dictName").val('');	
		$("#submitBtn").click();
	});
	$("#closeBtn").click(function(){
		window.parent.$("#dialog-form").dialog("close");
	});
	$("#selectBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		var ids= new Array();
		for(var i=0;i<selectes.length;i++){
			ids.push(selectes[i].id);
		}
	
		parent.setViews(ids);
	});		
	});
function viewShow(viewId,viewType,viewName,dictName,dictId,addFlg,deleteFlg,approveFlg,releaseFlg){
	jQuery("#dialog-viewShow").load("../view/viewshow.html", {"viewId":viewId,"viewType":viewType,"viewName":viewName,"dictId":dictId,"addFlg":addFlg,
	"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg,"operation":'show'}, function(e) {
	});
	dialogTest.dialog("option","title", "查看视图").dialog("open");		
};


