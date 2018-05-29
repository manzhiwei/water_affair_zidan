function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:155 + "px", top:275 + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

options.bootstrapMajorVersion=3;
options.itemTexts=function (type, page, current) {
                    switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "尾页";
                    case "page":
                        return page;
                    }
                };
options.numberOfPages=3;

$('#pagination').bootstrapPaginator(options);

function deleteConfirm(obj){
	var id = obj.getAttribute('value');
	var record = obj.parentElement.parentElement.children;
	swal({
        title: "确定清除?",
        text: '测点名称：' + record[1].innerText + '\n'
        	+ '报警时间：' + record[3].innerText + '\n'
        	+ '报警描述：' + record[7].innerText +'\n',
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        showLoaderOnConfirm: true
    }, function () {
    	$.ajax({
    		url: 'removeAlarm',
    		type: 'POST',
    		data: "id="+id,
    		dataType: 'html',
    		success: function(res){
    			swal("操作成功!", "记录已经清除", "success");
    	        obj.parentElement.parentElement.remove();
    		},
    		error: function(e){
    			swal("操作失败!", "","error");
    		}
    		
    	})
        
    });
}