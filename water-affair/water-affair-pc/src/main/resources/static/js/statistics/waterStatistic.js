$(".chosen-select").chosen();

function search() {
    searchHeichou();
    searchDibiao();
	return false;
}

var heichouGlobalData;
var dibiaoGlobalData;
function searchHeichou(){
	if($("#searchForm").valid() && $("#params").val()){
        var l = $("button[type='submit']").ladda();
        l.ladda('start');
		$.ajax({
			url: "/waterStatisticHeichouData",
			type: "POST",
			data: $("#searchForm").serialize(),
			dataType: "json",
			success: function(result){
				if(result){
					var timeData = [];
					var inputs = $("div[id^=picker]").not(".hide").find("input[type='text']");
					inputs.each(function(){
						timeData.push($(this).val());
					});
					$("#timeData").html(timeData.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
					
					heichouGlobalData = result;

                    $("#heichouTab").hide();
                    for(var key in result){
                        if(+result[key] > 0){
                            $("#heichouTab").show();
                            break;
                        }
                    }

                    createHeichouChart();
					createTable(result,"1");

				}
			},
			complete: function () {
                l.ladda('stop');
            }
		});
	}
	return false;
}

function searchDibiao(){
    if($("#searchForm").valid() && $("#params").val()){
        $.ajax({
            url: "/waterStatisticDibiaoData",
            type: "POST",
            data: $("#searchForm").serialize(),
            dataType: "json",
            success: function(result){
                if(result){
                    var timeData = [];
                    var inputs = $("div[id^=picker]").not(".hide").find("input[type='text']");
                    inputs.each(function(){
                        timeData.push($(this).val());
                    });
                    $("#timeData").html(timeData.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));

                    dibiaoGlobalData = result;

                    $("#dibiaoTab").hide();
                    for(var key in result){
                    	if(+result[key] > 0){
                    		$("#dibiaoTab").show();
                    		break;
						}
					}

                    createDibiaoChart();
                    createTable(result,"2");
                }
            }
        });
    }
    return false;
}

/*function getParam(flag){
	$.ajax({
		url:'/singleParamStatisticParam',
		type:'POST',
		data: "stationId=" + $('#pointId').val(),
		dataType: 'JSON',
		success: function(result){
			var paramsSelect = $("#params");
			paramsSelect.empty();
			for(var index in result){
				if(result[index].display == 1){
					var obj = $("<option></option>");
					obj.attr('value',result[index].param);
					obj.text(result[index].paramName);
					paramsSelect.append(obj);
				}
			}
			paramsSelect.val($("#paramVal").val());
			paramsSelect.chosen("destroy");
			paramsSelect.chosen({});
			if(flag){
				searchHeichou();
				searchDibiao();
			}
		}
	});
}*/

//$('select[name="stationId"]').change(getParam);

$('.hourpicker').datetimepicker({
	language: 'zh-CN',
	autoclose: true,
	format: 'yyyy-mm-dd hh',
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

$("#chartType").change(function(){
	var chartTypeValue = $(this).val();
	$("div[id^='picker']").each(function(){
		$(this).addClass("hide");
	});
	$("#picker" + chartTypeValue).removeClass("hide");
});

$("input[name='typeRadio']").change(function(){
	$("#typeCode").val($(this).val());
	search();
});

$("input[name='chartHeichouRadio']").change(createHeichouChart);
$("input[name='chartDibiaoRadio']").change(createDibiaoChart);

function createHeichouChart(){
	var legendData = [];
	var seriesData = [];
	var seriesName = '黑臭三分';

	if($("input[name='chartHeichouRadio']:checked").val() == "bar"){
		//柱状图
		var color = [ '#91c7ae', '#61a0a8','#c23531'];

		var index = 0;
		for(var level in heichouGlobalData){
			legendData.push(level);
			seriesData.push({
				value: heichouGlobalData[level],
				itemStyle: { 
					normal: { color: color[index]} 
				}				
			});
			index++;
		}
		createChartOfBar(legendData, seriesName, seriesData,"1");
	} else{
		//饼图
		for(var level in heichouGlobalData){
			legendData.push(level);
			seriesData.push({
				value: heichouGlobalData[level],
				name: level							
			});
		}
		createChartOfPie(legendData,seriesName,seriesData,"1");
	}
	
	
}

function createDibiaoChart(){
    var legendData = [];
    var seriesData = [];
    var seriesName = '地表水质';

    if($("input[name='chartDibiaoRadio']:checked").val() == "bar"){
        //柱状图
        var color = color = [ '#91c7ae', '#61a0a8', '#749f83', '#bda29a', '#ca8622', '#c23531'];
        var index = 0;
        for(var level in dibiaoGlobalData){
            legendData.push(level);
            seriesData.push({
                value: dibiaoGlobalData[level],
                itemStyle: {
                    normal: { color: color[index]}
                }
            });
            index++;
        }
        createChartOfBar(legendData, seriesName, seriesData,"2");
    } else{
        //饼图
        for(var level in dibiaoGlobalData){
            legendData.push(level);
            seriesData.push({
                value: dibiaoGlobalData[level],
                name: level
            });
        }
        createChartOfPie(legendData,seriesName,seriesData,"2");
    }


}

/**
 * 生成饼图
 * @param legendData ['重度黑臭','轻度黑臭','合格']
 * @param seriesName '黑臭三分'
 * @param seriesData [{value:235, name:'重度黑臭'},{value:310, name:'轻度黑臭'},{value:400, name:'合格'}]
 */
function createChartOfPie(legendData, seriesName, seriesData,typeCode){
	if(typeCode=='1'){
        // 基于准备好的dom，初始化echarts实例
        echarts.dispose(document.getElementById('main'));
        var myChart = echarts.init(document.getElementById('main'));
	}else {
        echarts.dispose(document.getElementById('main2'));
        var myChart = echarts.init(document.getElementById('main2'));
	}
	// 指定图表的配置项和数据
	var option = {
	    tooltip:{
	        trigger: 'item',
	        formatter: "{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: legendData
	    },
	    series: [{
	        name: seriesName,
	        type: 'pie',
	        data: seriesData,
	        startAngle: 30,
	        itemStyle: {
	            emphasis: {
	                shadowBlur: 10,
	                shadowOffsetX: 0,
	                shadowColor: 'rgba(0, 0, 0, 0.5)'
	            }
	        }
	    }]
	};
	
	//color: ['#c23531', '#61a0a8', '#91c7ae', '#bda29a', '#ca8622', '#749f83','#6e7074', '#546570', '#d48265', '#c4ccd3'],
	option.color = [ '#10B009', '#CB010F','#060606'];
	if(seriesName != '黑臭三分') {
		option.color = [ '#5EBEFD', '#10B009', '#E1C30C', '#FD7201', '#CB010F', '#060606'];
	}
	

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

/**
 * 生成柱状图
 * @param xAxisData ['重度黑臭','轻度黑臭','合格']
 * @param seriesName '黑臭三分'
 * @param seriesData [ {value: 150, itemStyle: { normal: { color: option.color[0]} } }, {value: 100, itemStyle: { normal: { color: option.color[1]} } }]
 */
function createChartOfBar(xAxisData, seriesName, seriesData, typeCode){
    if(typeCode=='1'){
        echarts.dispose(document.getElementById('main'));
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
    }else {
        echarts.dispose(document.getElementById('main2'));
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main2'));
    }

	// 指定图表的配置项和数据
	var option = {
	    color: ['#c23531', '#61a0a8', '#91c7ae','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'],
	    tooltip:{
	        trigger: 'item',
	        formatter: "{b} : {c}个"
	    },
	    xAxis: {
	        data: xAxisData
	    },
	    yAxis: {},
	    series: [{
	        name: seriesName,
	        type: 'bar',
	        barMaxWidth: '40px',
	        data: seriesData
	    }]
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function createTable(datas,typeCode){
	var html = '';
	var total = 0;
	for(var level in datas){
		total += +datas[level];
	}
	if(total == 0){
		total = 1;
	}
	for(var level in datas){
		html += '<tr>'
				+ '<td>' + level + '</td>'
				+ '<td>' + datas[level] + '</td>'
				+ '<td class="text-navy">' + (+datas[level]/total*100).toFixed(0) + ' % </td>'
				+ '</tr>';
		if(typeCode=='1'){
            $("#dataTable").html(html);
		}else{
            $("#dataTable2").html(html);
        }
	}
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
    },
    plugins:["wholerow","checkbox"]
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
/*    if (id.length > 0)
        id = id.substring(0, id.length - 1).replace(/s/g,'');*/
    var pointName = $("#pointName");
    pointName.attr("value", v);
    var pointId = $("#pointId");
    pointId.attr("value", id);
}

function showMenu() {
    var pointName = $("#pointName");
    var pointOffset = $("#pointName").offset();
    $("#menuContent").css({left:132 + "px", top:226 + "px"}).slideDown("fast");
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



$(function(){

    var date = new Date();
    if(!$("input[name='endTime']").val()){
        $("input[name='endTime']").val(formatDate(date, 'yyyy-MM-dd HH'));
    }
    if(!$("input[name='date']").val()){
        $("input[name='date']").val(formatDate(date, 'yyyy-MM-dd'));
    }
    if(!$("input[name='month']").val()){
        $("input[name='month']").val(formatDate(date, 'yyyy-MM'));
    }
    if(!$("input[name='year']").val()){
        $("input[name='year']").val(formatDate(date, 'yyyy'));
    }
    var time = date.getTime();
    time = time - 4 * 60 * 60 * 1000;
    date.setTime(time);
    if(!$("input[name='startTime']").val()){
        $("input[name='startTime']").val(formatDate(date, 'yyyy-MM-dd HH'));
    }

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
                if(data.returnData){
                	for(var index in data.returnData){
                		if(data.returnData[index].id != 0){
                            data.returnData[index].checked = "checked";
                            var pointName = $("#pointName");
                            pointName.attr("value", data.returnData[index].name);
                            var pointId = $("#pointId");
                            pointId.attr("value",  data.returnData[index].id);
                            break;
						}
					}
                    for(var index in data.returnData){
                        data.returnData[index].nocheck = false;
                    }
				}
                zNodes=data.returnData;

                search();
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
            var node = treeObj.getNodeByParam('id',ids[i]);
            treeObj.checkNode(node, true, true);
            var option = '<option value='+ids[i]+'>'+node.name+'</option>';
//			pointSelect.append(option);
        }
    }

});