<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
<head>


  <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>



<style>
body {font-family: Arial, Helvetica, sans-serif;
    background-color: lightblue;}
* {box-sizing: border-box;}


input[type=text],input[type=Date],input[type=tel],input[type=number],input[type=password],input[type=Email], select, textarea {
    width: 50%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
    resize: vertical;
}
input[type=text],input[type=Date],input[type=number],input[type=tel],input[type=password],input[type=Email],textarea{
    background-color: lightblue;
}
input[type=text]:hover,input[type=password]:hover,input[type=Date]:hover,input[type=tel]:hover,input[type=Email]:hover,textarea:hover,input[type=number]:hover{
    background-color: white;
}
input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color:white;
}

.container {
    border-radius: 5px;
    background-color: black;
    padding: 20px;
}

/* Set a style for the submit button */
.bt {
    background-color: #4CAF50;
    color: white;
    padding: 16px 20px;
    border: none;
    cursor: pointer;
    width: 20%;
    opacity: 0.9;
}

.bt:hover {
    opacity: 1;
    width: 20%;
    background-color: white;
}

/* Set a style for the submit button */
.btn {
    background-color: #4CAF50;
    color: white;
    padding: 16px 20px;
    border: none;
    cursor: pointer;
    width: 50%;
    opacity: 0.9;
}

.btn:hover {
    opacity: 1;
    width: 50%;
    background-color: white;
}
table, th, td {  
    border: 1px solid black;  
    border-collapse: collapse;  
}  
th, td {  
    padding: 10px;  
}  
table#alter tr:nth-child(even) {  
    background-color: #eee;  
}  
table#alter tr:nth-child(odd) {  
    background-color: #fff;  
}  
table#alter th {  
    color: white;  
    background-color: green;  
}  


</style>
</head>
<body>
 
<div class="container">
    <center><h1><font color="white"><b>AVAILABLE CARS</b></font></h1></center>  
    <center><h3><font color="white"><b>Select anyone car from the table below for reservation</b></font></h3></center>  
<%
try {
/* Create string of connection url within specified format with machine
name, port number and database name. Here machine name id localhost and 
database name is student. */
String connectionURL = "jdbc:mysql://localhost:3306/car";
// declare a connection by using Connection interface
Connection connection = null;
/* declare object of Statement interface that is used for executing sql 
statements. */
Statement statement = null;
// declare a resultset that uses as a table for output data from tha table.
ResultSet rs = null;
// Load JBBC driver "com.mysql.jdbc.Driver"
Class.forName("com.mysql.jdbc.Driver").newInstance();
/* Create a connection by using getConnection() method that takes parameters 
of string type connection url, user name and password to connect to database.*/
connection = DriverManager.getConnection(connectionURL, "root", "");
/* createStatement() is used for create statement object that is used for 
sending sql statements to the specified database. */
statement = connection.createStatement();
// sql query to retrieve values from the secified table.
String QueryString = "SELECT carid,cartype,rent,vin from rentcar where status ='NOT BOOKED'";
rs = statement.executeQuery(QueryString);
%>
<table id="alter">
<tr>
<th>Car ID</th>
<th>Car Type</th>
<th>Rent per day</th>
<th>Vechicle Identification Number</th>

</tr>
<%
while (rs.next()) {
%>
<tr>
<td><%=rs.getInt(1)%></td>
<td><%=rs.getString(2)%></td>
<td><%=rs.getString(3)%></td>
<td><%=rs.getString(4)%></td>
</tr>
<% } %>
<%
// close all the connections.
rs.close();
statement.close();
connection.close();
} catch (Exception ex) {
%>
</font>
<font size="+3" color="red"></b>
<%
out.println("Unable to connect to database.");
}
%>
</table>
    </div>
    <h3></h3>
<div class="container">
    <center><h1><font color="white"><b>TRAVEL DETAILS</b></font></h1></center> 
   <div class="row"> 
<div class="col-sm-6">
  <form action="http://localhost:8080/cars/TravelDetail" onsubmit="return validate()" method="post">
 
<br/>
<label for="fname"><font color="white">Pick-up place</font></label>   
    <br/> <input type="text" id="fname" name="pickup" placeholder="Enter Pick-up place.. " required>
<br/>
<label for="fname"><font color="white">Drop-off place</font></label>   
    <br/> <input type="text" id="fname" name="drop" placeholder="Enter Drop-off place.. " required>
<br/>
<label for="fname"><font color="white">Start Date</font></label>   
    <br/> <input type="Date" id="start" name="sdate" placeholder="Enter Start Date.. " required>
<br/>
<label for="fname"><font color="white">End Date</font></label>   
    <br/> <input type="Date" id="end" name="edate" placeholder="Enter End Date.. " required>
<br/>
    <label for="lno"><font color="white">License No</font></label>   
    <br/> <input type="text" id="lno" name="licenseno" >  
<br/>
    <label for="cid"><font color="white">Car ID : (select car id from the above table)</font></label>   
    <br/> <input type="number" id="cid" name="carid" placeholder="" required>
<br/>
    <input  class="btn btn-md" type="submit" value="Next">
<h3></h3>
     </form>
<!--form validation-->
<script>
            function validate()

                {
                    var firstname=document.getElementById("fname");
                   
                    var letters= /^[A-Za-z]+$/;
                    var numbers= /^[0-9]+$/;
                    var letternum=/^[A-Za-z0-9_@]+$/;
                 
                    if(firstname.value.match(letters))
                    {
                        
                    }
                    else
                    {
                        alert("enter a valid first name");
                        return false;
                    }
                
                return true;

                }
       </script>

</div>
    
 </body>
</html>