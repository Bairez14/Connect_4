import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private MenuBar menu;
	public int count = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Connect Four!");
		ObservableList<String> stats = FXCollections.observableArrayList();
		ObservableList<String> turn = FXCollections.observableArrayList();

		// turnList and gameStatus
		ListView<String> gameStats = new ListView<String>(stats);
		ListView<String> turnList = new ListView<String>(turn);
		turnList.setPrefWidth(500);
		turnList.setPrefHeight(100);
		turnList.setOrientation(Orientation.HORIZONTAL);
		turn.add("Player 1's turn");

		
		// Menu bar
		menu = new MenuBar();
		Menu mOne = new Menu("Options"); //a menu goes inside a menu bar
		Menu mTwo = new Menu("Themes");
		Menu mThree = new Menu("GamePlay");
		MenuItem iOne = new MenuItem("New Game");
		MenuItem iThree = new MenuItem("How To Play");
		MenuItem iTwo = new MenuItem("Exit");
		MenuItem theme1 = new MenuItem("Theme 1");
		MenuItem theme2 = new MenuItem("Theme 2");
		MenuItem theme3 = new MenuItem("Theme 3");
		MenuItem reverse = new MenuItem("Reverse Move");
		menu.getMenus().addAll(mOne, mTwo, mThree);
		mOne.getItems().addAll(iOne, iTwo, iThree);
		mTwo.getItems().addAll(theme1, theme2, theme3);
		mThree.getItems().addAll(reverse);
		

		
		// GridPane
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		addGrid(grid, turn);
		
		//HBox for turnList
		HBox turnBox = new HBox(turnList);
		turnBox.setAlignment(Pos.BOTTOM_CENTER);
		
		//HBox
		HBox layout = new HBox(grid, gameStats);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(50);
		
		//Scene 
		Scene scene = new Scene(new VBox(40, menu, layout, turnBox), 1000,1000);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//EventHandlers
		iOne.setOnAction(e-> resetGame(turn,grid));
		iTwo.setOnAction(e-> System.exit(0));
		
	}
	
	public void resetGame(ObservableList<String> turn, GridPane grid) {
		for(Node n: grid.getChildren()) {
			GameButton temp = (GameButton) n;
			temp.setText("");
			temp.setDisable(false);
		}
		turn.clear();
		turn.add("Player 1 goes first");
		count = 1;
	}
	
	public void checkTurn(ObservableList<String> turn, GameButton b1) {
		//System.out.println("button pressed: " + ((Button)e.getSource()).getText());
		if (count%2 == 0) {
			turn.add("Player 1's turn");
			
		} else {
			turn.add("Player 2's turn");
			
		}
		count++;
		b1.setDisable(validMove(b1, stats));
	}
	
	private boolean validMove(GameButton b1, ObservableList<String> stats) {
		
		return false;
	}

	public void addGrid(GridPane grid, ObservableList<String> turn) {
		
		for(int x = 0; x<7; x++) { // column
			for(int i = 0; i<6; i++) { // row
				GameButton b1 = new GameButton(x,i);
				b1.setPrefSize(100, 100);
				b1.setOnAction(e->checkTurn(turn, b1));
				b1.setStyle("-fx-background-color: lightBlue;" + "-fx-border-color: black;");
				grid.add(b1, x, i);
				 
			}
		}
}
	
}
