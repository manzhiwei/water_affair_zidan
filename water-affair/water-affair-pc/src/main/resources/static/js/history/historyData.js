function showMenu() {
    var cityObj = $("#pointName");
    var cityOffset = $("#pointName").offset();
    $("#menuContent").css({left:155 + "px", top:275 + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
options.totalPages < 1? options.totalPages = 1: 0;
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


$('#searchBtn').click(function(e){
	var starttime = $("input[name='startTime']").val().replace(/-/g, "/");
	var endTime = $("input[name='endTime']").val().replace(/-/g, "/");
	if(starttime!='' && endTime != '' && starttime>=endTime){
        swal("查询失败!", "结束日期需大于开始日期", "error");
		return;
	}
	var tmp = $('input[name="pointId"]').val();
	var l = $( '.ladda-button' ).ladda();
	l.ladda( 'start' );
	if(tmp && tmp.lastIndexOf(",") > 0){//大于1个站点的需要检查是否能多选
		$.ajax({
			url: '/checkStaions',
			type: 'POST',
			data: "pointIds="+tmp,
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