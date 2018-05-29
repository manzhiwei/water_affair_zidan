function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:165 + "px", top:225 + "px"}).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

var map = new AMap.Map('mapContainer',{
    resizeEnable: true,
    zoom: 12,
    center: [121.452731, 31.222311]
});
var marker;

function search(){
	if($("#searchForm").valid()){
		$.ajax({
			url: "/stationStatisticData",
			type: "POST",
			data: $("#searchForm").serialize(),
			dataType: "json",
			success: function(result){
				if(result.station) {
					if(marker){
						marker.setMap(null);
					}
					marker = new AMap.Marker({
			            position: [result.station.longitude, result.station.latitude]
			        });
			        marker.setMap(map);
			        map.setFitView();
			        map.setZoom(14);
			        $("#stationName").text(result.station.point);//所属地区
			        $("#picBtn").attr("value", result.station.gatewaySerial);
			        
				} else{
					$("#picBtn").attr("value", "");
				}

				$("#param").text(result.displayParam.join('、'));//监测参数
				
				if(result.station && result.station.transferCycle != null){
					$("#collectionTime").text(result.station.transferCycle);
				} else{
					$("#collectionTime").text(0);
				}

                $("#mubiao").text(result.aimResult);
				$("#zuixin").text(result.nowResult.resultName);
				$("#jinyiyue").text(result.monthResult.resultName);

				if(result.nowResult){
					var nowData = [];
					var paramInfo = [];
					for(var index in result.nowResult.details){
						var detail = result.nowResult.details[index];
						if(detail.display && detail.involved){
							nowData.push(detail.resultCode);
							paramInfo.push(detail.paramName);
						}
					}
					createChart('nowDataChart', paramInfo, '当前数据', nowData, result.station.stationJudgeType);				
				}
				if(result.monthResult){
					var monthData = [];
					var paramInfo = [];
					for(var index in result.monthResult.details){
						var detail = result.monthResult.details[index];
						if(detail.display && detail.involved){
							monthData.push(detail.resultCode);
							paramInfo.push(detail.paramName);
						}
					}
					createChart('monthDataChart', paramInfo, '近一个月数据', monthData, result.station.stationJudgeType);
				}
				
				if(result.company) {
					$("#companyName").text(result.company.companyName);
				}
				
				if(result.breakdownNum){
					$("#breakdownNum").text(result.breakdownNum + ' 次');
				} else{
					$("#breakdownNum").text('无');
				}

				//综合数据分析图表显示
				$("#changqichaobiao").text("长期超标（>80%）:"+(result.changqichaobiao.length > 0?result.changqichaobiao.join("、"):"无"));
				$("#lianxuchaobiao").text("近期连续超标（>5次）："+(result.lianxuchaobiao.length > 0?result.lianxuchaobiao.join("、"):"无"));
				$("#ouerchaobiao").text("偶尔超标（<5次）:"+(result.ouerchaobiao.length > 0?result.ouerchaobiao.join("、"):"无"));
                var monthparamInfo = [];
                var paramInfo = [];
				if(result.monthParamResult){
                    for(var p in result.monthParamResult){
                        monthparamInfo.push({
							value: result.monthParamResult[p]
						});
                        paramInfo.push(p);
                    }
				}
				createChart('monthCountChart', paramInfo, '近一个月次数', monthparamInfo,'');
				//站点运行情况1。参数指标异常(次)：氨氮（8次），溶解氧（20次）
				var errInd = [];
                if(result.monthParamAbnormal){
                    for(var p in result.monthParamAbnormal){
                        errInd.push(p+"("+result.monthParamAbnormal[p]+"次)");
                    }
                    $("#errInd").text("参数指标异常(次)："+errInd.join('、'));
                }
				
			}
		});
	}
	return false;
}


/**
 * 生成柱状图
 * @param xAxisData ['重度黑臭','轻度黑臭','合格']
 * @param seriesName '黑臭三分'
 * @param seriesData [ {value: 150, itemStyle: { normal: { color: option.color[0]} } }, {value: 100, itemStyle: { normal: { color: option.color[1]} } }]
 * @param type '1：黑臭图 2：地表图'
 */
function createChart(chartId, xAxisData, seriesName, seriesData ,type){
	echarts.dispose(document.getElementById(chartId));
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById(chartId));

	// 指定图表的配置项和数据
	var option;
	if(type=='1'){	//生成黑臭图
        option = {
            //color: ['#c23531', '#61a0a8', '#91c7ae','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'],
            color: ['#91c7ae'],
            grid: {
                left: '15%'
            },
            title: {
            	text: seriesName,
            	left: "center"
            },
            tooltip:{
                trigger: 'item',
                formatter: function (value,yAx) {
                    if(value.value=='1'){
                        return value.name+' : 合格';
                    }else if(value.value=='2'){
                        return value.name+' : 轻度黑臭';
                    }else if(value.value=='3'){
                        return value.name+' : 重度黑臭';
                    }else{
                        return value.name+' : 其他';
                    }
                }
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {
                max: 3,
                min: 0,
                minInterval: 1,
                axisLabel : {
                    formatter: function (value) {
                        if(value=='1'){
                            return '合格';
                        }else if(value=='2'){
                            return '轻度黑臭';
                        }else if(value=='3'){
                            return '重度黑臭';
                        }
						return "";
                    }
                },
            },
            series: [{
                name: seriesName,
                type: 'bar',
                barMaxWidth: '30px',
                data: seriesData
            }]
        };
	}else if(type=='2'){	//生成地表图
        option = {
            //color: ['#c23531', '#61a0a8', '#91c7ae','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'],
            color: ['#91c7ae'],
            grid: {
                left: '12%'
            },
            title: {
            	text: seriesName,
            	left: "center"
            },
            tooltip:{
                trigger: 'item',
                formatter: function (value,yAx) {
                    if(value.value=='1'){
                        return value.name + ' ： Ⅰ类';
                    }else if(value.value=='2'){
                        return value.name + ' ： Ⅱ类';
                    }else if(value.value=='3'){
                        return value.name + ' ： Ⅲ类';
                    }else if(value.value=='4'){
                        return value.name + ' ： Ⅳ类';
                    }else if(value.value=='5'){
                        return value.name + ' ： Ⅴ类';
                    }else if(value.value=='6'){
                        return value.name + ' ： 劣Ⅴ类';
                    }else{
                        return value.name + ' ： 其他';
                    }
                }
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {
                max: 6,
                min: 0,
                minInterval: 1,
                axisLabel : {
                    formatter: function (value) {
                        if(value=='1'){
                            return 'Ⅰ类';
                        }else if(value=='2'){
                            return 'Ⅱ类';
                        }else if(value=='3'){
                            return 'Ⅲ类';
                        }else if(value=='4'){
                            return 'Ⅳ类';
                        }else if(value=='5'){
                            return 'Ⅴ类';
                        }else if(value=='6'){
                            return '劣Ⅴ类';
                        }
                        return "";
                    }
                },
            },
            series: [{
                name: seriesName,
                type: 'bar',
                barMaxWidth: '30px',
                data: seriesData
            }]
        };
	}else{
        option = {
            //color: ['#c23531', '#61a0a8', '#91c7ae','#749f83', '#ca8622', '#bda29a','#6e7074', '#546570', '#d48265', '#c4ccd3'],
            color: ['#91c7ae'],
            title: {
            	text: seriesName,
            	left: "center"
            },
            tooltip:{
                trigger: 'item',
                formatter: '{b} : {c}'
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {
                minInterval: 1,
                max: null,
                min: null
            },
            series: [{
                name: seriesName,
                type: 'bar',
                barMaxWidth: '30px',
                data: seriesData
            }]
        };
	}

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function showVideo(){
	$('#contentModal').find('.modal-title').text('站点视频');
	$('#contentTbody').empty();
	$('#contentModal').modal('show');
}

function showImage(){
	$('#contentModal').find('.modal-title').text('站点图片');
	var gatewaySerial = $("#picBtn").attr('value');
	$('#contentTbody').empty();
	if(gatewaySerial){
		$.ajax({
			url: '/contentData',
			type: 'POST',
			data: {
				sn: gatewaySerial,
				type: 1
			},
			dataType: 'json',
			success: function(res){
				var html = '';
				var serverUrl = res.serverUrl; 
				if(res.data){
					for(var index in res.data){
						html += '<tr>\
	                                <td>' + (index + 1) + '</td>\
	                                <td>' + formatDate(res.data[index].createTime) + '</td>\
	                                <td><a type="button" class="btn btn-primary btn-xs" href=" ' + serverUrl + res.data[index].fileUrl + ' ">查看</a></td>\
	                            </tr>'
					}
				}
				$('#contentTbody').html(html);
				$('#contentModal').modal('show');
			}
		});
	} else{
		$('#contentModal').modal('show');
	}
}

$(function(){
	
	search();
	
});