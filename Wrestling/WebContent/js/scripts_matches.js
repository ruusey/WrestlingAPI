var statsArray = [];
var movesArray= [];
var isMeet = false;
var currentTeam=1;
var currMove=0;
var moveToScoreMap = [];
function populateWrestlerSelect(){
	$.ajax({
	    url: '/rest/Wrestling/GetAll?teamId='+currentTeam,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addSelectRow($('#wrestler_select'),item["name"],item["id"]);
	    		
	    	});
	    }
	});
}
function startNewCurrentMatch(){
	$.ajax({
	    url: '/rest/Wrestling/TruncateCurrentMatch',
	    type: 'GET',
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	 
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
}
function populatePreparedMatches(){
	$.ajax({
	    url: '/rest/Wrestling/GetAll?teamId='+currentTeam,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addPreparedMatch($('#prepared_matches'),item["name"],item["id"]);
	    		
	    	});
	    }
	});
}
function addPreparedMatch(tbody,name,id){
	var oppTeam = $("#away_team_name").val();
	$("#meet_versus_data").text("Meet Versus "+oppTeam);
	var trow = $('<tr>').attr("id", "trow_meet"+id);
	trow.append($("<td>").val(id).attr("id","home_match"+id).text(name));
	trow.append('<td><input type="text" id="opp_match'+id+'" placeholder="Opponent" /></td>');
	trow.append($('<i>').attr("onclick","setupMeetMatch("+id+")").attr("class","fa fa-play-circle fa-2x begin"));
	trow.append($('<i>').attr("onclick","removePreparedMatch("+id+")").attr("class","fa fa-times-circle fa-2x begin"));
	trow.appendTo(tbody);
}

function sendStats(stats){
	$.ajax({
	    url: '/rest/Wrestling/UpdateStats',
	    type: 'POST',
	    data: JSON.stringify(stats),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: false,
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    		//window.location.href = "/Wrestling/Match";
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
}
function updateCurrentMatch(matchItem){
	$.ajax({
	    url: '/rest/Wrestling/UpdateCurrentMatch',
	    type: 'POST',
	    data: JSON.stringify(matchItem),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: false,
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
}
function setupMeet(){
	
	isMeet=true;
	populatePreparedMatches();
	populateWrestlerSelectManual();
	$("#meet_data").show();
	$("#prepared_matches_container").show();
	$("#add_prepared_match_manual").show();
	$("#new_solo_match").hide();
	$("#new_meet_vs").hide();
	
}
function addSelectRow(sbody,name,id){
	sbody.append($("<option />").val(id).text(name));
}
$( document ).ready(function() {
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
	$("#coach_menu2").hide();
	$(".notice.error").hide();
    $(".notice.warning").hide();
    $(".notice.success").hide();
    $("#new_meet_container").hide();
    $("#new_solo_container").hide();
    $("#add_prepared_match_manual").hide();
    
    $("#home_forfeit").click(function(){
    	$("#away_meet_score").text(parseInt($("#away_meet_score").text())+6);
	});
	$("#away_forfeit").click(function(){
		$("#home_meet_score").text(parseInt($("#home_meet_score").text())+6);
	}); 
	$("#away_technical").click(function(){
    	$("#away_meet_score").text(parseInt($("#away_meet_score").text())-1);
	});
	$("#home_technical").click(function(){
		$("#home_meet_score").text(parseInt($("#home_meet_score").text())-1);
	});
	
	$("#close_current_match").click(function(){
		closeMatch();
	});
	populateWrestlerSelect();
	
	$("#meet_data").hide();
	$("#match_container").hide();
	$("#prepared_matches_container").hide();
	$("#home_wrestler_id").hide();
	$("#away_points").hide();
	$("#home_points").hide();
	$("#home_td").click(function(){
	    addPoint("home","takedown",2);
	}); 
	$("#home_e").click(function(){
		addPoint("home","escape",1);
	}); 
	$("#home_nf2").click(function(){
		addPoint("home","nearfall",2);
	}); 
	$("#home_nf3").click(function(){
		addPoint("home","nearfall",3);
	}); 
	$("#home_tv").click(function(){
		addPoint("home","tech violation",1);
	}); 
	$("#home_p").click(function(){
		addPoint("home","pin",0);
		finalize();
	}); 
	$("#away_td").click(function(){
		addPoint("away","takedown",2);
	}); 
	$("#away_e").click(function(){
		addPoint("away","escape",1);
	}); 
	$("#away_nf2").click(function(){
		addPoint("away","nearfall",2);
	}); 
	$("#away_nf3").click(function(){
		addPoint("away","nearfall",3);
	}); 
	$("#away_tv").click(function(){
		addPoint("away","tech violation",1);
	}); 
	$("#away_p").click(function(){
		addPoint("away","pin",0);
		if(isMeet){
			$("#away_meet_score").text(parseInt($("#away_meet_score").text())+6);
		}
		
		finalize();
	});
	$("#home_r").click(function(){
		addPoint("home","reversal",2);
	}); 
	$("#home_f").click(function(){
		addPoint("home","forfeit",1);
		finalize();
		closeMatch();
	}); 
	$("#away_r").click(function(){
		addPoint("away","reversal",2);
	});
	$("#next_period").click(function(){
		nextPeriod();
	}); 
	$("#undo_last").click(function(){
		undo();
	}); 
	$("#finalize_match").click(function(){
		finalize();
	}); 
	$("#show_coach_menu2").click(function(){

		var menuPos = $("#show_coach_menu2").offset();
		var width = $("#show_coach_menu2").outerWidth();
		var height = $("#show_coach_menu2").outerHeight();
		$("#coach_menu2").css({
			  position: "absolute",
		      top: (menuPos.top+height)+ "px",
		      left: menuPos.left+"px",
		      width: (width+50) +"px"
		});
		if($("#coach_menu2").is(":visible")){
			$("#menu_caret2").attr("class","fa fa-caret-right");
		}else{
			$("#menu_caret2").attr("class","fa fa-caret-down");
		}
		$("#coach_menu2").toggle();
	});
	$("#change_team_to_1_matches").click(function(){
		$("#wrestler_select").find("option:gt(0)").remove();
		currentTeam=1;
		populateWrestlerSelect();
		showSuccess("Now Managing Westminter Varsity");
	});
	$("#change_team_to_2_matches").click(function(){
		$("#wrestler_select").find("option:gt(0)").remove();
		currentTeam=2;
		populateWrestlerSelect();
		showWarning("Now Managing Westminter JV, Stats Are Not Being Tracked For This Team");
	});
});
function setupMatch(){
	startNewCurrentMatch();
	isMeet=false;
	$("#away_wrestler_p"+1).css({
		background:"white",
		border: "2px solid #00573c"
	});
	$("#home_wrestler_p"+1).css({
		background:"white",
		border: "2px solid #00573c"
	});
	
	var homeName = $( "#wrestler_select option:selected" ).text();
	var homeId = $( "#wrestler_select option:selected" ).val();
	if(homeId==0){
		showError("Please Select A Wrestler");
		return;
	}else{
		showSuccess("Match Started");
	}
	var awayName = $( "#away_name_input" ).val();
	$("#away_points").show();
	$("#home_points").show();
	$("#home_wrestler_name").text(homeName);
	$("#away_wrestler_name").text(awayName);
	$("#home_wrestler_id").text(homeId);
	$("#current_period").val("1");
	$("#current_period").text("Current Period: "+$("#current_period").val());
	$("#home_wrestler_score").text("0");
	$("#away_wrestler_score").text("0");
	$("#match_container").show();
	$("#new_solo_container").hide();
	$("#new_meet_container").hide();
}
function setupMeetMatch(id){
	startNewCurrentMatch();
	$("#add_prepared_match_manual").hide();
	$("#away_wrestler_p"+1).css({
		background:"white",
		border: "2px solid #00573c"
	});
	$("#home_wrestler_p"+1).css({
		background:"white",
		border: "2px solid #00573c"
	});
	var homeName = $( "#home_match"+id ).text();
	var homeId = id;
	if(homeId==0){
		showError("Please Select A Wrestler");
		return;
	}else{
		showSuccess("Meet Started");
	}
	var awayName = $( "#opp_match"+id ).val();
	$("#prepared_matches_container").hide();
	$("#w_select_container").hide();
	$("#away_points").show();
	$("#home_points").show();
	$("#home_wrestler_name").text(homeName);
	$("#away_wrestler_name").text(awayName);
	$("#home_wrestler_id").text(homeId);
	$("#current_period").val("1");
	$("#current_period").text("Current Period: "+$("#current_period").val());
	$("#home_wrestler_score").text("0");
	$("#away_wrestler_score").text("0");
	$("#match_container").show();
	$("#new_solo_container").hide();
	$("#new_meet_container").hide();
	
}
function addPoint(wrestler,pointName,pointValue){
	var currentPeriod=$("#current_period").val();
	var td=wrestler+"_wrestler_p"+currentPeriod;
	$("#"+td).append($('<a>').attr('class','match_point').attr("id","move"+currMove).text(pointName+": "+pointValue+" ")).append($('<i>').val(currMove).attr("id","i"+currMove).attr("style","color:red").attr("class","fa fa-undo fa-lg match_point").attr("onclick","undo('"+currMove+"','"+wrestler+"')").append(" "));
	$("#"+wrestler+"_wrestler_score").text(parseInt($("#"+wrestler+"_wrestler_score").text())+pointValue);
	
	if(pointName==="tech violation"){
		
	}else if(wrestler==="home"){
		addStatToArray(($("#home_wrestler_id").text()),$("#last_move").val(),pointName,pointValue);
	}
	log(wrestler,currentPeriod,pointName,pointValue);
	moveToScoreMap.push(pointValue);
	currMove=currMove+1;

}
function log(wrestler, period, pointName, pointValue){
	var w = $("#"+wrestler+"_wrestler_name").text();
	var json = {matchName:$("#home_wrestler_name").text()+'_vs_'+$("#away_wrestler_name").text(), wrestler:w+"("+wrestler+")", period:period, pointType:pointName, pointScore:pointValue};
	movesArray.push(json);
	updateCurrentMatch(json);
}
function nextPeriod(){
	if(parseInt($("#current_period").val())==3){
		
	}else{
		$("#match_table td").css({
			background:"#00573c",
			border: "2px solid white"
		});
		var period = parseInt($("#current_period").val())+1;
		$("#current_period").val(parseInt($("#current_period").val())+1);
		$("#current_period").text("Current Period: "+$("#current_period").val());
		$("#away_wrestler_p"+period).css({
			background:"white",
			border: "2px solid #00573c"
		});
		$("#home_wrestler_p"+period).css({
			background:"white",
			border: "2px solid #00573c"
		});
	}
}
function undo(move, wrestler){

	$("#move"+move).remove();
	$("#i"+move).remove();
	//findAndRemove("statID",move);
	$("#"+wrestler+"_wrestler_score").text(parseInt($("#"+wrestler+"_wrestler_score").text())-moveToScoreMap[move]);
	movesArray[move]=null;
	statsArray[move]=null;
}
function populateWrestlerSelectManual(){
	$.ajax({
	    url: '/rest/Wrestling/GetAll',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addSelectRow($('#wrestler_select_prepared'),item["name"],item["id"]);
	    		
	    	});
	    }
	});
}
function addManualMatch(){
	var tbody = $("#prepared_matches");
	var name = $( "#wrestler_select_prepared option:selected" ).text();
	var id = $( "#wrestler_select_prepared option:selected" ).val();
	var trow = $('<tr>').attr("id", "trow_meet"+id);
	trow.append($("<td>").val(id).attr("id","home_match"+id).text(name));
	trow.append('<td><input type="text" id="opp_match'+id+'" placeholder="Opponent" /></td>');
	trow.append($('<i>').attr("onclick","setupMeetMatch("+id+")").attr("class","fa fa-play-circle fa-2x begin"));
	trow.append($('<i>').attr("onclick","removePreparedMatch("+id+")").attr("class","fa fa-times-circle fa-2x begin"));
	trow.appendTo(tbody);
}
function addStatToArray(wrestlerID,statID,type,score){
	var json = {"wrestlerID":wrestlerID,"statID":statID, "type":type, "score":score};
	statsArray.push(json);
}
function findAndRemove(property, value) {
	   $.each(statsArray, function(index, result) {
	      if(result[property].valueOf() == value.valueOf()) {
	          statsArray=statsArray.splice(index, 1);
	      }
	   });
	}
function finalize(){
	$("#match_container").hide();
	$("#home_points").hide();
	$("#away_points").hide();
	var id = $("#home_wrestler_id").text();
	var dontTrackMatch = ($('#track_match_radio').is(':checked'))
	if(isMeet){
		$("#prepared_matches_container").show();
		$("#trow_meet"+id).remove();
		$("#add_prepared_match_manual").show();
	}else{
		$("#solo_match_selector").show();
		$("#meet_match_selector").show();
	}
	var res = parseInt($("#home_wrestler_score").text()) - parseInt($("#away_wrestler_score").text());
	var win = {"wrestlerID":id,"statID":"null", "type":"win", "score":1};
	var loss = {"wrestlerID":id,"statID":"null", "type":"loss", "score":1};
	
	var teampoint = 0;
	if(res>=15){
		teampoint=5;
		statsArray.push(win);
	}
	if(res>=8 && res<15){
		teampoint=4;
		statsArray.push(win);
	}
	if(res<8 && res>0){
		teampoint=3;
		statsArray.push(win);
	}
	if(res<0){
		statsArray.push(loss);
		
	}
	if(isMeet){
		if(res<0 && res<-8){
			$("#away_meet_score").text(parseInt($("#away_meet_score").text())+3);
			
		}
		if(res<=-8 && res<-15){
			$("#away_meet_score").text(parseInt($("#away_meet_score").text())+4);
			
		}
		if(res<=-15){
			$("#away_meet_score").text(parseInt($("#away_meet_score").text())+5);
			
		}
	}
	
	$.each(statsArray, function(index, result) {
		if(statsArray[index]==null){
			return;
		}
	    if(result["type"] === "pin") {
	          teampoint=6;
	      }    
	   });
	var teampoints = {"wrestlerID":id,"statID":"null", "type":"teampoint", "score":teampoint};
	if(isMeet){
		$("#home_meet_score").text(parseInt($("#home_meet_score").text())+teampoint);
	}
	if(isMeet){
		statsArray.push(teampoints);
	}
	if(currentTeam==1 && !dontTrackMatch){
		sendStats(statsArray);
	}else{
		showWarning("Stats Are Not Being Tracked For This Team");
	}
	
	sendMatchLog();
	closeMatch();
	
	
}
function sendMatchLog(){
	$.ajax({
	    url: '/rest/Wrestling/LogMatch',
	    type: 'POST',
	    data: JSON.stringify(movesArray),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: false,
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    		//window.location.href = "/Wrestling/Match";
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
	
	resetArrays();
}
function closeMatch(){
	for (i = 1; i < 4; i++) { 
		$("#away_wrestler_p"+i).css({
			background:"#00573c",
			border: "2px solid white"
		});
		$("#home_wrestler_p"+i).css({
			background:"#00573c",
			border: "2px solid white"
		});
	}
	
	resetArrays();
	$('.match_point').remove();
	if(isMeet){
		$("#add_prepared_match_manual").show();
		$("#prepared_matches_container").show();
		$("#match_container").hide();
		$("#home_points").hide();
		$("#away_points").hide();
	}else{
		$("#match_container").hide();
		$("#home_points").hide();
		$("#away_points").hide();
		$("#solo_match_selector").show();
		$("#meet_match_selector").show();
	}
}
function resetArrays(){
	statsArray=[];
	movesArray=[];
}
	
function removePreparedMatch(id){
	$("#trow_meet"+id).remove();
}
function showMeet(){
	$("#new_meet_container").show();
	$("#solo_match_selector").hide();
	$("#meet_match_selector").hide();
}
function showSoloMatch(){
	$("#new_solo_container").show();
	$("#solo_match_selector").hide();
	$("#meet_match_selector").hide();
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