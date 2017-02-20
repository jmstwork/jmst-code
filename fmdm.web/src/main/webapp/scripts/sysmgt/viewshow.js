jQuery(function($){
		 $(".tbody input[name='itemOrder1']").keydown(function(event){
//		  	alert("nn");
		  	var keyCode = event.which; 
          	if (keyCode == 46 || keyCode == 8 || keyCode == 37 || keyCode == 39 || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) ) 
             { return true; } 
             else { return false } }
             ).focus(function() 
                 { 
                 this.style.imeMode = 'disabled'; 
                 }); 
		$("#closeBtn02").click(function(){
			window.parent.dialogTest.dialog("close");
		});
		checkChecked("addFlg");
		checkChecked("deleteFlg");
		checkChecked("approveFlg");
		checkChecked("releaseFlg");
		var viewType00 = $("#viewType00").val();
		if(viewType00 != 1){
			$("#viewOperation").css('display','none');
		}
		//视图类型下拉改变事件
		$("#viewType01").change(function(){
		 	var viewType = $("#viewType01").val();
		 	//alert(viewType);
	 	    if (viewType == 1)  
		    {  
		       $("#viewOperation").css('display','');
		    }  
		    else  
		    {  
		        $("#viewOperation").css('display','none');
		    }
		    
		    var dictId = $("#dictId01").val();
		    var viewId = $("#viewId_").val();
		 	var viewType = $("#viewType01").val();
			jQuery("#fieldDataTable").load("../view/fieldTable.html", {"dictId":dictId,"viewId":viewId,"viewType":viewType}, function(e) {
			});
		
		});
		//术语名称下拉改变事件
		$("#dictId01").change(function(){
		 	var dictId = $("#dictId01").val();
		 	var viewId = $("#viewId_").val();
		 	var o = $("#viewType01");
		 	var viewType = $("#viewType01").val();
		 	if($.trim(o.val())=="" || $.trim(o.val())==null || $.trim(o.val())=="请选择..."){
				/*alert("请先选择视图类型！");
				return false;*/
		 		asyncbox.alert('请先选择视图类型！', '提示');
		 		return false;
			}
			jQuery("#fieldDataTable").load("../view/fieldTable.html", {"dictId":dictId,"viewId":viewId,"viewType":viewType}, function(e) {
			});
		});
		//保存事件
		var viewName01 = $("#viewName01"),
		viewType01 = $("#viewType01"),
		dictId01 = $("#dictId01"),
		fields = $( [] ).add( viewName01 ).add( viewType01 ).add( dictId01 ),
		tips = $( ".validateTips" );
			
		$("#saveBtn01").click(function(){
			fields.removeClass( "ui-state-error" );
			$(".validateTips").each(function(){
				tips.removeClass("ui-state-highlight");
				tips.text('');
			});
			var viewName = $("#viewName01");
			var viewType01 = $("#viewType01");
			var dictId = $("#dictId01");
			var isValid = true;
			
			
			isValid = checkNull(dictId,"术语") && isValid;
			
			isValid = checkNull(viewType01,"视图类型") && isValid;
			isValid = checkNull(viewName,"视图名称") && isValid;
			isValid = checkLength(viewName, "视图名称", 1, 20) && isValid;
			if(isValid){
				var viewName = $("#viewName01").val();
				var viewType = $("#viewType01").val();
				var dictId = $("#dictId01").val();
				var operation = $("#operation").val();
				var viewId = $("#viewId_").val();
				
				var addFlg = "";
				var deleteFlg = "";
				var approveFlg = "";
				var releaseFlg = "";
				if(viewType == 1){
					addFlg = $("#termOperation_addFlg").is(":checked")?'1':'0';
					deleteFlg = $("#termOperation_deleteFlg").is(":checked")?'1':'0';
					approveFlg = $("#termOperation_approveFlg").is(":checked")?'1':'0';
					releaseFlg = $("#termOperation_releaseFlg").is(":checked")?'1':'0';
				}
				var leg = $("#fieldDataTable tr").length - 1;
				var fieldTableArr = [];
				if(leg>1){
					if(viewType == 0){//检索类型
						$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
							var fieldNme = $(this).children("td").eq(1).text();
		
							var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
							var fieldId = $(this).children("td").eq(3)[0].id;
							//alert("fieldId:" + fieldId);
							//alert("itemFlg:" + itemFlg);
							var viewFieldId = $(this).children("td").eq(0)[0].id;
							//alert("viewFieldId:"+viewFieldId);
							var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
							//alert("item_order:" + item_order);
							//aalert();
							var retrievalFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
							//alert("retrievalFlg:" + retrievalFlg);
		
							var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
							//alert("orderFlg:" + orderFlg);
							var fieldOrder = $(this).children("td").eq(5).children(0)[2].value;
							//alert("fieldOrder:" + fieldOrder);
							//alert("fieldNme:" + fieldNme+"\n"+"itemFlg:" + itemFlg+"\n"+"itemOrder:" + itemOrder+"\n"+"retrievalFlg:" + retrievalFlg+"\n"+"orderFlg:" + orderFlg+"\n"+"fieldOrder:" + fieldOrder);
	
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
						$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
							var fieldNme = $(this).children("td").eq(1).text();
							//alet();
							var editFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
							var fieldId = $(this).children("td").eq(4)[0].id;
							//alert("fieldId:" + fieldId);
							//alert("itemFlg:" + itemFlg);
							var viewFieldId = $(this).children("td").eq(0)[0].id;
							//alert("viewFieldId:"+viewFieldId);
							var itemOrder = $(this).children("td").eq(4).children(0)[2].value;
							//alert("item_order:" + item_order);
							//aalert();
							var itemFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
							//alert("itemFlg:" + itemFlg);
		
							//var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
							//alert("orderFlg:" + orderFlg);
							//var fieldOrder = $(this).children("td").eq(5).children(0)[2].value;
							//alert("fieldOrder:" + fieldOrder);
							//alert("fieldNme:" + fieldNme+"\n"+"itemFlg:" + itemFlg+"\n"+"itemOrder:" + itemOrder+"\n"+"retrievalFlg:" + retrievalFlg+"\n"+"orderFlg:" + orderFlg+"\n"+"fieldOrder:" + fieldOrder);
	
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
						$("#fieldDataTable tr:gt(0):lt(" + leg + ")").each(function (cnt) {
								var fieldNme = $(this).children("td").eq(1).text();
			
								var itemFlg = $(this).children("td").eq(3).children().children()[0].checked==true?'1':'0';
								var itemOrder = $(this).children("td").eq(3).children(0)[2].value;
								var fieldId = $(this).children("td").eq(3)[0].id;
								//alert("fieldId:" + fieldId);
								//alert("itemFlg:" + itemFlg);
								var viewFieldId = $(this).children("td").eq(0)[0].id;
								//alert("viewFieldId:"+viewFieldId);
								//var itemOrder = $(this).children("td").eq(4).children(0)[2].value;
								//alert("item_order:" + item_order);
								//aalert();
								//var itemFlg = $(this).children("td").eq(4).children().children()[0].checked==true?'1':'0';
								//alert("itemFlg:" + itemFlg);
			
								//var orderFlg = $(this).children("td").eq(5).children().children()[0].checked==true?'1':'0';
								//alert("orderFlg:" + orderFlg);
								//var fieldOrder = $(this).children("td").eq(5).children(0)[2].value;
								//alert("fieldOrder:" + fieldOrder);
								//alert("fieldNme:" + fieldNme+"\n"+"itemFlg:" + itemFlg+"\n"+"itemOrder:" + itemOrder+"\n"+"retrievalFlg:" + retrievalFlg+"\n"+"orderFlg:" + orderFlg+"\n"+"fieldOrder:" + fieldOrder);
		
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
				//alert(fieldTableArr.length);
			for(var i=0;i<fieldTableArr.length;i++){ 
				fieldTableArr[i]=JSON.stringify(fieldTableArr[i]); 
				//alert(fieldTableArr[i]);
			}
			//alert(fieldTableArr);
			var url  = "../view/saveView.html";
			$.post(url,{"fieldTableArr":"["+fieldTableArr+"]","operation":operation,"viewId":viewId,"viewName":viewName,"viewType":viewType,"dictId":dictId,"addFlg":addFlg,"deleteFlg":deleteFlg,"approveFlg":approveFlg,"releaseFlg":releaseFlg},function(data){
				if(data.result == '1'){
					/*alert("保存成功！");
					var url  = "../view/viewlist.html";
					location.href=url;*/
					asyncbox.success('保存成功！', '执行结果',function(){
						var url  = "../view/viewlist.html";
						location.href=url;
					});
				}else{
					//alert("保存失败！");
					asyncbox.error('保存失败！', '执行结果');
				}
			});

			}
			
					
		});			
		function updateFieldTips(t) {
			tips.text(t).addClass( "ui-state-highlight" );
		}
		function checkNull(o,n){
			if($.trim(o.val())=="" || $.trim(o.val())==null || $.trim(o.val())=="请选择..."){
				o.addClass("ui-state-error");
				updateFieldTips( n + "不能为空 !");
				return false;
			}
			return true;
		}
		function checkChecked(o) {
			var val = $("#"+o).val();
			//alert(o+"__"+val);
			if(val == 1){
				$("#termOperation_"+o).attr("checked",true);
			}else{
				$("#termOperation_"+o).attr("checked",false);
			}
			
		}
		
		function checkLength(o, n, min, max) {
			if (o.val() == "" || o.val() == null) {
				updateFieldTips( n + "不能为空 ");
				return false;
			}
			var l = o.val().length;
			if (l > max || l < min) {
				updateFieldTips( n + "的长度必须在【" + min + " ~ " + max + "】之间");
				return false;
			} else {
				return true;
			}
		}
	
});
function check(id){
	$(id).val($(id).val().replace(/\D/g,''));

}
function numAdd(id){
	//alert(id);
	 var num_add = parseInt($(id).val())+1;
	 if($(id).val()==""){
	  num_add = 1;
	 }
	 $(id).val(num_add);
}
function numDec(id){
	//alert($(id).val());
	if($(id).val())
	var numdec = parseInt($(id).val())-1;
	if(numdec<1)
	{
	 	//alert("必须大于等于1");
		asyncbox.alert('必须大于等于1', '提示');
	 	return false;
	}
	$(id).val(numdec);
	 
}	

function onlyNum(id){
  $("#"+id).keydown(function(event)
     { var keyCode = event.which; 
          if (keyCode == 46 || keyCode == 8 || keyCode == 37 || keyCode == 39 || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) ) 
             { return true; } 
             else { return false } }
             ).focus(function() 
                 { this.style.imeMode = 'disabled'; }
                 ); 
}

