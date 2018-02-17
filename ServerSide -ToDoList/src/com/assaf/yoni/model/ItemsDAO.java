package com.assaf.yoni.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface ItemsDAO {
	
	public void addItem(String description, int userId) throws ToDoListException;
	public List getItems() throws ToDoListException;
	public List getItemByUser(int userId) throws ToDoListException;
	public void updateItem(int itemId, int userId, String description) throws ToDoListException;
	public void deleteItem(int itemId, int userId) throws ToDoListException;
	public void deleteAllUserItems(int userId) throws ToDoListException;
}
