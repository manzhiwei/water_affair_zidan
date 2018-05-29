$(document).ready(function(){
    $("#mainTable").DataTable({
        language: {
            search: "在表格中搜索:",
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        lengthChange: false,
        searching: false,
        info: false,
        processing: true,
        serverSide: true,
        ajax: {
            url: "data.json",
            type: "POST"
        },
        order: [[ 8, 'asc' ]],
        columnDefs: [{
            targets: [0, 2, 3, 9],
            orderable: false
        }],
        columns: [{
            data: "mcuId",
            render: function(data) {
                return '<div class="checkbox checkbox-primary m-n">\
                    <input type="checkbox" name="mcuId" value="' + data + '"/>\
                    <label></label>\
                    </div>';
            }
        },{
            data: "stationName",
            render: handleData
        },{
            data: "longitude",
            render: handleLongitude
        },{
            data: "latitude",
            render: handleLatitude
        },{
            data: "cardNo",
            render: handleData
        },{
            data: "projectCode",
            render: handleData
        },{
            data: "connnectTime",
            render: handleDate
        },{
            data: "controllerAddress",
            render: handleData
        },{
            data: "mcuId",
            render: handleData
        },{
            data: "mcuId",
            render: function(data){
                return '<a href="modbus.html?mcuId=' + data + '" class="btn btn-primary btn-xs">传感器信息</a>\
                    <a href="param.html?mcuId=' + data + '" class="btn btn-primary btn-xs">参数查看</a>\
                    <a href="message.html?mcuId='+ data + '" class="btn btn-primary btn-xs">消息查看</a>';
            }
        }]
    });
});

var handleData = function(data){
    if(data){
        return data;
    }
    return "--";
}

var handleDate = function(data){
    if(data){
        return formatDate(data);
    }
    return "--";
}

var handleLongitude = function(data){
    if(data){
        if(data.length > 8){
            return data.substring(0, 8);
        } else {
            return data;
        }
    }
    return "--";
}

var handleLatitude = function(data){
    if(data){
        if(data.length > 7){
            return data.substring(0, 7);
        } else {
            return data;
        }
    }
    return "--";
}

$('.modal').on('show.bs.modal', function (e) {
    var result = true;
    if($("input[type='checkbox'][name='mcuId']:checked").length == 0){
        result = false;
        showErrorInfo();
    } else{
        var $obj = $(e.target);
        $obj.find("input[name='mcuIds']").remove();
        $("input[type='checkbox'][name='mcuId']:checked").each(function(){
            var input = '<input type="hidden" name="mcuIds" value="' + $(this).val() + '">';
            $obj.find("form").append(input);
        });
    }
    return result;
});

toastr.options = {
    "closeButton": true,
    "debug": false,
    "progressBar": false,
    "preventDuplicates": false,
    "positionClass": "toast-top-right",
    "onclick": null,
    "showDuration": "400",
    "hideDuration": "1000",
    "timeOut": "2000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

function showErrorInfo(){
    toastr.error('至少选择一个设备', '提示');
}

$("#readParamForm select[name='jobCode']").change(function(){
    $("#readParamForm input[name='type']").val('');
    if($(this).val()){
        var type = + $(this).val().substr(2, 2) - 2;
        $("#readParamForm input[name='type']").val(type);

        if('WT04002' == $(this).val()){
            $("#waterParam").show();
            $.ajax({
                url: 'paramCode.json',
                type: 'post',
                data: "type=" + type,
                dataType: "json",
                success: function(res){
                    var html = '<option></option>';
                    if(res.data){
                        for(var index in res.data){
                            html += '<option value="' + res.data[index].code + '">' + res.data[index].name + '</option>'
                        }
                    }
                    $("#waterParam select").html(html);
                }
            })
        } else{
            $("#waterParam").hide();
        }
    } else{
        $("#waterParam").hide();
    }
});


$("#writeParamForm select[name='jobCode']").change(function(){
    $("#writeParamForm select[name='code']").empty();
    if($(this).val()){
        var type = + $(this).val().substr(2, 2) - 2;
        $("#writeParamForm input[name='type']").val(type);
        $.ajax({
            url: 'paramCode.json',
            type: 'post',
            data: "type=" + type,
            dataType: "json",
            success: function(res){
                var html = '<option></option>';
                if(res.data){
                    for(var index in res.data){
                        html += '<option value="' + res.data[index].code + '">' + res.data[index].name + '</option>'
                    }
                }
                $("#writeParamForm select[name='code']").html(html);
            }
        });
    }
});

function sendOrder(obj){
    if($("#sendOrderForm").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'sendOrder.json',
            type: "POST",
            data: $("#sendOrderForm").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}

function readParam(obj){
    if($("#readParamForm").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'readParam.json',
            type: "POST",
            data: $("#readParamForm").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}

function readParam1(obj){
    if($("#readParamForm1").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'readParam1.json',
            type: "POST",
            data: $("#readParamForm1").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}

function writeParam(obj){
    if($("#writeParamForm").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'writeParam.json',
            type: "POST",
            data: $("#writeParamForm").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}

function writeParam1(obj){
    if($("#writeParamForm1").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'writeParam1.json',
            type: "POST",
            data: $("#writeParamForm1").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}

function writeParam2(obj){
    if($("#writeParamForm2").valid()){
        var l = $(obj).ladda();
        l.ladda('start');
        $.ajax({
            url: 'writeParam2.json',
            type: "POST",
            data: $("#writeParamForm2").serialize(),
            dataType: "json",
            success: function(res){
                toastr.success('操作成功', '提示');
            },
            complete: function(){
                l.ladda('stop');
            },
            error: function(){
                toastr.error('操作失败', '提示');
            }
        });
    }
}