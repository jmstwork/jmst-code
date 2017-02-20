$(function(){
		$("#testBtn").bind("click",function(){
			document.getElementById("fromDataError").innerHTML=""
			var fromSql= $('#fromDatasourceSql').val();
			var toSql = $('#toDatasourceSql').val();
			var datasourceId = $("#datasourceId").val();
			if(fromSql==""){
				document.getElementById("fromDataError").innerHTML="SQL语句不可为空";
				return ;
			}
			if(toSql==""){
				document.getElementById("toDataError").innerHTML="SQL语句不可为空";
				return ;
			}
			var url_="./testSql.html";
			var error = "";
			jQuery.post(url_,{"fromSql":fromSql,"toSql":toSql,"datasourceId":datasourceId},function(e){
				if(e.fromSqlStr =="exception"){
					asyncbox.alert("源数据SQL异常无法执行,请重新检查SQL语句是否正确","提示信息");
					return;
				}else if(e.toSqlStr == "exception"){
					asyncbox.alert("目标数据源SQL异常无法执行,请重新检查SQL语句是否正确","提示信息");
					return;
				}else{
					var fromSqlArr =e.fromSqlStr.split(",");
					var toSqlArr=e.toSqlStr.split(",");
					for(i=0;i<toSqlArr.length;i++){
						for(j=0;j<fromSqlArr.length;j++){
							$("#result").val("true");
							if(i==j){
								if(fromSqlArr[j] != toSqlArr[i]){
									$("#result").val("false");
									var error = fromSqlArr[j];
									break;
								}
							}
							
						}
						if($("#result").val()=="false"){
							asyncbox.alert(error+" 校验失败！请检查目标数据与源数据SQL字段顺序与名称是否一致","提示信息");
							return;
						}
					}
					if($("#result").val()=="true"){
						asyncbox.alert("sql校验成功!","提示信息");
					}
				}
			});
			
			
		});
		$("#saveBtn").bind("click",function(){
			if($("#result").val()=="true"){
				saveDatasource();
			}else{
				var dictStatus=$("#dictStatus")[0].checked;
				if(dictStatus){
					asyncbox.alert("sql语句校验通过后才可保存","提示信息");
					}else{
						asyncbox.confirm('Sql校验未成功，是否继续保存？','sql校验',function(action){
							if(action == 'ok'){
								saveDatasource();
							}})
					}
				}
			});

		
})	
function saveDatasource(){
	var updateFlg =$("#updateFlg").val();
	var updateCount=$("#updateCount").val();
	var datasourceId =$("#datasourceId").find('option:selected').val();
	var syncId=$("#syncId").val();
	var dictId=$("#dictId").find('option:selected').val();
	var dictNametext=$("#dictId").find('option:selected')[0].innerHTML;
	var dictNameArr = dictNametext.split(";");
	var dictName = dictNameArr[1];
	var dictTableName= $("#dictTableName").val();
	var syncInterval=$("#syncInterval").val();
	var dictStatus=$("#dictStatus")[0].checked;
	if(dictStatus){
		dictStatus=1;
	}else{
		dictStatus=0;
	}
	var fromDatasourceSql=$("#fromDatasourceSql").val();
	var toDatasourceSql=$("#toDatasourceSql").val();
	if(datasourceId==""){
		document.getElementById("dataError").innerHTML="提示信息";
	}
	var url_="./synceditSave.html";
	jQuery.post(url_,{"datasourceId":datasourceId,"syncId":syncId,"dictId":dictId,"syncInterval":syncInterval,"dictName":dictName,"dictTableName":dictTableName,
		 "dictStatus":dictStatus,"fromDatasourceSql":fromDatasourceSql,"toDatasourceSql":toDatasourceSql,"updateFlg":updateFlg,"updateCount":updateCount},function(e){
			 if(e.status== 1){
				 asyncbox.alert("保存成功","提示信息",function(action){
					 if(action == 'ok'){
						 $("#result").val("");
						 $("#back").click();
					 }
				 });
			 }else{
				 asyncbox.alert("保存失败","提示信息");
				 }
			 });
}

function getDictTable(obj){
		var dictTable=document.getElementById("dictTableName");  
		dictTable.value=obj; 
	}