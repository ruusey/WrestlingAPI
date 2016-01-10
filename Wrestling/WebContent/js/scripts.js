var currentTeam = 1;
var statsArray = [];
function sendWrestler(){
	if($('#name_input').val().length<1){
		showError("Name Must Be Atleast 1 Character Long");
		return;
	}
	if($('#weight_input').val().length<3){
		showError("Enter A Valid Weight");
		return;
	}
	if($('#weight_input').val().length>3){
		showError("Enter Only One Weight");
		return;
	}
	var arr = { 
				id: 0,
				name: $('#name_input').val(), 
				weight: $('#weight_input').val(),
				email: $('#email_input').val(),
				phone: $('#phone_input').val(),
				contact: $('input[name=contact_radio]:checked', '#contact_methods').val(),
				team: {
					id: $('#teamSelect option:selected').val(), 
					name: $('#teamSelect option:selected').text()
					}
			};
	$.ajax({
	    url: '/rest/Wrestling/AddWrestler',
	    type: 'POST',
	    data: JSON.stringify(arr),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: false,
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
	getWrestlers();
	getStats();
}
function sendSMS(){
	$("#page_overlay").hide();
	$("#sms_send_div").hide();
	var text=$("#sms_input").val();
	$.ajax({
	    type: 'POST',
	    // make sure you respect the same origin policy with this url:
	    // http://en.wikipedia.org/wiki/Same_origin_policy
	    url: '/rest/Wrestling/SendSMS',
	    data: text,
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
function deleteWrestler() {
	var id = $("#hiddenIdDel").text();
	$.ajax({
	    url: '/rest/Wrestling/DeleteWrestler?wrestlerId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
	getWrestlers();
}
function modifyWrestler(){
	closeUpdate();
	var arr = { 
			id: $('#hidden_id_update').text(),
			name: $('#name_change').val(), 
			weight: $('#weight_change').val(),
			email: $('#email_change').val(),
			phone: $('#phone_change').val(),
			contact: $('input[name=contact_radio]:checked', '#contact_methods_change').val(),
			team: {
				id: $('#team_change_select option:selected').val(), 
				name: $('#team_change_select option:selected').text()
				}
		};
		$.ajax({
		    url: '/rest/Wrestling/Update',
		    type: 'POST',
		    data: JSON.stringify(arr),
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json',
		    async: false,
		    success: function(msg) {
		    	var str = JSON.stringify(msg);
		    	if(str.indexOf("true")>-1){
		    		showSuccess("Success: "+str);
		    	}else{
		    		showError("Error: "+str);
		    	}
		    	
		    }
		});
		getWrestlers();
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
	CKEDITOR.replace('team_bulletin',{toolbarStartupExpanded : false});
	$("#update_bulletin").click(function(){
		setBulletin(CKEDITOR.instances.team_bulletin.getData());
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
	$("#new_wrestler_container").hide();
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
	$("#show_contact_team").click(function(){
		$("#page_overlay").show();
		$("#sms_send_div").show();
	});
	$("#show_add_new_wrestler").click(function(){
		$("#page_overlay").toggle();
		$("#new_wrestler_container").toggle();
	});
	$("#show_manage_events").click(function(){
		getEvents();
		$("#page_overlay").toggle();
		$("#event_manage_container").toggle();
	});
	$("#show_contact_team_mobile").click(function(){
		$("#page_overlay").show();
		$("#sms_send_div").show();
	});
	$("#show_add_new_wrestler_mobile").click(function(){
		$("#page_overlay").toggle();
		$("#new_wrestler_container").toggle();
	});
	$("#show_manage_events_mobile").click(function(){
		getEvents();
		$("#page_overlay").toggle();
		$("#event_manage_container").toggle();
	});
	$("#event_manage_close").click(function(){
		$("#page_overlay").hide();
		$("#event_manage_container").hide();
	});
	$("#event_manage_add").click(function(){
		sendEvent();
	});
	$("#new_wrestler_close").click(function(){
		$("#page_overlay").toggle();
		$("#new_wrestler_container").toggle();
	});
	$("#sms_send_close").click(function(){
		$("#page_overlay").hide();
		$("#sms_send_div").hide();
	});
	$("#modify_stats_submit").click(function(){
		sendStats(statsArray);
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
	$("#wrestlerStats").hide();
	$("#loading_stats").hide();
	$("#update_div").hide();
	$("#delete_confirm").hide();
    $(".notice.error").hide();
    $(".notice.warning").hide();
    $(".notice.success").hide();
    $("#event_manage_container").hide();
    setTimeout(getBulletin,500);
    getTopStats();
	getStats();
	getWrestlers();
	

})

function addWrestlersDataRow(tbody,id,name,weight) {
	var trow = $('<tr>');
	$('<td>')
	.append($('<a>').attr('onclick',"openUpdate("+id+")").attr('class',"fa fa-pencil fa-2x").attr("id",id).attr("title","Edit Wrestler").css("color","white"))
	.append($('<a>').attr('onclick',"openDelete("+id+")").attr('class',"fa fa-times-circle fa-2x").attr("id",id+"del").attr("title","Delete Wrestler").css("color","white"))
	.appendTo(trow);
	$('<td>')
		.append($('<p>').text(name+"").attr("class", "fa fa-bar-chart fa-lg").attr("onclick","openStats("+id+")").attr('id',"name"+id))
		
		.appendTo(trow);
	$('<td>')
		.append($('<p>').text(weight+"").attr('id',"weight"+id))
		.appendTo(trow);
	
	
		trow.appendTo(tbody);
}
function addStatsDataRow(tbody,name,takedowns,escapes,nearfall,pins,wins,losses, teampoints, reversals,forfeits) {
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
function populateIndividualStats(tbody,name,takedowns,escapes,nearfall,pins,wins,losses,teampoints, reversals,forfeits) {
	var trow = $('<tr>');
	$("#individual_stats_name").text("Varsity Stats For "+name);
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
	    url: '/rest/Wrestling/GetAll?teamId='+1,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addWrestlersDataRow($('#wrestlers_table'),item["id"],item["name"],item["weight"]);
	    		
	    	});
	    }
	});
	$("#wrestlers_table_jv").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetAll?teamId='+2,
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addWrestlersDataRow($('#wrestlers_table_jv'),item["id"],item["name"],item["weight"]);
	    		
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
	$("#page_overlay").show();
	retrieveIndividualStatsUpdate(id);
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
	 //show the menu directly over the placeholder

	
	
}
function closeUpdate(){
	$('#update_div').toggle();
	$("#page_overlay").hide();
}
function openStats(id){
	
	
	retrieveIndividualStats(id);
	
   
	$("#wrestlerStats").toggle();
	$("#page_overlay").toggle();
		
}
function closeStats(){
	$("#wrestlerStats").toggle();
	$("#page_overlay").hide();
}
function retrieveIndividualStats(id){
	$("#stats_table_container").hide();
	$("#individual_stats").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/IndividualStats?wrestlerId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	populateIndividualStats($('#individual_stats'),$("#name"+id).text(),msg["takedown"],msg["escape"],msg["nearfall"],msg["pin"],msg["win"],msg["loss"],msg["teampoint"],msg["reversal"],msg["forfeit"]);
	    	
	    }
	});
	$("#stats_table_container").show();
}
function retrieveIndividualStatsUpdate(id){
	$("#individual_stats_modify").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/IndividualStats?wrestlerId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	populateIndividualStatsUpdate($('#individual_stats_modify'),$("#name"+id).text(),msg["takedown"],msg["escape"],msg["nearfall"],msg["pin"],msg["win"],msg["loss"],msg["teampoint"],msg["reversal"],msg["forfeit"],id);
	    	
	    }
	});
}
function populateIndividualStatsUpdate(tbody,name,takedowns,escapes,nearfall,pins,wins,losses,teampoints, reversals, forfeits, id) {
	var trow = $('<tr>');
	$("#individual_stats_name").text("Varsity Stats For "+name);
	$('<td>').append($('<p>').text(takedowns).attr('id','takedown_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"takedown"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"takedown"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(escapes).attr('id','escape_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"escape"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"escape"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(nearfall).attr('id','nearfall_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"nearfall"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"nearfall"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(pins).attr('id','pin_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"pin"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"pin"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(wins).attr('id','win_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"win"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"win"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(losses).attr('id','loss_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"loss"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"loss"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(teampoints).attr('id','teampoint_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"teampoint"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"teampoint"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(reversals).attr('id','reversal_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"reversal"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"reversal"'+','+-1+')')).appendTo(trow);
	$('<td>').append($('<p>').text(forfeits).attr('id','forfeit_update')).append($('<i>').attr('class','fa fa-plus fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"forfeit"'+','+1+')')).append($('<i>').attr('class','fa fa-minus-square fa-lg').attr('onclick','addStatToArray('+id+','+null+','+'"forfeit"'+','+-1+')')).appendTo(trow);
	
	
	
		trow.appendTo(tbody);
}
function addStatToArray(wrestlerID,statID,type,score){
	$("#"+type+"_update").text(parseInt($("#"+type+"_update").text())+score);
	var json = {"wrestlerID":wrestlerID,"statID":statID, "type":type, "score":score};
	statsArray.push(json);
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
	statsArray=[];
}
function sendEvent(){
	var json = {
			eventName: $("#event_title").val(),
			description: $("#event_description").val(),
			dateStr: $("#event_date").val()	
	}
	$.ajax({
	    url: '/rest/Wrestling/AddEvent',
	    type: 'POST',
	    data: JSON.stringify(json),
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
	getEvents();
}
function getEvents(){
	
	$("#current_events").find("tr:gt(0)").remove();
	$.ajax({
	    url: '/rest/Wrestling/GetEvents',
	    type: 'GET',
	    success: function(msg) {
	    	$.each(msg,function(i,item) {
	    		addEventTableRow($('#current_events'),item["ID"],item["eventName"],item["description"],item["dateStr"]);
	    	});
	    	
	    }
	});
}
function deleteEvent(id){
	
	$.ajax({
	    url: '/rest/Wrestling/DeleteEvent?eventId='+id,
	    type: 'GET',
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    	}else{
	    		showError("Error: "+str);
	    	}
	    }
	});
	getEvents();
}
function addEventTableRow(tbody,id,name,description,date) {
	var trow = $('<tr>').attr("id","event_"+id).val(id);
	$('<td>').text(name).appendTo(trow);
	$('<td>').text(description).appendTo(trow);
	$('<td>').text(date).appendTo(trow);
	$('<td>').append($('<i>').attr('class','fa fa-times-circle fa-lg').attr('onclick','deleteEvent("'+id+'")')).appendTo(trow);
	
	
		trow.appendTo(tbody);
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
(function($) {

	  $.fn.menumaker = function(options) {
	      
	      var cssmenu = $(this), settings = $.extend({
	        title: "Menu",
	        format: "dropdown",
	        sticky: false
	      }, options);

	      return this.each(function() {
	        cssmenu.prepend('<div id="menu-button">' + settings.title + '</div>');
	        $(this).find("#menu-button").on('click', function(){
	          $(this).toggleClass('menu-opened');
	          var mainmenu = $(this).next('ul');
	          if (mainmenu.hasClass('open')) { 
	            mainmenu.hide().removeClass('open');
	          }
	          else {
	            mainmenu.show().addClass('open');
	            if (settings.format === "dropdown") {
	              mainmenu.find('ul').show();
	            }
	          }
	        });

	        cssmenu.find('li ul').parent().addClass('has-sub');

	        multiTg = function() {
	          cssmenu.find(".has-sub").prepend('<span class="submenu-button"></span>');
	          cssmenu.find('.submenu-button').on('click', function() {
	            $(this).toggleClass('submenu-opened');
	            if ($(this).siblings('ul').hasClass('open')) {
	              $(this).siblings('ul').removeClass('open').hide();
	            }
	            else {
	              $(this).siblings('ul').addClass('open').show();
	            }
	          });
	        };

	        if (settings.format === 'multitoggle') multiTg();
	        else cssmenu.addClass('dropdown');

	        if (settings.sticky === true) cssmenu.css('position', 'fixed');

	        resizeFix = function() {
	          if ($( window ).width() > 768) {
	            cssmenu.find('ul').show();
	          }

	          if ($(window).width() <= 768) {
	            cssmenu.find('ul').hide().removeClass('open');
	          }
	        };
	        resizeFix();
	        return $(window).on('resize', resizeFix);

	      });
	  };
	})(jQuery);

	(function($){
	$(document).ready(function(){

	$("#cssmenu").menumaker({
	   title: "Menu",
	   format: "multitoggle"
	});

	});
	})(jQuery);

