/*此代码为双击表格编辑 单元格保存
$("td[data-role]").dblclick(function(){
    var td = $(this);
//	    console.log($(this).find("input"));
    // 根据表格文本创建文本框 并加入表表中--文本框的样式自己调整
    var text = td.text();
    var txt = $("<input type='text' class='form-control input-sm'>").val(text);
    txt.blur(function(){
    	// 失去焦点，保存值。
    	var data = getMonitorValue(this);
    	
        var newText = $(this).val();
        //ajax保存数据
        $.ajax({
            type: "POST",
            url: "/syssetting/pm/updateValue",
            dataType: "json",
            async: false,
            data: data,
            error: function (data, transport) {
            	swal("修改失败!", "系统异常,请联系管理员", "error");
            },
            success: function (data) {
            	if(data.returnCode =="0000"){
            		swal("修改成功!", "修改成功.", "success");
            	}else{
            		//没有别的情况,直接走else
            		swal("修改失败!", data.message, "error");
            	}
            }
        });
        // 移除文本框,显示新值
        $(this).remove();
        td.text(newText);
    });
    td.text("");
    td.append(txt);
    $(this).find("input").focus();
});

function getMonitorValue(obj){
	var stationId = $(obj).parents("tbody").attr("data-role-value");	//stationId
	var param = $(obj).parents("tr").attr("data-role-value");	//param
	var updateType = $(obj).parents("td").attr("data-role");	//改变的变量
	var updateValue = $(obj).val();	//改变的值
	var updateObj = $(obj).parents("td");	//域对象
	//获取其他不改变的值
	var rangeMaxObj = $(obj).parents("tr").find("td[data-role='rangeMax']");
	var rangeMinObj = $(obj).parents("tr").find("td[data-role='rangeMin']");
	var alertMaxObj = $(obj).parents("tr").find("td[data-role='alertMax']");
	var alertMinObj = $(obj).parents("tr").find("td[data-role='alertMin']");
	var data="({stationId:"+stationId+",param:'"+param+"',"+updateType+":"+updateValue+",";
	debugger;
	if(rangeMaxObj[0]!=updateObj[0]){
		data += rangeMaxObj.attr("data-role")+":"+rangeMaxObj.text()+",";
	}
	if(rangeMinObj[0]!=updateObj[0]){
		data += rangeMinObj.attr("data-role")+":"+rangeMinObj.text()+",";
	}
	if(alertMaxObj[0]!=updateObj[0]){
		data += alertMaxObj.attr("data-role")+":"+alertMaxObj.text()+",";
	}
	if(alertMinObj[0]!=updateObj[0]){
		data += alertMinObj.attr("data-role")+":"+alertMinObj.text()+",";
	}
	data = data.substr(0,data.length-1)+"})";
	var jsonData = eval(data);
	return jsonData;
}

*/

function cancelUpdate(){
	swal({
        title: "取消修改",
        text: "你确定要取消修改吗?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定取消!",
        cancelButtonText:"继续修改",
        showLoaderOnConfirm: true,
        closeOnConfirm: false
    }, function () {
    	location.href="page";
    });
}

function submitUpdate(){
	swal({
        title: "保存操作",
        text: "是否确认保存!",
        type: "info",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定保存!",
        cancelButtonText:"取消",
        showLoaderOnConfirm: true,
        closeOnConfirm: false
    }, function () {
    	$.ajax({
    	    type: "POST",
    	    url: "/syssetting/pm/updateValue",
    	    dataType: "json",
    	    async: false,
    	    data: $("#updateMonitorForm").serialize(),
    	    error: function (data, transport) {
    	    	swal("修改失败!", "系统异常,请联系管理员", "error");
    	    },
    	    success: function (data) {
    	    	if(data.returnCode =="0000"){
    	    		swal({
            	        title: "修改成功!",
            	        text: "修改成功.",
            	        type: "success",
            	        confirmButtonText: "确定",
            	        closeOnConfirm: false
            	    },function () {
            	    	location.href="page";
            	    })
    	    	}else{
    	    		//没有别的情况,直接走else
    	    		swal("修改失败!", data.message, "error");
    	    	}
    	    }
    	});
    });
}

