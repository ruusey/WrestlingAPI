
function getLogs(){
	$.ajax({
	    url: '/rest/Wrestling/GetMatchLogs',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addMatchesRow($('#match_logs_table'),item["matchName"],item["date"]);
	    		
	    	});
	    }
	});
}
function getLog(date){
	$("#match_logged_table").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetMatchLogs?date='+date,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addMatchData($('#match_logged_table'),item["wrestler"],item["period"],item["pointType"],item["pointScore"]);
	    		
	    	});
	    }
	});
	getResult(date);
}
function getResult(date){
	$("#match_result_table").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetResult?date='+date,
	    type: 'GET',
	    success: function(msg) {
	    		setMatchResult($('#match_result_table'),msg["homeScore"],msg["awayScore"],msg["result"]);
	    }
	});
}
function setMatchResult(tbody,homeScore,awayScore,result){
	var trow = $('<tr>');
	trow.append($("<td>").text(homeScore));
	trow.append($("<td>").text(awayScore));
	trow.append($("<td>").text(result));
	trow.appendTo(tbody);
	$("#page_overlay").show();
	$("#log_container").show();
}
function addMatchData(tbody,wrestler,period,point,score){
	var trow = $('<tr>');
	trow.append($("<td>").text(wrestler));
	trow.append($("<td>").text(period));
	trow.append($("<td>").text(point));
	trow.append($("<td>").text(score));
	trow.appendTo(tbody);
	$("#page_overlay").show();
	$("#log_container").show();
}
function addMatchesRow(tbody,matchName,date){
	var trow = $('<tr>');
	trow.append($("<td>").text(matchName));
	trow.append($("<td>").text(date));
	trow.append($("<td>").text("View Match").attr("onclick","getLog('"+date+"')"));
	trow.appendTo(tbody);
}
$( document ).ready(function() {
	getLogs();
	$("#log_container").hide();
	$("#page_overlay").hide();
	$("#log_close").click(function(){
		$("#log_container").hide();
		$("#page_overlay").hide();
	});
});

function showSuccess(info){
	toastr.success(info);
}
function showWarning(info){
	toastr.warning(info);
}
function showError(info){
	toastr.error(info);
}