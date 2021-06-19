package com.revature;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

public class MetaModelTests {
	
	private final MetaConstructor constructor = MetaConstructor.getInstance();
	private final TwORM t = TwORM.getInstance();
	
	@Test
	public void testAddModel() {
		boolean result = t.addClass(Person_Two.class);
		assertTrue(result);
	}
	
	@Test
	public void testGetModels() {
		HashMap<String, MetaModel<?>> rs = constructor.getModels();
		assertTrue(rs.size() != 0);
		assertTrue(rs.size() == 1);
		
	}
	
}
