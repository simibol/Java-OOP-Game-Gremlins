package board;

import processing.core.PApplet;

import static java.util.Objects.nonNull;

import board.entities.collision.Collision;

public class BoardElement {
    protected ImageFunction image;

    protected int boardX, boardY;
    protected int displayX, displayY;

    protected Board board;

    protected BoardElement(Board board, int boardX, int boardY, int displayX, int displayY) {
        this.board = board;

        this.boardX = boardX;
        this.boardY = boardY;

        this.displayX = displayX;
        this.displayY = displayY;
    }

    public void display(PApplet app) {
        if(nonNull(image.get())) app.image(image.get(), displayX, displayY, board.tileWidth, board.tileHeight);
    }

    public Collision collidesWith(BoardElement boardElement) {
        return Collision.collision(this, boardElement, board);
    }

    public void update() {
        
    }

    public void die() {
        board.remove(this);
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardX(int newBoardX) {
        boardX = newBoardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setBoardY(int newBoardY) {
        boardY = newBoardY;
    }

    public int getDisplayX() {
        return displayX;
    }

    public void setDisplayX(int newDisplayX) {
        displayX = newDisplayX;
    }

    public int getDisplayY() {
        return displayY;
    }

    public void setDisplayY(int newDisplayY) {
        displayY = newDisplayY;
    }

    public int changeDisplayXBy(int deltaX) {
        return (displayX += deltaX);
    }

    public int changeDisplayYBy(int deltaY) {
        return (displayY += deltaY);
    }

    public Board getBoard() {
        return board;    
    }
}
