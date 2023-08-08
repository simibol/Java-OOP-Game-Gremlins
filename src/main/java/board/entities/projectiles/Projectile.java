package board.entities.projectiles;

import board.Board;
import board.entities.Direction;
import board.entities.Entity;

public class Projectile extends Entity {
    private Direction direction;

    public Projectile(Board board, int boardX, int boardY, Direction direction, int displayX, int displayY, int speed) {
        super(board, boardX, boardY, displayX, displayY, speed);

        this.direction = direction;
    }

    @Override
    public void update() {
        move(direction, true);
    }
}
