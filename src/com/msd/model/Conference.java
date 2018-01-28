package com.msd.model;

import java.util.Date;
import java.util.List;

public interface Conference {	
	//gets the list of all conferences in database
	public List<ConferenceMO> getAllConferences();
	//gets the name of the conference corresponding to the conference id
	public String getConferenceName(int conferenceId);
	//gets the acronym of the conference corresponding to the conference id
	public String getConferenceAcronym(int conferenceId);
	//gets the date the conference occurred corresponding to the conference id
	public Date getConferenceDate(int conferenceId);
	//gets the name of the publications in the conference corresponding to the conference id
	public List<PublicationMO> getPublications(int conferenceId);
	//gets the list of publications in the conference corresponding to the filters
	public List<PublicationMO> getPublicationsByFilters(List<PublicationMO> publicationList, Filters[] filters);
	//gets the name of the conference id corresponding to the conference name
	public String getConferenceID(String conferenceName);
	List<ConferenceMO> getAllConferences(String text);}
