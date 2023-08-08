package board.entities.collision;

import board.BoardElement;
import board.entities.Entity;
import gremlins.Action;

@FunctionalInterface
public interface CollisionConsequence {
    public void act(BoardElement actor);

    public default CollisionConsequence then(Action action) {
        return (BoardElement actor) -> {
            this.act(actor);
            action.act();
        };
    }

    public static final CollisionConsequence DO_NOTHING = (BoardElement actor) -> {};

    public static final CollisionConsequence HAULT = (BoardElement actor) -> {
        if(actor instanceof Entity) ((Entity) actor).lockOnTile();
    };

    public static final CollisionConsequence DIE = (BoardElement actor) -> {
        actor.die();
    };
}
