function checkEditPwdForm(){
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var confirmPwd = $("#confirmPwd").val();
	if(oldPwd == ''){
		alert("请输入原密码");
		$("#oldPwd").focus();
		return;
	}
	if(newPwd == ''){
		alert("请输入新密码");
		$("#newPwd").focus();
		return;
	}
	if(confirmPwd == ''){
		alert("请确认新密码");
		$("#confirmPwd").focus();
		return;
	}
	if(newPwd != confirmPwd){
		alert("两次密码输入不一致");
		return;
	}
	$.ajax({
        type: "POST",
        url: "editPwd",
        dataType: "json",
        async: false,
        data: {oldPwd:oldPwd,newPwd:newPwd,confirmPwd:confirmPwd},
        error: function (data, transport) {
        	alert("系统超时，请刷新后重试。");
        },
        success: function (data) {
        	if(data.returnCode =="0000"){
        		$("#editPwd").modal("hide");
        		alert("修改成功")
        	}else{
        		//没有别的情况,直接走else
        		alert(data.message);
        		$("#oldPwd").focus();
        	}
        }
    });
}

/**
 * 将日期格式化成指定格式的字符串
 * @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
 * @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
 * @returns 返回格式化后的日期字符串
 */
function formatDate(date, fmt)
{
    date = date == undefined ? new Date() : date;
    date = typeof date == 'number' ? new Date(date) : date;
    fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
    var obj =
    {
        'y': date.getFullYear(), // 年份，注意必须用getFullYear
        'M': date.getMonth() + 1, // 月份，注意是从0-11
        'd': date.getDate(), // 日期
        'q': Math.floor((date.getMonth() + 3) / 3), // 季度
        'w': date.getDay(), // 星期，注意是0-6
        'H': date.getHours(), // 24小时制
        'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
        'm': date.getMinutes(), // 分钟
        's': date.getSeconds(), // 秒
        'S': date.getMilliseconds() // 毫秒
    };
    var week = ['天', '一', '二', '三', '四', '五', '六'];
    for(var i in obj)
    {
        fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m)
        {
            var val = obj[i] + '';
            if(i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
            for(var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
            return m.length == 1 ? val : val.substring(val.length - m.length);
        });
    }
    return fmt;
}

$('a[href="' + window.location.pathname + '"]').parent().parent().parent().addClass("active");
$('a[href="' + window.location.pathname + '"]').parent().parent().removeClass("collapse");
$('a[href="' + window.location.pathname + '"]').parent().addClass("active");

$('.datetimepicker').datetimepicker({
	language: 'zh-CN',
	autoclose: 1,
	format: 'yyyy-mm-dd hh:ii:ss'
});