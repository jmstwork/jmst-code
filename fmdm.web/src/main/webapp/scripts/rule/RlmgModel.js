var i = 2;
// 添加一个参与人填写行
function AddSignRow() {
	i++;
	// 添加行
	var newTR = SignFrame.insertRow();
	// 添加列:序号
	var newNameTD = newTR.insertCell(0);
	// 添加列内容
	newNameTD.innerHTML = "<div align='right' class='label'><font color='red'>*</font>字段名"
			+ "</div>";

	// 添加列:姓名
	var newNameTD = newTR.insertCell(1);
	// 添加列内容
	newNameTD.innerHTML = "<input type='text' name='fieldName" + "' id='row"
			+ i + "FName" + "' th:field='*{fieldName}'/>";

	// 添加列:电子邮箱
	var newEmailTD = newTR.insertCell(2);
	// 添加列内容
	newEmailTD.innerHTML = "<div align='right' class='label'><font color='red'>*</font>字段英文名  "
			+ "</div>";

	// 添加列:电话
	var newTelTD = newTR.insertCell(3);
	// 添加列内容
	newTelTD.innerHTML = "<input type='text' name='fieldEnName' id='fieldEnName'"
			+ i + " th:field='*{fieldEnName}'/>";

	// 添加列:手机
	var newMobileTD = newTR.insertCell(4);
	// 添加列内容
	newMobileTD.innerHTML = "<div align='right' class='label'><font color='red'>*</font>字段类型  "
			+ "</div>";

	// 添加列:公司名
	var newCompanyTD = newTR.insertCell(5);
	// 添加列内容
	newCompanyTD.innerHTML = "<select style='width: 120px;' name='fieldType' id='fieldType'"
			+ i
			+ " th:field='*{fieldType}'><option value='-1'>请选择</option><option value='Integer'>整型</option><option value='String'>文本</option><option value='Date'>日期</option><option value='Boolean'>布尔</option><option value='Float'>浮点</option></select>";

	// 添加列:删除按钮
	var newDeleteTD = newTR.insertCell(6);
	// 添加列内容
	newDeleteTD.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:AddSignRow();' class='Linkblue'>"
			+ "添加"
			+ "</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='deleteRow(this,2)' class='Linkblue'>"
			+ "删除" + "</a>";
}

function deleteRow(obj, useFlg) {
	if (useFlg == 1) {
		asyncbox.error('该字段已被规则使用，不能被删除！', '编辑模型');
		// alert("该字段已被规则使用，不能被删除");
	} else {
		var index = obj.parentElement.parentElement.rowIndex;
		SignFrame.deleteRow(index);
	}
}
// 窗口表格删除一行
function clearnRow(obj) {
	document.getElementById("fieldName").value = "";
	document.getElementById("fieldEnName").value = "";
	document.getElementById("fieldType").value = "-1";
}
$(function() {
	$("#saveBtn").bind(
			"click",
			function() {
				var modelName = $("input[name='modelName']").val();
				var modelEnName = $("input[name='modelEnName']").val();
				var fieldName = $("input[name='fieldName']");
				var fieldEnName = $("input[name='fieldEnName']");
				var fieldType = $("select[name='fieldType']");
				if (modelName == null || modelName == "") {
					asyncbox.error('模型名不能为空！', '新建模型');
					// alert("模型名不能为空！");
				} else if (modelEnName == null || modelEnName == "") {
					asyncbox.error('模型英文名不能为空！', '新建模型');
					// alert("模型英文名不能为空！");
				} else if (modelName.length > 10) {
					asyncbox.error('模型名超出长度！', '新建模型');
				} else if (modelEnName.length > 20) {
					asyncbox.error('模型英文名超出长度！', '新建模型');
				} else {
					var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
					if(reg.test(modelEnName)){
						asyncbox.error('模型英文名中出现汉字！', '提示');
						return;
					}
					var flag = true;
					var fieldNameStr = "";
					var fieldEnNameStr = "";
					var fieldTypeStr = "";
					for (var i = 0; i < fieldName.length; i++) {
						if (fieldName[i].value == null
								|| fieldName[i].value == "") {
							flag = false;
							asyncbox.error('字段名不能为空！', '新建模型');
							return;
							// alert("字段名不能为空！");
						} else if (fieldEnName[i].value == null
								|| fieldEnName[i].value == "") {
							flag = false;
							asyncbox.error('字段英文名不能为空！', '新建模型');
							return;
							// alert("字段英文名不能为空！");
						} else if (fieldType[i].value == "-1") {
							flag = false;
							asyncbox.error('字段类型不能为空！', '新建模型');
							return;
							// alert("字段类型不能为空！");
						} else if (fieldName[i].value.length > 20) {
							flag = false;
							asyncbox.error('字段名长度超出！', '新建模型');
							return;
						} else if (fieldEnName[i].value.length > 20) {
							flag = false;
							asyncbox.error('字段英文名长度超出！', '新建模型');
							return;
						}
						if("" == fieldNameStr){
							fieldNameStr += fieldName[i].value;
							fieldEnNameStr += fieldEnName[i].value;
							fieldTypeStr += fieldType[i].value;
						}else{
							fieldNameStr += "," + fieldName[i].value;
							fieldEnNameStr += "," + fieldEnName[i].value;
							fieldTypeStr += "," + fieldType[i].value;
						}
					}
					
					if(reg.test(fieldEnNameStr)){
						asyncbox.error('字段英文名中出现汉字！', '提示');
						return;
					}
					
					var s = fieldNameStr + ",";
					var ary = fieldNameStr.split(",");
					for(var i = 0 ; i < ary.length ; i++) {
						if(s.replace(ary[i]+",","").indexOf(ary[i]+",")>-1) {
							//alert("数组中有重复元素：" + ary[i]);
							asyncbox.error('字段名有重复', '提示');
							return;
						}
					}
					
					s = fieldEnNameStr + ",";
					ary = fieldEnNameStr.split(",");
					for(var i = 0 ; i < ary.length ; i++) {
						if(s.replace(ary[i]+",","").indexOf(ary[i]+",")>-1) {
							//alert("数组中有重复元素：" + ary[i]);
							asyncbox.error('字段英文名有重复', '提示');
							return;
						}
					}
					
					if (flag) {
						/*
						 * asyncbox.success('保存成功！', '', function() {
						 * $("form").submit(); var url_ =
						 * "../rmlg/modelView.html"; location.href = url_; });
						 */
						var url = "../rmlg/modelSave.html";
						$.post(url, {
							'modelId' : $('#modelId').val(),
							'modelName' : modelName,
							'modelEnName' : modelEnName,
							'fieldId' : $("input[name='fieldId']").val(),
							'fieldName' : fieldNameStr,
							'fieldEnName' : fieldEnNameStr,
							'fieldType' : fieldTypeStr,
							'opt': $('#opt').val()
						}, function(data) {
							//alert(data.status);
							if (data.status == '1') {
								asyncbox.success('保存成功！', '执行结果', function() {
									var url_ = "../rmlg/modelView.html";
									location.href = url_;
								})
							} else {
								//alert(data.msg);
								if (null == data.msg) {
									asyncbox.error('保存失败！', '执行结果');
								} else {
									asyncbox.error(data.msg, '执行结果');
								}

							}
						})
					}
				}
			});
	$("#cleanBtn").bind("click", function() {
		// document.getElementById("modelName").value= "";
		$("modelName").val("");
		var url_ = "../rmlg/modelView.html";
		location.href = url_;
	});
	$("#returnBtn").click(function() {
		var url_ = "../rmlg/modelView.html";
		location.href = url_;
	});
})
function deleteModle(modelId,obj) {
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
	asyncbox.confirm('确定要删除该条数据吗？', '模型一览', function(action) {
		if (action == 'ok') {
			var url_ = "../rmlg/deleteModel.html";
			jQuery.post(url_, {
				"modelId" : modelId
			}, function(e) {
				if (e.status == '1') {
					asyncbox.success('刪除成功！', '模型一览', function() {
						var url_ = "../rmlg/modelView.html";
						location.href = url_;
					});
				} else {
					asyncbox.error('刪除失败，该条数据已被规则引用！', '模型一览',function(){
						trObj.css("background","");
					});
				}
			});
		}else{
			 trObj.css("background","");
		 }
	});

	// if(confirm("确定要删除该条数据吗？"))
	// {
	// var url_ ="../rmlg/deleteModel.html";
	// jQuery.post(url_,{"modelId":modelId},function(e){
	// if(e.status==1){
	// alert("刪除成功");
	// var url_ = "../rmlg/modelView.html";
	// location.href=url_;
	// }else{
	// alert("刪除失败，该条数据已被规则引用！");
	// }
	// });
	// }
}
function updateModle(obj) {
	//alert(3472984);
	/*var form = document.modelForm;
	form.action = "../rmlg/modelOperateView.html?opt=edit&modelId=" + obj;
	form.submit();*/
	
	var url_ = "../rmlg/modelOperateView.html?opt=edit&modelId=" + obj;
	location.href = url_;

}