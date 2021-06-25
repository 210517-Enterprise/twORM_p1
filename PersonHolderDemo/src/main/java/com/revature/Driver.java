package com.revature;

import java.util.Scanner;

public class Driver {

	static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean exited = false;
		
		System.out.println("Welcome to the PHd");
		
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
			} else {
				System.out.println("Invalid command");
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
