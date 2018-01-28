/*
 * Copyright (c) 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.msd.layout;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import com.msd.model.*;
public class KeywordSearch extends Application  {

	GridPane grid = new GridPane();
	public Scene scene = new Scene(grid, 900, 750);
	Button btn = new Button("");
	Button exit = new Button("");
	Text scenetitle = new Text("Search Results");
	TextField author = new TextField("");
	Stage primaryStage;


	String text;

	List<AuthorMO> authors;
	List<PublicationMO> publications;

	GridPane authorGrid ;
	GridPane pubGrid ;

	KeywordSearch(){


		System.out.println("Init");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);


		author.setPrefHeight(70);
		author.setText(getText());
		grid.add(author, 1, 1);



		btn.setId("searchBtn");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 2, 1);

		exit.setId("exitBtn");
		exit.setText("EXIT");
		grid.add(exit, 1, 3);



		scene.getStylesheets().add(KeywordSearch.class.getResource("/resources/HomeScreen.css").toExternalForm());

		System.out.println("Set Scene for Results Screen");

	}

	public void start(Stage primaryStage, String text){
		this.primaryStage = primaryStage;
		setText(text);
		author.setText(getText());
		start(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Run Results");
		if(getText().length()>0){
			showResults(getText());
		}

		this.exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Exit from Search Results screen");
				System.exit(0);
			}
		});

		this.btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try{
					scenetitle.setText("Searching for "+ author.getText());
					authors = updateAuthors(author.getText());
					publications = updatePublications(author.getText());
					rebuildAuthorView(authors);
					rebuildPubView(publications);

				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});


		primaryStage.setTitle("Search Results");
		primaryStage.setScene(this.scene);
		primaryStage.show();
	}

	static List<AuthorMO> updateAuthors(String text){
		AuthorMO a = new AuthorMO();

		String[] stringList = text.split(" ");
		int length = stringList.length;


		List<AuthorMO> finalAuthors = new ArrayList<AuthorMO>();

		for(int i = 0; i < length; i++){
			List<AuthorMO> listAuthor = a.getAllAuthors(stringList[i]);
			finalAuthors.addAll(listAuthor);
		}
		if(finalAuthors.size() > 10){
			finalAuthors = finalAuthors.subList(0, 10);
		}
		return finalAuthors;

	}

	static List<PublicationMO> updatePublications(String text){
		PublicationMO p = new PublicationMO();

		String[] stringList = text.split(" ");
		int length = stringList.length;

		List<PublicationMO> finalPub = new ArrayList<PublicationMO>();

		for(int i = 0; i < length; i++){
			List<PublicationMO> listPubTitle = p.getPublicationsByTitle(stringList[i]);
			List<PublicationMO> listPubConf = p.getMostRecentPublications(stringList[i],"C");
			finalPub.addAll(listPubTitle);
			finalPub.addAll(listPubConf);
		}

		if(finalPub.size() > 10){
			finalPub = finalPub.subList(0, 10);
		}
		return finalPub;

	}

	void showResults(String text){
		authors = updateAuthors(text);
		authorGrid = buildAuthorListView(authors);
		authorGrid.setId("authorGrid");

		publications = updatePublications(text);
		pubGrid = buildPubListView(publications);
		pubGrid.setId("pubGrid");

		grid.add(authorGrid, 1, 4, 4, 1);	
		grid.add(pubGrid, 2, 4, 4, 3);	

	}





	public static String authorsToString(List<AuthorMO> authors){
		int len = authors.size();
		String resultString = "";

		for(int i = 0; i < len; ++i){
			resultString+=(authors.get(i).getAuthorName())+", ";
		}

		return resultString;

	}



	void rebuildAuthorView(List<AuthorMO> list){
		GridPane authorGrid = (GridPane)scene.lookup("#authorGrid");
		System.out.println("authorGrid -  "+authorGrid.toString());
		grid.getChildren().remove(authorGrid);
		if(list.size()>=10)
			authorGrid = buildAuthorListView(list.subList(0, 10));
		else authorGrid = buildAuthorListView(list);
		authorGrid.setId("authorGrid");
		grid.add(authorGrid, 1, 4, 4, 1);
	}


	void rebuildPubView(List<PublicationMO> list){
		GridPane pubGrid = (GridPane)scene.lookup("#pubGrid");
		grid.getChildren().remove(pubGrid);
		if(list.size()>=10)
			pubGrid = buildPubListView(list.subList(0, 10));
		else pubGrid = buildPubListView(list);
		pubGrid.setId("pubGrid");
		grid.add(pubGrid, 2, 4, 4,3);
	}






	static GridPane buildPubListView(List<PublicationMO> pubList){
		GridPane pubGrid = new GridPane();
		pubGrid.setId("pubGrid");


		int row = 1;



		if (pubList.size() > 10){
			pubList.subList(0, 10);
		}

		for(PublicationMO pub:pubList){
			GridPane subGrid = new GridPane();
			subGrid.setHgap(10);

			if(row==1){
				
				Text authors = new Text("Authors");
				authors.setWrappingWidth(250);
				subGrid.add(authors, 0, 0, 1, 1);
				Text title = new Text("Titles");
				title.setId("");
				subGrid.add(title, 1, 0, 1, 1);
				title.setWrappingWidth(200);
				Text years = new Text("Year");
				subGrid.add(years, 2, 0, 1, 1);
				years.setWrappingWidth(100);
			}

			else{
				Text pubTitle = new Text(pub.getName());
				pubTitle.setWrappingWidth(250);
				Text author = new Text(authorsToString(pub.getAuthors()));
				author.setWrappingWidth(200);
				Text year = new Text(String.valueOf(pub.getYear()));
				year.setWrappingWidth(150);

				subGrid.add(author, 0, row);
				subGrid.add(year,2, row);
				subGrid.add(pubTitle, 1, row);



				subGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {	
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						System.out.println("You clicked on - "+subGrid.getChildren().get(0).toString());
						//primaryStage.close();
						String pub = ((Text)subGrid.getChildren().get(0)).getText();
						AuthorScreen as = new AuthorScreen(pub);
						as.start(new Stage());

					}});;}
			pubGrid.add(subGrid, 0, row);
			row++;
		}

		return pubGrid;
	}



	static GridPane buildAuthorListView(List<AuthorMO> authorList){
		GridPane authorGrid = new GridPane();
		int row = 1;
		Text id = new Text("ID");
		id.setId("authorGrid");
		authorGrid.add(id, 0, 0, 1, 1);
		Text author = new Text("Author");
		author.setWrappingWidth(100);
		author.setTextAlignment(TextAlignment.LEFT);
		authorGrid.add(author, 1, 0, 1, 1);

		if (authorList.size() > 10){
			authorList.subList(0, 10);
		}
		for(AuthorMO aut:authorList){
			GridPane subGrid = new GridPane();
			subGrid.setHgap(10);
			Text authorName = new Text(aut.getAuthorName());
			authorName.setWrappingWidth(100);
			Text authorID = new Text(aut.getAuthorID());
			authorID.setWrappingWidth(50);
			subGrid.add(authorID, 0, row);
			subGrid.add(authorName, 1, row);
			authorGrid.add(subGrid, 0, row);

			subGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println("You clicked on - "+subGrid.getChildren().get(0).toString());
					//primaryStage.close();
					String author = ((Text)subGrid.getChildren().get(0)).getText();
					AuthorScreen as = new AuthorScreen(author);
					as.start(new Stage());

				}});;

				row++;
		}
		return authorGrid;

	}




	public Text getScenetitle() {
		return scenetitle;
	}

	public void setScenetitle(Text scenetitle) {
		this.scenetitle = scenetitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
