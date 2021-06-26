package com.revature.service;

import java.util.Scanner;

public class inputService {

	private static Scanner scanner = new Scanner(System.in);

	public static int getInt(int max) {
		int inputValue;

		// Confirm user input is int type
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("         ERROR: Please enter a number.");
				scanner.nextLine();
			}

			// Retrieve user input
			inputValue = scanner.nextInt();
			scanner.nextLine();

			// Confirm user input is within the range of 0 to max
			if (inputValue <= 0 || inputValue > max) {
				System.out.println("         ERROR: Please enter a number between 1 and " + max);
			}

		} while (inputValue < 0 || inputValue > max);

		// Return user input
		return inputValue;
	}
	
	public static String getString(int max) {
		String input;

		while (true) {
			input = scanner.nextLine();

			input = input.trim();
			if (input.length() == 0) {
				System.out.println("         String has no content. Try again.");
				continue;
			}

			if (input.length() > max) {
				System.out.println("         Enter string less than" + max);
				continue;
			}

			return input;
		}
	}
}
