$(function(){
	var oldRow = $("#dataTable>table>tbody tr").eq(0);//修改前的行
	var newRow = $("#dataTable>table>tbody tr").eq(1);//修改后的行
	showDifferentRowInRed(oldRow, newRow);
})

function showDifferentRowInRed(oldRow, newRow){
	var oldColumns = oldRow.children('td');
	var newColumns = newRow.children('td');
	for(var i = 0; i < oldColumns.length; i++){
		showDifferentColumnInRed(oldColumns.eq(i), newColumns.eq(i));
	}
}

function showDifferentColumnInRed(oldColumn, newColumn){
	if(isDifferent(oldColumn.text(), newColumn.text())){
		changeRed(oldColumn);
	}
}

function isDifferent(oldValue, newValue){
	if(oldValue.trim()!=newValue.trim()){
		return true;
	}else{
		return false;
	}
}

function changeRed(column){
	var value = column.text();
	var html = "<span style='color:red;'>"+value+"</span>";
	column.html(html);
}