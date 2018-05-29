function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:160 + "px", top:275 + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

options.bootstrapMajorVersion=3;
options.itemTexts=function (type, page, current) {
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
                };
options.numberOfPages=3;

$('#pagination').bootstrapPaginator(options);

function showVideo(obj){
	var pointId = obj.getAttribute('value');
	var point = obj.getAttribute('name');
	$('#contentModal').find('.modal-title').text(point + ' - 视频');
	
	$('#contentModal').modal('show');
}

function showImage(obj){
	var gatewaySerial = obj.getAttribute('value');
	var point = obj.getAttribute('name');
	$('#contentModal').find('.modal-title').text(point + ' - 图片');
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

function showDetail(obj){
	var stationId = obj.getAttribute('value');
	var l = $(obj).ladda();
	l.ladda('start');
	$.ajax({
		url: '/mapData/stationData',
		type: 'POST',
		data: 'stationId=' + stationId,
		dataType: 'json',
		success: function(result){
			$("#infoTable").empty();
			var html = '<tr>';
			var i = 0;
			for(var index in result.params){
				//debugger;
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
			$("#infoTable").html(html);
			
			$(".infoStation").text((result.data.companyName != null? result.data.companyName + '-' : '') + result.data.point);
			$(".infoTime").text(result.data.time? formatDate(result.data.time): '无数据');
			$(".infoBattery").text(result.data.battery != null? result.data.battery: '-');
			$(".infoFullBattery").text(result.data.fullBattery != null? result.data.fullBattery: '-');
			$(".infoNetwork").text(result.data.network != null? result.data.network: '-');
			for(var index in result.params){
				$(".infoName" + index).text(result.params[index].paramName);
				$(".infoUnit" + index).text(result.params[index].unit);
				$(".infoData" + index).text(result.data[index]!= null ? result.data[index].toFixed(2): '-');
			}
			l.ladda('stop');
			$('#detailModal').modal('show');
			$('.infoStationId').val(stationId);
		},
		error:function(result) {
			l.ladda('stop');
		}
	});
}

function flushData(obj){
	var stationId = $(obj).val();
	var record = $(".infoStation").text();
	var l = $(obj).ladda();
	l.ladda('start');
	$.ajax({
		url: '/mapData/stationData',
		type: 'POST',
		data: 'stationId=' + stationId + '&record=' + record,
		dataType: 'json',
		success: function(result){
			$("#infoTable").empty();
			var html = '<tr>';
			var i = 0;
			for(var index in result.params){
				//debugger;
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
			$("#infoTable").html(html);
		
			$(".infoStation").text((result.data.companyName? result.data.companyName + '-' : '') + result.data.point);
			$(".infoTime").text(result.data.time? formatDate(result.data.time): '无数据');
			$(".infoBattery").text(result.data.battery != null? result.data.battery: '-');
			$(".infoFullBattery").text(result.data.fullBattery != null? result.data.fullBattery: '-');
			$(".infoNetwork").text(result.data.network != null? result.data.network: '-');
			for(var index in result.params){
				$(".infoName" + index).text(result.params[index].paramName);
				$(".infoUnit" + index).text(result.params[index].unit);
				$(".infoData" + index).text(result.data[index]!= null ? result.data[index].toFixed(2): '-');
			}
			l.ladda('stop');
		},
		error:function(result) {
			l.ladda('stop');
		}
	});
}



$('#searchBtn').click(function(event){
	var tmp = $('input[name="pointId"]').val();
	var l = $(this).ladda();
	l.ladda( 'start' );
	if(tmp && tmp.lastIndexOf(",") > 0){//大于1个站点的需要检查是否能多选
		$.ajax({
			url: '/checkStaions',
			type: 'POST',
			data: "pointIds=" + tmp,
			dataType: 'json',
			success: function(res){
				if(!res.unpass || res.unpass.length <= 0){
					$('input[name="pointId"]').val(tmp);
					$('#searchForm').submit();
				} else {
					var stations = new Array();
					for(var i = 0; i < res.unpass.length; i++ ){
						stations[i] = $('#pointIds option[value="' + res.unpass[i] + '"]').text();
					}
					l.ladda('stop');
					showErrorInfo(stations);
				}
			},
			error: function(e){
				l.ladda('stop');
			}
		});
	} else{
		//l.ladda('stop');
		$('#searchForm').submit();
	}
	
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

//生成32路数据的表
/**
$(function(){
	var html = '';
	for(var i = 0; i < 32; i = i + 2){
		html += '<tr>'
			+ '<td class="infoNameP' + (1 + i) + '">' + '</td>'
			+ '<td><span class="infoDataP' + (1 + i) + '"></span> <span class="infoUnitP' + (i + 1) + '"></span>' + '</td>'
			+ '<td class="infoNameP' + (2 + i) + '">' + '</td>'
			+ '<td><span class="infoDataP' + (2 + i) + '"></span> <span class="infoUnitP' + (2 + i) + '"></span>' + '</td>'
			+ '</tr>';
	}
	$("#infoTable").append(html);
});
*/