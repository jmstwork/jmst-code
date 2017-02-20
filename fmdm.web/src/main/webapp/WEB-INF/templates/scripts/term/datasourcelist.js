$(function() {
		var datasourceName = $( "#datasourceName" ),
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
			/*setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );*/
		}
		function checkNull(o, n){
			var l = o.val().length;
			if(o.val()=="" || o.val()==null){
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
				    jQuery.post(url_,{"dataSourceName":datasourceName.val(),"jdbcSpecific":jdbcSpecific.find('option:selected')[0].id,"jdbcUrl":jdbcUrl.val(),
							 "jdbcUserName":jdbcUserName.val(),"jdbcPassword":jdbcPassword.val(),"systemId":systemId.find('option:selected').val()},function(e){
								 alert(22);
							if(e.status==1){
								updateTips('测试成功!');
							}else{
								updateTips('测试失败!');
							}	 
						});
					
				},
				"提交": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );

					bValid = bValid && checkLength( datasourceName, "数据源名称", 1, 50 );
					bValid = bValid && checkNull( jdbcSpecific, "数据库类型" );
					bValid = bValid && checkNull( jdbcUrl, "数据库连接" );
					bValid = bValid && checkLength( jdbcUserName, "用户名", 1, 50 );
					bValid = bValid && checkLength( jdbcPassword, "密码", 1, 32 );
					bValid = bValid && checkLength( systemId, "提供系统", 1, 32 );

					bValid = bValid && checkRegexp( jdbcPassword, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

					if ( bValid ) {
						 var url_= "./datasourceAdd.html"
						 jQuery.post(url_,{"datasourceName":datasourceName.val(),"jdbcSpecific":jdbcSpecific.find('option:selected')[0].id,"jdbcUrl":jdbcUrl.val(),
							 "jdbcUserName":jdbcUserName.val(),"jdbcPassword":jdbcPassword.val(),"systemId":systemId.find('option:selected').val()},function(e){
							 if(e.status==1){
								 updateTips("保存成功");
								 $( this ).dialog( "close" );
							 }else{
								 updateTips("保存失败");
							 }
						 });
						
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
	
	function getDbUrl(obj){
		
		var objUrl=document.getElementById("jdbcUrl");  
        objUrl.value=obj; 
	}
	function deleteRow(obj){	
		var td =$(obj).parent().parent().children();
		var datasourceId = td[1].name;
		var url_= "./datasourceDelete.html";
		    jQuery.post(url_,{"datasourceId":datasourceId},function(e){
		    	if(e.status==1){
		    		alert("删除成功");
		    		doSearch();
		    		
		    	}else{
		    		alert("删除失败");
		    	}
		    });
	}
	function updateDatasource(obj){
		var td =$(obj).parent().parent().children();
		var datasourceId = td[1].name;
		var url_= "./updateDatasource.html";
		jQuery.post(url_,{"datasourceId":datasourceId},function(e){
			$( "#dialog-form" ).dialog( "open" );
		});
		
	}
	