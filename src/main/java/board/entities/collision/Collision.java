package board.entities.collision;

import board.Board;
import board.BoardElement;
import board.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public class Collision {
    private BoardElement elementA;
    private BoardElement elementB;
    private CollisionType collisionType;

    public Collision(BoardElement elementA, BoardElement elementB) {
        this.elementA = elementA;
        this.elementB = elementB;
        this.collisionType = CollisionType.getCorrespondingCollisionType(elementA, elementB);
    }

    public static Collision collision(BoardElement a, BoardElement b, Board board) {
        boolean collision =
            a.getDisplayX() < b.getDisplayX() + board.tileWidth &&
            a.getDisplayX() + board.tileWidth > b.getDisplayX() &&
            a.getDisplayY() < b.getDisplayY() + board.tileHeight &&
            board.tileHeight + a.getDisplayY() > b.getDisplayY();
        
        if(collision) return new Collision(a, b);
        else return null;
    }

    public static Collision[] findAllCollisions(Entity entity, Board board) {
        List<Collision> collisions = new ArrayList<Collision>();

        Stream.concat(board.getEntities().stream(), board.getNonEmptyTiles().stream()).forEach(
            (boardElement) -> {
                if(!entity.equals(boardElement)) {
                    Collision collision = entity.collidesWith(boardElement);
                    if(nonNull(collision)) collisions.add(collision);
                }
            }    
        );
        
        return collisions.toArray(new Collision[0]);
    }

    public void act() {
        if(nonNull(collisionType)) {
            collisionType.act(elementA, elementB);
        }
    }

    public static void act(Collision... collisions) {
        for(Collision collision : collisions) collision.act();
    }
}
