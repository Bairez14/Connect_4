import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import javafx.scene.image.WritableImage;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class JavaFXTemplate extends Application {
	private MenuBar menu;
	public int count = 1;
	GameLogic game = new GameLogic();
	//HashMap<String, Scene> sceneMap;
	ObservableList<String> stats;
	GridPane grid;
	TextField turns;
	// 2D array that holds all the buttons
	public GameButton[][] buttons = new GameButton[7][6];

	public static void main(String[] args) {
		launch(args);
	}

	// feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Welcome to Connect Four!");
		Button begin = new Button("Start");
		BorderPane pane = new BorderPane();
		VBox cajita = new VBox(begin);
		cajita.setAlignment(Pos.BOTTOM_CENTER);
		pane.setPadding(new Insets(70));
		pane.setBottom(cajita);

		Scene startScene = new Scene(new VBox(40, cajita), 1000, 1000);
		startScene.getRoot().setStyle("-fx-background-image: url(https://ppld.librarymarket.com/sites/default/files/2019-01/retro%20video%20game%20image.jpg?auto=format&fit=max&w=2000);");
		primaryStage.setScene(startScene);
		
		begin.setOnAction(e -> primaryStage.setScene(gamingScene(startScene, primaryStage)));

		primaryStage.show();
	}

	public void resetGame(TextField turns, GridPane grid, ObservableList<String> stats) {
		for (Node n : grid.getChildren()) {
			GameButton temp = (GameButton) n;
			temp.setText("");
			temp.setDisable(false);
			temp.setStyle("-fx-background-color: transparent;" + "-fx-border-color: white;");
		}
		// turns.setText("");
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
			if (game.win(buttons, b1, stats)) {
				//highlight(game.getWinningButtons());
				PauseTransition pause = new PauseTransition(Duration.seconds(3));
				pause.setOnFinished(e -> winnerWindow(b1));
				pause.play();
			} else if(game.getTie() == true){
				PauseTransition pause = new PauseTransition(Duration.seconds(2));
				pause.setOnFinished(e -> winnerWindow(b1));
				pause.play();
			}
			game.getPlayerMoves().add(b1);

		} else {
			stats.add("Player " + b1.getPlayer() + "'s move: (" + b1.getCol() + ", " + b1.getRow() + ") is invalid. Try again");
		}
	}

	public void highlight(ArrayList<GameButton> winningButtons){
		for(int i = 0; i < 4; i++){
			winningButtons.get(i).setStyle("-fx-background-color: black;");
		}	 
	}

	public GameButton undo(ArrayList<GameButton> playerMoves, TextField turns, ObservableList<String> stats) {
		GameButton b1 = playerMoves.get(playerMoves.size() - 1);
		stats.add("Player " + b1.getPlayer() + " undid a move. Make a move!");
		turns.setText("player " + b1.getPlayer() + "'s turn");
		playerMoves.get(playerMoves.size() - 1).setDisable(false);
		playerMoves.get(playerMoves.size() - 1).setClicked(false);
		playerMoves.get(playerMoves.size() - 1).setPlayer(0);
		playerMoves.get(playerMoves.size() - 1)
				.setStyle("-fx-background-color: transparent;" + "-fx-border-color: white;");
		playerMoves.remove(playerMoves.size() - 1);
		count--;

		return playerMoves.get(playerMoves.size() - 1);
	}

	// checks if button pressed is a valid move in game
	private boolean validMove(GridPane grid, GameButton b1, ObservableList<String> stats) {
		if (b1.getRow() == 5) { // allows only bottom row to be clicked at start of game
			// b1.setClicked(true);
			setPlayerColor(b1, grid);
			return true;
		} else if (buttons[b1.getCol()][b1.getRow() + 1].getClicked() == true) { // checks if button underneath is
																					// pressed
			// b1.setClicked(true);
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
				b1.setStyle("-fx-background-color: transparent;" + "-fx-border-color: white;");
				grid.add(b1, x, i); // adding buttons to grid
			}
		}
	}

	public Scene gamingScene(Scene scene2, Stage primaryStage){
		stats = FXCollections.observableArrayList();
		// ObservableList<String> turn = FXCollections.observableArrayList();
		turns = new TextField();
		turns.setEditable(false);

		// turns and gameStatus
		ListView<String> gameStats = new ListView<String>(stats);
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
		MenuItem theme3 = new MenuItem("Default");
		MenuItem reverse = new MenuItem("Reverse Move");
		menu.getMenus().addAll(mOne, mTwo, mThree);
		mOne.getItems().addAll(iOne, iTwo, iThree);
		mTwo.getItems().addAll(theme1, theme2, theme3);
		mThree.getItems().addAll(reverse);

		// GridPane
		grid = new GridPane();
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

		// creates the scene
		Scene scene = new Scene(new VBox(40, menu, layout, turnBox), 1000, 1000);
		scene.getRoot().setStyle("-fx-background-color: black;");
		primaryStage.setScene(scene);

		// EventHandlers
		iOne.setOnAction(e -> resetGame(turns, grid, stats));
		iTwo.setOnAction(e -> System.exit(0));
		iThree.setOnAction(e -> howToWindow());
		reverse.setOnAction(e -> undo(game.getPlayerMoves(), turns, stats));
		theme1.setOnAction(e -> primaryStage.setScene(setTheme1(scene)));
		theme2.setOnAction(e -> primaryStage.setScene(setTheme2(scene)));
		theme3.setOnAction(e -> primaryStage.setScene(setTheme3(scene)));
		return scene;
	}

	public Scene setTheme1(Scene scene) {
		Image image = new Image(
				"https://www.teahub.io/photos/full/203-2033195_hd-wallpaper-chicago-desktop-backgrounds.jpg", 1000, 1000, false, false);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		scene.getRoot().setStyle("-fx-background-image: url(https://www.teahub.io/photos/full/203-2033195_hd-wallpaper-chicago-desktop-backgrounds.jpg);");
		return scene;
		// https://www.teahub.io/photos/full/203-2033195_hd-wallpaper-chicago-desktop-backgrounds.jpg
	}

	public Scene setTheme2(Scene scene) {
		scene.getRoot().setStyle("-fx-background-image: url(https://assets.bigcartel.com/product_images/173492974/Kanye_Bear.jpeg?auto=format&fit=max&w=max);");

		return scene;
	}

	//Original default theme
	public Scene setTheme3(Scene scene) {
		scene.getRoot().setStyle("-fx-background-color: black;");

		return scene;
	}


	public void winnerWindow(GameButton b1) {
		Stage newStage = new Stage();
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(200));
		VBox box = new VBox();
		Button newGame = new Button("Play Again");
		Button exit = new Button("Exit");
		String ganador;

		if(game.getTie() == true){
			ganador = ("Game is a tie");
		} else {
			ganador = ("Player " + b1.getPlayer() + " won!");
		}

		TextField winner = new TextField(ganador);
		winner.setPrefWidth(450);
		winner.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
		winner.setEditable(false);
		winner.setFocusTraversable(false);
		
		VBox botones = new VBox(newGame, exit);
		VBox.setMargin(newGame, new Insets(20));
		botones.setAlignment(Pos.BOTTOM_CENTER);
		box.getChildren().add(winner);

		exit.setOnAction(e-> System.exit(0));
		newGame.setOnAction(e-> {resetGame(turns, grid, stats); newStage.close();});
		pane.setTop(box);
		pane.setCenter(botones);

		Scene stageScene = new Scene(pane, 600, 600);
		stageScene.getRoot().setStyle("-fx-font-family: 'Dekko';" + "-fx-background-image: url(https://c.tenor.com/s9yLYzDwk78AAAAC/%E3%81%8A%E3%82%81%E3%81%A7%E3%81%A8%E3%81%86-%E5%AC%89%E3%81%97%E3%81%84.gif?auto=format&fit=600&w=600);");//?auto=format&fit=max&w=max
		newStage.setScene(stageScene);
		newStage.show();	
	}

	public void howToWindow() {
		Stage newStage = new Stage();

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));

		newStage.setTitle("How to Play");
		VBox box = new VBox();
		String instructions = ("Welcome to Connect Four!\nThis is a two player game.\nThe goal"
				+ " is to try to build a row of four boxes while keeping your opponent from doing the same.\nPlayers will alternate "
				+ "turns after selecting a button until a player selects 4 boxes in a row be it horixontally, vertically or in a diagonal. ");
		Label label = new Label(instructions);
		label.setWrapText(true);
		label.setTextFill(Color.WHITE);
		label.setFont(Font.font("Verdana", 22));
		label.setPrefWidth(500);
		label.setPrefHeight(500);
		box.getChildren().add(label);
		box.setAlignment(Pos.CENTER);
		pane.setCenter(box);

		Scene stageScene = new Scene(pane, 500, 630);
		stageScene.getRoot().setStyle("-fx-background-image: url(https://i.pinimg.com/originals/21/ce/35/21ce35c56f6506f628c9eef26d1d462d.gif?auto=format&fit=max&w=max);");//?auto=format&fit=max&w=max

		//
		newStage.setScene(stageScene);
		newStage.show();
	}
}