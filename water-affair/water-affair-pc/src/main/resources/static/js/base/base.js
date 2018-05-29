/*
 * base.js 
 * Created by zhujia
 */

/**
 * 可以将时间对象格式化
 * 使用方法:new Date(254000000).Format("yyyy-MM-dd hh:mm:ss")
 */
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

/**
 * jquery提交验证
 * 可支持拓展验证,见下方参考示例,其他的验证方式可通过regex.js中的正则去判断
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
	maxlength: $.validator.format( "输入长度最多是 {0} 的字符串（汉字算一个字符）." ),
    minlength: $.validator.format( "输入长度最小是 {0} 的字符串（汉字算一个字符）." ),
    rangelength: $.validator.format( "输入长度必须介于{0}和 {1} 之间的字符串（汉字算一个字符）." ),
    range: $.validator.format( "输入值必须介于 {0} 和 {1} 之间 ." ),
    max: $.validator.format( "输入值不能大于 {0}." ),
    min: $.validator.format( "输入值不能小于  {0}." )
};

jQuery.extend(jQuery.validator.messages, jqValidMsg);

//拓展验证--中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
  return this.optional(element) || ( length >= param[0] && length <= param[1] );   
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

//拓展验证--邮政编码验证   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

