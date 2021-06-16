package com.revature.ObjSql;

import org.apache.log4j.Logger;

public class Updater {
	
	private static final Updater ObjUpd = new Updater();
	
	private static Logger log = Logger.getLogger(Updater.class);
	
	private Updater() {
		super();
	}
	
    public static Updater getInstance() {
        return ObjUpd;
    }
}
