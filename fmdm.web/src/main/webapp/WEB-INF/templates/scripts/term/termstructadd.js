$(function(){
		$("#addfield").button().click(function() {
				$( "#dialog-form" ).dialog( "open" );
		});
		
		$( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 350,
			width: 450,
			resizable: false,
			modal: true,
			draggable: false,
			buttons: {
				"测试连接": function(){
					alert('OK!');
				},
				"提交": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );

					bValid = bValid && checkLength( databaseName, "数据源名称", 6, 30 );
					bValid = bValid && checkLength( dbType, "数据库类型", 1, 30 );
					bValid = bValid && checkLength( dbConn, "数据库连接", 8, 30 );
					bValid = bValid && checkLength( jdbc_user, "用户名", 8, 30 );
					bValid = bValid && checkLength( password, "密码", 8, 30 );
					bValid = bValid && checkLength( systemId, "提供系统", 1, 30 );

					bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

					if ( bValid ) {
						$( this ).dialog( "close" );
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
})