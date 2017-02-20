$(function() {
		$("#cleanBtn").bind("click", function() {
			var url_ = "./datasourcelist.html";
			location.href=url_;
		});
	
		var datasourceName = $( "#datasourceName" ),
			datasourceId = $( "#datasourceId" ),
			jdbcSpecific = $( "#jdbcSpecific" ),
			jdbcUrl = $( "#jdbcUrl" ),
			jdbcUserName = $( "#jdbcUserName" ),
			jdbcPassword = $( "#jdbcPassword" ),
			systemId=$("#sysName"),
			allFields = $( [] ).add( datasourceName ).add( jdbcSpecific ).add( jdbcUrl ).add( jdbcUserName ).add( jdbcPassword ).add( systemId ),
			tips = $( ".validateTips" );

		function updateTips( t ) {
			tips.text( t )
				.addClass( "ui-state-highlight" );
		}
		
		function checkDataName(o,m,n){
			var result= false;	
			var url_="./checkDataName.html";
			 $.ajaxSetup({async:false});
			jQuery.post(url_,{"datasourceName":o.val().replace(/(^\s*)|(\s*$)/g,''),"datasourceId":m.val()},function(e){
				if(e.nameCount >= 1){
					updateTips( n + "不能重复");
					result=false;
				}else{
					result = true;
				}
			});
			return result;
		}
		
		function checkNull(o, n){
			var l = o.val().length;
			if(o.val().replace(/(^\s*)|(\s*$)/g,'')=="" || o.val().replace(/(^\s*)|(\s*$)/g,'')==null){
				updateTips( n + "不能为空 ");
				return false;
			}
			return true;
		}
		
		function checkLength( o, n, min, max ) {
			var l = o.val().length;
			if ( l > max || l < min ) {
				o.addClass( "ui-state-error" );
				if(o.val()=="" || o.val()==null){
					updateTips( n + "不能为空 ");
				}else{
					updateTips( n + "的长度必须在【" +
					min + " ~ " + max + "】之间" );
				}
				return false;
			} else {
				return true;
			}
		}
			
		function checkRegexp( o, regexp, n ) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips( n );
				return false;
			} else {
				return true;
			}
		}
		
		$( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 350,
			width: 550,
			resizable: false,
			modal: true,
			draggable: false,
			buttons: {
				"测试连接": function(){
					var url_= "./datasourceTest.html"
				    jQuery.post(url_,{"dataSourceName":datasourceName.val().replace(/(^\s*)|(\s*$)/g,''),"jdbcSpecific":jdbcSpecific.find('option:selected')[0].value,
				    	"jdbcUrl":jdbcUrl.val().replace(/(^\s*)|(\s*$)/g,''),"jdbcUserName":jdbcUserName.val(),"jdbcPassword":jdbcPassword.val(),"systemId":systemId.find('option:selected').val()},function(e){
							if(e.status==1){
								updateTips('测试成功!');
								$("#result").val("true");
							}else{
								updateTips('测试失败!');
							}	 
						});
					
				},
				"保存": function() {
					if($("#result").val()=="false"){
						asyncbox.confirm('数据连接测试未成功，是否继续保存？','数据源编辑',function(action){
							if(action == 'ok'){

						updateTips('');
						var bValid = true;
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checkLength( datasourceName, "数据源名称", 1, 30);
						bValid = bValid && checkNull( datasourceName, "数据源名称" );
						bValid = bValid && checkLength( jdbcSpecific, "数据库类型",1, 50 );
						bValid = bValid && checkNull( jdbcUrl, "数据库连接" );
						bValid = bValid && checkNull( jdbcPassword, "密码" );
						bValid = bValid && checkNull( jdbcUserName, "用户名" );
						bValid = bValid && checkLength( jdbcUserName, "用户名", 1, 50 );
						bValid = bValid && checkLength( systemId, "提供系统", 1, 32 );
						//bValid = bValid && checkRegexp( jdbcPassword, /^([0-9a-zA-Z])+$/, "密码请输入字母或数字 : a-z 0-9" );
						bValid = bValid && checkDataName( datasourceName,datasourceId, "数据源名称" );
						if ( bValid ) {
							 var url_= "./datasourceAdd.html"
							 jQuery.post(url_,{"datasourceName":datasourceName.val().replace(/(^\s*)|(\s*$)/g,'') ,"jdbcSpecific":jdbcSpecific.find('option:selected').val(),
								 "jdbcUrl":jdbcUrl.val().replace(/(^\s*)|(\s*$)/g,''),"datasourceId":$("#datasourceId").val(),"jdbcUserName":jdbcUserName.val().replace(/(^\s*)|(\s*$)/g,'') ,
								 "jdbcPassword":jdbcPassword.val(),"systemId":systemId.find('option:selected').val(),"updateFlg":$("#updateFlg").val(),"updateCount":$("#updateCount").val()},function(e){
									 if(e.status==1){
									 $("#updateFlg").val("");
									 $("#dialog-form").dialog( "close" );
									 asyncbox.alert("保存成功！","提示信息");
									 $("#seachBtn").trigger("click");
								 }else{
									 $("#updateFlg").val("");
									 updateTips("保存失败");
								 }
							 });
							
						}
					
						
						}})
					}else{
						updateTips('');
						var bValid = true;
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checkLength( datasourceName, "数据源名称", 1, 30);
						bValid = bValid && checkNull( datasourceName, "数据源名称" );
						bValid = bValid && checkLength( jdbcSpecific, "数据库类型",1, 50 );
						bValid = bValid && checkNull( jdbcUrl, "数据库连接" );
						bValid = bValid && checkNull( jdbcPassword, "密码" );
						bValid = bValid && checkNull( jdbcUserName, "用户名" );
						bValid = bValid && checkLength( jdbcUserName, "用户名", 1, 50 );
						bValid = bValid && checkLength( systemId, "提供系统", 1, 32 );
						//bValid = bValid && checkRegexp( jdbcPassword, /^([0-9a-zA-Z])+$/, "密码请输入字母或数字 : a-z 0-9" );
						bValid = bValid && checkDataName( datasourceName,datasourceId, "数据源名称" );
						if ( bValid ) {
							 var url_= "./datasourceAdd.html"
							 jQuery.post(url_,{"datasourceName":datasourceName.val().replace(/(^\s*)|(\s*$)/g,'') ,"jdbcSpecific":jdbcSpecific.find('option:selected').val(),
								 "jdbcUrl":jdbcUrl.val().replace(/(^\s*)|(\s*$)/g,''),"datasourceId":$("#datasourceId").val(),"jdbcUserName":jdbcUserName.val().replace(/(^\s*)|(\s*$)/g,'') ,
								 "jdbcPassword":jdbcPassword.val(),"systemId":systemId.find('option:selected').val(),"updateFlg":$("#updateFlg").val(),"updateCount":$("#updateCount").val()},function(e){
									 if(e.status==1){
									 asyncbox.alert("保存成功！","提示信息",function(action){
										 if(action == 'ok'){
											 $("#updateFlg").val("");
											 $("#dialog-form").dialog( "close" );
											 $("#seachBtn").trigger("click");
										 }
									 });
								 }else{
									 $("#updateFlg").val("");
									 updateTips("保存失败");
								 }
							 });
							
						}
					}
					
				},
				"取消": function() {
					allFields.val( "" ).removeClass( "ui-state-error" );
					$(".validateTips").text('').removeClass( "ui-state-highlight");
					$( this ).dialog( "close" );
				}
			},
			"close": function() {
				$(".validateTips").text('').removeClass( "ui-state-highlight");
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});
		$("#addBtn")
			.click(function() {
				$( "#dialog-form" ).dialog( "open" );
			});
	});
	
	function doSearch(){
		var form = document.DataForm;
		var systemId = $("#sysName").find("option:selected").val();
	
		form.action =  "./datasourcelist.html";
		form.submit();
	}
	
	document.onkeydown = function(e){
	       if(!e) e = window.event;
	       if((e.keyCode || e.which) == 13){
	    	   doSearch();
	       }
	}
	
	function getDbUrl(obj){
		
		var objUrl=document.getElementById("jdbcUrl");  
        objUrl.value=obj; 
	}
	
	function deleteRow(obj,updateCount,aObj){	
		var trObj = $(aObj).parent().parent();
		trObj.css("background","#fab5b5");
		asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
			 if(action == "ok"){
			var url_= "./datasourceDelete.html";
			    jQuery.post(url_,{"datasourceId":obj,"updateCount":updateCount},function(e){
			    	if(e.count>0){
			    		asyncbox.alert("此数据源在术语同步列表中引用，不可删除！","提示信息");
			    		return;
			    	}else if(e.status==1){
			    		asyncbox.alert("删除成功！","提示信息");
			    		doSearch();
			    	}else{
			    		asyncbox.alert("删除失败！","提示信息");
			    	}
			    });
		}else{
			trObj.css("background","");
		}
	})
}
	
	function updateDatasource(obj){
		var url_= "./datasourceUpdate.html";
		jQuery.post(url_,{"datasourceId":obj},function(e){
			$("#datasourceName").val(e.datasourceSet.datasourceName);
			$("#jdbcSpecific").attr("value",e.datasourceSet.jdbcSpecific);
			$("#jdbcUrl").val(e.datasourceSet.jdbcUrl);
			$("#jdbcUserName").val(e.datasourceSet.jdbcUserName);
			$("#jdbcPassword").val(e.datasourceSet.jdbcPassword);
			$("#sysName").attr("value",e.datasourceSet.systemId);
			$("#datasourceId").val(e.datasourceSet.datasourceId);
			$("#updateCount").val(e.datasourceSet.updateCount);
			$("#updateFlg").val("update");
			$( "#dialog-form" ).dialog( "open" );
		});
		
	}
	