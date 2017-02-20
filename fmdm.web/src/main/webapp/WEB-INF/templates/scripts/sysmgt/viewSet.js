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
	$("textarea").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).text();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).text(v);
		}
	});
	var roleName = $( "#roleName" ),
		memo=$("#memo"),
		allFields = $( [] ).add( roleName ),
	    tips = $( ".validateTips" );

	function updateTips( o, t ) {
		//tips.text(t).addClass( "ui-state-highlight" );
		//o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		tips.text(t).addClass( "ui-state-highlight" );
	}
	
	$("#addBtn").click(function(){
		var url_ = "../role/selectViews.html";
		//location.href=url_;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
	});
	$("#dialog-form" ).dialog({
		autoOpen: false,
		height: 520,
		width: 900,
		resizable: false,
		modal: true,
		draggable: false
	});
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(confirm("确认要删除选中记录吗")){
			selectes.parent().parent().remove();
			if($("#parentBox")[0].checked){
				$("#parentBox")[0].checked=false;
			}
		}
	});
	
	$("#saveBtn").click(function(){				
		var roleName = $( "#roleName" ),
			memo=$("#memo");				
			var roleId = $( "#roleId" ).val(),
			    roleName = $( "#roleName" ).val(),
				memo=$("#memo").val();
			selects = $(".tbody input[type='checkbox']");
			var ids = new Array();
			for(var i=0;i<selects.length;i++){
				var id=selects[i].parentNode.id;
				ids.push(id);
			}
			var viewIds = ids.join(',');
			var url  = "../role/saveRoleView.html";
			$.post(url,{"roleId":roleId,"roleName":roleName,"memo":memo,"viewIds":viewIds},function(data){
				if(data.status == '1'){
					alert("保存成功！");
					var url_ = "../role/roleList.html";
					location.href=url_;
				}else{
					alert("保存失败！");
				}
			});		
	});
	$("#returnBtn").click(function(){
		var url_ = "../authority/authorityMain.html";
		location.href=url_;
	});
});	
function setViews(ids){
	var selects = $(".tbody input[type='checkbox']");
	var repeat=""
	for(var i=0;i<selects.length;i++){
		for(var j=0;j<ids.length;j++){
			var viewId=selects[i].parentNode.id;
			var viewName = selects.parent().parent()[i].childNodes[2].innerText;
			if(viewId == ids[j]){
				if(repeat ==""){
					repeat=viewName;
				}else{
					repeat+=","+viewName;
				}
			}
		}
	}
	if(repeat!=""){
		alert("已选择视图列表中已存在以下视图："+repeat+"  请重新确认");
		return;
	}
	var url  = "../role/selectViewsByViewsId.html?viewIds="+ids;
	$.post(url,function(data){
		var viewList=data.viewList;
		for(var i=0;i<viewList.length;i++){										
			var view = viewList[i];						
			var rowcnt = $("table[id$='viewTable']>tbody").children("tr").length+1;
			var r = jQuery(".tbody").children().length;
			var tr = "<tr>";
			var td = "";						
			td = td +"<td align='center' id="+view.viewId+">"+"<input type='checkbox' id='childrenBox' name='childrenBox' onclick='javascript:checkChildren();'/>"+"</td>";
			td = td +"<td align='center'>"+rowcnt+"</td>";
			td = td +"<td>"+view.viewName+"</td>";
			td = td +"<td>"+view.viewTypeCN+"</td>";
			td = td +"<td>"+view.dictName+"</td>";
			td = td +"<td>"+view.tableName+"</td>";
			td = td +"<td>"+"<a href='javascript:void(0);' th:onclick="+"'"+"'javascript:viewShow(\'' + ${view.viewId} + '\',\'' + ${view.viewType} + '\',\'' + ${view.viewName} + '\',\'' + ${view.dictName} + '\',\'' + ${view.dictId} + '\',\'' + ${view.addFlg} + '\',\'' + ${view.deleteFlg} + '\',\'' + ${view.approveFlg} + '\',\'' + ${view.releaseFlg} + '\');'"+"'"+" style='color:blue;text-decoration:underline; ' >查看</a>"+"</td>";			
			tr = tr + td + "</tr>";
			jQuery(".tbody").append(tr);
		}
		alert("已选择成功。");
	});
}

function viewShow(viewId,viewType,viewName,dictName,dictId,addFlg,deleteFlg,approveFlg,releaseFlg){
	jQuery("#dialog-viewShow").load("../view/viewshow.html", {"viewId":viewId,"viewType":viewType,"viewName":viewName,"dictId":dictId,"addFlg":addFlg,
	"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg,"operation":'show'}, function(e) {
	});
	dialogTest.dialog("option","title", "查看视图").dialog("open");	
};



