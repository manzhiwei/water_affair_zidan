//删除角色
$('.demo4').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
            title: "提示",
            text: "请先选中一个角色"
        });
	}else if(checkedLength>1){
		swal({
            title: "提示",
            text: "最多勾选一个角色"
        });
	}else{
		swal({
	        title: "删除角色",
	        text: "是否要删除该角色,一旦删除无法恢复!",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定删除!",
	        cancelButtonText:"取消",
	        showLoaderOnConfirm: true,
	        closeOnConfirm: false
	    }, function () {
	    	var roleId = $("input[type='checkbox']:checked").val();
	    	$.ajax({
	            type: "POST",
	            url: "/syssetting/rm/deleteRole",
	            dataType: "json",
	            async: false,
	            data: {id:roleId},
	            error: function (data, transport) {
	            	swal("删除失败!", "系统异常,请联系管理员", "error");
	            },
	            success: function (data) {
	            	if(data.returnCode =="0000"){
	            		swal({
	            	        title: "删除成功!",
	            	        text: "该角色已删除!",
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
//修改角色
$('.demo3').click(function () {
	var checkedLength = $("input[type='checkbox']:checked").length;
	if(checkedLength<1){
		swal({
			title: "提示",
			text: "请先选中一个用户"
		});
	}else if(checkedLength>1){
		swal({
			title: "提示",
			text: "最多勾选一个用户"
		});
	}else{
		var roleId = $("input[type='checkbox']:checked").val();
		$.ajax({
	        type: "POST",
	        url: "/syssetting/rm/getRoleById",
	        dataType: "json",
	        async: false,
	        data: {roleId:roleId},
	        error: function (data, transport) {
	        	swal("系统异常!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		getMenu(roleId);
	        		$("#addRole").modal("show");
	        		var roles = data.returnData.roles;
	        		$("#addRole").on("shown.bs.modal", function(){
	        			$("#myModalLabel2").text("修改角色");
	        			$("#roleId").val(data.returnData.id);
	        			$("#roleName").val(data.returnData.roleName);
	        			$("#roleDescription").val(data.returnData.roleDescription);
	        			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	        		})
	        	}else{
            		//没有别的情况,直接走else
            		swal("操作失败!", data.message, "error");
            	}
	        }
	    });
	}
});
//保存角色
function saveRole(){
	$("#myModalLabel2").text("新增角色");
	var menuId = getSelectedNode();
	var id = $("#roleId").val();
	var roleName = $("#roleName").val();
	var roleDescription = $("#roleDescription").val();
	var data = {id:id,roleName:roleName,roleDescription:roleDescription,menuId:menuId}
	$.ajax({
        type: "POST",
        url: "/syssetting/rm/addRole",
        dataType: "json",
        async: false,
        data: data,
        error: function (data, transport) {
        	swal("保存失败!", "系统异常,请联系管理员", "error");
        },
        success: function (data) {
        	if(data.returnCode =="0000"){
        		$("#addRole").modal("hide");
        		swal({
        	        title: "保存成功!",
        	        text: "保存角色成功!",
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

//根据id获取菜单
function getMenu(id){
	$.ajax({
        type: "POST",
        url: "/syssetting/rm/getRoleMenu",
        dataType: "json",
        async: false,
        data: {roleId:id},
        error: function (data, transport) {
        	console.error("用户id:"+id+",无法获取菜单,请联系管理员");
        },
        success: function (data) {
        	if(data.returnCode =="0000"){
        		console.info("用户id:"+id+",菜单获取成功");
        		zNodes=data.returnData;
        	}else{
        	}
        }
    });
}

function showAddRole(){
	getMenu('');
//	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#addRole").modal('show');
	$("#addRole").on("shown.bs.modal", function(){
		$("#myModalLabel2").text("新增角色");
		$("#roleId").val("");
		$("#roleName").val("");
		$("#roleDescription").val("");
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	})
}

function getSelectedNode(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes();
	var nodeJson = '';
	for(var i=0;i<nodes.length;i++){
		var id=nodes[i].id;
//		nodeJson+='{id:'+id+'},';
		nodeJson+=id+',';
	}
	if(nodes.length>0)
		nodeJson = nodeJson.substring(0,nodeJson.length-1);
	return nodeJson;
}