package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;
import main.Book;
import main.User;

public class LibraryCatalog {
	
	/**
	 * private variables catalog and users
	 */
	private List<Book> catalog;
	private List<User> users;
	
	/**
	 * This method is the LibraryCatalog class constructor. It initializes catalog and users.
	 * @throws IOException
	 */
	public LibraryCatalog() throws IOException{
		catalog = getBooksFromFiles();
        users = getUsersFromFiles();
	}

	/**
	 * This method looks for books by their id's. It starts by creating a new empty list of books.
	 * Then, it removes the given string curly brackets and it divides it by spaces, to then store the parts in an array.
	 * Each element of the array is a book ID. We iterate over the array and change the element from string to integer. Inside the
	 * same loop, we iterate over every book in catalog and if its ID is equal to the current element, then it is added to the
	 * checkedOutList list. After iterating, checkedOutList is returned. 
	 * @see Book.java
	 * @param bookList
	 * @return  List<Book> checkedOutList - converted books from bookList
	 * @throws IOException
	 */
	private List<Book> fromStringToList(String bookList) throws IOException{
		List<Book> checkedOutList = new ArrayList<>();
	    String[] bookIds = bookList.replace("{", "").replace("}", "").split(" "); 
	    
	    for (String bookId : bookIds) {
	        int bookIdInt = Integer.parseInt(bookId.trim());
	        for (Book book : this.getBooksFromFiles()) {
	            if (book.getId() == bookIdInt) {
	                checkedOutList.add(book);
	                break;
	            }
	        }
	    }
	    return checkedOutList;
	}
	
	/**
	 * This method goes through every line in catalog.csv. It starts by creating a new empty list of books.
	 * Then, it reads the first line of the catalog file and jumps to the next one since it doesn't contain useful information.
	 * We iterate over each line on the file, split it by its commas and store each part in an array. 
	 * After checking that there are six parts as supposed, each part is stored in individual variables with id, lastCheckOut and 
	 * checkOut converted from string to their respective types. With these variables, a new book is created and stored in the 
	 * fileBooks list, which is returned after iteration is done.
	 * @see User.java
	 * @see Book.java
	 * @return List<Book> fileBooks - books in catalog file
	 * @throws IOException
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		List<Book> fileBooks = new ArrayList<>();
        String filePath = "data/catalog.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                	int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    LocalDate lastCheckOut = LocalDate.parse(parts[4]);
                    boolean checkOut = Boolean.parseBoolean(parts[5]);
                    Book book = new Book(id, title, author, genre, lastCheckOut, checkOut);
                    fileBooks.add(book);
                }
            }
        }
	    return fileBooks;
	}
	
	/**
	 * This method goes through every line in user.csv. It starts by creating a new empty list of users.
	 * Then, it reads the first line of the user file and jumps to the next one since it doesn't contain useful information.
	 * We iterate over each line on the file, split it by its commas and store each part in an array. 
	 * Each part is then stored in individual variables with id converted from string to integer. We check if the array
	 * with parts has three elements in it since there are some users that have no checkedOutList. If it does, we use fromStringToList() to convert
	 * the id's in the string to a list of books and that will be the checkedOutList variable, if not, checkedOutList stays as an empty list.
	 * With these variables, a new user is created and stored in the fileUsers list, which is returned after iteration is done.
	 * @see User.java
	 * @see Book.java
	 * @return List<User> fileUsers - users in user file
	 * @throws IOException
	 */
	private List<User> getUsersFromFiles() throws IOException {
		List<User> fileUsers = new ArrayList<>();
        String filePath = "data/user.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
            	int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                List<Book> checkedOutList = new ArrayList<>();
                if (parts.length == 3) {
                	checkedOutList = fromStringToList(parts[2]);
                }
                fileUsers.add(new User(id, name, checkedOutList));
                
            }
        }
		return fileUsers;
	}
	
	/**
	 * This method returns the books in the catalog.csv file
	 * @return List<Book> catalog - list of books in the catalog.csv file
	 */
	public List<Book> getBookCatalog() {
		return this.catalog;
	}
	
	/**
	 * This method returns the users in the user.csv file
	 * @return List<User> users - list of users in the user.csv file
	 */
	public List<User> getUsers() {
		return this.users;
	}
	
	/**
	 * This methods adds a book with the values passed through the parameters. It starts by creating the books
	 * new ID by adding one to the last books ID. Then, the new book is created with the given values,
	 * "2023, 9, 15" date, checkedOut boolean as false and the new ID. The new book is finally added to 
	 * the catalog list.
	 * @see Book.java
	 * @param title
	 * @param author
	 * @param genre
	 */
	public void addBook(String title, String author, String genre) {
		int newId = getBookCatalog().last().getId() + 1; 
	    Book newBook = new Book(newId, title, author, genre, LocalDate.of(2023, 9, 15), false);
	    getBookCatalog().add(newBook);
	}
	
	/**
	 * This methods removes the book with the given id. It starts by iterating over each book 
	 * in the book library. If the book has the same ID as the one passed, the book is removed from the list.
	 * @see Book.java
	 * @param id
	 */
	public void removeBook(int id) {
		for(Book book: getBookCatalog()) {
			if(book.getId() == id) {
				getBookCatalog().remove(book); 
			}
		}
	}	
	
	/**
	 * This method checks out a book that hasn't been checked out. It starts by iterating over each book 
	 * in the book library. If the book has the same ID as the ID passed, we check if the book is checked out.
	 * If it isn't, it is checked out by turning the checkedOut boolean to true and changing LastCheckOut to "2023, 9, 15". 
	 * After checking it out, true is returned. If it was already checked out, false is returned. 
	 * @param id
	 * @return true / false - returns true if book wasn't checked out, if it was it returns false. 
	 */
	public boolean checkOutBook(int id) {
		for(Book book: getBookCatalog()) {
			if(book.getId() == id) {
				if(!book.isCheckedOut()) {
					book.setCheckedOut(true);
					book.setLastCheckOut(LocalDate.of(2023, 9, 15));
					return true;
				}
			}
		}
		return false; 
	}
	
	/**
	 * This method returns a book if it isn’t already returned. It starts by iterating over each book 
	 * in the catalog list. If the book has the same ID as the ID passed, we check if the book is checked out.
	 * If it is, its checkedOut boolean is changed to false and true is returned. If not, false is returned. 
	 * @param id
	 * @return  true / false - returns true if book wasn't returned, if it was it returns false. 
	 */
	public boolean returnBook(int id) {
		for(Book book: getBookCatalog()) {
			if(book.getId() == id) {
				if(book.isCheckedOut()) {
					book.setCheckedOut(false);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method returns whether the book of the given id is available for check-out. It starts by iterating over each book 
	 * in the book library. If the book has the same ID as the ID passed, we check if the book is checked out. 
	 * If it is, false is returned. If not, true is returned. 
	 * 
	 * @param id
	 * @return  true / false - returns true if book was available for check out, if it wasn't it returns false. 
	 */
	public boolean getBookAvailability(int id) {
		for(Book book: getBookCatalog()) {
			if(book.getId() == id) {
				if(book.isCheckedOut()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * This method counts the amount of books with the given title. It starts by creating the count variable. It then iterates 
	 * over the books book library and if the book's title is the same as the given title, one is added to count. After iterating, 
	 * count is returned. 
	 * @param title
	 * @return int count - number of books with given title
	 */
	public int bookCount(String title) {
		int count = 0;
		for(Book book: getBookCatalog()) {
			if(book.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This method counts the amount of books with the given genre. It first create a filter that fits the corresponding genre.
	 * Then, searchForBook() lambda function is used with the filter created to make a list of the books with the given genre.
	 * The size of the list is returned. 
	 * @param genre
	 * @return searchForBook(categoryFilter).size() - size of the list with the books that have the given genre
	 */
	public int genreCount(String genre) {
		FilterFunction<Book> categoryFilter = book -> book.getGenre().equalsIgnoreCase(genre);
		return searchForBook(categoryFilter).size();
	}
	
	/**
	 * This method creates the final report that is expected from the project. The report is stored in a string variable called output.
	 * 
	 * First, we add the amount of books per category. An array called genre is created and we store each genre string. Then, for each 
	 * genre, we add the genre and the amount by calling genreCount(genres[i]), "i" being the index of the specified genre. Finally, the 
	 * total amount of books is added.
	 * 
	 * Second, we add the books that are checked out. An integer variable is created to store the total amount of books, called checkedCount.
	 * Then, we iterate over each book in book library and check it is checked out. If it is, we add the book with toString() format to the output
	 * and one is added to checkedCount. Finally checkedCount is added.  
	 * 
	 * Third, we add the users that owe money. An integer variable is created to store the total of money owed called totalDue. Then, we iterate over
	 * the user library and another variable is created to store each persons fee. We iterate over the users checked out list and add the fees they owe 
	 * in the fee variable. After calculating fees, the final number is added to totalDue. If the fee is not zero, then we add the fee to the output 
	 * with the users name in the proper format, with fee having two decimal places. Finally totalDue is added. 
	 * 
	 * Finally, we create the file with the final report. Each line from output is written into the file created with FileWriter by using the
	 * BufferedWriter which lets us use writer.write() to do so. Try and catch are added to handle any potential IOException that might occur while
	 * writing the file. 
	 * @throws IOException
	 */
	public void generateReport() throws IOException {
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		
	    String genres[] = {"Adventure", "Fiction", "Classics", "Mystery", "Science Fiction"};
	    
		output += "Adventure\t\t\t\t\t" + (genreCount(genres[0])) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (genreCount(genres[1])) + "\n";
		output += "Classics\t\t\t\t\t" + (genreCount(genres[2])) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (genreCount(genres[3])) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (genreCount(genres[4])) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (genreCount(genres[0]) + genreCount(genres[1]) + genreCount(genres[2]) + genreCount(genres[3]) + genreCount(genres[4])) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
		int checkedCount = 0; 
		for(Book book : getBookCatalog()) {
			if(book.isCheckedOut()) {
				output += book.toString() + "\n";
				checkedCount++;
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (checkedCount) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		
		float totalDue = 0; 
		for(User user: this.getUsers()) {
			float fee = 0; 
			for(Book book : user.getCheckedOutList()) {
				fee += book.calculateFees();
			}
			totalDue += fee; 
			if(fee != 0) {
				String decimalFee = String.format("%.2f", fee);
			    output += user.getName() + "\t\t\t\t\t$" + decimalFee + "\n";
			}
			
		}
		
		String decimalTotal = String.format("%.2f", totalDue);

		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (decimalTotal) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("report/report.txt"))) {
		    writer.write(output);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	
	/**
	 * This method creates a list of books that contain the books that pass the filter. First, a new 
	 * Book list is created. Then, we iterate over the books in the book library. If the book passes the 
	 * given filter, then it is added to the new list. After iterating, the new list is returned. 
	 * @return List<Book> bookList - list of books that pass the filter
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		List<Book> bookList = new ArrayList<>();

	    for (Book book : getBookCatalog()) {
	        if (func.filter(book)) {
	        	bookList.add(book);
	        }
	    }
	    return bookList;
	}
		
	/**
	 * This method creates a list of Users that contain the users that pass the filter. First, a new 
	 * User list is created. Then, we iterate over the users in the user library. If the user passes the 
	 * given filter, then it is added to the new list. After iterating, the new list is returned. 
	 * @param func
	 * @return List<User> bookList - list of users that pass the filter
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		List<User> userList = new ArrayList<>();
		
		for (User user : this.getUsers()) {
	        if (func.filter(user)) {
	        	userList.add(user);
	        }
		}
		return userList;
	}
}
