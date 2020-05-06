package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/AdminHospital", "root", "");
			
			//For testing
			System.out.print("Successfully connected");
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return con;
	}

	public String insertHospital(String hosID, String hosName, String hosLocation,String hosPhoneno, String hosCharge) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) 
			{
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = "insert into hospital(`hosNO`,`hosID`,`hosName`,`hosLocation`,`hosPhoneno`,`hosCharge`)" + " values (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, hosID);
			preparedStmt.setString(3, hosName);
			preparedStmt.setString(4, hosLocation);
			preparedStmt.setInt(5, Integer.parseInt(hosPhoneno));
			preparedStmt.setDouble(6, Double.parseDouble(hosCharge));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
			
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readHospitals() {

		String output = "";
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
		    }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Hospital ID</th> <th>Hospital Name</th><th>Hospital Location</th>"
					+ "<th>Hospital PhoneNo</th> <th>Hospital Charge</th> <th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String hosNO = Integer.toString(rs.getInt("hosNO"));
				String hosID = rs.getString("hosID");
				String hosName = rs.getString("hosName");
				String hosLocation = rs.getString("hosLocation");
				String hosPhoneno = Integer.toString(rs.getInt("hosPhoneno"));
				String hosCharge = Double.toString(rs.getDouble("hosCharge"));
				
				
				// Add into the html table
				output += "<tr><td><input id='hidhosNOUpdate' name='hidhosNOUpdate' type='hidden' value='" + hosNO
						+ "'>" + hosID + "</td>";
				output += "<td>" + hosName + "</td>";
				output += "<td>" + hosLocation + "</td>";
				output += "<td>" + hosPhoneno + "</td>";
				output += "<td>" + hosCharge + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-hospitalno='"
						+ hosNO + "'>" + "</td></tr>";
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		} 
		catch (Exception e)
		{
			output = "Error while reading the hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String hosNO,String hosID, String hosName, String hosLocation, String hosPhoneno, String hosCharge) {
		String output = "";
		
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE hospital SET hosID=?,hosName=?,hosLocation=?,hosPhoneno=?,hosCharge=? WHERE hosNO=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, hosID);
			preparedStmt.setString(2, hosName);
			preparedStmt.setString(3, hosLocation);
			preparedStmt.setInt(4, Integer.parseInt(hosPhoneno));
			preparedStmt.setDouble(5, Double.parseDouble(hosCharge));
			preparedStmt.setInt(6, Integer.parseInt(hosNO));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
			
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(String hosNO) {
		String output = "";
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from hospital where hosNO=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hosNO));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
			
		} 
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
