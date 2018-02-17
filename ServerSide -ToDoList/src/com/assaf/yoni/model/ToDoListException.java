package com.assaf.yoni.model;

@SuppressWarnings("serial")
public class ToDoListException extends Exception{
	public ToDoListException(String throwMessage) {
		super(throwMessage);
	}
}
