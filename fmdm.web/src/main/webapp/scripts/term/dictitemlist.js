$(function(){
	//设置表格宽度
	var leftOffset = $(window).width()-197;
	//获取登录账户的用户名
	$("#termListDiv").css( {
		"width" : leftOffset + "px"
	});
	
	 //弹出框
	var tips;
	$("#searchBtn").click(function(){
    	var form = document.DataForm;
    	$("[name='export']").val("");
    	form.action = "./dictitemlist.html";
    	form.submit();
	});
	$("#cleanBtn").click(function(){
		var viewTypeId = $("#viewTypeId").val();
		var title = $("#titleFlag").val();
		if(title == '0'){
			var url_ = "./dictitemlist.html?title="+$("#titleFlag").val()+"&dictId="+$("#dictId").val()+
			"&dictName="+$("#dictName").val()+"&tableName="+$("#tableName").val()+
			"&searchAll="+"&viewTypeId="+viewTypeId;
		}else{
			var url_ = "./dictitemlist.html?title="+$("#titleFlag").val()+"&dictId="+$("#dictId").val()+
			"&dictName="+$("#dictName").val()+"&tableName="+$("#tableName").val()+
			"&searchAll=on"+"&viewTypeId="+viewTypeId;
		}
		location.href=url_;
	});
	//导出数据 add by wp
	$("#exportBtn").click(function(){
    	var form = document.DataForm;
    	$("[name='export']").val("export");
    	form.action = "./dictitemlist.html";
    	form.reset();
	});
	$(window).keydown(function (e) {
		if (e.which == 13) { 
			$("#searchBtn").click();
		} 
	});
	
	//复选框事件
    $("#checkall").click(function() { 
    	if($("#checkall").attr("checked")){
    		$("input[id='uni_keyBox']").prop("checked",true);
    	}else{
    		$("input[id='uni_keyBox']").prop("checked",false);
    	}
    	
    });
    
    $("input[name='uni_keyBox']").click(function() { 
    	if($("input[name='uni_keyBox']:checked").length < $("input[id='uni_keyBox']").length){
    		$("#checkall").prop("checked",false);
    	}
    	else if($("input[name='uni_keyBox']:checked").length = $("input[id='uni_keyBox']").length){
    		$("#checkall").prop("checked",true);
    	}
    });
	
    $( "#commentA-form" ).dialog({
		autoOpen: false,
		height: 260,
		width: 425,
		resizable: true,
		modal: true,
		draggable: true,
		buttons: {
			"确认": function() {
				var comment = $("#commentA").val();
				var uni_keys="";    
				  $('input[name="uni_keyBox"]:checked').each(function(){    
					  uni_keys += $(this).val()+",";    
				  }); 
				var userName=$("#userName").val();
				var dictId=$("#dictId").val();
				var title=$("#title").val();
				var status="a";
				var url_= "./termStateEdit.html";
				var isSuccess = true;
				$.ajaxSetup({async:false});
			    jQuery.post(url_,{"uni_keys":uni_keys,"dictId":dictId,"title":title,"status":status,"comment":comment},function(e){ 
			    	if(e.ifSuccess){
			    		asyncbox.alert("审批成功","提示信息",function(action){
			    			if(action == 'ok'){
			    				$.ajaxSetup({async:true});
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }else{
			    		asyncbox.alert("审批失败","提示信息",function(action){
			    			if(action == 'ok'){
			    				$.ajaxSetup({async:true});
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }
				});
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
    $( "#commentR-form" ).dialog({
		autoOpen: false,
		height: 260,
		width: 425,
		resizable: true,
		modal: true,
		draggable: true,
		buttons: {
			"确认": function() {
				var comment = $("#commentR").val();
				var uni_keys="";    
				  $('input[name="uni_keyBox"]:checked').each(function(){    
					  uni_keys += $(this).val()+",";    
				  });
				var userName=$("#userName").val();
				var dictId=$("#dictId").val();
				var title=$("#title").val();
				var status="r";
				var url_= "./termStateEdit.html";
				$.ajaxSetup({async:false});
			    jQuery.post(url_,{"uni_keys":uni_keys,"dictId":dictId,"title":title,"status":status,"comment":comment},function(e){ 
			    	if(e.ifSuccess){
			    		asyncbox.alert("驳回成功","提示信息",function(action){
			    			if(action == 'ok'){
			    				$.ajaxSetup({async:true});
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }else{
			    		asyncbox.alert("驳回失败","提示信息",function(action){
			    			if(action == 'ok'){
			    				$.ajaxSetup({async:true});
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }
				});
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	
})
//弹出修改/增加数据输入框
function showTermEdit(uni_key,dictId,title,viewType,viewId,titleName){
	jQuery("#dialog-form").load("./dictDataEditshow.html", {"uni_key":uni_key,"dictId":dictId,"title":title}, function(e) {
	});
	$( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 550,
		width: 650,
		resizable: true,
		modal: true,
		draggable: true,
		title:titleName,
		buttons: {
			"提交": function() {
				tips = $( ".validateTips" );
				var bValid = true;
				var form = document.itemDataEditForm;
				var wrongMessage="";
				var primaryKeys="";
				var primaryKeysLabel="";
				$("#itemDataEditForm input[type='text']").each(function(){
					var labelValue = $(this).parent().prev().children('label').eq(0).text();
					var checkArray = $(this).attr("pattern").split(",");
					if("Y"==checkArray[0]){
						if(!checkNull($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"不能为空 ;";
							bValid = bValid && false;
						}
						if(!checkLen($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"长度超过表的定义长度 ;";
							bValid = bValid && false;
						}
						
					}
					if("NUMBER"==checkArray[1]){
						var precision = checkArray[3];
						var length = checkArray[4];
						if(!checkIsNaN($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"只能为数字;";
							bValid = bValid && false;
						}
						//alert(precisionCompare($(this).val(),precision,length,labelValue));
						if(!precisionCompare($(this).val(),precision,length,labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"长度为:"+length+"精度为:"+precision+";" 
							bValid = bValid && false;
						}
					}
					if("Y"==checkArray[2]){
						primaryKeys+=$(this).attr('name')+":"+$(this).val()+",";
						primaryKeysLabel+=labelValue+" ";
					}
					if(bValid){
						$(this).removeClass( "ui-state-error" );
					}
				});
				if(!checkUniOne(dictId,primaryKeys)){
					wrongMessage+=primaryKeysLabel+"主键重复，请更改;";
					bValid = bValid && false;
				}
				
				if(bValid){
					var viewTypeId = viewType+","+viewId;
					form.action = "./termDataEdit.html?viewTypeId="+viewTypeId;
			    	form.submit();
				}else{
					updateTips(wrongMessage);
				}
			},
			"取消": function() {
				$("#itemDataEditForm input[type='text']").each(function(){
					$(this).val( "" ).removeClass( "ui-state-error" );
				});
				$(".validateTips").text('').removeClass( "ui-state-highlight");
				$( this ).dialog( "close" );
			}
		},
		"close": function() {
			$(".validateTips").text('').removeClass( "ui-state-highlight");
			$("#itemDataEditForm input[type='text']").each(function(){
				$(this).val( "" ).removeClass( "ui-state-error" );
			});
		}
	});
	$( "#dialog-form" ).dialog( "open" );	
	
};
//弹出修改/增加数据输入框
function showAdminTermEdit(uni_key,dictId,title,titleName){
	jQuery("#dialog-form").load("./dictDataEditshow.html", {"uni_key":uni_key,"dictId":dictId,"title":title}, function(e) {
	});
	$( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 550,
		width: 650,
		resizable: true,
		modal: true,
		draggable: true,
		title:titleName,
		buttons: {
			"提交": function() {
				tips = $( ".validateTips" );
				var bValid = true;
				var form = document.itemDataEditForm;
				var wrongMessage="";
				var primaryKeys="";
				var primaryKeysLabel="";
				$("#itemDataEditForm input[type='text']").each(function(){
					var labelValue = $(this).parent().prev().children('label').eq(0).text();
					var checkArray = $(this).attr("pattern").split(",");
					if("Y"==checkArray[0]){
						if(!checkNull($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"不能为空 ;";
							bValid = bValid && false;
						}
						if(!checkLen($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"长度超过表的定义长度 ;";
							bValid = bValid && false;
						}
						
					}
					if("NUMBER"==checkArray[1]){
						var precision = checkArray[3];
						var length = checkArray[4];
						if(!checkIsNaN($(this), labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"只能为数字;";
							bValid = bValid && false;
						}
						//alert(precisionCompare($(this).val(),precision,length,labelValue));
						if(!precisionCompare($(this).val(),precision,length,labelValue)){
							$(this).addClass( "ui-state-error" );
							wrongMessage+=labelValue+"长度为:"+length+"精度为:"+precision+";" 
							bValid = bValid && false;
						}
					}
					if("Y"==checkArray[2]){
						primaryKeys+=$(this).attr('name')+":"+$(this).val()+",";
						primaryKeysLabel+=labelValue+" ";
					}
					if(bValid){
						$(this).removeClass( "ui-state-error" );
					}
				});
				if(!checkUniOne(dictId,primaryKeys)){
					wrongMessage+=primaryKeysLabel+"主键重复，请更改;";
					bValid = bValid && false;
				}
				
				if(bValid){
					var viewTypeId = null;
					form.action = "./termDataEdit.html?viewTypeId="+viewTypeId;
					form.submit();
				}else{
					updateTips(wrongMessage);
				}
			},
			"取消": function() {
				$("#itemDataEditForm input[type='text']").each(function(){
					$(this).val( "" ).removeClass( "ui-state-error" );
				});
				$(".validateTips").text('').removeClass( "ui-state-highlight");
				$( this ).dialog( "close" );
			}
		},
		"close": function() {
			$(".validateTips").text('').removeClass( "ui-state-highlight");
			$("#itemDataEditForm input[type='text']").each(function(){
				$(this).val( "" ).removeClass( "ui-state-error" );
			});
		}
	});
	$( "#dialog-form" ).dialog( "open" );	
	
};

function updateTips( t ) {
	tips.text( t )   
		.addClass( "ui-state-highlight" );
}
function checkIsNaN(o,n){
	if(isNaN(o.val().replace(/(^\s*)|(\s*$)/g,''))){
		//updateTips( n + "只能为数字 ");
		return false;
	}
	return true;
}
function checkNull(o, n){
	var l = o.val().length;
	if(o.val().replace(/(^\s*)|(\s*$)/g,'')=="" || o.val().replace(/(^\s*)|(\s*$)/g,'')==null){
		//updateTips( n + "不能为空 ");
		return false;
	}
	return true;
}
function checkLen($this,n) {
	var x = $this.val()
	var l = x.replace(/[^\x00-\xff]/g, "xxx").length;
	var maxLen = parseInt($this.attr("maxLength"))
	var labelValue =$this.parent().prev().children('label').eq(0).text();
	if (l > maxLen) {
		return false;
	}
	return true;
}
//唯一性校验
function checkUniOne(dictId,primaryKeys){
	var url_= "./checkUniPrimaryKey.html"
	var status=true;
	$.ajaxSetup({async:false});
    jQuery.post(url_,{"primaryKeys":primaryKeys,"dictId":dictId},function(e){ 
		 if(e.status==false){
			 status= false;
		 }
	});
    $.ajaxSetup({async:true});
	return status;
}

//审批 发布
function rejectTermEdit(dictId,title,status){
	var commentDepart = "";
	var userName=$("#userName").val();
	//var comment="";
	if(status=='a'){
		commentDepart = "审批";
		var uni_keys="";    
		  $('input[name="uni_keyBox"]:checked').each(function(){    
			  uni_keys += $(this).val()+",";    
		  });    
		if(uni_keys.length==0){
			asyncbox.alert("请选择要"+commentDepart+"的数据！","提示信息");
			return;
		}
		$( "#commentA-form" ).dialog( "open" );
	}else if(status=='r'){
		commentDepart = "驳回";
		var uni_keys="";    
		  $('input[name="uni_keyBox"]:checked').each(function(){    
			  uni_keys += $(this).val()+",";
		  });    
		if(uni_keys.length==0){
			asyncbox.alert("请选择要"+commentDepart+"的数据！","提示信息");
			return;
		}
		$( "#commentR-form" ).dialog( "open" );
	}else if(status=='c'){
		commentDepart = "发布";
		var uni_keys="";    
		  $('input[name="uni_keyBox"]:checked').each(function(){    
			  uni_keys += $(this).val()+",";    
		  });    
		if(uni_keys.length==0){
			asyncbox.alert("请选择要"+commentDepart+"的数据！","提示信息");
			return;
		}
		asyncbox.confirm('确定'+commentDepart+'吗？','术语发布',function(action){
			if(action == 'ok'){
				var url_= "./termStateEdit.html";
			    jQuery.post(url_,{"uni_keys":uni_keys,"dictId":dictId,"title":title,"status":status},function(e){ 
			    	 if(e.ifSuccess){
			    		asyncbox.alert(commentDepart+"成功","提示信息",function(action){
			    			if(action == 'ok'){
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }else{
			    		asyncbox.alert(commentDepart+"失败","提示信息",function(action){
			    			if(action == 'ok'){
			    				if(userName == 'admin'){
			    					doAdminSearch();
			    				}else{
			    					doSearch();
			    				}
			    			}
			    		});
			    	 }
				});
			}})
	}
	
	
};

//删除
function deleteTermData(uniKey,dictId,title,userName,obj){
	var trObj = $(obj).parent().parent();
	trObj.css("background","#fab5b5")
	if(userName == 'admin'){
		asyncbox.confirm('确定要删除吗？','术语数据删除',function(action){
			if(action == 'ok'){
				var url_= "./termDataDelete.html"
					jQuery.post(url_,{"uniKey":uniKey,"dictId":dictId,"title":title},function(e){ 
						if(e.status==1){
							asyncbox.alert("删除成功","提示信息");
						}else{
							asyncbox.alert("删除失败","提示信息");
						}
						doAdminSearch();
					});
			}else{
				trObj.css("background","");
			}
		})
	}else{
		//判断该账户是否具有所有字段的编辑权限，如果没有就提示权限不足，无法删除。
		var url_= "./checkAuthority.html"
			jQuery.post(url_,{"dictId":dictId},function(e){ 
				if(e.status==false){
					asyncbox.alert("您的权限不足，无法删除，请联系管理员!","提示信息",function(action){
						if(action == 'ok'){
							trObj.css("background","");
						}
					});
				}else{
					
					asyncbox.confirm('确定要删除吗？','术语数据删除',function(action){
						if(action == 'ok'){
							var url_= "./termDataDelete.html"
								jQuery.post(url_,{"uniKey":uniKey,"dictId":dictId,"title":title},function(e){ 
									if(e.status==1){
										asyncbox.alert("删除成功","提示信息");
									}else{
										asyncbox.alert("删除失败","提示信息");
									}
									doSearch();
								});
						}else{
							trObj.css("background","");
						}
					})
					
					
				}
			});
	}
}
//精度
function precisionCompare(num,precision,length,labelValue){
	var result = 10;
	for(var i=0;i<length-1;i++){
		var j = 10;
		result = result*j;
	}
	var reg=new RegExp(/^[0-9]*$/gi);
	if($.trim(precision)==0 && $.trim(precision)!=""){
		reg= new RegExp(/^[0-9]*$/gi);
		if(!reg.test(num)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
		
		if(parseFloat(num)>(result-1)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
		
	}else if($.trim(precision)==1){
		reg= new RegExp(/^[0-9]+(.[0-9]{1})?$/gi);
		if(!reg.test(num)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
		
		
		if(parseFloat(num)>(result/10-0.1)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
		
		
	}else if($.trim(precision)==2){
		reg= new RegExp(/^[0-9]+(.[0-9]{2})?$/gi);
		if(!reg.test(num)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
		if(parseFloat(num)>(result/100-0.01)){
			//defaultValue.addClass("ui-state-error");
			//updateFieldTips(labelValue+"长度为:"+length+"精度为:"+precision+"!" );
			return false;
		}
	}
	return true;
}
function doSearch(){
	var form = document.DataForm;
	form.action = "./dictitemlist.html";
	form.submit();
}

function doAdminSearch(){
	var form = document.DataForm;
	form.action = "./admindictitemlist.html";
	form.submit();
}



