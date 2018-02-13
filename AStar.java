package grid;

import java.util.*;

/**
 * AStar - class that implements A* algorithm to search a grid
 * @author Danielle Plecki
 */
class AStar{
    /**
     * This method uses the information from the user's Grid object to find and
     * return the shortest path between two specified points.
     *
     * It should be noted that:
     *  G score = the movement cost to move from the starting point to a given position on the grid,
     *            following the path generated to get there
     *  H score = the distance from a given position on the grid to the end position, it is a
     *            heuristic calculation made by using the distance formula
     *  F score = G + H
     *
     * @param grid Grid that was created from the user's json file
     * @param startPosition starting position of the path, specified by the user
     * @param endPosition ending position of the path, specified by the user
     * @return string of path from ending position to starting, each position
     *         is formatted as x=0, y=0 and each subsequent position is on
     *         a new line
     */
    static String algorithm(Grid grid ,Position startPosition, Position endPosition){
        final int maxGscore = 2000000000; //Arbitrarily large number used as default for comparison
        Set<Position> closedSet = new HashSet<>();
        Set<Position> openSet = new HashSet<>();
        openSet.add(startPosition);
        HashMap<Position, Integer> gScore = new HashMap<>();
        gScore.put(startPosition, 0);
        HashMap<Position, Double> fScore = new HashMap<>();
        fScore.put(startPosition, hEstimate(startPosition, endPosition));
        HashMap<Position, Position> cameFrom = new HashMap<>();
        ArrayList<Position> validNeighbors;
        String path = "";
        boolean empty = false;

        while(!empty){
            //Current position is set to the position in the open set with the shortest distance to the end position
            Position currentPosition = lowestFScore(openSet, fScore);
            //Ends search once the end position is reached
            if(currentPosition.equals(endPosition)){
                 path = constructShortestPath(cameFrom, currentPosition);
                 empty = true;
            }
            openSet.remove(currentPosition);
            closedSet.add(currentPosition);
            validNeighbors = getValidNeighbors(currentPosition, grid);
            for(Position neighbor : validNeighbors){
                if(contains(closedSet, neighbor)){
                    continue; //Ignore neighbors that have already been evaluated
                }
                //Distance between current and neighbor is always 1
                int neighborGScoreTentative = gScore.get(currentPosition) + 1;
                //If previous G score for neighbor is null, maxGscore is an arbitrarily large number
                // that is used for comparison so that the tentative G score is always less
                int neighborGScorePrevious = gScore.getOrDefault(neighbor, maxGscore);
                if(!contains(openSet, neighbor)) {
                    openSet.add(neighbor);
                }
                else if(neighborGScoreTentative >= neighborGScorePrevious){
                    continue; //Not a better path
                }
                cameFrom.put(neighbor, currentPosition);
                gScore.put(neighbor, neighborGScoreTentative);
                double neighborFScore = gScore.get(neighbor) + hEstimate(neighbor, endPosition);
                fScore.put(neighbor, neighborFScore);
            }
        }
        //Once end position is reached, the path is constructed as a String and the while loop is exited
        //to return the path here
        return path;
    }

    /**
     * This method calculates and returns the estimated heuristic(H) score by finding the distance
     * between the current position and the end position. The distance formula
     * D = sqrt((x^2)+(y^2))  is used for this calculation.
     *
     * @param currentPosition position algorithm is currently evaluating
     * @param endPosition end position specified by user
     * @return the estimate of the H score of the current position
     */
   static double hEstimate( Position currentPosition, Position endPosition){
        double changeX = Math.abs(currentPosition.getX() - endPosition.getX());
        double changeY = Math.abs(currentPosition.getY() - endPosition.getY());
        return Math.sqrt((changeX*changeX)+(changeY*changeY));
    }

    /**
     * This method iterates through the current open set and returns the position that has the
     * lowest F score, which will be made the new current position. If the open set only has
     * one position, then that will be the position returned.
     *
     * @param openSet current open set that contains all positions that could be evaluated
     * @param fScore HashMap that contains all positions that have previously been evaluated and their
     *               current F score
     * @return the position in the open set with the lowest F score
     */
    private static Position lowestFScore(Set<Position> openSet, HashMap<Position, Double> fScore){
        double lowestFScore = 2000000000; //Arbitrarily high number that no F score will surpass
        Iterator<Position> positionIterator = openSet.iterator();
        //Initializes position to a null Position, but this will be changed
        Position lowestFScorePosition = null;
        if(openSet.size() == 1){
            lowestFScorePosition = positionIterator.next();
        }
        else {
            while(positionIterator.hasNext()){
                Position tempPosition = positionIterator.next();
                double tempFScore = fScore.get(tempPosition);
                if(tempFScore<lowestFScore){
                    lowestFScore = tempFScore;
                    lowestFScorePosition = tempPosition;
                }
            }
        }
        return lowestFScorePosition;
    }

    /**
     * This method creates positions of each  neighbor of the current position and determines if each
     * position is valid by using the isValidPosition() method. If the neighbor is a valid position, it is added
     * to an ArrayList of valid neighbors that is returned at the end of this method.
     *
     * @param currentPosition position algorithm is currently evaluating
     * @param grid Grid object that contains the information of the dimensions and obstacles
     * @return an ArrayList of all the neighbors of the current position that are valid positions
     */
    static ArrayList<Position> getValidNeighbors(Position currentPosition, Grid grid){
        ArrayList<Position> validNeighbors = new ArrayList<>();
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        Position north = new Position(currentX, currentY+1);
        Position south = new Position(currentX, currentY-1);
        Position east = new Position(currentX+1, currentY);
        Position west = new Position(currentX-1, currentY);
        ArrayList<Position> possibleNeighbors = new ArrayList<>(Arrays.asList(north, south, east, west));
        for(Position newPosition : possibleNeighbors){
            if(isValidPosition(newPosition, grid)){
                validNeighbors.add(newPosition);
            }
        }
        return validNeighbors;
    }

    /**
     * This method checks with the grid to determine whether or not that position is
     * a valid position. It would be invalid it lies outside of the dimensions of the grid
     * or if there is an obstacle at that position.
     *
     * @param newPosition possible position to move to from the current position
     * @param grid Grid object that contains the information of the dimensions
     *             and the locations of the obstacles
     * @return true if it is a valid position and false if it is not
     */
    static boolean isValidPosition(Position newPosition, Grid grid){
        Position[] allObstacles = grid.getObstacles();
        int dimensionMax = grid.getDimension();
        if(newPosition.getX()>dimensionMax-1 || newPosition.getY()>dimensionMax-1
                || newPosition.getX()<0 || newPosition.getY()<0){
            return false;
        }
        for(Position obstacle : allObstacles){
            if(newPosition.equals(obstacle)){
                return false;
            }
        }
        return true;
    }

    /**
     * This method determines whether or not a set contains a specified position. This is used
     * rather than the default set.contains() because multiple variables within the Position
     * objects have to be evaluated.
     *
     * @param set set of Position objects
     * @param B Position to be evaluated
     * @return true if Position B is in the set and false if it is not
     */
    static boolean contains(Set<Position> set, Position B){
        boolean setContainsB = false;
        Iterator<Position> openSetIterator = set.iterator();
        if(set.size() == 0){
            setContainsB = false;
        }
        else if(openSetIterator.next().equals(B)) {
            setContainsB = true;
        }
        else {
            while(openSetIterator.hasNext()){
                if(openSetIterator.next().equals(B)){
                    setContainsB = true;
                }
            }
        }
        return setContainsB;
    }

        /**
     * This method takes current position (which is the end position) and constructs a path by connecting
     * each position to where it came from, to where that came from, and so on until the starting
     * position is reached
     *
     * @param cameFrom HashMap that contains each position and the position that they came from
     * @param currentPosition position algorithm is currently evaluating, which in this case is the
     *                        end position
     * @return string of path from ending position to starting
     */
    private static String constructShortestPath(HashMap<Position, Position> cameFrom, Position currentPosition){
        String totalPath = "x="+currentPosition.getX()+", y="+currentPosition.getY();
        while(cameFrom.containsKey(currentPosition)){
            currentPosition = cameFrom.get(currentPosition);
            totalPath += "\nx="+currentPosition.getX()+", y="+currentPosition.getY();
        }
        return totalPath;
    }
}
