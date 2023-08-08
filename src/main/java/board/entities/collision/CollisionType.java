package board.entities.collision;

import board.BoardElement;
import board.entities.characters.Gremlin;
import board.entities.characters.Wizard;
import board.entities.projectiles.Fireball;
import board.entities.projectiles.Slime;
import board.tiles.BrickWall;
import board.tiles.Exit;
import board.tiles.Powerup;
import board.tiles.StoneWall;
import gremlins.App;

import static board.entities.collision.CollisionConsequence.*;

public enum CollisionType {
    WIZARD_FIREBALL(
        Wizard.class, DO_NOTHING,
        Fireball.class, DO_NOTHING
    ),

    WIZARD_GREMLIN(
        Wizard.class, DIE,
        Gremlin.class, DO_NOTHING
    ),

    WIZARD_SLIME(
        Wizard.class, DIE,
        Slime.class, DIE
    ),

    WIZARD_BRICK_WALL(
        Wizard.class, HAULT,
        BrickWall.class, DO_NOTHING
    ),

    WIZARD_STONE_WALL(
        Wizard.class, HAULT,
        StoneWall.class, DO_NOTHING
    ),

    WIZARD_POWERUP(
        Wizard.class, DO_NOTHING,
        Powerup.class, DO_NOTHING
    ),

    WIZARD_EXIT(
        Wizard.class, (BoardElement actor) -> App.nextLevel(),
        Exit.class, DO_NOTHING
    ),

    GREMLIN_FIREBALL(
        Gremlin.class, DIE,
        Fireball.class, DIE
    ),

    GREMLIN_GREMLIN(
        Gremlin.class, DO_NOTHING,
        Gremlin.class, DO_NOTHING
    ),

    GREMLIN_SLIME(
        Gremlin.class, DO_NOTHING,
        Slime.class, DO_NOTHING
    ),

    GREMLIN_BRICK_WALL(
        Gremlin.class, HAULT,
        BrickWall.class, DO_NOTHING
    ),

    GREMLIN_STONE_WALL(
        Gremlin.class, HAULT,
        StoneWall.class, DO_NOTHING
    ),

    GREMLIN_POWERUP(
        Gremlin.class, DO_NOTHING,
        Powerup.class, DO_NOTHING
    ),

    FIREBALL_SLIME(
        Fireball.class, DIE,
        Slime.class, DIE
    ),

    FIREBALL_BRICK_WALL(
        Fireball.class, DIE,
        BrickWall.class, DIE
    ),

    FIREBALL_STONE_WALL(
        Fireball.class, DIE,
        StoneWall.class, DO_NOTHING
    ),

    FIREBALL_POWERUP(
        Fireball.class, DO_NOTHING,
        Powerup.class, DO_NOTHING
    ),
    
    SLIME_BRICK_WALL(
        Slime.class, DIE,
        BrickWall.class, DO_NOTHING
    ),

    SLIME_STONE_WALL(
        Slime.class, DIE,
        StoneWall.class, DO_NOTHING
    ),

    SLIME_POWERUP(
        Slime.class, DO_NOTHING,
        Powerup.class, DO_NOTHING
    );

    public Class<? extends BoardElement> classA;
    CollisionConsequence consequenceA;
     
    public Class<? extends BoardElement> classB;
    CollisionConsequence consequenceB;

    CollisionType(Class<? extends BoardElement> ClassA, CollisionConsequence ConsequenceA, Class<? extends BoardElement> ClassB, CollisionConsequence ConsequenceB) {
        this.classA = ClassA;
        this.consequenceA = ConsequenceA;

        this.classB = ClassB;
        this.consequenceB = ConsequenceB;
    }

    public static CollisionType getCorrespondingCollisionType(Class<? extends BoardElement> ClassA, Class<? extends BoardElement> ClassB) {
        for(CollisionType collisionType : CollisionType.values()) {
            if((collisionType.classA.equals(ClassA) && collisionType.classB.equals(ClassB)) || (collisionType.classA.equals(ClassB) && collisionType.classB.equals(ClassA))) {
                return collisionType;
            }
        }

        return null;
    }

    public static CollisionType getCorrespondingCollisionType(BoardElement elementA, BoardElement elementB) {
        return getCorrespondingCollisionType(elementA.getClass(), elementB.getClass());
    }

    public void act(BoardElement elementA, BoardElement elementB) {
        if(elementA.getClass().equals(classA) && elementB.getClass().equals(classB)) {
            consequenceA.act(elementA);
            consequenceB.act(elementB);
        }
        else if(elementA.getClass().equals(classB) && elementB.getClass().equals(classA)) {
            consequenceA.act(elementB);
            consequenceB.act(elementA);
        }
    }
}
