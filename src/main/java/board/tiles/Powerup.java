package board.tiles;

import board.Board;
import gremlins.App;

public class Powerup extends Tile {
    public static double DEFAULT_POWERUP_DURATION = 10; // seconds
    
    public Powerup(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);
        
        this.image = () -> App.Images.powerup;
    }
    

}
