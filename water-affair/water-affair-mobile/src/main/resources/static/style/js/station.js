$.init();

chartSetting.count = 0;

$(document).on("click", ".btn-div a", function(){
    if(checkSetting()){
	    $.showIndicator();
		chartSetting.title = $(this).find("span").eq(0).text();
	    $.popup(".popup-chart");
		$(".popup-chart .title").text(chartSetting.title);
		
		chartSetting.param = $(this).attr("name");
		chartSetting.mcuId = $(this).attr("value");
    }
});

$(document).on('opened','.popup-chart', function () {
	//设置默认数据
	setDefult();
	//请求数据并生成图表
	getData();
});

//请求数据
var getData= function(){
	$.ajax({
		url: 'stationParamData.json',
		type: 'POST',
		data: chartSetting,
		dataType: 'json',
		success: function(res){
			var legendData = [chartSetting.title];
			var seriesData = {
					name:chartSetting.title,
		            type:'line',
		            smooth: true,
		            data: new Array()
				};
			if(res && res.datas){
				for(var index in res.datas){
					var data = res.datas[index];
					seriesData.data.push([data.time, data[chartSetting.param]]);
				}
			}
			createChart(legendData, [seriesData]);
			$.hideIndicator();
		},
		error: function(e){
			$.hideIndicator();
			$.toast('加载失败');
		}
	});
}

/**
* 创建图表
* legendData 图例组件数组
* xAxisData 横坐标
*/
function createChart(legendData, seriesDatas){
	echarts.dispose(document.getElementById('main-chart'));
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main-chart'));
	
	// 指定图表的配置项和数据
	var option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    legend: {
	        data: legendData
	    },
	    xAxis: {
	    	type: 'time',
	        axisLabel: {
	        	formatter: function(value, index){
	        		return formatDate(new Date(value), 'HH:mm:ss');
	        	}
	        }
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: seriesDatas
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

var setDefult = function(){
	chartSetting.count = 0;
	var interval = +chartSetting.interval;
	chartSetting.startTime = +chartSetting.time;
	if(interval > 30){
		chartSetting.type = 2;
		chartSetting.space = 24 * 60 * 60 * 1000;
	} else{
		chartSetting.type = 1;
		chartSetting.space = 60 * 60 * 1000;
	}
    setChartInput();
	echarts.dispose(document.getElementById('main-chart'));
}

$(document).on('click', '#chartDown', function(){
	$.showIndicator();
	chartSetting.count++;
	setChartInput();
	getData();
});
$(document).on('click', '#chartUp', function(){
	if(chartSetting.count > 0){
		$.showIndicator();
		chartSetting.count--;
		setChartInput();
		getData();
	}
});

var setChartInput = function(){
	if(chartSetting.count > 0){
		$("#chartUp").removeClass("disabled");
	} else{
		$("#chartUp").addClass("disabled");
	}

	var time = +chartSetting.time;
	var space = +chartSetting.space;
	var count = +chartSetting.count;
	var ts = time - space * count;
	var timeStr = "";
	if(chartSetting.type == 2){
		timeStr = formatDate(ts, 'yyyy-MM-dd');	
	} else{
		timeStr = formatDate(ts, 'yy-MM-dd HH');
	}
	chartSetting.startTime = ts;
	$("#chartDate").val(timeStr);
}

var checkSetting = function(){
	if(!chartSetting){
		$.toast('参数初始化错误');
		return false;
	}
	if(!chartSetting.interval){
		$.toast('测点传输数据周期未设置');
		return false;
	}
	var interval = +chartSetting.interval;
	if(isNaN(interval) || interval <= 0){
		$.toast('测点传输数据周期设置有误');
		return false;
	}
	var time = +chartSetting.time;
	if(isNaN(time)){
		$.toast('该测点无数据');
		return false;
	}
	return true;
}