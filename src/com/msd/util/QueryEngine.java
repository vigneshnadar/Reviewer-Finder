package com.msd.util;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.msd.model.Author;
import com.msd.model.AuthorMO;
import com.msd.model.ConferenceMO;
import com.msd.model.Filters;
import com.msd.model.JournalMO;
import com.msd.model.PublicationMO;

public class QueryEngine {


	/*Sample helper method to loadCommittees*/
	public void loadCommittees(String folderPath){
		// TODO Auto-generated method stub
		try{

			File folder = new File(folderPath);
			File[] listOfFiles = folder.listFiles();
			DBConnection conn = new DBConnection();

			Connection myConn = conn.getConnection();


			for (int k = 0; k < listOfFiles.length; k++) {
				if (listOfFiles[k].isFile()) {


					String filePath = folder+"/"+listOfFiles[k].getName();
					System.out.println("Reading - " + listOfFiles[k].getName());

					ArrayList<ArrayList<String>> confData = readCommittees.readFileByLines(filePath);


					//Create a statement
					String sql = "INSERT INTO CONFERENCES (C_NAME, C_YEAR, MEMBER, MEMBER_TYPE) VALUES (?, ?, ?, ?)";


					for(int i=0;i<confData.size();i++){
						ArrayList<String> subList = confData.get(i);
						conn.setPrepStmt(myConn.prepareStatement(sql));

						for(int j=1;j<=4;j++){
							conn.getPrepStmt().setString(j, subList.get(j-1));
						}
						conn.getPrepStmt().executeUpdate();
					}

				} else  {
					System.out.println("Other files");
				}
			}


			myConn.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		


	}

	public List<Filters> getFilters(String type) {
		// TODO Auto-generated method stub
		
		List<Filters> filters = new ArrayList<Filters>();
		
		DBConnection conn = new DBConnection();
		Connection myConn = conn.getConnection();
		String sql = "SELECT F_NAME, F_TYPE FROM FILTERS WHERE SCREEN LIKE ?";
		
		try {
			conn.setPrepStmt(myConn.prepareStatement(sql));
			conn.getPrepStmt().setString(1, "%"+type+"%");
			conn.setResultSet(conn.getPrepStmt().executeQuery());
			
			while(conn.getResultSet().next()){
				Filters f = new Filters();
				f.setFilterName(conn.getResultSet().getString(1));
				f.setFilterType(conn.getResultSet().getString(2));
				filters.add(f);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return filters;
	}

	public ArrayList<ConferenceMO> getAllConferences(String text) {
		// TODO Auto-generated method stub
		ArrayList<ConferenceMO> list = new ArrayList<ConferenceMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String sql = "select distinct(conference_name) from conference_data where conference_name like ?";
		try {
		conn.setPrepStmt(myconn.prepareStatement(sql));
		conn.getPrepStmt().setString(1, "%" + text + "%");
		System.out.println(conn.getPrepStmt().toString());
		conn.setResultSet(conn.getPrepStmt().executeQuery());
		
	
			while(conn.getResultSet().next()){
				//System.out.println("In here!");
				ConferenceMO c = new ConferenceMO();
				c.setConferenceName(conn.getResultSet().getString(1));
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public ArrayList<JournalMO> getAllJournals(String text) {
		// TODO Auto-generated method stub
		ArrayList<JournalMO> list = new ArrayList<JournalMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String sql = "select distinct(journal) from journal_data where journal like ?";
		try {
		conn.setPrepStmt(myconn.prepareStatement(sql));
		conn.getPrepStmt().setString(1, "%" + text + "%");
		System.out.println(conn.getPrepStmt().toString());
		conn.setResultSet(conn.getPrepStmt().executeQuery());
		
	
			while(conn.getResultSet().next()){
				//System.out.println("In here!");
				JournalMO c = new JournalMO();
				c.setJournalName(conn.getResultSet().getString(1));
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<JournalMO> getAllJournalAcronym(String text) {
		// TODO Auto-generated method stub
		ArrayList<JournalMO> list = new ArrayList<JournalMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String sql = "select distinct(journal_acronym) from journal_data where journal like ?";
		try {
		conn.setPrepStmt(myconn.prepareStatement(sql));
		conn.getPrepStmt().setString(1, "%" + text + "%");
		System.out.println(conn.getPrepStmt().toString());
		conn.setResultSet(conn.getPrepStmt().executeQuery());
		
	
			while(conn.getResultSet().next()){
				//System.out.println("In here!");
				JournalMO c = new JournalMO();
				c.setJournalName(conn.getResultSet().getString(1));
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	//get all the publications published by the given author name
	public List<PublicationMO> getPublicationsByAuthor(String authorName) {
		// TODO Auto-generated method stub
		ArrayList<PublicationMO> plist = new ArrayList<PublicationMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		try
		{
		 CallableStatement cStmt = myconn.prepareCall("{call getAuthorDetails(?)}");
		 cStmt.setString(1, authorName);
		 boolean hadResults = cStmt.execute();
		 int i=0;
		 List<AuthorMO> autList =new ArrayList<AuthorMO>();
		 while (hadResults) {
		        ResultSet rs = cStmt.getResultSet();

		     // process result set
		        while (rs.next()) {
		            // retrieve values of fields
		        	String title = rs.getString("title");
		        	String venue = rs.getString("venue");
		        	PublicationMO c = new PublicationMO();
		        	c.setYear(10);
					c.setName(title);
					c.setVenue(venue);
					c.setAuthors(autList);
					plist.add(c);
		             
		        }

		        hadResults = cStmt.getMoreResults();
		    }

		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return plist;
	}
	
	
	
	
	//get all the publications published by the given author name
		public List<AuthorMO> getSimilarAuthors(String authorName) {
			// TODO Auto-generated method stub
			ArrayList<AuthorMO> plist = new ArrayList<AuthorMO>();
			
			DBConnection conn = new DBConnection();
			Connection myconn = conn.getConnection();
			try
			{
			 CallableStatement cStmt = myconn.prepareCall("{call getSimilarAuthors(?)}");
			 cStmt.setString(1, authorName);
			 boolean hadResults = cStmt.execute();
			 int i=0;
			 List<AuthorMO> autList =new ArrayList<AuthorMO>();
			 while (hadResults) {
			        ResultSet rs = cStmt.getResultSet();

			     // process result set
			        while (rs.next()) {
			            // retrieve values of fields
			        	String similarAuthor = rs.getString("author_name");
			        	AuthorMO c = new AuthorMO(similarAuthor);
						plist.add(c);
			             
			        }

			        hadResults = cStmt.getMoreResults();
			    }

			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return plist;
		}
	
	
	

	public List<PublicationMO> getPublicationsByType(String confName,String pubType) {
		// TODO Auto-generated method stub
		ArrayList<PublicationMO> list = new ArrayList<PublicationMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String journalType="J",conferenceType="C";
		String sql=null;
		if(pubType.equals(journalType))
		{
			 sql = "select year, title, author from journal_data "
					+ "where journal like ? order by year desc";
		}
		else
		{
			sql = "select year, title, author from conference_data "
					+ "where conference_name like ? order by year desc";	
		}
		

		try {
		conn.setPrepStmt(myconn.prepareStatement(sql));
		conn.getPrepStmt().setString(1, "%" + confName + "%");
		System.out.println(conn.getPrepStmt().toString());
		conn.setResultSet(conn.getPrepStmt().executeQuery());
		
	
			while(conn.getResultSet().next()){
				//System.out.println("In here!");
				PublicationMO c = new PublicationMO();
				c.setYear(conn.getResultSet().getInt(1));
				c.setName(conn.getResultSet().getString(2));
				c.setAuthors(setAuthors(conn.getResultSet().getString(3)));
				
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	public static List<AuthorMO> setAuthors(String authors){
		String temp[] = authors.split(";");
		
		List<AuthorMO> list = new ArrayList<AuthorMO>();
		
		for(String t:temp){
			AuthorMO a = new AuthorMO(t, t);
			list.add(a);
		}
		
		return list;
	}

	public List<PublicationMO> getPublicationsByTitle(String title) {
		// TODO Auto-generated method stub
		ArrayList<PublicationMO> list = new ArrayList<PublicationMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String sql = "select year, title, author from conference_data "
				+ "where title like ? order by year desc";

		try {
		conn.setPrepStmt(myconn.prepareStatement(sql));
		conn.getPrepStmt().setString(1, "%" + title + "%");
		System.out.println(conn.getPrepStmt().toString());
		conn.setResultSet(conn.getPrepStmt().executeQuery());
		
	
			while(conn.getResultSet().next()){
				//System.out.println("In here!");
				PublicationMO c = new PublicationMO();
				c.setYear(conn.getResultSet().getInt(1));
				c.setName(conn.getResultSet().getString(2));
				c.setAuthors(setAuthors(conn.getResultSet().getString(3)));
				
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	public List<AuthorMO> getFilteredAuthorList(int[] yp, int[] yc, int cFlag, int jFlag, String confNames,
			String journalNames,int commFlag) {
		// TODO Auto-generated method stub
ArrayList<PublicationMO> plist = new ArrayList<PublicationMO>();
		
		DBConnection conn = new DBConnection();
		Connection myconn = conn.getConnection();
		String pubNames=null;
		List<AuthorMO> autList =new ArrayList<AuthorMO>();
			
		try
		{
		 CallableStatement cStmt = myconn.prepareCall("{call applyAuthorFilters(?,?,?,?,?,?,?,?,?)}");
		 cStmt.setString(1, "");
		 cStmt.setInt(2, yp[0]);
		 cStmt.setInt(3, yp[1]);
		 cStmt.setString(4, confNames);
		 cStmt.setInt(5, yc[0]);
		 cStmt.setInt(6, yc[1]);
		 cStmt.setInt(7, jFlag);
		 cStmt.setInt(8, cFlag);
		 cStmt.setInt(9, commFlag);
		 boolean hadResults = cStmt.execute();
		 int i=0;
		 
		 while (hadResults) {
		        ResultSet rs = cStmt.getResultSet();

		     // process result set
		        while (rs.next()) {
		            // retrieve values of fields
		        	String author = rs.getString("author_name");
		        	String authposition = rs.getString("author_recent_position");
		        	AuthorMO a = new AuthorMO();
					a.setAuthorName(author);
					autList.add(a);
		             
		        }

		        hadResults = cStmt.getMoreResults();
		    }

		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return autList;
	}
	
//	public List<AuthorMO> getAuthorsByTitle(String title) {
//		ArrayList<AuthorMO> list = new ArrayList<AuthorMO>();
//		
//		DBConnection conn = new DBConnection();
//		Connection myconn = conn.getConnection();
//		String sql = "select year, title, author from conference_data "
//				+ "where title like ? order by year desc";
//
//		try {
//		conn.setPrepStmt(myconn.prepareStatement(sql));
//		conn.getPrepStmt().setString(1, "%" + title + "%");
//		System.out.println(conn.getPrepStmt().toString());
//		conn.setResultSet(conn.getPrepStmt().executeQuery());
//		
//	
//			while(conn.getResultSet().next()){
//				//System.out.println("In here!");
//				PublicationMO c = new PublicationMO();
//				c.setYear(conn.getResultSet().getInt(1));
//				c.setName(conn.getResultSet().getString(2));
//				c.setAuthors(setAuthors(conn.getResultSet().getString(3)));
//				
//				list.add(c);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
	
}
