/**
 * mask wall for jQuery
 * Written by zhengdongyang
 * Date: 2010/08/08
 * @version 1.0
 * 
 * @example
 * isBar(ture:进度条处理-默认值,false:等待处理
 * $(document).progressDialog.showDialog();
 * 
 *  $.ajax({
	  .....
	  complete:function(data){
	    $(document).progressDialog.hideDialog(isBar,text);
	    //do something
	  }
	});
 **/
(function($) {
	$.fn.progressDialog = function() {
	};

	$.fn.progressDialog.showDialog = function(isBar,text) {
		text = text || "请求处理中...";
		isBar = isBar && true;
		createElement(isBar,text);
		setPosition();
		waterfall.appendTo("body");
		$(window).bind('resize', function() {
			setPosition();
		});
	}

	$.fn.progressDialog.hideDialog = function(text) {
		waterfall.remove();
	}

	function createElement(isBar,text) {
		if (!waterfall) {
			waterfall = $(document.createElement("div"));
			waterfall.attr("id", "waterfall");
			waterfall.css( {
				"height" : $(document).height(),
				"width" : $(document).width(),
				"filter" : "alpha(opacity = 50)",
				"-moz-opacity" : "0.5",
				"opacity" : "0.5",
				"background-color" : "#F1F1F9",
				"position" : "absolute",
				"left" : "0px",
				"top" : "0px"
			});
		}
		if (!loadDiv) {
			loadDiv = document.createElement("div");
		}
		$(loadDiv).appendTo(waterfall);
		var content;
		if(isBar == false){
			content =
				"<div id='progress' style='color:#57A000;font-size:1.2em;text-align:center;'>"+
					'<div class="progresstext" style="padding:0 0 3px 10px;" id="progressDialogText">'+ text + '</div>'+
					'<div class="progressbar" style="" id="uploadprogressbar">'+
					'<img src = "../images/ajax_loading_green_nested.gif" border="0"/>'+
					'</div>'+
				"</div>";
		}else{
			content =
				"<div id='progress' style='color:#57A000;font-size:1.2em;'>"+
					'<div class="progresstext" style="padding:0 0 3px 40px;" id="progressDialogText">'+ text + '</div>'+
					'<div class="progressbar" style="font-size:1.2em;" id="uploadprogressbar"></div>'+
				"</div>";			
		}
		
		$(loadDiv).html(content);
		
	}

	function setPosition() {
		var leftOffset = ($(window).width() - width) / 2;
		var topOffset = ($(window).height() - Height) / 2;
		$(loadDiv).css( {
			"position" : "absolute",
			"height" : Height + "px",
			"width" : width + "px",
			"left" : leftOffset + "px",
			"top" : topOffset + "px"
		});
	}
	
	$.mastWallShow = function(text) {
		$(document).progressDialog.showDialog(false,text);
	}
	
	$.mastWallHide = function() {
		$(document).progressDialog.hideDialog();
	}

	var waterfall;
	var loadDiv;
	var width = 170;
	var Height = 60;
})(jQuery);

$(document).ready(function(){
	// 监听所有表单提交
	$("form").each(function(index){
		if(!this.supersubmit)
		this.supersubmit = this.submit;
		// 提交前处理
		this.submit = function(mask){
			if(isNaN(mask)|| mask)// mask=false 不启动水幕
			$.mastWallShow();// 启动水幕
			// TODO 保留处理过程
			this.supersubmit(); 
		};
	});
})
