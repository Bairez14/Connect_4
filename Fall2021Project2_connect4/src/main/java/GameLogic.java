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

	public void setPlayerMoves(ArrayList<GameButton> moves){
		this.playerMoves = moves;
	}

	public ArrayList<GameButton> getPlayerMoves() {
		return playerMoves;
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
		int counter = 0;
		int p = b1.getPlayer(); // keeps track of player
		GameButton start = diagonalHelper1(temp, p);
		if (start.getRow() > 2) { // diagonal win can only happen when the row is greater than 2
			return false;
		} //else {
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
		//}
		return false;
	}

	public boolean winDiagonalDown(GameButton[][] buttons, GameButton b1) {
		int counter = 0;
		int traverse = 1;
		int p = b1.getPlayer();
		int r = b1.getRow();
		int c = b1.getCol();
		if (r < 5) {
			traverse = 1; // resetting travers to check upwards
			while (buttons[b1.getCol() + traverse][b1.getRow() - traverse].getPlayer() == p) {
				counter++;
				traverse++;
			}
			if (counter >= 3) {
				return true;
			}
		} else {
			//System.out.println("( " + (c - traverse) + ", " + (r + traverse) + ")");
			// if the last move was top upper rightmost
			while ((r + traverse <= 5) && (c - traverse <= 0)) {
				if (buttons[b1.getCol() - traverse][b1.getRow() + traverse].getPlayer() == p) {
					System.out.println("( " + (c - traverse) + ", " + (r + traverse) + ")");
					counter++;
					traverse++;
				}
			}
			System.out.println("counter: " + counter);
			if (counter >= 3) {
				return true;
			}

			traverse = 1; // resetting travers to check upwards
			while (buttons[b1.getCol() + traverse][b1.getRow() - traverse].getPlayer() == p) {
				counter++;
				traverse++;
			}
			if (counter >= 3) {
				return true;
			}
		}
			// last move was left downward most
			// if (counter == 0) {
			// 	if (buttons[c + 1][r - 1].getPlayer() == p && buttons[c + 2][r - 2].getPlayer() == p 
			// 			&& buttons[c + 3][r - 3].getPlayer() == p) {
			// 		return true;
			// 	}
			//  }
			// // had 1 button of type b1 under
			// // we must now check 2 buttons above original
			// if (counter == 1) {
			// 	// the two right from original button whic is b1
			// 	if (buttons[c + 1][r - 1].getPlayer() == p && buttons[c + 2][r - 2].getPlayer() == p) {
			// 		return true;
			// 	}
			// }
			// // had 2 buttons of type b1 under
			// // check one above
			// if (counter == 2) {
			// 	if (buttons[c + 1][r - 1].getPlayer() == p) {
			// 		return true;
			// 	}
			// }
			
		return false;
	}

	/************************ METHOD 2  *************************************************/
	// 	GameButton temp = b1;
		
	// 	int counter = 0;
	// 	int traverse = 1;
	// 	int p = b1.getPlayer(); // keeps track of player
	// 	GameButton start = diagonalHelper2(temp, p);
	// 	//System.out.println("(" + temp.getCol() + ", " + temp.getRow() + ")");

	// 	if (start.getRow() > 2) { // diagonal win can only happen when the row is greater than 2
	// 		return false;
	// 	} // else {
	// 		if (buttons[start.getCol()][start.getRow()].getPlayer() == p) { // checks first case
	// 			counter++;
	// 		}
	// 		while ((start.getCol()-traverse != 1) && (start.getRow()+traverse != 5) && (start.getCol() != 0) && (start.getRow() != 0)) {
	// 			System.out.println("(" + start.getCol() + ", " + start.getRow() + ")");
	// 			// start.setCol(start.getCol() - 1);
	// 			// start.setRow(start.getRow() + 1);

	// 			if (buttons[start.getCol() - traverse][start.getRow() + traverse].getPlayer() == p) {
	// 				traverse++;
	// 				counter++;
	// 			} else {
	// 				counter = 0;
	// 			}
	// 			System.out.println("counter: " + counter);

	// 			if (counter == 4) {
	// 				System.out.println("Diagonal Down win!!!");
	// 				return true;
	// 			}
	// 		}
	// 		//}
	// 		return false;
		
	// }

	// helper function for winDiagonalUP
	// Sets pointer to up left most diagonal place
	public GameButton diagonalHelper1(GameButton temp, int p) {
		while (temp.getPlayer() != p) {//temp.getPlayer() && temp.getRow() != 0
			// System.out.println("(" + b1.getCol() + ", " + b1.getRow() + ")");
			temp.setCol(temp.getCol() - 1);
			temp.setRow(temp.getRow() - 1);
		}
		return temp;
	}

	// helper function for winDiagonalDown
	// Sets pointer to up right most diagonal place
	public GameButton diagonalHelper2(GameButton temp, int p) {
		while (temp.getPlayer() != p) {
			// System.out.println("(" + b1.getCol() + ", " + b1.getRow() + ")");
			temp.setCol(temp.getCol() + 1);
			temp.setRow(temp.getRow() - 1);
		}
		return temp;
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
			//	|| winDiagonalUp(buttons, b1)) {
			//	|| gameTie(buttons)
			) {
			System.out.println("WINNER");
		} else if (gameTie(buttons)){
			System.out.println("Game Tie!!");
		} else {
			System.out.println("Keep going");
		}
	}

}