package board.entities.projectiles;

import board.Board;
import board.entities.Direction;
import gremlins.App;

public class Slime extends Projectile {
    private static final int DEFAULT_SPEED = 4;

    public Slime(Board board, int boardX, int boardY, Direction direction, int displayX, int displayY) {
        super(board, boardX, boardY, direction, displayX, displayY, DEFAULT_SPEED);

        this.image = () -> App.Images.slime;
    }
}
