
package com.msd.layout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HomeScreen extends Application {
	
	KeywordSearch showSearchResults ;
	TextField userTextField = new TextField();
	AuthorSearchScreen authorSearch;
	ConferenceSearchScreen confSearch;
	JournalSearchScreen journalSearch;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	showSearchResults = new KeywordSearch();
    	confSearch = new ConferenceSearchScreen();
    	journalSearch = new JournalSearchScreen();
    	authorSearch = new AuthorSearchScreen();
    	
    	
        primaryStage.setTitle("Welcome to Reviewer Finder");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Enter keywords : ");
        scenetitle.setId("welcome-text");
        HBox hBox = new HBox();
        hBox.getChildren().add(scenetitle);
        hBox.setAlignment(Pos.CENTER);
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle, 0, 0, 3, 1);
        

        userTextField.setPrefHeight(70);
        grid.add(userTextField, 1, 1, 2, 1);

        
        Button btn = new Button("");
        btn.setId("searchBtn");
        btn.getStyleClass().add("card");
        btn.setMaxWidth(50);
        HBox hbBtn = new HBox();
        //hbBtn.setAlignment(Pos.CENTER);
        hbBtn.setMaxWidth(200);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 1, 1, 1);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        actiontarget.setId("actiontarget");
        
        
        
        Button conference = new Button("");
        conference.setId("card");
        conference.setText("Search Conferences");
        conference.getStyleClass().add("round-blue");
        grid.add(conference, 1, 4);
        
        Button journal = new Button("");
        journal.setId("card");
        journal.setText("Search Journals");
        journal.getStyleClass().add("round-blue");
        
        grid.add(journal, 2, 4);
        
        Button author = new Button("");
        author.setId("card");
        author.setText("Search Authors");
        author.getStyleClass().add("round-blue");
        
        grid.add(author, 3, 4);
        
        conference.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                 System.out.println("Inside Conference Click Handler() ");
                 primaryStage.close();
                 confSearch.start(new Stage());
                
            }
        });
        
        journal.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                 System.out.println("Inside Journal Click Handler() ");
                 primaryStage.close();
                 journalSearch.start(new Stage());
                
            }
        });
        
        author.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                 System.out.println("Inside Author Click Handler() ");
                 primaryStage.close();
                 authorSearch.start(new Stage());
                
            }
        });
        
        
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                 System.out.println("Inside Search Button Click Handler() 5");
                 primaryStage.close();
                 showSearchResults.start(new Stage(), userTextField.getText().trim());
                
            }
        });


        Scene scene = new Scene(grid, 700, 700);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(HomeScreen.class.getResource("/resources/HomeScreen.css").toExternalForm());
        primaryStage.show();
    }
}