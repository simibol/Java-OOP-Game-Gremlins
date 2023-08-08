package board.tiles;

import board.Board;
import board.BoardElement;

public class Tile extends BoardElement { 
    public Tile(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);

        this.boardX = boardX;
        this.boardY = boardY;
    }

    public boolean isEmpty() {
        return this instanceof Empty;
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }
}
