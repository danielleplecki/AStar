package grid;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main - program to find shortest path between two positions on a grid
 *
 * @author Danielle Plecki
 */
public class Main {

    /**
     *This is the main method that handles user input and implements the A* search for the
     * specified grid. A file path is input by the user that contains the grid information
     * as well as all start and end positions. The method receives and prints a string from
     * the A* algorithm that lists the shortest path from end position to start position.
     *
     * @param args array of strings that accepts file location of json grid
     * @throws IOException throws exception while parsing json
     */
    public static void main(String[] args) throws IOException{
        Path filePath = Paths.get(args[0]);
        Grid grid = setGrid(filePath);
        Position startPosition = grid.getStart();
        Position endPosition = grid.getEnd();


        //Calls the A* algorithm to return the shortest path in order from end to start
        String path = AStar.algorithm(grid, startPosition, endPosition);
        System.out.println(path);

    }

    /**
     * This is a method used to take the file path from the user and retrieve the json
     * information. It then parses the json into a Grid object using Google's gson
     * library. The grid returned contains all the info needed such as dimensions,
     * start and end locations, and all obstacles.
     *
     * @param filePath path entered by user as json grid location
     * @return Grid object that contains all information needed for the search
     * @throws IOException throws exception from parsing into Grid object
     */
    static Grid setGrid(Path filePath) throws IOException{
        String content = new String(Files.readAllBytes(filePath));
        Gson gson = new Gson();
        return gson.fromJson(content, Grid.class);
    }
}
