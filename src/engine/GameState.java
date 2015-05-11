package engine;

public class GameState {

	public enum Square {
		EATEN, POISON, WAFFLE
	}

	Board board;

	int currentPlayer;

	public GameState(Board board, int currentPlayer) {
		this.board = board.cloneBoard();
		this.currentPlayer = currentPlayer;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	public Board getBoard() {
		return board;
	}
}
