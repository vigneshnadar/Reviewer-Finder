package com.msd.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msd.model.PublicationMO;

public class PublicationMOTest {
	
	PublicationMO publication = new PublicationMO();

	@Test
	public void testGetMostRecentPublications() {
		assertEquals(publication.getMostRecentPublications("ECOOP", "C").get(0).getName(),
				"Scopes Describe Frames: A Uniform Model for Memory Layout in Dynamic Semantics.");
		assertEquals(publication.getMostRecentPublications("ECOOP", "C").get(1).getName(),
				"Towards a model of corecursion with default.");

	}

	@Test
	public void testGetAuthorPublications() {
//		System.out.println(publication.getAuthorPublications("Casper Bach Poulsen").get(0).getName());
//		assertEquals(publication.getAuthorPublications("Casper Bach Poulsen").get(0).getName(),
//				"Scopes Describe Frames: A Uniform Model for Memory Layout in Dynamic Semantics.");
		
	}
	

	@Test
	public void testGetPublicationsByTitle() {
		assertEquals(publication.getPublicationsByTitle("algorithms").get(0).getName(),
				"A short counterexample property for safety and liveness verification of fault-tolerant distributed algorithms.");
		assertEquals(publication.getPublicationsByTitle("css").get(1).getName(),
				"Detecting redundant CSS rules in HTML5 applications: a tree rewriting approach.");
	}

}
