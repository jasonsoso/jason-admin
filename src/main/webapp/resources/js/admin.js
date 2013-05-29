
jason = {};

jason.select = function() {

	var selectItems = [];

	$(':checkbox[name="items"][checked]').each(function() {
		selectItems.push($(this).val());
	});

	return selectItems;
};

jason.selectAll = function() {
	$(':checkbox[name="items"]').attr('checked', true);
};

jason.unselectAll = function() {
	$(':checkbox[name="items"]').attr('checked', false);
};

jason.search = function() {
	$('#myForm').attr("method", "get").submit();
};

jason.jumpPage = function(pageNo){
	$("#pageNo").val(pageNo);
	$("#myForm").attr("method", "get");
	$("#myForm").submit();
};



$(function() {
	$("#select_all").click(function() {
		jason.selectAll();
	});
	
	$("#unselect_all").click(function() {
		jason.unselectAll();
	});
	
	$("#search").click(function() {
		jason.search();
	});
});