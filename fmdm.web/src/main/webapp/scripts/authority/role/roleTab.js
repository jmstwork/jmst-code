 $(function() {
 	var tabTitle=$("#tabTitle").val();
 	if(tabTitle==undefined)
 		tabTitle=0;
 	 $("#tabs").tabs({active:1});
     $("#tabs").tabs({
       beforeLoad: function(event,ui){
         ui.jqXHR.error(function() {
           ui.panel.html(
             "不能加载该标签页。如果这不是一个演示。" +
             "我们会尽快修复这个问题。" );
         });
     }
    });
   var roleId = $("#roleId");
});
