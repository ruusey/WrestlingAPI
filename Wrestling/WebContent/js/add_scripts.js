

function sendWrestler(){
	
	if($('#name_input').val().length<1){
		showError("Name Must Be Atleast 1 Character Long");
		$("#new_wrestler_submit").attr("disabled",false);
		return;
	}
	if($('#weight_input').val().length<3){
		$("#new_wrestler_submit").attr("disabled",false);
		showError("Enter A Valid Weight");
		return;
	}
	if(isNaN($('#phone_input').val())){
		showError("Phone Number Must Be A Number And Contain No Special Characters");
		$("#new_wrestler_submit").attr("disabled",false);
		return;
	}
	if($('#email_input').val().indexOf("@")==-1){
		showError("Must Be A Valid Email Adress");
		$("#new_wrestler_submit").attr("disabled",false);
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
	    url: '/rest/Wrestling/Add',
	    type: 'POST',
	    data: JSON.stringify(arr),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: true,
	    success: function(msg) {
	    	var str = JSON.stringify(msg);
	    	if(str.indexOf("true")>-1){
	    		showSuccess("Success: "+str);
	    		$("#success_message").show();
	    		$("#new_wrestler_container").hide();
	    	}else{
	    		$("#new_wrestler_submit").attr("disabled",false);
	    		showError("Error: "+str);
	    		
	    	}
	    }
	});
}
$( document ).ready(function() {
	$("#success_message").hide();
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

	$("#new_wrestler_submit").click(function(){
		$("#new_wrestler_submit").attr("disabled",true);
		sendWrestler();
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
