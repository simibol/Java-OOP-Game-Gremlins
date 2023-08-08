package board.entities.characters;

import gremlins.App;
import processing.core.PApplet;
import processing.core.PFont;

import static board.entities.Direction.*;

import board.Board;
import board.entities.Direction;
import board.entities.projectiles.Fireball;


import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;


import java.time.Instant;

public class Wizard extends Character {
    private static final Direction STARTING_DIRECTION = UP;
    private static final int DEFAULT_SPEED = 2;

    private Direction directionTravelling;

    public Wizard(Board board, int boardX, int boardY, int displayX, int displayY) {
        super(board, boardX, boardY, displayX, displayY, DEFAULT_SPEED);

        this.spellCooldown = board.getLevel().wizardCooldown;

        this.direction = STARTING_DIRECTION;
        this.directionTravelling = this.direction;

        this.image = () -> {
            switch (this.direction) {
                case UP:
                    return App.Images.wizardUp;
                case RIGHT:
                    return App.Images.wizardRight;
                case DOWN:
                    return App.Images.wizardDown;
                case LEFT:
                    return App.Images.wizardLeft;

                default:
                    return null;
            }
        };
    }

    @Override
    public void update() {
        // Movement
        boolean voluntaryMove = false;
        if (direction == UP && App.UP) voluntaryMove = true;
        if (direction == LEFT && App.LEFT) voluntaryMove = true;
        if (direction == DOWN && App.DOWN) voluntaryMove = true;
        if (direction == RIGHT && App.RIGHT) voluntaryMove = true;

        if (super.isLockedOnTile()) {
            super.move(direction, voluntaryMove);
            directionTravelling = direction;
        } 
        else {
            super.move(directionTravelling, voluntaryMove);
        }

        // Check for shooting
        if (App.SPACE) tryToShoot(Fireball.class);
    }

    public void updateDirection(Direction direction) {
        if (nonNull(direction)) {
            this.direction = direction;
        }
    }

    public void updateDirection(int keyCode) {
        updateDirection(Direction.getDirectionFromArrowKeys(keyCode));
    }

    @Override
    public void die() {
        App.removeLife();
    }
    

    @Override
    public void display(PApplet app) {
        super.display(app);

        int fontSize = 15;
        app.textSize(fontSize);
        app.fill(0);

        int x = fontSize;
        int y = App.BOARD_DISPLAY_HEIGHT + (App.HEIGHT - App.BOARD_DISPLAY_HEIGHT) / 2;
        app.text("Lives: ", x, y);

        x = fontSize * 4;
        y += 3 - App.Images.playerLife.height;
        for (int i = 0; i < App.lives; i++) {
            app.image(App.Images.playerLife, x, y);
            x += App.Images.playerLife.width;
        }

        x += App.Images.playerLife.width;
        y = App.BOARD_DISPLAY_HEIGHT + (App.HEIGHT - App.BOARD_DISPLAY_HEIGHT) / 2;
        app.text("Level: " + (App.currentLevel + 1) + "/" + App.levels.length, x, y);

        x = App.WIDTH / 2 - 90;
        app.text("Cooldown: ", x, y);

        x = App.WIDTH / 2;

        int manaWidth = 300;
        int width = manaWidth;
        int height = 10;
        y -= height;

        app.strokeWeight(3);
        app.fill(255);
        app.rect(x, y, width, height);
        
        app.fill(255, 0, 255);
        width = 0;
        if(nonNull(this.lastSpellTimestamp)) width = (int) ((Instant.now().toEpochMilli() - this.lastSpellTimestamp.toEpochMilli()) * manaWidth / (this.spellCooldown * 1000.0));
        if(width >= manaWidth || isNull(this.lastSpellTimestamp)) {
            width = manaWidth;
            app.fill(0, 180, 0);
        }
        if(width > 0) app.rect(x, y, width, height);
        app.strokeWeight(1);
    }
}
