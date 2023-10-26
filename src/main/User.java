package main;

import data_structures.ArrayList;
import interfaces.List;

public class User {
	
	/**
	 * Declaring variables used for User class
	 */
	private int id;
	private String name;
	private List<Book> checkedOutList; 
	
	/**
	 * This method is the User class constructor. It initializes id, name and checkedOutList. 
	 * @param id
	 * @param name
	 * @param checkedOutList
	 */
	public User(int id, String name, List<Book> checkedOutList){
		this.id = id; 
		this.name = name;
		this.checkedOutList = checkedOutList; 
	}
	
	/**
	 * id getter
	 * @return int id - user id 
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * id setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * name getter
	 * @return string name - user name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name; 
	}
	
	/**
	 * CheckedOutList getter 
	 * @return List<Book> checkedOutList - user checkedOutList 
	 */
	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}

	/**
	 * checkedOutList setter
	 * @param checkedOutList
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
