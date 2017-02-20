jQuery(function($){
	$("#initHospital").val("");
	$("input[type='text']").keyup(function(event){
		if(event.keyCode=='32'){
			var v = $(this).val();
			v = v.replace(/(^\s*)|(\s*$)/g, "");
			$(this).val(v);
		}
	});
	
	$("#addBtn").click(function(){
		var hospitalCode=$('#hospitalCode').find('option:selected').val();
		if(hospitalCode==""){
			alert("请先选择医疗机构");
			return;
		}
		var url_  = "../subscribe/selectUnit.html?hospitalCode="+hospitalCode;
		$("#dataIframe").attr("src",url_);
		$("#dialog-form").dialog("open");
	});
	$("#deleteBtn").click(function(){
		var selectes = $(".tbody input[type='checkbox']:checked");
		if(selectes.length<1){
			alert("请选择一条记录操作");
			return ;
		}
		if(confirm("确认要删除选中记录吗")){
			selectes.parent().parent().remove();
			if($("#parentBox")[0].checked){
				$("#parentBox")[0].checked=false;
			}
		}
		
	});
	var unitGroupId = $( "#unitGroupId" ),
	unitGroupName = $( "#unitGroupName" ),
	hospitalCode = $( "#hospitalCode" ),
	unitGroupType = $( "#unitGroupType" ),
	unitGroupDesc = $( "#unitGroupDesc" ),
	selectes = $(".tbody input[type='checkbox']"),
	allFields = $( [] ).add( unitGroupId ).add( unitGroupName ).add( hospitalCode ).add(unitGroupType).add(unitGroupDesc),
	tips = $( ".validateTips" );

function updateTips( t ) {
	$("#unitGroup").text(t).addClass( "ui-state-highlight" );
}
function updateTipsUnit( t ) {
	$("#unit").text(t).addClass( "ui-state-highlight" );
}
function checkLength( o, n, min, max ) {
	if(o.val()=="" || o.val()==null){
		updateTips( n + "不能为空 ");
		return false;
	}
	var l = o.val().length;
	if ( l > max || l < min ) {
		updateTips( n + "的长度必须在【" + min + " ~ " + max + "】之间" );
		return false;
	} else {
		return true;
	}
}
function checkNull(o, n){
	var l = o.val().length;
	if(o.val().replace(/(^\s*)|(\s*$)/g,'')=="" || o.val().replace(/(^\s*)|(\s*$)/g,'')==null){
		updateTips( n + "不能为空 ");
		return false;
	}
	return true;
}
function checkSelect(o){
	if(o.length<1){
		updateTipsUnit("请至少选择一个科室");
		return false ;
	}
	return true;
}
/*function checkRepeatSelect(o){
	for(var i=0;i<o.length;i++){
		for(var j=0;j<o.length;j++){
			var unitId=o.parent().parent()[i].childNodes[2].innerText;
			var newUnitId =o.parent().parent()[j].childNodes[2].innerText;
			if(i!=j && unitId == newUnitId){
				updateTipsUnit("选择的科室中有重复科室，请确认！");
				return false ;
			}
		}
		
	}
	return true;
}*/
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
function checkRegexp( o, regexp, n ) {
	if ( !( regexp.test( o.val() ) ) ) {
		o.addClass( "ui-state-error" );
		updateTips( n );
		return false;
	} else {
		return true;
	}
}
$("#saveBtn").click(function(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );
	$(".validateTips").each(function(){
		$("#unitGroup").removeClass("ui-state-highlight");
		$("#unitGroup").text('');
		$("#unit").removeClass("ui-state-highlight");
		$("#unit").text('');
	});
	
	var unitGroupId = $( "#unitGroupId" ),
	unitGroupName = $( "#unitGroupName" ),
	hospitalCode = $( "#hospitalCode" ),
	unitGroupType = $( "#unitGroupType" ),
	unitGroupDesc = $( "#unitGroupDesc" );
	
	bValid = checkNull(unitGroupType.find('option:selected'),"科室组类别") && bValid;
	bValid = checkLength( unitGroupName, "科室组名称",0,50 ) && bValid;
	bValid = checkLength(unitGroupId, "科室组ID",0,6 ) && bValid;
	if($("#flag").val()==0){
		bValid = checkUnitGroupExist(unitGroupId) && bValid;
		bValid = bValid && checkRegexp( unitGroupId, /^([0-9a-zA-Z])+$/, "科室组ID请输入字母或数字 : a-z 0-9" );
	}
	bValid = checkSelect( $(".tbody input[type='checkbox']") ) && bValid;
	//alert(bValid);
	if(bValid){
		var id =$("#id").val(),
		unitGroupId = $( "#unitGroupId" ).val(),
		unitGroupName = $( "#unitGroupName" ).val(),
		hospitalCode = $( "#hospitalCode" ).find('option:selected').val(),
		unitGroupType = $( "#unitGroupType" ).find('option:selected').val(),
		unitGroupDesc = $( "#unitGroupDesc" ).val(),
		flag = $("#flag").val();
		selects = $(".tbody input[type='checkbox']");
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
	}
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
