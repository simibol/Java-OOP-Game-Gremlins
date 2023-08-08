package board.entities;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    

    public static Direction getDirectionFromArrowKeys(int keyCode) {
        switch(keyCode) {
            case 38: return UP;
            case 37: return LEFT;
            case 40: return DOWN;
            case 39: return RIGHT;

            default: return null;
        }
    }

    public Direction flip() {
        switch(this) {
            case UP: return DOWN;
            case LEFT: return RIGHT;
            case DOWN: return UP;
            case RIGHT: return LEFT;

            default: return null;
        }
    }
}
