$('.demo4').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
            title: "提示",
            text: "请先选中一个测点"
        });
	}else if(checkedLength>1){
		swal({
            title: "提示",
            text: "最多勾选一个测点"
        });
	}else{
		swal({
	        title: "删除测点",
	        text: "是否要删除该测点,一旦删除无法恢复!",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定删除!",
	        cancelButtonText:"取消",
	        showLoaderOnConfirm: true,
	        closeOnConfirm: false
	    }, function () {
	    	var portId = $("input[type='checkbox']:checked").val();
	    	$.ajax({
	            type: "POST",
	            url: "/syssetting/pm/deletePort",
	            dataType: "json",
	            async: false,
	            data: {id:portId},
	            error: function (data, transport) {
	            	swal("删除失败!", "系统异常,请联系管理员", "error");
	            },
	            success: function (data) {
	            	if(data.returnCode =="0000"){
	            		swal({
	            	        title: "删除成功!",
	            	        text: "该测点已删除.",
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

//修改测点
$('.demo3').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
			title: "提示",
			text: "请先选中一个测点"
		});
	}else if(checkedLength>1){
		swal({
			title: "提示",
			text: "最多勾选一个测点"
		});
	}else{
		var stationId = $("input[type='checkbox']:checked").val();
		$.ajax({
	        type: "POST",
	        url: "/syssetting/pm/getPortById",
	        dataType: "json",
	        async: false,
	        data: {id:stationId},
	        error: function (data, transport) {
	        	swal("系统异常!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		$("#addPort").modal("show");
	        		var roles = data.returnData.roles;
	        		$("#addPort").on("shown.bs.modal", function(){
	        			$("#myModalLabel2").text("修改测点");
	        			$("#stationId").val(data.returnData.stationId);
	        			$("#stationBaseId").val(data.returnData.stationBaseId);
	        			$("#point").val(data.returnData.point);
	        			$("#coordinate").val(data.returnData.longitude + ',' + data.returnData.latitude);
	        			$("#installTime").val(new Date(data.returnData.installTime).Format("yyyy-MM-dd hh:mm:ss"));
	        			$("#installCoordinate").val(data.returnData.installLongitude + ',' + data.returnData.installLatitude);
	        			$("#cardNo").val(data.returnData.cardNo);
	        			$("#projectCode").val(data.returnData.projectCode);
	        			$("#gatewaySerial").val(data.returnData.gatewaySerial);
	        			$("#stationJudgeType").val(data.returnData.stationJudgeType);
	        			$("#stationStandard").val(data.returnData.stationStandard);
	        			$("#transferCycle").val(data.returnData.transferCycle);
	        			$("#companyId").val(data.returnData.companyId);
	        			/* 此处不在zuo
	        			$("#powerDelay").val(data.returnData.powerDelay);
	        			$("#collectionTime").val(data.returnData.collectionTime);
	        			$("#serialQueryInterval").val(data.returnData.serialQueryInterval);
	        			$("#serialInterval").val(data.returnData.serialInterval);
	        			$("#lowBatteryAlarm").val(data.returnData.lowBatteryAlarm);
	        			$("#lowBatteryAlarmRange").val(data.returnData.lowBatteryAlarmRange);
	        			$("#lowBatteryAlarm2").val(data.returnData.lowBatteryAlarm2);
	        			$("#lowBatteryAlarmRange2").val(data.returnData.lowBatteryAlarmRange2);
	        			$("#voltageOffset").val(data.returnData.voltageOffset);
	        			$("#recordStorageInterval").val(data.returnData.recordStorageInterval);
	        			$("#recordUploadInterval").val(data.returnData.recordUploadInterval);
	        			$("#realtimeUploadInterval").val(data.returnData.realtimeUploadInterval);
	        			$("#fullBattery").val(data.returnData.fullBattery);
	        			*/
	        		})
	        	}else{
            		//没有别的情况,直接走else
            		swal("操作失败!", data.message, "error");
            	}
	        }
	    });
	}
});


function savePort(){
	if($("#addPortForm").valid()){
		$.ajax({
	        type: "POST",
	        url: "/syssetting/pm/addPort",
	        dataType: "json",
	        async: false,
	        data: $("#addPortForm").serialize(),
	        error: function (data, transport) {
	        	swal("保存失败!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		$("#addPort").modal("hide");
	        		swal({
	        	        title: "保存成功!",
	        	        text: "保存成功.",
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