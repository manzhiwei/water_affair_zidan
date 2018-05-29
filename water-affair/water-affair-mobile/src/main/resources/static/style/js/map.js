var map = new AMap.Map('map-container',{
    resizeEnable: true,
    zoom: 12,
    center: [121.452731, 31.222311]
});

$(function(){
	getRealtimeData();//最上面的信息条
	getLevelDatas();//水质分级按钮
	getCompanyBtn();//区域按钮
});

function getCompanyBtn(){
	$.ajax({
		url: '/mapData/company',
		type: 'POST',
		dataType: 'json',
		success: function(res){
			var data = res.data;
			if(data){
				var html = '';
				for(var index in data){
					html += '<button class="button button-round button-fill button-primary" value="' + data[index].id + '">' + data[index].companyName + '</button>';
				}
				$('.station-btn-list').append(html);
			}
			bindBtn();//注册事件
		}
	})
}

function getRealtimeData(){
	$.ajax({
		url: "/mapData/realtimeNetwork",
		type: "POST",
		dataType: "json",
		success: function(res){
			var total = res.data.stationNum;
			var online = res.data.stationNum - res.data.data[3];
			var offline = res.data.data[3];
			$(".bar-info span").eq(0).text(total);
			
			if(total == 0){
				total = 1;
			}
			
			$(".bar-info span").eq(1).text(online);
			$(".bar-info span").eq(2).text((online/total*100).toFixed(1));
			$(".bar-info span").eq(3).text(offline);
			$(".bar-info span").eq(4).text((100-(+(online/total*100).toFixed(1))).toFixed(1));
			
		}
	});
}

//类型切换
/**
$(".type-btns button").click(function(){
	if($(this).hasClass("button-primary")){
		infoWindow.close();
		$(".type-btns .button-success").removeClass("button-success").addClass("button-primary");
		$(this).removeClass("button-primary").addClass("button-success");
		getLevelDatas();
	}
});
*/

$(".level-btns button").click(function(){
	infoWindow.close();
	if($(this).hasClass('active')){
		$(this).removeClass('active');
	} else{
		$(this).addClass('active');
	}
	var activeLevels = new Array();
	$(".level-btns button.active").each(function(){
		activeLevels.push($(this).attr("value"));
	});
	markers.forEach(function(marker) {
		if(activeLevels.length > 0){
			var showFlag = false;
			for(var al in activeLevels){
				if(marker.getExtData().typeCode + '-' + marker.getExtData().level == activeLevels[al]){
					showFlag = true;
				}
			}
			if(showFlag){
				//这里需要高亮
				marker.setIcon(new AMap.Icon({image:'/img/icon/marker-' + marker.getExtData().typeCode + '-' + marker.getExtData().level + 'b.png', imageSize: [28,28]}));
				marker.show();
			} else{
				marker.hide();
			}
		} else{
			//去除高亮
			marker.setIcon(new AMap.Icon({image:'/img/icon/marker-' + marker.getExtData().typeCode + '-' + marker.getExtData().level + 'b.png', imageSize: [18,18]}));
			marker.show();
		}
    });
	map.setFitView();
})

function getLevelDatas(){
	//var typeCode = $(".type-btns .button-success").attr("name");
	var typeCode = 0;
	$.ajax({
		url: "/mapData/levelDatas?typeCode="+typeCode,
		type: "POST",
		dataType: "json",
		success: function(res){
            var levelNums = res.levelNums;

            var showHeichou = true;
            for(var index in levelNums){
                if(levelNums[index].typeCode == "2"
                    && levelNums[index].num > 0){
                    showHeichou = false;
                }
            }
			for(var index in levelNums){
				var btn = $(".level-btns button[name=" + levelNums[index].level + "]");
				btn.find("span").eq(0).text(levelNums[index].num);
				btn.find("span").eq(1).text(levelNums[index].precent);
			}
			if(showHeichou){
				$("#heichouLevelBtns").hide().show();
                $("#dibiaoLevelBtns").hide();
			} else{
                $("#heichouLevelBtns").hide();
                $("#dibiaoLevelBtns").hide().show();
			}
			//$(".level-btns").not(".hide").addClass("hide");
			//$(".level-btns").eq(typeCode - 1).removeClass("hide");
			//$(".level-btns button").removeClass("active");
			
			handleStations(res.stations, typeCode);
		}
	});
}


var infoWindow = new AMap.InfoWindow({
	autoMove: true,
	closeWhenClickMap: true,
    content: ""  //使用默认信息窗体框样式，显示信息内容
});
infoWindow.on('open', function(){
	map.setStatus({scrollWheel: false});//窗体显示滚轮失效
	$("body").css('overflow-y', 'hidden');
});
infoWindow.on('close', function(){
	map.setStatus({scrollWheel: true});//窗体关闭滚轮有效
	$("body").css('overflow-y', 'auto');
});

var markers;
function handleStations(stations, typeCode){
	if(markers){
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
	}
	markers = new Array();
	if(stations){
		for(var index in stations){
			var level = $('.level-btns button[name="' + stations[index].result +'"]').attr('value');
			var marker = new AMap.Marker({
				map: map,
				position: [stations[index].longitude, stations[index].latitude],
				offset : new AMap.Pixel(-12,-12),
				icon: new AMap.Icon({image:'/style/icon/marker-' + stations[index].typeCode + '-' + stations[index].levelCode + 'b.png',imageSize:[18,18]}),
				title: stations[index].point,
				clickable: true,
				extData: {
					station: stations[index],
					typeCode: stations[index].typeCode,
					level:  stations[index].levelCode
				}
			});
			marker.on('click', function(event){
				infoWindow.close();
				infoWindow.setContent('');
				var data = this.getExtData();
				var marker = this;
				$.ajax({
					url: '/mapData/stationData',
					type: 'POST',
					data: 'stationId=' + data.station.stationId,
					dataType: 'json',
					success: function(result){
					
						$(".infoTable").empty();
						var html = '';/*'<li class="item-content">'
			        			 + '<div class="item-media"><i class="icon icon-f7"></i></div><div class="item-inner">'
			        			 + '<div class="item-title">电池状态</div><div class="item-after"><span class="infoBattery"></span> / <span class="infoFullBattery"></span></div></div>'
			        			 + '</li><li class="item-content">'
			        			 + '<div class="item-media"><i class="icon icon-f7"></i></div><div class="item-inner">'
			        			 + '<div class="item-title">网络状态</div><div class="item-after"><span class="infoNetwork"></span> db</div></div></li>';*/
						var i = 0;
						for(var index in result.params){
							if(result.params[index].display == '1'){
								
								html += '<li class="item-content"><div class="item-media"><i class="icon icon-f7"></i></div><div class="item-inner">'
									+ '<div class="item-title infoName' + index + '"></div><div class="item-after">'
									+ '<span class="infoData' + index + '"></span> <span class="infoUnit' + index + '"></span></div></div></li>';
								i ++;
							}
							
						}
						$(".infoTable").append(html);
						
						$(".map-badge").text(data.station.result);
						$(".map-badge").attr('class',
							'badge map-badge ' + $('.level-btns button[name="' + data.station.result +'"]').attr('class'));
						
						$(".infoHref").attr('href','/station.html?stationId='+ result.data.pointId);
						//$(".infoHref").trigger('click');//要点两次才能跳转的bug
						$(".infoStation").text((result.data.companyName? result.data.companyName + '-' : '') + result.data.point);
						$(".infoTime").text(result.data.time? formatDate(result.data.time): '无数据');
						//$(".infoBattery").text(result.data.battery != null? result.data.battery: '-');
						//$(".infoFullBattery").text(result.data.fullBattery != null? result.data.fullBattery: '-');
						//$(".infoNetwork").text(result.data.network != null? result.data.network: '-');
						
						for(var index in result.params){
							if(result.params[index].display == '1'){
								$(".infoName" + index).text(result.params[index].paramName);
								$(".infoUnit" + index).text(result.params[index].unit);
								$(".infoData" + index).text(result.data[index] != null ? result.data[index].toFixed(2): '-');
							}
						}
						
						infoWindow.setContent($("#info").html());
						infoWindow.open(map, marker.getPosition());
					}
				});
			});
			markers.push(marker);
		}
		map.setFitView();
	}
}

function bindBtn(){
	$(".station-btn-list button").click(function(e){
		if($(this).hasClass("button-warning")){
			// 有->无
    		$(this).removeClass("button-warning");
			$(this).addClass("button-primary");
		} else{
			// 无->有
			$(this).removeClass("button-primary");
    		$(this).addClass("button-warning");
    		if($(this).attr('id') != 'allTownBtn'){
					$("#allTownBtn").removeClass("button-warning");
					$("#allTownBtn").addClass("button-primary");
				}
		}
    	handleMarkers();
    });
}

function handleMarkers(){
	if($("#allTownBtn.button-warning")[0]){
		//显示全部
		if(markers){
			markers.forEach(function(marker) {
				marker.setMap(map);
			});
		}
	} else if(!$(".station-btn-list button.button-warning")[0]){
		//全部不显示
		if(markers){
			markers.forEach(function(marker) {
				marker.setMap(null);
			});
		}
	} else{
		// 部分显示
		var companyIds = [];
		$(".station-btn-list button.button-warning").each(function(){
			companyIds.push($(this).attr('value'));
		});
		if(markers){
			markers.forEach(function(marker) {
				marker.setMap(null);
				for(var index in companyIds){
					if(companyIds[index] == marker.getExtData().station.companyId){
						marker.setMap(map);
						break;
					}
				}
				
			});
		}
	}
	map.setFitView();
}

/**
 * 将日期格式化成指定格式的字符串
 * @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
 * @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
 * @returns 返回格式化后的日期字符串
 */
function formatDate(date, fmt)
{
    date = date == undefined ? new Date() : date;
    date = typeof date == 'number' ? new Date(date) : date;
    fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
    var obj =
    {
        'y': date.getFullYear(), // 年份，注意必须用getFullYear
        'M': date.getMonth() + 1, // 月份，注意是从0-11
        'd': date.getDate(), // 日期
        'q': Math.floor((date.getMonth() + 3) / 3), // 季度
        'w': date.getDay(), // 星期，注意是0-6
        'H': date.getHours(), // 24小时制
        'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
        'm': date.getMinutes(), // 分钟
        's': date.getSeconds(), // 秒
        'S': date.getMilliseconds() // 毫秒
    };
    var week = ['天', '一', '二', '三', '四', '五', '六'];
    for(var i in obj)
    {
        fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m)
        {
            var val = obj[i] + '';
            if(i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
            for(var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
            return m.length == 1 ? val : val.substring(val.length - m.length);
        });
    }
    return fmt;
}