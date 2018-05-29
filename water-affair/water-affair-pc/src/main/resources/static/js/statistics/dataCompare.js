function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:140 + "px", top:225 + "px"}).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function search(){
	var p = $("#params").val();
	if($("#searchForm").valid()){
		$.ajax({
			url: "/dataCompareData",
			type: "POST",
			data: $("#searchForm").serialize(),
			dataType: "json",
			success: function(result){
				var count=Object.keys(result).length;//得到result大小
//				'时间段1','时间段2','时间段3','时间段4','时间段5','时间段6',
				var legendData = ['平均值'];
				var xAxisData = [];
				var avgData = [];
				var seriesData = [
//					{name:'时间段1',type:'line',data:data1},
//					{name:'时间段2',type:'line',data:data2},
//					{name:'时间段3',type:'line',data:data3},
//					{name:'时间段4',type:'line',data:data4},
//					{name:'时间段5',type:'line',data:data5},
//					{name:'时间段6',type:'line',data:data6},
//					{name:'平均值',type:'line',data:avgData}
				];
				for(var i=0;i<count;i++){
					legendData.unshift("时间段"+(i+1));
					var data1 = [];
					for(var j=0;j< result["data"+(i+1)].length;j++) {
						data1.push({
							name:formatDate(result["data"+(i+1)][j].time),
							value:result["data"+(i+1)][j][p]
						});
						//将内部循环获得的数据跟平均值加起来
						if(avgData.length>j){
							avgData[j]=avgData[j]+result["data"+(i+1)][j][p];
						}else{
							avgData.push(+result["data"+(i+1)][j][p]);
							xAxisData.push('');	
						}
					}
					var val = {name:'时间段'+(i+1),type:'line',data:data1}
					seriesData.push(val);
				}
				//最后则计算平均值
				if(count>0){
					for(var n=0;n<avgData.length;n++){
						avgData[n]=((avgData[n])/count).toFixed(4);
					}
				}
				seriesData.push({name:'平均值',type:'line',data:avgData});
				
//				if(!result.data1){
//					result.data1 = [];
//				}
//				if(!result.data2){
//					result.data2 = [];
//				}
//				if(!result.data3){
//					result.data3 = [];
//				}
//				if(!result.data4){
//					result.data4 = [];
//				}
//				if(!result.data5){
//					result.data5 = [];
//				}
//				if(!result.data6){
//					result.data6 = [];
//				}
//				
//				var data1 = [];
//				var data2 = [];
//				var data3 = [];
//				var data4 = [];
//				var data5 = [];
//				var data6 = [];
//				var avgData = [];
//				for(var i=0; i< result.data1.length || i< result.data2.length || i< result.data3.length|| i< result.data4.length|| i< result.data5.length|| i< result.data6.length; i++){
//					if(i< result.data1.length) {
//						data1.push({
//							name:formatDate(result.data1[i].time),
//							value:result.data1[i][p]
//						});
//					}
//					if(i< result.data2.length) {
//						data2.push({
//							name:formatDate(result.data2[i].time),
//							value:result.data2[i][p]
//						});
//					}
//					if(i< result.data3.length) {
//						data3.push({
//							name:formatDate(result.data3[i].time),
//							value:result.data3[i][p]
//						});
//					}
//					if(i< result.data4.length) {
//						data4.push({
//							name:formatDate(result.data4[i].time),
//							value:result.data4[i][p]
//						});
//					}
//					if(i< result.data5.length) {
//						data5.push({
//							name:formatDate(result.data5[i].time),
//							value:result.data5[i][p]
//						});
//					}
//					if(i< result.data6.length) {
//						data6.push({
//							name:formatDate(result.data6[i].time),
//							value:result.data6[i][p]
//						});
//					}
//					if(i< result.data1.length
//						&& i< result.data2.length
//						&& i< result.data3.length
//						&& i< result.data4.length
//						&& i< result.data5.length
//						&& i< result.data6.length) {
//						avgData.push(((result.data1[i][p] + result.data2[i][p] + result.data3[i][p] + result.data4[i][p] + result.data5[i][p] + result.data6[i][p])/6).toFixed(4));
//					}
//					
//					xAxisData.push('');	
//				}
//				
//				var seriesData = [
//					{name:'时间段1',type:'line',data:data1},
//					{name:'时间段2',type:'line',data:data2},
//					{name:'时间段3',type:'line',data:data3},
//					{name:'时间段4',type:'line',data:data4},
//					{name:'时间段5',type:'line',data:data5},
//					{name:'时间段6',type:'line',data:data6},
//					{name:'平均值',type:'line',data:avgData}
//				];
				createChart(legendData, xAxisData, seriesData);
			}
		});
	}
	return false;
}

function getParam(flag){
	$.ajax({
		url:'/singleParamStatisticParam',
		type:'POST',
		data: "stationId=" + $('input[name="stationId"]').val(),
		dataType: 'JSON',
		success: function(result){
		console.log(result);
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
			paramsSelect.chosen("destroy");
			paramsSelect.chosen({});
			if(flag){
				search();
			}
		}
	});
}

$('input[name="stationId"]').change(getParam);
_checkFunc = getParam;

/**
* 创建图表
* legendData 图例组件数组 ['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
* xAxisData 横坐标 ['周一','周二','周三','周四','周五','周六','周日']
* seriesData 系列 [{name:'邮件营销',type:'line',stack: '总量',areaStyle: {normal: {}},data:[120, 132, 101, 134, 90, 230, 210]}
*/
function createChart(legendData, xAxisData, seriesData){
	echarts.dispose(document.getElementById('main'));
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	
	// 指定图表的配置项和数据
	var option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    dataZoom: [
	           {
		            type: 'slider',
		            show: true,
		            xAxisIndex: [0],
		            start: 0,
		            end: 100
		        },
		        {
		            type: 'slider',
		            show: true,
		            yAxisIndex: [0],
		            left: '0%',
		            start: 0,
		            end: 100
		        },
		        {
		            type: 'inside',
		            xAxisIndex: 0,
		            filterMode: 'empty'
		        },
		        {
		            type: 'inside',
		            yAxisIndex: 0,
		            filterMode: 'empty'
		        }
        ],
	    legend: {
	        data: legendData
	    },
	    tooltip: {
	    	trigger: 'axis',
	    	formatter: function (params) {
	    		var tips = [];
	    		for(var index in params){
	    			var tip = params[index].marker + '';
	    			if(params[index].name){
	    				tip += params[index].name + '：';
	    			} else{
	    				tip += params[index].seriesName + '：';
	    			}
	    			tip += params[index].value;
	    			tips.push(tip);
	    		}
	    		return tips.join('<br/>');
	    	}
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '15%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: true,
	        data: xAxisData
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: seriesData
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}


function hidden1(){
//	$("#lable3").css("display","none");
//	$("#lable31").css("display","none");
//	$("#lable32").css("display","none");
//	$("input[name='endTime3']").css("display","none");
//	$("input[name='startTime3']").css("display","none");
//	$("#lable4").css("display","none");
//	$("#lable41").css("display","none");
//	$("#lable42").css("display","none");
//	$("input[name='endTime4']").css("display","none");
//	$("input[name='startTime4']").css("display","none");
//	$("#lable5").css("display","none");
//	$("#lable51").css("display","none");
//	$("#lable52").css("display","none");
//	$("input[name='endTime5']").css("display","none");
//	$("input[name='startTime5']").css("display","none");
//	$("#lable6").css("display","none");
//	$("#lable61").css("display","none");
//	$("#lable62").css("display","none");
//	$("input[name='endTime6']").css("display","none");
//	$("input[name='startTime6']").css("display","none");
	$('.add').last().remove();
}

function show1(){
	var addCount=$('.add').length;//需要从2开始计算因为第一个是默认值
	var template='<div class="row add">'+
	'<div class="col-lg-1">'+
		'<div class="form-group">'+
			'<label class="control-label col-lg-12 text-nowrap">时间段'+(addCount+2)+'：</label>'+
		'</div>'+
	'</div>'+
	'<div class="col-lg-4 text-nowrap">'+
		'<div class="form-group">'+
			'<label class="col-lg-4 control-label" labelFor="startTime'+(addCount+2)+'">开始时间：</label>'+
			'<div class="col-lg-8">'+
				'<input type="text" name="startTime1" id="startTime'+(addCount+2)+'" placeholder="开始时间" class="form-control datetimepicker" required="true"/>'+
			'</div>'+
		'</div>'+
	'</div>'+
	'<div class="col-lg-4 text-nowrap">'+
		'<div class="form-group">'+
			'<label class="col-lg-4 control-label" labelFor="endTime'+(addCount+2)+'">结束时间：</label>'+
			'<div class="col-lg-8">'+
				'<input type="text" name="startTime1" id="endTime'+(addCount+2)+'" placeholder="结束时间" class="form-control datetimepicker" required="true"/>'+
			'</div>'+
		'</div>'+
	'</div>'+
'</div>';
	var form=$('#searchForm');
	var obj=$(template);
	form.append(obj);
	var date = new Date();
	$("input[id='endTime"+(addCount+2)+"']").val(formatDate(date));
	var time = date.getTime();
	time = time - 1 * 24 * 60 * 60 * 1000;
	date.setTime(time);
	$("input[id='startTime"+(addCount+2)+"']").val(formatDate(date));
	initDatePicker($('#startTime'+(addCount+2)));
	initDatePicker($('#endTime'+(addCount+2)));
	//****关键代码****绑定日期控件的onfocus事件，同时将上面得到的有效日期数据绑定到控件中
//	$("#lable3").css("display","");
//	$("#lable31").css("display","");
//	$("#lable32").css("display","");
//	$("input[name='endTime3']").css("display","");
//	$("input[name='startTime3']").css("display","");
//	$("#lable4").css("display","");
//	$("#lable41").css("display","");
//	$("#lable42").css("display","");
//	$("input[name='endTime4']").css("display","");
//	$("input[name='startTime4']").css("display","");
//	$("#lable5").css("display","");
//	$("#lable51").css("display","");
//	$("#lable52").css("display","");
//	$("input[name='endTime5']").css("display","");
//	$("input[name='startTime5']").css("display","");
//	$("#lable6").css("display","");
//	$("#lable61").css("display","");
//	$("#lable62").css("display","");
//	$("input[name='endTime6']").css("display","");
//	$("input[name='startTime6']").css("display","");
}

function initDatePicker(ele){
	ele.datetimepicker({
		format: "yyyy-mm-dd hh:ii:ss",
        language:'zh-CN',
        autoclose:true,
        //linkField: "mirror_field",
        //linkFormat: "hh:ii"
        minView:'hour',
        todayBtn:false,
        showMeridian:false,
        minuteStep:5,
        todayBtn:true,
        todayHighlight:true
	});
}

$(function(){
	var date = new Date();
	$("input[id='endTime1']").val(formatDate(date));
	var time = date.getTime();
	time = time - 1 * 24 * 60 * 60 * 1000;
	date.setTime(time);
	$("input[id='startTime1']").val(formatDate(date));
//	$("input[name='endTime2']").val(formatDate(date));
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime2']").val(formatDate(date));
//	$("input[name='endTime3']").val(formatDate(date));
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime3']").val(formatDate(date));
//	$("input[name='endTime4']").val(formatDate(date));
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime4']").val(formatDate(date));
//	$("input[name='endTime5']").val(formatDate(date));
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime5']").val(formatDate(date));
//	$("input[name='endTime6']").val(formatDate(date));
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime6']").val(formatDate(date));
	

	getParam(true);
	$("#show1").click(function(){
		show1();
	});
	$("#hidden1").click(function(){
		hidden1();
	});
});