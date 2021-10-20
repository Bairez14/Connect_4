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

	class Coordinates {
		private Integer row;
		private Integer col;

		Coordinates(int col, int row) {
			this.col = col;
			this.row = row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getRow() {
			return this.row;
		}

		public void setCol(int col) {
			this.col = col;
		}

		public int getCol() {
			return this.col;
		}

		public boolean winVertical(int col, int row) {
			return true;
		}

		public boolean winHorizontal(int col, int row) {
			return true;
		}

		public boolean winDiagonal(int col, int row) {
			return true;
		}

		public boolean gameTie() {
			return true;
		}
	}
}
