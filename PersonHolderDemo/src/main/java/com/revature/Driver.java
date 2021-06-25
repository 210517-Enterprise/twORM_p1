package com.revature;

import java.util.Scanner;

import com.revature.dao.JobDAO;
import com.revature.dao.PersonDAO;

public class Driver {

	static Scanner s = new Scanner(System.in);
	
	static boolean exited;
	
	public static void main(String[] args) {
		JobDAO j = new JobDAO();
		PersonDAO p = new PersonDAO();
		WelcomeScreen();
	}

	private static void WelcomeScreen() {
		System.out.println("--- Welcome to the Database ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search and Edit User Records");
		System.out.println("2. Search and Edit Job Records");
		System.out.println("3. Exit the Program");

		String input = s.next();
		input = input.trim();
		s.nextLine();

		if (input.equalsIgnoreCase("1")) {
			userMenu();
		} else if (input.equalsIgnoreCase("2")) {
			jobMenu();
		} else if (input.equalsIgnoreCase("3")) {
			System.out.println("Goodbye");
		} else {
			System.out.println("Invalid command");
			WelcomeScreen();
		}
	}



	private static void userMenu() {
		System.out.println("--- User Menu ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Display All User Records");
		System.out.println("2. Search by Columns");
		System.out.println("3. Return to the Welcome Screen");

		String input = s.next();
		input = input.trim();
		s.nextLine();

		if (input.equalsIgnoreCase("1")) {
			System.out.println("--- All Users ---");
			System.out.println("Username \tFirst Name\tLast Name\t Job ID \t   Age  \t Salary");
		} else if (input.equalsIgnoreCase("2")) {
			jobMenu();
		} else if (input.equalsIgnoreCase("3")) {
			WelcomeScreen();
		} else {
			System.out.println("Invalid command");
			userMenu();
		}
	}
	
	private static void editUser() {
		
	}
	
	private static void jobMenu() {
		System.out.println("--- Job Menu ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search All Job Records");
		System.out.println("2. Edit Job Records");
		System.out.println("3. Return to the Welcome Screen");

		String input = s.next();
		input = input.trim();
		s.nextLine();

		if (input.equalsIgnoreCase("1")) {
			//findUser();
		} else if (input.equalsIgnoreCase("2")) {
			jobMenu();
		} else if (input.equalsIgnoreCase("3")) {
			WelcomeScreen();
		} else {
			System.out.println("Invalid command");
			userMenu();
		}
	}
	
	public static void searchUser() {
		System.out.println("Do you want to ");
	}
	
	private static void insert() {
		// TODO
	}
	
	private static void delete() {
		// TODO
	}
	
	private static void update() {
		// TODO
	}
}
