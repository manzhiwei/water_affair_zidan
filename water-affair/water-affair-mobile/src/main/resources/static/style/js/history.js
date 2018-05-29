$(function () {
	'use strict';
  	$.init();
  	search();
});

var sysDate = new Date();

$(".datetime-picker").eq(0).datetimePicker({
	value: [sysDate.getFullYear(), (sysDate.getMonth() + 101 + "").substring(1), (sysDate.getDate() + 100 + "").substring(1), '0', '00'],
	onClose: search
});
$(".datetime-picker").eq(1).datetimePicker({
	value: [sysDate.getFullYear(), (sysDate.getMonth() + 101 + "").substring(1), (sysDate.getDate() + 100 + "").substring(1), sysDate.getHours() == 24 ? 23 : sysDate.getHours() + 1 , '00'],
	onClose: search
});
$(".picker").picker({
	toolbarTemplate: '<header class="bar bar-nav">\
		<button class="button button-link pull-right close-picker">确定</button>\
		<h1 class="title">类型</h1>\
		</header>',
	cols: [
		{
			textAlign: 'center',
			values: ['日均值','小时数据','月均值','实时数据']
		}],
	onClose: search
});

function search(){
	$("#table-datas").empty();
	$("#currentPage").val("1");
	addData();
}

var loading = false;

function addData(){
	loading = true;
	$.showIndicator();
	var dataType = null;
	switch($("#dataType").val()){
		case '小时数据': dataType = 2;break;
		case '日均值': dataType = 1;break;
		case '月均值': dataType = 4;break;
		case '实时数据': dataType = 5;break;
	}
	$.ajax({
		url: '/historyDatas',
		type: 'POST',
		data: {
			currentPage: $("#currentPage").val(),
			stationId: $("#stationId").val(),
			startTime: $("#startTime").val(),
			endTime: $("#endTime").val(),
			dataType: dataType
		},
		dataType: 'json',
		success: function(result){
			var datas = result.datas;
			if(datas){
				
				for(var index in datas){
					var temp = $("#table-template").html();
					var $temp = $(temp);
					$temp.find("td").each(function(){
						if($(this).attr("name") == "time"){
							var time = null;
							if(dataType == 2){
								$(this).text(formatDate(datas[index].time, "MM-dd HH") + " 时");
							} else if(dataType == 4){
								$(this).text(formatDate(datas[index].time, "MM") + " 月");
							} else if(dataType == 5){
								$(this).text(formatDate(datas[index].time, "MM-dd HH:mm:ss"));
							} else{
								$(this).text(formatDate(datas[index].time, "MM-dd"));
							}
						} else{
							$(this).text(datas[index][$(this).attr("name")] == null? '--': datas[index][$(this).attr("name")].toFixed(2));
						}
					});
					$("#table-datas").append($temp.get(0));
				}
				
				var nextPage = +$("#currentPage").val() + 1;
				$("#currentPage").val(nextPage);
				
				if(nextPage == 2){
					// 注册'infinite'事件处理函数
					$('.infinite-scroll').on('infinite',function() {
					    // 如果正在加载，则退出
					    if (loading) return;
					
					    // 设置flag
					    addData();
					
					});
					$('.infinite-scroll-preloader').show();
					$.attachInfiniteScroll($('.infinite-scroll'));
					
					if(datas.length == 0){
		            	//没有数据
		            	$("#table-datas").append('<tr><td>没有数据</td></tr>');
		            }
				}
				
				if(datas.length < 10){
					// 加载完毕，则注销无限加载事件，以防不必要的加载
	            	$.detachInfiniteScroll($('.infinite-scroll'));
		            // 删除加载提示符
		            $('.infinite-scroll-preloader').hide();
				}
				
			} else{
				// 加载完毕，则注销无限加载事件，以防不必要的加载
	            $.detachInfiniteScroll($('.infinite-scroll'));
	            // 删除加载提示符
	            $('.infinite-scroll-preloader').hide();
			}
		},
		complete: function(){
			loading = false;
			setTimeout(function () {
		      	$.hideIndicator();
		  	}, 200);
		}
	});
}
