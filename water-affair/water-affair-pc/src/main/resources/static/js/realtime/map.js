//$(".chosen-select").chosen({});

var map = new AMap.Map('mapContainer',{
    resizeEnable: true,
    zoom: 12,
    center: [121.452731, 31.222311]
});
AMap.plugin(['AMap.ToolBar'],
    function(){
        map.addControl(new AMap.ToolBar({position:'LB'}));
	}
);

// 分页选项
var options = {
	    currentPage: 1,
	    totalPages: 2,
	    onPageChanged: function(e,oldPage,newPage){
	    	getTableData(newPage);
	    },
	    itemTexts: function (type, page, current) {
	        switch (type) {
		        case "first":
		            return "首页";
		        case "prev":
		            return "上一页";
		        case "next":
		            return "下一页";
		        case "last":
		            return "尾页";
		        case "page":
		            return page;
		        }
	    },
	    bootstrapMajorVersion: 3,
	    numberOfPages: 3
	}

//$('#pagination').bootstrapPaginator(options);

function getTableData(newPage){
	$.ajax({
		url: 'mapData/tableDatas',
		type: 'POST',
		data: $('#tableForm').serialize() + "&currentPage=" + newPage ,
		dataType: 'JSON',
		success: function(res){
			handleResult(res);
		}
	});
}

//$("select[name='pointIds']").on('change', getNewTableData);

//getNewTableData();
//请求表格数据
function getNewTableData(){
	$.ajax({
		url: 'mapData/tableDatas',
		type: 'POST',
		data: $('#tableForm').serialize(),
		dataType: 'JSON',
		success: function(res){
			options.currentPage = res.myPage.currentPages;
			options.totalPages = res.myPage.totalPages;
			handleResult(res);
			$('#pagination').bootstrapPaginator(options);
		}
	});
}
//处理表格数据
function handleResult(result){
	$("#tableBody").empty();
	var $param = $("[name='param']");
	var datas = result.datas;
	var html = '';
	if(datas){
		for(var i = 0; i < datas.length; i ++){
			html += '<tr>'
				+ '<td>' + datas[i].companyName + '</td>'
				+ '<td>' + datas[i].point + '</td>'
				+ '<td>' + datas[i].projectCode + '</td>';
			if(datas[i].time){
				html += '<td>' + formatDate(new Date(datas[i].time)) + '</td>';
			} else{
				html += '<td>-</td>';
			}
			if($param){
				for(var j = 0; j < $param.length; j ++){
					html += '<td>';
					var p = $param[j].getAttribute('value');
					if(datas[i][p] != null){
						html += datas[i][p].toFixed(4);
					} else{
						html += '-';
					}
					
					html += '</td>';
				}
			}
			html += '</tr>';
		}
	}
	$("#tableBody").html(html);
	$("#table").hide().show();
}

// 类型切换
$('input[name="typeRadio"]').change(function(){
	$(".map-buttons").empty();
	getLevelDatas();
	infoWindow.close();
});

getLevelDatas();

var stations;
function getLevelDatas(){
	//var typeCode = $('input[name="typeRadio"]:checked').val();
	var typeCode = 0;
	$.ajax({
		url: '/mapData/levelDatas',
		type: 'POST',
		data: 'typeCode=' + typeCode,
		dataType: 'json',
		success: function(result){
			if(result){
				infoWindow.close();
				if(result.levelNums){
					handleLevelNums(result.levelNums);
				}
				if(result.stations){
					handleStations(result.stations);
				}
			}
		}
	});
}

function handleLevelNums(levelNums){
	var res = '';
	var noDisplayHeichou = true;
	var noDispalyGuobiao = true;
	for(var index in levelNums){
		if(levelNums[index].typeCode == "2"
			&& levelNums[index].num > 0){
			noDispalyGuobiao = false;
		}
		if(levelNums[index].typeCode == "1"
			&& levelNums[index].num > 0){
			noDisplayHeichou = false;
		}
	}
	for(var index in levelNums){
		if((levelNums[index].typeCode == "1" && !noDisplayHeichou)
			|| (levelNums[index].typeCode == "2" && !noDispalyGuobiao)){
			res += '<button class="map-button-' + levelNums[index].typeCode + '-' + (Number(levelNums[index].levelCode)) + '" name="' + (Number(levelNums[index].levelCode)) + '" value="'+ levelNums[index].level +'" >'
				+ levelNums[index].level + ' <br/>' + levelNums[index].num + '个 <br/>' + levelNums[index].precent + '%</button>';
		}
	}
	$(".map-buttons").append(res);
	$(".map-buttons button").on('click', function(){
		infoWindow.close();
		if($(this).hasClass('active')){
			$(this).removeClass('active');
		} else{
			$(this).addClass('active');
		}
		var activeLevels = new Array();
		$(".map-buttons button.active").each(function(){
			activeLevels.push($(this).attr("value"));
		});
		markers.forEach(function(marker) {
			if(activeLevels.length > 0){
				var showFlag = false;
				for(var al in activeLevels){
					if(marker.getExtData().result == activeLevels[al]){
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
		map.setFitView(markers);
	});
}

var infoWindow = new AMap.InfoWindow({
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
function handleStations(stations){
	if(markers){
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
	}
	markers = new Array();
	if(stations){
		for(var index in stations){
			var marker = new AMap.Marker({
				map: map,
				position: [stations[index].longitude, stations[index].latitude],
				offset : new AMap.Pixel(-12,-12),
				icon: new AMap.Icon({image:'/img/icon/marker-' + stations[index].typeCode + '-' + stations[index].levelCode + 'b.png',imageSize:[18,18]}),
				title: stations[index].point,
				clickable: true,
				extData: {
					station: stations[index],
					typeCode: stations[index].typeCode,
					level: stations[index].levelCode,
					result: stations[index].result
				}
			});
			marker.on('click', function(event){
				infoWindow.close();
				infoWindow.setContent('');
				var data = this.getExtData();
				var marker = this;
				console.log(data);
				$.ajax({
					url: '/mapData/stationData',
					type: 'POST',
					data: 'stationId=' + data.station.stationId,
					dataType: 'json',
					success: function(result){
					
						$(".infoTable").empty();
						var html = '<tr>';
								 /*+ '<td>电池状态</td>'
								 + '<td><span class="infoBattery"></span> / <span class="infoFullBattery"></span></td>'
								 + '<td>网络状态</td>'
								 + '<td><span class="infoNetwork"></span> db</td>'
								 */
						var i = 0;
						for(var index in result.params){
							if(result.params[index].display == '1'){
								if(i != 0 && i % 2 == 0){
									html += '</tr><tr>';
								}
								html += '<td class="infoName' + index + '">' + '</td>'
									+ '<td><span class="infoData' + index + '"></span> <span class="infoUnit' + index + '"></span>' + '</td>';
								i ++;
							}
							
						}
						html += '</tr>';
						$(".infoTable").append(html);
						
						$(".infoStation").text((result.data.companyName? result.data.companyName + '-' : '') + result.data.point);
						$(".infoTime").text(result.data.time? formatDate(result.data.time): '无数据');
						//$(".infoBattery").text(result.data.battery? result.data.battery: '-');
						//$(".infoFullBattery").text(result.data.fullBattery? result.data.fullBattery: '-');
						//$(".infoNetwork").text(result.data.network? result.data.network: '-');
						
						for(var index in result.params){
							if(result.params[index].display == '1'){
								$(".infoName" + index).text(result.params[index].paramName);
								$(".infoUnit" + index).text(result.params[index].unit);
								$(".infoData" + index).text(result.data[index] != null ? result.data[index].toFixed(2): '-');
							}
						}
						
						infoWindow.setContent($(".info").html());
						infoWindow.open(map, marker.getPosition());
					}
				});
			});
			markers.push(marker);
		}
		map.setFitView();
	}
}


