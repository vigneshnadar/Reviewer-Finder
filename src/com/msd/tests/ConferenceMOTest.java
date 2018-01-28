package com.msd.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msd.model.ConferenceMO;

public class ConferenceMOTest {

	ConferenceMO conference = new ConferenceMO();
	
	@Test
	public void testGetAllConferencesString() {
		assertEquals(conference.getAllConferences("ECOOP").get(0).getConferenceName(),
				"ECOOP");
		assertEquals(conference.getAllConferences("oopsla").get(0).getConferenceName(),
				"OOPSLA");
	}

}
