package board.entities.characters;

import java.time.Instant;

import board.Board;
import board.entities.Direction;
import board.entities.Entity;
import board.entities.projectiles.Projectile;
import board.entities.projectiles.Slime;

import static java.util.Objects.isNull;

import java.lang.reflect.InvocationTargetException;

public class Character extends Entity {
    protected Direction direction;
    
    protected double spellCooldown; // in seconds
    protected Instant lastSpellTimestamp;

    public Character(Board board, int boardX, int boardY, int displayX, int displayY, int speed) {
        super(board, boardX, boardY, displayX, displayY, speed);
    }

    public void tryToShoot(Class<? extends Projectile> projectileClass) {
        if(isNull(lastSpellTimestamp) || Instant.now().toEpochMilli() - lastSpellTimestamp.toEpochMilli() > spellCooldown * 1000.0) {
            lastSpellTimestamp = Instant.now();
            Projectile projectile;

            try {
                projectile = 
                    projectileClass
                    .getDeclaredConstructor(Board.class, int.class, int.class, Direction.class, int.class, int.class)
                    .newInstance(board, boardX, boardY, direction, boardX * board.tileWidth, boardY * board.tileHeight);
                
                board.spawn(projectile);
            } 
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
