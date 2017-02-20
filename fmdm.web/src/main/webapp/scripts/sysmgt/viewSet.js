jQuery(function($) {
	var flag = $("#flag").val();
	$("input[type='text']").keyup(function(event) {
		if (event.keyCode == '32') {
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	
	$("textarea").keyup(function(event) {
		if (event.keyCode == '32') {
			var v = $(this).text();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).text(v);
		}
	});
	
	var roleName = $("#roleName"), memo = $("#memo"), allFields = $([]).add(
			roleName), tips = $(".validateTips");

	function updateTips(o, t) {
		// tips.text(t).addClass( "ui-state-highlight" );
		// o.addClass( "ui-state-error" );
		var id = $(o).attr("id");
		tips.text(t).addClass("ui-state-highlight");
	}

	$("#searchBtn").click(function() {
		$("#viewSetForm").submit();
	})
	
	$("#cleanBtn").click(function() {
//		$("#viewName").val("");
//		$("#viewType").val("");
//		$("#dictName").val("");
		var url_ = "../role/setRoleView.html?roleId="+$("#roleId").val()+"&flag="+flag;
		location.href=url_;
	})
	
	$("#addBtn").click(function() {
		alert("点击新增按钮的时候flag"+flag);
		asyncbox.open({
			id : "viewList",
			modal : true,
			drag : true,
			scrolling :"yes",
			title : "",
			url : "../role/selectViews.html?roleId="+$("#roleId").val(),
			width : 800,
			height : 404,
			align:'left',
			btnsbar: [{
			     text    : '选择', 
			     action  : 'save'
			},{
				 text    : '关闭', 
			     action  : 'close_1'
			}],
			callback : function(action,opener){
				if(action == 'save'){
					var obj = opener.$('input[name="childrenBox"]:checked');
					var ids = new Array();
					obj.each(function(i){
						var id = obj[i].id;
						ids.push(id);
					})
					var viewIds = ids.join(',');
					if(viewIds==""){
						asyncbox.alert("请至少选择一个视图！","提示");
						return false;
					}else{
						var url  = "../role/saveRoleView.html";
						$.post(url,{"roleId":$("#roleId").val(),"viewIds":viewIds},function(data){
							if(data.status == '1'){
								asyncbox.success("保存成功！","执行结果",function(){
									alert("保存成功的时候flag"+flag);
									var url_ = "../role/setRoleView.html?roleId="+$("#roleId").val()+"&flag="+flag;
									location.href=url_;
								});
							}else{
								asyncbox.error("保存失败！","执行结果",function(){
									var url_ = "../role/setRoleView.html?roleId="+$("#roleId").val()+"&flag="+flag;
									location.href=url_;
								});
							}
						});
					}
				}
			}
		})
	});
	
	
	$("#deleteBtn").click(function() {
		var selectes = $(".tbody input[type='checkbox']:checked");
		if (selectes.length < 1) {
			asyncbox.alert("请选择一条记录操作", "提示");
			return;
		}
		
		asyncbox.confirm("确认要删除选中记录吗?", "确认", function(action) {
			if ('ok' == action) {
				selectes.parent().parent().remove();
				if ($("#parentBox")[0].checked) {
					$("#parentBox")[0].checked = false;
				}
				var ids = new Array();
				selectes.each(function(i){
					ids.push(selectes[i].id);
				})
				var viewIds = ids.join(",");
				
				$.post("../role/deleteRoleView.html",{"roleId":$("#roleId").val(),"viewIds":viewIds},function(data){
					if(data.status == '1'){
						asyncbox.success("保存成功！","执行结果",function(){
							var url_ = "../role/setRoleView.html?roleId="+$("#roleId").val()+"&flag="+flag;
							location.href=url_;
						});
					}else{
						asyncbox.error("保存失败！","执行结果",function(){
							var url_ = "../role/setRoleView.html?roleId="+$("#roleId").val()+"&flag="+flag;
							location.href=url_;
						});
					}
				});
			}
		});
	});

	
	$("#returnBtn").click(function() {
		var flag = $("#flag").val();
		if(flag=='returnRole'){
			var url_ = "../role/roleList.html";
			location.href = url_;
		}else{
			var userAccount = flag.split(",")[1];
			var url_ = "../user/selectUsersRolesList.html?userAccount="+userAccount;
			location.href = url_;
		}
	});
});

function viewShow(viewId, viewType, viewName, dictName, dictId, addFlg,
		deleteFlg, approveFlg, releaseFlg) {
	asyncbox.open({
		id : "viewList",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : "../view/viewshow.html?operation=show&viewId="+viewId,
		width : 600,
		height : 420,
		align:'left'
	})
};

