package com.msd.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msd.model.AuthorMO;

public class AuthorMOTest {

	
	AuthorMO author = new AuthorMO();
	
	
	@Test
	public void testGetAllAuthors() {
		assertEquals(author.getAllAuthors("Alexandre DAVID").get(0).getAuthorName(),
				"Alexandre David");
		assertEquals(author.getAllAuthors("Alexandre DAVID").get(0).getAuthorId(),
				"707");
		
		assertEquals(author.getAllAuthors("peter").get(1).getAuthorName(),
				"Chang Hwan Peter Kim");
		assertEquals(author.getAllAuthors("peter").get(1).getAuthorId(),
				"2964");

	}
	
	@Test
	public void testGetSimilarAuthors() {
//		System.out.println(author.getSimilarAuthors("Alexandre DAVID").get(0).getAuthorName());
//		assertEquals(author.getSimilarAuthors("Alexandre DAVID").get(0).getAuthorName(),
//				"Alexandre David");
//		assertEquals(author.getSimilarAuthors("Alexandre DAVID").get(0).getAuthorId(),
//				"707");
//		
//		assertEquals(author.getSimilarAuthors("peter").get(1).getAuthorName(),
//				"Chang Hwan Peter Kim");
//		assertEquals(author.getSimilarAuthors("peter").get(1).getAuthorId(),
//				"2964");

	}

}
