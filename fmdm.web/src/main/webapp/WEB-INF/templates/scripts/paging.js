function firstPage(form)
{
    //alert(form.currentPage.value);
    form.jumpToPage.value = 1;
    form.reset();
    form.submit();
}

function prevPage(form)
{
    var cp = parseInt(form.currentPage.value, 10);
    if(cp <= 1)
        form.jumpToPage.value = 1;
    else
        form.jumpToPage.value = cp - 1;
    form.reset();
    form.submit();
}

function nextPages(form)
{
    var cp = parseInt(form.currentPage.value, 5);
    var tp = parseInt(form.totalPage.value, 5);
    if(cp >= tp)
        form.jumpToPage.value = tp;
    else
        form.jumpToPage.value = cp + 1;
    form.reset();
    form.submit();
}

function lastPages(form)
{
    //alert(form.currentPage.value);
    form.jumpToPage.value = form.totalPage.value;
    form.reset();
    form.submit();
}

function jumpToPage(form, pageNo)
{
	alert(99);
    form.reset();
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
            form.jumpToPage.value = pageNo.value;
            form.reset();
            form.submit();
        }
    }
    catch(e)
    {
        //alert(e.message);
        pageNo.focus();
        pageNo.select();
    }
}
