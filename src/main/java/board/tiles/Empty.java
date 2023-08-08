package board.tiles;

import board.Board;

public class Empty extends Tile {
    public Empty(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);

        this.image = () -> null;
    }
}
