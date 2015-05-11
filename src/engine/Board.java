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
    
    public Board(int width, int height)
    {
	board = new Square[width][height];
	this.width = width;
	this.height = height;
	
	for(int i = 0; i < width; i++) {
	    for(int j = 0; j < height; j++) {
		board[i][j] = Square.WAFFLE;
	    }
	}
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
	return null; // TODO
    }
    
    @Override
    public String toString() {
	return null; // TODO
    }
    
}
