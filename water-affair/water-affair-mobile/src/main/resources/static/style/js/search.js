var search = {
	show: function(){
		$("#main-container").addClass('hide');
		$("#search-container").removeClass('hide');
		var searchBar = $("div.bar");
        searchBar.addClass("bar-header-secondary");
        searchBar.prev().removeClass("hide").addClass("bar bar-nav bar-standard");
	},
	hide: function(){
		$("#main-container").removeClass('hide');
		$("#search-container").addClass('hide');
		$("#searchInput").val('');
        var searchBar = $("div.bar");
        searchBar.removeClass("bar-header-secondary");
        searchBar.prev().addClass("hide").removeClass("bar bar-nav bar-standard");
	},
	noResult: function(){
		$("#search-container .list-block").addClass('hide');
		$("#search-container .search-no-result").removeClass('hide');
	},
	doing: function(){
		$("#search-container .list-block").addClass('hide');
		$("#search-container .search-doing-result").removeClass('hide');
	},
	showResult: function(datas){
		var html = '<ul>';
		if(datas){
			for(var i in datas){
				html += '<li>\
		        		<a href="station.html?stationId=' + datas[i].id + '" class="item-link item-content external">\
				            <div class="item-inner">\
					            <div class="item-subtitle">' + datas[i].point + '</div>\
					        </div>\
				        </a>\
		      		</li>';
			}
		}
		html += '</ul>';
		$("#search-container .list-block").addClass('hide');
		$("#search-container .search-has-result").html(html);
		$("#search-container .search-has-result").removeClass('hide');
	},
	clearResult: function(){
		$("#search-container .list-block").addClass('hide');
	}
}

$("#searchInput").focus(function(){
	if($("#search-container").hasClass('hide')){
		search.clearResult();
	}
	search.show();
});

$(".searchbar-cancel").click(search.hide);

$("#searchInput").blur(function(){
	var searchInput = $(this).val();
	if(searchInput){
		$(this).val(searchInput.trim());
	}
	if(!$(this).val()){
		search.hide();
	}
});

$("#searchInput").keyup(function(){
	var searchInput = $(this).val();
	if(searchInput){
		search.doing();
		searchInput = searchInput.trim();
		$.ajax({
			url:'search.json',
			type:'POST',
			data:'point='+searchInput,
			dataType:'json',
			success: function(resp){
				if(resp.result && resp.result.length > 0){
					search.showResult(resp.result);
				} else{
					search.noResult();
				}
			},
			error: function(e){
				search.noResult();
			}
		});
	} else{
		search.clearResult();
	}
});