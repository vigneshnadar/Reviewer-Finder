package com.msd.model;

import java.util.List;

public interface Author {

	public String getAuthorName();
	public List<AuthorMO> getAllAuthors(String author);
	public int getNumberOfPublications();
	public List<String> getResearchFields();
	public List<AuthorMO> getSimilarAuthors();
	public String getAuthorID();
}
