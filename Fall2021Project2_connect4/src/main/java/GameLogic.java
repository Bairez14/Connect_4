import java.util.ArrayList;

public class GameLogic {
	private ArrayList<Coordinates> p1;
	private ArrayList<Coordinates> p2;
	
	GameLogic() {
		p1 = new ArrayList();
		p2 = new ArrayList();
	}
	
	
	class Coordinates {
		private Integer row;
		private Integer col;
		
		Coordinates(int row, int col) {
			this.col = col;
			this.row = row;
		}
		
		public void setRow (int row) {
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
	}
}


