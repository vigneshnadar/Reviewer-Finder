package com.msd.util;

import java.sql.*;
 
public class DBConnection {

	PreparedStatement prepStmt;
	ResultSet resultSet;

	public Connection getConnection(){

		Connection myConn=null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/team21", "root","msdteam21");

		} catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myConn;
	}

	public static void main(String[] args) {
		
		QueryEngine qe = new QueryEngine();
		qe.loadCommittees("/Users/AnkitaNallana/Desktop/committees/");
		
	}

	public PreparedStatement getPrepStmt() {
		return prepStmt;
	}

	public void setPrepStmt(PreparedStatement prepStmt) {
		this.prepStmt = prepStmt;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	
	
}
