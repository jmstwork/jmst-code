$(document).ready(function(){
	$("#leftmenu ul.nav_menu").hide();				
	
	$("#leftmenu li.parent a.mainMenu").click(function(){
	   if($(this).attr("href")=="javascript:void(0);"){
			if($(this).hasClass("linkClick")){
				$(this).parent().find("ul.nav_menu").slideUp(300);
				$(this).parent().find("a.mainMenu").removeClass("linkClick");
				var id = $(this).parent().attr("id");
				removeCookie(id);
			}else {
				var td = document.getElementById("leftMenuTD");
				//1.优先循环关闭已经展开的
				var totalMenu = $(this).parent().parent().children();
				for(var i=0;i<totalMenu.length;i++){
					if(totalMenu[i].innerHTML.indexOf("mainMenu linkClick")>0){
						totalMenu[i].children[0].click();
					}
				} 
				
				//2.将当前点击的展开
				$(this).parent().find("ul.nav_menu").slideDown(300);
				$(this).parent().find("a.mainMenu").addClass("linkClick");
				//var height = td.offsetHeight;
				var id = $(this).parent().attr("id");
				addCookie(id);

			}
		} else {
				return true;
		}
	});
	
	/*左侧菜单栏高亮显示*/
	$("#leftmenu li.parent ul.nav_menu li a.secondMenu").click(
		function() {
			var oElements = $("#leftmenu li.parent ul.nav_menu li a");
			for ( var i = 0; i < oElements.length; i++) {
				  if (oElements.parent().find("a.secondMenu").hasClass("olinkClick")) {
						oElements.parent().find("a.secondMenu").removeClass("olinkClick");
				  }
			}
			$(this).parent().find("a.secondMenu").addClass("olinkClick");
		});
	
	//默认展开第一项
	//$("#leftmenu ul li:first a.mainMenu").click();
	
	openNaviByCookie();
	});

function addCookie(id){
	var cookie = document.cookie;
	var cookieItems = cookie.split('; ');
	var i = 0;
	var opened = null;
	for(i = 0; i < cookieItems.length; i++ ){
		if( cookieItems[i].indexOf("openedNavi") != -1 ){
			opened = cookieItems[i];	
		}
	}
	if(opened != null){
		if(opened.length > "openedNavi=".length){
			opened += ",";
		}
		opened += id;
		document.cookie = opened + ";path=/";
	} else {
		document.cookie = "openedNavi=" + id + ";path=/";
	}
}

function removeCookie(id){
	var cookie = document.cookie;
	var cookieItems = cookie.split('; ');
	var i = 0;
	var opened = null;
	for(i = 0; i < cookieItems.length; i++ ){
		if( cookieItems[i].indexOf("openedNavi") != -1 ){
			opened = cookieItems[i];	
		}
	}
	if(opened != null){
		opened = opened.replace("openedNavi=", "");
		var ids = opened.split(",");
		var removed = "";
		for(i = 0; i < ids.length; i++){
			if(ids[i] != id){
				if(removed.length > 0){
					removed += ",";
				}
				removed += ids[i];
			} 
		}
		opened = "openedNavi=" + removed;
		document.cookie = opened + ";path=/";
	}
}
/**
 * 查找cookie中已打开的导航栏并展开
 * */
function openNaviByCookie(){
	var cookie = document.cookie;
	var cookieItems = cookie.split('; ');
	var i = 0;
	var opened = null;
	for(i = 0; i < cookieItems.length; i++ ){
		if( cookieItems[i].indexOf("openedNavi") != -1 ){
			opened = cookieItems[i];	
		}
	}
	if(opened != null){
		opened = opened.replace("openedNavi=", "");
		//只记录最后一个被点开的菜单
		var ids = opened.split(",");
		for(i = ids.length-1; i < ids.length; i++){
			$("#" + ids[i] + " a.mainMenu").addClass("linkClick");
			$("#" + ids[i] + " ul.nav_menu").slideDown(0);
		}

	}
}