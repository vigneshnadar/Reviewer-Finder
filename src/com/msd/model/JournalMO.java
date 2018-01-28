package com.msd.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.msd.util.QueryEngine;

public class JournalMO implements Journal{
	
	String journalName;
	String journalAcronym;
	String journalId;
	int volume;
	int issue;

	public JournalMO(){}
	
	JournalMO(String journalName, String journalId){
		this.journalName = journalName;
		this.journalId = journalId;
	}
	@Override
	public String getJournalName(int journalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJournalAcronym(int journalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getJournalPublicationDate(int journalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getJournalIssue(int journalId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getJournalVolume(int journalId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Publication> getPublications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> getPublicationsByFilters(List<Publication> publicationList, Filters[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJournalID(String journalName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public String getJournalAcronym() {
		return journalAcronym;
	}

	public void setJournalAcronym(String journalAcronym) {
		this.journalAcronym = journalAcronym;
	}

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}
	
	
	@Override
	public List<JournalMO> getAllJournals(String text) {
		// TODO Auto-generated method stub
		System.out.println("in confMO");
		ArrayList<JournalMO> list = new ArrayList<JournalMO>();
		try{
			JournalMO c = new JournalMO();
			QueryEngine qe = new QueryEngine();
			list = qe.getAllJournals(text);
		
		
		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public List<JournalMO> getAllJournalAcronym(String text) {
		// TODO Auto-generated method stub
		System.out.println("in confMO");
		ArrayList<JournalMO> list = new ArrayList<JournalMO>();
		try{
			JournalMO c = new JournalMO();
			QueryEngine qe = new QueryEngine();
			list = qe.getAllJournalAcronym(text);
		
		
		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<JournalMO> getJournals(String text) {
		// TODO Auto-generated method stub
		List<JournalMO> list = new ArrayList<JournalMO>();
		try{
			JournalMO[] j = new JournalMO[5];
		
		
		for(int i=0;i<5;i++){
			j[i] = new JournalMO("JN"+(9468+i), "_JN"+(7765+i));
			list.add(j[i]);
		}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

}
