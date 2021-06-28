package com.revature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.revature.Models.Job;
import com.revature.Models.Person;
import com.revature.dao.JobDAO;
import com.revature.dao.PersonDAO;
import com.revature.service.inputService;

public class Driver {
	
	static Scanner scan = new Scanner(System.in);
	static JobDAO jdao = new JobDAO();
	static PersonDAO pdao = new PersonDAO();
	
	public static void main(String[] args) {
		Job j1 = new Job("Cooker", "Makes the Drugs");
		Job j2 = new Job("Dealer", "Sells the Drugs");
		Job j3 = new Job("Launderer", "Cleans the Money");
		Job j4 = new Job("Lawyer", "Cleans the Messes");
		
		Job[] initJobs = {j1, j2, j3, j4};
		
		Person p1 = new Person("TheChemist", "Walter", "White", 1, 50, 37000);
		Person p2 = new Person("Chicken", "Gus", "Fring", 2, 49, 65400);
		Person p3 = new Person("Wifey", "Skylar", "White", 3, 45, 32000);
		Person p4 = new Person("Scumbag", "Saul", "Goodman", 4, 49, 55500);
		Person p5 = new Person("Vegan", "Gale", "Boetticher", 1, 31, 25000);
		Person p6 = new Person("Baddabing", "Vinnie", "Baggadonuts", 2, 45, 25000);
		Person p7 = new Person("Jimmy", "James", "McGill", 4, 49, 55500);
		Person p8 = new Person("Bruce", "Jesse", "Pinkman", 2, 27, 25000);
		
		Person[] initPersons = {p1, p2, p3, p4, p5, p6, p7, p8};
		
		jdao.insertJob(initJobs);
		pdao.insertPerson(initPersons);
		
		welcomeScreen();
		
	}
	
	
	private static void welcomeScreen() {
		System.out.println("|-------------------------------|");
		System.out.println("|    Welcome to the Gus\'s DB    |");
		System.out.println("|-------------------------------|");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search and Edit User Records");
		System.out.println("2. Search and Edit Job Records");
		System.out.println("3. Exit the Program");

		int input = inputService.getInt(3);
		
		switch (input) {
			case 1:
				userMenu();
				break;
			case 2:
				jobMenu();
				break;
			case 3:
				System.out.println("Goodbye!");
				scan.close();
				System.exit(0);
			default:
				System.out.println("Invalid input detected");
				welcomeScreen();
		}
			
	}
	
	
	private static void userMenu() {
		System.out.println("|-------------------------------|");
		System.out.println("|           User Menu           |");
		System.out.println("|-------------------------------|");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Display All User Records");
		System.out.println("2. Select User by Username");
		System.out.println("3. Search Users by Column");
		System.out.println("4. Search Users by Multiple Columns");
		System.out.println("5. Insert New User Record");
		System.out.println("6. Delete User Record");
		System.out.println("7. Return to the Welcome Screen");
		
		int input = inputService.getInt(7);
		
		switch (input) {
		case 1:
			displayAllUsers();
			break;
		case 2:
			searchUserName();
			break;
		case 3:
			searchUserColumn();
			break;
		case 4:
			searchUserColumns();
			break;
		case 5:
			insertUser();
			break;
		case 6:
			deleteUser();
			break;
		case 7: 
			welcomeScreen();
		}
	}
	
	
	private static void recordMenu() {
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Select User Record(s) to Edit");
		System.out.println("2. Return to the User Menu");
		System.out.println("3. Return to the Welcome Screen");

		int input = inputService.getInt(3);
		
		switch (input) {
		case 1:
			editUser();
			break;
		case 2:
			userMenu();
		case 3:
			welcomeScreen();
		}
	}
	
	
	private static void jobMenu() {
		System.out.println("|-------------------------------|");
		System.out.println("|           Job  Menu           |");
		System.out.println("|-------------------------------|");
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Display All Job Records");
		System.out.println("2. Select Job by ID#");
		System.out.println("3. Search Jobs by Column");
		System.out.println("4. Insert New Job Record");
		System.out.println("5. Delete a Job Record");
		System.out.println("6. Return to the Welcome Screen");
		
		int input = inputService.getInt(6);
		
		switch (input) {
		case 1:
			displayAllJobs();
			break;
		case 2:
			searchJobId();
			break;
		case 3:
			searchJobColumn();
			break;
		case 4:
			insertJob();
			break;
		case 5:
			deleteJob();
			break;
		case 6:
			welcomeScreen();
		}
	}
	
	private static void jobRecordMenu() {
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Select Job Record to Edit");
		System.out.println("2. Return to the Job Menu");
		System.out.println("3. Return to the Welcome Screen");
		
		int input = inputService.getInt(3);
		
		switch (input) {
			case 1:
				editJob();
				break;
			case 2:
				jobMenu();
				break;
			case 3:
				welcomeScreen();
		}
	}
	
	
	
	
	private static void displayAllUsers() {
		List<Person> all = pdao.getAll();
		if (all == null) {
			System.out.println("No Records Found");
			userMenu();
		}
		System.out.println("|-- All User Records--|");
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21s| %-21s|%n", "Username", "First Name", "Last Name", "Job ", "Job Description", "User Age", "Salary in USD");
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		for (Person per : all) {
			Job j = jdao.getJobPK(per.getJobId());
			System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21d| %,-21d|%n", per.getId(), per.getFirstName(), per.getLastName(), j.getJobName(), j.getJobDescription(), per.getAge(), per.getSalary());
		}
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		System.out.println();
		
		recordMenu();
	}
	
	private static void displayAllJobs() {
		List<Job> jobs = jdao.getAll();
		
		if (jobs == null) {
			System.out.println("No records found.");
			jobMenu();
		}
		System.out.println("|-- All Job Records --|");
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s|%n", "Job ID", "Name", "Description");
		System.out.println("|---------------------|----------------------|----------------------|");
		for (Job job : jobs) {
			System.out.printf("| %-20s| %-21s| %-21s|%n", job.getId(), job.getJobName(), job.getJobDescription());
		}
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.println();
		
		jobRecordMenu();
	}
	
	
	private static void searchUserName() {
		System.out.println();
		System.out.println("|--- Search by Username ---|");
		System.out.println("Enter the username to search for: ");
		String input = inputService.getString(50);
		
		Person per = pdao.getPersonPK(input);
		if (per == null) {
			System.out.println("Sorry, no record found.");
			userMenu();
		}
		Job j = jdao.getJobPK(per.getJobId());
		
		System.out.println("|---  User Record  ---|");
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21s| %-21s|%n", "Username", "First Name", "Last Name", "Job ", "Job Description", "User Age", "Salary in USD");
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21d| %,-21d|%n", per.getId(), per.getFirstName(), per.getLastName(), j.getJobName(), j.getJobDescription(), per.getAge(), per.getSalary());
		System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
		System.out.println();
		
		System.out.println("Enter the number of your selection: ");
		System.out.println("1. Edit this record");
		System.out.println("2. Return to user menu");
		System.out.println("3. Return to main menu");
		
		int input2 = inputService.getInt(3);
		switch (input2) {
			case 1:
				pdao.updatePerson(editUser(per));
				userMenu();
				break;
			case 2:
				userMenu();
				break;
			case 3:
				welcomeScreen();
		}
	}
	
	private static void searchJobId() {
		System.out.println();
		System.out.println("|--- Search by Job ID ---|");
		System.out.println("Enter the id to search for: ");
		int input = inputService.getInt(5);
		
		Job job = jdao.getJobPK(input);
		if (job == null) {
			System.out.println("Sorry, no record found.");
			jobMenu();
		}
		
		System.out.println("|---  Job  Record  ---|");
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s|%n", "Job ID", "Name", "Description");
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s|%n", job.getId(), job.getJobName(), job.getJobDescription());
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.println();
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Select Job Record to Edit");
		System.out.println("2. Return to the Job Menu");
		System.out.println("3. Return to the Welcome Screen");
		
		int input2 = inputService.getInt(3);
		switch (input2) {
		case 1:
			jdao.updateJob(editJob(job));
			jobMenu();
			break;
		case 2:
			jobMenu();
			break;
		case 3:
			welcomeScreen();
		}
		
	}
	
	
	private static void editUser() {
		List<Person> toUpdate = new ArrayList<Person>();
		
		while(true) {
			System.out.println("Enter the username of the user you want to edit. If you do not wish to edit more users, enter done.");
			String input = inputService.getString(50);
			
			if (input.equalsIgnoreCase("done")) {
				pdao.updatePerson(toUpdate.toArray(new Person[0]));
				break;
			} else {
				Person per = pdao.getPersonPK(input);
				if (per != null) {
					toUpdate.add(editUser(per));
				} else {
					System.out.println("Sorry, no user could be found with that username.");
				}
			}
		}
		userMenu();
	}
	
	private static void editJob() {
		List<Job> toUpdate = new ArrayList<Job>();
		
		while(true) {
			System.out.println("Enter the job id of the job you want to edit. If you do not want to edit more jobs, enter 0.");
			int input = inputService.getInt(5);
			
			if (input == 0) {
				jdao.updateJob(toUpdate.toArray(new Job[0]));
				break;
			} else {
				Job job = jdao.getJobPK(input);
				if (job != null) {
					toUpdate.add(editJob(job));
				} else {
					System.out.println("Sorry, no job could be found with that id.");
				}
			}
		}
		jobMenu();
	}
	
	private static Person editUser(Person per) {
		
		while (true) {
			System.out.println("|--- Current  User ---|");
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21s|%n", "Username", "First Name", "Last Name", "Job ID", "User Age", "Salary in USD");
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21d| %,-21d|%n", per.getId(), per.getFirstName(), per.getLastName(), per.getJobId(), per.getAge(), per.getSalary());
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Edit First Name");
			System.out.println("2. Edit Last Name");
			System.out.println("3. Edit Job ID");
			System.out.println("4. Edit User Age");
			System.out.println("5. Edit Salary");
			System.out.println("6. Finished");
			
			int input = inputService.getInt(6);
			
			switch (input) {
			case 1:
				System.out.println("Enter the new First Name: ");
				String fname = inputService.getString(50);
				per.setFirstName(fname);
				break;
			case 2:
				System.out.println("Enter the new Last Name: ");
				String lname = inputService.getString(50);
				per.setLastName(lname);
				break;
			case 3:
				System.out.println("Enter the new Job ID: ");
				int nj = inputService.getInt(5);
				per.setJobId(nj);
				break;
			case 4:
				System.out.println("Enter the new Age: ");
				int age = inputService.getInt(125);
				per.setAge(age);
				break;
			case 5:
				System.out.println("Enter the new Salary: ");
				int sal = inputService.getInt(Integer.MAX_VALUE);
				per.setSalary(sal);
				break;
			case 6:
				return per;
			default:
				return per;
			}
		}
	}
	
	private static Job editJob(Job job) {
		while(true) {
			System.out.println("|---  Current Job  ---|");
			System.out.println("|---------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s|%n", "Job ID", "Name", "Description");
			System.out.println("|---------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s|%n", job.getId(), job.getJobName(), job.getJobDescription());
			System.out.println("|---------------------|----------------------|----------------------|");
			System.out.println();
			System.out.println("Enter the number of your selection:");
			System.out.println("1. Edit Job Name");
			System.out.println("2. Edit Job Description");
			System.out.println("3. Finished");
			
			int input = inputService.getInt(4);
			
			switch (input) {
			case 1:
				System.out.println("Enter new job name: ");
				job.setJobName(inputService.getString(50));
				break;
			case 2:
				System.out.println("Enter new job description: ");
				job.setJobDescription(inputService.getString(50));
				break;
			case 3:
				return job;
			default:
				return job;
			}
		}
	}
	
	private static void searchUserColumn() {
		List<Person> persons = new ArrayList<>();
		String column = "";
		Object value = new Object();
		
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search by First Name");
		System.out.println("2. Search by Last Name");
		System.out.println("3. Search by Job ID");
		System.out.println("4. Search by User Age");
		System.out.println("5. Search by Salary");
		System.out.println("6. Return to the User Menu");
		
		int input = inputService.getInt(6);
		
		switch(input) {
		case 1:
			column += "first name";
			System.out.println("Enter the " + column + " to search for: ");
			value = inputService.getString(50);
			persons = pdao.getPersonByColumn("first_name", value);
			break;
		case 2:
			column += "last name";
			System.out.println("Enter the " + column + " to search for: ");
			value = inputService.getString(50);
			persons = pdao.getPersonByColumn("last_name", value);
			break;
		case 3:
			column += "job id";
			System.out.println("Enter the " + column + " to search for: ");
			value = inputService.getInt(5);
			persons = pdao.getPersonByColumn("job_id", value);
			break;
		case 4:
			column += "age";
			System.out.println("Enter the " + column + " to search for: ");
			value = inputService.getInt(125);
			persons = pdao.getPersonByColumn("user_age", value);
			break;
		case 5:
			column += "salary";
			System.out.println("Enter the " + column + " to search for: ");
			value = inputService.getInt(Integer.MAX_VALUE);
			persons = pdao.getPersonByColumn("salary", value);
			break;
		case 6:
			userMenu();
			break;
		default:
			userMenu();
		}
		
		if (persons == null) {
			System.out.println("Sorry no records were found.");
			userMenu();
		} else {
			System.out.println("|--- Records Where the " + column + " is " + value + " ---|");
			
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21s| %-21s|%n", "Username", "First Name", "Last Name", "Job ", "Job Description", "User Age", "Salary in USD");
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			for (Person per : persons) {
				Job j = jdao.getJobPK(per.getJobId());
				System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21d| %,-21d|%n", per.getId(), per.getFirstName(), per.getLastName(), j.getJobName(), j.getJobDescription(), per.getAge(), per.getSalary());
			}
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.println();
			
			recordMenu();
		}
	}
	
	private static void searchUserColumns() {
		List<Person> persons = new ArrayList<>();
		HashMap<String, Object> columns = new HashMap<>();
		
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search by First Name");
		System.out.println("2. Search by Last Name");
		System.out.println("3. Search by Job ID");
		System.out.println("4. Search by User Age");
		System.out.println("5. Search by Salary");
		System.out.println("6. Return to the User Menu");
		
		int input = inputService.getInt(6);
		
		switch (input) {
		case 1:
			System.out.println("Enter the first name to search for:");
			String fname = inputService.getString(50);
			columns.put("first_name", fname);
			break;
		case 2:
			System.out.println("Enter the last name to search for:");
			String lname = inputService.getString(50);
			columns.put("last_name", lname);
			break;
		case 3:
			System.out.println("Enter the job ID to search for:");
			int id = inputService.getInt(5);
			columns.put("job_id", id);
			break;
		case 4:
			System.out.println("Enter the age to search for:");
			int age = inputService.getInt(125);
			columns.put("user_age", age);
			break;
		case 5:
			System.out.println("Enter the salary to search for:");
			int salary = inputService.getInt(Integer.MAX_VALUE);
			columns.put("salary", salary);
			break;
		case 6:
			userMenu();
		default:
			userMenu();
		}
		
		boolean more = true;
		while(more) {
			System.out.println("Add an additional column to search: ");
			System.out.println("1. Search by First Name");
			System.out.println("2. Search by Last Name");
			System.out.println("3. Search by Job ID");
			System.out.println("4. Search by User Age");
			System.out.println("5. Search by Salary");
			System.out.println("6. Finished. Run Search");
			
			int input2 = inputService.getInt(6);
			
			switch (input2) {
			case 1:
				System.out.println("Enter the first name to search for:");
				String fname = inputService.getString(50);
				columns.put("first_name", fname);
				break;
			case 2:
				System.out.println("Enter the last name to search for:");
				String lname = inputService.getString(50);
				columns.put("last_name", lname);
				break;
			case 3:
				System.out.println("Enter the job ID to search for:");
				int id = inputService.getInt(5);
				columns.put("job_id", id);
				break;
			case 4:
				System.out.println("Enter the age to search for:");
				int age = inputService.getInt(125);
				columns.put("user_age", age);
				break;
			case 5:
				System.out.println("Enter the salary to search for:");
				int salary = inputService.getInt(Integer.MAX_VALUE);
				columns.put("salary", salary);
				break;
			case 6:
				more = false;
			default:
				more = false;
			}
		}
		
		persons = pdao.getPersonByColumns(columns);
		
		if (persons == null) {
			System.out.println("Sorry no records were found.");
			userMenu();
		} else {
			System.out.println("|-- Records Matching the Search Parameters --|");
			
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21s| %-21s|%n", "Username", "First Name", "Last Name", "Job ", "Job Description", "User Age", "Salary in USD");
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			for (Person per : persons) {
				Job j = jdao.getJobPK(per.getJobId());
				System.out.printf("| %-20s| %-21s| %-21s| %-21s| %-21s| %-21d| %,-21d|%n", per.getId(), per.getFirstName(), per.getLastName(), j.getJobName(), j.getJobDescription(), per.getAge(), per.getSalary());
			}
			System.out.println("|---------------------|----------------------|----------------------|----------------------|----------------------|----------------------|----------------------|");
			System.out.println();
			
			recordMenu();
		}
	}
	
	private static void searchJobColumn() {
		List<Job> jobs = new ArrayList<>();
		String column = "";
		String value = "";
		
		System.out.println("Enter the number of your selection:");
		System.out.println("1. Search by Name");
		System.out.println("2. Search by Description");
		System.out.println("3. Return to the Job Menu");
		
		int input = inputService.getInt(3);
		switch(input) {
		case 1:
			column += "job name";
			System.out.println("Enter the name of the job to search for: ");
			value += inputService.getString(50);
			jobs = jdao.getJobByColumn("job_name", value);
			break;
		case 2:
			column += "description";
			System.out.println("Enter the description of the job to search for: ");
			value += inputService.getString(50);
			jobs = jdao.getJobByColumn("description", value);
			break;
		case 3:
			jobMenu();
		}
		
		System.out.println("|--- Records Where the " + column + " is " + value + " ---|");
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.printf("| %-20s| %-21s| %-21s|%n", "Job ID", "Name", "Description");
		System.out.println("|---------------------|----------------------|----------------------|");
		for (Job job : jobs) {
			System.out.printf("| %-20s| %-21s| %-21s|%n", job.getId(), job.getJobName(), job.getJobDescription());
		}
		System.out.println("|---------------------|----------------------|----------------------|");
		System.out.println();
		jobMenu();
	}
	
	
	private static void insertUser() {
		List<Person> persons = new ArrayList<>();
		
		persons.add(createPerson());
		
		while(true) {
			
			System.out.println("User added. Please enter the number of your selection: ");
			System.out.println("1. Add another user.");
			System.out.println("2. Finished making changes.");
			int input = inputService.getInt(2);
			
			if (input == 1) {
				persons.add(createPerson());
			} else {
				break;
			}
		}
		
		pdao.insertPerson(persons.toArray(new Person[0]));
		userMenu();
	}
	
	private static Person createPerson() {
		Person per = new Person();
		System.out.println("Enter the username:");
		per.setId(inputService.getString(50));
		
		System.out.println("Enter the first name:");
		per.setFirstName(inputService.getString(50));
		
		System.out.println("Enter the last name:");
		per.setLastName(inputService.getString(50));
		
		System.out.println("Enter the job id:");
		per.setJobId(inputService.getInt(5));
		
		System.out.println("Enter the user's age:");
		per.setAge(inputService.getInt(125));
		
		System.out.println("Enter the user's salary:");
		per.setSalary(inputService.getInt(Integer.MAX_VALUE));
		
		return per;
	}
	
	private static void insertJob() {
		List<Job> jobs = new ArrayList<>();
		
		jobs.add(createJob());
		
		while(true) {
			
			System.out.println("Job added. Please enter the number of your selection: ");
			System.out.println("1. Add another job.");
			System.out.println("2. Finished making changes.");
			int input = inputService.getInt(2);
			
			if (input == 1) {
				jobs.add(createJob());
			} else {
				break;
			}
		}
		
		jdao.insertJob(jobs.toArray(new Job[0]));
		jobMenu();
	}
	
	private static Job createJob() {
		Job job = new Job();
		System.out.println("Enter the job name:");
		job.setJobName(inputService.getString(50));
		
		System.out.println("Enter the job description:");
		job.setJobDescription(inputService.getString(50));
		
		return job;
	}
	
	private static void deleteUser() {
		System.out.println("Enter the username for the user to be deleted: ");
		String username = inputService.getString(50);
		Person per = pdao.getPersonPK(username);
		if (per != null) {
			pdao.deletePerson(per);
			System.out.println("User " + username + " deleted");
		} else {
			System.out.println("Sorry, no user with that username.");
		}
		userMenu();
	}
	
	private static void deleteJob() {
		System.out.println("Enter the ID number for the job to be deleted: ");
		int id = inputService.getInt(5);
		Job job = jdao.getJobPK(id);
		if (job !=null) {
			jdao.deleteJob(job);
			System.out.println(job.getJobName() + " job deleted");
		} else {
			System.out.println("Sorry, no job with that ID number.");
		}
		jobMenu();
	}
	
}
