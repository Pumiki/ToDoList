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

import com.assaf.yoni.model.HibernateToDoListDAO;
import com.assaf.yoni.model.ToDoListException;
import com.assaf.yoni.model.Users;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HibernateToDoListDAO hibernateDAO = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogInServlet() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int userId = 0;
		String nextView = "";
		try 
		{
			userId = this.hibernateDAO.getInstance().getUserId(username, password);			
			if (userId > 0)
			{
				request.getSession().setAttribute("Username", username);
				request.getSession().setAttribute("UserId", userId);				
				request.getSession().setAttribute("ItemList", this.hibernateDAO.getInstance().getItemByUser(userId));
				nextView = "/Management.jsp";
			}
			else
			{
				request.setAttribute("Message", "Incorrect username or password");
				nextView = "/Index.jsp";
			}
			
			RequestDispatcher view = request.getRequestDispatcher(nextView);
			view.include(request, response);
		} 
		catch (ToDoListException e1) 
		{
			e1.printStackTrace();
		}
	}
}

