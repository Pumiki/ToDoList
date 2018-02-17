package com.assaf.yoni.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inventory")
public class Items {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="USER_ID")
	private int userId;
	
	public Items() {
		
	}

	public Items(String description, int userId) {
		super();
		this.description = description;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Items [id=" + id + ", description=" + description + ", userId=" + userId + "]";
	}

}
