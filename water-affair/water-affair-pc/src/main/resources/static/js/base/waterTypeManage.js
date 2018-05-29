var edit = false;
var oldDom = "";

////原始修改方法：之后将拆成2个一个黑臭，一个地表
//function modifyParam(obj){
//    if(edit){
//        swal("操作失败!", "请先保存编辑中的数据", "warning");
//        return;
//    }
//    var $obj = $(obj);
//    var param = $obj.parents("tr").find("input[data-attr='param']").val();
//    $.ajax({
//        type : "POST",
//        url : "getParamInfo",
//        dataType : "json",
//        data : {param:param},
//        success : function(json) {
//            edit=true;
//            var waters = json.returnData;
//            var paramDom=""+
//                "<input type='hidden' value='"+param+"' name='param'/>" +
//                "    <td>"+waters[0].paramName+"</td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[0].typeCode' value='1' />" +
//                "    <input type='hidden' name='water[0].level' value='合格' />" +
//                "    <input type='text' class='form-control' name='water[0].value1' value='"+waters[0].lowerLimit+"'/>" +
//                "    <select name='water[0].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[0].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[0].value2' value='"+waters[0].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[1].typeCode' value='1' />" +
//                "    <input type='hidden' name='water[1].level' value='轻度黑臭' />" +
//                "    <input type='text' class='form-control' name='water[1].value1' value='"+waters[1].lowerLimit+"'/>" +
//                "    <select name='water[1].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[1].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[1].value2' value='"+waters[1].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[2].typeCode' value='1' />" +
//                "    <input type='hidden' name='water[2].level' value='重度黑臭' />" +
//                "    <input type='text' class='form-control' name='water[2].value1' value='"+waters[2].lowerLimit+"'/>" +
//                "    <select name='water[2].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[2].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[2].value2' value='"+waters[2].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[3].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[3].level' value='Ⅰ类' />" +
//                "    <input type='text' class='form-control' name='water[3].value1' value='"+waters[3].lowerLimit+"'/>" +
//                "    <select name='water[3].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[3].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[3].value2' value='"+waters[3].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[4].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[4].level' value='Ⅱ类' />" +
//                "    <input type='text' class='form-control' name='water[4].value1' value='"+waters[4].lowerLimit+"'/>" +
//                "    <select name='water[4].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[4].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[4].value2' value='"+waters[4].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[5].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[5].level' value='Ⅲ类' />" +
//                "    <input type='text' class='form-control' name='water[5].value1' value='"+waters[5].lowerLimit+"'/>" +
//                "    <select name='water[5].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[5].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[5].value2' value='"+waters[5].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[6].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[6].level' value='Ⅳ类' />" +
//                "    <input type='text' class='form-control' name='water[6].value1' value='"+waters[6].lowerLimit+"'/>" +
//                "    <select name='water[6].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[6].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[6].value2' value='"+waters[6].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[7].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[7].level' value='Ⅴ类' />" +
//                "    <input type='text' class='form-control' name='water[7].value1' value='"+waters[7].lowerLimit+"'/>" +
//                "    <select name='water[7].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[7].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[7].value2' value='"+waters[7].upperLimit+"'/>" +
//                "    </td>" +
//                "    <td>" +
//                "    <input type='hidden' name='water[8].typeCode' value='2' />" +
//                "    <input type='hidden' name='water[8].level' value='劣Ⅴ类' />" +
//                "    <input type='text' class='form-control' name='water[8].value1' value='"+waters[8].lowerLimit+"'/>" +
//                "    <select name='water[8].option1' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "x" +
//                "<select name='water[8].option2' class='form-control'>" +
//                "    <option value='0'> </option>" +
//                "    <option value='1'><</option>" +
//                "    <option value='2'>></option>" +
//                "    <option value='3'>≤</option>" +
//                "    <option value='4'>≥</option>" +
//                "    </select>" +
//                "    <input type='text' class='form-control' name='water[8].value2' value='"+waters[8].upperLimit+"'/>" +
//                "    </td>" +
//                "   <td>"+
//                "   <a class='btn btn-sm btn-warning' onclick='javascript:saveParam(this)'>保存</a>" +
//                "   <a class='btn btn-sm btn-danger' onclick='javascript:cancelUpdate(this)'>取消</a>" +
//                "   </td>";
//            //dom edit
//            var tr = $obj.parents("tr");
//            oldDom = tr.children();
//            tr.empty();
//            tr.html(paramDom);
//            for(i=0;i<9;i++){
//                //左边表达式
//                if(waters[i].hasLower=='1' && waters[i].containLower=='0'){
//                    $("select[name='water["+i+"].option1']").val("1");
//                }else if(waters[i].hasLower=='1' && waters[i].containLower=='1'){
//                    $("select[name='water["+i+"].option1']").val("3");
//                }else{
//                    $("select[name='water["+i+"].option1']").val("0");
//                }
//                //右边表达式
//                if(waters[i].hasUpper=='1' && waters[i].containUpper=='0'){
//                    $("select[name='water["+i+"].option2']").val("1");
//                }else if(waters[i].hasUpper=='1' && waters[i].containUpper=='1'){
//                    $("select[name='water["+i+"].option2']").val("3");
//                }else{
//                    $("select[name='water["+i+"].option2']").val("0");
//                }
//            }
//
////			$obj.parent().html(button);
//        },
//        error: function (data, transport) {
//            swal("操作失败!", "系统异常,请联系管理员", "error");
//        }
//    });
//}

function modifyHeichouParam(obj){
    if(edit){
        swal("操作失败!", "请先保存编辑中的数据", "warning");
        return;
    }
    var $obj = $(obj);
    var param = $obj.parents("tr").find("input[data-attr='param']").val();
    $.ajax({
        type : "POST",
        url : "getParamInfo",
        dataType : "json",
        data : {param:param},
        success : function(json) {
            edit=true;
            var waters = json.returnData;
            var paramDom=""+
                "<input type='hidden' value='"+param+"' name='param'/>" +
                "    <td>"+waters[0].paramName+"</td>" +
                "    <td>" +
                "    <input type='hidden' name='water[0].typeCode' value='1' />" +
                "    <input type='hidden' name='water[0].level' value='合格' />" +
                "    <input type='text' class='form-control' name='water[0].value1' value='"+waters[0].lowerLimit+"'/>" +
                "    <select name='water[0].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[0].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[0].value2' value='"+waters[0].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[1].typeCode' value='1' />" +
                "    <input type='hidden' name='water[1].level' value='轻度黑臭' />" +
                "    <input type='text' class='form-control' name='water[1].value1' value='"+waters[1].lowerLimit+"'/>" +
                "    <select name='water[1].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[1].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[1].value2' value='"+waters[1].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[2].typeCode' value='1' />" +
                "    <input type='hidden' name='water[2].level' value='重度黑臭' />" +
                "    <input type='text' class='form-control' name='water[2].value1' value='"+waters[2].lowerLimit+"'/>" +
                "    <select name='water[2].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[2].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[2].value2' value='"+waters[2].upperLimit+"'/>" +
                "    </td>" +
                "   <td>"+
                "   <a class='btn btn-sm btn-warning' onclick='javascript:saveParam0(this)'>保存</a>" +
                "   <a class='btn btn-sm btn-danger' onclick='javascript:cancelUpdate(this)'>取消</a>" +
                "   </td>";
            //dom edit
            var tr = $obj.parents("tr");
            oldDom = tr.children();
            tr.empty();
            tr.html(paramDom);
            for(i=0;i<3;i++){
                //左边表达式
                if(waters[i].hasLower=='1' && waters[i].containLower=='0'){
                    $("select[name='water["+i+"].option1']").val("1");
                }else if(waters[i].hasLower=='1' && waters[i].containLower=='1'){
                    $("select[name='water["+i+"].option1']").val("3");
                }else{
                    $("select[name='water["+i+"].option1']").val("0");
                }
                //右边表达式
                if(waters[i].hasUpper=='1' && waters[i].containUpper=='0'){
                    $("select[name='water["+i+"].option2']").val("1");
                }else if(waters[i].hasUpper=='1' && waters[i].containUpper=='1'){
                    $("select[name='water["+i+"].option2']").val("3");
                }else{
                    $("select[name='water["+i+"].option2']").val("0");
                }
            }

//			$obj.parent().html(button);
        },
        error: function (data, transport) {
            swal("操作失败!", "系统异常,请联系管理员", "error");
        }
    });
}

function modifyDiBiaoParam(obj){
    if(edit){
        swal("操作失败!", "请先保存编辑中的数据", "warning");
        return;
    }
    var $obj = $(obj);
    var param = $obj.parents("tr").find("input[data-attr='param']").val();
    $.ajax({
        type : "POST",
        url : "getParamInfo",
        dataType : "json",
        data : {param:param},
        success : function(json) {
            edit=true;
            var waters = json.returnData;
            var paramDom=""+
                "<input type='hidden' value='"+param+"' name='param'/>" +
                "    <td>"+waters[0].paramName+"</td>" +
                "    <td>" +
                "    <input type='hidden' name='water[3].typeCode' value='2' />" +
                "    <input type='hidden' name='water[3].level' value='Ⅰ类' />" +
                "    <input type='text' class='form-control' name='water[3].value1' value='"+waters[3].lowerLimit+"'/>" +
                "    <select name='water[3].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[3].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[3].value2' value='"+waters[3].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[4].typeCode' value='2' />" +
                "    <input type='hidden' name='water[4].level' value='Ⅱ类' />" +
                "    <input type='text' class='form-control' name='water[4].value1' value='"+waters[4].lowerLimit+"'/>" +
                "    <select name='water[4].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[4].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[4].value2' value='"+waters[4].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[5].typeCode' value='2' />" +
                "    <input type='hidden' name='water[5].level' value='Ⅲ类' />" +
                "    <input type='text' class='form-control' name='water[5].value1' value='"+waters[5].lowerLimit+"'/>" +
                "    <select name='water[5].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[5].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[5].value2' value='"+waters[5].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[6].typeCode' value='2' />" +
                "    <input type='hidden' name='water[6].level' value='Ⅳ类' />" +
                "    <input type='text' class='form-control' name='water[6].value1' value='"+waters[6].lowerLimit+"'/>" +
                "    <select name='water[6].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[6].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[6].value2' value='"+waters[6].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[7].typeCode' value='2' />" +
                "    <input type='hidden' name='water[7].level' value='Ⅴ类' />" +
                "    <input type='text' class='form-control' name='water[7].value1' value='"+waters[7].lowerLimit+"'/>" +
                "    <select name='water[7].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[7].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[7].value2' value='"+waters[7].upperLimit+"'/>" +
                "    </td>" +
                "    <td>" +
                "    <input type='hidden' name='water[8].typeCode' value='2' />" +
                "    <input type='hidden' name='water[8].level' value='劣Ⅴ类' />" +
                "    <input type='text' class='form-control' name='water[8].value1' value='"+waters[8].lowerLimit+"'/>" +
                "    <select name='water[8].option1' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "x" +
                "<select name='water[8].option2' class='form-control'>" +
                "    <option value='0'> </option>" +
                "    <option value='1'><</option>" +
                "    <option value='2'>></option>" +
                "    <option value='3'>≤</option>" +
                "    <option value='4'>≥</option>" +
                "    </select>" +
                "    <input type='text' class='form-control' name='water[8].value2' value='"+waters[8].upperLimit+"'/>" +
                "    </td>" +
                "   <td>"+
                "   <a class='btn btn-sm btn-warning' onclick='javascript:saveParam1(this)'>保存</a>" +
                "   <a class='btn btn-sm btn-danger' onclick='javascript:cancelUpdate(this)'>取消</a>" +
                "   </td>";
            //dom edit
            var tr = $obj.parents("tr");
            oldDom = tr.children();
            tr.empty();
            tr.html(paramDom);
            for(i=3;i<9;i++){
                //左边表达式
                if(waters[i].hasLower=='1' && waters[i].containLower=='0'){
                    $("select[name='water["+i+"].option1']").val("1");
                }else if(waters[i].hasLower=='1' && waters[i].containLower=='1'){
                    $("select[name='water["+i+"].option1']").val("3");
                }else{
                    $("select[name='water["+i+"].option1']").val("0");
                }
                //右边表达式
                if(waters[i].hasUpper=='1' && waters[i].containUpper=='0'){
                    $("select[name='water["+i+"].option2']").val("1");
                }else if(waters[i].hasUpper=='1' && waters[i].containUpper=='1'){
                    $("select[name='water["+i+"].option2']").val("3");
                }else{
                    $("select[name='water["+i+"].option2']").val("0");
                }
            }

//			$obj.parent().html(button);
        },
        error: function (data, transport) {
            swal("操作失败!", "系统异常,请联系管理员", "error");
        }
    });
}

//function saveParam(obj) {
//    $.ajax({
//        type : "POST",
//        url : "saveWaterLevel",
//        dataType : "json",
//        data : $("#editForm").serialize(),
//        success : function(json) {
//            edit = false;
//            swal({
//                title: "保存成功!",
//                text: "保存成功!",
//                type: "success",
//                confirmButtonText: "确定",
//                closeOnConfirm: false
//            },function () {
//                location.reload();
//            })
//        },
//        error: function (data, transport) {
//            swal("操作失败!", "系统异常,请联系管理员", "error");
//        }
//    });
//}

function saveParam0(obj) {
    $.ajax({
        type : "POST",
        url : "saveWaterLevel",
        dataType : "json",
        data : $("#editForm0").serialize(),
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

function saveParam1(obj) {
    $.ajax({
        type : "POST",
        url : "saveWaterLevel",
        dataType : "json",
        data : $("#editForm1").serialize(),
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