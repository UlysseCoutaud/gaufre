package engine;

import java.awt.Point;

public class Board {
    
    public enum Square {
        EATEN,
        POISON,
        WAFFLE
    }
    
    public final Square[][] board;
    public final int width;
    public final int height;
    
    public Board(int width, int height, Square[][] board) {
        board = new Square[width][height];
        this.width = width;
        this.height = height;
	
        for(int i = 0; i < width; i++) {
	        for(int j = 0; j < height; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }
    
    public Board(int width, int height) {
        Square[][] b = new Square[width][height];
        this.width = width;
        this.height = height;
	
        for(int i = 0; i < width; i++) {
	        for(int j = 0; j < height; j++) {
                this.board[i][j] = Square.WAFFLE;
            }
        }
        this.board[0][0] = Square.POISON;
    }
    
    public boolean isWaffle(int i, int j)
    {
        return board[i][j] == Square.WAFFLE;
    }
    
    public boolean isWaffle(Point p)
    {
        return isWaffle(p.x, p.y);
    }
    
    public boolean isEaten(int i, int j)
    {
        return board[i][j] == Square.EATEN;
    }
    
    public boolean isEaten(Point p)
    {
        return isEaten(p.x, p.y);
    }
    
    public boolean isPoison(int i, int j)
    {
        return board[i][j] == Square.POISON;
    }
    
    public boolean isPoison(Point p)
    {
        return isPoison(p.x, p.y);
    }
    
    public Board cloneBoard() {
        return new Board(width, height, board);
    }
    
    @Override
    public String toString() {
        String string = new String;
        for (i = 0; i < width; i++) {
            string += "+---";
        }
        string += "+\n";
        for (i = 0; i < width; i++) {
            string += "| ";
            for (j = 0; j < height; j++) {
                if (isPoison(i, j)) {
                    string += "P ";
                } else if (isWaffle(i, j)){
                    string += "W ";
                } else {
                    string += "E ";
                }
                string += "|";
            }
            string += "\n";
            for (i = 0; i < width; i++) {
                string += "+---";
            }
            string += "+\n";           
        }
        return string;
    } 
}
