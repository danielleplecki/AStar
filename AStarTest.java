package grid;

import org.junit.BeforeClass;
import org.junit.Test;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test class to test A* algorithm and other methods in the AStar class
 * @author Danielle Plecki
 */
public class AStarTest extends AStar{
    private static Grid instructorTestGrid;
    private static String instructorTestSolution = "";
    private static Grid testGridA;
    private static String gridASolution = "";

    @Test
    public void checkAlgorithm() throws Exception {
        //InstructorExample
        Position start = instructorTestGrid.getStart();
        Position end = instructorTestGrid.getEnd();
        String output = AStar.algorithm(instructorTestGrid, start, end);
        assertTrue(output.equals(instructorTestSolution));

        //ExampleA
        start = testGridA.getStart();
        end = testGridA.getEnd();
        output = AStar.algorithm(testGridA, start, end);
        assertTrue(output.equals(gridASolution));
    }

    @Test
    public void checkHEstimate() throws Exception{
        Position end = instructorTestGrid.getEnd();
        Position A = new Position(7, 8);
        double hScoreA = hEstimate(A, end);
        assertTrue(hScoreA == 7.211102550927978);

    }

    @Test
    public void checkValidNeighbors() throws Exception{
        Position withObstacles = new Position(4, 4);
        ArrayList<Position> testNeighbors = getValidNeighbors(withObstacles, instructorTestGrid);
        assertFalse(testNeighbors.size() == 6); //Should only have 2 due to obstacles
        Position outOfBounds = new Position(1,9);
        testNeighbors = getValidNeighbors(outOfBounds, instructorTestGrid);
        assertTrue(testNeighbors.size() == 3); //1 is out of bounds
    }

    @Test
    public void checkValidPosition() throws Exception{
        Position negativeP = new Position(-3, 4);
        Position obstacleP = new Position(2, 3);
        Position pastDimensionP = new Position(5, 14);
        Position equalDimensionP = new Position(10, 2);
        Position validP = new Position(8, 7);
        assertFalse(isValidPosition(negativeP, instructorTestGrid));
        assertFalse(isValidPosition(obstacleP, instructorTestGrid));
        assertFalse(isValidPosition(pastDimensionP, instructorTestGrid));
        assertFalse(isValidPosition(equalDimensionP, instructorTestGrid));
        assertTrue(isValidPosition(validP, instructorTestGrid));
    }

    @Test
    public void checkContains() throws Exception{
        Position p1 = new Position(3, 4);
        Position p2 = new Position(1, 1);
        Position p3 = new Position(7, 3);
        Position p4 = new Position(10,6);
        Position p5 = new Position(4, 5);
        Set<Position> testSet = new HashSet<> (Arrays.asList(p1, p2, p3, p4, p5));
        Position pContain = new Position(7, 3);
        Position pNotContain = new Position(2 ,4);
        assertTrue(contains(testSet, pContain));
        assertFalse(contains(testSet, pNotContain));
    }

    @BeforeClass
    public static void setUp() throws Exception{
        instructorTestGrid = Main.setGrid(Paths.get("C:\\Users\\danie\\IdeaProjects\\AStar\\Example Grids\\InstructorExample.json"));
        instructorTestSolution =
                "x=3, y=2\n" +
                "x=4, y=2\n" +
                "x=5, y=2\n" +
                "x=6, y=2\n" +
                "x=6, y=3\n" +
                "x=6, y=4\n" +
                "x=6, y=5\n" +
                "x=5, y=5\n" +
                "x=4, y=5\n" +
                "x=4, y=4\n" +
                "x=3, y=4\n" +
                "x=2, y=4\n" +
                "x=1, y=4\n" +
                "x=0, y=4\n" +
                "x=0, y=3\n" +
                "x=0, y=2\n" +
                "x=0, y=1\n" +
                "x=0, y=0";
        testGridA = Main.setGrid(Paths.get("C:\\Users\\danie\\IdeaProjects\\AStar\\Example Grids\\ExampleA.json"));
        gridASolution =
                "x=1, y=8\n" +
                "x=0, y=8\n" +
                "x=0, y=9\n" +
                "x=0, y=10\n" +
                "x=0, y=11\n" +
                "x=1, y=11\n" +
                "x=2, y=11\n" +
                "x=3, y=11\n" +
                "x=4, y=11\n" +
                "x=4, y=10\n" +
                "x=4, y=9\n" +
                "x=5, y=9\n" +
                "x=6, y=9\n" +
                "x=7, y=9\n" +
                "x=8, y=9\n" +
                "x=8, y=8\n" +
                "x=8, y=7\n" +
                "x=8, y=6\n" +
                "x=8, y=5\n" +
                "x=8, y=4\n" +
                "x=8, y=3\n" +
                "x=9, y=3";
    }
}