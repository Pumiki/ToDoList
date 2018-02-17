package com.assaf.yoni.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;

import com.assaf.yoni.model.HibernateToDoListDAO;
import com.assaf.yoni.model.Items;
import com.assaf.yoni.model.ToDoListException;
import com.assaf.yoni.model.Users;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/MainPageUserServlet")
public class MainPageUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HibernateToDoListDAO hibernateDAO = null;
	private String username = "";
	private String password = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainPageUserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		String nextView = "";
		if((String) request.getSession().getAttribute("Username") != null) 
		{
			int userId = (int) request.getSession().getAttribute("UserId");
			String actionToDo = request.getParameter("ActionToDo");

			if (actionToDo != null)
			{
				switch (actionToDo)
				{
				case "AddNote":
				{
					String noteBody = request.getParameter("NoteBody");
					addNote(noteBody, userId);
					setRequestAttributesOfUser(request, userId);
					nextView = "/Management.jsp";
					break;
				}
				case "DeleteNote":
				{
					int noteId = Integer.parseInt(request.getParameter("NoteId"));
					deleteNote(noteId, userId);
					setRequestAttributesOfUser(request, userId);
					nextView = "/Management.jsp";
					break;
				}
				case "LogOut":
				{
					request.getSession().invalidate();
					nextView = "/Index.jsp";
				}
				}
			}	
		}
		else 
		{

			nextView = "/Index.jsp";
		}

		RequestDispatcher view = request.getRequestDispatcher(nextView);
		view.include(request, response);
	}

	private void addNote(String noteBody, int userId)
	{
		try 
		{;
		this.hibernateDAO.getInstance().addItem(noteBody, userId);
		} 
		catch (ToDoListException e) 
		{
			e.printStackTrace();
		}
	}

	private void deleteNote(int noteId, int userId) throws IOException
	{
		try 
		{
			this.hibernateDAO.getInstance().deleteItem(noteId, userId);;
		} 
		catch (ToDoListException e) 
		{
			e.printStackTrace();
		}
	}

	private void setRequestAttributesOfUser(HttpServletRequest request, int userId)
	{
		try 
		{
			request.getSession().setAttribute("ItemList", this.hibernateDAO.getInstance().getItemByUser(userId));
		} 
		catch (ToDoListException e)
		{
			e.printStackTrace();
		}	
	}
}
