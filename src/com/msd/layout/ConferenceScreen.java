package com.msd.layout;

import javafx.scene.paint.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.msd.model.AuthorMO;
import com.msd.model.Filters;
import com.msd.model.PublicationMO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ConferenceScreen extends Application  {

	GridPane grid = new GridPane();
	public Scene scene = new Scene(grid, 900, 650);
	Button btn = new Button("");
	Button backButton = new Button("");
	Text scenetitle = new Text();
	TextField searchText = new TextField("");
	List<PublicationMO> pubList ;
	List<PublicationMO> displayPubList ;
	List<PublicationMO> filteredList ;
	List<AuthorMO> authorsList;
	HashMap<String, Integer> displayAuthorsList;
	Stage primaryStage;
	List<Filters> filters;
	String text;

	
	ConferenceScreen(String conf){

		//The constructor here sets the layout of the screen
		
		text = conf;

		System.out.println("Init");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(60);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		scenetitle.setText(conf);
		scenetitle.setId("welcome-text");
		scenetitle.setTextAlignment(TextAlignment.CENTER);
		grid.add(scenetitle, 0, 0, 2, 1);

		PublicationMO p = new PublicationMO();

		pubList = p.getMostRecentPublications(conf,"C");
		displayPubList = new ArrayList<PublicationMO>(pubList.subList(0,10));
		filteredList = new ArrayList<PublicationMO>(pubList);
		GridPane pubGrid = buildPubListView(displayPubList);
		pubGrid.setId("pubGrid");
		grid.add(pubGrid, 0, 1, 2, 1);

		/* Add filters */
		filters = getCustomFilters();
		GridPane filtersGrid = buildFiltersGrid(filters);
		grid.add(filtersGrid, 3, 1);
		scene.getStylesheets().add(ConferenceScreen.class.getResource("/resources/HomeScreen.css").toExternalForm());

		System.out.println("Set Scene for Results Screen");

	}

	static GridPane buildPubListView(List<PublicationMO> pubList){
		GridPane pubGrid = new GridPane();
		
		
		int row = 1;
		
		pubGrid.setVgap(10);
		
			for(PublicationMO P:pubList){
				GridPane subGrid = new GridPane();
				subGrid.setHgap(10);
				
				
				if(row==1){
					
					Text pTitle = new Text("Publication Title");
					pTitle.setWrappingWidth(250);
					pTitle.setTextAlignment(TextAlignment.CENTER);
					subGrid.add(pTitle, 0,row);
					
					Text pAuthors = new Text("Publication Authors");
					pAuthors.setWrappingWidth(200);
					pAuthors.setTextAlignment(TextAlignment.CENTER);
					
					subGrid.add(pAuthors, 1,row);
					
					Text pYear = new Text("Publication Year");
					pYear.setTextAlignment(TextAlignment.CENTER);
					
					subGrid.add(pYear, 2,row);
					//subGrid.setStyle("-fx-background-color: rgba(0,0,0,0.2);");
				}
				
				else{
				
				Text pubTitle;
				
				if(P.getName().length()>39)
					{ 
					pubTitle = new Text(P.getName().substring(0, 40)+"...");
					}
				
				else { pubTitle = new Text(P.getName());}
				
				pubTitle.setWrappingWidth(250);
				pubTitle.setTextAlignment(TextAlignment.CENTER);
				
				String authors="";
				
				for(AuthorMO a : P.getAuthors()){
					authors +=a.getAuthorName()+", ";
				}
				authors = authors.substring(0, authors.length()-2);
				subGrid.add(pubTitle, 0, row);
				Text allAuthors = new Text(authors);
				allAuthors.setWrappingWidth(200);
				allAuthors.setTextAlignment(TextAlignment.CENTER);
				subGrid.add(allAuthors, 1, row);
				
				Text pYear = new Text(P.getYear()+"");
				pYear.setTextAlignment(TextAlignment.CENTER);
				subGrid.add(pYear, 2, row);
				}
				pubGrid.add(subGrid, 0, row, 3, 1);
				pubGrid.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
				

				row++;
			
		}
		return pubGrid;
	}

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Run Results - Conf screen");
		this.primaryStage = primaryStage;


		this.backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Exit from Conference Screen");
				HomeScreen homescreen = new HomeScreen();
				primaryStage.close();
				homescreen.start(new Stage());
			}
		});

		


		primaryStage.setTitle("Conference - "+text);
		primaryStage.setScene(this.scene);
		primaryStage.show();
	}

	List<Filters> getCustomFilters(){

		List<Filters> filters = Filters.getFiltersByType("C");

		/*build author list for Author Filter*/
		displayAuthorsList = Filters.buildAuthorsList(pubList);
		System.out.println("Authors List = "+displayAuthorsList.toString());
		
		for(Filters f:filters){
			System.out.println("filterContent for "+f.getFilterName()+" is "+f.getFilterContent());
			if("A".equalsIgnoreCase(f.getFilterType())){
				f.setFilterContent(Filters.buildAuthorFilter(displayAuthorsList));
			}
		}
		return filters;
	}

	List<PublicationMO> applyYearFilter(int year1, int year2, List<PublicationMO> list, List<Filters> filters){
		
		List<PublicationMO> pubList = Filters.applyYearFilter(year1, year2, list, filters);
		rebuildFiltersGrid(filters);
		return pubList;
	}
	
	
	GridPane buildFiltersGrid(List<Filters> filters){

		GridPane filtersGrid = new GridPane();
		filtersGrid.setVgap(10);
		Text text = new Text("Filter Results by : ");
		text.setStyle("-fx-border-weight:3pt");
		filtersGrid.add(text, 1, 0, 5, 1);
		filtersGrid.setId("filtersGrid");
		int count = 1;
		
		for(int i=0;i<filters.size();i++){
			System.out.println("filterContentSize - "+filters.get(i).getFilterName()+" - "+filters.get(i).getFilterContent());
			if(filters.get(i).getFilterContent()!=null){
				Text filterTitle = new Text(filters.get(i).getFilterName());
				
				filterTitle.setUnderline(true);
				filtersGrid.add(filterTitle, 1, count++);
				
				if("YEAR".equalsIgnoreCase(filters.get(i).getFilterType())){
					TextField y1 = ((TextField)filters.get(i).getFilterContent().get(0));
					TextField y2 = ((TextField)filters.get(i).getFilterContent().get(2));
					
					HBox yearBox = new HBox();
					for(int j=0;j<filters.get(i).getFilterContent().size();j++){
						
						//filtersGrid.add((Node) filters.get(i).getFilterContent().get(j), j+1, count);
						yearBox.getChildren().add((Node) filters.get(i).getFilterContent().get(j));
						
					}
					filtersGrid.add(yearBox, 1, count);
					count++;
					
					Button apply = new Button("Filter by year");
					apply.setPrefHeight(20);
					apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
							
							@Override
							public void handle(MouseEvent event) {
								/*rebuild your pubList & the view*/
								int year1 = Integer.parseInt(y1.getText());
								int year2 = Integer.parseInt(y2.getText());
								filteredList = applyYearFilter(year1, year2, pubList, filters);
								rebuildView(filteredList);
							}});
					
					filtersGrid.add(apply, 1, count++);
					
				}
				else if("A".equalsIgnoreCase(filters.get(i).getFilterType())){
					int col=1;
					for(int j=0;j<5;j++){
						filtersGrid.add((Text) filters.get(i).getFilterContent().get(j), col, count, 3, 1);
						String authorName = (String)((Text) filters.get(i).getFilterContent().get(j)).getText();
						String auth = authorName.substring(0, authorName.length()-3);
						System.out.println("auth - "+auth);
						((Text)filters.get(i).getFilterContent().get(j)).setOnMouseClicked(new EventHandler<MouseEvent>() {
							
							@Override
							public void handle(MouseEvent event) {
								/*rebuild your pubList & the view*/
								rebuildView(applyAuthorFilter(auth, filteredList));
							}});
						count++;
					}
					
				}
			}
			
		}
		
		
		
		Button apply = new Button("Filter");
		apply.setBackground(Background.EMPTY);
		apply.setMinSize(50, 30);
		apply.getStyleClass().add("apply");
		apply.setId("apply");
		apply.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Apply Filters!");

				//iterate thru filters
				for(Filters f : filters){
					if("Year".equalsIgnoreCase(f.getFilterType())){


						/*apply this filter to pubList
						displayPubList = applyYearFilter("", pubList);
						System.out.println("after applying - "+displayPubList.size());*/
						
						
						//rebuildView(displayPubList);
						
					}
				}


			}
		});

		//filtersGrid.add(apply, 1, count);

		return filtersGrid;
	}
	
	 void rebuildFiltersGrid(List<Filters> list){
		
		System.out.println("list rec - "+list.size()); 
		GridPane filtersGrid = (GridPane)scene.lookup("#filtersGrid");
		grid.getChildren().remove(filtersGrid);
		if(list.size()>10)
			filtersGrid = buildFiltersGrid(list.subList(0, 10));
		else filtersGrid = buildFiltersGrid(list);
		filtersGrid.setId("filtersGrid");
		grid.add(filtersGrid, 3, 1, 2, 1);
	}
	
	 void rebuildView(List<PublicationMO> list){
			
			System.out.println("list rec - "+list.size()); 
			GridPane pubGrid = (GridPane)scene.lookup("#pubGrid");
			grid.getChildren().remove(pubGrid);
			if(list.size()>10)
				pubGrid = buildPubListView(list.subList(0, 10));
			else pubGrid = buildPubListView(list);
			pubGrid.setId("pubGrid");
			grid.add(pubGrid, 0, 1, 2, 1);
		}
	 
	 

	static List<PublicationMO> applyAuthorFilter(String author, List<PublicationMO> list){
		
		System.out.println("Inside applyAuthorFilter - list - "+list.size());
		
		List<PublicationMO> fList = new ArrayList<PublicationMO>();
		for(PublicationMO p : list){
			List<AuthorMO> aList = p.getAuthors();
			for(AuthorMO a : aList){
				if(author.equalsIgnoreCase(a.getAuthorName())){
					System.out.println("Match with "+author);
					if(!fList.contains(p)){
						fList.add(p);
					}
					
				}
			}
		}
		System.out.println("filtered list size - "+fList.size());
		return fList;
		
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