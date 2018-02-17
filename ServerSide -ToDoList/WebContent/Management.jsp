<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="com.assaf.yoni.model.Items" import="java.util.List" import="com.assaf.yoni.model.HibernateToDoListDAO" import="com.assaf.yoni.model.Users"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<div class="bgM">

      <form id="logOutForm" method="post" action="MainPageUserServlet">	
			<button type="Submit" class = "logOutButton" style = "margin-left": 185px;>Logout</button>
			<form> 
            <input type="hidden" name="ActionToDo" value="LogOut"> 
            </form> 
		</form>
		
	  <%
	    String username = (String) session.getAttribute("Username");
      	out.print("<p>");
      	out.print("Welcome, " + "<b>" + username + "!");
      	out.print("</b></p>");
      %>
    
      <p><welcomePar>My ToDoList:</welcomePar></p><br/><br/><br/><br/><br/><br/><br/>
	  <a href="#"><img id="addButton" src="http://www.clker.com/cliparts/u/F/K/J/M/A/add-button-md.png" width = 5%;></a><br/><br/>
	  	  	  
	  
		<form id="addToDoForm" method="post" action="MainPageUserServlet">
			<b>To Do: </b><input type="text" name="NoteBody" size="40"/><br>	
			<input type=submit value="Submit" />
			<form> 
            <input type="hidden" name="ActionToDo" value="AddNote"> 
            </form> 
		</form><br/><br/>

		<%
		int iColor = 1, iNumOfToDo = 1;
		String[] arrOfColors = {"success", "info", "dark"};
		String currentColor = null;
      	List<Items> itemList = (List<Items>) session.getAttribute("ItemList");
      	if (itemList != null)
      	{
		out.print("<div class='row'>");
		for (Items item : itemList)
		{
			currentColor = arrOfColors[iColor];
			out.print("<div class='col-sm-3'>");
			out.print("<div class='card-body'>");
			out.print("<div class='card text-white bg-" + currentColor + "'mb-3' style='max-width: 18rem;'>");
			out.print("<div class='card-header'>To Do: #" + iNumOfToDo);
			
			out.print("<form method='post' action='MainPageUserServlet'>");
			out.print("<input type='hidden' name='NoteId' value=" + item.getId() + ">");
			//out.print("<input type='submit' style = 'margin-left: 170px'; value='X'>");
			
			
			
			
			
			out.print("<input type='submit' class='btn btn-outline-danger' style = 'margin-left: 185px;margin-top: -25px;' value='X'/>");
			out.print("<form>");
            out.print("<input type='hidden' name='ActionToDo' value='DeleteNote'>"); 
            out.print("</form>");
			out.print("</form>");
			
			out.print("</div>");
			out.print("<div class='card-body'>");
			out.print("<p class='card-text'>" + item.getDescription() + "</p>");
			out.print("</div></div></div></div><br/>");
			iNumOfToDo++;
			iColor++;
			iColor = iColor % 3;
		}
		out.print("</div>");
      	}
		%>

		<script type="text/javascript">
			function func() {
				var element;
				element = document.getElementById("addToDoForm");
				element.style.display = element.style.display === "none" ? "block" : "none";
			}	
			
			document.getElementById("addToDoForm").style.display = "none";
			document.getElementById('addButton').onclick = function() {
				func();
			};
		</script>	
		
	</div>
	 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>