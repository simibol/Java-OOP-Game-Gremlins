package board.tiles;

import board.Board;
import gremlins.App;

public class Exit extends Tile {
    public Exit(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);

        this.image = () -> App.Images.door;
    }
}
