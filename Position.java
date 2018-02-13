package grid;

/**
 * Position - class is used to create an object that contains both the x and
 * the y coordinates of the position.
 *
 * @author Danielle Plecki
 */
class Position {
    private int x;
    private int y;

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Position(int newX, int newY){
        setX(newX);
        setY(newY);
    }

    Position(){} //default constructor
    boolean equals(Position B){
        return (this.x == B.x && this.y==B.y);
    }
}
