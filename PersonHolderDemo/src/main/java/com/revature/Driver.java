package com.revature;

import java.util.Scanner;

public class Driver {

	static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean exited = false;
		
		System.out.println("Welcome to the PHd");
		System.out.println("Enter find, insert, update, delete, or exit");
		// MAIN LOOP
		while(!exited) {
			
			String input = s.next();
			s.nextLine();
			
			if(input.equalsIgnoreCase("find")) {
				find();
			} else if(input.equalsIgnoreCase("insert")) {
				insert();
			} else if(input.equalsIgnoreCase("delete")) {
				delete();
			}  else if(input.equalsIgnoreCase("update")) { 
				update();
			} else if(input.equalsIgnoreCase("exit")) {
				exited = true;
				System.out.println("Goodbye");
			} else {
				System.out.println("Invalid command");
				System.out.println("Enter find, insert, delete, update, or exit");
			}
		}
		// END OF LOOP
	}

	public static void find() {
		// TODO
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
