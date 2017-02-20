
$(function() {
			$("#mapaddsearch").bind("click", function() {
				var form = document.tsform;
				form.action = "./addMappingCode.html";
				form.submit();
			});
			$("#fileField").hide();
			
			
	    var fieldTips = $(".fieldValidateTips"),
	        sourceCode = $("#sourceCode"),
	        sourceCodeContent = $("#sourceCodeContent"),
	        sourceCodeVersion = $("#sourceCodeVersion"),
	        srcUniKey = $("#srcUniKey"),
	        targetCode = $("#targetCode"),
	        targetCodeContent = $("#targetCodeContent"),
	        targetCodeVersion = $("#targetCodeVersion"),
	        tarUniKey = $("#tarUniKey"),
	        allFields = $([]).add(sourceCode).add(sourceCodeContent).add(targetCode).add(targetCodeContent);
	    
	    function checkNull(){	
	    	var flag = true;
	        var aa = allFields.map(function() {
	        	   var reg = /[*：]/g; 
	        	   var a = $(this).parent().parent().find("label").text().replace(reg,"");
	        	   var vale = $(this).val(); 	
	        	   if($.trim(vale) == null || $.trim(vale) == ""){
	        		   return a;
	        	   }
	    	     }).get().join('、');
	        if($.trim(aa)!=null && $.trim(aa)!=""){
	        	updateFieldTips(aa);
	        	flag = false;
	        }
	        
	    	return flag;
	    }
	    
	    function updateFieldTips(t) {
	    	fieldTips.text(t+"不允许为空！").addClass("ui-state-highlight");
	    }
	    
	    
	    function checkdata(){
	    	var flag = true;
	    	//进行数据验证
			var url_="./checkdata.html";
			$('#fieldform').ajaxSubmit({
			      type:"post",
			      url:url_,
			      async:false,
			      complete:function(msg){
			    	  flag = true;
			      }
			    });
			
	    }
   
		$( "#addMappingrowdialog" ).dialog({
			autoOpen: false,
			height: 350,
			width: 490,
			resizable: true,
			modal: true,
			draggable: false,
			open: function() {
				$(this).bind("keypress.ui-dialog", function(event) {
					if (event.keyCode == $.ui.keyCode.ENTER) {										
						$( this ).dialog( "close" );
					}
				});
			},
			buttons: {
				"确定": function() {
					var bValid = false;
					fieldTips.text("").removeClass("ui-state-highlight");
					allFields.removeClass("ui-state-error");
					if(checkNull()){
						var url="./checkCodedata.html";
						$('#fieldform').ajaxSubmit({
						      type:"post",
						      url:url,
						      async:false,
						      success:function(msg){
						    	  if(msg.status==0){		    		 
						    		  bValid = true;
						    		  asyncbox.alert(msg.opt+"成功","提示");
						    	  }else if(msg.status==1){
						    		  //alert("存在重复数据，\n\n请重新选择数据！");
						    		  asyncbox.alert("存在重复数据，\n\n请重新选择数据！","提示");
						    	  }else if(msg.status==2){
						    		  //alert("编码或者编码名称不存在,\n\n请重新选择数据！");
						    		  asyncbox.alert("编码或者编码名称不存在,\n\n请重新选择数据！","提示");
						    	  }else{
						    		  //	asyncbox.alert(msg.opt+"失败","提示");
						    	  }
						      }
						    });
						
						
						if(bValid){
                            if($("#optRow").val()>0){
                            	var hid = $("#tr_"+$("#optRow").val());
                            	var tdObj = hid.children("td");
                            	hid.find("[name='srcUniKey']").val(srcUniKey.val());
                            	hid.find("[name='tarUniKey']").val(tarUniKey.val());
								tdObj.eq(1).text(sourceCode.val());
								tdObj.eq(2).text(sourceCodeContent.val());
								tdObj.eq(3).text(sourceCodeVersion.val());
								tdObj.eq(4).text(targetCode.val());
								tdObj.eq(5).text(targetCodeContent.val());
								tdObj.eq(6).text(targetCodeVersion.val());
                            }else{
                            	//var operation =  $("#operation").val();
                            	//if(operation != null && operation !=""){
                            		location.href="./addMappingCode.html?"+urlargs();
                            	//}else{
                            	//	location.href="./addMappingCode.html"
                            	//}                       	
                            }
							$(this).dialog( "close" );
						}
					};
					
				},
				"关闭": function() {
					$(this).dialog( "close" );
				}
			},
			close: function() {
			   if($("div#pager_first_sourceCode").length>0){
				   $("div#pager_first_sourceCode").parent().parent().parent().remove();
			   }	
		    }
		});
		
		
		function urlargs(){		
			var sourceTable = $("#sourceTable").val();
			var targetTable = $("#targetTable").val();
			var targetName = $("#targetName").val();
			var sourceName = $("#sourceName").val();
			var operation = $("#operation").val();
			
			return "&sourceTable="+sourceTable+"&targetTable="+targetTable+"&targetName="+targetName
			+"&sourceName="+sourceName+"&operation="+operation+"&redict=redict";
		}
		
		$( "#addExcel" ).dialog({
			autoOpen: false,
			height: 150,
			width: 470,
			resizable: true,
			modal: true,
			draggable: true,
			open: function() {
				$(this).bind("keypress.ui-dialog", function(event) {
					if (event.keyCode == $.ui.keyCode.ENTER) {										
						$( this ).dialog( "close" );
					}
				});
			},
			buttons: {
				"导入": function() {
					var aa = $("#textfield").val();
					if(aa == null || aa==""){
						//alert("请选择Excel文件！");
						//alert(aa);
						asyncbox.alert("请选择Excel文件！","提示");
					}else{
						var url="./importexceldata.html";
						$('#excelform').ajaxSubmit({
						      type:"post",
						      url:url,
						      beforeSubmit:function(){
						    	  $("#addExcel").dialog( "close" );
						      },
						      complete:function(msg){
						    	  //alert(msg.responseText);
						    	  asyncbox.alert(msg.responseText,"提示",function(){
						    		  //$.mastWallHide();
							    	  location.href="./addMappingCode.html";
						    	  });
						      }
						});
					}
					
				},
				"关闭": function() {
					$(this).dialog( "close" );
				}
			},
			close: function() {
				$("#textfield").val("");
		    }
		});
});  

//前台对象弹出打印
function writeObj(obj){ 
    var description = ""; 
    for(var i in obj){   
        var property=obj[i];   
        description+=i+" = "+property+"\n";  
    } 
    //alert(description);
    asyncbox.alert(description,"提示");
} 

//前台对象转为字符串
function obj2str(o){
	   var r = [];
	   if(typeof o == "string" || o == null) {
	     return o;
	   }
	   if(typeof o == "object"){
	     if(!o.sort){
	       r[0]="{"
	       for(var i in o){
	         r[r.length]=i;
	         r[r.length]=":";
	         r[r.length]=obj2str(o[i]);
	         r[r.length]=",";
	       }
	       r[r.length-1]="}"
	     }else{
	       r[0]="["
	       for(var i =0;i<o.length;i++){
	         r[r.length]=obj2str(o[i]);
	         r[r.length]=",";
	       }
	       r[r.length-1]="]"
	     }
	     return r.join("");
	   }
	   return o.toString();
	}


function clickScanBtn(){

	$("#fileField").trigger("click");
}

function addexcel(){
	$("#addExcel").dialog("open");
}

function addorfix(id){
   if($.trim(id) == null || $.trim(id) == ""){
	   var sourceTable = $("form#tsform #sourceTable").val();
	   var targetTable = $("form#tsform #targetTable").val();
	  
	   
	   if($.trim(sourceTable)==null || $.trim(sourceTable)=="" || $.trim(targetTable)==null || $.trim(targetTable)==""){
		   asyncbox.alert("新增时源和目标不允许为空！","提示");
		   return false;
	   }
	   
	   var flag = false;
	   $.ajax({
		   type: "post",
		   url: "./checkTabledata.html",
		   async:false,
		   data:{sourceTable:sourceTable,targetTable:targetTable},
		   success: function(e){
			    if(e.status==0){
					 flag = true;
					 asyncbox.alert("源或目标数据表不存在,\n\n请重新选择数据表！","提示");
				 }else if(e.status==-1){
					 flag = true;
					 asyncbox.alert("操作失败！","提示");
				 }
			  }
		 });
	   if(flag){
		   return false;
	   }
	   myDropDownListCode("sourceCode","./selectCodeNodeInfo.html?table="+sourceTable,1,10,"sourceCodeContent");
	   myDropDownListCode("targetCode","./selectCodeNodeInfo.html?table="+targetTable,1,10,"targetCodeContent");
	   myDropDownListCode("sourceCodeContent","./selectCodeNodeInfo.html?table="+sourceTable,1,10,"sourceCode");
	   myDropDownListCode("targetCodeContent","./selectCodeNodeInfo.html?table="+targetTable,1,10,"targetCode");

   }
	   showAddMappingDialog(id);
}


function deleteRow(id,obj) {
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5");
	asyncbox.confirm("确定删除该术语映射吗？","提示",function(action){
		if('ok' == action){
			var tdObj = $("#tr_"+id);
			var form = document.tsform;
			var sourceTable=tdObj.find($("[name='sourceTableRecord']")).val(),
			    targetTable=tdObj.find($("[name='targetTableRecord']")).val(),
			    sourceUniKey=tdObj.find("[name='srcUniKey']").val(),
			    targetUniKey=tdObj.find("[name='tarUniKey']").val(),
			    sourceName=$("form#tsform #sourceName").val(),
			    targetName=$("form#tsform #targetName").val();
			var url = "./deleteMappingCode.html?sourceTable1="+sourceTable+"&targetTable1="+targetTable+"&sourceCode1="+sourceUniKey+"&targetCode1="+targetUniKey+"&deltype=single&sourceName1="+sourceName+"&targetName1="+targetName;
			form.action = url;
			form.submit();
		}else{
			 trObj.css("background","");
		 }
	});
}


function showAddMappingDialog(id){
	var form = document.fieldform;
	form.reset();
	if(id != null){
		var hid = $("#tr_"+id);
		var tdObj = hid.children("td");

		$("#srcUniKey").add("#srcUniKey_y").val(hid.find("[name='srcUniKey']").val());
		$("#tarUniKey").add("#tarUniKey_y").val(hid.find("[name='tarUniKey']").val());
		
		$("#sourceCode").val(tdObj.eq(1).text());
		$("#sourceCodeContent").val(tdObj.eq(2).text());
		$("#sourceCodeVersion").val(tdObj.eq(3).text());
		
		$("#targetCode").val(tdObj.eq(4).text());
		$("#targetCodeContent").val(tdObj.eq(5).text());
		$("#targetCodeVersion").val(tdObj.eq(6).text());
		
		$("form#fieldform #sourceTable").val($("#tr_"+id).find($("[name='sourceTableRecord']")).val());
		$("form#fieldform #targetTable").val($("#tr_"+id).find($("[name='targetTableRecord']")).val());
		
		var aa = $("form#fieldform #targetTable").val();
		$(".fix").attr("disabled",true);
		$("#optRow").val(id);
		myDropDownListCode("targetCode","./selectCodeNodeInfo.html?table="+aa,1,10,"targetCodeContent");
		myDropDownListCode("targetCodeContent","./selectCodeNodeInfo.html?table="+aa,1,10,"targetCode");
	}else{
		var sourceTable = $("form#tsform #sourceTable").val();
		var targetTable = $("form#tsform #targetTable").val();
		$("form#fieldform #sourceTable").val(sourceTable);
		$("form#fieldform #targetTable").val(targetTable);
		$(".fix").attr("disabled",false);
		$("form#fieldform [name='operation']").val("add");
	}
	$("#addMappingrowdialog").dialog("open");
	$("#addMappingrowdialog").css("height","220");
}

function checkfiletype(e){
	var aa = e.substring(e.lastIndexOf('.')+1,e.length);
	if($.trim(aa) != "xls" && $.trim(aa) != "xlsx"){
		$("#textfield").val("");
		
		var file = $("#fileField");
		file.after(file.clone().val(""));
		file.remove(); 

		asyncbox.alert("文件类型不符,\n\n请选择Excel文件！","提示");
	}else{
		$("#textfield").val(e);
	}
}

function addvalue(){
	
	var a = $("#sourceTable").val();
	var b=$("#targetTable").val();
	var c=$("#targetName").val();
	var d=$("#sourceName").val();
	
	var a_attr = $("#sourceTable").attr("disabled");
    
	if($.trim(a)!=null && $.trim(a)!="" && a_attr=="disabled"){
		$("form#tsform").append('<input type="hidden" name="sourceTable" value="'+a+'"/>');
	}
	if($.trim(b)!=null && $.trim(b)!="" && a_attr=="disabled"){
		$("form#tsform").append('<input type="hidden" name="targetTable" value="'+b+'"/>');
	}
    if($.trim(c)!=null && $.trim(c)!="" && a_attr=="disabled"){
    	$("form#tsform").append('<input type="hidden" name="targetName" value="'+c+'"/>');
	}
    if($.trim(d)!=null && $.trim(d)!="" && a_attr=="disabled"){
    	$("form#tsform").append('<input type="hidden" name="sourceName" value="'+d+'"/>');
	}
}

function firstPage1(form)
{
	addvalue();
    //alert(form.currentPage.value);
	form.reset();
    form.jumpToPage.value = 1;
    form.submit();
}

function prevPage1(form)
{
	addvalue();
    var cp = parseInt(form.currentPage.value, 10);
    //alert(cp);
    form.reset();
    if(cp <= 1)
        form.jumpToPage.value = 1;
    else
        form.jumpToPage.value = cp - 1;
    form.submit();
 
}

function nextPage1(form)
{
	addvalue();
    var cp = parseInt(form.currentPage.value, 10);
    var tp = parseInt(form.totalPage.value, 10);
    form.reset();
    if(cp >= tp)
        form.jumpToPage.value = tp;
    else
        form.jumpToPage.value = cp + 1;
    form.submit();
   
}

function lastPage1(form)
{
	addvalue();
    //alert(form.currentPage.value);
    form.reset();
    form.jumpToPage.value = form.totalPage.value;
    form.submit();
   
}

function jumpToPage1(form, pageNo)
{
   // form.reset();
    //alert(form.currentPage.value);
    var tp = parseInt(form.totalPage.value, 10);
    try
    {
    	addvalue();
        var inpVal = parseInt(pageNo.value, 10);
        if(isNaN(inpVal) || inpVal < 1)
        {
            var msg = "请输入正确的页码！";
            var err = new Error(msg);
            if(!err.message)
            {
                err.message = msg;
            }
            throw err;
        }
        else if(inpVal > tp)
        {
            var msg = "输入的页码超过总页数，请重新输入！";
            var err = new Error(msg);
            if(!err.message)
            {
                err.message = msg;
            }
            throw err;
        }
        else
        {
            //form.reset();
            form.jumpToPage.value = pageNo.value;
            form.submit();
            form.reset();
        }
        
    }
    catch(e)
    {
        //alert(e.message);
    	asyncbox.alert(e,"提示");
        pageNo.focus();
        pageNo.select();
    }
}




		
		
		
		