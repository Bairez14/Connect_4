import javafx.scene.control.Button;


public class GameButton extends Button {
	int row;
	int col;
	int player;
	boolean clicked;
	
	GameButton(int col, int row) {
		this.row = row;
		this.col = col;
		player = 0;
		clicked = false;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(int row){
		this.row = row;
	}

	public int getCol() {
		return this.col;
	}

	public void setCol(int col){
		this.col = col;
	}

	public boolean getClicked(){
		return this.clicked;
	}

	public void setClicked(boolean p) {
		this.clicked = p;
	}

	public int getPlayer() {
		return this.player;
	}
	
	public void setPlayer(int p){
		this.player = p;
	}

}


// how to win
// check horizontal, vertical and down, diagonal
// have a counter that increases to 4. if 4, there is a winner, else false.