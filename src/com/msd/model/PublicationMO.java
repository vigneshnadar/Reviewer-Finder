package com.msd.model;

import java.util.Date;
import java.util.List;

import com.msd.util.QueryEngine;

public class PublicationMO implements Publication{

	String name;
	String venue;
	int year;
	List<AuthorMO> authors;
	@Override
	public String getTitle(int publicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getAuthors(int publicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numberOfPages(int publicationId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getPublicationDate(int publicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> getPublicationsByFilters(List<Publication> publicationList, Filters[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPublicationID(String publicationName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<PublicationMO> getMostRecentPublications(String confName,String pubType){
		
		QueryEngine qe = new QueryEngine();
		List<PublicationMO> pubsList = qe.getPublicationsByType(confName,pubType);
		
		return pubsList;
	}
	
	public List<PublicationMO> getAuthorPublications(String authorName){
		
		QueryEngine qe = new QueryEngine();
		List<PublicationMO> pubsList = qe.getPublicationsByAuthor(authorName);
		
		return pubsList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<AuthorMO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorMO> authors) {
		this.authors = authors;
	}

	public List<PublicationMO> getPublicationsByTitle(String title){
		
		QueryEngine qe = new QueryEngine();
		List<PublicationMO> pubsList = qe.getPublicationsByTitle(title);
		
		return pubsList;
	}
	
	
}
