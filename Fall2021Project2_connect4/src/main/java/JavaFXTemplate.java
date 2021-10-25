import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.*;
import java.util.*;
import java.time.Duration; //Time import
import java.time.LocalDateTime;

public class JavaFXTemplate extends Application {
	private MenuBar menu;
	public int count = 1;
	GameLogic game = new GameLogic();
	HashMap<String, Scene> sceneMap;
	// 2D array that holds all the buttons
	public GameButton[][] buttons = new GameButton[7][6];

	public static void main(String[] args) {
		launch(args);
	}

	// feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Welcome to Connect Four!");
		//startingScene(primaryStage);
		ObservableList<String> stats = FXCollections.observableArrayList();
		//ObservableList<String> turn = FXCollections.observableArrayList();
		TextField turns = new TextField();
		turns.setEditable(false);

		// turnList and gameStatus
		ListView<String> gameStats = new ListView<String>(stats);
		//ListView<String> turnList = new ListView<String>(turn);
		// turnList.setPrefWidth(500);
		// turnList.setPrefHeight(100);
		// turnList.setOrientation(Orientation.HORIZONTAL);
		turns.setAlignment(Pos.BOTTOM_CENTER);
		turns.setText("Player 1's turn");

		// Menu bar
		menu = new MenuBar();
		Menu mOne = new Menu("Options"); // a menu goes inside a menu bar
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
		addGrid(grid, turns, stats);

		// HBox for turnList
		HBox turnBox = new HBox(turns);
		turnBox.setAlignment(Pos.BOTTOM_CENTER);

		// HBox
		HBox layout = new HBox(grid, gameStats);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(50);

		// creates the 3 themes and stores in a map
		//sceneMap.put("theme1", setTheme1(layout, turn, stats));
		//sceneMap.put("theme2", setTheme2(layout, turn, stats));
		//sceneMap.put("theme3", setTheme3(layout, turn, stats));

		// EventHandlers
		iOne.setOnAction(e -> resetGame(turns, grid, stats));
		iTwo.setOnAction(e -> System.exit(0));
		iThree.setOnAction(e -> howToWindow());
		reverse.setOnAction(e -> undo(game.getPlayerMoves(), turns, stats));
		theme1.setOnAction(e-> primaryStage.setScene(sceneMap.get("theme1")));
		theme2.setOnAction(e -> primaryStage.setScene(sceneMap.get("theme2")));
		theme3.setOnAction(e -> primaryStage.setScene(sceneMap.get("theme3")));
		
		// Scene
		Scene scene = new Scene(new VBox(40, menu, layout, turnBox), 1000, 1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void resetGame(TextField turns, GridPane grid, ObservableList<String> stats) {
		for (Node n : grid.getChildren()) {
			GameButton temp = (GameButton) n;
			temp.setText("");
			temp.setDisable(false);
			temp.setStyle("-fx-background-color: lightBlue;" + "-fx-border-color: black;");
		}
		//turns.setText("");
		stats.clear();
		turns.setText("Player 1 goes first");

		count = 1;
	}

	public void checkTurn(GridPane grid, TextField turns, GameButton b1, ObservableList<String> stats) {
		// System.out.println("button pressed: " + ((Button)e.getSource()).getText());
		if (count % 2 == 0) {
			turns.setText("Player 1's turn");
			b1.setPlayer(2);

		} else {
			turns.setText("Player 2's turn");
			b1.setPlayer(1);
		}
		if (validMove(grid, b1, stats) == true) {
			b1.setClicked(true);
			b1.setDisable(true);
			count++;
			game.win(buttons, b1, stats);
			if(game.win(buttons, b1, stats)){ 	
				Duration duration = Duration.ofSeconds(2);
				PauseTransition pause = new PauseTransition(duration);
				 // starts the pause
				pause.setOnFinished(e -> winnerWindow(b1));
				pause.play();
				//winderWindow(b1);
			}      
			game.getPlayerMoves().add(b1); 

		}
	}
	
	public GameButton undo(ArrayList<GameButton> playerMoves,TextField turns, ObservableList<String> stats) {
		GameButton b1 = playerMoves.get(playerMoves.size() - 1);
		stats.add("Player " + b1.getPlayer() + " undid a move. Make a move!");
		turns.setText("player " + b1.getPlayer() + "'s turn");
		playerMoves.get(playerMoves.size() - 1).setDisable(false);
		playerMoves.get(playerMoves.size() - 1).setClicked(false);
		playerMoves.get(playerMoves.size() - 1).setPlayer(0);
		playerMoves.get(playerMoves.size() - 1).setStyle("-fx-background-color: lightBlue;" + "-fx-border-color: black;");
		playerMoves.remove(playerMoves.size() - 1);
		count--;

		return playerMoves.get(playerMoves.size() - 1);
	}

	// checks if button pressed is a valid move in game
	private boolean validMove(GridPane grid, GameButton b1, ObservableList<String> stats) {
		if (b1.getRow() == 5) { // allows only bottom row to be clicked at start of game
			//b1.setClicked(true);
			setPlayerColor(b1, grid);
			return true;
		} else if (buttons[b1.getCol()][b1.getRow() + 1].getClicked() == true) { // checks if button underneath is pressed
			//b1.setClicked(true);
			setPlayerColor(b1, grid);
			return true;
		}
		return false;
	}

	public void setPlayerColor(GameButton b1, GridPane grid) {
		if (b1.getPlayer() == 1) {
			b1.setStyle("-fx-background-color: Red");
		} else if (b1.getPlayer() == 2) {
			b1.setStyle("-fx-background-color: Yellow");
		}
	}

	public void addGrid(GridPane grid, TextField turns, ObservableList<String> stats) {
		for (int x = 0; x < 7; x++) { // column
			for (int i = 0; i < 6; i++) { // row
				GameButton b1 = new GameButton(x, i);
				buttons[x][i] = b1; // populates buttons to 2d array of buttons
				b1.setPrefSize(90, 90);
				b1.setOnAction(e -> checkTurn(grid, turns, b1, stats));
				b1.setStyle("-fx-background-color: lightBlue;" + "-fx-border-color: black;");
				grid.add(b1, x, i); // adding buttons to grid
			}
		}
	}

	// public Scene setTheme1(HBox layout, ObservableList<String> turn, ObservableList<String> stats) {
	// 	GridPane grid = new GridPane();
	// 	grid.setAlignment(Pos.CENTER);
	// 	grid.setHgap(10);
	// 	grid.setVgap(10);
	// 	addGrid(grid, turn, stats);
	// 	return new Scene(layout, 1000, 1000);
	// }

	// public Scene setTheme2(HBox layout, ObservableList<String> turn, ObservableList<String> stats){
	// 	GridPane grid = new GridPane();
	// 	grid.setAlignment(Pos.CENTER);
	// 	grid.setHgap(10);
	// 	grid.setVgap(10);
	// 	addGrid(grid, turn, stats);
	// 	return new Scene(layout, 1000, 1000);
	// }

	// //Original default theme
	// public Scene setTheme3( ObservableList<String> turn, ObservableList<String> stats){ 
	// 	return new Scene(layout, 1000, 1000);
	// }

	// //Welcome scene
	// public void startingScene(Scene primaryStage){ 
	// 	//primaryStage.setTitle("Welcome to Connect Four1");
		
	// }
	public void winnerWindow(GameButton b1) {
		Stage newStage = new Stage();
		VBox box = new VBox();
		Button newGame = new Button("New Game");
		Button exit = new Button("Exit");
		TextField winner = new TextField("Player "+ b1.getPlayer()+ " won!");
		//need to also list if it is a tie

		
		box.getChildren().add(winner);

		Scene stageScene = new Scene(box, 600, 600);
		newStage.setScene(stageScene);
		newStage.show();
	}

	public void howToWindow(){
		Stage newStage = new Stage();
		newStage.setTitle("How to Play");
		VBox box = new VBox();
		TextField instructions = new TextField("Welcome to Cennoct Four!\n This is a two player game.\n The goal"
			+ "is to try to build a row of four boxes while keeping your opponent from doing the same. Players will alternate "
			+ "turns after selecting a button until a player selects 4 boxes in a row be in horixontally, vertical or diagonal. ");
		
		instructions.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
		instructions.setAlignment(Pos.CENTER);
		box.getChildren().add(instructions);

		Scene stageScene = new Scene(box, 600, 600);
		newStage.setScene(stageScene);
		newStage.show();
	}

}