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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

import com.msd.model.*;
public class ConferenceSearchScreen extends Application  {

	GridPane grid = new GridPane();
	public Scene scene = new Scene(grid, 500, 500);
	Button btn = new Button("");
	Button backButton = new Button("");
	Text scenetitle = new Text("Search within Conferences");
	TextField searchText = new TextField("");
	Stage primaryStage;

	String text;

	Author authors;

	ConferenceSearchScreen(){


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
		
		searchText.setPrefHeight(50);
		searchText.setPrefWidth(250);
		searchText.setText(getText());
		subGrid.add(searchText, 1, 1);
		

		btn.setId("searchBtn");
		btn.setPrefSize(30, 50);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		subGrid.add(btn, 2, 1);
		
		backButton.setId("exitBtn");
		backButton.setText("Go Back");
		subGrid.add(backButton, 3, 1);
		
		grid.add(subGrid, 0, 1, 3, 1);
	
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


		primaryStage.setTitle("Search Results");
		primaryStage.setScene(this.scene);
		primaryStage.show();
	}

	void showResults(String text){
		ConferenceMO c = new ConferenceMO();

		List<ConferenceMO> list = c.getAllConferences(text);

		if(list.size()==0){
			scenetitle.setText("No results found!");
		}

		else { 
			scenetitle.setText(list.size()+" matches found!");

			/*building a table and adding it to View
			TableView<ConferenceMO> tableView = buildTable(list);
			grid.add(tableView, 1, 4);*/
			
			GridPane confGrid = new GridPane();
			confGrid.setVgap(10);
			int row = 1;
			Text title = new Text("CONFERENCE NAME");
			title.setId("titleHeader");
			confGrid.add(title, 0, 0, 2, 1);
			
			for(ConferenceMO conf:list){
				GridPane subGrid = new GridPane();
				subGrid.setHgap(10);
				Text confName = new Text(conf.getConferenceName());
				Text confAcronym = new Text("(OOPSLA)");
				subGrid.add(confName, 0, row);
				subGrid.add(confAcronym, 1, row);
				confGrid.add(subGrid, 0, row);
				
				subGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
					
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						System.out.println("You clicked on - "+subGrid.getChildren().get(0).toString());
						primaryStage.close();
						String conf = ((Text)subGrid.getChildren().get(0)).getText();
						ConferenceScreen c = new ConferenceScreen(conf);
						c.start(new Stage());
						
					}});;
				
				row++;
			}
			
			grid.add(confGrid, 1, 4, 4, 1);
			
			
			
		} 

	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	TableView<ConferenceMO> buildTable(List<ConferenceMO> list){
		ObservableList<ObservableList> data = FXCollections.observableArrayList();;
		TableView tableview = new TableView();
		TableColumn col = new TableColumn("Conference Id");
		col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
			public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
				return new SimpleStringProperty(param.getValue().get(0).toString());                        
			}                    
		});

		tableview.getColumns().add(col);
		col = new TableColumn("Conference Name");
		col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
			public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
				return new SimpleStringProperty(param.getValue().get(1).toString());                        
			}                    
		});

		tableview.getColumns().add(col);

		for(int i=0 ; i<list.size(); i++){
			//Iterate Column
			ObservableList<String> row = FXCollections.observableArrayList();

			row.add(list.get(i).getConferenceID());
			row.add(list.get(i).getConferenceName());
			data.add(row);

		}


		tableview.setItems(data);
		System.out.println("data: \n"+tableview.getItems());
		return tableview;
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