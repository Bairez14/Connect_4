import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameLogic {
	private ArrayList<GameButton> playerMoves;

	GameLogic() {
		playerMoves = new ArrayList();
	}

	public boolean winVertical(GameButton[][] buttons, GameButton b1) {
		int counter = 0;
		int p = b1.getPlayer();
		
		while (counter < 4) {
			// checks if it's the same player 4 spaces, else returns false
			if (buttons[b1.getCol()][b1.getRow() + 1].getPlayer() != p) {
				return false;
			}
			counter++;
		}
		return true;
	}

	public boolean winHorizontal(int col, int row) {
		return true;
	}

	public boolean winDiagonal(int col, int row) {
		return true;
	}

	public boolean gameTie(GameButton[][] buttons) {
		for (int x = 0; x < 7; x++) { // column
			for (int i = 0; i < 6; i++) { // row
				if (buttons[x][i].player == 0) {
					return false;
				}
			}
		}
		System.out.println("TIE!!!!!");
		return true;
	}
	
}
