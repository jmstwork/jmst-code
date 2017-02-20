jQuery(function($) {
	
	$("#seachBtn").click(function() {
		var url = "../view/viewlist.html";
		$("#viewForm").attr("action", url);
		$("#viewForm").submit();
	});

	$("#cleanBtn").click(function() {
		$("#viewName").val('');
		$("#dictName").val('');
		$("#viewType").val('');
		var url = "../view/viewlist.html";
		$("#viewForm").attr("action", url);
		$("#viewForm").submit();
	});

	$("#addBtn").click(function() {
		asyncbox.open({
			id : "viewList",
			modal : true,
			drag : true,
			scrolling :"no",
			title : "",
			url : "../view/viewshow.html?operation=add",
			width : 600,
			height : 460,
			btnsbar: [{
			     text    : '保存', 
			     action  : 'save'
			},{
				 text    : '关闭', 
			     action  : 'close_1'
			}],
			callback : function(action,opener){
				if(action == 'save'){
					var isWrong = false;
					var viewName = opener.$("#viewName01").val().replace(/(^\s*)|(\s*$)/g,"");
					if(viewName==""||viewName==null){
						opener.$("#viewNameDesc").css("color","red").text("视图名称不能为空！");
						isWrong = true;
					}else{
						if(viewName.length>20){
							opener.$("#viewNameDesc").css("color","red").text("视图名称长度过长！");
							isWrong = true;
						}else{
							opener.$("#viewNameDesc").text("");
						}
					}
					
					var viewType = opener.$("#viewType01").val();
					if(viewType==""||viewType==null){
						opener.$("#viewTypeDesc").css("color","red").text("视图类型不能为空！");
						isWrong = true;
					}else{
						opener.$("#viewTypeDesc").text("");
					}
					var dictId = opener.$("#dictId01").val();
					if(dictId==""||dictId==null){
						opener.$("#dictIdDesc").css("color","red").text("术语名称不能为空！");
						isWrong = true;
					}else{
						opener.$("#viewTypeDesc").text("");
					}
					
					if(isWrong){
						return false;
					}
					
					var operation = opener.$("#operation").val();
					var viewId = opener.$("#viewId_").val();
					
					var addFlg = "";
					var deleteFlg = "";
					var approveFlg = "";
					var releaseFlg = "";
					if(viewType == 1){
						addFlg = opener.$("#termOperation_addFlg").is(":checked")?'1':'0';
						deleteFlg = opener.$("#termOperation_deleteFlg").is(":checked")?'1':'0';
						approveFlg = opener.$("#termOperation_approveFlg").is(":checked")?'1':'0';
						releaseFlg = opener.$("#termOperation_releaseFlg").is(":checked")?'1':'0';
					}
					
					var leg = opener.$("#fieldDataTable tr").length - 1;
					var fieldTableArr = [];
					if(leg>1){
						if(viewType == 0){//检索类型（名字改为查看类型，原来的查看类型不再用）
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
								var fieldNme = $(this).children("td").eq(1).text();
								var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
								var fieldId = $(this).children("td").eq(3)[0].id;
								var viewFieldId = $(this).children("td").eq(0)[0].id;
								var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
								var retrievalFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
//								var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
								var fieldOrder = $(this).children("td").eq(4).children(0)[2].value;
				
								var fieldObject = {
									"viewFieldId":viewFieldId,
									"fieldId":fieldId,
									"fieldName":fieldNme,
									"itemFlg":itemFlg,
									"itemOrder":itemOrder,
									"retrievalFlg":retrievalFlg,
									"orderFlg":'',
									"fieldOrder":fieldOrder
								}
								fieldTableArr.push(fieldObject);
							});					
						}else if(viewType == 1){//编辑类型
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
								var fieldNme = $(this).children("td").eq(1).text();
								var status  =  $(this).children("td").eq(3).find("#viewType01").val();
								if(status == '0'){
							       var editFlg = '1';
							       var retrievalFlg = '1';
							       var itemFlg = '1';
								}else if(status =='1'){
							       var editFlg = '0';
							       var retrievalFlg = '1';
							       var itemFlg = '1';
								}else if(status =='2'){
								   var editFlg = '0';
								   var retrievalFlg = '0';
								   var itemFlg = '1';
								}else if(status =='3'){
							       var editFlg = '0';
							       var retrievalFlg = '0';
							       var itemFlg = '0';
								}
								var fieldId = $(this).children("td").eq(4)[0].id;//jquery对象转成dom对象，可直接使用.id
								var viewFieldId = $(this).children("td").eq(0)[0].id;
								var itemOrder = $(this).children("td").eq(4).find("input[id^='itemOrder']").val();
								var fieldOrder = $(this).children("td").eq(5).find("input[id^='fieldOrder']").val();
								var fieldObject = {
									"viewFieldId":viewFieldId,
									"fieldId":fieldId,
									"fieldName":fieldNme,
									"itemFlg":itemFlg,
									"itemOrder":itemOrder,
									"retrievalFlg":retrievalFlg,
									"orderFlg":'',
									"fieldOrder":fieldOrder,
									"editFlg":editFlg
								}
								fieldTableArr.push(fieldObject);
							});						
						}else if(viewType == 2){//查看类型
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
									var fieldNme = $(this).children("td").eq(1).text();
									var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
									var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
									var fieldId = $(this).children("td").eq(3)[0].id;
									var viewFieldId = $(this).children("td").eq(0)[0].id;
									var fieldObject = {
										"viewFieldId":viewFieldId,
										"fieldId":fieldId,
										"fieldName":fieldNme,
										"itemFlg":itemFlg,
										"itemOrder":itemOrder,
										"retrievalFlg":'',
										"orderFlg":'',
										"fieldOrder":'',
										"editFlg":''
									}
									fieldTableArr.push(fieldObject);
								});						
						
						}
				
					}
					for(var i=0;i<fieldTableArr.length;i++){ 
						fieldTableArr[i]=JSON.stringify(fieldTableArr[i]); 
					}
					var url  = "../view/saveView.html";
					$.post(url,{"fieldTableArr":"["+fieldTableArr+"]","operation":operation,"viewId":viewId,"viewName":viewName,"viewType":viewType,"dictId":dictId,"addFlg":addFlg,"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg},function(data){
						if(data.result == '1'){
							asyncbox.success('保存成功！', '执行结果',function(){
								var url  = "../view/viewlist.html";
								location.href=url;
							});
						}else{
							asyncbox.error('保存失败！', '执行结果');
						}
					});
				}
			}
		})
		
	});

});

function editView(viewId) {
		asyncbox.open({
			id : "viewEdit",
			modal : true,
			drag : true,
			scrolling :"no",
			title : "",
			url : "../view/viewshow.html?operation=edit&viewId="+viewId,
			width : 600,
			height : 460,
			btnsbar: [{
			     text    : '保存', 
			     action  : 'save'
			},{
				 text    : '关闭', 
			     action  : 'close_1'
			}],
			callback : function(action,opener){
				if(action == 'save'){

					var isWrong = false;
					var viewName = opener.$("#viewName01").val().replace(/(^\s*)|(\s*$)/g,"");
					if(viewName==""||viewName==null){
						opener.$("#viewNameDesc").css("color","red").text("视图名称不能为空！");
						isWrong = true;
					}else{
						if(viewName.length>20){
							opener.$("#viewNameDesc").css("color","red").text("视图名称长度过长！");
							isWrong = true;
						}else{
							opener.$("#viewNameDesc").text("");
						}
					}
					
					var viewType = opener.$("#viewType01").val();
					if(viewType==""||viewType==null){
						opener.$("#viewTypeDesc").css("color","red").text("视图类型不能为空！");
						isWrong = true;
					}else{
						opener.$("#viewTypeDesc").text("");
					}
					var dictId = opener.$("#dictId01").val();
					if(dictId==""||dictId==null){
						opener.$("#dictIdDesc").css("color","red").text("术语名称不能为空！");
						isWrong = true;
					}else{
						opener.$("#viewTypeDesc").text("");
					}
					
					if(isWrong){
						return false;
					}
					
					var operation = opener.$("#operation").val();
					var viewId = opener.$("#viewId_").val();
					
					var addFlg = "";
					var deleteFlg = "";
					var approveFlg = "";
					var releaseFlg = "";
					if(viewType == 1){
						addFlg = opener.$("#termOperation_addFlg").is(":checked")?'1':'0';
						deleteFlg = opener.$("#termOperation_deleteFlg").is(":checked")?'1':'0';
						approveFlg = opener.$("#termOperation_approveFlg").is(":checked")?'1':'0';
						releaseFlg = opener.$("#termOperation_releaseFlg").is(":checked")?'1':'0';
					}
					
					var leg = opener.$("#fieldDataTable tr").length - 1;
					var fieldTableArr = [];
					if(leg>1){
						if(viewType == 0){//检索类型--改为查看类型，查看类型的视图去掉
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
								var fieldNme = $(this).children("td").eq(1).text();
								var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
								var fieldId = $(this).children("td").eq(3)[0].id;
								var viewFieldId = $(this).children("td").eq(0)[0].id;
								var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
								var retrievalFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
//								var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
								var fieldOrder = $(this).children("td").eq(4).children(0)[2].value;
				
								var fieldObject = {
									"viewFieldId":viewFieldId,
									"fieldId":fieldId,
									"fieldName":fieldNme,
									"itemFlg":itemFlg,
									"itemOrder":itemOrder,
									"retrievalFlg":retrievalFlg,
									"orderFlg":'',
									"fieldOrder":fieldOrder
								}
								fieldTableArr.push(fieldObject);
							});					
						}else if(viewType == 1){//编辑类型
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
								var fieldNme = $(this).children("td").eq(1).text();
								var status  =  $(this).children("td").eq(3).find("#viewType01").val();
								if(status == '0'){
							       var editFlg = '1';
							       var retrievalFlg = '1';
							       var itemFlg = '1';
								}else if(status =='1'){
							       var editFlg = '0';
							       var retrievalFlg = '1';
							       var itemFlg = '1';
								}else if(status =='2'){
								   var editFlg = '0';
								   var retrievalFlg = '0';
								   var itemFlg = '1';
								}else if(status =='3'){
							       var editFlg = '0';
							       var retrievalFlg = '0';
							       var itemFlg = '0';
								}
								var fieldId = $(this).children("td").eq(4)[0].id;
								var viewFieldId = $(this).children("td").eq(0)[0].id;
								var itemOrder = $(this).children("td").eq(4).find("input[id^='itemOrder']").val();
								var fieldOrder = $(this).children("td").eq(5).find("input[id^='fieldOrder']").val();
								var fieldObject = {
									"viewFieldId":viewFieldId,
									"fieldId":fieldId,
									"fieldName":fieldNme,
									"itemFlg":itemFlg,
									"itemOrder":itemOrder,
									"retrievalFlg":retrievalFlg,
									"orderFlg":'',
									"fieldOrder":fieldOrder,
									"editFlg":editFlg
								}
								fieldTableArr.push(fieldObject);
							});						
						}else if(viewType == 2){//查看类型
							opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
									var fieldNme = $(this).children("td").eq(1).text();
									var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
									var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
									var fieldId = $(this).children("td").eq(3)[0].id;
									var viewFieldId = $(this).children("td").eq(0)[0].id;
									var fieldObject = {
										"viewFieldId":viewFieldId,
										"fieldId":fieldId,
										"fieldName":fieldNme,
										"itemFlg":itemFlg,
										"itemOrder":itemOrder,
										"retrievalFlg":'',
										"orderFlg":'',
										"fieldOrder":'',
										"editFlg":''
									}
									fieldTableArr.push(fieldObject);
								});						
						
						}
				
					}
					for(var i=0;i<fieldTableArr.length;i++){ 
						fieldTableArr[i]=JSON.stringify(fieldTableArr[i]); 
					}
					var url  = "../view/saveView.html";
					$.post(url,{"fieldTableArr":"["+fieldTableArr+"]","operation":operation,"viewId":viewId,"viewName":viewName,"viewType":viewType,"dictId":dictId,"addFlg":addFlg,"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg},function(data){
						if(data.result == '1'){
							asyncbox.success('保存成功！', '执行结果',function(){
								var url  = "../view/viewlist.html";
								location.href=url;
							});
						}else{
							asyncbox.error('保存失败！', '执行结果');
						}
					});
				}
			}
		
		})
};


function deleteView(viewId,obj) {
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
	asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
		 if(action == "ok"){
			var url = "../view/deleteViews.html";
			$.post(url, {
				"viewId" : viewId
			}, function(data) {
				if (data.status == '1') {
					asyncbox.success('删除成功', '执行结果', function() {
						var url_ = "../view/viewlist.html";
						location.href = url_;
					});
				} else {
					asyncbox.error(data.deleteMsg, '执行结果');
				}
			});
		 }else{
			 trObj.css("background","");
		 }	 
	})
};


function viewShow(viewId, viewType, viewName, dictName, dictId, addFlg,
		deleteFlg, approveFlg, releaseFlg) {
	asyncbox.open({
		id : "viewEdit",
		modal : true,
		drag : true,
		scrolling :"no",
		title : "",
		url : "./viewshow.html?operation=show&viewId="+viewId,
		width : 600,
		height : 440
	})
};

function doSearch() {
	var form = document.viewForm;
	form.action = "./viewList.html";
	form.submit();
}

function operateView(opener){
		var viewName = opener.$("#viewName01").val().replace(/(^\s*)|(\s*$)/g,"");
		var isOK = true;
		if(viewName==""||viewName==null){
			opener.$("#viewNameDesc").css("color","red").text("视图名称不能为空！");
			isOK = false;
		}else{
			opener.$("#viewNameDesc").text("");
		}
		alert(223);
		return;
		if(viewName.length>20){
			opener.$("#viewNameDesc").css("color","red").text("视图名名长度过长！");
			isOK = false;
		}else{
			opener.$("#viewNameDesc").text("");
		}
		
		var viewType = opener.$("#viewType01").val();
		if(viewType==""||viewType==null){
			opener.$("#viewTypeDesc").css("color","red").text("视图类型不能为空！");
			isOK = false;
		}else{
			opener.$("#viewTypeDesc").text("");
		}
		var dictId = opener.$("#dictId01").val();
		if(dictId==""||dictId==null){
			opener.$("#dictIdDesc").css("color","red").text("术语名称不能为空！");
			isWrong = true;
		}else{
			opener.$("#viewTypeDesc").text("");
		}
		
		if(isOK){
			var operation = opener.$("#operation").val();
			var viewId = opener.$("#viewId_").val();
			
			var addFlg = "";
			var deleteFlg = "";
			var approveFlg = "";
			var releaseFlg = "";
			if(viewType == 1){
				addFlg = opener.$("#termOperation_addFlg").is(":checked")?'1':'0';
				deleteFlg = opener.$("#termOperation_deleteFlg").is(":checked")?'1':'0';
				approveFlg = opener.$("#termOperation_approveFlg").is(":checked")?'1':'0';
				releaseFlg = opener.$("#termOperation_releaseFlg").is(":checked")?'1':'0';
			}
			
			var leg = opener.$("#fieldDataTable tr").length - 1;
			var fieldTableArr = [];
			if(leg>1){
				if(viewType == 0){//检索类型
					opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
						var fieldNme = $(this).children("td").eq(1).text();
						var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
						var fieldId = $(this).children("td").eq(3)[0].id;
						var viewFieldId = $(this).children("td").eq(0)[0].id;
						var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
						var retrievalFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
						var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
						var fieldOrder = $(this).children("td").eq(5).children(0)[2].value;
		
						var fieldObject = {
							"viewFieldId":viewFieldId,
							"fieldId":fieldId,
							"fieldName":fieldNme,
							"itemFlg":itemFlg,
							"itemOrder":itemOrder,
							"retrievalFlg":retrievalFlg,
							"orderFlg":orderFlg,
							"fieldOrder":fieldOrder
						}
						fieldTableArr.push(fieldObject);
					});					
				}else if(viewType == 1){//编辑类型
					opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
						var fieldNme = $(this).children("td").eq(1).text();
						var editFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
						var fieldId = $(this).children("td").eq(4)[0].id;
						var viewFieldId = $(this).children("td").eq(0)[0].id;
						var itemOrder = $(this).children("td").eq(4).children(0)[2].value;
						var itemFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
						var fieldObject = {
							"viewFieldId":viewFieldId,
							"fieldId":fieldId,
							"fieldName":fieldNme,
							"itemFlg":itemFlg,
							"itemOrder":itemOrder,
							"retrievalFlg":'',
							"orderFlg":'',
							"fieldOrder":'',
							"editFlg":editFlg
						}
						fieldTableArr.push(fieldObject);
					});						
				}else if(viewType == 2){//查看类型
					opener.$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
							var fieldNme = $(this).children("td").eq(1).text();
							var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
							var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
							var fieldId = $(this).children("td").eq(3)[0].id;
							var viewFieldId = $(this).children("td").eq(0)[0].id;
							var fieldObject = {
								"viewFieldId":viewFieldId,
								"fieldId":fieldId,
								"fieldName":fieldNme,
								"itemFlg":itemFlg,
								"itemOrder":itemOrder,
								"retrievalFlg":'',
								"orderFlg":'',
								"fieldOrder":'',
								"editFlg":''
							}
							fieldTableArr.push(fieldObject);
						});						
				
				}
		
			}
			for(var i=0;i<fieldTableArr.length;i++){ 
				fieldTableArr[i]=JSON.stringify(fieldTableArr[i]); 
			}
			var url  = "../view/saveView.html";
			$.post(url,{"fieldTableArr":"["+fieldTableArr+"]","operation":operation,"viewId":viewId,"viewName":viewName,"viewType":viewType,"dictId":dictId,"addFlg":addFlg,"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg},function(data){
				if(data.result == '1'){
					asyncbox.success('保存成功！', '执行结果',function(){
						var url  = "../view/viewlist.html";
						location.href=url;
					});
				}else{
					asyncbox.error('保存失败！', '执行结果');
				}
			});
		}else{
			return false;
		}
	}
function deletView(){
	var childrenBox = document.getElementsByName("childrenBox");
	var viewId=null;
	var delsize = 0;
	for(var i=0;i<childrenBox.length;i++){
		if(childrenBox[i].checked){
			if(viewId==null){
				delsize++;
				viewId = childrenBox[i].id;
			}else{
				delsize++;
				viewId = viewId + "," + childrenBox[i].id;
			}
		}
	}
	if(viewId==null){
		asyncbox.error("请选择术语视图","提示");
	}else{
		asyncbox.confirm("确定要删除这"+delsize+"条记录吗？","提示",function(action){
			 if(action == "ok"){
				var url = "../view/deleteViews.html";
				$.post(url, {
					"viewId" : viewId
				}, function(data) {
					if (data.status == '1') {
						asyncbox.success('删除成功', '执行结果', function() {
							var url_ = "../view/viewlist.html";
							location.href = url_;
						});
					} else {
						asyncbox.error(data.deleteMsg, '执行结果');
//						asyncbox.error('删除失败', '执行结果');
					}
				});
			 }
		})
	}
}