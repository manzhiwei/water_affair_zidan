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
			pointSelect.append(option);
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
//删除维修记录
$('.demo4').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
            title: "提示",
            text: "请先选中一条维修记录"
        });
	}else if(checkedLength>1){
		swal({
            title: "提示",
            text: "最多勾选一条记录"
        });
	}else{
		swal({
	        title: "删除维修记录",
	        text: "是否要删除该记录,一旦删除无法恢复!",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定删除!",
	        cancelButtonText:"取消",
	        showLoaderOnConfirm: true,
	        closeOnConfirm: false
	    }, function () {
	    	var userId = $("input[type='checkbox']:checked").val();
	    	$.ajax({
	            type: "POST",
	            url: "/repairRecord/deleteRepairRecord",
	            dataType: "json",
	            async: false,
	            data: {id:userId},
	            error: function (data, transport) {
	            	swal("删除失败!", "系统异常,请联系管理员", "error");
	            },
	            success: function (data) {
	            	if(data.returnCode =="0000"){
	            		swal({
	            	        title: "删除成功!",
	            	        text: "该维修记录已删除!",
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