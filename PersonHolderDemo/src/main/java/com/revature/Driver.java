package com.revature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.JobDAO;
import com.revature.dao.PersonDAO;
import com.revature.Models.*;

public class Driver {

	static Scanner scan = new Scanner(System.in);

	static JobDAO jdao = new JobDAO();

	static PersonDAO pdao = new PersonDAO();

	public static void main(String[] args) {
//		Person billy = new Person("BillyBoy", "William", "Boulevard", 3, 24, 10_000);
//        Person jilly = new Person("Jillster", "Jillian", "Applewood", 4, 45, 35_000);
//        pdao.insertPerson(billy, jilly);
		Job one = new Job("Assassin", "Killing People");
		jdao.insertJob(one);
		welcomeScreen();
	}

	private static void welcomeScreen() {
		System.out.println("--- Welcome to the Database ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search and Edit User Records");
		System.out.println("2. Search and Edit Job Records");
		System.out.println("3. Exit the Program");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		if (input.equalsIgnoreCase("1")) {
			userMenu();
		} else if (input.equalsIgnoreCase("2")) {
			jobMenu();
		} else if (input.equalsIgnoreCase("3")) {
			System.out.println("Goodbye");
			scan.close();
		} else {
			System.out.println("Invalid input");
			welcomeScreen();
		}
	}
	
	// done
	private static void userMenu() {
		System.out.println("--- User Menu ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Display All User Records");
		System.out.println("2. Select User by Username");
		System.out.println("3. Search Users by Columns");
		System.out.println("4. Insert New User Record");
		System.out.println("5. Return to the Welcome Screen");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		if (input.equalsIgnoreCase("1")) {
			List<Person> all = pdao.getAll();
			if (all.isEmpty()) {
				System.out.println("No Records Found");
				userMenu();
			}
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			for (Person per : all) {
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
			}
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Select User Record to Edit");
			System.out.println("2. Return to the User Menu");
			System.out.println("3. Return to the Welcome Screen");

			input = scan.next();
			input = input.trim();
			scan.nextLine();

			if (input.equalsIgnoreCase("1")) {
				editUser();
			} else if (input.equalsIgnoreCase("2")) {
				userMenu();
			} else if (input.equalsIgnoreCase("3")) {
				welcomeScreen();
			} else {
				System.out.println("Invalid input");
				userMenu();
			}
		} else if (input.equalsIgnoreCase("2")) {
			editUser();
		} else if (input.equalsIgnoreCase("3")) {
			searchUserColumns();
		} else if (input.equalsIgnoreCase("4")) {
			insertUser();
		} else if (input.equalsIgnoreCase("5")) {
			welcomeScreen();
		} else {
			System.out.println("Invalid input");
			userMenu();
		}
	}
	
	// done
	private static void insertUser() {
		
		Person per = new Person();
		
		System.out.println("Enter the Username:");
		String input = scan.next();
		input = input.trim();
		scan.nextLine();
		per.setId(input);
		
		System.out.println("Enter the First Name:");
		input = scan.next();
		input = input.trim();
		scan.nextLine();
		per.setFirstName(input);

		System.out.println("Enter the Last Name:");
		input = scan.next();
		input = input.trim();
		scan.nextLine();
		per.setLastName(input);
		
		try {
			System.out.println("Enter the Job ID:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			per.setJobId(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
			editUser(per);
		}
		
		try {
			System.out.println("Enter the User Age:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			per.setAge(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
			insertUser();
		}
		
		try {
			System.out.println("Enter the Salary in USD:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			per.setSalary(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
			insertUser();
		}
		
		pdao.insertPerson(per);
		editUser(per);
		
	}

	// done
	private static void searchUserColumns() {

		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search by First Name");
		System.out.println("2. Search by Last Name");
		System.out.println("3. Search by Job ID");
		System.out.println("4. Search by User Age");
		System.out.println("5. Search by Salary");
		System.out.println("6. Return to the User Menu");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		List<Person> list = new ArrayList<>();

		if (input.equalsIgnoreCase("1")) {

			System.out.println("Enter the First Name to Search For:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();

			list = pdao.getPersonByColumn("first_name", input);

			if (list.isEmpty()) {

				System.out.println("No Records Found");
				searchUserColumns();

			} else {

				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				for (Person per : list) {
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
				}
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.println();
				editUser();

			}

		} else if (input.equalsIgnoreCase("2")) {

			System.out.println("Enter the Last Name to Search For:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();

			list = pdao.getPersonByColumn("last_name", input);

			if (list.isEmpty()) {

				System.out.println("No Records Found");
				searchUserColumns();

			} else {

				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				for (Person per : list) {
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
				}
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.println();
				editUser();

			}
		} else if (input.equalsIgnoreCase("3")) {
			
			System.out.println("Enter the Job ID to Search For:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			int i = 0;
			try {
				i = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
				searchUserColumns();
			}

			list = pdao.getPersonByColumn("job_id", i);

			if (list.isEmpty()) {

				System.out.println("No Records Found");
				searchUserColumns();

			} else {

				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				for (Person per : list) {
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
				}
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.println();
				editUser();

			}
		} else if (input.equalsIgnoreCase("4")) {

			System.out.println("Enter the Age to Search For:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			int i = 0;
			try {
				i = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
				searchUserColumns();
			}

			list = pdao.getPersonByColumn("user_age", i);

			if (list.isEmpty()) {

				System.out.println("No Records Found");
				searchUserColumns();

			} else {

				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				for (Person per : list) {
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
				}
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.println();
				editUser();

			}
		} else if (input.equalsIgnoreCase("5")) {

			System.out.println("Enter the Salary to Search For:");
			input = scan.next();
			input = input.trim();
			scan.nextLine();
			int i = 0;
			try {
				i = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
				searchUserColumns();
			}

			list = pdao.getPersonByColumn("salary", i);

			if (list.isEmpty()) {

				System.out.println("No Records Found");
				searchUserColumns();

			} else {

				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				for (Person per : list) {
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
				}
				System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
				System.out.println();
				editUser();

			}
		} else if (input.equalsIgnoreCase("6")) {
			userMenu();
		} else {
			System.out.println("Invalid input");
			searchUserColumns();
		}
	}

	// done
	private static void editUser() {

		System.out.println("Enter the Username of the Record to edit or 0 to return to the User Menu");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		if (input.equals("0")) {
			userMenu();
		} else {
			Person u = pdao.getPersonPK(input);
			editUser(u);
		}
	}

	// done
	private static void editUser(Person per) {

		if (per != null) {

			System.out.println("--- Current User Record ---");
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %,-20d%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
			System.out.println("---------------------|----------------------|----------------------|----------------------|----------------------|----------------------");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Edit First Name");
			System.out.println("2. Edit Last Name");
			System.out.println("3. Edit Job ID");
			System.out.println("4. Edit User Age");
			System.out.println("5. Edit Salary");
			System.out.println("6. Return to the User Menu");

			String input = scan.next();
			input = input.trim();
			scan.nextLine();

			if (input.equalsIgnoreCase("1")) {
				System.out.println("Enter the new First Name:");
				input = scan.next();
				input = input.trim();
				scan.nextLine();
				per.setFirstName(input);
				editUser(per);
			} else if (input.equalsIgnoreCase("2")) {
				System.out.println("Enter the new Last Name:");
				input = scan.next();
				input = input.trim();
				scan.nextLine();
				per.setLastName(input);
				editUser(per);
			} else if (input.equalsIgnoreCase("3")) {
				try {
					System.out.println("Enter the new Job ID:");
					input = scan.next();
					input = input.trim();
					scan.nextLine();
					per.setJobId(Integer.parseInt(input));
					editUser(per);
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					editUser(per);
				}
			} else if (input.equalsIgnoreCase("4")) {
				try {
					System.out.println("Enter the new User Age:");
					input = scan.next();
					input = input.trim();
					scan.nextLine();
					per.setAge(Integer.parseInt(input));
					editUser(per);
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					editUser(per);
				}
			} else if (input.equalsIgnoreCase("5")) {
				try {
					System.out.println("Enter the new Salary in USD:");
					input = scan.next();
					input = input.trim();
					scan.nextLine();
					per.setSalary(Integer.parseInt(input));
					editUser(per);
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					editUser(per);
				}
			} else if (input.equalsIgnoreCase("6")) {
				pdao.updatePerson(per);
				userMenu();
			} else {
				System.out.println("Invalid input");
				editUser(per);
			}
		} else {
			System.out.println("User not found");
			editUser();
		}
	}

	private static void jobMenu() {
		System.out.println("--- Job Menu ---");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Display All Job Records");
		System.out.println("2. Select Job by ID#");
		System.out.println("3. Search Jobs by Columns");
		System.out.println("4. Insert New Job Record");
		System.out.println("5. Return to the Welcome Screen");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		if (input.equalsIgnoreCase("1")) {
			List<Job> all = jdao.getAll();
			if (all.isEmpty()) {
				System.out.println("No Records Found");
				jobMenu();
			}
			System.out.println("---------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s", "Job ID", "Name", "Description");
			System.out.println("---------------------|----------------------|----------------------");
			for (Job job : all) {
				System.out.printf("%-20s | %-20s | %-20s", job.getId(), job.getJobName(), job.getJobDescription());
			}
			System.out.println("---------------------|----------------------|----------------------");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Select Job Record to Edit");
			System.out.println("2. Return to the Job Menu");
			System.out.println("3. Return to the Welcome Screen");

			input = scan.next();
			input = input.trim();
			scan.nextLine();

			if (input.equalsIgnoreCase("1")) {
				editJob();
			} else if (input.equalsIgnoreCase("2")) {
				jobMenu();
			} else if (input.equalsIgnoreCase("3")) {
				welcomeScreen();
			} else {
				System.out.println("Invalid input");
				jobMenu();
			}
		} else if (input.equalsIgnoreCase("2")) {
			selectJob();
		} else if (input.equalsIgnoreCase("3")) {
			searchJobColumns();
		} else if (input.equalsIgnoreCase("4")) {
			insertJob();
		} else if (input.equalsIgnoreCase("5")) {
			welcomeScreen();
		} else {
			System.out.println("Invalid input");
			jobMenu();
		}
	}
	
	private static void selectJob() {
		// TODO Auto-generated method stub

	}
	private static void searchJobColumns() {
		// TODO Auto-generated method stub
		
	}
	
	private static void insertJob() {
		// TODO Auto-generated method stub
		
	}
	// done
	private static void editJob() {
		
		System.out.println("Enter the Username of the Record to edit or 0 to return to the User Menu");

		String input = scan.next();
		input = input.trim();
		scan.nextLine();

		if (input.equals("0")) {
			jobMenu();
		} else {
			try {
				Job u = jdao.getJobPK(Integer.parseInt(input));
				editJob(u);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
				editJob();
			}
		}
		
	}
	
	// done
	private static void editJob(Job job) {

		if (job != null) {

			System.out.println("--- Current Job Record ---");
			System.out.println("---------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s", "Job ID", "Name", "Description");
			System.out.println("---------------------|----------------------|----------------------");
			System.out.printf("%-20s | %-20s | %-20s", job.getId(), job.getJobName(), job.getJobDescription());
			System.out.println("---------------------|----------------------|----------------------");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Edit Job Name");
			System.out.println("2. Edit Job Description");
			System.out.println("3. Return to the Job Menu");

			String input = scan.next();
			input = input.trim();
			scan.nextLine();

			if (input.equalsIgnoreCase("1")) {
				System.out.println("Enter the new Name:");
				input = scan.nextLine();
				input = input.trim();
				scan.nextLine();
				job.setJobName(input);
				editJob(job);
			} else if (input.equalsIgnoreCase("2")) {
				System.out.println("Enter the new Description:");
				input = scan.nextLine();
				input = input.trim();
				scan.nextLine();
				job.setJobDescription(input);
				editJob(job);
			} else if (input.equalsIgnoreCase("3")) {
				jdao.updateJob(job);
				jobMenu();
			} else {
				System.out.println("Invalid input");
				editJob(job);
			}
		} else {
			System.out.println("Job not found");
			editJob();
		}
	}

}
