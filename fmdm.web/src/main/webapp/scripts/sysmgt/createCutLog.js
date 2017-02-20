$(function() {
	var logTableRows = $('#dataTable>table>tbody>tr');
	for(var i = 0; i < logTableRows.length; i++){
		var column = logTableRows.eq(i).children('td').eq(5);
		createAndSetCutLog(column);
	}
});

function createAndSetCutLog(column){
	var text = column.text();
	var index = text.indexOf("@");
	if(index!=-1){
		var general = text.substring(0,index);
		var detail = text.substring(index+1,text.length);
		detail.replace(/@/g,'\n');
		column.text(general);
		column.attr('title',detail.replace(/@/g,'\n'));
	}
}
	