<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <title>ToDoList</title>
  </head>
  
  <body>
	<div class="bg">
	<div class="row">
	<div class="col-md-4 col-sm-4 col-xs-12"></div>
	<div class="col-md-4 col-sm-4 col-xs-12">
		<p><welcomePar>Welcome</welcomePar></p>
 	<form class="form-container" method="post" action="LogInServlet">
	  <div class="form-group">
	    <label for="InputUsername">Username</label>
	    <input type="text" class="form-control" placeholder="Enter Username" name="username">
	  </div>
	  <div class="form-group">
	    <label for="InputPassword">Password</label>
	    <input type="password" class="form-control" placeholder="Enter password" name="password">
	  </div><br/>
	  <button type="submit" class="btn btn-dark btn-block">Submit</button><br/>
	  <%
      	String Message =(String) request.getAttribute("Message");
      	if(Message != null)
      	{
      		out.print("<p><font color='red'>  ");
      		out.print(Message);
      		out.print("</font></p>");
      	}
      %>
	  <pressReg>Don't have account yet? <a href="Register.jsp">Register for free!</a></pressReg>
	</form><br/><br/><br/><br/><br/><br/><br/><br/>
	</div>
	<div class="col-md-4 col-sm-4 col-xs-12"></div>
	</div>
	</div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </body>
</html>