var SuccessMsg = "保存成功";
var FailMsg = "保存失败";
var noRepeat = "描述列数据不能重复";
var descMain, item_selMain, compareMain, valueMain, flag, isCon;
var specialItem = "";
var content = "";

var comArr = new Array();
var itemArr = new Array();
var conArr = new Array();
var valArr = new Array();

function addCondition(t) {
	if ($("#modelCode").val() == "") {
		asyncbox.error("模型事实不能为空");
		return false;
	}
	var title ;
	var url;
	var tid;
	if (t == 1) {
		title = "结果列配置";
		url = "../rule/initResultPage.html?modelId="
				+ $("#modelCode").val() + "&date=" + new Date();
		tid = "setCondition";
	}else{
		title = "条件列配置";
		url = "../rule/initConditionPage.html?modelId="
				+ $("#modelCode").val() + "&date=" + new Date();
		tid = "setCondition";
	}
	asyncbox
			.open({
				id : tid,
				modal : true,
				drag : false,
				title : title,
				url : url,
				width : 600,
				height : 320,
				btnsbar : $.btn.OKCANCEL,
				callback : function(action,opener) {
					if (action == 'ok') {
						if (opener.document.getElementById("t").value == 1) {
							var s = opener.document.getElementById("desc").value;
							if(!(/^[\u4E00-\u9FFF a-z A-Z ()（）\-+_ 0-9]*$/.test(s))){
								asyncbox.error("描述输入内容包含非法字符!");
								return false;
							}
							if (s.replace(/(^\s*)|(\s*$)/g, "") == "") {
								asyncbox.error("描述不能为空");
								return false;
							}
							var temp="";
							var td_titles = jQuery("#column_title").find("th:gt(2)");
							for ( var i = 0; i < td_titles.length; i++) {
								temp += "/"+td_titles[i].innerText;
								if(i==(td_titles.length-1)){
									temp +="/";
								}
							}
							if(temp.indexOf("/"+s+"/")>=0){
								asyncbox.error("描述的输入内容重复!");
								return false;
							}
								
							
						} else {
							var s = opener.document.getElementById("desc2").value;
							if(!(/^[\u4E00-\u9FFF a-z A-Z ()（）\-+_ 0-9]*$/.test(s))){
								asyncbox.error("描述输入内容包含非法字符!");
								return false;
							}
							if (s.replace(
									/(^\s*)|(\s*$)/g, "") == "") {
								asyncbox.error("描述不能为空");
								return false;
							}
							if (opener.document
									.getElementById("context").value.length > 300) {
								asyncbox.error("输入内容过长");
								return false;
							}
							
						}
						opener.submitOk();
					}
				}
			});
	$("#flag").val(0);
}

function addRuleCondition() {
	var txt = "<div id='div_"+descMain+"'><a href='#'  class='Linkblue' onclick=editCondition('"
			+ descMain
			+ "');>&nbsp;&nbsp;&nbsp;&nbsp;编辑</a>&nbsp;&nbsp;<a href='#' class='Linkblue' onclick=removeCondition('"
			+ descMain + "');>删除</a>&nbsp;&nbsp;" + descMain + "</div>";
	jQuery("#td_condition").append(txt);
	addColumnTitle(1);
}

function addOperateResult() {
	var txt = "<div id='div_"+descMain+"' name='result'><a href='#' class='Linkblue' onclick=editCondition('"
			+ descMain
			+ "');>&nbsp;&nbsp;&nbsp;&nbsp;编辑</a>&nbsp;&nbsp;<a href='#' class='Linkblue' onclick=removeCondition('"
			+ descMain + "');>删除</a>&nbsp;&nbsp;" + descMain + "</div>";
	jQuery("#td_operate").append(txt);
	addColumnTitle(1);
}

function removeCondition(oId) {
	var num = 3;
	var tds = jQuery("#column_title").children();
	for ( var i = 3; i < tds.length; i++) {
		if (tds[i].id == oId) {
			num = i;
			break;
		}
	}

	jQuery("#div_" + oId).remove();
	/*var c = $("#"+obj.id);
	if(c.length>0){
		for(var i=0;i<c.length;i++){
			if(c[i].nodeName=="DIV"){
				$($("#"+obj.id)[i]).remove();
				break;
			}
		}
	}else{
		$("#"+obj.id).remove();
	}*/
	jQuery("#column_title th:nth-child(" + (num + 1) + ")").remove();
	jQuery("#column_exe td:nth-child(" + (num - 3 + 1) + ")").remove();
	var r = $(".tbody").children();
	for ( var i = 0; i < r.length; i++) {
		r[i].deleteCell(num);
	}
}

var n = 3;
function editCondition(v) {
	if ($("#modelCode").val() == "") {
		$("#modelCodeCheck").show();
		return;
	}
	var tds = jQuery("#" + v).parent().children();
	for ( var i = 3; i < tds.length; i++) {
		if (tds[i].id == v) {
			n = i;
			break;
		}
	}
	//jQuery("#column_title").find("td:eq(3)")[0].textContent;
	//var exe = jQuery("#column_exe").find("td:eq("+(n-3)+")")[0].childNodes[0].data;
	var exe = jQuery("#column_exe td:eq(" + (n - 3) + ")")[0].innerText;
	var txt = "";
	var inp = jQuery("#column_exe td:eq(" + (n - 3) + ")").find(
			"input[type=hidden]");
	for ( var j = 0; j < inp.length; j++) {
		if (j == 0) {
			txt = inp[j].value;
		} else {
			txt = txt + "," + inp[j].value;
		}
	}
	;

	var title = "条件列配置";
	var url = "../rule/initConditionPage.html"
	var tid = "setCondition";
	if (jQuery("#div_" + v).attr("name") == 'result') {
		url = "../rule/initResultPage.html"
		tid = "setCondition";
	}
	asyncbox
			.open({
				id : tid,
				modal : true,
				//drag:false,
				title : title,
				url : url,
				data : {'modelId':$("#modelCode").val(),'isCon':encodeURI(jQuery("#" + v).attr("class")),'desc':encodeURI(v),'exe':encodeURI(exe + ":" + txt)},
				width : 700,
				height : 320,
				btnsbar : $.btn.OKCANCEL,
				callback : function(action,opener) {
					if (action == 'ok') {
						if (opener.document.getElementById("t").value == 1) {
							var s = opener.document.getElementById("desc").value;
							if(!(/^[\u4E00-\u9FFF a-z A-Z ()（）\-+_ 0-9]*$/.test(s))){
								asyncbox.error("描述输入内容包含非法字符!");
								return false;
							}
							if (s.replace(/(^\s*)|(\s*$)/g, "") == "") {
								asyncbox.error("描述不能为空");
								return false;
							}
						} else {
							var s = opener.document.getElementById("desc2").value;
							if(!(/^[\u4E00-\u9FFF a-z A-Z ()（）\-+_ 0-9]*$/.test(s))){
								asyncbox.error("描述输入内容包含非法字符!");
								return false;
							}
							if (s.replace(
									/(^\s*)|(\s*$)/g, "") == "") {
								asyncbox.error("描述不能为空");
								return false;
							}
							if (opener.document
									.getElementById("context").value.length > 300) {
								asyncbox.error("输入内容过长");
								return false;
							}
							
						}
						opener.submitOk();
					}
				}
			});
}
function editConditionExe(v) {
	var exe = "";
	if (v == 2) {
		exe = content;
	} else {
		exe = item_selMain + compareMain + '{0}';
		if (specialItem.indexOf(item_selMain) != -1) {
			exe = item_selMain + compareMain + '\"{0}\"';
		}
		exe = exe
				+ '<input name="val" type="hidden" value="'+valueMain+'" />';
		for ( var i = 0; i < comArr.length; i++) {
			var temp = '{' + (i + 1) + '}';
			if (specialItem.indexOf(itemArr[i]) != -1) {
				temp = '\"{' + (i + 1) + '}\"';
			}
			exe = exe + comArr[i] + itemArr[i] + conArr[i] + temp;
			exe = exe
					+ '<input  name="val" type="hidden" value="'+valArr[i]+'" />';
		}
	}
	jQuery("#column_exe td:eq(" + (n - 3) + ")")[0].innerHTML = exe;
}

//添加字段表达式
function addColumnTitle(v) {
	var td_column = '<th style="width:20px;height:10px;word-break:keep-all;white-space:nowrap;" id="'+descMain+'" name="'+isCon+'" class="'+flag+'">'
			+ descMain + '</th>';
	jQuery("#column_title").append(td_column);
	var exe = "";
	var d = "";
	if (flag == 2) {
		exe = content;
	} else {
		exe = item_selMain + compareMain + '{0}';
		if (specialItem.indexOf(item_selMain) != -1) {
			exe = item_selMain + compareMain + '\"{0}\"';
		}
		exe = exe
				+ '<input name="val" type="hidden" value="'+valueMain+'" />';
		//var d = "\""+valueMain+"\"";
		d = valueMain;
		for ( var i = 0; i < comArr.length; i++) {
			var temp = '{' + (i + 1) + '}';
			if (specialItem.indexOf(itemArr[i]) != -1) {
				temp = '\"{' + (i + 1) + '}\"';
			}
			exe = exe + comArr[i] + itemArr[i] + conArr[i] + temp;
			exe = exe
					+ '<input  name="val" type="hidden" value="'+valArr[i]+'" />';
			//d += ","+"\""+valArr[i]+"\"";
			d += "," + valArr[i];
		}
	}

	jQuery("#column_exe").append("<td>" + exe + "</td>");

	var r = $(".tbody").children();
	for ( var i = 0; i < r.length; i++) {
		//var cell = document.createElement("TD");
		//r[i].appendChild(cell);
		jQuery(r[i]).append(
				"<td ondblclick='javascript:editTD(this)'>" + d + "</td>");
	}

}

function addRow() {
	var cols = jQuery("#mainTable tr:first-child")[0].cells.length;
	var tr = "<tr>";
	var td = "";
	var r = jQuery(".tbody").children().length;
	if (r > 0) {
		r = jQuery(".tbody tr:last-child").find("td:nth-child(2)")[0].innerHTML;
	}
	for ( var i = 0; i < cols; i++) {
		if (i == 0) {
			td = td
					+ '<td align="center"><a href="javascript:void(0);" class="Linkblue" onclick="addRow();">添加</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="Linkblue" onclick="deleteRow(this);">删除</a></td>';
		} else if (i == 1) {
			td = td + "<td align='center'>" + (parseInt(r, 10) + 1)
					+ "</td>";
		} else if (i > 2) {
			var txt = "";
			var inp = jQuery("#column_exe td:eq(" + (i - 3) + ")").find(
					"input[type=hidden]");
			for ( var j = 0; j < inp.length; j++) {
				if (j == 0) {
					//txt = "\""+inp[j].value+"\"";
					txt = inp[j].value;
				} else {
					//txt = txt +","+"\""+inp[j].value+"\"";
					txt = txt + "," + inp[j].value;
				}
			}
			td = td + "<td ondblclick='javascript:editTD(this)'>" + txt
					+ "</td>";
		} else {
			td = td + "<td name='rowMemoData' ondblclick='javascript:editTD(this)'></td>";
		}
	}
	tr = tr + td + "</tr>"
	jQuery(".tbody").append(tr);
}
//删除行
function deleteRow(obj){
	var tr = $(obj).parent().parent();
	$(obj).parent().parent().remove();
	var rowNum = tr.children()[1].innerText;
	var rows = jQuery(".tbody").children();
	//alert(rowNum);
	//alert(rows[rowNum-1].children[1].innerText);
	for(var i=rowNum-1;i<rows.length;i++){
		rows[i].children[1].innerText=i+1;
	}
}


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
		var inputobj = (jQuery("<input type='text' onblur='focusout(this);' maxLength='50' style='margin:0;width:100%;height:100%'; />")).appendTo(obj);
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
	function saveRule() {
		var flag = true;
		if ($("#ruleName").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
			asyncbox.error("规则名称不能为空");
			return false;
		}else if ($("#groupCode").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
			asyncbox.error("请选择所属的组");
			return false;
		}else if ($("#deptName").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
			asyncbox.error("请选择所属科室");
			return false;
		}else if ($("#modelCode").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
			asyncbox.error("请选择模型事实");
			return false;
		}else if(!flag){
			return false;
		}else if (jQuery("#column_exe").find("td").length < 1) {
			asyncbox.error("请添加条件或操作");
			return false;
		}else if (jQuery(".tbody").find("tr").length < 1) {
			asyncbox.error("请添加数据");
			return false;
		}
		//校验描述列数据是否为空，合法
	 	var array=$("td[name='rowMemoData']");
		for(i=0;i<array.length;i++){ 
			if (array[i].outerText.replace(/(^\s*)|(\s*$)/g,"") == "") {
				asyncbox.error("描述列数据不能为空");
				return false;
			}
			
		}
		//校验描述列数据唯一
		var temp;
		var copy;
		for(i = 0; i < array.length; i++){
			if(temp == null ){
				temp = array[i].outerText;
			}else {
				var tempArr = temp.split(",");
				for(var j=0;j<tempArr.length;j++){
					if(array[i].outerText.replace(/(^\s*)|(\s*$)/g, "")== tempArr[j].replace(/(^\s*)|(\s*$)/g, "")){
						if(copy == null){
							var con = i+1;
							var num = j+1;
							copy = "第"+num+","+con+"行";
						}else{
							var con = i+1;
							var num = j+1;
							copy =copy +"and"+num+","+con+"行";
						}
					}
			   }
				temp = temp +","+array[i].outerText;
		  }
		}
		if(copy !=null){
			allRight = false;
			asyncbox.error(copy+noRepeat);
			return;
		}
		var check_url="../rule/checkExitRul.html";
		var ruleName=$("#ruleName").val();
		$.getJSON(check_url, {ruleName:ruleName}, function(data) {
			if(data.resultSet==1){
				asyncbox.error("规则名称重复");
				return false;
			}else{
				document.getElementById("saveBtn").disabled = true;
				var num = 2;
				var titles = "";
				var exe = "";
				var data = "";
				var td_titles = jQuery("#column_title").find("th:gt(2)");
				for ( var i = 0; i < td_titles.length; i++) {
					if (i == 0) {
						titles = td_titles[i].innerHTML + ":"
								+ td_titles[i].attributes.getNamedItem("class").value
								+ ":"
								+ td_titles[i].attributes.getNamedItem("name").value;
					} else {
						titles = titles + "#" + td_titles[i].innerHTML + ":"
								+ td_titles[i].attributes.getNamedItem("class").value
								+ ":"
								+ td_titles[i].attributes.getNamedItem("name").value;
					}
				}
				var td_exe = jQuery("#column_exe").find("td");
				for ( var i = 0; i < td_exe.length; i++) {
					var nodes = td_exe[i].childNodes;
					var defaultValue = "";
					var f = 0;
					for ( var j = 0; j < nodes.length; j++) {
						if (nodes[j].nodeName == 'INPUT') {
							if (f == 0)
								defaultValue = nodes[j].value;
							else
								defaultValue = defaultValue + "," + nodes[j].value;
							f++;
						}
					}
					if (i == 0) {
						exe = td_exe[i].innerText + ":" + defaultValue;
					} else {
						exe = exe + "#" + td_exe[i].innerText + ":" + defaultValue;
					}
				}
				var tdLength = jQuery(".tbody tr")[0].children.length;
				var rows = jQuery(".tbody").find("tr");
				for ( var j = 2; j < tdLength; j++) {
					for ( var i = 0; i < rows.length; i++) {
						if (j == 2 && i == 0) {
							data = rows[i].children[j].innerText;
						} else if (data != "" && i == 0) {
							data += rows[i].children[j].innerText;
						} else {
							data = data + ":" + rows[i].children[j].innerText;
						}
					}
					if (j != tdLength - 1) {
						data += "#"
					}
				}
				jQuery("#titles").val(titles);
				jQuery("#exe").val(exe);
				jQuery("#data").val(data);
				//jQuery("#submitBtn").click();
				
				var ruleId = "${rule.ruleId}";
				var ruleName =jQuery("#ruleName").val();
				var rulegroupId = jQuery("#groupCode").val();
				var unitId = jQuery("#deptCode").val();
				if(unitId==null){
					return;
				}
				var modelId= jQuery("#modelCode").val();
				var memo = jQuery("#memo").val();
//				var url_ ="../rule/createRule.html?titles=" + encodeURI(encodeURI(titles))
//						+ "&exe=" + encodeURI(encodeURI(exe)) + "&data=" + encodeURI(encodeURI(data))
//						+ "&ruleName=" + encodeURI(encodeURI(ruleName))+ "&rulegroupId=" + rulegroupId
//						+ "&unitId=" + unitId+ "&modelId=" + modelId+ "&memo=" + encodeURI(encodeURI(memo));
				$.getJSON("../rule/createRule.html", {'titles':encodeURI(titles),'exe':encodeURI(exe),
					'data':encodeURI(data),'ruleName':encodeURI(ruleName),
					'rulegroupId':rulegroupId,'unitId':unitId,
					'modelId':modelId,'memo':encodeURI(memo)}, function(data) {
					if (data.resultSet == 1) {
						//alert(SuccessMsg);
						returnRule();
						asyncbox.success(SuccessMsg);
					}else{
						//alert(FailMsg);
						asyncbox.error(FailMsg);
						document.getElementById("saveBtn").disabled = false;
					}
				});
			}
		});
	}
	function returnRule(){
		var url_ = "../rule/ruleList.html";
		location.href=url_;
	}
	function returnList() {
		var form = document.ruleForm;
		form.action = "../rule/ruleList.html";
		form.submit();
	}
	function checkValue(id, value) {
		var illegal = false;
		$.ajaxSetup({async:false});
		if(id=="deptName"&&value!=""){
			 var url_ = "../rule/checkDeptCode.html?deptName=" + encodeURI(encodeURI($("#deptName").val()));
			 $.getJSON(url_,"",function(data) {
				if (data.resultSet != "") {
					$("#deptCode").val(data.resultSet);
				}else{
					alert("输入的科室不存在，请重新输入");
					illegal = true;
				}
			}); 
		 }
		
		if(illegal){
			$("#" + id).val("");
		}
	}
	//autoComplete
	$(function() {
		var tempJsonStr = $( "#dictDepartmentJson" ).val();
		var dictDepartmentList = eval('(' + tempJsonStr + ')');
		var dictDepartmentArr = new Array(dictDepartmentList.length);
		
		for(var i=0;i<dictDepartmentList.length;i++){
			dictDepartmentArr[i] = {"value":dictDepartmentList[i].name,"data":dictDepartmentList[i].code};
		}
		$( "#deptName" ).autocomplete({
			source: dictDepartmentArr,
		      select: function(event, ui) {
		          $( "#deptName" ).val( ui.item.value );
		          $( "#deptCode" ).val( ui.item.data );
		      }
		});
		
		$("#dialog-condition" ).dialog({
			autoOpen: false,
			height: 520,
			width: 690,
			resizable: false,
			modal: true,
			draggable: false
		});
		$("#dialog-operate" ).dialog({
			autoOpen: false,
			height: 520,
			width: 690,
			resizable: false,
			modal: true,
			draggable: false
		});
  	});

	function deleteRule(ruleId){
		asyncbox.confirm('确定要删除该条数据吗？','规则一览',function(action){
			if(action == 'ok'){
				var url_ ="../rule/deleteRule.html";
				jQuery.post(url_,{"ruleId":ruleId},function(e){
					if(e.status=='1'){
						asyncbox.success('刪除成功！','规则一览',function(){
							var url_ = "../rule/ruleList.html";
							location.href=url_;
						});						
					}else{
						asyncbox.error('刪除失败！','规则一览');
					}
				});
			}
		});
	}