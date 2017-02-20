	$(function(){
		if($("#result").val()=="1"){
			asyncbox.success("操作成功！","结果反馈");
			$("#result").val()
		}else if($("#result").val()=="0" || $("#result").val()=="-1"){
			asyncbox.error("操作失败，请联系管理员！","结果反馈");
		}
		
		$('#noticeMethod').change(function(obj){ 
			if($(this).val()!=""){
				$("#noticeMethodValue").css("background","white");
				$("#noticeMethodValue").attr("readOnly",false);
			}else{
				$("#noticeMethodValue").css("background","#cbcbcb");
				$("#noticeMethodValue").attr("readOnly",true);
				$("#noticeMethodValue").val("");
			}
		}) 
			
		$("#addWs").click(function(){
			$("#opt").val("add");
			$("#wsForm").attr("action","../sysmgt/wsOperateView.html").submit();
		})		
					
		$("#seachBtn").click(function(){
			$("#wsForm").attr("action","../sysmgt/wsList.html").submit();
		})
		
		$("#cleanBtn").click(function(){
			var url_ = "../sysmgt/wsList.html";
			location.href=url_;
		})
		
		
		$("#cardBtn").click(function() {
			asyncbox.open({
				id : "chooseCard",
				modal : true,
				drag : true,
				scrolling :"no",
				title : "",
				url : "../sysmgt/userSelectList.html",
				width : 700,
				height : 404,
			})
		})
		
		var dictDepartmentList = eval('(' + $('#temp').val() + ')');
		var dictDepartmentArr = new Array(dictDepartmentList.length);
		for(var i=0;i<dictDepartmentList.length;i++){
			var tempArr = new Array(2);
			tempArr[0] = dictDepartmentList[i].name;
			tempArr[1] = dictDepartmentList[i].code;
			dictDepartmentArr[i] = tempArr;
		}
		
		$( "#unitName" ).autocomplete({
		      data: dictDepartmentArr,
		      onItemSelect: function(item) {
		          $( "#unitName" ).val( item.value );
		          $( "#unitId" ).val( item.data );
		      }
		});
	})			

	function editWS(editId) {
		$("#opt").val("edit");
		$("#settingId").val(editId);
		$("#wsForm").attr("action","../sysmgt/wsOperateView.html").submit();
	}

	function CallbackRefresh(obj) {
		if (obj.value) {
			$("#searchBtn").click();
		}
	}

	function deleteWS(settingId,obj) {
		//asyncbox.alert('欢迎使用 jQuery.AsyncBox 弹窗插件','asyncbox_Title');
		//asyncbox.success('dddd','执行结果'); 
		//asyncbox.error('dddd','执行结果');
		//asyncbox.prompt('dddd','执行结果');
		
		var trObj = $(obj).parent().parent();
		trObj.css("background","#fab5b5");
		asyncbox.confirm("确定要删除这条记录吗？","提示",function(action){
	        if(action == "ok"){
	        	$("#settingId").val(settingId);
				$("#opt").val("delete");
				$("#wsForm").attr("action","../sysmgt/wsOperate.html").submit();
	        }else{
	        	trObj.css("background","");
	        }
		})
	}
	