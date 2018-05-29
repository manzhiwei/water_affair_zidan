var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: onClick,
		onCheck: onCheck
	}
};

var zNodes ='';

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	id = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		id += nodes[i].id +",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (id.length > 0) id = id.substring(0, id.length - 1).replace(/s/g,'');
	var pointName = $("#pointName");
	pointName.attr("value", v);
	var pointId = $("#pointId");
	pointId.attr("value", id);
	var stationId = $("#station").val();
	$("#firstLoad").val(stationId!=pointId.val())
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
	if (!(event.target.id == "menuBtn" || event.target.id == "pointName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(document).ready(function(){
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
	
	var chartTypeValue = $("#reportType").val();
	$("div[id^='picker']").each(function(){
		$(this).addClass("hide");
	});
	$("#picker" + chartTypeValue).removeClass("hide");
});

$('.hourpicker').datetimepicker({
	language: 'zh-CN',
	autoclose: true,
	minView: 1
});

$('.datepicker').datetimepicker({
	language: 'zh-CN',
	autoclose: true,
	format: 'yyyy-mm-dd',
	minView: 2
});

$('.monthpicker').datetimepicker({
	language: 'zh-CN',
	autoclose: true,
	format: 'yyyy-mm',
	startView: 3,
	minView: 3
});

$('.yearpicker').datetimepicker({
	language: 'zh-CN',
	autoclose: true,
	format: 'yyyy',
	startView: 4,
	minView: 4
});

$("#reportType").change(function(){
	var chartTypeValue = $(this).val();
	$("div[id^='picker']").each(function(){
		$(this).addClass("hide");
	});
	$("#picker" + chartTypeValue).removeClass("hide");
});