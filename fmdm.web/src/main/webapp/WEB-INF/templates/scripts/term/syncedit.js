$(function(){
		$("#testBtn").bind("click",function(){
			var fromSQL= $('fromSQL').val();
			var toSQL = $('toSQL').val();
			
		});
		$("#saveBtn").bind("click",function(){
			var datasourceId =$("#datasourceId").find('option:selected').val();
			var dictId=$("#dictId").find('option:selected')[0].id;
			var dictTableName= $("#dictTableName").val();
			var syncInterval=$("#syncInterval").val();
			var dictStatus=$("#dictStatus")[0].checked;
			var fromDatasourceSql=$("#fromDatasourceSql").val();
			var toDatasourceSql=$("#toDatasourceSql").val();
			var url_="./synceditSave.html";
			jQuery.post(url_,{"datasourceId":datasourceId,"dictId":dictId,"syncInterval":syncInterval,
				 "dictStatus":dictStatus,"fromDatasourceSql":fromDatasourceSql,"toDatasourceSql":toDatasourceSql},function(e){
					 if(e.status=1){
						 alert("保存成功");
					 }else{
						 alert("保存失败");
					 }
				 });
			
		});
		
})	

function getDictTable(obj){
		var dictTable=document.getElementById("dictTableName");  
		dictTable.value=obj; 
	}