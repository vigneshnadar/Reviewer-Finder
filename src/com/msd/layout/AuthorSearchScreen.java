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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

import com.msd.model.*;
import com.msd.util.QueryEngine;
public class AuthorSearchScreen extends Application  {

	GridPane grid = new GridPane();
	public Scene scene = new Scene(grid, 500, 500);
	Button btn = new Button("");
	Button backButton = new Button("");
	Text scenetitle = new Text("  Search within Authors");
	TextField searchText = new TextField("");
	Stage primaryStage;
	GridPane authorGrid;
	List<Filters> filters;
	List<AuthorMO> filteredAuthorList;

	String text;

	Author authors;

	AuthorSearchScreen(){


		System.out.println("Init");
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		scenetitle.setId("welcome-text");
		scenetitle.setTextAlignment(TextAlignment.CENTER);
		grid.add(scenetitle, 0, 0, 2, 1);

		GridPane subGrid = new GridPane();
		subGrid.setHgap(10);

		searchText.setPrefHeight(70);
		searchText.setText(getText());
		searchText.setMaxSize(500, 500);
		searchText.setMinSize(330,50);
		subGrid.add(searchText, 1, 1);


		btn.setId("searchBtn");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		subGrid.add(btn, 2, 1);

		backButton.setId("exitBtn");
		backButton.setText("Go Back");
		backButton.setMaxSize(500, 500);
		backButton.setMinSize(50,50);
		subGrid.add(backButton, 3, 1);

		grid.add(subGrid, 0, 1, 3, 1);
		authorGrid = buildAuthorGrid(new AuthorMO().getAllAuthors(""));
		authorGrid.setId("authorGrid");
		grid.add(authorGrid, 1, 2, 4, 1);

		/* Add filters */
		filters = getCustomFilters();
		GridPane filtersGrid = buildFiltersGrid(filters);
		grid.add(filtersGrid, 4, 2);


		scene.getStylesheets().add(ConferenceSearchScreen.class.getResource("/resources/HomeScreen.css").toExternalForm());

		System.out.println("Set Scene for Results Screen");

	}



	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		System.out.println("Run Results - Conf screen");


		this.backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Exit from Conference Screen");
				HomeScreen homescreen = new HomeScreen();
				primaryStage.close();
				homescreen.start(new Stage());
			}
		});

		this.btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try{
					scenetitle.setText("Searching for "+text);
					showResults(searchText.getText().trim());

				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});


		primaryStage.setTitle("Author Search Results");
		primaryStage.setScene(this.scene);
		primaryStage.show();
	}

	void showResults(String text){
		AuthorMO c = new AuthorMO();
		System.out.println("show results called");
		List<AuthorMO> list = c.getAllAuthors(text);

		

	}
	
	
	void rebuildAuthorsGrid(List<AuthorMO> authorList){
		if(authorList.size()==0){
			scenetitle.setText("No results found!");
		}

		else { 
			scenetitle.setText(authorList.size()+" matches found!");
			authorGrid = (GridPane)scene.lookup("#authorGrid");
			grid.getChildren().remove(authorGrid);
			authorGrid = buildAuthorGrid(authorList);
			authorGrid.setId("authorGrid");
			grid.add(authorGrid, 1, 2, 4, 1);



		} 
	}

	GridPane buildAuthorGrid(List<AuthorMO> list){
		GridPane authorGrid = new GridPane();
		authorGrid.setVgap(10);

		int row = 1;
		Text title = new Text("Authors");
		title.setId("titleHeader");
		authorGrid.add(title, 0, 0, 2, 1);

		for(AuthorMO aut:list){
			GridPane subGrid = new GridPane();
			subGrid.setHgap(10);
			Text authorName = new Text(aut.getAuthorName());
			Text confAcronym = new Text("(OOPSLA)");
			subGrid.add(authorName, 0, row);
			//subGrid.add(confAcronym, 1, row);
			authorGrid.add(subGrid, 0, row);

			subGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println("You clicked on - "+subGrid.getChildren().get(0).toString());

					String conf = ((Text)subGrid.getChildren().get(0)).getText();
					AuthorScreen as = new AuthorScreen(conf);
					primaryStage.close();
					as.start(new Stage());

				}});;

				row++;
		}

		return authorGrid;


	}

	List<Filters> getCustomFilters(){

		List<Filters> filters = Filters.getFiltersByType("A");
		/*build author list for Author Filter
		displayAuthorsList = buildAuthorsList(displayPubList);
		System.out.println("Authors List = "+displayAuthorsList.toString());
		for(Filters f:filters){
			if("A".equalsIgnoreCase(f.getFilterType())){
				f.setFilterContent(Filters.buildAuthorFilter(displayAuthorsList));
			}
		}*/
		return filters;
	}

	GridPane buildFiltersGrid(List<Filters> filters){

		GridPane filtersGrid = new GridPane();
		Text text = new Text("Filter Results by : ");
		text.setStyle("-fx-border-weight:3pt");
		filtersGrid.add(text, 1, 0, 5, 1);
		filtersGrid.setId("filtersGrid");
		int count = 1;
		
		for(int i=0;i<filters.size();i++){
			System.out.println("filterContentSize - "+filters.get(i).getFilterName()+" - "+filters.get(i).getFilterContent()+" - "+filters.get(i).getFilterType());
			if(filters.get(i).getFilterContent()!=null){
				Text filterTitle = new Text(filters.get(i).getFilterName());

				filterTitle.setUnderline(true);
				filtersGrid.add(filterTitle, 1, count++);
				
				if("YEAR".equalsIgnoreCase(filters.get(i).getFilterType())){
					if("YEAR".equalsIgnoreCase(filters.get(i).getFilterName()))
					{
						
					}
					else
					{
						
					}

					HBox yearBox = new HBox();
					for(int j=0;j<filters.get(i).getFilterContent().size();j++){

						//filtersGrid.add((Node) filters.get(i).getFilterContent().get(j), j+1, count);
						yearBox.getChildren().add((Node) filters.get(i).getFilterContent().get(j));

					}
					filtersGrid.add(yearBox, 1, count);
					count++;


				}

				else if(("CHECKBOX_C".equalsIgnoreCase(filters.get(i).getFilterType())) 
						|| "CHECKBOX_J".equalsIgnoreCase(filters.get(i).getFilterType())){

					int colCheck=1;
					if(filters.get(i).getFilterContent()!=null)
					{
						for(int j=0;j<filters.get(i).getFilterContent().size();j++)
						{
							System.out.println("Inside conference chechbox");
							filtersGrid.add((Node) filters.get(i).getFilterContent().get(j), colCheck, count, 3, 1);
							count++;
						}//end of for
					}//end of inner if
				}//end of outer if
			}



		}



		Button apply = new Button("Filter");
		apply.setBackground(Background.EMPTY);
		apply.setMaxHeight(10);
		apply.setMaxWidth(60);
		apply.getStyleClass().add("apply");
		apply.setId("apply");
		
		apply.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Apply Filters!");
				int cFlag = 0, jFlag = 0, commFlag = 0;
				int[] yc = null, yp = null;

				String confNames="", journalNames="";
				//iterate thru filters
				for(Filters f : filters){
					if("Year".equalsIgnoreCase(f.getFilterType())){
						if("YEAR".equalsIgnoreCase(f.getFilterName()))
						{
							TextField y1,y2;

							y1 = ((TextField)f.getFilterContent().get(0));
							y2 = ((TextField)f.getFilterContent().get(2));
							yp = new int[2];
							String regex = "[0-9]*";
							
							if(y1.getText().matches(regex)||y2.getText().matches(regex))
							{
							yp[0] = Integer.parseInt(y1.getText());
							yp[1] = Integer.parseInt(y2.getText());}}
							
							else{
								TextField yc1,yc2;
								yc1 = ((TextField)f.getFilterContent().get(0));
								yc2 = ((TextField)f.getFilterContent().get(2));

								yc = new int[2];
								String regex = "[0-9]+";
								if(yc1.getText().matches(regex)||yc2.getText().matches(regex))
								{
									yc[0] = Integer.parseInt(yc1.getText());
								yc[1] = Integer.parseInt(yc2.getText());
								commFlag = 1;}

							}

						}
					if(("CHECKBOX_C".equalsIgnoreCase(f.getFilterType())) 
							|| "CHECKBOX_J".equalsIgnoreCase(f.getFilterType())){
						
						if("CHECKBOX_C".equalsIgnoreCase(f.getFilterType())){
							for(Object o : f.getFilterContent()){
								CheckBox c = (CheckBox)o;
								if(c.isSelected()){
									confNames += c.getId()+";";
									cFlag = 1;
								}
							}
						}
						if("CHECKBOX_J".equalsIgnoreCase(f.getFilterType())){
							for(Object o : f.getFilterContent()){
								CheckBox c = (CheckBox)o;
								if(c.isSelected()){
									journalNames += c.getId()+";";
									jFlag = 1;
								}
							}
						}
						
					}
				}

				filteredAuthorList = applyAllFilters(yp, yc, cFlag, jFlag, confNames, journalNames,commFlag);
				rebuildAuthorsGrid(filteredAuthorList);
				
			}
		});

		filtersGrid.add(apply, 1, count);

		return filtersGrid;
	}

	static List<AuthorMO> applyAllFilters(int[] yp, int [] yc, 
			int cFlag, int jFlag,String confNames,String journalNames,int commFlag){
		QueryEngine qe = new QueryEngine();
		List<AuthorMO> filteredAuthorList = qe.getFilteredAuthorList(yp, yc, cFlag, jFlag, confNames, journalNames,commFlag);
		System.out.println(filteredAuthorList.toString());
		return filteredAuthorList;
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