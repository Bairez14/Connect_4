import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameLogic {
	private ArrayList<GameButton> playerMoves;

	GameLogic() {
		playerMoves = new ArrayList<GameButton>();
	}

	public boolean winVertical(GameButton[][] buttons, GameButton b1) {
		int counter = 1;
		int p = b1.getPlayer();
		if (b1.getRow() > 2) {
			return false;
		} else {
			while ((counter < 4)) {
				// checks if it's the same player 4 spaces, else returns false
				if ((buttons[b1.getCol()][b1.getRow() + counter].getPlayer() != p)) {
					counter = 0; // reset counter
					return false;
				}
				counter++;
			}
			System.out.println("Vertical win!!!");
			return true;
		}
	}

	public boolean winHorizontal(GameButton[][] buttons, GameButton b1) {
		int counter = 0;
		int p = b1.getPlayer();
		for (int c = 0; c < 7; c++) {
			if (buttons[c][b1.getRow()].getPlayer() == p) {
				counter++;
			} else {
				counter = 0;
			}

			if (counter == 4) {
				System.out.println("Horizontal win!!!");
				return true;
			}
		}
		return false;
	}

	public boolean winDiagonalUp(GameButton[][] buttons, GameButton b1) {
		GameButton temp = b1;
		GameButton start = diagonalHelper1(temp);
		int counter = 0;
		int p = b1.getPlayer(); // keeps track of player

		if (start.getRow() > 2) { // diagonal win can only happen when the row is greater than 2
			return false;
		} else {
			if (buttons[start.getCol()][start.getRow()].getPlayer() == p) {
				counter++;
			}
			while ((start.getRow() != 5) && (start.getCol() != 5)) {
				// System.out.println("(" + start.getCol() + ", " + start.getRow() + ")");
				start.setCol(start.getCol() + 1);
				start.setRow(start.getRow() + 1);
				if (buttons[start.getCol()][start.getRow()].getPlayer() == p) {
					counter++;
				} else {
					counter = 0;
				}
				// System.out.println("counter: " + counter);
				if (counter == 4) {
					System.out.println("Diagonal Up win!!!");
					return true;
				}
			}
		}
		return false;
	}

	public boolean winDiagonalDown(GameButton[][] buttons, GameButton b1) {
		GameButton temp = b1;
		GameButton start = diagonalHelper2(temp);
		int counter = 0;
		int p = b1.getPlayer(); // keeps track of player

		System.out.println("(" + temp.getCol() + ", " + temp.getRow() + ")");

		if (start.getRow() > 2) { // diagonal win can only happen when the row is greater than 2
			return false;
		} else {
			if (buttons[start.getCol()][start.getRow()].getPlayer() == p) { // checks first case
				counter++;
			}
			while ((start.getCol() != 1) && (start.getRow() != 5)) {
				System.out.println("(" + start.getCol() + ", " + start.getRow() + ")");
				start.setCol(start.getCol() - 1);
				start.setRow(start.getRow() + 1);
				if (buttons[start.getCol()][start.getRow()].getPlayer() == p) {
					counter++;
				} else {
					counter = 0;
				}
				System.out.println("counter: " + counter);

				if (counter == 4) {
					System.out.println("Diagonal Down win!!!");
					return true;
				}
			}
		}
		return false;
	}

	// helper function for winDiagonalUP
	// Sets pointer to left most diagonal place
	public GameButton diagonalHelper1(GameButton b1) {
		while (b1.getCol() != 0 && b1.getRow() != 0) {
			// System.out.println("(" + b1.getCol() + ", " + b1.getRow() + ")");
			b1.setCol(b1.getCol() - 1);
			b1.setRow(b1.getRow() - 1);
		}
		return b1;
	}

	// helper function for winDiagonalDown
	// Sets pointer to right most diagonal place
	public GameButton diagonalHelper2(GameButton b1) {
		while (b1.getCol() != 6 && b1.getRow() != 0) {
			// System.out.println("(" + b1.getCol() + ", " + b1.getRow() + ")");
			b1.setCol(b1.getCol() + 1);
			b1.setRow(b1.getRow() - 1);
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
		// Was testing one type of win at a time

		if (winVertical(buttons, b1) || winHorizontal(buttons, b1) || winDiagonalDown(buttons, b1)
				|| winDiagonalUp(buttons, b1) || gameTie(buttons)) {
			System.out.println("WINNER");
		} else {
			System.out.println("Keep going");
		}
	}

}