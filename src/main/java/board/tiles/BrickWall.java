package board.tiles;

import board.Board;
import gremlins.App;

public class BrickWall extends Tile {
    private static int MAX_BREAKING_STAGE = 3;
    private static int FRAMES_PER_BREAKING_STAGE = 4;

    private boolean breaking;
    private int breakingStageFrame;

    public BrickWall(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY);

        this.image = () -> {
            if(breaking) {
                switch(breakingStageFrame / FRAMES_PER_BREAKING_STAGE) {
                    case 0:
                    return App.Images.brickWallDestroyed0;
                    case 1:
                    return App.Images.brickWallDestroyed1;
                    case 2:
                    return App.Images.brickWallDestroyed2;
                    case 3:
                    return App.Images.brickWallDestroyed3;

                    default:
                    return App.Images.brickWall;
                }
            }
            else return App.Images.brickWall;
        };
    }

    @Override 
    public void update() {
        // Breaking
        if(breaking) {
            breakingStageFrame++;

            if(breakingStageFrame / FRAMES_PER_BREAKING_STAGE > MAX_BREAKING_STAGE) {
                super.die();
            }
        }
    }

    @Override
    public void die() {
        if(!breaking) {
            breaking = true;
            breakingStageFrame = 0;
        }
    }
}
