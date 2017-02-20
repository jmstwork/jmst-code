$(function(){
	
	//返回按钮操作
	$("#backBtn").click(function(){
		$("#tempForm").attr("action","../sysmgt/wsList.html").submit();
	})
	
	//重置按钮操作
	$("#resetBtn").click(function(){
		$("#resetOpt").val("add");
		$("#tempForm").attr("action","../sysmgt/wsOperateView.html").submit();
	})
	
	var dictDepartmentList = eval('(' + $("#temp").val() + ')');
	var dictDepartmentArr = new Array(dictDepartmentList.length);
	
	for(var i=0;i<dictDepartmentList.length;i++){
		var tempArr = new Array(2);
		tempArr[0] = dictDepartmentList[i].name;
		tempArr[1] = dictDepartmentList[i].code;
		dictDepartmentArr[i] = tempArr;
	}
	
	$("#unitName").autocomplete({
		      data: dictDepartmentArr,
		      onItemSelect: function(item) {
		    	   $( "#unitName").val( item.value );
			       $( "#unitId").val( item.data );
		      }
	});
	
	
	$("#systemSet").click(function() {
		if ($("#systemSetMethod").attr("checked")) {
			var sysChoosed = document.getElementById("sysIds").value;
			asyncbox.open({
				id : "chooseSys",
				modal : true,
				drag : true,
				scrolling :"no",
				title : "",
				url : "../sysmgt/systemSelectList.html?sysChoosed="+ sysChoosed,
				width : 700,
				height : 414,
				btnsbar: [{
				     text    : '选择', 
				     action  : 'save'
				},{
					 text    : '关闭', 
				     action  : 'close_1'
				}],
				callback : function(action,opener){
					if(action == 'save'){
						//1.获取被选择的记录
						/*var sysIds = $("#sysIds").val(); 
						var sysNames = $("#sysNames").val();*/
						var sysIds = "";
						var sysNames = "";
						var obj = opener.$('input[name="childrenBox"]:checked');
						
						var unChecked = opener.$('input[name="childrenBox"]').not("input:checked");
						
						//获取未被选中的checkbox情况
						var unSysId = "";
						var unSysName = "";
						unChecked.each(function(i){
							var id = unChecked[i].parentElement.parentElement.childNodes[2].innerText;
							var name = unChecked[i].parentElement.parentElement.childNodes[3].innerText;
							if(sysIds.indexOf(","+id)!=-1){
								sysIds = sysIds.replace(","+id,"");
							}else if(sysIds.indexOf(id)!=-1){
								sysIds = sysIds.replace(id,"");
							}
							
							if(sysNames.indexOf(","+name)!=-1){
								sysNames = sysNames.replace(","+name,"");
							}else if(sysNames.indexOf(name+",")!=-1){
								sysNames = sysNames.replace(name+",","");
							}
						})
						
						//获取选中的checkbox情况
						obj.each(function(i){
							var id = obj[i].parentElement.parentElement.childNodes[5].innerText;
							var name = obj[i].parentElement.parentElement.childNodes[7].innerText;
							if(sysIds.indexOf(id)==-1){
								if($("#sysIds").val()!=""){
									sysIds += "," + id;
									sysNames += "," + name;
								}else{
									sysIds += id +",";
									sysNames += name + ",";
								}
								
							}
						})
						
						var ids = ""
						var names = "";
						if($("#sysIds").val()!=""){
							ids = sysIds.substring(0,sysIds.length);
							names = sysNames.substring(0,sysNames.length);
						}else{
							ids = sysIds.substring(0,sysIds.length-1);
							names = sysNames.substring(0,sysNames.length-1);
						}
						$("#sysIds").val(ids);
						$("#sysNames").val(names);
						document.getElementById("systemSetResult").innerHTML = "所选系统：" + names;
					}
				}
			});
		}
	})
	
	
	$("#cardBtn").click(function() {
		asyncbox.open({
			id : "chooseCard",
			modal : true,
			drag : true,
			title : "",
			scrolling :"no",
			url : "../sysmgt/userSelectList.html",
			width : 700,
			height : 408,
		})
	})
	
	//保存按钮
	$("#saveBtn").click(function(){
		var empty = false;
		var unitId = "";
		var state = false;
		var isOk = false;
		if ("add" == $("#opt").val()) {
			//1.科室
			unitId = $("#unitId").val();
			var unitName = $("#unitName").val().replace(/(^\s*)|(\s*$)/g,"");
			
			//2.接收者
			var userNo = $("#userNo").val();
			
			//3.规则组
			var gcObj = $("#groupCode option:selected");
			var gcValue = gcObj.val();
			var gcName = gcObj.text(); 
			
			//4.对象类型
			var utObj = $("#userType option:selected");
			var utValue = utObj.val();
			var utName = utObj.text(); 
			
			//判断一：非空判断
			var errMsg = "";
			if(unitId==""&&unitName==""){
				errMsg = "[科室部门]";
				empty=true;
			}
			if(userNo==""){
				errMsg += " [接收者]";
				empty=true;
			}
			if(gcValue==""){
				errMsg += " [规则组]";
				empty=true;
			}
			if(utValue ==""){
				errMsg += " [对象类型]";
				empty=true;
			}
			
			//判断1：非空校验
			if(empty){
				errMsg += "不能为空!";
				$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + errMsg);
				$(".panel").css("background-color","#fefcda");
				$(".panel").slideDown("slow");
				state = true;
				return;
			}else{
				isOK = true;
			}
			
			//判断2：科室部门不存在校验
			if(unitId=="" && unitName!=""){
				errMsg = "科室部门无效，不能直接输入，请点击选择已存在的科室部门！"
				showWarnMsg(state,errMsg);
				state = true;
				return;
			}else{
				isOK = true;
			}
			
		}else if(opt=="edit"&&userTypeValue=="4"){
			unitId = $("#unitId").val();
			unitName = $("#unitName").val();
			//用于记录日志
			//departName = deptObj.options[deptObj.selectedIndex].text;
		}
		
		var tel = $("#tel").val().replace(/(^\s*)|(\s*$)/g,"");
		var email = $("#email").val().replace(/(^\s*)|(\s*$)/g,"");
		//判断3：通知方式为空校验
		if(!$("#telMethod").is(':checked')&&!$("#emailMethod").is(':checked')&&!$("#systemSetMethod").is(':checked')){
			errMsg = "请至少选择一种通知方式！"
			showWarnMsg(state,errMsg);
			state = true;
			return;
		}else{
			isOK = true;
		}
		
		//判断4：手机号为空校验
		if($("#telMethod").is(':checked')&&tel==""){
			errMsg = "请填写手机号！"
			showWarnMsg(state,errMsg);
			state = true;
			return;
		}else{
			isOK = true;
		}
		
		//判断5：手机号规范校验
		if($("#telMethod").is(':checked')&& !/^1\d{10}$/.test(tel)){
			errMsg = "手机号不符合规范，请重新填写！"
			showWarnMsg(state,errMsg);
			$("#tel").val("");
			state = true;
			return;
		}else{
			isOK = true;
		}

		var receiveDays = "";
		for ( var i = 1; i <= 7; i++) {
			if ($("#day_"+i).is(":checked")) {
				receiveDays += ":" + (i);
			}
		}
		
		//判断6：接收时段为空校验
		if($("#telMethod").is(':checked') && receiveDays==""){
			errMsg = "请至少选择一个接收时段！"
			showWarnMsg(state,errMsg);
			state = true;
			return;
		}else{
			$("#receiveDays").val(receiveDays);
			isOK = true;
		}
		
		//判断7：邮箱地址为空校验
		if($("#emailMethod").is(':checked') &&email==""){
			errMsg = "请填写邮箱地址！"
			showWarnMsg(state,errMsg);
			state = true;
			return;
		}else{
			isOK = true;
		}
		
		//判断8：邮箱规范校验
		if($("#emailMethod").is(':checked') &&!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(email)){
			errMsg = "邮箱地址不规范，请重新填写！"
			showWarnMsg(state,errMsg);
			$("#email").val("");
			state = true;
			return;
		}else{
			isOK = true;
		}
		
		//判断7：邮箱地址为空校验
		if($("#systemSetMethod").is(':checked') &&$("#sysIds").val()==""){
			errMsg = "请至少选择一个系统！"
			showWarnMsg(state,errMsg);
			state = true;
			return;
		}else{
			isOK = true;
		}
		
		if(isOK){
			$(".panel").slideUp("slow");
			$("#opt").val("addSave");
			$("#addForm").attr("action","../sysmgt/wsOperate.html").submit();
		}
		
	})
	
})

function checkSelected() {
		var auth = '';
		var sysList = tbl.getSelectedRows();
		for ( var i = 0; i < sysList.size(); i++) {
			var system = sysList.item(i);
			if (i == 0) {
				auth = system.get("sysId");
			} else {
				auth = auth + ":" + system.get("sysId");
			}
		}
		return auth;
	}

function readOnlyOrNot(id,obj) {
	if ($(obj).attr("checked")) {
		if(id=="telMethod"){
			$("#tel").attr("disabled",false).css("background-color","");
			for (var i=0;i<=7;i++){
				$("#day_"+i).attr("disabled",false).css("background-color","");
			}
		}else if(id=="emailMethod"){
			$("#email").attr("disabled",false).css("background-color","");
		}else if(id=="systemSetMethod"){
			$("#systemSet").attr("disabled",false);
		}
	}else{
		if(id=="telMethod"){
			$("#tel").attr("disabled",true).css("background-color","#cbcbcb").val("");
			for (var i=0;i<=7;i++){
				$("#day_"+i).attr("disabled",true).css("background-color","#cbcbcb").removeAttr("checked");
			}
		}else if(id=="emailMethod"){
			$("#email").attr("disabled",true).css("background-color","#cbcbcb").val("");
		}else if(id=="systemSetMethod"){
			$("#systemSet").attr("disabled",true).css("background-color","#cbcbcb").val("");
			document.getElementById("systemSetResult").innerHTML = "";
			$("#sysNames").val("");
			$("#sysIds").val("");
		}
	}
}


function showWarnMsg(state,errMsg){
	if(state){
		$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + errMsg);
	}else{
		$(".panel").html("<img src='../images/msg/warn.png' style='vertical-align: top;'/> " + errMsg);
		$(".panel").css("background-color","#fefcda");
		$(".panel").slideDown("slow");
	}
}
