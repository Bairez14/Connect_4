import javafx.scene.control.Button;


public class GameButton extends Button {
	int row;
	int col;
	int player;
	
	GameButton(int col, int row) {
		this.row = row;
		this.col = col;
		player = 0;
	}

}


// how to win
// check horizontal, vertical and down, diagonal
// have a counter that increases to 4. if 4, there is a winner, else false.