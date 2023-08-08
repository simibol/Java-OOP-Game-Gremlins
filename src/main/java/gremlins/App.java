package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.Random;
import board.Board;
import java.awt.Color;
import java.util.Set;

import processing.event.KeyEvent;

import java.io.*;

public class App extends PApplet {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;

    public static final int BOARD_DISPLAY_WIDTH = 720;
    public static final int BOARD_DISPLAY_HEIGHT = 660;

    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Color BACKGROUND_COLOR = new Color(247, 210, 136);

    public static final Random RANDOM_GENERATOR = new Random();

    public static class Images {
        public static PImage stoneWall, gremlin, slime, fireball, door, powerup;
        public static PImage brickWall, brickWallDestroyed0, brickWallDestroyed1, brickWallDestroyed2, brickWallDestroyed3;
        public static PImage wizardUp, wizardRight, wizardDown, wizardLeft;
        public static PImage playerLife;

        public static void loadImages(PApplet app) {
            stoneWall = loadImage(app, "stonewall.png");
            gremlin = loadImage(app, "gremlin.png");
            slime = loadImage(app, "slime.png");
            fireball = loadImage(app, "fireball.png");
            door = loadImage(app, "door.png");
            //powerup = loadImage(app, "powerup.png");

            brickWall = loadImage(app, "brickwall.png");
            brickWallDestroyed0 = loadImage(app, "brickwall_destroyed0.png");
            brickWallDestroyed1 = loadImage(app, "brickwall_destroyed1.png");
            brickWallDestroyed2 = loadImage(app, "brickwall_destroyed2.png");
            brickWallDestroyed3 = loadImage(app, "brickwall_destroyed3.png");

            wizardLeft = loadImage(app, "wizard0.png");
            wizardRight = loadImage(app, "wizard1.png");
            wizardUp = loadImage(app, "wizard2.png");
            wizardDown = loadImage(app, "wizard3.png");

            playerLife = loadImage(app, "wizard0.png");
        }

        public static PImage loadImage(PApplet app, String filepath) {
            return app.loadImage(app.getClass().getResource(filepath).getPath().replace("%20", " "));
        }
    }

    public String configPath;

    public static int lives;
    public static int currentLevel;
    public static Level[] levels;
    
    public static Board currentBoard;
    
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        // Load images during setup
        Images.loadImages(this);

        JSONObject conf = loadJSONObject(new File(this.configPath));
        JSONArray JSONLevels = conf.getJSONArray("levels");
        
        levels = new Level[JSONLevels.size()];

        for(int i = 0; i < levels.length; i++) {
            JSONObject JSONLevel = JSONLevels.getJSONObject(i);
            levels[i] = new Level(
                i + 1,
                JSONLevel.getString("layout"), 
                conf.getInt("lives"), 
                JSONLevel.getDouble("wizard_cooldown"), 
                JSONLevel.getDouble("enemy_cooldown")
            );
        }

        currentLevel = 0;

        try {
            currentBoard = new Board(levels[currentLevel].getBoardAsString(), levels[currentLevel], BOARD_DISPLAY_WIDTH, BOARD_DISPLAY_HEIGHT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        lives = levels[currentLevel].wizardLives;
    }

    /*
     * User inputs
     */

    public static boolean UP = false;
    public static boolean LEFT = false;
    public static boolean DOWN = false;
    public static boolean RIGHT = false;
    public static boolean SPACE = false;

    public void keyPressed(KeyEvent e) {
        int keys = e.getKeyCode();
        if (keys == 38) UP = true;
        if (keys == 37) LEFT= true;
        if (keys == 40) DOWN = true;
        if (keys == 39) RIGHT = true;
        if(keys == ' ') SPACE = true;

        currentBoard.getWizard().updateDirection(keys);
    }
    
    public void keyReleased(KeyEvent e) {
        int keys = e.getKeyCode();
        if (keys == 38) UP = false;
        if (keys == 37) LEFT = false;
        if (keys == 40) DOWN = false;
        if (keys == 39) RIGHT = false;
        if(keys == ' ') SPACE = false;
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(BACKGROUND_COLOR.getRGB());

        currentBoard.update();

        currentBoard.display(this);

        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }

    public static void removeLife() {
        lives--;
        if(App.lives == 0) System.exit(0);

        resetBoard();
    }

    public static void nextLevel() {
        currentLevel++;
        if(currentLevel >= levels.length) System.exit(0); // TODO make "YOU WIN!!" screen
        
        resetBoard();
    }

    public static void resetBoard() {
        try {
            currentBoard = new Board(levels[currentLevel].getBoardAsString(), levels[currentLevel], BOARD_DISPLAY_WIDTH, BOARD_DISPLAY_HEIGHT);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
