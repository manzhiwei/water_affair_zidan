//删除用户
$('.demo4').click(function () {
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
		swal({
	        title: "删除用户",
	        text: "是否要删除该用户,一旦删除无法恢复!",
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
	            url: "/syssetting/om/deleteUser",
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
	            	        text: "该用户已删除!",
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
//修改用户
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
		var userId = $("input[type='checkbox']:checked").val();
		$.ajax({
	        type: "POST",
	        url: "/syssetting/om/getUserById",
	        dataType: "json",
	        async: false,
	        data: {id:userId},
	        error: function (data, transport) {
	        	swal("系统异常!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		$("#addUser").modal("show");
	        		var roles = data.returnData.roles;
	        		$("#addUser").on("shown.bs.modal", function(){
	        			$("#myModalLabel2").text("修改用户");
	        			$("#userId").val(data.returnData.id);
	        			$("#username").val(data.returnData.username);
	        			$("#realName").val(data.returnData.realName);
	        			$("#cellphone").val(data.returnData.cellphone);
	        			for(var i=0;i<roles.length;i++){
	        				$("select").val(roles[i].id);
	        			}
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


//保存用户
function saveUser(){
	if(checkForm()){
		$.ajax({
	        type: "POST",
	        url: "/syssetting/om/addUser",
	        dataType: "json",
	        async: false,
	        data: $("#addUserForm").serialize(),
	        error: function (data, transport) {
	        	swal("保存失败!", "系统异常,请联系管理员", "error");
	        },
	        success: function (data) {
	        	if(data.returnCode =="0000"){
	        		$("#addUser").modal("hide");
	        		swal({
	        	        title: "保存成功!",
	        	        text: "保存成功!",
	        	        type: "success",
	        	        confirmButtonText: "确定",
	        	        closeOnConfirm: false
	        	    },function () {
	        	    	//如果是当前用户则退出重新登录
	        	    var currentUser=$("#hidloginuser").val();
	        	    var modifyUser=$("#username").val();
	        	    if(currentUser==modifyUser){
	        	    	window.location.href="/logout"; 
	        	    }else{
	        	    		location.reload();
	        	    }
	        	    })
	        	}else{
	        		//没有别的情况,直接走else
	        		swal("保存失败!", data.message, "error");
	        	}
	        }
	    });
	}
}

function changeOption(){
	var selTexts = $("#roleId").find("option:selected");
	var selText = "";
	if(selTexts.length>0){
		var append = false;
		for(var i=0;i<selTexts.length;i++){
			if(!append){
				append = true;
			}else{
				selText+=",";
			}
			selText+=selTexts[i].text;
		}
	}
	$("#roleName").val(selText);
}

function checkForm(){
	if($("#username").val().trim()==''){
		swal("提示!", "请填写用户名", "warning");
		return false;
	}
	if($("#password").val()=='' || $("#password").val().length<6 || $("#password").val().length>12){
		swal("提示!", "请输入6-12位用户名密码", "warning");
		return false;
	}
	if($("#passwordConfirm").val()=='' || $("#passwordConfirm").val().length<6 || $("#passwordConfirm").val().length>12){
		swal("提示!", "请输入6-12位确认用户名密码", "warning");
		return false;
	}
	if($("#password").val()!=$("#passwordConfirm").val()){
		swal("提示!", "两次密码输入不一致", "warning");
		return false;
	}
	if($("#realName").val().trim()==''){
		swal("提示!", "请填写用户名称", "warning");
		return false;
	}
	if($("#cellphone").val().trim()=='' || $("#cellphone").val().trim().length!=11){
		swal("提示!", "请正确填写手机号", "warning");
		return false;
	}
	if($("#roleId").val()==''){
		swal("提示!", "请选择用户角色", "warning");
		return false;
	}
	return true;
}