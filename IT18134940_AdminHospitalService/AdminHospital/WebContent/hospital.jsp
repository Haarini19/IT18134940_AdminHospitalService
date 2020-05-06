<%@page import="model.Hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Hospital</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/hospital.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Hospital Management</h1>
				<form id="formHospital" name="formHospital">
					Hospital ID: 
					<input id="hosID" name="hosID" type="text"
						class="form-control form-control-sm"> <br> 
						
					Hospital Name:
					<input id="hosName" name="hosName" type="text"
						class="form-control form-control-sm"> <br>
						
					Hospital Location: 
					 <input id="hosLocation" name="hosLocation" type="text"
						class="form-control form-control-sm"> <br> 
						
					Hospital PhoneNo: 
					 <input id="hosPhoneno" name="hosPhoneno" type="text"
						class="form-control form-control-sm"> <br> 
						
					Hospital Charge: 
					 <input id="hosCharge" name="hosCharge" type="text"
						class="form-control form-control-sm"> <br> 
					
						
						<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidhosNOSave" name="hidhosNOSave" value="">
						
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divHospitalGrid">
					<%
						Hospital hospitalObj = new Hospital();
					    out.print(hospitalObj.readHospitals());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>