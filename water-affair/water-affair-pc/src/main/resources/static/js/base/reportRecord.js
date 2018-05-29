Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var setting = {
	check : {
		enable : true,
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick,
		onCheck : onCheck
	}
};

var zNodes ='';

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), 
	nodes = zTree.getCheckedNodes(true), 
	v = "",
	id = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		id += nodes[i].id +",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (id.length > 0)
		id = id.substring(0, id.length - 1).replace(/s/g,'');
	var pointName = $("#pointName");
	pointName.attr("value", v);
	var pointId = $("#pointId");
	pointId.attr("value", id);
}

function showMenu() {
	var cityObj = $("#pointName");
	var cityOffset = $("#pointName").offset();
	$("#menuContent").css({left:163 + "px", top:276 + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "pointName"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
		hideMenu();
	}
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "pointName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(document).ready(function() {
	$.ajax({
        type: "POST",
        url: "/getAreaStation",
        dataType: "json",
        async: false,
        data: '',
        error: function (data, transport) {
        	console.error("无法获取结点,请联系管理员");
        },
        success: function (data) {
        	if(data.returnCode =="0000"){
        		console.info("结点获取成功");
        		zNodes=data.returnData;
        	}else{
        	}
        }
    });
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var pointId = $("#pointId").val();
	var pointSelect = $("#stationId");
	if(pointId.length > 0){
		var ids = pointId.split(',');
		for(var i=0;i<ids.length;i++){
			var node = treeObj.getNodeByParam('id','s'+ids[i]);
			treeObj.checkNode(node, true, true);
			var option = '<option value='+ids[i]+'>'+node.name+'</option>';
//			pointSelect.append(option);
		}
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
});

function checkSelected(){
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
            title: "提示",
            text: "请先选中一条故障信息"
        });
		return false;
	}else if(checkedLength>1){
		swal({
            title: "提示",
            text: "最多勾选一条故障信息"
        });
		return false;
	}
	return true;
}

//删除故障
$('.demo4').click(function () {
	if(checkSelected()){
		swal({
	        title: "删除故障",
	        text: "是否要删除该故障信息,一旦删除无法恢复!",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定删除!",
	        cancelButtonText:"取消",
	        showLoaderOnConfirm: true,
	        closeOnConfirm: false
	    }, function () {
	    	var breakdownId = $("input[type='checkbox']:checked").val();
	    	$.ajax({
	            type: "POST",
	            url: "/reportRecord/deleteBreakdown",
	            dataType: "json",
	            async: false,
	            data: {id:breakdownId},
	            error: function (data, transport) {
	            	swal("删除失败!", "系统异常,请联系管理员", "error");
	            },
	            success: function (data) {
	            	if(data.returnCode =="0000"){
	            		swal({
	            	        title: "删除成功!",
	            	        text: "该故障已删除!",
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

function resetBreakdownAddForm(){
	$("#createTime").val(new Date().Format("yyyy-MM-dd hh:mm:ss"));
	$("#myModalLabel2").html("新增故障");
	$("#addBreakdown").modal("show");
}


function saveBreakdown(){
	if($("#companyName").val()==''){
		swal("保存失败!", "请先选择区域", "warning");
		return;
	}
	if($("#stationId").val()==''){
		swal("保存失败!", "请先选择测点", "warning");
		return;
	}
	$.ajax({
		type: "POST",
		url: "/reportRecord/saveBreakdown",
		dataType: "json",
		async: false,
		data: $("#addBreakdownForm").serialize(),
		error: function (data, transport) {
			swal("保存失败!", "系统异常,请联系管理员", "error");
		},
		success: function (data) {
			if(data.returnCode =="0000"){
        		$("#addBreakdown").modal("hide");
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

function resetRepaidAddForm(companyName,point,id){
	$("#addRepair").modal("show");
	$("#addRepair").on("shown.bs.modal", function(){
		$("#myModalLabel3").html(companyName+"-"+point+"新增申报故障维修记录");
		$("#breakdownId").val(id);
		$("#addRepairForm")[0].reset();
		$("select").val("");
	});
}

function saveRepair(){
	$.ajax({
		type: "POST",
		url: "/reportRecord/saveRepair",
		dataType: "json",
		async: false,
		data: $("#addRepairForm").serialize(),
		error: function (data, transport) {
			swal("保存失败!", "系统异常,请联系管理员", "error");
		},
		success: function (data) {
			if(data.returnCode =="0000"){
        		$("#addRepair").modal("hide");
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

function getSelectedNode(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes();
	var nodeJson = '';
	for(var i=0;i<nodes.length;i++){
		var id=nodes[i].id;
		nodeJson+=id+',';
	}
	if(nodes.length>0){
		nodeJson = nodeJson.substring(0,nodeJson.length-1);
		nodeJson = nodeJson.replace(/s/g,'');
	}
	return nodeJson;
}

function changePoint(obj){
	if($(obj).val()!=''){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var node = treeObj.getNodeByParam('id','s'+$(obj).val());
		$("#companyName").val(node.getParentNode().name);
		$("#companyId").val(node.getParentNode().id);
	}else{
		$("#companyName").val('');
		$("#companyId").val('');
	}
}

function checkPhone(obj){
//	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
//	if(!myreg.test($(obj).val())) 
//	{ 
//		swal("提示", "请输入正确的手机号", "error");
////	    $(obj).focus();
//	}
}

function checkMoney(obj){
	var myreg = /^[0-9]*(\.[0-9]{1,2})?$/;
	if(!myreg.test($(obj).val()))
	{ 
		swal("提示", "请输入正确的金额", "error");
	    $(obj).focus();
	}
}