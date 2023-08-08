package board;

import java.util.List;

import board.entities.Entity;
import board.entities.characters.Wizard;
import board.tiles.Empty;
import board.tiles.Powerup;
import board.tiles.Tile;
import gremlins.Level;
import processing.core.PApplet;

import static java.util.Objects.nonNull;

import java.util.ArrayList;

public class Board {
    public final int tileWidth;
    public final int tileHeight;

    private Tile[][] board;

    private final int width;
    private final int height;

    private final int displayWidth;
    private final int displayHeight;

    private List<BoardElement> boardElements;
    private List<Tile> emptyTiles;
    private List<Tile> nonEmptyTiles;
    private List<Entity> entities;
    

    private Wizard wizard;

    private Level level;

    public Board(String board, Level level, int displayWidth, int displayHeight) {
        this.level = level;
        String[] rows = board.split("\n");

        this.width = rows[0].length() - 1;
        this.height = rows.length;

        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        this.board = new Tile[rows[0].length()][rows.length];
        this.boardElements = new ArrayList<BoardElement>();
        this.emptyTiles = new ArrayList<Tile>();
        this.nonEmptyTiles = new ArrayList<Tile>();
        this.entities = new ArrayList<Entity>();
        

        tileWidth = this.displayWidth / this.width;
        tileHeight = this.displayHeight / this.height;

        for(int x = 0; x < this.width; x++) {
            for(int y = 0; y < this.height; y++) {
                BoardElementConstructor boardElementConstructor = BoardElementConstructor.getFromCharacter(rows[y].charAt(x));
                
                if(nonNull(boardElementConstructor)) {
                    BoardElement boardElement = boardElementConstructor.construct(this, x, y, x * tileWidth, y * tileHeight);

                    if(boardElement instanceof Tile) {
                        this.board[x][y] = (Tile) boardElement;
                        
                        if(this.board[x][y].isEmpty()) emptyTiles.add(this.board[x][y]);
                        else nonEmptyTiles.add(this.board[x][y]);
                    }
                    else if(boardElement instanceof Entity) {
                        this.board[x][y] = new Empty(this, x, y, x * tileWidth, y * tileHeight);

                        entities.add((Entity) boardElement);
                        
                        if(boardElement instanceof Wizard) wizard = (Wizard) boardElement;
                    }

                    boardElements.add(boardElement);   
                }
            }
        }
    }

    public void display(PApplet app) {
        // Forced display order
        for(Tile tile : nonEmptyTiles) tile.display(app);
        for(Entity entity : entities) entity.display(app);
    }

    public void update() { // Updates tiles and entities
        // Iterative loop to avoid Concurrent Modification Exception
        for(int i = 0; i < boardElements.size(); i++) boardElements.get(i).update();
    }

    public Wizard getWizard() {
        return wizard;
    }

    public List<Tile> getNonEmptyTiles() {
        return nonEmptyTiles;
    }

    public void spawn(Entity entity) {
        boardElements.add(entity);
        entities.add(entity);
    }

    public void remove(Entity entity) {
        boardElements.remove(entity);
        entities.remove(entity);
    }

    public void remove(Tile tile) {
        nonEmptyTiles.remove(tile);
        boardElements.remove(tile);
        board[tile.getBoardX()][tile.getBoardY()] = new Empty(this, tile.getBoardX(), tile.getBoardY(), tile.getBoardX() * tileWidth, tile.getBoardY() * tileHeight);
    }

    public void remove(BoardElement boardElement) {
        if(boardElement instanceof Entity) remove((Entity) boardElement);
        else if(boardElement instanceof Tile) remove((Tile) boardElement);
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTileAt(int x, int y) {
        if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) return board[x][y];
        else return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Tile> getEmptyTiles() {
        return emptyTiles;
    }

    public Level getLevel() {
        return level;
    }
}
