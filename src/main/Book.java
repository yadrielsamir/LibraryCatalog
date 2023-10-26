
package main;
import java.time.LocalDate;
public class Book {
	/**
	 * Declaring variables used for Book class
	 */
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	/** 
	 * This method is the Book class constructor. It initializes id, title, author, genre, lastCheckOut and checkedOut.
	 * @param id
	 * @param title
	 * @param author
	 * @param genre
	 * @param lastCheckOut
	 * @param checkOut
	 */
	public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkOut){
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkOut;
	}
	
	/** 
	 * id getter
	 * @return int id - book id
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
	 * title getter
	 * @return string title - book title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/** 
	 * title setter
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * author getter
	 * @return string author - book author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/** 
	 * author setter
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/** 
	 * genre getter
	 * @return string genre - book genre 
	 */
	public String getGenre() {
		return this.genre;
	}
	
	/** 
	 * genre setter
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/** 
	 * LastCheckOut getter
	 * @return LocalDate lastCheckOut - book LastCheckOut 
	 */
	public LocalDate getLastCheckOut() {
		return this.lastCheckOut;
	}
	
	/** 
	 * LastCheckOut setter
	 * @param lastCheckOut
	 */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	/** 
	 * isCheckedOut getter
	 * @return bool isCheckedOut - book isCheckedOut
	 */
	public boolean isCheckedOut() {
		return this.checkedOut;
	}
	
	/** 
	 * isCheckedOut setter
	 * @param checkedOut
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	

	@Override
	/**
	 * This method returns the target title and author in "{TITLE} By {AUTHOR}" format in upper case. It is done by using
	 * toUpperCase(), which changes strings to upper case. 
	 * @return title and author in "{TITLE} By {AUTHOR}" format in upper case. 
	 */
	public String toString() {
		return this.getTitle().toUpperCase() + " BY " + this.getAuthor().toUpperCase();
	}
	
	/**
	 * This method returns the amount of money owed depending on how much time has passed. First, it is checked if target book 
	 * hasn't been checked out and if checkedOut boolean is true. If it passes both, we calculate the date it was supposed to be
	 * given back. Then, the days they exceed are calculated by finding the difference between today date (2023, 9, 15) and the due date. 
	 * If the difference is greater or equal to zero, we calculate the fee and return it. If not, we return zero. 
	 * @return 10f + 1.5f * daysLate / 0f - If the book exceeded the time limit, then the calculated fee is returned, if not, then zero is returned.
	 */
	public float calculateFees() {
		//Check if book was borrowed in the first place and if it was then checked out
		if(this.getLastCheckOut() != null && this.isCheckedOut()) {
			//Variables that store the current date and the date that the book was supposed to be checked out
			LocalDate todaysDate = LocalDate.of(2023, 9, 15);
			LocalDate dueDate = this.getLastCheckOut().plusDays(31);
			
			//Variable that calculates how many days have passed since the dueDate
			long daysLate = todaysDate.toEpochDay() - dueDate.toEpochDay();
			
			//Checking if days passed after due date
			if(daysLate >= 0) {
				//Calculating fee
	            return 10f + 1.5f * daysLate;
	        }
		}
		return 0f;
	}    
}
