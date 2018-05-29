$('input').on('ifChecked', function(event){  
  var id = $(this).parents("tr").find("input[name=id]").val();
  var val = $(this).val();
  changeStatus(id,val,this);
});  

function changeStatus(id,status,obj) {
	var statusStr = status=='0'?'关闭':'打开';
	var $obj = $(obj);
	var span = '';
	var button = '';
	if(status=='0'){
		//操作完后改为不显示状态且更换按钮为显示
		span = '<i class="fa fa-close text-danger"></i>';
		button = '<button type="button" class="btn btn-w-m btn-primary btn-sm" onclick="javascript:changeStatus(\''+id+'\',\'1\',this);">显示</button>';
	}else{
		//操作完后改为显示状态且更换按钮为不显示
		span = '<i class="fa fa-check text-navy"></i>';
		button = '<button type="button" class="btn btn-w-m btn-danger btn-sm" onclick="javascript:changeStatus(\''+id+'\',\'0\',this);">不显示</button>';
	}
	$.ajax({
		type : "POST",
		url : "changeStatus",
		dataType : "json",
		data : {id:id,display:status},
		success : function(json) {
			swal("操作成功!", "该参数已"+statusStr, "success");
			//dom edit
			$obj.parents("td").prev().html(span);
//			$obj.parent().html(button);
		},
		error: function (data, transport) {
        	swal("操作失败!", "系统异常,请联系管理员", "error");
        }
	});
}

var edit = false;
var oldDom = "";

function modifyParam(obj){
    if(edit){
        swal("操作失败!", "请先保存编辑中的数据", "warning");
        return;
    }
    var $obj = $(obj);
    var id = $obj.parents("tr").find("input[data-attr='param']").val();
    $.ajax({
        type : "POST",
        url : "getParamInfo",
        dataType : "json",
        data : {id:id},
        success : function(json) {
            edit=true;
            var param = json.returnData;
            var paramDom="" +
                "<input type='hidden' value='"+param.id+"' name='id'>" +
                "<td>"+param.id+"</td>" +
                "<td>"+param.paramName+"</td>" +
                "<td>"+param.unit+"</td>" +
                "<td>" +
                "    <select data-placeholder='请选择参数类型' class='form-control required' name='roundType' id='roundType'>" +
                "        <option value='1'>整型</option>" +
                "        <option value='2'>浮点型</option>" +
                "    </select>" +
                "</td>" +
                "<td>" +
                "    <select data-placeholder='请选择是否显示' class='form-control required' name='display' id='display'>" +
                "        <option value='1'>是</option>" +
                "        <option value='0'>否</option>" +
                "    </select>" +
                "</td>" +
                "<td>" +
                "    <select data-placeholder='请选择是否参与评价' class='form-control required' name='involved' id='involved'>" +
                "        <option value='1'>是</option>" +
                "        <option value='0'>否</option>" +
                "    </select>" +
                "</td>" +
                "<td>" +
                "    <select data-placeholder='请选择黑臭评价标准' class='form-control required' name='heichou' id='heichou'>" +
                "        <option value='1'>合格</option>" +
                "        <option value='2'>轻度黑臭</option>" +
                "        <option value='3'>重度黑臭</option>" +
                "    </select>" +
                "</td>" +
                "<td>" +
                "    <select data-placeholder='请选择地表评价标准' class='form-control required' name='dibiao' id='dibiao'>" +
                "        <option value='1'>Ⅰ类</option>" +
                "        <option value='2'>Ⅱ类</option>" +
                "        <option value='3'>Ⅲ类</option>" +
                "        <option value='4'>Ⅳ类</option>" +
                "        <option value='5'>Ⅴ类</option>" +
                "        <option value='6'>劣Ⅴ类</option>" +
                "    </select>" +
                "</td>" +
                "<td>"+
                "   <a class='btn btn-sm btn-warning' onclick='javascript:saveParam(this)'>保存</a>" +
                "   <a class='btn btn-sm btn-danger' onclick='javascript:cancelUpdate(this)'>取消</a>" +
                "</td>";
            //dom edit
            var tr = $obj.parents("tr");
            oldDom = tr.children();
            tr.empty();
            tr.html(paramDom);
            $("select[name='roundType']").val(param.roundType);
            $("select[name='display']").val(param.display);
            $("select[name='involved']").val(param.involved);
            $("select[name='heichou']").val(param.heichou);
            $("select[name='dibiao']").val(param.dibiao);


//			$obj.parent().html(button);
        },
        error: function (data, transport) {
            swal("操作失败!", "系统异常,请联系管理员", "error");
        }
    });
}

function saveParam(obj) {
    $.ajax({
        type : "POST",
        url : "saveWtParam",
        dataType : "json",
        data : $("#editForm").serialize(),
        success : function(json) {
            edit = false;
            swal({
                title: "保存成功!",
                text: "保存成功!",
                type: "success",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },function () {
                location.reload();
            })
        },
        error: function (data, transport) {
            swal("操作失败!", "系统异常,请联系管理员", "error");
        }
    });
}

function cancelUpdate(obj) {
    var $obj = $(obj);
    var tr = $obj.parents("tr");
    tr.empty();
    tr.html(oldDom);
    edit = false;
}