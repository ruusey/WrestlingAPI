var currentTeam = 1;
var refreshIntervalId;
function getEvents(){
	
	$("#current_events").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetEvents',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addToTicker(item["ID"],item["eventName"],item["description"],item["dateStr"]);
	    	});
	    	
	    }
	});
}
function getCurrentMatch(){
	$("#current_match_table").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetCurrentMatch',
	    type: 'GET',
	    success: function(msg) {
	    	if(msg.length<=0){
	    		$("#no_current_match").show();
	    	}else{
	    		$("#no_current_match").hide();
	    		$.each(msg,function(i,item) {
		    		addCurrentMatchRow($("#current_match_table"), item["matchName"],item["period"],item["wrestler"],item["pointType"], item["pointScore"], item["date"]);
		    	});
	    	}
	    	
	    	
	    }
	});
}
function getStatSheet(){
	$("#stat_sheet_table").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetStats',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addStatsSheetDataRow($('#stat_sheet_table'),item["wrestlerName"],item["takedown"],item["escape"],item["nearfall"],item["pin"],item["win"],item["loss"],item["teampoint"],item["reversal"],item["forfeit"]);
	    	});
	    	
	    	
	    }
	});
}

function addCurrentMatchRow(tbody,matchName, period,  wrestler, pointName, pointValue, date){
	var trow = $('<tr>');
	trow.append($("<td>").text(matchName));
	trow.append($("<td>").text(wrestler));
	trow.append($("<td>").text(period));
	trow.append($("<td>").text(pointName));
	trow.append($("<td>").text(pointValue));
	trow.append($("<td>").text(date));
	trow.appendTo(tbody);
}
function addToTicker(id,name,description,date) {
	var par = $('<p>').attr('onmouseover','displayTickerDescription("'+description+'","event'+id+'")').attr('id',"event"+id).attr('onmouseout','closeTickerDescription();');
	par.text(name+" @ "+date);
	par.appendTo($("#news_container"));
}
function displayTickerDescription(description,name){
	var pos = $("#"+name).offset();
	var width = $("#"+name).outerWidth();
    $("#ticker_description").css({
        position: "absolute",
        left: (pos.left)+"px",
        width: "20%"
    });
    $("#ticker_description_text").text(description);
    $("#ticker_description").show();
}
function closeTickerDescription(){
	$("#ticker_description_text").text("");
	$("#ticker_description").hide();
}

function setBulletin(html){
	
	$.ajax({
	    type: 'POST',
	    // make sure you respect the same origin policy with this url:
	    // http://en.wikipedia.org/wiki/Same_origin_policy
	    url: '/rest/Wrestling/SetBulletin',
	    data: html,
	    success: function(msg){
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
}
function getBulletin(){
	
	$.ajax({
	    type: 'GET',
	    // make sure you respect the same origin policy with this url:
	    // http://en.wikipedia.org/wiki/Same_origin_policy
	    url: '/rest/Wrestling/GetBulletin',
	    success: function(msg){
	    	CKEDITOR.instances.team_bulletin.setData(msg)
	    }
	});
}
$( document ).ready(function() {
	CKEDITOR.replace('team_bulletin');
	 setTimeout(getBulletin,500);
	$("#update_bulletin").click(function(){
		setBulletin(CKEDITOR.instances.team_bulletin.getData());
	});
	$("#change_team_to_1").click(function(){
		currentTeam=1;
		getWrestlers();
		getTopStats();
		showSuccess("Now Managing Westminter Varsity");
	});
	$("#change_team_to_2").click(function(){
		currentTeam=2;
		getWrestlers();
		getTopStats();
		showSuccess("Now Managing Westminter JV");
	});
	$("#view_current_match").click(function(){
		startCurrentMatchRetrieval();
		showSuccess("Updating Current Match");
	});
	toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "newestOnTop": false,
			  "progressBar": false,
			  "positionClass": "toast-top-center",
			  "preventDuplicates": false,
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "10000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
	$("#page_overlay").hide();
	$("#coach_menu").hide();
	$("#sms_send_div").hide();
	$("#current_match_container").hide();
	$("#new_wrestler_container").hide();
	$("#ticker_description").hide();
	$("#show_coach_menu").click(function(){
		var menuPos = $("#show_coach_menu").offset();
		var width = $("#show_coach_menu").outerWidth();
		var height = $("#show_coach_menu").outerHeight();
		$("#coach_menu").css({
			  position: "absolute",
		      top: (menuPos.top+height)+ "px",
		      left: menuPos.left+"px",
		      width: (width+50) +"px"
		});
		if($("#coach_menu").is(":visible")){
			$("#menu_caret").attr("class","fa fa-caret-right");
		}else{
			$("#menu_caret").attr("class","fa fa-caret-down");
		}
		
		$("#coach_menu").toggle();
	});
	$("#banner_marquee").hover(function () { 
	    this.stop();
	}, function () {
	    this.start();
	});
	$("#show_contact_team").click(function(){
		$("#page_overlay").show();
		$("#sms_send_div").show();
	});
	$("#show_add_new_wrestler").click(function(){
		$("#page_overlay").toggle();
		$("#new_wrestler_container").toggle();
	});
	$("#new_wrestler_close").click(function(){
		$("#page_overlay").toggle();
		$("#new_wrestler_container").toggle();
	});
	$("#sms_send_close").click(function(){
		$("#page_overlay").hide();
		$("#sms_send_div").hide();
	});
	
	 $("#none_contact").change(function () {
        showError("You Will Not Receive Important Updates From Coach");
     });
	 $("#sms_send_button").click(function(){
			sendSMS();
		}); 
	 $('#phone_input').on('input',function(e){
	     if(isNaN($('#phone_input').val()) && $('#phone_input').val().length==10){
	    	 showError("Phone Number Must Contain No Special Characters And Must Be 10 Digits Long");
	    	 $("#new_wrestler_submit").attr("disabled","disabled");
	     }else{
	    	 $("#new_wrestler_submit").removeAttr("disabled");
	     }
	    });
	 $("#stat_sheet_container").hide();
	$("#wrestlerStats").hide();
	$("#update_div").hide();
	$("#delete_confirm").hide();
    $(".notice.error").hide();
    $(".notice.warning").hide();
    $(".notice.success").hide();
    $("#loading_stats").hide();
    $("#page_overlay").hide();
    getEvents();
    getStatSheet();
    getTopStats();
	getStats();
	getWrestlers();
	

})
function showStatSheet(){
	$("#stat_sheet_container").show();
	$("#page_overlay").show();
}
function addWrestlersDataRow(tbody,team,id,name,weight) {
	var trow = $('<tr>');
	$('<td>')
	.append($('<p>').text(team+""))
	
	.appendTo(trow);
	$('<td>')
		.append($('<p>').text(name+"").attr("class", "fa fa-bar-chart fa-lg").attr("onclick","openStats("+id+")").attr('id',"name"+id))
		
		.appendTo(trow);
	$('<td>')
		.append($('<p>').text(weight+"").attr('id',"weight"+id))
		.appendTo(trow);

		trow.appendTo(tbody);
}
function addStatsDataRow(tbody,name,takedowns,escapes,nearfall,pins,wins,losses, teampoints, reversals, forfeits) {
	var trow = $('<tr>');
	$('<td>').text(name).appendTo(trow);
	$('<td>').text(takedowns).appendTo(trow);
	$('<td>').text(escapes).appendTo(trow);
	$('<td>').text(nearfall).appendTo(trow);
	$('<td>').text(pins).appendTo(trow);
	$('<td>').text(wins).appendTo(trow);
	$('<td>').text(losses).appendTo(trow);
	$('<td>').text(teampoints).appendTo(trow);
	$('<td>').text(reversals).appendTo(trow);
	$('<td>').text(forfeits).appendTo(trow);
	
		trow.appendTo(tbody);
}
function addStatsSheetDataRow(tbody,wrestler,takedowns,escapes,nearfall,pins,wins,losses, teampoints, reversals, forfeits) {
	var trow = $('<tr>');
	$('<td>').text(wrestler).appendTo(trow);
	$('<td>').text(takedowns).appendTo(trow);
	$('<td>').text(escapes).appendTo(trow);
	$('<td>').text(nearfall).appendTo(trow);
	$('<td>').text(pins).appendTo(trow);
	$('<td>').text(wins).appendTo(trow);
	$('<td>').text(losses).appendTo(trow);
	$('<td>').text(teampoints).appendTo(trow);
	$('<td>').text(reversals).appendTo(trow);
	$('<td>').text(forfeits).appendTo(trow);
	
		trow.appendTo(tbody);
}
function populateIndividualStats(tbody,name,takedowns,escapes,nearfall,pins,wins,losses,teampoints, reversals, forfeits) {
	var trow = $('<tr>');
	$("#individual_stats_name").text("Stats For: "+name);
	$('<td>').text(takedowns).appendTo(trow);
	$('<td>').text(escapes).appendTo(trow);
	$('<td>').text(nearfall).appendTo(trow);
	$('<td>').text(pins).appendTo(trow);
	$('<td>').text(wins).appendTo(trow);
	$('<td>').text(losses).appendTo(trow);
	$('<td>').text(teampoints).appendTo(trow);
	$('<td>').text(reversals).appendTo(trow);
	$('<td>').text(forfeits).appendTo(trow);
	
		trow.appendTo(tbody);
}

function getWrestlers(){
	$("#wrestlers_table").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetAll?teamId=0',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addWrestlersDataRow($('#wrestlers_table'),item["team"].name,item["id"],item["name"],item["weight"]);
	    		
	    	});
	    }
	});
}
function getTopStats(){
	$("#top_stats").find("tr:gt(0)").remove();
	var tbody = $("#top_stats");
	$.ajax({
	    url: '/rest/Wrestling/GetTopStats?teamId='+currentTeam,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		var trow = $('<tr>');
	    		
	    		trow.append($("<td>").text(item["stat"]+"s"));
	    		trow.append($("<td>").text(item["wrestler"]));
	    		
	    		trow.append($("<td>").text(item["number"]));
	    		
	    		trow.appendTo(tbody);
	    		
	    	});
	    }
	});
}
function getStats(){
	$("#stats_table").find("tr:gt(0)").remove();
	//$('#stats_table').empty();
	$.ajax({
	    url: '/rest/Wrestling/GetStats',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addStatsDataRow($('#stats_table'),item["wrestler"],item["takedown"],item["escape"],item["nearfall"],item["pin"],item["win"],item["loss"],item["teampoint"],item["reversal"],item["forfeit"]);
	    	});
	    }
	});
}

function openUpdate(id){
	$.ajax({
	    url: '/rest/Wrestling/GetWrestler?wrestlerId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	$('#name_change').val(msg["name"]);
			$('#weight_change').val(msg["weight"]);
			$('#phone_change').val(msg["phone"]);
			$('#email_change').val(msg["email"]);
			$('#select_contact'+msg["contact"]).prop('checked',true);
			$('#team_change_select').val(msg["team"].id);
			
	
	    }
	});
	$('#update_div').toggle();
	$('#hidden_id_update').hide();
	$('#hidden_id_update').text(id);
	var pos = $("#"+id).offset();
	var width = $("#"+id).outerWidth();
	var height = $('#update_div').outerHeight();
	 //show the menu directly over the placeholder
    $("#update_div").css({
        position: "absolute",
        top: (pos.top-(height/2))+ "px",
        left: (pos.left+width)+"px",
        width: "20%"
    });
	
	
}
function closeUpdate(){
	$('#update_div').toggle();
}
function openStats(id){

	
	retrieveIndividualStats(id);
	 //show the menu directly over the placeholder
	$("#wrestlerStats").toggle();
	$("#page_overlay").toggle();
		
		
}
function closeStats(){
	$("#wrestlerStats").toggle();
	$("#page_overlay").hide();
}
function closeStatSheet(){
	$("#stat_sheet_container").hide();
	$("#page_overlay").hide();
}
function retrieveIndividualStats(id){
	$("#stats_table_container").hide();
	$("#loading_stats").show();
	
	$("#individual_stats").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/IndividualStats?wrestlerId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	populateIndividualStats($('#individual_stats'),$("#name"+id).text(),msg["takedown"],msg["escape"],msg["nearfall"],msg["pin"],msg["win"],msg["loss"],msg["teampoint"],msg["reversal"],msg["forfeit"]);
	    	
	    }
	});
	$("#loading_stats").hide();
	$("#stats_table_container").show();
}
function openDelete(id){
	$('#delete_confirm').toggle();
	$('#hiddenIdDel').hide();
	$('#hiddenIdDel').text(id);
	var pos = $("#"+id).offset();
	 //show the menu directly over the placeholder
    $("#delete_confirm").css({
        position: "absolute",
        top: pos.top+ "px",
        left: pos.left+"px",
    });
	
	
}
function startCurrentMatchRetrieval(){
	$("#current_match_container").show();
	$("#page_overlay").show();
	refreshIntervalId = setInterval(getCurrentMatch, 5000);
}
function stopCurrentMatchRetrieval(){
	$("#current_match_container").hide();
	$("#page_overlay").hide();
	clearInterval(refreshIntervalId);
}

function showSuccess(info){
	toastr.success(info);
}
function showWarning(info){
	toastr.warning(info);
}
function showError(info){
	toastr.error(info);
}
