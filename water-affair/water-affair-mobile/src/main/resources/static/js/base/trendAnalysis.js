$(".chosen-select").chosen();

var checkboxHtml = $('.checkboxes').html();

function changeParam(flag){
//	var stationId = $('select[name="stationId"]').val();
	var stationId = $('#stationId').attr("data");
	if(stationId){
		$.ajax({
			url: '/mapData/stationData',
			type: 'POST',
			data: "stationId="+stationId,
			dataType: 'json',
			success: function(result) {
				$('.checkboxes').empty();
				if(result.params){
					var params = result.params;
					for(var index in params) {
						if(params[index].display == '1'){
							var obj = $(checkboxHtml);
							obj.find("input").val(params[index].param);
							obj.find("input").attr('id', params[index].param);
							obj.find("label").attr('for', params[index].param);
							obj.find("label").text(params[index].paramName);
							obj.appendTo('.checkboxes');
						}
					}
					if(flag){
						$("input[name='params']").each(function(){
							var text = $(this).next().text();
							if( text == '氨氮' || text == '溶解氧'){
								$(this).trigger("click");
							}
						});
						search();
					}
				}
			}
		});
	}
}


$("select[name='stationId']").change(changeParam);

function search(){
	var legendData = new Array();
	$('input[name="params"]:checked').next().each(function(){
		legendData.push($(this).text());
	});
	var params = new Array();
	$('input[name="params"]:checked').each(function(){
		params.push($(this).val());
	});
	if($("#searchForm").valid() && !!$('input[name="params"]:checked').length ){
		$.ajax({
			url: "/multipleParamStatisticData",
			type: "POST",
			data: $("#searchForm").serialize(),
			dataType: "json",
			success: function(result){
				console.log(result);
				if(result.data){
					var seriesData = new Array();
					var xAxisData = new Array();
					for(var i = 0; i<legendData.length; i++){
						seriesData[i] = {
				            name:legendData[i],
				            type:'line',
				            smooth: true,
				            data: new Array()
				        }
					}
					for(var index in result.data){
						xAxisData.push(formatDate(new Date(result.data[index].time)));
						for(var i = 0; i<params.length; i++){
							seriesData[i].data.push(result.data[index][params[i]]);
						}
					}
					createChart(legendData,xAxisData,seriesData);
				}
			}
		});
	}
	return false;
}


/**
* 创建图表
* legendData 图例组件数组
* xAxisData 横坐标
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


$(function(){

//	var date = new Date();
//	$("input[name='endTime']").val(formatDate(date));
//	var time = date.getTime();
//	time = time - 1 * 24 * 60 * 60 * 1000;
//	date.setTime(time);
//	$("input[name='startTime']").val(formatDate(date));
	changeParam(true);
});