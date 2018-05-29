function checkDate() {
    var starttime = $("input[name='beginTime']").val().replace(/-/g, "/");
    var endTime = $("input[name='endTime']").val().replace(/-/g, "/");
    if(starttime!='' && endTime != '' && starttime>=endTime){
        swal("查询失败!", "结束日期需大于开始日期", "error");
        return;
    }
    $("#searchForm").submit();
}
$(function (){
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
});