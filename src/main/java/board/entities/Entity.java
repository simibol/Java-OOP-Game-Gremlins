package board.entities;

import java.util.function.BooleanSupplier;

import board.Board;
import board.BoardElement;
import board.entities.collision.Collision;
import board.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Entity extends BoardElement {
    public int speed; // Pixels per frame

    BooleanSupplier lockedOnTile;

    public Entity(Board board, int boardX, int boardY, int displayX, int displayY, int speed) {
        super(board, boardX, boardY, displayX, displayY);

        this.speed = speed;

        this.lockedOnTile = () -> 
            this.displayX % this.board.tileWidth == 0 && 
            this.displayY % this.board.tileHeight == 0; 
    }

    public boolean move(Direction direction, boolean forced) { // Returns if the entity did move or not
        if(!forced && isLockedOnTile()) return false;

        int originalDisplayX = displayX;
        int originalDisplayY = displayY;

        int deltaX;
        int deltaY;

        switch(direction) {
            case UP:
                deltaX = 0;
                deltaY = -speed;
                break;

            case RIGHT:
                deltaX = +speed;
                deltaY = 0;
                break;

            case DOWN:
                deltaX = 0;
                deltaY = +speed;
                break;

            case LEFT:
                deltaX = -speed;
                deltaY = 0;
                break;

            default:
                deltaX = 0;
                deltaY = 0;
                break;
        }

        this.displayX += deltaX;
        this.displayY += deltaY;
        updateBoardCoordinates();

        Collision[] collisions = Collision.findAllCollisions(this, board);

        if(collisions.length > 0) { // check if there was a collision
            Collision.act(collisions);
        }
        else if(!forced) {
            if(Math.abs(this.displayX % this.board.tileWidth - this.board.tileWidth) <= deltaX && Math.abs(this.displayY % this.board.tileHeight - this.board.tileHeight) <= deltaY) {
                lockOnTile();    
            }
        }

        return displayX != originalDisplayX || displayY != originalDisplayY;
    }

    public boolean move(Direction direction) {
        return move(direction, true);
    }

    public void lockOnTile() {
        updateBoardCoordinates();

        this.displayX = this.board.tileWidth * this.boardX;
        this.displayY = this.board.tileHeight * this.boardY;
    }

    public void updateBoardCoordinates() {
        this.boardX = (int) Math.round((double) this.displayX / this.board.tileWidth);
        this.boardY = (int) Math.round((double) this.displayY / this.board.tileHeight);
    }

    public boolean isLockedOnTile() {
        return lockedOnTile.getAsBoolean();
    }

    public List<Direction> getAvaliableDirections() {
        List<Direction> directions = new ArrayList<Direction>();

        Tile up = board.getTileAt(boardX, boardY - 1);
        Tile right = board.getTileAt(boardX + 1, boardY);
        Tile down = board.getTileAt(boardX, boardY + 1);
        Tile left = board.getTileAt(boardX - 1, boardY);

        if(up != null && up.isEmpty()) directions.add(Direction.UP);
        if(right != null && right.isEmpty()) directions.add(Direction.RIGHT);
        if(down != null && down.isEmpty()) directions.add(Direction.DOWN);
        if(left != null && left.isEmpty()) directions.add(Direction.LEFT);

        return directions;
    }
}
