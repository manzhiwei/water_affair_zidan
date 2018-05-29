var dateInput = [
	new Date(),//当前
	new Date(),//日
	new Date(),//周
	new Date(),//月
	new Date()//年
];

var dataType = null;

$(function () {
	'use strict';
  	$.init();
  	
	if($(".buttons-list a.button-fill").length == 0
		&& $(".buttons-list a").length > 1){
		$(".buttons-list a").eq(0).removeClass("button-dark").addClass("button-success").addClass("button-fill");
		$(".buttons-list a").eq(1).removeClass("button-dark").addClass("button-success").addClass("button-fill");
	}

    dataType = $(".buttons-tab a.active").attr("name");
	showDate();
});

$(".buttons-tab a").click(function(){
	if(!$(this).hasClass("active")){
		$(".buttons-tab a.active").removeClass("active");
		$(this).addClass("active");
        dataType = $(".buttons-tab a.active").attr("name");
        showDate();
	}
});

$(".buttons-list a").click(function(){
	if($(this).hasClass("button-dark")){
		$(this).removeClass("button-dark").addClass("button-success").addClass("button-fill");
	} else{
		$(this).removeClass("button-success").removeClass("button-fill").addClass("button-dark");
	}
	createChart();
});
// 切换时变更日期
function showDate(){
    var dateStr = dateFormat(dataType, dateInput[dataType]);
	$("#chartDate").val(dateStr);
	if(dateStr >= dateFormat(dataType, dateInput[0])) {
        $('#chartUp').addClass("disabled");
    } else {
        $('#chartUp').removeClass("disabled");
	}
    search();
}
// 点击减少
$(document).on('click', '#chartDown', function(){
    dateAdd(dataType, -1, dateInput[dataType]);
    showDate();
});
// 点击增加
$(document).on('click', '#chartUp', function(){
	if (dateFormat(dataType, dateInput[dataType]) < dateFormat(dataType, dateInput[0])) {
        dateAdd(dataType, 1, dateInput[dataType]);
        showDate();
	}
});

var rawData = null;
/**
 * 查询
 */
function search(){
	$.showIndicator();
	dataType = $(".buttons-tab a.active").attr("name");
	$.ajax({
		url: "/analysisData",
		type: "POST",
		data: {
			dataType: dataType,
			stationId: $("#stationId").val(),
			dateStr: $("#chartDate").val()
		},
		dataType: "json",
		success: function(result){
			$("#table-datas").empty();
			rawData = null;
			if(result.data && result.data.length > 0){
				rawData = result.data;
				
				for(var index in rawData){
					var dirty = ['无'];
					var levels = [];
					var isOk = '达标';
					var isOkClass = 'color-success';
					var typesClass = 1;
					var levelsClass = 1;
					if(rawData[index].levelResult){
                        typesClass = rawData[index].levelResult.levelType;
                        levelsClass = rawData[index].levelResult.resultCode;
						if(!rawData[index].levelResult.finalResult){
							isOk = '超标';
							dirty = [];
							isOkClass = 'color-danger';
						}
						levels = [/**rawData[index].levelResult.type1Result,*/ rawData[index].levelResult.resultName];
						if(rawData[index].levelResult.details){
							for(var j in rawData[index].levelResult.details){
								if(!rawData[index].levelResult.details[j].finalResult){
									dirty.push(rawData[index].levelResult.details[j].paramName);
								}
							}
						}
					}
					var time = new Date(rawData[index].dataRaw.time);
					var timeStr = '';
					switch(dataType){
						case '1':
							timeStr = formatDate(time, 'MM-dd HH') + " 时";
							break;
						case '2':
							timeStr = formatDate(time, 'MM-dd');
							break;
						case '3':
							timeStr = formatDate(time, 'MM-dd');
							break;
						case '4':
							timeStr = formatDate(time, 'MM') + " 月";
							break;
					}
				
					var html = '<tr>\
							<td>' + timeStr + '</td>\
							<td><a class="button button-fill button-round map-button-' + typesClass + '-' + levelsClass + '">' + levels.join('/') + '</a></td>\
							<td class="' + isOkClass + '">' + isOk + '</td>\
							<td>' + dirty.join('、') + '</td>\
							</tr>';
					$("#table-datas").append(html);
				}
				
			} else{
				$("#table-datas").html("<tr><td>没有数据</td></tr>");
			}
			createChart();
		},
		complete: function(){
			setTimeout(function () {
		      	$.hideIndicator();
		  	}, 200);
		}
	});
}

function createChart(){
	echarts.dispose(document.getElementById('main-chart'));
	/**if($(".buttons-list a.button-fill").length == 0){
		$(".buttons-list a").each(function(){
			if($(this).text().trim() == '溶解氧'
				|| $(this).text().trim() == '氨氮'){
				$(this).removeClass("button-dark").addClass("button-success").addClass("button-fill");
			}
		});
	}*/
	
	var legendData = [];
	var selVal = [];
	var xAxisData = [];
	var seriesData = [];
	
	$(".buttons-list a.button-fill").each(function(){
		legendData.push($(this).text().trim());
		selVal.push($(this).attr("name"))
	});
	if(rawData){
		for(var index=rawData.length-1;index>=0;index--){
			var time = new Date(rawData[index].dataRaw.time);
			var timeStr = '';
			switch(dataType){
				case '1':
					timeStr = formatDate(time, 'MM-dd HH' + " 时");
					break;
				case '2':
					timeStr = formatDate(time, 'MM-dd');
					break;
				case '3':
					timeStr = formatDate(time, 'MM-dd');
					break;
				case '4':
					timeStr = formatDate(time, 'MM') + " 月";
					break;
			}
			xAxisData.push(timeStr);
		}
		
		
		for(var index in selVal){
			var data = [];
			for(var dataIndex in rawData){
				if(rawData[dataIndex].dataRaw[selVal[index]] != null){
					data.push(+((rawData[dataIndex].dataRaw[selVal[index]]).toFixed(4)));
				}
			}
			var item = {
	            name: legendData[index],
	            type:'line',
	            data: data
	        };
	        seriesData.push(item);
		}
	}

	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main-chart'));
	
	// 指定图表的配置项和数据
	var option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data: legendData
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
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

/**
 * 日期加减
 * @param dataType 1 日 2 周 3 月 4 年
 * @param number
 * @param date
 * @returns {*}
 */
function dateAdd(dataType, number, date)
{
    switch(+dataType)
    {
        case  4  :  {
            date.setFullYear(date.getFullYear()+number);
            return  date;
            break;
        }
        case  3  :  {
            date.setMonth(date.getMonth()+number);
            return  date;
            break;
        }
        case  2  :  {
            date.setDate(date.getDate()+number*7);
            return  date;
            break;
        }
        case  1  :  {
            date.setDate(date.getDate()+number);
            return  date;
            break;
        }
        default  :  {
            date.setDate(date.getDate()+number);
            return  date;
            break;
        }
    }
}

function dateFormat(dataType, date){
	var str = 'yyyy-MM-dd';
	switch (dataType) {
		case "4": str = "yyyy"; break;
		case "3": str = "yyyy-MM"; break;
	}
	return formatDate(date, str);
}