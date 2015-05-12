
package player;

import java.awt.Point;

public class BoardUlysse {

    int width, height;

    BoardUlysse() {
        width = 10;
        height = 10;

    }

    public BoardUlysse clone() {
        BoardUlysse res = new BoardUlysse();
        return res;
    }

    public static boolean isWaffle(int i, int j) {
        return true;
    }

    public static boolean isPoison(int i, int j) {
        return true;
    }

    public static boolean isEaten(int i, int j) {
        return true;
    }

    /**
     * Returns true if we can eat this square without being poisoned
     */
    public static boolean isSafe(int i, int j) {
        return true;
    }

    public Point poisonPosition() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (isPoison(i, j))
                    return new Point(i, j);
            }
        }
        return null;
    }

    /**
     * Eats all the waffle squares to the right and under p
     */
    public void play(Point p) {
    }

    /**
     * Removes one waffle square
     */
    public void remove(Point p) {
    }
}
