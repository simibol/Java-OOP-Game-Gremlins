package board.entities.characters;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.entities.Direction;
import board.entities.projectiles.Slime;
import board.tiles.Tile;
import gremlins.App;

import static java.util.Objects.nonNull;

public class Gremlin extends Character {
    private static final int DEFAULT_SPEED = 1;
    private static final double MINIMUM_RESPAWN_TILE_DISTANCE_FROM_WIZARD = 10;

    private boolean directionDefined = false;

    public Gremlin(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY, DEFAULT_SPEED);

        this.spellCooldown = board.getLevel().enemyCooldown;

        this.image = () -> App.Images.gremlin;
    }

    @Override
    public void update() {
        if(!directionDefined) {
            direction = this.getRandomAvaliableDirection();
            directionDefined = true;
        }

        boolean moved = this.move(direction);

        if(!moved) {
            direction = this.getRandomAvaliableDirection();
            this.move(direction);
        }

        tryToShoot(Slime.class);
    }

    

    @Override
    public void die() {
        super.die();
        
        respawn();
    }

    public void respawn() {
        List<Tile> potentialRespawnTiles = new ArrayList<Tile>();

        for(Tile tile : board.getEmptyTiles()) {
            double deltaX = board.getWizard().getBoardX() - tile.getBoardX();
            double deltaY = board.getWizard().getBoardX() - tile.getBoardX();

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            
            if(distance >= MINIMUM_RESPAWN_TILE_DISTANCE_FROM_WIZARD) potentialRespawnTiles.add(tile);
        }

        Tile respawnTile = potentialRespawnTiles.get((int) Math.floor(App.RANDOM_GENERATOR.nextDouble() * potentialRespawnTiles.size()));

        board.spawn(new Gremlin(board, respawnTile.getBoardX(), respawnTile.getBoardY(), respawnTile.getDisplayX(), respawnTile.getDisplayY()));
    }

    public Direction getRandomAvaliableDirection() {
        List<Direction> directions = this.getAvaliableDirections();
        if(directions.size() > 1 && nonNull(this.direction)) directions.remove(this.direction.flip());

        return directions.get((int) Math.floor(App.RANDOM_GENERATOR.nextDouble() * directions.size()));
    }
}
