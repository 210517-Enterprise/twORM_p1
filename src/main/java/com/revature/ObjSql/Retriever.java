package com.revature.ObjSql;

import org.apache.log4j.Logger;

public class Retriever {

	private static final Retriever ObjRet = new Retriever();
	
	private static Logger log = Logger.getLogger(Retriever.class);
	
	private Retriever() {
		super();
	}
	
    public static Retriever getInstance() {
        return ObjRet;
    }
}
