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
		int counter = 1;
		int p = b1.getPlayer();
		
		while (counter < 5) {
			// checks if it's the same player 4 spaces, else returns false
			if (buttons[b1.getCol()][b1.getRow() + counter].getPlayer() != p) {
				return false;
			}
			counter++;
		}
		System.out.println("Vertical win!!!");
		return true;
	}

	public boolean winHorizontal(GameButton[][] buttons, GameButton b1 ) {
		int counter = 0;
		for (int c = 0; c < 7; c++) {
			if (buttons[c][b1.getRow()].getPlayer() == b1.getPlayer()) {
				counter++;
			}
			if (counter == 4) {
				System.out.println("Horizontal win!!!");
				return true;
			}
			else {
				counter = 0;
			}
		}
		return false;
	}

	public boolean winDiagonalUp(GameButton[][] buttons, GameButton b1) {
		GameButton start = diagonalHelper1(b1);
		int counter = 0;
		int p = b1.getPlayer(); //keeps track of player
		
		while((start.getCol() != 6) || (start.getRow() != 5)) {
			start.setCol(start.getCol() + 1);
			start.setRow(start.getRow() + 1);
			if(buttons[start.col][start.row].getPlayer() == p) {
				counter++;
			}
			if (counter == 4) {
				System.out.println("Diagonal Up win!!!");
				return true;
			} else {
				counter = 0;
			}
		}
		return false;
	}
	
	public boolean winDiagonalDown(GameButton[][] buttons, GameButton b1) {
		GameButton start = diagonalHelper2(b1);
		int counter = 0;
		int p = b1.getPlayer(); // keeps track of player

		while ((start.getCol() != 6) || (start.getRow() != 5)) {
			start.setCol(start.getCol() - 1);
			start.setRow(start.getRow() + 1);
			if (buttons[start.col][start.row].getPlayer() == p) {
				counter++;
			}
			if (counter == 4) {
				System.out.println("Diagonal Down win!!!");
				return true;
			} else {
				counter = 0;
			}
		}
		return false;
	}

	public GameButton diagonalHelper1(GameButton b1) {
		while (b1.col != 0 || b1.row != 0) {
			b1.setCol(b1.col - 1);
			b1.setRow(b1.row - 1);
		}
		return b1;
	}

	public GameButton diagonalHelper2(GameButton b1){
		while (b1.col != 0 || b1.row != 0) {
			b1.setCol(b1.col - 1);
			b1.setRow(b1.row + 1);
		}
		return b1;
	}


	public boolean gameTie(GameButton[][] buttons) {
		for (int c = 0; c < 7; c++) { // column
			for (int r = 0; r < 6; r++) { // row
				if (buttons[c][r].player == 0) {
					return false;
				}
			}
		}
		System.out.println("TIE!!!!!");
		return true;
	}

	public void win(GameButton[][] buttons, GameButton b1) {
		if (winVertical(buttons, b1)
			|| winHorizontal(buttons, b1)
			|| winDiagonalDown(buttons, b1)
			|| winDiagonalUp(buttons, b1)
			|| gameTie(buttons)){
			System.out.println("WINNER");
		} else {
				System.out.println("Keep going");
			}
	}
	
}
