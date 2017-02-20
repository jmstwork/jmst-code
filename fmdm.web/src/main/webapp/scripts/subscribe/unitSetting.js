$(function(){
	$("#addBtn").click(function(){
		var hospitalCode = $('#hospitalCode').val();
		if(hospitalCode==""){
			asyncbox.alert("无效数据，该记录的医疗机构为空！","提示信息");
			return;
		}
		
		asyncbox.open({
			id : "chooseCard",
			modal : true,
			drag : true,
			scrolling:"no",
			title : "",
			url : "../subscribe/selectUnit.html?hospitalCode="+hospitalCode,
			width : 700,
			height : 422,
			btnsbar: [{
			     text    : '保存', 
			     action  : 'save'
			},{
				 text    : '关闭', 
			     action  : 'close_1'
			}],
			callback : function(action,opener){
				if(action == 'save'){
					var obj = opener.$('input[name="childrenBox"]:checked');
					var ids = new Array();
					obj.each(function(i){
						var id = obj[i].id;
						ids.push(id);
					})
					var unitIds = ids.join(',');
					if(unitIds==""){
						asyncbox.alert("请选择一条记录操作","提示信息");
						return;
					}
					var url  = "../subscribe/saveUnit.html";
					$.post(url,{"unitGroupId":$("#unitGroupId").val(),"unitIds":unitIds},function(data){
						if(data.status == '1'){
							asyncbox.success("保存成功！","执行结果",function(){
								var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
								location.href=url_;
							});
						}else if(data.status == '2'){
							asyncbox.alert("不可重复添加科室信息！","执行结果",function(){
								var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
								location.href=url_;
							});
						}else{
							asyncbox.error("保存失败！","执行结果",function(){
								var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
								location.href=url_;
							});
						}
					});
				}
			}
		})
	});
	
	$("#seachBtn").click(function(){
		$("#DataForm3").submit();
	})
	
	$("#cleanBtn").click(function(){
		$("#unitId").val("");
		$("#unitName").val("");
		var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
		location.href=url_;
	})
	
	$("#backBtn").click(function(){
		var url_ = "../subscribe/unitGroupList.html";
		location.href=url_;
	})
	
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			asyncbox.alert("请选择一条记录操作","提示信息");
			return;
		}
		
		asyncbox.confirm("确认要删除选中记录吗?","确认信息",function(action){
			if(action == "ok"){
				var ids = new Array();
				selectes.each(function(i){
					ids.push($(this).parent().parent().children()[2].innerText);
				})
				
				var unitIds = ids.join(",");
				/*$("#unitIds").val(unitIds);
				$("#DataForm3").attr("action","../subscribe/deleteUnit.html").submit();*/
				
				$.post("../subscribe/deleteUnit.html",{"unitGroupId":$("#unitGroupId").val(),"unitIds":unitIds},function(data){
					if(data.status == '1'){
						asyncbox.success("删除成功！","执行结果",function(){
							var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
							location.href=url_;
						});
					}else{
						asyncbox.error("删除失败！","执行结果",function(){
							var url_ = "../subscribe/unitSetting.html?editUnitGroupId="+$("#unitGroupId").val();
							location.href=url_;
						});
					}
				});
				/*selectes.parent().parent().remove();
				if($("#parentBox")[0].checked){
					$("#parentBox")[0].checked=false;
				}*/
			}
		})
	});
	
	function checkUnitGroupExist(o){
		var b = true;
		var url_ = "../subscribe/checkUnitGroupExist.html";
		$.ajaxSetup({ async : false }); 
		var unitGroupId= o.val();
		$.post(url_,{"unitGroupId":unitGroupId},function(e){
			if(e.status == '1'){
				updateTips("该科室组ID已被注册");
				b = false;
			}
		});
		return b;
	}

	$("#saveBtn").click(function(){
		
			var ids = new Array();
			for(var i=0;i<selects.length;i++){
				var unitId=selects.parent().parent()[i].cells[2].innerText;
				ids.push(unitId);
			}
			var unitIds = ids.join(',');
			var url  = "../subscribe/saveUnitGroup.html";
			$.post(url,{"id":id,"unitGroupId":unitGroupId,"unitGroupName":unitGroupName,"hospitalCode":hospitalCode,"unitGroupType":unitGroupType,
				"unitGroupDesc":unitGroupDesc,"unitIds":unitIds,"flag":flag},function(data){
				if(data.status == '1'){
					alert("保存成功！");
					var url_ = "../subscribe/unitGroupList.html";
					location.href=url_;
				}else{
					alert("保存失败！");
				}
			});
	});
		$("#dialog-form" ).dialog({
			autoOpen: false,
			height: 480,
			width: 800,
			resizable: false,
			modal: true,
			draggable: false
		});
	});

	function unitGroupDetail(unitGroupId){
		var url_ = "../subscribe/unitGroupDetail.html?unitGroupId="+unitGroupId;
		//location.href=url_;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
	}

	function setData(unitIds,hospitalCode){
		var unitIdArry=new Array();
		unitIdArry = unitIds.split(',');
		var selects = $(".tbody input[type='checkbox']");
		var repeat=""
		for(var i=0;i<selects.length;i++){
			for(var j=0;j<unitIdArry.length;j++){
				var unitId=selects.parent().parent()[i].childNodes[2].innerText;
				if(unitId == unitIdArry[j]){
					if(repeat ==""){
						repeat=unitId;
					}else{
						repeat+=","+unitId;
					}
				}
			}
		}
		if(repeat!=""){
			alert("已选择科室列表中已存在以下科室："+repeat+"  请重新确认");
			return;
		}
		var url  = "../subscribe/selectUnits.html?unitIds="+unitIds+"&hospitalCode="+hospitalCode;
		$.post(url,function(data){
			var unitGroupDetailList=data.unitGroupDetailList;
			for(var i=0;i<unitGroupDetailList.length;i++){
				var unitGroupDetail=unitGroupDetailList[i];
				var rowcnt = $("table[id$='unitTable']>tbody").children("tr").length+1;
				var r = jQuery(".tbody").children().length;
				var tr = "<tr>";
				var td = "";
				td = td +"<td align='center'>"+"<input type='checkbox' id='childrenBox' name='childrenBox' onclick='javascript:checkChildren();'/>"+"</td>";
				td = td +"<td align='center'>"+rowcnt+"</td>";
				td = td +"<td>"+unitGroupDetail.unitId+"</td>";
				td = td +"<td>"+unitGroupDetail.unitName+"</td>";
				td = td +"<td>"+unitGroupDetail.pyCode+"</td>";
				tr = tr + td + "</tr>";
				jQuery(".tbody").append(tr);
			}
			alert("已选择成功。");
		});
	}
	
	function changeHospital(obj){
		var beforeObj=$("#initHospital").val();
		if(beforeObj!=""&&obj!=beforeObj){
			var checkBox=$(".tbody input[type='checkbox']");
			if(checkBox.length>0){
				if(confirm("更换医疗机构已选择科室将会全部删除，请确认是否继续")){
					$("#initHospital").val(obj)
					for(var i=0;i<checkBox.length;i++){
						checkBox[i].checked=true;
					}
					$("#deleteBtn").click();
				}else{
					$("#hospitalCode").attr("value",beforeObj);;
					$("#initHospital").val(beforeObj);
				}
			}
		}else{
			$("#initHospital").val(obj);
		}
	}
