package board.tiles;

import board.Board;
import gremlins.App;

public class StoneWall extends Tile {
    public StoneWall(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);

        this.image = () -> App.Images.stoneWall;
    }
}
