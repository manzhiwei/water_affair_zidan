/**
 * 删除区域操作
 */
$('.demo4').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
            title: "提示",
            text: "请先选中一个区域"
        });
	}else if(checkedLength>1){
		swal({
            title: "提示",
            text: "最多勾选一个区域"
        });
	}else{
		swal({
	        title: "删除区域",
	        text: "是否要删除该区域,一旦删除无法恢复!",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定删除!",
	        cancelButtonText:"取消",
	        showLoaderOnConfirm: true,
	        closeOnConfirm: false
	    }, function () {
	    	var areaId = $("input[type='checkbox']:checked").val();
	    	$.ajax({
	            type: "POST",
	            url: "/syssetting/am/deleteArea",
	            dataType: "json",
	            async: false,
	            data: {id:areaId},
	            error: function (data, transport) {
	            	swal("删除失败!", "系统异常,请联系管理员", "error");
	            },
	            success: function (data) {
	            	if(data.returnCode =="0000"){
	            		swal({
	            	        title: "删除成功!",
	            	        text: "该区域已删除!",
	            	        type: "success",
	            	        confirmButtonText: "确定",
	            	        closeOnConfirm: false
	            	    },function () {
	            	    	location.reload();
	            	    })
	            	}else{
	            		//没有别的情况,直接走else
	            		swal("删除失败!", data.message, "error");
	            	}
	            }
	        });
	    });
	}
});
/**
 * 修改区域操作
 */
$('.demo3').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
			title: "提示",
			text: "请先选中一个区域"
		});
	}else if(checkedLength>1){
		swal({
			title: "提示",
			text: "最多勾选一个区域"
		});
	}else{
		var userId = $("input[type='checkbox']:checked").val();
		$.ajax({
	        type: "POST",
	        url: "/syssetting/am/getAreaById",
	        dataType: "json",
	        async: false,
	        data: {id:userId},
	        error: function (data, transport) {
	        	swal("系统异常!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		$("#addArea").modal("show");
	        		var roles = data.returnData.roles;
	        		$("#addArea").on("shown.bs.modal", function(){
	        			$("#myModalLabel").text("修改区域");
	        			$("#id").val(data.returnData.id);
	        			$("#companyName").val(data.returnData.companyName);
	        			$("#level").val(data.returnData.level);
	        			$("#parentId").val(data.returnData.parentId);
	        			$("#coordinate").val(data.returnData.longitude+" "+","+" "+data.returnData.latitude);
	        			$("select").trigger("chosen:updated");
	        			var config = {
	        		        '.chosen-select'           : {},
	        		        '.chosen-select-deselect'  : {allow_single_deselect:true},
	        		        '.chosen-select-no-single' : {disable_search_threshold:10},
	        		        '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	        		        '.chosen-select-width'     : {width:"95%"}
	        		        }
	        		    for (var selector in config) {
	        		        $(selector).chosen(config[selector]);
	        		    }
	        			changeOption();
	        		})
	        	}else{
            		//没有别的情况,直接走else
            		swal("操作失败!", data.message, "error");
            	}
	        }
	    });
	}
});


function changeOption(){
}
/**
 * 保存区域
 */
function saveArea(){
	if($("#addAreaForm").valid()){
		$.ajax({
			type: "POST",
			url: "/syssetting/am/addArea",
			dataType: "json",
			async: false,
			data: $("#addAreaForm").serialize(),
			error: function (data, transport) {
				swal("添加失败!", "系统异常,请联系管理员", "error");
			},
			success: function (data) {
				if(data.returnCode =="0000"){
					$("#addArea").modal('hide');
					swal({
	        	        title: "保存成功!",
	        	        text: "保存成功!",
	        	        type: "success",
	        	        confirmButtonText: "确定",
	        	        closeOnConfirm: false
	        	    },function () {
	        	    	location.reload();
	        	    })
				}else{
					//没有别的情况,直接走else
					swal("保存失败!", data.message, "error");
				}
			}
		});
	}
}

/**
 * jquery validation
 */
var jqValidMsg = {
	required: "必填字段",
	remote: "请修正该字段",
	email: "请输入正确格式的电子邮件",
	url: "请输入合法的网址",
	date: "请输入合法的日期",
	dateISO: "请输入合法的日期 (ISO).",
	number: "请输入合法的数字",
	digits: "只能输入整数",
	creditcard: "请输入合法的信用卡号",
	equalTo: "请再次输入相同的值",
	accept: "请输入拥有合法后缀名的字符串",
};
jQuery.extend(jQuery.validator.messages, jqValidMsg);