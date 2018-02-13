package grid;

import org.junit.BeforeClass;
import org.junit.Test;
import java.nio.file.Paths;
import static org.junit.Assert.*;

/**
 * Test class for testing Grid methods
 * @author Danielle Plecki
 */
public class GridTest extends Grid{
    private static Grid instructorTestGrid;

    @BeforeClass
    public static void setUp() throws Exception{
        instructorTestGrid = Main.setGrid(Paths.get("C:\\Users\\danie\\IdeaProjects\\AStar\\Example Grids\\InstructorExample.json"));
    }

    @Test
    public void testDimension() throws Exception {
        //Dimension of InstructorExample.json is 10x10
        assertTrue(instructorTestGrid.getDimension() == 10);
    }

    @Test
    public void testStart() throws Exception{
        //Start position in InstructorExample.json is (0,0)
        Position startCorrect = new Position(0,0);
        assertTrue(instructorTestGrid.getStart().equals(startCorrect));
    }

    @Test
    public void testEnd() throws Exception{
        //End position in InstructorExample.json is (3,2)
        Position endCorrect = new Position(3, 2);
        assertTrue(instructorTestGrid.getEnd().equals(endCorrect));
    }

    @Test
    public void testObstacles() throws Exception{
        //There are 9 obstacles in InstructorExample.json
        Position[] allObstacles = instructorTestGrid.getObstacles();
        assertTrue(allObstacles.length == 9);
        Position notObstacle = new Position(4,4);
        assertFalse(allObstacles[7].equals(notObstacle));
    }
}