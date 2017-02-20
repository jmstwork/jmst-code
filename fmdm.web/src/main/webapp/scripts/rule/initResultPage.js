$(function(){
	var exe = $("#exe").val();
	var desc = $("#initDesc").val();
	if (exe != '' && exe != '1') {
		var arr = exe.split(":");
		for ( var i = 0; i < arr.length; i++) {
			//将页面本身的一个公式赋值
			jQuery("#desc").val(desc);
			jQuery("#desc").attr("disabled", "disabled");
			jQuery("#item").val(arr[0]);
			jQuery("#operate_reasult").val(arr[1]);
		}
	}
});
function submitOk() {
	window.parent.comArr.length = 0;
	window.parent.itemArr.length = 0;
	window.parent.conArr.length = 0;
	window.parent.valArr.length = 0;

	window.parent.descMain = null;
	window.parent.item_selMain = null;
	window.parent.compareMain = null;
	window.parent.valueMain = null;

	window.parent.compareMain = "=";

	window.parent.descMain = jQuery("#desc").val();
	window.parent.item_selMain = jQuery("#item").val();
	if (jQuery("#item").children()[jQuery("#item")[0].selectedIndex].id == "String"
			|| jQuery("#item").children()[jQuery("#item")[0].selectedIndex].id == "Date") {
		if (window.parent.specialItem == "") {
			window.parent.specialItem = jQuery("#item").val();
		} else {
			window.parent.specialItem += ":" + jQuery("#item").val();
		}
	}
	window.parent.valueMain = jQuery("#operate_reasult").val();
	window.parent.isCon = 2;
	window.parent.flag = 0;
	var exe = $("#exe").val();
	if (exe != null && exe != '') {
		window.parent.editConditionExe();
	} else {
		window.parent.addOperateResult();
	}
	//$.close("setResult");
}
function setValue() {
	if (window.parent.document.getElementById("flag").value == 1) {
		$("#desc").val(window.parent.descMain);
		$("#operate_reasult").val(window.parent.valueMain);
	}
}