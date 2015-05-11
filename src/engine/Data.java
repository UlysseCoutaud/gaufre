public class Data {

	Stack<GameState> stack = new Stack<GameState>();
	Stack<GameState> saveOfStack = new Stack<GameState>();
	
	public final int width = 0;
	public final int height = 0;
	
	int currentPlayer = 1;	
	
	Square[][] squareBoard;
	
	Board board;
	
	public Data(int width, int height) {
		this.width = width;
		this.height = height;
		squareBoard = new Square[width][height];	
	}	
	
	public void initializeBoard() {
		for (i = 0; i < this.width; i++) {
			for (j = 0; j < this.height; j++) {
				squareBoard[i][j] = EMPTY;
			}
		}
		square[0][0] = POISON;
	}
	
	public void undoAction() {
		if (stack.isEmpty()) {
			System.out.println("undo Impossible");
			return;
		}		
		saveOfStack.push(stack.pop());
	}
	
	public void redoAction() {
		if (saveOfStack.isEmpty()) {
			System.out.println("redo impossible");
			return;
		}
		stack.push(SaveOfStack.pop());
	}
	
	public void addInStackAction(Square[][] squareBoard) {
		Board board = new Board(squareBoard, width, height);
		GameState gameState = new GameState(board, currentPlayer);
		if (currentPlayer == 1) {
			currentPlayer = 2;
		} else {
			currentPlayer = 1;
		}
		stack.push(gameState);
		saveOfStack.clear();
	}
	
	public erasePartOfWaffle(int x, int y) {
		if (board[x][y] == EMPTY) {
			System.out.println("empty cell selected")
		} else if (board[x][y] == WAFFLE) {
			int xLeft = width - x;
			int yLeft = height - y;
			for (i = x; i <= xLeft; x++) {
				for (j = y; j <= yLeft; y++) {
					board[i][j] = EATEN;
				}
			}
		}
	}	
}
