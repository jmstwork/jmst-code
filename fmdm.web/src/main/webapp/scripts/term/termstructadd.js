//记录删除的字段信息
var dropColumn = "";
var optRow = "";
$(function() {
	        //dict_field高亮显示错误信息			
			function updateFieldTips(t) {
				fieldTips.text(t).addClass("ui-state-highlight");
		    }
			
			$("#mapaddsearch").bind("click", function() {
				var form = document.tsform;
				form.action = "./addMappingCode.html";
				form.submit();
			});
			
			//dict_main高亮显示错误信息
			function updateMainTips(t) {
//				mailnTips.html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + t);
//				mainTips.css("background-color","#fefcda");
//				mainTips.slideDown("slow");
				$(".mainValidateTips").hide();
				$(".mainValidateTips").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + t);
				$(".mainValidateTips").css("background-color","#fefcda","font-size","100%","vertical-align","middle");
				$(".mainValidateTips").slideDown("slow");
				
		    }
			
			//执行sql时，高亮显示结果
			function updateExecuteTips(t) {
				executeTips.text(t).addClass("ui-state-highlight");
		    }
			
			//长度校验
			function checkMixed(o,n,min,max) {
				var l = $.trim(o.val()).length;
				//优先判断是否为空
				if($.trim(o.val())=="" || $.trim(o.val())==null){
					o.addClass("ui-state-error");
					//如果输入了空格，需要去掉空格；
					o.val("");
					updateFieldTips( n + "不能为空!");
					return false;
				}
				
				if(n=="列名" || n=="术语ID"){
					//列名只能以字母开头，包含字母数字和下划线
					var reg= new RegExp(/^[a-zA-Z]+[a-zA-Z0-9_]*$/gi);
					if(!reg.test($.trim(o.val()))){
						o.addClass("ui-state-error");
						updateFieldTips( n + "必须以字母开头,只能包括字母、数字和下划线!");
						return false;
					}
					var wrong = false;
					if($("#dictId").val()!=""&&$("#dictId").val()!=null&&optRow!="editRow"){
						var url_="./selectFieldIsExist.html";
						jQuery.post(url_,
								{
								    "dictId":$("#dictId").val(),
								    "fieldName":o.val(),
								    "dropColumn":dropColumn
								},function(e){
									 if(e.result==1){
										 updateFieldTips(n+"已存在,请重新输入!");
										 o.addClass("ui-state-error");
										 wrong = true;
									 }
						});
						if(wrong){
							 return false;
						}
					}
				}
				
				if(n=="该字段"){  //给长度取名叫该字段，是为了避免出现：长度的长度必须在[1~50]之间
					
					//只能为大于1的正整数判断
					reg= new RegExp(/^\+?[1-9][0-9]*$/gi);
					if(!reg.test($.trim(o.val()))){
						o.addClass("ui-state-error");
						updateFieldTips( n + "只能为大于1的正整数!");
						return false;
					}
					
					//长度判断
		
					if($.trim(fieldType.val())=="NUMBER"){
						//数值型校验长度
						if (parseFloat($.trim(o.val()))>38) {
							o.addClass("ui-state-error");
							updateFieldTips( n + "的取值必须在[1 ~ 38]之间!" );
							return false;
						}
						
						//在选择数值型的情况下校验缺省值
						/*if($.trim(precision.val())=="" || $.trim(precision.val())==null){
							precision.addClass("ui-state-error");
							updateFieldTips("精度不能为空!" );
							return false;
						}else{
							reg= new RegExp(/^\+?[1-9][0-9]*$/gi);
							if(!reg.test($.trim(precision.val()))){
								precision.addClass("ui-state-error");
								updateFieldTips("缺省值只能为大于1的正整数!" );
								return false;
							}*/

						if (!($.trim(precision.val())==1 || $.trim(precision.val())==2 || $.trim(precision.val())==0|| $.trim(precision.val())==3 || $.trim(precision.val())==4)) {
							precision.addClass("ui-state-error");
							updateFieldTips("精度的值只能为0,1,2,3或4!" );
							return false;
						}
						//}
						//在选择数值型的情况下校验缺省值
						if($.trim($("#defaultValue").val())!="" && $.trim($("#defaultValue").val())!=null){
							var result = 10;
							for(var i=0;i<length.val()-1;i++){
								var j = 10;
								result = result*j;
							}
							
							//长度校验
							if($.trim($("#defaultValue").val()).length>10 || $.trim($("#defaultValue").val()).length<1){
								defaultValue.addClass("ui-state-error");
								updateFieldTips("缺省值的长度必须在[1 ~ 10]之间!" );
								return false;
							}

							if($.trim(precision.val())==0 && $.trim(precision.val())!=""){
								reg= new RegExp(/^[0-9]*$/gi);
								if(!reg.test($("#defaultValue").val())){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值只能为整数!" );
									return false;
								}
								
								if(parseFloat($("#defaultValue").val())>(result-1)){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值的大于此列允许的最大精度"+(result-1)+"!" );
									return false;
								}
								
							}else if($.trim(precision.val())==1){
								
								reg= new RegExp(/^[0-9]+(.[0-9]{1})?$/gi);
								if(!reg.test($("#defaultValue").val())){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值只能为一位小数的正实数!" );
									return false;
								}
								
								
								if(parseFloat($("#defaultValue").val())>(result/10-0.1)){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值的大于此列允许的最大精度"+(result/10-0.1)+"!" );
									return false;
								}
								
								
							}else if($.trim(precision.val())==2){
								reg= new RegExp(/^[0-9]+(.[0-9]{2})?$/gi);
								if(!reg.test($("#defaultValue").val())){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值只能为两位小数的正实数!" );
									return false;
								}
							
								if(parseFloat(precision.val())>parseFloat(length.val())){
									precision.addClass("ui-state-error");
									updateFieldTips("精度大于的长度!" );
									return false;
								}
								if(parseFloat($("#defaultValue").val())>(result/100-0.01)){
									defaultValue.addClass("ui-state-error");
									updateFieldTips("缺省值的大于此列允许的最大精度"+(result/100-0.01)+"!" );
									return false;
								}
							}
						}
					}else if($("#fieldType").val()=="VARCHAR2"){
						if (l>2000 || l<1) {
							o.addClass("ui-state-error");
							updateFieldTips( n + "的长度必须在[1 ~ 2000]之间!" );
							return false;
						}
						//Author:liu_hongjie
				        //Date : 2014/9/18 15:58
				        //[BUG]0048915 ADD BEGIN
						
						var lv=length.val();
						var dvl=$("#defaultValue").val().length;
						if(lv<dvl){
							defaultValue.addClass("ui-state-error");							
							updateFieldTips( "缺省值的长度不能大于定义的长度!" );
							return false;	
						}
						//[BUG]0048915 ADD END
					}
					
					return true;
					
				}else{
					if (l>max || l<min) {
						o.addClass("ui-state-error");
						updateFieldTips( n + "的长度必须在[" + min + " ~ " + max + "]之间!" );
						return false;
					} 
				}
				return true;
			}
				
			//检查是否为空
			function checkNull(o,n){
				var l = o.val().length;
				
				if($.trim(o.val())=="" || $.trim(o.val())==null || $.trim(o.val())=="请选择..."){
					o.addClass("ui-state-error");
					updateFieldTips( n + "不能为空 !");
					return false;
				}
				if(n=="类型"){
					if(o.val()=="NUMBER(1)"&&$("#defaultValue").val()!=""&&$("#defaultValue").val()!=null){
						//布尔型只能为0-9之间的数字
						var reg= new RegExp(/^[01]$/);
						if(!reg.test($("#defaultValue").val())){
							updateFieldTips("缺省值只能为0或1!" );
							$("#defaultValue").addClass("ui-state-error");
							return false;
						}
					}
				}
				return true;
			}
									
			function checkMain(o,n,min,max) {
				var wrong = false;
				$.ajaxSetup({async:false});
				var l = $.trim(o.val()).length;
				//优先判断是否为空
				if($.trim(o.val())=="" || $.trim(o.val())==null){
					o.addClass("ui-state-error");
					//如果输入了空格，需要去掉空格；
					o.val("");
					updateMainTips( n + "不能为空！");
					return false;
				}
				
				//校验数据是否存在重复
				if(n=="结构名称"){
					if($.trim(o.val())!=""){
						var url_="./selectValueIsExist.html";
						jQuery.post(url_,
								{
								    "dictName":o.val(),
								    "operation":$("#operation").val(),
								    "dictId":$("#dictId").val()
								},function(e){
									 if(e.status==1){
										 updateMainTips(n+"已存在,请重新输入!");
										 o.addClass("ui-state-error");
										 wrong = true;
									 }
						});
						if(wrong){
							return false;
						}
					}
				}
				
				if(n=="对应表名"){
					//表名不能以数字开头123Table，表名无效错误！
					var reg= new RegExp(/^[a-zA-Z]+[a-zA-Z0-9_]*$/gi);
					if(!reg.test($.trim(o.val()))){
						o.addClass("ui-state-error");
						updateMainTips( n + "必须以字母开头,只能包括字母、数字和下划线!");
						return false;
					}else{
						if($.trim(o.val())!=""){
							//输入的表名是否为oracle关键字

							var url_="./selectIsOracleKeyWord.html";
							jQuery.post(url_,
									{
									    "fieldName":tableName.val()
									},function(e){
										 //如果是关键字，提示修改!
										 if(e.result==1){
											 mainTips.text("输入的表名为oracle关键字,请重新输入!").addClass("ui-state-highlight");
											 tableName.addClass("ui-state-error");
											 wrong = true;
										 }else{
										     url_="./selectValueIsExist.html";
											 jQuery.post(url_,
													{
													    "tableName":o.val(),
													    "operation":$("#operation").val(),
													    "dictId":$("#dictId").val()
													},function(e){
														 if(e.status==1){
															 updateMainTips(n+"术语结构已存在,请重新输入!");
															 o.addClass("ui-state-error");
															 wrong = true;
														 }else if(e.status==2){
															 updateMainTips(n+"物理表已存在,请先手动删除物理表!");
															 o.addClass("ui-state-error");
															 wrong = true;
														 }else if(e.status==3){
															 updateMainTips(n+"物理表和术语结构都存在,请重新输入!");
															 o.addClass("ui-state-error");
															 wrong = true;
														 }
											 });
										 }
									});
								   if(wrong){
										return false;
								   }
						}
					}
				}
				
				if(n=="术语ID"){
					if($.trim(o.val())!=""){
						var url_="./selectValueIsExist.html";
						jQuery.post(url_,
								{
								    "serviceId":o.val(),
								    "operation":$("#operation").val(),
								    "dictId":$("#dictId").val()
								},function(e){
									 if(e.status==1){
										 updateMainTips(n+"已存在,请重新输入!");
										 o.addClass("ui-state-error");
										 wrong = true;
									 }
						});
						if(wrong){
							return false;
						}
						//列名只能以字母开头，包含字母数字和下划线
						var reg= new RegExp(/^[a-zA-Z]+[a-zA-Z0-9_]*$/gi);
						if(!reg.test($.trim(o.val()))){
							o.addClass("ui-state-error");
							updateMainTips( n + "必须以字母开头,只能包括字母、数字和下划线!");
							return false;
						}
					}
					
					
				}
				
				if(max!=0&&min!=0){
					if (l>max || l<min) {
						o.addClass("ui-state-error");
						updateMainTips( n + "的长度必须在[" + min + " ~ " + max + "]之间!" );
						return false;
					}
				}
				
				return true;
			}
			
			function checkMainNull(o,n) {
				if($.trim(o.val())=="" || $.trim(o.val())==null || $.trim(o.val())=="请选择..."){
					o.addClass("ui-state-error");
					//如果输入了空格，需要去掉空格；
					o.val("");
					updateMainTips( n + "为空,请输入!");
					return false;
				}else{
					return true;
				}
			}
			
	var fieldName = $("#fieldName"),
		fieldDesc = $("#fieldDesc"),
		fieldType = $("#fieldType"),
		length = $("#length"),
		precision = $("#precision"),
		defaultValue = $("#defaultValue"),
		isMust = $("#isMust"),
		isPrimary = $("#isPrimary"),
		isShow = $("#isShow"),
		isFilter = $("#isFilter"),
		fieldTips = $(".fieldValidateTips"),
		mainTips = $(".mainValidateTips"),
		dictName = $("#dictName"),
		tableName = $("#tableName"),
		sysId = $("#sysId"),
		serviceId = $("#serviceId"),
		mainTypeCd = $("#typeCd"),
		executeTips = $(".executeValidateTips");
		allFields = $([]).add(fieldName).add(fieldDesc).add(fieldType).add(length).add(precision).add(defaultValue),
		allMains = 	$([]).add(dictName).add(tableName).add(sysId).add(serviceId).add(mainTypeCd);	
					
		$( "#addrowdialog" ).dialog({
			autoOpen: false,
			height: 320,
			width: 470,
			resizable: true,
			modal: true,
			draggable: true,
			//Author:liu_hongjie
	        //Date : 2014/9/19 10:58
	        //[BUG]0048891 ADD BEGIN
			open: function() {
				//jquery之dialog的键盘事件(点击回车关闭对话框)
				$(this).bind("keypress.ui-dialog", function(event) {
					if (event.keyCode == $.ui.keyCode.ENTER) {						
						$.mastWallShow();
						$.mastWallHide();						
						$( this ).dialog( "close" );
						
					}
				});
			},
			 //[BUG]0048891 ADD END
			buttons: {
				"确定": function() {
					//表单校验
					$("#saveTermStuct").removeAttr("disabled");
					$("#showSQL").removeAttr("disabled");
				    $.mastWallShow();
					var bValid = true;
					fieldTips.text("").removeClass("ui-state-highlight");
					allFields.removeClass("ui-state-error");					
					bValid = bValid && checkMixed(fieldName,"列名", 1,25);
					bValid = bValid && checkMixed(fieldDesc,"注释",1,50);
					bValid = bValid && checkNull(fieldType, "类型" );
					if($("#lengthShow").css("display")!="none"){
						bValid = bValid && checkMixed(length, "长度",1, 10);
					}
					//bValid = bValid && checkMixed(defaultValue, "缺省值",1, 25);
					if(bValid){
						var form = document.fieldform;
						var thisObj =  $( this );
						//判断是否为oracle关键字
						var url_="./selectIsOracleKeyWord.html";
						jQuery.post(url_,
								{
								    "fieldName":fieldName.val()
								},function(e){
									 //如果是关键字，提示修改!
									 if(e.result==1){
										 fieldTips.text("输入的名称为oracle关键字,请重新输入!").addClass( "ui-state-highlight");
										 fieldName.addClass("ui-state-error");
										 return;
									 }else{
										 allFields.removeClass("ui-state-error");
										 fieldTips.text("").removeClass( "ui-state-highlight");
										 url_="./selectExecuteSql.html";
											//判判断行数，optRow!=0为修改，否则为新增
											var value ="";
											if(fieldType.find("option:selected").val()=="DATE"){
												value = $("#defaultDate").val();
											}else{
												value = $("#defaultValue").val();
											}
											if($("#optRow").val()!="0"){
												var tdObj = $("#tr_"+$("#optRow").val()).children("td");

												//如果物理表存在
												if($("#tableIsExist").val()=="1"){
													//发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
													jQuery.post(url_,
															{
																"dropColumn":dropColumn,
															    "tableName":tableName.val(),
															    "fieldId":tdObj.eq(0).text(),
															    "fieldName":fieldName.val(),
															    "fieldDesc":fieldDesc.val(),
															    "fieldType":fieldType.find("option:selected").val(),
															    "length":length.val(),
															    "precision":precision.val(),
															    "defaultValue":value,
															    "isMust":isMust.attr("checked")=="checked"?"Y":"N",
															    "isPrimary":isPrimary.attr("checked")=="checked"?"Y":"N"
															},function(e){
																 //如果存在风险操作，提示警告信息
																 if(e.warnMsg!=""&&e.warnMsg!="undefined"){
																	alert(e.warnMsg);
																 }
																 //将修改对应的sql返回并设到隐藏列中
																 tdObj.eq(13).text(e.sql);
													});
												}
												tdObj.eq(2).text(fieldName.val());
												tdObj.eq(3).text(fieldDesc.val());
												tdObj.eq(4).text(fieldType.find("option:selected").text());
												tdObj.eq(5).text(fieldType.find("option:selected").val());
												tdObj.eq(6).text(length.val());
												tdObj.eq(7).text(precision.val());
												tdObj.eq(8).text(value);
												tdObj.eq(9).text(isMust.attr("checked")=="checked"?"√":"×");
												tdObj.eq(10).text(isPrimary.attr("checked")=="checked"?"√":"×");
												tdObj.eq(11).text(isShow.attr("checked")=="checked"?"√":"×");
												tdObj.eq(12).text(isFilter.attr("checked")=="checked"?"√":"×");
													
											}else{
												//如果物理表存在，才有请求的必要。
												if($("#tableIsExist").val()=="1"){
													jQuery.post(url_,
															{
																"dropColumn":dropColumn,
															    "tableName":tableName.val(),
															    "fieldId":"",
															    "fieldName":fieldName.val(),
															    "fieldDesc":fieldDesc.val(),
															    "fieldType":fieldType.find("option:selected").val(),
															    "length":length.val(),
															    "precision":precision.val(),
															    "defaultValue":value,
															    "isMust":isMust.attr("checked")=="checked"?"Y":"N",
															    "isPrimary":isPrimary.attr("checked")=="checked"?"Y":"N"
															},function(e){
																 //如果存在风险操作，提示警告信息
																 if(e.warnMsg!=""){
																	alert(e.warnMsg);
																 }
																 addFieldInfo(e.sql);
													});
												}else{
													addFieldInfo("noSql");
												}
												form.reset();
											}
											thisObj.dialog( "close" );
									 }
						});
					}
					$.mastWallHide();
				},
				"关闭": function() {
					$.mastWallShow();
					$.mastWallHide();
					allFields.val("").removeClass("ui-state-error");
					$(".validateTips").text("").removeClass( "ui-state-highlight");
					//Author:liu_hongjie
			        //Date : 2014/9/18 15:58
			        //[BUG]0048915 ADD BEGIN
					updateFieldTips("");
					//[BUG]0048915 ADD END
					$( this ).dialog( "close" );
				}
			},"close": function() {
				$.mastWallShow();
				$.mastWallHide();
		    }
		});
		
		function addFieldInfo(sqlText){
			var value ="";
			if(fieldType.find("option:selected").val()=="DATE"){
				value = $("#defaultDate").val();
			}else{
				value = $("#defaultValue").val();
			}
			
			var rowcnt = $("table[id$='fieldTable']>tbody").children("tr").length+1;
			$("table[id$='fieldTable']>tbody").append("<tr id=\"tr_"+rowcnt+"\">"
					+"<td style='display:none'></td>"
					+"<td align='center'>"+rowcnt+"</td>"
					+"<td>"+fieldName.val()+"</td>"
					+"<td>"+fieldDesc.val()+"</td>"
					+"<td>"+fieldType.find("option:selected").text()+"</td>"
					+"<td style='display:none'>"+fieldType.find("option:selected").val()+"</td>"
					+"<td>"+length.val()+"</td>"
					+"<td>"+precision.val()+"</td>"
					+"<td>"+value+"</td>"
					+"<td align='center'>"+(isMust.attr("checked")=="checked"?"√":"×")+"</td>"
					+"<td align='center'>"+(isPrimary.attr("checked")=="checked"?"√":"×")+"</td>"
					+"<td align='center'>"+(isShow.attr("checked")=="checked"?"√":"×")+"</td>"
					+"<td align='center'>"+(isFilter.attr("checked")=="checked"?"√":"×")+"</td>"
					+"<td style='display:none'>"+sqlText+"</td>"
					+"<td align='center'><a href=\"#\" onclick=\"javascript:editRow('"+rowcnt+"');\" style=\"color:#2d56a5;cursor:hand;\" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style=\"color:#2d56a5;cursor:hand;\"  onclick=\"javascript:deleteRow('"+rowcnt+"');\">删除</a></td>"
					+"</tr>");
		}
		
		
		$( "#showSQLdialog" ).dialog({
			autoOpen: false,
			height: 370,
			width: 550,
			resizable: false,
			modal: true,
			draggable: true,
			buttons: {
				"执行": function() {
			 		$(":button:contains('执行')").attr("disabled","disabled");
					if($("#sqlContentArr").val()!=""){
						$.mastWallShow();
						var url_="./doExecuteSql.html";
						jQuery.post(url_,
								{"sqlContentArr":$("#sqlContentArr").val(),
								 "sqlIdArr":$("#sqlIdArr").val(),
								 "tableName":$("#tableName").val(),
								 "dictId":$("#dictId").val()
								 },function(e){
									 
									 executeTips.css("color","red");
									 updateExecuteTips("");
									 if(e.flag==0){
										 executeTips.text("创建物理表"+$("#tableName").val()+"成功!").addClass("ui-state-highlight");
										 executeTips.css("color","blue");
									 }else if(e.flag==1){
										 executeTips.css("color","blue");
										 updateExecuteTips("执行sql成功,物理表"+$("#tableName").val()+"结构已更新!");
									 }else if(e.flag==-1){
										 updateExecuteTips("sql执行错误!");
									 }else if(e.flag==2){
										 updateExecuteTips("物理表"+$("#tableName").val()+"已存在,请勿重复创建!");
									 }else if(e.flag==-2){
										 updateExecuteTips("存在部分sql执行错误!");
									 }
									 $("#executeSql").empty();
									 var listArr = e.resultList.split("@");
									 for(var i=0;i<listArr.length;i++){
											$("#executeSql").append(listArr[i]);
									 }
									 
						});
					}else{
						executeTips.css("color","red");
						updateExecuteTips("不存在待执行的sql,请勿点击执行按钮!");
					}
					
				},
				"关闭": function() {
					$.mastWallShow();
					$.mastWallHide();
					$("#autoOpen").val("false");
					var form = document.tsform;
					form.action="./editTermStruct.html";
					form.submit();
					
					$( this ).dialog( "close" );
				}
			},"close": function() {
					$.mastWallShow();
					$.mastWallHide();
					var form = document.tsform;
					form.action="./editTermStruct.html";
					form.submit();
				$( this ).dialog( "close" );
			}
		});
		
		/*$("#mgErrorSQLDialog" ).dialog({
			autoOpen: false,
			height: 370,
			width: 750,
			resizable: false,
			modal: true,
			draggable: true,
			buttons: {
				"全部归档": function() {
					  
				},
				"关闭": function() {
					$( this ).dialog("close");
				}
			},
			"close": function() {
				
			}
		});*/
		

		/*$("#mgErrorSQL").click(function() {
			$("#mgErrorSQLDialog").load("./mgErrorSql.html", {"dictId":$("#dictId").val()}, function(e) {
				
			});
			$("#mgErrorSQLDialog").dialog("open");
		})*/
		
		//校验main信息是否存在异常
		function checkDictMain(){
			var bValid = true;
			mainTips.text("").removeClass("ui-state-highlight");
			allMains.removeClass("ui-state-error");
			bValid = bValid && checkMain(dictName,"结构名称", 1,50);
			if(!bValid){
				return false;
			}
			bValid = bValid && checkMain(tableName,"对应表名", 1,25);
			if(!bValid){
				return false;
			}

			bValid = bValid && checkMain(sysId,"主数据源",0,0);	
			if(!bValid){
				return false;
			}
			bValid = bValid && checkMain(serviceId,"术语ID", 1,25);
			if(!bValid){
				return false;
			}
			bValid = bValid && checkMain(mainTypeCd,"结构类型",0,0);
			if(!bValid){
				return false;
			}else{
				return true;
			}
		}
		
		//校验main信息中除“对应表名”外其他信息是否存在异常
		function checkDictMainNoTabName(){
			var bValid = true;
			mainTips.text("").removeClass("ui-state-highlight");
			allMains.removeClass("ui-state-error");
			bValid = bValid && checkMain(dictName,"结构名称", 1,50);
			if(!bValid){
				return false;
			}
//			bValid = bValid && checkMain(tableName,"对应表名", 1,25);
//			if(!bValid){
//				return false;
//			}

			bValid = bValid && checkMain(sysId,"主数据源",0,0);	
			if(!bValid){
				return false;
			}
			bValid = bValid && checkMain(serviceId,"术语ID", 1,25);
			if(!bValid){
				return false;
			}
			bValid = bValid && checkMain(mainTypeCd,"结构类型",0,0);
			if(!bValid){
				return false;
			}else{
				return true;
			}
		}
		
		//校验field信息是否存在异常
		function checkDictField(){
			
			//无任何字段信息时，保存提示字段信息不完整
			if($("#fieldTable").find("tr").length==1){
				updateFieldTips("字段信息不完整,请完善!");
	    		showAddDialog();
	    		allMains.removeClass("ui-state-error");
				mainTips.text("").removeClass("ui-state-highlight");
				return false;
			}
			
			//校验是否存在相同
			if(!checkFieldNameIsExist()){
				return false;
			}else{
				return true;
			}
		}
		
		$("#addrow").click(function() {
			optRow = "addRow";
			$.mastWallShow();
			//校验main信息是否符合规范
			//如果物理表不存在，则需要checkDictMain			
		if($("#tableIsExist").val()!="1"){ 			
			if(checkDictMain()){
				$.mastWallShow();
				var url_="./selectIsOracleKeyWord.html";
				jQuery.post(url_,
						{
						    "fieldName":tableName.val()
						},function(e){
							 //如果是关键字，提示修改!
							 if(e.result==1){
								 mainTips.text("输入的表名为oracle关键字,请重新输入!").addClass("ui-state-highlight");
								 tableName.addClass("ui-state-error");
								 return;
							 }else{
								 allMains.removeClass("ui-state-error");
								 mainTips.text("").removeClass( "ui-state-highlight");
								 showAddDialog();
							 }
						})
			}else{
				$.mastWallHide();
			}
		}else{
			  if(checkDictMainNoTabName()){
					$.mastWallShow();		
					allMains.removeClass("ui-state-error");
					mainTips.text("").removeClass( "ui-state-highlight");
					 showAddDialog();
							
				}else{
					$.mastWallHide();
				}			 
		}
		});
	
		function showAddDialog(){
			var form = document.fieldform;
			form.reset();
			$("#isMust").attr("disabled",false);
			$("#isMust").attr("checked",false);
			$("#isPrimary").attr("checked",false);
			$("#isShow").attr("checked",false);
			$("#isFilter").attr("checked",false);
			$("#precisionShow").css("display","none");
			$("#fieldName").removeAttr("disabled");
			$("#length").css("display","block");
			$("#lengthShow").css("display","table-row");
			$("#showDate").html("<input type=\"text\" name=\"defaultValue\" id=\"defaultValue\" value=\"\"/>");
			$("#addrowdialog").dialog("open");
			$("#addrowdialog").css("height","220");
		}
		
		function getIndexNames(){
				
			var indexNames = "";
			$("#fieldTable").find("tr").each(function(m){
				var fieldName = "";
				$(this).find("td").each(function(n){
					if(n==2){
						fieldName = $(this).text();
					}
					//添加索引
					if(n==10 && $(this).text()=="√"){
						indexNames += fieldName + ",";
					}
				})
			})
			indexNames = indexNames.substring(0,indexNames.length-1);
			return indexNames;
		}
		
	    $("#showSQL").click(function() {
	    	$("#showSQL").attr("disabled","true");
	    	$.mastWallShow();
	    	//校验main和field信息是否存在异常
	    	//if(checkDictMain() && checkDictField()){
	    	//如果物理表不存在，则需要checkDictMain,否则需要checkDictMainNoTabName
	    	var bool = false;
	    	if($("#tableIsExist").val()!="1"){
	    		bool = checkDictMain();
	    	}else{
	    		bool = checkDictMainNoTabName();
	    	}
	    	
	    	if(bool && checkDictField()){
	    		$.mastWallShow();
			    //保存术语术语结构数据
			    //标识是执行的保存和SQL预览，让页面停留在sql页面，后台保存，不跳转
			    getFieldListData();
				var indexNames = getIndexNames();
				var url_="./saveAndViewSQL.html";
				jQuery.post(url_,
						{"indexNames":indexNames,
						"paramData":$("#paramData").val(),
						 "dropColumn":$("#dropColumn").val(),
						 "dictId":$("#dictId").val(),
						 "serviceId":$("#serviceId").val(),
						 "dictName":$("#dictName").val(),
						 "tableName":$("#tableName").val(),
						 "typeCd":$("#typeCd").val(),
						 "sysId":$("#sysId").val()
						 },function(e){
							 if(e.status==1){
								 //点击保存并浏览SQL，默认已经保存
								 executeTips.text("保存成功!").addClass("ui-state-highlight");
								 executeTips.css("color","blue");
								 if(!($("#operation").val()=="edit")){
									 $("#dictId").val(e.dictId);
								 }
								 showSQLDialog();
							 }else{
								 executeTips.text("保存失败!").addClass("ui-state-highlight");
								 executeTips.css("color","red");
							 }
							 
							 //不管有没有保存成功，删除记录从页面上消失!
							 $("#dropColumn").val("");
							 dropColumn = "";
							 $.mastWallHide();
							 $("#showSQL").removeAttr("disabled");
				});
	    	}else {
	    		$.mastWallHide();
				$("#showSQL").removeAttr("disabled");
	    	}	    		    	
	    });
		
		$("#fieldType").change(function(){
			//改变后，长度和默认值会自动清空
			$("#length").val("");
			$("#precisionShow").val("");
			
			//如果下拉选择数值类型需要增加精度值
			var selectedValue = $(this).find("option:selected").val();
			if(selectedValue=="NUMBER"){
				$("#precisionShow").css("display","table-row");
				$("#addrowdialog").css("height","320");
			}else{
				$("#precisionShow").css("display","none");
				$("#precision").val("");
				$("#addrowdialog").css("height","240");
			}

			//如果选择日期或者布尔，长度应该隐藏
			if(selectedValue=="DATE"||selectedValue=="NUMBER(1)"){
				$("#lengthShow").css("display","none");
				$("#addrowdialog").css("height","180");		
			}else if(selectedValue=="VARCHAR2"){
				$("#lengthShow").css("display","table-row");
				$("#addrowdialog").css("height","220");
				$("#defaultValue").val("");
			}else{
				$("#lengthShow").css("display","table-row");				
				$("#addrowdialog").css("height","240");
				$("#defaultValue").val("");
			}
			
			if(selectedValue=="DATE"){
				$("#showDate").html("<input type=\"text\" class=\"Wdate\" id=\"defaultDate\" name=\"defaultDate\" onfocus=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})\"/>");
			}else{
				$("#defaultValue").val("");
				$("#showDate").html("<input type=\"text\" name=\"defaultValue\" id=\"defaultValue\" value=\"\"/>");
			}
		});
		
		$("#isPrimary").click(function(){

			if($("#isPrimary").attr("checked")=="checked"){
				$("#isMust").attr("disabled",true);
				$("#isMust").attr("checked",true);
			}else{
				$("#isMust").attr("disabled",false);
				$("#isMust").attr("checked",false);
			}
		})
		
		$("#saveTermStuct").click(function() {			 
		  $("#saveTermStuct").attr("disabled","true");
		  $.mastWallShow();
		  //校验main和field信息是否存在异常
		  //if(checkDictMain() && checkDictField()){
		  //如果物理表不存在，则需要checkDictMain,否则需要checkDictMainNoTabName
	    	var bool = false;
	    	if($("#tableIsExist").val()!="1"){
	    		bool = checkDictMain();
	    	}else{
	    		bool = checkDictMainNoTabName();
	    	}
	    	
	    	if(bool && checkDictField()){    	
			  	$.mastWallShow();
	    		allMains.removeClass("ui-state-error");
				mainTips.text("").removeClass("ui-state-highlight");
				//获取field列表信息，为paramData设值
				getFieldListData();
				var indexNames = getIndexNames();
				var url_="./saveTermStruct.html";
				jQuery.post(url_,
						{"indexNames":indexNames,
						"paramData":$("#paramData").val(),
					 	 "dropColumn":$("#dropColumn").val(),
						 "dictId":$("#dictId").val(),
						 "serviceId":$("#serviceId").val(),
						 "dictName":$("#dictName").val(),
						 "tableName":$("#tableName").val(),
						 "typeCd":$("#typeCd").val(),
						 "sysId":$("#sysId").val()
						 },function(e){
							 if(e.status==1){
								 asyncbox.alert("保存成功！","提示信息",function(action){
									 if(action = 'ok'){
										 //新增时发起请求，进入编辑界面，避免重复新增；编辑不用；
										 $("#dictId").val(e.dictId);
										 $("#autoOpen").val(e.autoOpen);
										 var form = document.tsform;
										 form.action="./editTermStruct.html";
										 form.submit(); 
									 }
								 });
							 }else{
								 asyncbox.alert("存在异常,保存失败！","提示信息");
							 }
							 //不管有没有保存成功，删除记录从页面上消失!
							 $("#dropColumn").val("");
							 dropColumn = "";
							 $.mastWallHide();
							 $("#saveTermStuct").removeAttr("disabled");
				       });
					}else{
						$.mastWallHide();
						$("#saveTermStuct").removeAttr("disabled");
					}
		});
		
		
		function checkFieldNameIsExist(){
			var result = true;
			var noPrimary = true;
			var arr = new Array();
			$("#fieldTable").find("tr").each(function(m){
				$(this).find("td").each(function(n){
					//字段名
					$(this).removeClass("ui-state-error");
					if(n==2){
						if(m>1){ 
							if($.inArray($(this).text().toLocaleLowerCase(),arr)>0){
								$(this).addClass("ui-state-error");
								$("#fieldTable").find("tr").eq($.inArray($(this).text().toLocaleLowerCase(),arr)).find("td").eq(2).addClass("ui-state-error");
								updateMainTips("字段名称有重复,请修改!");
								result = false;
							}
						}
						arr[m] = $(this).text().toLocaleLowerCase();
					}
					if(n==10){
						if($(this).text()=="√"){
							noPrimary = false;
						}
					}
				})
			})
			if(noPrimary){
				updateMainTips("不存在唯一标识,请确认!");
				result = false;
			}
			
			return result;
		}
});

	//删除字段信息
	function deleteRow(id,obj){	
		if($("#tableIsExist").val()=="1"&&$("#tr_"+id).children("td").eq(0).text()!=""){
			var url_ = "./selectDataIsExist.html";
			//发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
			jQuery.post(url_,
					{
					    "tableName":$("#tableName").val(),
					    "fieldName":$("#tr_"+id).children("td").eq(2).text()
					},function(e){
						var fieldId = $("#tr_"+id).children("td").eq(0).text();
						var fieldName = $("#tr_"+id).children("td").eq(2).text();
						var tableName = $("#tableName").val();
						if(e.result>0){
							var trObj = $(obj).parent().parent();
							trObj.css("background","#fab5b5")
							asyncbox.confirm('该字段对应的物理表列有数据存在! 要删除吗？','术语结构修改',function(action){
								if(action == 'ok'){
								$("#tr_"+id).remove();
								//将删除的sql记录到dict_sql表中，点击保存后，在删除操作的地方核实该sql的有效性，因为如果点击保存前刷新页面，删除操作无效！
								addOneDeleteSql(fieldId,fieldName,tableName);
								//删除后，排序，更换id和删除的参数1111111
								for(var i=(parseFloat(id)+1);i<=$("table[id$='fieldTable']>tbody").children("tr").length+1;i++){
									$("#tr_"+i).children("td").eq(1).text(i-1);
									$("#tr_"+i).children("td")[14].innerHTML = "<a href=\"#\" onclick=\"javascript:editRow("+(i-1)+");\" style=\"color:#2d56a5;\" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"javascript:deleteRow('"+(i-1)+"');\" style=\"color:#2d56a5;\" >删除</a>";
									$("#tr_"+i).attr("id","tr_"+(i-1));
								}
							}else{
								trObj.css("background","");
							}})
						}else if(e.result==0){
							var trObj = $(obj).parent().parent();
							trObj.css("background","#fab5b5")
							asyncbox.confirm('确定要删除该字段吗？','术语结构修改',function(action){
								if(action == 'ok'){
								$("#tr_"+id).remove();
								addOneDeleteSql(fieldId,fieldName,tableName);
								//删除后，排序，更换id和删除的参数1111111
								for(var i=(parseFloat(id)+1);i<=$("table[id$='fieldTable']>tbody").children("tr").length+1;i++){
									$("#tr_"+i).children("td").eq(1).text(i-1);
									$("#tr_"+i).children("td")[14].innerHTML = "<a href=\"#\" onclick=\"javascript:editRow("+(i-1)+");\" style=\"color:#2d56a5;\" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"javascript:deleteRow('"+(i-1)+"');\" style=\"color:#2d56a5;\" >删除</a>";
									$("#tr_"+i).attr("id","tr_"+(i-1));
								}
							}else{
								trObj.css("background","");
							}})
						}else if(e.result==-1){
							var trObj = $(obj).parent().parent();
							trObj.css("background","#fab5b5")
							asyncbox.confirm('确定要删除该字段吗？请注意：由于表中无此列，故不会生成对应的sql。','术语结构修改',function(action){
								if(action == 'ok'){
								$("#tr_"+id).remove();
								//删除后，排序，更换id和删除的参数1111111
								for(var i=(parseFloat(id)+1);i<=$("table[id$='fieldTable']>tbody").children("tr").length+1;i++){
									$("#tr_"+i).children("td").eq(1).text(i-1);
									$("#tr_"+i).children("td")[14].innerHTML = "<a href=\"#\" onclick=\"javascript:editRow("+(i-1)+");\" style=\"color:#2d56a5;\" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"javascript:deleteRow('"+(i-1)+"');\" style=\"color:#2d56a5;\" >删除</a>";
									$("#tr_"+i).attr("id","tr_"+(i-1));
								}
							}else{
								trObj.css("background","");
							}
						})
					}
						
			});
		}else{
			var trObj = $(obj).parent().parent();
			trObj.css("background","#fab5b5")
			asyncbox.confirm('确定要删除该字段吗？','术语结构修改',function(action){
				if(action == 'ok'){
				$("#tr_"+id).remove();
				//删除后，排序，更换id和删除的参数
				for(var i=(parseFloat(id)+1);i<=$("table[id$='fieldTable']>tbody").children("tr").length+1;i++){
					$("#tr_"+i).children("td").eq(1).text(i-1);
					$("#tr_"+i).children("td")[14].innerHTML = "<a href=\"#\" onclick=\"javascript:editRow("+(i-1)+");\" style=\"color:#2d56a5;\" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"javascript:deleteRow('"+(i-1)+"');\" style=\"color:#2d56a5;\" >删除</a>";
					$("#tr_"+i).attr("id","tr_"+(i-1));
				}
			}else{
				trObj.css("background","");
			}})
		}
		
	}
	
	//删除字段时，形成一条删除的sql语句
	function addOneDeleteSql(fieldId,fieldName,tableName){
		var url_ = "./selectOneDeleteSql.html";
		//发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
		jQuery.post(url_,
				{
				 "fieldId":fieldId,
				 "tableName":tableName,
		         "fieldName":fieldName
		},function(e){
			dropColumn += e.deleteSql;
		});
	}
	
	//编辑字段信息
	function editRow(id){	
		optRow = "editRow";
		//获取各输入控件对象
		var fieldName = $("#fieldName");
		var fieldDesc = $("#fieldDesc");
		var fieldType = $("#fieldType");
		var length = $("#length");
		var precision = $("#precision");
		var defaultValue = $("#defaultValue");
		
		//清理掉错误提示信息		
		var fieldTips = $(".fieldValidateTips");
		var allFields = $([]).add(fieldName).add(fieldDesc).add(fieldType).add(length).add(precision).add(defaultValue);
		fieldTips.text("").removeClass("ui-state-highlight");
		allFields.removeClass("ui-state-error");
		
		var form = document.fieldform;
		form.reset();
		$("#isMust").attr("checked",false);
		$("#isPrimary").attr("checked",false);
		$("#isShow").attr("checked",false);
		$("#isFilter").attr("checked",false);
		
		//根据参数获取改行记录，挨个赋值
		var tdObj = $("#tr_"+id).children("td");
		$("#fieldName").val(tdObj.eq(2).text());
		
		//修改字段信息时，对于已经存在物理表的且物理表中存在物理字段的，字段名不可修改
		if($("#tableIsExist").val()=="1"){
			var url_ = "./selectDataIsExist.html";
			//发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
			jQuery.post(url_,
					{
					    "tableName":$("#tableName").val(),
					    "fieldName":$("#tr_"+id).children("td").eq(2).text()
					},function(e){
						if(e.result==-1){
							$("#fieldName").removeAttr("disabled");
						}else{
							$("#fieldName").attr("disabled","true");
						}
					});
			
		}
		
		$("#fieldDesc").val(tdObj.eq(3).text());
		//读取隐藏列中字段类型对应的value，而不是text
		$("#fieldType").attr("value",tdObj.eq(5).text());
	
		if(tdObj.eq(5).text()=="DATE" || tdObj.eq(5).text()=="NUMBER(1)"){
			$("#lengthShow").css("display","none");
		}else{
			$("#lengthShow").css("display","table-row");
		}
		
		$("#length").val(tdObj.eq(6).text());
	
		if(tdObj.eq(5).text()=="NUMBER"){
			$("#precisionShow").css("display","table-row");
		}else{
			$("#precisionShow").css("display","none");
		}
		$("#precision").val(tdObj.eq(7).text());
		if(tdObj.eq(5).text()=="DATE"){
			
			$("#showDate").html("<input type=\"text\" class=\"Wdate\" id=\"defaultDate\" name=\"defaultDate\" onfocus=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})\"/>");
			$("#defaultDate").val(tdObj.eq(8).text());
		}else{
			$("#showDate").html("<input type=\"text\" name=\"defaultValue\" id=\"defaultValue\" value=\"\"/>");
			$("#defaultValue").val(tdObj.eq(8).text());
		}
		
		if(tdObj.eq(9).text()=="√"){
			$("#isMust").attr("checked",true);
		}
		if(tdObj.eq(10).text()=="√"){
			$("#isPrimary").attr("checked",true);
		}else{
			$("#isMust").removeAttr("disabled");
		}
		if(tdObj.eq(11).text()=="√"){
			$("#isShow").attr("checked",true);
		}
		if(tdObj.eq(12).text()=="√"){
			$("#isFilter").attr("checked",true);
		}
		//记录当前操作未修改操作
		$("#optRow").val(id);
		//打开dialog
		$("#addrowdialog").dialog("open");
		
		if(tdObj.eq(5).text()=="DATE" || tdObj.eq(5).text()=="NUMBER(1)"){
			$("#addrowdialog").css("height","180");
		}else if(tdObj.eq(5).text()=="VARCHAR2"){
			$("#addrowdialog").css("height","200");
		}else{
			$("#addrowdialog").css("height","240");
		}
	}
	
	//遍历field列表，将各个列信息保存提交
	function getFieldListData(){
		var dispOrder,fieldName,fieldDesc,fieldType,length,precision,defaultValue,isMust,isPrimary,isShow;
		var paramData="";
		$("#fieldTable").find("tr").each(function(i){
			var tdData="";
			//循环列表每行数据，保存到paramData中，传递给后台
			$(this).find("td").each(function(j){
				if(j!=14){
					if(j==13){
						tdData = tdData + ($(this).text()==""?"noSql":$(this).text());
					}else{
						tdData = tdData + $(this).text()+"#"
					}
				}
			})
			if(i!=0){
				if(i==$("#fieldTable").find("tr").length-1){
					paramData += tdData;
				}else{
					paramData += tdData+"@";
				}
			}
			
		});
		$("#paramData").val(paramData);
		$("#dropColumn").val(dropColumn);
	}
	
	//展示sql执行窗口
	function showSQLDialog(){
		var trData = "";
		var idArr = "";
		var sqlArr = "";
		var comment = "";
		var indexUnion = "";
		//清空表中的所有行
		$("#executeSql").empty();
		//如果存在物理表，sql为alter,如果不存在，则create
		if($("#tableIsExist").val()=="1"){
			//所有待执行的sql都从dict_sql表中执行
			var url_ = "./selectAllExecuteSql.html";
			//发请求，查询该字段背后是否存在数据,是否需要风险提示和sql返回
			jQuery.post(url_,
					{
					    "dictId":$("#dictId").val()
					},function(e){
						if(e.sql!=""){
							if(e.sql=="术语结构无变动、无SQL预览!"){
								$(":button:contains('执行')").attr("disabled","disabled");
								$("#executeSql").append("<tr><td>"+e.sql);
							}else{
								var arr = e.sql.split("@");
								$("#sqlIdArr").val(e.idArr);
								$("#sqlContentArr").val(e.sqlArr);
								for(var i=0;i<arr.length;i++){
									$("#executeSql").append(arr[i]);
								}
							}
						}
			        });
			
		}else{
			//组织SQL展示
	    	trData = "create table " + $("#tableName").val() + "<br/>(";
	    	comment = "comment on table " + $("#tableName").val() + " is '" + $("#dictName").val() + "'; <br/>"
	    	$("#fieldTable").find("tr").each(function(m){
	    		var tdData = "";
	    		var isNumber = false;
	    		var dateOrBoolean = false;
	    		var typeA="";
	    		var fieldName = "";
				$(this).find("td").each(function(n){
					//n=2:dictName,n=5:fieldType,n=6:length,n=7：precision
					//字段名
					if(n==2){
						tdData = "" + tdData+$(this).text() + "   ";
						fieldName = $(this).text();
					}
					
					if(n==3){
						comment += "comment on column " + $("#tableName").val() + "." + fieldName +" IS '" + $(this).text() + "'; <br/>"
					}
					
					//字段类型
					if(n==5){
						typeA = $(this).text();
						if($(this).text()=="DATE" || $(this).text()=="NUMBER(1)"){
							dateOrBoolean = true;
							tdData += $(this).text() + "  ";
						}else{
							if($(this).text()=="NUMBER"){
								isNumber = true;
							}
							tdData += $(this).text() + "(";
						}
						
						
					}
					
					//如果为日期或布尔，省略长度、精度，进入缺省值判断
					if(!dateOrBoolean){
						//长度
						if(n==6){
							if(isNumber){
								tdData += $(this).text() + ",";
							}else{
								
							//Author:liu_hongjie
				            // Date : 2014/9/9 13:58
				            //[BUG]0048840 ADD BEGIN
							//	tdData += $(this).text() + " char)";
								tdData += $(this).text() + ")";
							//[BUG]0048840 ADD END
							}
							
						}
						
						//精度
						if(n==7&&isNumber){
							tdData += $(this).text() + ")";
						}
					}
					
					//缺省值
					if(n==8 && $(this).text()!=""){
						//区分是数字还是字符
						if(typeA.indexOf("NUMBER")!=-1){
							tdData += " default " + $(this).text();
						}else if(typeA.indexOf("DATE")!=-1){
							tdData += " default  timestamp '" + $(this).text() + "'";
						}else{
							tdData += " default '" + $(this).text() + "'";
						}
						
						
					}
		
					if(n==9 && $(this).text()=="√"){
						tdData += "  not null"
					}
					
					//添加索引
					if(n==10 && $(this).text()=="√"){
						indexUnion += fieldName + ",";
					}
				})
				if(m==0){
					trData  +=  tdData + "<br/>";
				}else{
					trData  +=  tdData + ",<br/>";
				}
			})
			indexUnion = indexUnion.substring(0,indexUnion.length-1);
			
			trData  += "  create_by  VARCHAR2(50),<br/>";
	    	trData  += "  create_time  DATE,<br/>";
	    	trData  += "  last_update_by  VARCHAR2(50),<br/>";
	    	trData  += "  last_update_time  DATE,<br/>";
	    	trData  += "  delete_by  VARCHAR2(50),<br/>";
	    	trData  += "  delete_time  DATE,<br/>";
	    	trData  += "  delete_flg  VARCHAR2(1),<br/>";
	    	trData  += "  update_count  NUMBER(10),<br/>";
	    	trData  += "  item_version  NUMBER(10),<br/>";
	    	trData  += "  opt_status  VARCHAR2(1),<br/>";
	    	trData  += "  release_status  VARCHAR2(1),<br/>";
	    	trData  += "  uni_key  VARCHAR2(50) NOT　NULL<br/>";
	    	trData  += ");<br/>";
	    	trData  += "alter table ";
	    	trData  += $("#tableName").val();
	    	trData  += " add constraint ";
	    	//保障主键名称唯一
	    	//trData  += " pk_uni_key_" + $("#tableName").val();
	    	//Author:liu_hongjie
            // Date : 2014/9/1 15:58
            //[BUG]0047781 ADD BEGIN
	    	trData  += " pk_" + $("#tableName").val();
	    	//[BUG]0047781 ADD END
	    	trData  += " primary key (uni_key); <br/>";
	    	
	    	//为默认设置字段设值注释
	    	comment += "comment on column " + $("#tableName").val() + ".create_by  is '创建人'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".create_time   is '创建时间'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".last_update_by  is '最后修改人'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".last_update_time  is '最后修改时间'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".delete_by    is  '删除人'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".delete_time  is  '删除时间'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".delete_flg   is  '删除标识'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".update_count is  '更新次数'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".item_version is  '版本号'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".opt_status   is  '操作状态'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".release_status is '发布状态'; <br/>";
	    	comment += "comment on column " + $("#tableName").val() + ".uni_key  is '唯一主键'; <br/>";
	    			
	    	trData  += comment;
	    	//trData  += "create index index_" + $("#tableName").val() + " on " + $("#tableName").val() + "(" + indexUnion +")"+ "; <br/>"
	    	//Author:liu_hongjie
            // Date : 2014/9/1 15:58
            //[BUG]0047781 ADD BEGIN
	    	trData  += "create index IDX_" + $("#tableName").val() + " on " + $("#tableName").val() + "(" + indexUnion +")"+ "; <br/>"
	    	//[BUG]0047781 ADD END
	    	$("#sqlContentArr").val(trData);
	    	$("#executeSql").append("<tr><td>"+trData+"</td></tr>");
		}
		$("#showSQLdialog").dialog("open");
		return;
	}
	
