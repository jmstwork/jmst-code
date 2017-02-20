function firstPage(form)
{
	if($("[name='export']").val() != null)
		$("[name='export']").val("");
    //alert(form.currentPage.value);
	form.reset();
	//alert(form.action);
    form.jumpToPage.value = 1;
    form.submit();
}

function prevPage(form)
{
	if($("[name='export']").val() != null)
		$("[name='export']").val("");
    var cp = parseInt(form.currentPage.value, 10);
    form.reset();
    if(cp <= 1)
        form.jumpToPage.value = 1;
    else
        form.jumpToPage.value = cp - 1;
    form.submit();
}

function nextPage(form)
{
	if($("[name='export']").val() != null)
		$("[name='export']").val("");
    var cp = parseInt(form.currentPage.value, 10);
    var tp = parseInt(form.totalPage.value, 10);
    form.reset();
    if(cp >= tp)
        form.jumpToPage.value = tp;
    else
        form.jumpToPage.value = cp + 1;
    form.submit();
}

function lastPage(form)
{
	if($("[name='export']").val() != null)
		$("[name='export']").val("");
    //alert(form.currentPage.value);
    form.reset();
    form.jumpToPage.value = form.totalPage.value;
    form.submit();
}

function jumpToPage(form, pageNo)
{
	if($("[name='export']").val() != null)
		$("[name='export']").val("");
   // form.reset();
    //alert(form.currentPage.value);
    var tp = parseInt(form.totalPage.value, 10);
    try
    {
        var inpVal = parseInt(pageNo.value, 10);

        if(isNaN(inpVal) || inpVal < 1)
        {
            var msg = "请输入正确的页码！";
            var err = new Error(msg);
            if(!err.message)
            {
                err.message = msg;
            }
            throw err;
        }
        else if(inpVal > tp)
        {
            var msg = "输入的页码超过总页数，请重新输入！";
            var err = new Error(msg);
            if(!err.message)
            {
                err.message = msg;
            }
            throw err;
        }
        else
        {
            //form.reset();
            form.jumpToPage.value = pageNo.value;
            form.submit();
            form.reset();
        }
    }
    catch(e)
    {
        //alert(e.message);
    	alert(e);
        pageNo.focus();
        pageNo.select();
    }
}

//处理按回车键提交表单
$(function(){
	$("input,select").keypress(function (e) {
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		if (keyCode == 13){
			$("form")[0].submit();
		}else{
			 return true;
		}
	});
	
	//处理ajax请求session过期后跳转
	$.ajaxSetup({   
        contentType:"application/x-www-form-urlencoded;charset=utf-8",   
        cache:false ,   
        complete:function(XHR,TS){   
	        var resText=XHR.responseText;
	        if(XHR.status=="401"){ 
	        	 window.top.location.href = "../security/login.html";
	        }   
        }   
    });   

});

//checkbox选择
function checkParent(){
    var children = document.getElementsByName("childrenBox");
    var parent = document.getElementById("parentBox");
    var tempState=parent.checked;
    for(i=0;i<children.length;i++) {
        if(children[i].checked!=tempState)
            children[i].click();
     }
}
function checkChildren() {
     var children = document.getElementsByName("childrenBox");
     var parent = document.getElementById("parentBox");
     for(var i=0; i<children.length; i++) {
         if(!children[i].checked) {
             parent.checked = false;
         return;
         }
     }
     parent.checked = true;
}

function lrFixFooter(obj){
	var footer = $(obj),doc = $(document);
	//当前网页
	var height1 = document.body.offsetHeight;
	var height2 = document.body.scrollHeight;
		if(height1 >= height2){
			footer.css({
				width:"100%",
				position:"absolute",
				bottom:0	
			});
			
		}else{
			footer.css({
				position:"static",
				bottom:0
			});
		}
	}


$(function(){
	lrFixFooter('div.footerwarp');
    $(window).bind('resize',function(){
          lrFixFooter('div.footerwarp');
      });
});
	 
	 