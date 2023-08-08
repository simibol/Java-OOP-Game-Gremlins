package board;

import board.entities.characters.Gremlin;
import board.entities.characters.Wizard;
import board.tiles.*;

@FunctionalInterface
public interface BoardElementConstructor {
    BoardElement construct(Board board, int boardX, int boardY, int displayX, int displayY);    

    public static BoardElementConstructor getFromCharacter(Character c) {
        switch(c) {
            // Tiles
            case 'X': return StoneWall::new;
            case 'B': return BrickWall::new;
            case 'E': return Exit::new; 
            case 'P': return Powerup::new;
            case ' ': return Empty::new; 

            // Entities
            case 'W': return Wizard::new;
            case 'G': return Gremlin::new;

            default: return null;
        }
    }
}
