 /**
	 * autocomplete联想输入操作ajax
	 * author wp
	 */
function myDropDownListCode(inputId, ajaxUrl, minChars, pageSize, valueId) {
	//alert(inputId);
    $("#"+inputId).autocomplete(ajaxUrl, {
    	//在触发autoComplete前用户至少需要输入的字符数，Default:1，如果设为0
        minChars: minChars,
        //autoComplete下拉显示项目的个数，Default: 10 
        max: pageSize,
        //指定下拉框的宽度，Default: input元素的宽度 
        width: 300,
        scrollHeight:400, 
        //决定比较时是否要在字符串内部查看匹配，如ba是否与foo bar中的ba匹配.使用缓存时比较重要.不要和autofill混用.Default: false 
        matchContains: "true",
        //指定数据类型的渲染方式
        dataType: 'json',
        //要不要在用户选择时自动将用户当前鼠标所在的值填入到input框，Default: false 
        autoFill: false,
        extraParams:{pv:inputId},
        //mustMatch:true,
        parse: function (data) {
            var rows = new Array();

            for (var i = 0; i < data.dt.length; i++) {
                rows.push({
                    data: data.dt[i],
                    value: data.dt[i].sourceTable,
                    result: data.dt[i].sourceName
                });
            }

            if (data.dataCount == 0) {
                $("#" + inputId).attr("pager_index_"+inputId+"", 0);
            }
            else {
                $("#" + inputId).attr("pager_index_"+inputId+"", 1);
            }

            $("#" + inputId).attr("dataCount_"+inputId+"", data.dataCount);

            var dataCount = parseInt(data.dataCount);

            var totalPage = parseInt(dataCount / pageSize);

            if (dataCount != 0 && dataCount % pageSize != 0) {
                totalPage += 1;
            }

            $("#" + inputId).attr("pager_total_"+inputId+"", totalPage);
            $("#" + inputId).attr("dataCount_"+inputId+"", dataCount);

            var pager_total = document.getElementById("pager_total_"+inputId+"");

            if (pager_total != null) {
                pager_total.innerHTML = totalPage;
            }

            return rows;
        },
        formatItem: function (row, i, max) {
            var curIndex = $("#pager_currentIndex_"+inputId+"").val();
            var dataCount = $("#" + inputId).attr("dataCount_"+inputId+"");
            var index = (curIndex - 1) * pageSize + i;

            return index + "/" + dataCount + ": \"" + row.sourceTable + "\" [名称:" + row.sourceName + ",版本号:"+row.version+"]";
        },
        formatMatch: function (row, i, max) {
            return row.sourceTable;
        },
        formatResult: function (row) {
            return row.sourceTable;
        }
    });

    $("#" + inputId).result(function (event, data, formatted) {
        if(data){
        	if(inputId=="targetCode"){
        		$(this).val(data.sourceTable);
           	    $("#" + valueId).val(data.sourceName);
           	    $("#targetCodeVersion").val(data.version);
           	    $("#tarUniKey").val(data.unikey);
           	    $("#targetItemName").val(data.sourceName);
        	}else if(inputId=="targetCodeContent"){
        		$(this).val(data.sourceName);
           	    $("#" + valueId).val(data.sourceTable);
           	    $("#targetCodeVersion").val(data.version);
           	    $("#tarUniKey").val(data.unikey);
           	    $("#targetItemName").val(data.sourceName);
        	}else if(inputId == "sourceCode"){
        		$(this).val(data.sourceTable);
           	    $("#" + valueId).val(data.sourceName);
           	    $("#sourceCodeVersion").val(data.version);
           	    $("#srcUniKey").val(data.unikey);
           	    $("#sourceItemName").val(data.sourceName);
        	}else if(inputId == "sourceCodeContent"){
        		$(this).val(data.sourceName);
           	    $("#" + valueId).val(data.sourceTable);
           	    $("#sourceCodeVersion").val(data.version);
           	    $("#srcUniKey").val(data.unikey);
           	    $("#sourceItemName").val(data.sourceName);
        	}
        }
    });
}