public class GameState {

	public enum Square {
		EATEN,
		POISON,
		WAFFLE
	}	
	
	Square[][] squareBoard;
	
	int currentPlayer;
	
	public GameState(Board board, int currentPlayer) {
		this.squareBoard.clone(board.board);
		this.currentPlayer = currentPlayer;
	}
	
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Square[][] getSquareBoard() {
		return this.squareBoard;
	}	
}
