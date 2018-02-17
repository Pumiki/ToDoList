package com.assaf.yoni.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface UsersDAO {
	
	public void addUser(String username, String firstname, String lastname, String email) throws ToDoListException;
	public List getUsers() throws ToDoListException;
	public int getUserId(String username, String password) throws ToDoListException;
	public void updateUser(int userId, String password, String firstname, String lastname) throws ToDoListException;
	public void deleteUser(int userId) throws ToDoListException;
}
