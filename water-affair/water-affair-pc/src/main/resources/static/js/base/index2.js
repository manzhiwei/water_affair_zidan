$(function(){
	getRealtimeNetwork2();//实时联网状态
	createsSingleParamTable();//单参数趋势表
	getRealtimeMonitoring();//实时监控
	getDatWater('bar');//24小时水质比重
	createOneDayWarningOverproof();//24小时预警记录
});

function getRealtimeNetwork2(){
	createChartOfRealtimeNetwork(["离线","在线"], [{value:1, name:'离线'},{value:7, name:'在线'}]);
}

function getRealtimeNetwork(){
	$.ajax({
		url: '/indexRest/realtimeNetwork',
		type: 'POST',
		dataType: 'json',
		success: function(result){
			var lengendData = new Array();
			var seriesData = new Array();
			if(result && result.data){
				for(var index in result.data){
					lengendData[index] = result.data[index].status;
					seriesData[index] = {
							value: result.data[index].num,
							name: result.data[index].status
					};
				}
			}
			createChartOfRealtimeNetwork(lengendData, seriesData);
		}
		
	});
}


/**
 * 生成实时联网状态图表
 * @param lengendData
 * 	['离线','在线']
 * @param seriesData
 * 	[{value:335, name:'离线'},{value:310, name:'在线'}]
 */
function createChartOfRealtimeNetwork(lengendData, seriesData){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('realtimeNetwork'));

	// 指定图表的配置项和数据
	var option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b} : {c} ({d}%)"
	    },
	    color: ['#61a0a8', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: lengendData
	    },
	    series: [{
	        name: '实时联网情况',
	        type: 'pie',
	        label: {
	            normal: {
	                position: 'inner',
		            formatter: "{b}\n\n{c}个"
	            },
	        },
	        data: seriesData,
	        itemStyle: {
	            emphasis: {
	                shadowBlur: 10,
	                shadowOffsetX: 0,
	                shadowColor: 'rgba(0, 0, 0, 0.5)'
	            }
	        }
	    }]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}


function getRealtimeMonitoring(){
	$.ajax({
		url: '/indexRest/realtimeMonitoring',
		type: 'POST',
		dataType: 'json',
		success: function(result){
			maxNum = result.stationNum;
			nums = result.data;
			createRealtimeMonitoringChart(maxNum, nums);
		}
	});
}

/**
*nums: [正常, 超标, 离线, 异常]
*/
function createRealtimeMonitoringChart(maxNum, nums){
	$("#zc").html(nums[0]);//正常
	$("#cb").html(nums[1]);//超标
	$("#yc").html(nums[2]);//异常
	$("#lx").html(nums[3]);//离线
	createChartOfRealtimeNetwork(["离线","在线"], [{value:nums[3], name:'离线'},{value:maxNum-nums[3], name:'在线'}]);
//	// 基于准备好的dom，初始化echarts实例
//	var myChart = echarts.init(document.getElementById('realtimeMonitoring'));
//
//	// 指定图表的配置项和数据
//	var option = {
//	    tooltip: {},
//	    color: ['#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c23531', '#c4ccd3'],
//	    radar: {
//	        indicator: [
//	           { name: '正常', max: maxNum},
//	           { name: '超标', max: maxNum},
//	           { name: '离线', max: maxNum},
//	           { name: '异常', max: maxNum}
//	        ]
//	    },
//	    series: [{
//	        name: '实时监控',
//	        type: 'radar',
//	        // areaStyle: {normal: {}},
//	        data : [
//	            {
//	                value : nums,
//	                name : ''
//	            }
//	        ]
//	    }]
//	};
//	// 使用刚指定的配置项和数据显示图表。
//	myChart.setOption(option);
}

$(".chartType").click(function(){
	var chartType = $(".active.chartType").attr("name");
	$(".chartType").removeClass("active");
	$(this).addClass("active");
	if(chartType != $(this).attr("name")){
		//切换过图表样式
		chartType = $(this).attr("name");
		getDatWater(chartType);		
	}
});

$("input[name='waterType']").change(function(){
	var chartType = $(".active.chartType").attr("name");
	getDatWater2(chartType);
})

function getDatWater2(chartType){
	var typeCode = $("input[name='waterType']:checked").val();
	var data = {
			"合格": 6,
			"轻度黑臭": 1,
			"重度黑臭": 1
		};
	if(chartType == "bar"){
		//柱状图
		var xAxisData = new Array();
		var seriesData = new Array();
		var index = 0;
		var color = [ '#61a0a8', '#91c7ae','#c23531','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'];
		for(var name in data){
			xAxisData[index] = name;
			seriesData[index] = {
					value: data[name], 
					itemStyle: { 
						normal: { color: color[index]} 
					}
				};
			index ++;
		}
		createChartOfDayWaterBar(xAxisData, "黑臭三分", seriesData);
	} else {
		//默认：饼图
		var color = [ '#91c7ae','#c23531','#61a0a8', '#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3']
		var legendData = new Array();
		var seriesData = new Array();
		var index = 0;
		for(var name in data){
			legendData[index] = name;
			seriesData[index] = {
					name: name,
					value: data[name],
					itemStyle: { 
						normal: { color: color[index]} 
					}
				};
			index ++;
		}
		createChartOfDayWaterPie(legendData, "黑臭三分", seriesData);
	}
}

/**
 * chartType: pie/bar
 */
function getDatWater(chartType){
	var typeCode = $("input[name='waterType']:checked").val();
	$.ajax({
		url: '/indexRest/dayWater',
		type: 'POST',
		data: 'typeCode=' + typeCode,
		dataType: 'json',
		success: function(result){
			typeCode=result.typeCode;
			var color = ['#10B009', '#CB010F','#060606', '#61a0a8', '#91c7ae', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265'];
			var typeName = '';
			if(typeCode == '1'){
				typeName = '黑臭三分'
			} else if(typeCode == '2'){
				typeName = '地表水监测'
				color = ['#5EBEFD', '#10B009','#E1C30C', '#FD7201', '#CB010F', '#060606', '#bda29a','#6e7074', '#546570', '#d48265']
			}
			if(result.data){
				if(chartType == "bar"){
					//柱状图
					var xAxisData = new Array();
					var seriesData = new Array();
					var index = 0;
//					var color = ['10B009', 'CB010F','060606', '#61a0a8', '#91c7ae', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265']
					for(var name in result.data1){
						xAxisData[index] = result.data1[name];
						seriesData[index] = {
								value: result.data[result.data1[name]], 
								itemStyle: { 
									normal: { color: color[index]} 
								}
							};
						index ++;
					}
					createChartOfDayWaterBar(xAxisData, typeName, seriesData,color);
				} else {
					//默认：饼图
					var legendData = new Array();
					var seriesData = new Array();
					var index = 0;
					for(var name in result.data1){
						legendData[index] = result.data1[name];
						seriesData[index] = {
								name: result.data1[name],
								value: result.data[result.data1[name]]
							};
						index ++;
					}
					createChartOfDayWaterPie(legendData, typeName, seriesData,color);
				}
			}
		}
	});
}

/**
 * 生成饼图
 * @param legendData ['重度黑臭','轻度黑臭','合格']
 * @param seriesName '黑臭三分'
 * @param seriesData [{value:235, name:'重度黑臭'},{value:310, name:'轻度黑臭'},{value:400, name:'合格'}]
 */
function createChartOfDayWaterPie(legendData, seriesName, seriesData,color){
	// 基于准备好的dom，初始化echarts实例
	echarts.dispose(document.getElementById('dayWater'));
	var myChart = echarts.init(document.getElementById('dayWater'));
	
	// 指定图表的配置项和数据
	var option = {
	    tooltip:{
	        trigger: 'item',
	        formatter: "{b} : {c} ({d}%)"
	    },
	    color: color,//['green', 'red','black', '#bda29a', '#ca8622', '#749f83','#6e7074', '#546570', '#d48265', '#c4ccd3'],
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

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

/**
 * 生成柱状图
 * @param xAxisData ['重度黑臭','轻度黑臭','合格']
 * @param seriesName '黑臭三分'
 * @param seriesData [ {value: 150, itemStyle: { normal: { color: option.color[0]} } }, {value: 100, itemStyle: { normal: { color: option.color[1]} } }]
 */
function createChartOfDayWaterBar(xAxisData, seriesName, seriesData,color){
	echarts.dispose(document.getElementById('dayWater'));
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('dayWater'));

	// 指定图表的配置项和数据
	var option = {
	    color: color,//['green', 'red','black','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'],
	    tooltip:{
	        trigger: 'item',
	        formatter: "{b} : {c}个"
	    },
	    xAxis: {
		    axisLabel :{  
		        interval:0   
		    },
	        data: xAxisData
	    },
        yAxis: {
            minInterval: 1
        },
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

function createsSingleParamTable2(){
	$.ajax({
		url: '/indexRest/singleParam',
		type: 'POST',
		dataType: 'json',
		data: {
			draw: 1,
			start: 0,
			length: 10,
			type:0,
			param:"p3"
		},
		success: function(res){
			var html = "";
			for(var index in res.data){
				var icon = "fa-level-down";
				if(res.data[index].type == 3){
					icon = "fa-level-up";
				}
				html += '<tr>\
						<td><small>' + res.data[index].point + '</small></td>\
						<td>' + '溶解氧' + '</td>\
						<td>' + res.data[index].value + '</td>\
						<td class="text-navy"> <i class="fa ' + icon + '" style="margin-left: 18px;"></i></td>\
					</tr>';
			}
			$("#singleParamTbody").html(html);
		}
	});
}


$('#paramSelect').chosen({});
$('#typeSelect').chosen({});
/**
* 单参数变化表
*/
var singleParamTable;
function createsSingleParamTable(){
	singleParamTable = $('#singleParam').DataTable({
		"ordering": false,
		"searching": false,
		"info": false,
		"lengthChange": false,
		"pageLength": 4,
		"serverSide": true,
		"ajax": {
				url: '/indexRest/singleParam',
				type: 'POST',
				data: {
					type: $('#typeSelect').val(),
					param: $('#paramSelect').val()
				}
			},
		"language": {
			"emptyTable":     "没有数据",
		    "loadingRecords": "加载中...",
		    "processing":     "正在进行中...",
		    "search":         "查询:",
		    "zeroRecords":    "没有记录",
		    "paginate": {
		        "first":      "首页",
		        "last":       "末页",
		        "next":       "后一页",
		        "previous":   "前一页"
		    },
		    "aria": {
		        "sortAscending":  ": activate to sort column ascending",
		        "sortDescending": ": activate to sort column descending"
		    }
		},
		columns: [{
				data: 'point'
			},{
				data: 'paramName'
			},{
				data: 'value',
				render: function(data, type, full, meta ){
					if(data){
						return data.toFixed(2);
					}
					return data;
				}
			},{
				data: 'type',
				render: function(data, type, full, meta ){
					var result;
					switch(data){
						//case '1'://平稳
						case '2'://上升
							result = '<td class="text-navy"> <i class="fa fa-level-up text-navy" style="margin-left: 18px;"></i></td>';
							break;
						case '3'://下降
							result = '<td class="text-navy"> <i class="fa fa-level-down text-navy" style="margin-left: 18px;"></i></td>';
							break;
						default:
							result = '<td class="text-navy"> <i class="fa fa-minus text-navy" style="margin-left: 18px;"></i></td>';
					}
					return result;
				}
			}
		]
	});
}

	
$('#paramSelect').change(function(){
	singleParamTable.destroy();
	createsSingleParamTable();
});
$('#typeSelect').change(function(){
	singleParamTable.destroy();
	createsSingleParamTable();
});

$('.tabs-container li a[data-toggle="tab"]').on("click",function(e){
	if($(this).parent().attr('class') != 'active'){
		createOneDayWarning($(this).attr('href').substring(5));
	}
});

/**
*24小时预警记录
* type 1：超标 2：异常 3：故障
*/
var oneDayWarningTable;
function createOneDayWarning(type){
	if(oneDayWarningTable){
		oneDayWarningTable.destory();
	}
	switch(type){
		case '1': createOneDayWarningOverproof();break;
		case '2': createOneDayWarningAbnormal();break;
		case '3': createOneDayWarningFault();break;
	}
}

function createOneDayWarningOverproof(){
	$.ajax({
		url: '/indexRest/overproof',
		type: 'POST',
		dataType: 'json',
		success: function(result){
			var lengendData = new Array();
			var seriesData = new Array();
			var content=$("#yujing");
			content.html("");
			if(result && result.data){
				for(var index in result.data){
					var date=formatDate(result.data[index].time,"yyyy-MM-dd");
					var time=formatDate(result.data[index].time,"HH:mm:ss");
					var canshu='参数';
					var zuijinjiancezhi='最近监测值';
					var canshuV=result.data[index].paramName;
					if(result.data[index].typeCode==1){
						canshu='预警类型';
						zuijinjiancezhi='预警内容';
						canshuV=result.data[index].typeValue;
					}
					var template='<div id="yujing_chil" class="timeline-item">'+
				     '           <div class="row">'+
				     '       <div class="col-xs-3 date">'+
				     '           <i class="fa fa-briefcase"></i>'+
				     '           	'+date+' '+
				     '           <br/>'+
				     '           <small class="text-navy">'+time+'</small>'+
				     '       </div>'+
				     '       <div class="col-xs-7 content no-top-border">'+
				     '           <p class="m-b-xs"><strong>'+result.data[index].point+'</strong></p>'+
				     '           <p>'+canshu+'：'+canshuV+'</p>'+
				     '           <p>'+zuijinjiancezhi+'：'+result.data[index].value+'</p>'+
				     '       </div>'+
				     '   </div>'+
				     '</div>';
					content.append(template);
				}
			}
		}
		
	});
//	oneDayWarningTable = $('#overproof').DataTable({
//		"ordering": false,
//		"searching": false,
//		"info": false,
//		"lengthChange": false,
//		"pageLength": 3,
//		"serverSide": true,
//		"ajax": {
//				url: '/indexRest/overproof',
//				type: 'POST'
//			},
//		"language": {
//			"emptyTable":     "没有数据",
//		    "loadingRecords": "加载中...",
//		    "processing":     "正在进行中...",
//		    "search":         "查询:",
//		    "zeroRecords":    "没有记录",
//		    "paginate": {
//		        "first":      "首页",
//		        "last":       "末页",
//		        "next":       "后一页",
//		        "previous":   "前一页"
//		    },
//		    "aria": {
//		        "sortAscending":  ": activate to sort column ascending",
//		        "sortDescending": ": activate to sort column descending"
//		    }
//		},
//		columns: [{
//				data: 'point'
//			},{
//				data: 'paramName'
//			},{
//				data: 'type',
//				render: function(data, type, full, meta ){
//					return formatDate(data);
//				}
//			},{
//				data: 'value',
//				render: function(data, type, full, meta ){
//					if(data){
//						return data.toFixed(2);
//					}
//					return data;
//				}
//				
//			}
//		]
//	});
}

function createOneDayWarningAbnormal(){
	singleParamTable = $('#singleParam').DataTable({
		"ordering": false,
		"searching": false,
		"info": false,
		"lengthChange": false,
		"pageLength": 3,
		"serverSide": true,
		"ajax": {
				url: '/indexRest/singleParam',
				type: 'POST',
				data: {
					type: $('#typeSelect').val(),
					param: $('#paramSelect').val()
				}
			},
		"language": {
			"emptyTable":     "没有数据",
		    "loadingRecords": "加载中...",
		    "processing":     "正在进行中...",
		    "search":         "查询:",
		    "zeroRecords":    "没有记录",
		    "paginate": {
		        "first":      "首页",
		        "last":       "末页",
		        "next":       "后一页",
		        "previous":   "前一页"
		    },
		    "aria": {
		        "sortAscending":  ": activate to sort column ascending",
		        "sortDescending": ": activate to sort column descending"
		    }
		},
		columns: [{
				data: 'point'
			},{
				data: 'paramName'
			},{
				data: 'type',
				render: function(data, type, full, meta ){
					var result;
					switch(data){
						//case '1'://平稳
						case '2'://上升
							result = '<span class="text-navy m-l-md"><i class="fa fa-long-arrow-up"></i></span>';
							break;
						case '3'://下降
							result = '<span class="text-danger m-l-md"><i class="fa fa-long-arrow-down"></i></span>';
							break;
						default:
							result = '<span class="m-l-md">--</span>';
					}
					return result;
				}
			},{
				data: 'value',
				render: function(data, type, full, meta ){
					if(data){
						return data.toFixed(2);
					}
					return data;
				}
				
			}
		]
	});
}


function createOneDayWarningFault(){
	singleParamTable = $('#singleParam').DataTable({
		"ordering": false,
		"searching": false,
		"info": false,
		"lengthChange": false,
		"pageLength": 3,
		"serverSide": true,
		"ajax": {
				url: '/indexRest/singleParam',
				type: 'POST',
				data: {
					type: $('#typeSelect').val(),
					param: $('#paramSelect').val()
				}
			},
		"language": {
			"emptyTable":     "没有数据",
		    "loadingRecords": "加载中...",
		    "processing":     "正在进行中...",
		    "search":         "查询:",
		    "zeroRecords":    "没有记录",
		    "paginate": {
		        "first":      "首页",
		        "last":       "末页",
		        "next":       "后一页",
		        "previous":   "前一页"
		    },
		    "aria": {
		        "sortAscending":  ": activate to sort column ascending",
		        "sortDescending": ": activate to sort column descending"
		    }
		},
		columns: [{
				data: 'point'
			},{
				data: 'paramName'
			},{
				data: 'type',
				render: function(data, type, full, meta ){
					var result;
					switch(data){
						//case '1'://平稳
						case '2'://上升
							result = '<span class="text-navy m-l-md"><i class="fa fa-long-arrow-up"></i></span>';
							break;
						case '3'://下降
							result = '<span class="text-danger m-l-md"><i class="fa fa-long-arrow-down"></i></span>';
							break;
						default:
							result = '<span class="m-l-md">--</span>';
					}
					return result;
				}
			},{
				data: 'value',
				render: function(data, type, full, meta ){
					if(data){
						return data.toFixed(2);
					}
					return data;
				}
				
			}
		]
	});
}