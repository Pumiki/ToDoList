package com.assaf.yoni.model;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class HibernateToDoListDAO implements IToDoListDAO {
	
	private static HibernateToDoListDAO instanceOfDAO = null;
	// create session factory
	private SessionFactory factory = null;
	// create session
	private Session session = null;
	
	
	public HibernateToDoListDAO() {
		factory = new AnnotationConfiguration().configure()
		.buildSessionFactory();
	}
	
	// Singleton pattern
	public static HibernateToDoListDAO getInstance(){
		if(instanceOfDAO == null) {
			instanceOfDAO = new HibernateToDoListDAO();
		}
		return instanceOfDAO;
	}
	
	// Add item to Items table
	@Override
	public void addItem(String description, int userId) throws ToDoListException{
		if (description.isEmpty())
			return;
		try {
			// Create a Item object
			Items newItem = new Items(description,userId);
			insertToDB(newItem);
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: addItem");
		}
		finally {
			closeConnection();
		}
	}
	
	// Add user to Users table
	@Override
	public void addUser(String username,String password, String firstname, String lastname) throws ToDoListException{
		if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
			return;
		try {
			// Create a Item object
			Users newUser = new Users(username, password, firstname, lastname);
			insertToDB(newUser);
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: addUser");
		}
		finally {
			closeConnection();
		}
	}
	
	// Return list of all items
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public List getItems() throws ToDoListException{
		try {
			openConnection();
			List retItems = retrieveList();
			List<Items> itemList = this.session.createQuery("from Items").list();
		    commitTrans();
		    for(Items item : itemList) {
		    	retItems.add(item);
		    }
			return retItems;
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: getItems");
		}
		finally {
			closeConnection();
		}
	}
	
	// Return list of all users
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public List getUsers() throws ToDoListException{
		try {
			openConnection();
			List<Users> userList = this.session.createQuery("from Users").list();
		    commitTrans();
			return userList;
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: getUsers");
		}
		finally {
			closeConnection();
		}
	}
	
	// Return user id 
	@SuppressWarnings("unchecked")
	@Override
	public int getUserId(String username, String password) throws ToDoListException{	
		int requiredUserId = 0;
	    List<Users> allUsers = getUsers();
	    for (Users checkedUser : allUsers)
	    {
	    	System.out.println(checkedUser.toString());
	    	if (checkedUser.getUsername().equals(username) && checkedUser.getPassword().equals(password))
	    	{
	    		requiredUserId = checkedUser.getId();
	    		break;
	    	}
	    }
	    return requiredUserId;
	}
	
	// Return all items that belongs to user
	@SuppressWarnings("rawtypes")
	@Override
	public List getItemByUser(int userId) throws ToDoListException{
		try {
			openConnection();
			List items = session.createQuery("FROM Items WHERE USER_ID = " + userId).list();
			return items;		
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: getItemByUser");
		}
		finally {
			closeConnection();
		}
	}
	
	// Update item data
	@Override
	public void updateItem(int itemId, int userId, String description) throws ToDoListException{
		if(!description.isEmpty()){
			try {
				openConnection();
				Items item = (Items) this.session.get(Items.class, itemId);
				if (userId == item.getUserId()){
					item.setDescription(description);
					commitTrans();
				}
			}
			catch(HibernateException he) {
				catchFunc(he);
				throw new ToDoListException("Exeption error: updateItem");
			}
			finally {
				closeConnection();
			}
		}		
	}
	
	// Update user data
	@Override
	public void updateUser(int userId, String password, String firstname, String lastname) throws ToDoListException{
		if(!password.isEmpty() || !firstname.isEmpty() || !lastname.isEmpty()){
			try {
				openConnection();
				Users user = (Users) this.session.get(Users.class, userId);
				if (userId == user.getId()){
					// Save the object
					user.setPassword(password);
					user.setFirstname(firstname);
					user.setLastname(lastname);
					commitTrans();
				}
			}
			catch(HibernateException he) {
				catchFunc(he);
				throw new ToDoListException("Exeption error: updateUser");
			}
			finally {
				closeConnection();
			}
		}		
	}	
	
	// Delete specific item
	@Override
	public void deleteItem(int itemId, int userId) throws ToDoListException{
		try {
			openConnection();
			session.createQuery("delete FROM Items WHERE ID = " + itemId + 
					"AND USER_ID = " + userId).executeUpdate();
			commitTrans();
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: deleteItem");
		}
		finally {
			closeConnection();
		}
	}
	
	// Delete user by id
	@Override
	public void deleteUser(int userId) throws ToDoListException{
		try {
			deleteAllUserItems(userId);
			if(!session.isOpen())
			{
				openConnection();
			}
			session.createQuery("delete FROM Users WHERE ID = " + userId).executeUpdate();
			commitTrans();
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: deleteUser");
		}
		finally {
			closeConnection();
		}
	}
	
	// Delete all users items
	@Override
	public void deleteAllUserItems(int userId) throws ToDoListException {
		try {
			openConnection();
			session.createQuery("delete FROM Items WHERE USER_ID = " + userId).executeUpdate();
			commitTrans();
		}
		catch(HibernateException he) {
			catchFunc(he);
			throw new ToDoListException("Exeption error: deleteAllUserItems");
		}
		finally {
			closeConnection();
		}
	}
	
	// Insert object to data base
	private void insertToDB(Object obj) {
		openConnection();
		this.session.save(obj);
		commitTrans();
	}
	
	// Catch function
	private void catchFunc(HibernateException he) {
		if(this.session.getTransaction()!=null) {
			this.session.getTransaction().rollback();
			he.printStackTrace();
		}
	}
	
	// Close session
	private void closeConnection() {
		try {			
			if(this.session != null){
				this.session.close();
			}
		}
		catch (HibernateException he){
			he.printStackTrace();
		}
	}
	
	// Begin a transaction
	private void openConnection(){
		this.session = this.factory.openSession();
		this.session.beginTransaction();
	}
	
	// Commit transaction
	private void commitTrans() {
		this.session.getTransaction().commit();
	}
	
	// Return new list
	@SuppressWarnings("rawtypes")
	public List retrieveList() {
		List retItems = new LinkedList();
		return retItems;
	}
	
	// Delete data table
	public void dropTableData(String table) {
		openConnection();
		session.createQuery("delete from " + table).executeUpdate();
		commitTrans();
		closeConnection();
	}
	
}
