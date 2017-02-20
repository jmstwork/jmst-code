var FailMsg = "保存失败";
var SuccessMsg = "保存成功";
var noRepeat = "描述列数据不能重复";
$(function() {
	$("#cleanBtn").click(function() {
		$("#description").val("");
	})

	$("#seachBtn").click(function() {
		var form = document.ruleForm;
		form.action = "../rule/editRuleDataPage.html";
		form.submit();
	});

})

// 添加行
function addRow() {
	var cols = jQuery("#mainTable tr:first-child")[0].cells.length;
	var tr = "<tr>";
	var td = "";
	var r = jQuery(".tbody").children().length;
	if (r > 0) {
		r = jQuery(".tbody tr:last-child").find("td:nth-child(2)")[0].innerHTML;
	}
	for (var i = 0; i < cols; i++) {
		if (i == 0) {
			td = td
					+ '<td align="center"><a href="javascript:void(0);" class="Linkblue" onclick="addRow();">添加</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="Linkblue" onclick="deleteRow(this);">删除</a></td>';
		} else if (i == 1) {
			td = td
					+ "<td align='center'>" + (parseInt(r, 10) + 1) + "</td>";
		} else if (i == 2) {
			td = td
					+ "<td name='rowMemoData' ondblclick='javascript:editTD(this)'></td>";
		} else {
			td = td
					+ "<td ondblclick='javascript:editTD(this)'></td>";
		}
	}
	tr = tr + td + "</tr>"
	jQuery(".tbody").append(tr);
}

// 删除行
function deleteRow(obj) {
	var tr = $(obj).parent().parent();
	$(obj).parent().parent().remove();
	var rowNum = tr.children()[1].innerText;
	var rows = jQuery(".tbody").children();
	// alert(rowNum);
	// alert(rows[rowNum-1].children[1].innerText);
	for (var i = rowNum - 1; i < rows.length; i++) {
		rows[i].children[1].innerText = i + 1;
	}
}
// 返回
function returnRule(opt) {
	if (opt == 'edit') {
		var url_ = "../rule/ruleList.html";
		location.href = url_;
	} else {
		var form = document.ruleForm;
		form.action = "../rule/initCreateGuidePage.html";
		form.submit();
	}
}
// editTD
function editTD(obj) {
	var txt = $(obj.outerHTML).text();
	if (txt != null && txt != "") {
		if (obj.childNodes.length > 0) {
			obj.childNodes[0].data = '';
		} else {
			obj.textContent = "";
		}
	}
	if ($(obj.outerHTML).find("input").length == 1
			&& $(obj.outerHTML).find("input")[0].nodeName == "INPUT") {
		$(obj.outerHTML).find("input")[0].focus();
		return;
	}
	var inputobj = (jQuery("<input type='text' onblur='focusout(this);' maxLength='50' style='margin:0;width:100%;height:100%'; />"))
			.appendTo(obj);
	inputobj.trigger("focus");
	inputobj.val(" " + txt);
	inputobj.focusout(function() {
		if (obj.childNodes[0].nodeName == "#text") {
			obj.childNodes[0].data = inputobj.val();
		} else {
			obj.innerHTML = inputobj.val();
		}
		inputobj.remove();
	});
	jQuery(obj.outerHTML).find("input[value='']").remove();
}

// 保存规则数据
function saveRuleData() {
	var flag = true;
	if (jQuery(".tbody").find("tr").length < 1) {
		asyncbox.error("请添加数据");
		return false;
	}
	// 校验描述列数据是否为空，合法
	var array = $("td[name='rowMemoData']");
	for (i = 0; i < array.length; i++) {
		if (array[i].outerText.replace(/(^\s*)|(\s*$)/g, "") == "") {
			asyncbox.alert("描述列数据不能为空", "提示信息");
			return false;
		}
	}
	// 校验描述列数据唯一
	var temp;
	var copy;
	for (i = 0; i < array.length; i++) {
		if (temp == null) {
			temp = array[i].outerText;
		} else {
			var tempArr = temp.split(",");
			for (var j = 0; j < tempArr.length; j++) {
				if (array[i].outerText.replace(/(^\s*)|(\s*$)/g, "") == tempArr[j]
						.replace(/(^\s*)|(\s*$)/g, "")) {
					if (copy == null) {
						var con = i + 1;
						var num = j + 1;
						copy = "第" + num + "," + con + "行";
					} else {
						var con = i + 1;
						var num = j + 1;
						copy = copy + "and" + num + "," + con + "行";
					}
				}
			}
			temp = temp + "," + array[i].outerText;
		}
	}
	if (copy != null) {
		allRight = false;
		// asyncbox.error(copy);
		asyncbox.error(noRepeat, "提示");
		return;
	}
	var check_url = "../rule/checkExitRul.html";
	var ruleName = $("#ruleName").val();
	$
			.getJSON(
					check_url,
					{
						ruleName : ruleName,
						'ruleId': $("#ruleId").val()
					},
					function(data) {
						if (data.resultSet == 1) {
							asyncbox.error("规则名称重复");
							return false;
						} else {
							document.getElementById("saveBtn").disabled = true;
							var num = 2;
							var titles = "";
							var exe = "";
							var data = "";
							var td_titles = jQuery("#column_title").find(
									"th:gt(2)");
							for (var i = 0; i < td_titles.length; i++) {
								if (i == 0) {
									titles = td_titles[i].innerHTML
											+ ":"
											+ td_titles[i].attributes
													.getNamedItem("class").value
											+ ":"
											+ td_titles[i].attributes
													.getNamedItem("name").value;
								} else {
									titles = titles
											+ "#"
											+ td_titles[i].innerHTML
											+ ":"
											+ td_titles[i].attributes
													.getNamedItem("class").value
											+ ":"
											+ td_titles[i].attributes
													.getNamedItem("name").value;
								}
							}
							var td_exe = jQuery("#column_exe").find("td");
							for (var i = 0; i < td_exe.length; i++) {
								var nodes = td_exe[i].childNodes;
								var defaultValue = "";
								var f = 0;
								for (var j = 0; j < nodes.length; j++) {
									if (nodes[j].nodeName == 'INPUT') {
										if (f == 0)
											defaultValue = nodes[j].value;
										else
											defaultValue = defaultValue + ","
													+ nodes[j].value;
										f++;
									}
								}
								if (i == 0) {
									exe = td_exe[i].innerText + ":"
											+ defaultValue;
								} else {
									exe = exe + "#" + td_exe[i].innerText + ":"
											+ defaultValue;
								}
							}
							var tdLength = jQuery(".tbody tr")[0].children.length;
							var rows = jQuery(".tbody").find("tr");
							for (var j = 2; j < tdLength; j++) {
								for (var i = 0; i < rows.length; i++) {
									if (j == 2 && i == 0) {
										data = rows[i].children[j].innerText;
									} else if (data != "" && i == 0) {
										data += rows[i].children[j].innerText;
									} else {
										data = data + ":"
												+ rows[i].children[j].innerText;
									}
								}
								if (j != tdLength - 1) {
									data += "#"
								}
							}
							jQuery("#titles").val(titles);
							jQuery("#exe").val(exe);
							jQuery("#data").val(data);
							// jQuery("#submitBtn").click();

							var ruleId = jQuery("#ruleId").val();
							var ruleName = jQuery("#ruleName").val();
							var rulegroupId = jQuery("#groupCode").val();
							var unitId = jQuery("#deptCode").val();
							/*
							 * if(unitId==null){ return; }
							 */
							var modelId = jQuery("#modelCode").val();
							var memo = jQuery("#memo").val();
							var opt = jQuery("#opt").val();
							// alert(opt);
							var url_ = "../rule/saveCopyRule.html";
							$.getJSON(
											url_,
											{
												'titles' : encodeURI(titles),
												'exe' : encodeURI(exe),
												'data' : encodeURI(data),
												'ruleName' : encodeURI(ruleName),
												'rulegroupId' : rulegroupId,
												'unitId' : unitId,
												'modelId' : modelId,
												'memo' : encodeURI(memo),
												'ruleId' : encodeURI(ruleId),
												'opt' : opt
											},
											function(data) {
												if (data.resultSet == 1) {
													//alert(SuccessMsg);
													asyncbox
															.success(
																	SuccessMsg,
																	"执行结果",
																	function() {
																		returnRule('edit');
																	});
												} else {
													//alert(FailMsg);
													asyncbox.error(FailMsg,
															"执行结果");
													document
															.getElementById("saveBtn").disabled = false;
												}
											});
						}
					});
}