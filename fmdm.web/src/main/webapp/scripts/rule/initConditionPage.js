var num = 1;
var t = 1 ;
var exe ;
var isCon;

$(function(){
	exe = $("#exe").val();
	var desc = $("#descFirst").val();
    var optionList = ""; //获取规则名称option值
    $("#item option").each(function () {
        var txt = $(this).text(); //获取单个text
        var val = $(this).val(); //获取单个value
        var node = "<option value=\"" + val + "\">" + txt + "</option>";
        optionList += node;
    });
	isCon = $("#isCon").val();
	if(isCon==2){
		jQuery("#ziyou").click();
		jQuery("#desc2").val(desc);
		jQuery("#desc2").attr("disabled","disabled");
		jQuery("#context").val(exe);
	}else{
		if(exe!="" && exe!='1'){
			var arr = exe.split("#");
			for(var i=0;i<arr.length;i++){
				if(i%2==0){
					var temp = arr[i].split(":");
					var compare = temp[1];
					if(compare == ">"){
						compare ="gt";
					}
					if(compare == "<"){
						compare ="lt";
					}
					if(compare == ">="){
						compare ="gte";
					}
					if(compare == "<="){
						compare ="lte";
					}
					if(i==0){
						//将页面本身的一个公式赋值
						jQuery("#desc").val(desc);
						//jQuery("#desc").css("border","0");
						jQuery("#desc").attr("disabled","disabled");
						jQuery("#item").val(temp[0]);
						jQuery("#compare").val(compare);
						jQuery("#input_value").val(temp[2]);
					}else{
						//在现有页面后追加字段
						var htmlText = '<tr id="con_'+num+'"><td align="right">字段名</td><td><select name="con_item" id="con_sel_item_'+num+'">'+optionList+'</select>&nbsp;&nbsp;&nbsp;&nbsp;'+
						'<select name="con_c" id="con_sel_c_'+num+'"><option value="==" >等于</option><option value="gt" >大于</option><option value="lt" >小于</option><option value="gte" >大于等于</option><option value="lte" >小于等于</option><option value="!=" >不等于</option></select>&nbsp;&nbsp;&nbsp;&nbsp;<input id="con_input_'+num+'" name="con_v" maxlength="10" type="text" value="'+temp[2]+'" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:addCond(this);" class="Linkblue" target="_self">添加</a>&nbsp;&nbsp; <a href="#" onclick="javascript:deleteConditiion('+num+');" class="Linkblue" target="_self">删除</a></td></tr>';
						jQuery("#condition").append(htmlText);
						jQuery("#con_sel_item_"+num).val(temp[0]);
						jQuery("#con_sel_c_"+num).val(compare);
					}
				}else{
					//追加逻辑运算符&& ||
					var htmlText = '<tr id="com_'+(++num)+'"><td align="right">逻辑</td><td><select name="com" id="com_sel_'+num+'"><option value="&&" >与</option><option value="||" >或</option></select></td></tr>';
					jQuery("#condition").append(htmlText);
					jQuery("#com_sel_"+num).val(arr[i]);
				}
			}
		}
	}
});
function changeTab(v){
	if(v==2){
		jQuery("#freeEdit").show();
		jQuery("#selectEdit").hide();
	}else{
		jQuery("#freeEdit").hide();
		jQuery("#selectEdit").show();	
	}
	jQuery("#t").val(v);
	t=v;
}
function addCond(obj){
	var optionList = ""; //获取规则名称option值
	//var length = jQuery("#item").length;
	jQuery("#item option").each(function () {
        var txt = $(this).text(); //获取单个text
        var val = $(this).val(); //获取单个value
        var node = "<option value=\"" + val + "\">" + txt + "</option>";
        optionList += node;
    });
	/*jQuery("#item").children("option").each(function(){
		var txt = $(this).text(); //获取单个text
        var val = $(this).val(); //获取单个value
        var node = "<option value=\"" + val + "\">" + txt + "</option>";
        optionList += node;
	});*/
//    alert(optionList);
	num ++;
	var htmlText = '<tr id="com_'+num+'"><td align="right">逻辑</td><td><select name="com" id="com_sel_'+num+'"><option value="&&">与</option><option value="||">或</option></select></td></tr>'+
	'<tr id="con_'+num+'"><td align="right">字段名</td><td><select name="con_item" id="con_sel_item_'+num+'">'+optionList+'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="con_c" id="con_sel_c_'+num+'"><option value="==">等于</option><option value="gt">大于</option><option value="lt">小于</option><option value="gte">大于等于</option><option value="lte">小于等于</option><option value="!=">不等于</option></select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="con_input_'+num+'" maxlength="10" name="con_v" type="text" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:addCond(this);" class="Linkblue" target="_self">添加</a>&nbsp;&nbsp; <a href="#" onclick="javascript:deleteConditiion('+num+');" class="Linkblue" target="_self">删除</a></td></tr>';
	
	jQuery("#condition").append(htmlText);
}
function deleteConditiion(n){
	jQuery("#com_"+n).remove();
	jQuery("#con_"+n).remove();
}

function submitOk(){	
	if(t==2){
		desc = jQuery("#desc2").val();
		window.parent.descMain = desc;
		window.parent.isCon = 1;
		window.parent.content="";
		window.parent.content=jQuery("#context").val();
	}else{
		desc = jQuery("#desc").val();
		var item_sel =jQuery("#item").val();
		var compare = changeCompare(jQuery("#compare").val());
		var input_value = jQuery("#input_value").val();
		
		if(jQuery("#item").children()[jQuery("#item")[0].selectedIndex].id=="String" || jQuery("#item").children()[jQuery("#item")[0].selectedIndex].id =="Date"){
			if(window.parent.specialItem == ""){
				window.parent.specialItem = item_sel;
			}else{
				window.parent.specialItem += ":"+item_sel;
			}
		}
		if(desc.replace(/(^\s*)|(\s*$)/g, "")==""){
			asyncbox.error("描述为空添加失败");
			return false;
		}
		window.parent.comArr.length =0;
		window.parent.itemArr.length =0;
		window.parent.conArr.length =0;
		window.parent.valArr.length =0;
		
		window.parent.descMain = null;
		window.parent.item_selMain = null;
		window.parent.compareMain = null;
		window.parent.valueMain = null;
		
		var com = jQuery("#selectEdit select[name='com']");
		var con_item = jQuery("#selectEdit select[name='con_item']");
		var con_c = jQuery("#selectEdit select[name='con_c']");
		var con_v = jQuery("#selectEdit input[name='con_v']");
		
		for(var i=0;i<com.length;i++){
			window.parent.comArr.push(com[i].value);
			window.parent.itemArr.push(con_item[i].children[con_item[i].selectedIndex].value);
			if(con_item[i].children[con_item[i].selectedIndex].id=="String" || con_item[i].children[con_item[i].selectedIndex].id=="Date"){
				if(window.parent.specialItem == ""){
					window.parent.specialItem = con_item[i].children[con_item[i].selectedIndex].value;
				}else{
					window.parent.specialItem += ":"+con_item[i].children[con_item[i].selectedIndex].value;
				}
			}
			window.parent.conArr.push(changeCompare(con_c[i].value));
			window.parent.valArr.push(con_v[i].value);
		}
		window.parent.descMain = desc;
		window.parent.item_selMain = item_sel;
		window.parent.compareMain = compare;
		window.parent.valueMain = input_value;
		window.parent.isCon = 1;
	}
	window.parent.flag = t;
	if(exe!=null && exe!=''){
		window.parent.editConditionExe(isCon);
	}else{
		window.parent.addRuleCondition();
	}
	//asyncbox.close('setCondition');
}
function setValue(){
	if(window.parent.document.getElementById("flag").value == 1){
		$("#desc").val(window.parent.descMain);
		$("#input_value").val(window.parent.valueMain);
	}
}
function changeCompare(compare){
	if(compare == "gt"){
		compare =">";
	}
	if(compare == "lt"){
		compare ="<";
	}
	if(compare == "gte"){
		compare =">=";
	}
	if(compare == "lte"){
		compare ="<=";
	}
	return compare;
}
