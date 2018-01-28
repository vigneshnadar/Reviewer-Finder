package com.msd.model;
import com.msd.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorMO implements Author {

	String authorName;
	String authorId;
	int pubCount;
	
	public AuthorMO(String authorName, String authorId){
		this.authorName = authorName;
		this.authorId = authorId;
	}
	

	
	public AuthorMO(String authorName){
		this.authorName = authorName;
	}
	
	public AuthorMO(){}
	
	@Override
	public String getAuthorName() {
		// TODO Auto-generated method stub
		return this.authorName;
	}
	
	
	public List<AuthorMO> getSimilarAuthors(String authorName){
		
		QueryEngine qe = new QueryEngine();
		List<AuthorMO> authList = qe.getSimilarAuthors(authorName);
		
		return authList;
	}

	@Override
	public List<AuthorMO> getAllAuthors(String author) {
		// TODO Auto-generated method stub
		ArrayList<AuthorMO> list = new ArrayList<AuthorMO>();
		try{
		System.out.println("here with - "+author+" - new");
		AuthorMO[] a = new AuthorMO[5];
		DBConnection dl = new DBConnection();
		Connection con = dl.getConnection();
		Statement st = con.createStatement();
		

		String selectSQL = "SELECT author_id,author_name FROM authors WHERE author_name like '%"+author+"%'";
		//PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
		//preparedStatement.setString(1,"peter norvig");
		System.out.println("1");
		System.out.println(selectSQL);
		//ResultSet rs = preparedStatement.executeQuery(selectSQL);
		ResultSet rs = st.executeQuery(selectSQL);
		System.out.println("1");
		int i=0;
		while (rs.next()) {
			System.out.println("2");
			int authorId=rs.getInt("author_id");
			String authorName = rs.getString("author_name");
			list.add(new AuthorMO(authorName, String.valueOf(authorId)));
			if(i==10) break;
			i++;
		}
		

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public int getNumberOfPublications() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "AuthorMO [authorName=" + authorName + ", authorId=" + authorId + ", pubCount=" + pubCount + "]";
	}



	@Override
	public List<String> getResearchFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorMO> getSimilarAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthorID() {
		// TODO Auto-generated method stub
		return this.authorId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}



	public int getPubCount() {
		return pubCount;
	}



	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}

}
