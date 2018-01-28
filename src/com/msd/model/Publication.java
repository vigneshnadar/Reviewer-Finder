package com.msd.model;

import java.util.Date;
import java.util.List;

public interface Publication {
	
	//gets the publication name corresponding to the publication id
	public String getTitle(int publicationId);
	//gets the list of authors corresponding to the publication id
	public List<Author> getAuthors(int publicationId);
	//gets the number of pages in a publication corresponding to the publication id
	public int numberOfPages(int publicationId);
	//gets the publication date of the publication corresponding to the publication id
	public Date getPublicationDate(int publicationId);	
	//gets the list of publications in the conference corresponding to the filters
	public List<Publication> getPublicationsByFilters(List<Publication> publicationList, Filters[] filters);
	//gets the publication id corresponding to the publication name
	public String getPublicationID(String publicationName);
}
