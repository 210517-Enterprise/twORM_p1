package com.revature;

import com.revature.demo.Crime;
import com.revature.twORM.TwORM;

public class Driver {
	
	public static void main(String[] args) {
	TwORM t = TwORM.getInstance();
	t.addClass(Crime.class);
	Crime arson = new Crime("Arson", "Burning stuff", 5);
	Crime theft = new Crime("Theft", "Stealing stuff", 5);
	Crime loitering = new Crime("Loitering", "Whatever cops say it is", 30);
	t.disableAutoCommit();
	t.beginTransaction();
	t.addObjectToDb(loitering);
	t.addObjectToDb(theft);
	t.addObjectToDb(arson);
	t.commitChanges();
	System.out.println(t.getCache());
	loitering.setDescription("Criminal laziness");
	t.updateObjectInDB(loitering);
	System.out.println(t.getCache());
	t.abortChanges();
	System.out.println(t.getCache());
	}

}
