import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameLogic {
	ArrayList<GameButton> playerMoves;
	ArrayList<GameButton> winningButtons;
	boolean tie;

	GameLogic() {
		playerMoves = new ArrayList<GameButton>();
		winningButtons = new ArrayList<GameButton>();
		this.tie = false;
	}

	public void setTie(boolean t){
		this.tie = t;
	}

	public boolean getTie(){
		return this.tie;
	}

	public void setPlayerMoves(ArrayList<GameButton> moves) {
		playerMoves = moves;
	}

	public ArrayList<GameButton> getPlayerMoves() {
		return playerMoves;
	}

	public void setWinningButtons(ArrayList<GameButton> b) {
		winningButtons = b;
	}

	public ArrayList<GameButton> getWinningButtons() {
		return winningButtons;
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
				//winningButtons.add(b1);
			}
			//System.out.println("Vertical win!!!");
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
				//System.out.println("Horizontal win!!!");
				return true;
			}
		}
		return false;
	}

	public boolean winDiagonalUp(GameButton[][] buttons, GameButton b1) {
		int traverse = 1;
		int counter = 0;
		int p = b1.getPlayer();
		int r = b1.getRow();
		int c = b1.getCol();
		
		// never be an instance where you can win diagonally in these coordinates
		if ((c == 0 && r == 3) || (c == 0 && r == 4) || (c == 0 && r == 5) || (c == 1 && r == 4) || (c == 1 && r == 5)
				|| (c == 2 && r == 5) || (c == 6 && r == 0) || (c == 6 && r == 1) || (c == 6 && r == 2)
				|| (c == 5 && r == 0) || (c == 5 && r == 1) || (c == 4 && r == 0)) {
			return false;
		}

		// Check diagonally up since we would be starting at either col = 6 or row = 5
		if (c == 6 || r == 5) {
			for (int i = 0; i < 4; i++) {
				if (buttons[c - i][r - i].getPlayer() != p) {
					return false;
				}
				//winningButtons.add(b1);
			}
			return true;
		} else if (c == 0 || r == 0) { // Check diagonally down since we would be starting at either col = 6 or row = 0
			for (int i = 0; i < 4; i++) {
				if (buttons[c + i][r + i].getPlayer() != p) {
					return false;
				}
				//winningButtons.add(b1);
			}
			return true;
		} else {
			while ((c + traverse <= 6) && (r + traverse <= 5)) { // going down
				if (buttons[c + traverse][r + traverse].getPlayer() == p) {
					counter++;
					traverse++;
					//winningButtons.add(b1);
				} else if (counter >= 3) {
					return true;
				} else {
					break;
				}
			}
			if (counter >= 3) {
				return true;
			}
			//System.out.println("Counter: " + counter);
			traverse = 1;
			while ((c - traverse >= 0) && (r - traverse >= 0)) { // going up
				if (buttons[c - traverse][r - traverse].getPlayer() == p) {
					traverse++;
					counter++;
					//winningButtons.add(b1);
				} else if (counter >= 3) {
					return true;
				} else {
					return false;
				}
			}
			if (counter >= 3) {
				return true;
			}
		}
		return false;
	}

	public boolean winDiagonalDown(GameButton[][] buttons, GameButton b1) {
		int traverse = 1;
		int counter = 0;
		int p = b1.getPlayer();
		int r = b1.getRow();
		int c = b1.getCol();
		// never be an instance where you can win diagonally in these coordinates
		if ((c == 0 && r == 0) || (c == 6 && r == 5) || (c == 6 && r == 4) || (c == 6 && r == 3) || (c == 5 && r == 5)
				|| (c == 5 && r == 4) || (c == 4 && r == 5) || (c == 0 && r == 1) || (c == 0 && r == 2)
				|| (c == 1 && r == 0) || (c == 1 && r == 1) || (c == 2 && r == 0)) {
			return false;
		}

		// Check diagonally up since we would be starting at either col = 0 or row = 5
		if (c == 0 || r == 5) {
			for (int i = 0; i < 4; i++) {
				if (buttons[c + i][r - i].getPlayer() != p) {
					return false;
				}
				//winningButtons.add(b1);
			}
			return true;
		} else if (c == 6 || r == 0) { // Check diagonally down since we would be starting at either col = 6 or row = 0
			for (int i = 0; i < 4; i++) {
				if (buttons[c - i][r + i].getPlayer() != p) {
					return false;
				}
				//winningButtons.add(b1);
			}
			return true;
		} else {
			while ((c - traverse >= 0) && (r + traverse <= 5)) { // going down
				if (buttons[c - traverse][r + traverse].getPlayer() == p) {
					counter++;
					traverse++;
					//winningButtons.add(b1);
				} else if (counter >= 3) {
					return true;
				} else {
					break;
				}
			}
			if (counter >= 3) {
				return true;
			}
			//System.out.println("Counter: " + counter);
			traverse = 1;
			while ((c + traverse <= 6) && (r - traverse >= 0)) { // going up
				if (buttons[c + traverse][r - traverse].getPlayer() == p) {
					traverse++;
					counter++;
					//winningButtons.add(b1);
				} else if (counter >= 3) {
					return true;
				} else {
					return false;
				}
			}
		} 
		return false;
	}

	public boolean gameTie(GameButton[][] buttons) {
		for (int c = 0; c < 7; c++) { // column
			for (int r = 0; r < 6; r++) { // row
				if (buttons[c][r].player == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean win(GameButton[][] buttons, GameButton b1, ObservableList<String> stats) {
		if (winVertical(buttons, b1)
			|| winHorizontal(buttons, b1) 
		  	|| winDiagonalDown(buttons, b1)
			|| winDiagonalUp(buttons, b1)
			) {
			stats.add("Player " + b1.getPlayer() + " won!");
			return true;
		} else if (gameTie(buttons)) {
			setTie(true);
			this.tie = true;
			stats.add("Game is a tie!");
			return false;
		} else {
			stats.add("Player " + b1.getPlayer() + "'s move: " + b1.getCol() + ", " + b1.getRow());
			return false;
		}
	}

}