$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidhosNOSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "HospitalAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onHospitalSaveComplete(response.responseText, status);
		}
	});
});

function onHospitalSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidhosNOSave").val("");
	$("#formHospital")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidhosNOSave").val(
					$(this).closest("tr").find('#hidhosNOUpdate').val());
			$("#hosID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#hosName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#hosLocation").val($(this).closest("tr").find('td:eq(2)').text());
			$("#hosPhoneno").val($(this).closest("tr").find('td:eq(3)').text());
			$("#hosCharge").val($(this).closest("tr").find('td:eq(4)').text());
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "HospitalAPI",
		type : "DELETE",
		data : "hosNO=" + $(this).data("hospitalno"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});
function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validateHospitalForm() {
	// CODE
	if ($("#hosID").val().trim() == "") {
		return "Insert Hospital ID.";
	}
	// NAME
	if ($("#hosName").val().trim() == "") {
		return "Insert Hospital Name.";
	}
	//LOCATION
	if ($("#hosLocation").val().trim() == "") {
		return "Insert Hospital Location.";
	}
	// PHONE NO-------------------------------
	if ($("#hosPhoneno").val().trim() == "") {
		return "Insert Phone no.";
	}
	// is numerical value
	var tmpPhoneno = $("#hosPhoneno").val().trim();
	if (!$.isNumeric(tmpPhoneno)) {
		return "Insert a numerical value for Phone no.";
	}
	// PRICE-------------------------------
	if ($("#hosCharge").val().trim() == "") {
		return "Insert Hospital Charge.";
	}
	// is numerical value
	var tmpCharge = $("#hosCharge").val().trim();
	if (!$.isNumeric(tmpCharge)) {
		return "Insert a numerical value for Hospital Charge.";
	}
	// convert to decimal price
	$("#hosCharge").val(parseFloat(tmpCharge).toFixed(2));
	
	
	return true;

}
