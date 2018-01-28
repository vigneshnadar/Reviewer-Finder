package com.msd.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.msd.util.QueryEngine;

public class ConferenceMO implements Conference{

	String conferenceName;
	String conferenceID;
	int year;
	
	ConferenceMO(String conferenceName, String conferenceId, int year){
		this.conferenceID = conferenceId;
		this.conferenceName = conferenceName;
		this.year = year;
	}
	
	public ConferenceMO(){}
	
	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getConferenceID() {
		return conferenceID;
	}

	public void setConferenceID(String conferenceID) {
		this.conferenceID = conferenceID;
	}

	@Override
	public List<ConferenceMO> getAllConferences(String text) {
		// TODO Auto-generated method stub
		System.out.println("in confMO");
		ArrayList<ConferenceMO> list = new ArrayList<ConferenceMO>();
		try{
			ConferenceMO c = new ConferenceMO();
			QueryEngine qe = new QueryEngine();
			list = qe.getAllConferences(text);
		
		
		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public String getConferenceName(int conferenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConferenceAcronym(int conferenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getConferenceDate(int conferenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicationMO> getPublications(int conferenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicationMO> getPublicationsByFilters(List<PublicationMO> publicationList, Filters[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConferenceID(String conferenceName) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public List<ConferenceMO> getAllConferences() {
		// TODO Auto-generated method stub
		return null;
	}

}
