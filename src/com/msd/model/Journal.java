package com.msd.model;
                                                    
import java.util.Date;
import java.util.List;

public interface Journal {

	//gets the journal name corresponding to the journal id
	public String getJournalName(int journalId);
	//gets the journal acronym corresponding to the journal id
	public String getJournalAcronym(int journalId);
	//gets the journal publication date corresponding to the journal id
	public Date getJournalPublicationDate(int journalId);
	//gets the journal issue corresponding to the journal id
	public int getJournalIssue(int journalId);
	//gets the journal volume corresponding to the journal id
	public int getJournalVolume(int journalId);
	//gets the list of all publications in a journal
	public List<Publication> getPublications();
	//gets the list of publications in the journal corresponding to the filters
	public List<Publication> getPublicationsByFilters(List<Publication> publicationList,Filters[] filters);
	//given a journal name returns the  journal id
	public String getJournalID(String journalName);
	public List<JournalMO> getAllJournals(String text) ;
	public List<JournalMO> getJournals(String text);
}
