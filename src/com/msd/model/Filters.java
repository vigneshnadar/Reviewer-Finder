package com.msd.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.msd.util.QueryEngine;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Filters {

	String filterName;
	String filterType;
	List<Object> filterContent;

	public Filters(){

	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public List<Object> getFilterContent() {
		return filterContent;
	}

	public void setFilterContent(List<Object> filterContent) {
		this.filterContent = filterContent;
	}

	public static HashMap<String, Integer> buildAuthorsList(List<PublicationMO> pubList){

		HashMap<String, Integer> authors = new HashMap<String, Integer>();
		for(PublicationMO p:pubList){
			List<AuthorMO> temp = p.getAuthors();
			for(AuthorMO a : temp){
				if(authors.containsKey(a.getAuthorName())){
					int count = authors.get(a.getAuthorName()) + 1;
					authors.put(a.getAuthorName(), count);
				}
				else authors.put(a.getAuthorName(), 1);
			}
		}
		
		return authors;
	}
	
	
	public static List<PublicationMO> applyYearFilter(int year1, int year2, List<PublicationMO> list, List<Filters> filters){
		List<PublicationMO> pubList = new ArrayList<PublicationMO>();
		
		for(int i=0;i<list.size();i++){
			if(isBetween(list.get(i).getYear(), year1, year2)){
				pubList.add(list.get(i));
			}
		}
		
		for(Filters f : filters){
			if("A".equalsIgnoreCase(f.getFilterType())){
				System.out.println("Earlier - "+f.getFilterContent().toString());
				HashMap<String, Integer> tempList = buildAuthorsList(pubList);
				f.setFilterContent(Filters.buildAuthorFilter(tempList));
				System.out.println("Now - "+f.getFilterContent().toString());
				
			}
		}
		
		return pubList;
	}
	
	static boolean isBetween(int year,  int year1, int year2){
		if(year>=year1 && year<=year2)
			return true;
		return false;
	}
	
	public static List<Filters> getFiltersByType(String type){

		List<Filters> list ;

		QueryEngine qe = new QueryEngine();
		list = qe.getFilters(type);
		System.out.println("Filters - "+list.toString());
		for(Filters f:list){
			System.out.println("filter - "+f.getFilterType());
			if("YEAR".equalsIgnoreCase(f.getFilterType())){
				System.out.println("Building the Year Filter");
				List<Object> filterContent = new ArrayList<Object>();
				System.out.println("we are here");
				TextField tf1 = new TextField();
				tf1.setMaxWidth(50);
				filterContent.add(tf1);
				System.out.println("uptil here!");
				filterContent.add(new Text(" - "));

				tf1 = new TextField();
				tf1.setMaxWidth(50);
				filterContent.add(tf1);
				f.setFilterContent(filterContent);
				System.out.println("BUILT YEAR FILTER");
				System.out.println(f.getFilterContent());
			}
			
			
			else if("CHECKBOX_C".equalsIgnoreCase(f.getFilterType())){
				System.out.println("Building the Year Filter");
				List<Object> filterContent = new ArrayList<Object>();
				List<ConferenceMO> confList = qe.getAllConferences("");
				
				confList = confList.subList(0, Math.min(confList.size(), 5));
				for(ConferenceMO conf: confList)
				{				
					//A checkbox with a string caption
					CheckBox confCheckBox = new CheckBox(conf.getConferenceName());
					confCheckBox.setId(conf.getConferenceName());

				filterContent.add(confCheckBox);
				}
				f.setFilterContent(filterContent);
				System.out.println(f.getFilterContent());
			}
			
			
			
			
			else if("CHECKBOX_J".equalsIgnoreCase(f.getFilterType())){
			System.out.println("Building the Year Filter");
			List<Object> filterContent = new ArrayList<Object>();
			List<JournalMO> journalList = qe.getAllJournalAcronym("");
			
			journalList = journalList.subList(0, Math.min(journalList.size(), 5));
			for(JournalMO journ: journalList)
			{				
				//A checkbox with a string caption
				CheckBox journCheckBox = new CheckBox(journ.getJournalName());
				journCheckBox.setId(journ.getJournalName());

			filterContent.add(journCheckBox);
			}
			f.setFilterContent(filterContent);
			System.out.println(f.getFilterContent());
		}
		
			
			

		}
		
		return list;
	}

	public static List<Object> buildAuthorFilter(HashMap<String, Integer> authors){

		List<Object> filterContent = new ArrayList<Object>();

		for(String s : authors.keySet()){
			Text authorName = new Text(s+"("+authors.get(s)+")");
			filterContent.add(authorName);
		}

		return filterContent;
	}

	@Override
	public String toString() {
		return "Filters [filterName=" + filterName + ", filterType=" + filterType + ", filterContent=" + filterContent
				+ "]";
	}
}
