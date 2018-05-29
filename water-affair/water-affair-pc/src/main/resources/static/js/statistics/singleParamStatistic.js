function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:45 + "px", top:225 + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

var stations = null;
function search(){
	if(stations){
		showErrorInfo(stations);
		return false;
	}
	var params = $("#params").val();
	if($("#searchForm").valid()){
		var l = $("button[type='submit']").ladda();
		l.ladda('start');
		$.ajax({
			url: "/singleParamStatisticData",
			type: "POST",
			data: $("#searchForm").serialize(),
			dataType: "json",
			success: function(result){
				var legendData = [];
				var stationIds = $('[name="stationId"]').val();
				if(stationIds){
					for(var index in stationIds){
						legendData.push($('[name="stationId"] option[value="' + stationIds[index] + '"]').text());
					}
				}
				if(result){
					var seriesData = [];
					for(var key in result){
						var data = {
								name:key,
					            type:'line',
					            smooth: true,
					            data: new Array()
							};
						for(var index in result[key]){
							data.data.push([result[key][index].time,result[key][index][params]]);
						}
						seriesData.push(data);
					}
					createChart(legendData,seriesData);
				}
				l.ladda('stop');
			},
			error: function(){
				l.ladda('stop');
			}
		});
	}
	return false;
}

function checkStation(flag){
	var stationIds = $('[name="stationId"]').val();
	if(stationIds && stationIds.lastIndexOf(",") > 0){
		
		//两个以上站点，判断能否复选
		$.ajax({
			url: '/checkStaions',
			type: 'POST',
			data: "pointIds="+stationIds,
			dataType: 'json',
			success: function(res){
				if(!res.unpass || res.unpass.length <= 0){
					getParam(flag);
				} else {
					stations = new Array();
					for(var i = 0; i < res.unpass.length; i++ ){
						stations[i] = $('#stationId option[value="' + res.unpass[i] + '"]').text();
					}
					showErrorInfo(stations);
				}
			},
			error: function(e){
			}
		});
	} else{
		getParam(flag);
	}
	
}

function getParam(flag){
	stations = null;
	$.ajax({
		url:'/singleParamStatisticParam',
		type:'POST',
		data: $("#searchForm").serialize(),
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
			paramsSelect.chosen("destroy");
			paramsSelect.chosen({});
			
			if(flag){
				search();
			}
		}
	});
}

_checkFunc = function(){
    checkStation(false);
}

/**
* 创建图表
* legendData 图例组件数组
* xAxisData 横坐标
*/
function createChart(legendData, seriesData){
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
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '15%',
	        containLabel: true
	    },
	    xAxis: {
	    	type: 'time',
	        axisLabel: {
	        	formatter: function(value, index){
	        		return formatDate(new Date(value));
	        	}
	        }
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: seriesData
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}




$(function(){	

	var date = new Date();
	$("input[name='endTime']").val(formatDate(date));
	var time = date.getTime();
	time = time - 1 * 24 * 60 * 60 * 1000;
	date.setTime(time);
	$("input[name='startTime']").val(formatDate(date));
	
	checkStation(true);

});

function showErrorInfo(stations){
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
	var sta = '';
	for(var index in stations){
		sta += '<br/>' + stations[index];
	}
    toastr.error('以下站点只能单选：' + sta, '查询失败');
}