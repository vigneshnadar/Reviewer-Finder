
package com.msd.layout;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
public class AuthorScreen extends Application  {

	GridPane grid = new GridPane();
	public Scene scene = new Scene(grid, 500, 500);
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

	
	AuthorScreen(String auth){

		text = auth;

		System.out.println("Init");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		scenetitle.setText(auth);
		scenetitle.setId("welcome-text");
		scenetitle.setTextAlignment(TextAlignment.CENTER);
		grid.add(scenetitle, 0, 0, 2, 1);

		PublicationMO p = new PublicationMO();

		pubList = p.getAuthorPublications(auth);
		if(pubList.size() > 10)
			pubList = pubList.subList(0,10);

		
		System.out.println("pubList fetched - size - "+pubList.size());
		displayPubList = new ArrayList<PublicationMO>(pubList);
		System.out.println("pubList now - size - "+pubList.size());
		
		GridPane pubGrid = buildPubListView(displayPubList);
		pubGrid.setId("pubGrid");
		pubGrid.setVgap(10);

		grid.add(pubGrid, 0, 1, 2, 1);
		
		Button similarAuthor = new Button("");
		similarAuthor.setId("similarAuth");
		similarAuthor.setText("Find Similar Authors");
        grid.add(similarAuthor, 0, 2);

		/* Add filters */
		filters = getCustomFilters();
		GridPane filtersGrid = buildFiltersGrid(filters);
		grid.add(filtersGrid, 2, 1);
		
		similarAuthor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                 System.out.println("Inside Similar Author Click Handler() ");
                 //primaryStage.close();

                 showResults(auth);
                 //confSearch.start(new Stage());
                
            }
        });


		scene.getStylesheets().add(ConferenceScreen.class.getResource("/resources/HomeScreen.css").toExternalForm());

		System.out.println("Set Scene for Results Screen");

	}
	
	
	
	void showResults(String text){
		AuthorMO c = new AuthorMO();

		List<AuthorMO> list = c.getSimilarAuthors(text);

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
			
			for(AuthorMO aut:list){
				GridPane subGrid = new GridPane();
				subGrid.setHgap(10);
				Text confName = new Text(aut.getAuthorName());
				Text confAcronym = new Text("(OOPSLA)");
				subGrid.add(confName, 0, row);
				//subGrid.add(confAcronym, 1, row);
				confGrid.add(subGrid, 0, row);
				
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
			
			grid.add(confGrid, 0, 3, 4, 1);
			
			
			
		} }

	static GridPane buildPubListView(List<PublicationMO> pubList){
		GridPane pubGrid = new GridPane();
		int row = 0;
		String pubDetails="";
		
		
			for(PublicationMO P:pubList){
				GridPane subGrid = new GridPane();
				subGrid.setHgap(10);
				
				Text pubTitle;
				Text pubVenue;
				
				if(P.getName().length()>29)
					{ pubDetails = P.getName().substring(0, 30)+"...";}
				
				else { pubDetails = P.getName();}
				
				pubDetails = pubDetails + " " + P.getVenue();
				pubTitle = new Text(pubDetails);
				
				//pubVenue = new Text(P.getVenue());
				pubTitle.setWrappingWidth(900);
				//pubVenue.setWrappingWidth(300);

				subGrid.add(pubTitle, 0, row,2,1);
				//subGrid.add(pubVenue, 1, row);
				pubGrid.add(subGrid, 0, row);


				row++;
			
		}
		return pubGrid;
	}

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Run Results - Author screen");
		this.primaryStage = primaryStage;


		this.backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Exit from Author Screen");
				HomeScreen homescreen = new HomeScreen();
				primaryStage.close();
				homescreen.start(new Stage());
			}
		});

		


		primaryStage.setTitle("Author - "+text);
		primaryStage.setScene(this.scene);
		primaryStage.show();
	}

	

	List<Filters> getCustomFilters(){

		List<Filters> filters = Filters.getFiltersByType("A");
		/*build author list for Author Filter*/
		displayAuthorsList = buildAuthorsList(displayPubList);
		System.out.println("Authors List = "+displayAuthorsList.toString());
		for(Filters f:filters){
			if("A".equalsIgnoreCase(f.getFilterType())){
				f.setFilterContent(Filters.buildAuthorFilter(displayAuthorsList));
			}
		}
		return filters;
	}

	HashMap<String, Integer> buildAuthorsList(List<PublicationMO> pubList){

		HashMap<String, Integer> authors = new HashMap<String, Integer>();
		for(PublicationMO p:pubList){
			System.out.println("pub name"+p.getName());
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
					TextField y1 = ((TextField)filters.get(i).getFilterContent().get(0));
					TextField y2 = ((TextField)filters.get(i).getFilterContent().get(2));
					
					HBox yearBox = new HBox();
					for(int j=0;j<filters.get(i).getFilterContent().size();j++){
						
						//filtersGrid.add((Node) filters.get(i).getFilterContent().get(j), j+1, count);
						yearBox.getChildren().add((Node) filters.get(i).getFilterContent().get(j));
						
					}
					filtersGrid.add(yearBox, 1, count);
					count++;
					
					
				}
				else if("A".equalsIgnoreCase(filters.get(i).getFilterType())){
					int col=1;
					
						if(filters.get(i).getFilterContent()!=null)
						{
					for(int j=0;j<filters.get(i).getFilterContent().size();j++)
					{
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
					}//end of for
				}//end of if
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

				//iterate thru filters
				for(Filters f : filters){
					if("Year".equalsIgnoreCase(f.getFilterType())){


						/*apply this filter to pubList*/
//						displayPubList = applyYearFilter("", pubList);
//						System.out.println("after applying - "+displayPubList.size());
//						
//						
//						rebuildView(displayPubList);
						
					}
				}


			}
		});

		filtersGrid.add(apply, 1, count);

		return filtersGrid;
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
	
	 List<PublicationMO> applyYearFilter(int year1, int year2, List<PublicationMO> list, List<Filters> filters){
			
			List<PublicationMO> pubList = Filters.applyYearFilter(year1, year2, list, filters);
			rebuildFiltersGrid(filters);
			return pubList;
		}
	 
	 
	 void rebuildFiltersGrid(List<Filters> list){
			
			System.out.println("list rec - "+list.size()); 
			GridPane filtersGrid = (GridPane)scene.lookup("#filtersGrid");
			grid.getChildren().remove(filtersGrid);
			if(list.size()>10)
				filtersGrid = buildFiltersGrid(list.subList(0, 10));
			else filtersGrid = buildFiltersGrid(list);
			filtersGrid.setId("filtersGrid");
			grid.add(filtersGrid, 2, 1, 2, 1);
		}

	static List<PublicationMO> applyAuthorFilter(String author, List<PublicationMO> list){
		
		List<PublicationMO> fList = new ArrayList<PublicationMO>();
		
		for(PublicationMO p : list){
			List<AuthorMO> aList = p.getAuthors();
			for(AuthorMO a : aList){
				if(author.equalsIgnoreCase(a.getAuthorName())){
					if(!fList.contains(p)){
						fList.add(p);
					}
					
				}
			}
		}
		System.out.println("filtered list size - "+fList.size());
		return fList;
		
	}

	static boolean isBetween(int year,  int year1, int year2){
		if(year>=year1 && year<=year2)
			return true;
		return false;
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