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
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HibernateToDoListDAO hibernateDAO = null;   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String nextView = "";

		try 
		{
			if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
			{
				request.getSession().setAttribute("Message", "All fields must be filled");
				nextView = "/Register.jsp";
			}
			else if (!checkIfSameUsernameAlreadyExists(username))
			{
				int userId = 0;
				this.hibernateDAO.getInstance().addUser(username, password, firstname, lastname);
				userId = this.hibernateDAO.getInstance().getUserId(username, password);
				request.getSession().setAttribute("Username", username);
				request.getSession().setAttribute("UserId", userId);
				nextView = "/Management.jsp";
			}
			else
			{
				request.getSession().setAttribute("Message", "User is already exist");
				nextView = "/Register.jsp";
			}
			
			RequestDispatcher view = request.getRequestDispatcher(nextView);
			view.include(request, response);
		} 
		catch (ToDoListException e)
		{
			e.printStackTrace();
		}

	}

	private Boolean checkIfSameUsernameAlreadyExists(String username)
	{
		Boolean isUsernameAlreadyInDB = false;

		try 
		{
			List<Users> users = this.hibernateDAO.getInstance().getUsers();
			for (Users user : users)
			{
				if (user.getUsername().equals(username))
				{
					isUsernameAlreadyInDB = true;
					break;
				}
			}
		}
		catch (ToDoListException e)
		{
			e.printStackTrace();
		}

		return isUsernameAlreadyInDB;
	}

}
