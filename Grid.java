package grid;

/**
 * Grid - class is used to create an object from a json file, which
 * is then used in other classes to access information about the Grid
 *
 * @author Danielle Plecki
 */
class Grid {
    private int dimension;
    private Position start;
    private Position end;
    private Position[] obstacles;

    int getDimension() {
        return dimension;
    }

    void setDimension(int dimension) {
        this.dimension = dimension;
    }

    Position getStart() {
        return start;
    }

    void setStart(Position start) {
        this.start = start;
    }

    Position getEnd() {
        return end;
    }

    void setEnd(Position end) {
        this.end = end;
    }

    Position[] getObstacles() {
        return obstacles;
    }

    void setObstacles(Position[] obstacles) {
        this.obstacles = obstacles;
    }
}
