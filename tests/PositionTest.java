package grid;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Test class to test Position methods
 * @author Danielle Plecki
 */
public class PositionTest extends Position{
    private static Grid instructorTestGrid;

    @BeforeClass
    public static void setUp() throws Exception{
        instructorTestGrid = Main.setGrid(Paths.get("C:\\Users\\danie\\IdeaProjects\\AStar\\Example Grids\\InstructorExample.json"));
    }

    @Test
    public void newPosition() throws Exception {
        Position A = new Position(5 ,8);
        assertTrue(A.getX() == 5 && A.getY() == 8);
        Position B = new Position(-10, 2);
        assertFalse(B.getX() == 10 && B.getY() == 2);
        Position C = new Position(8030239, 2038107);
        assertTrue(C.getX() == 8030239 && C.getY() == 2038107);
    }

    @Test
    public void checkEquals() throws Exception{
        Position first = new Position(32, 14);
        Position second = new Position(32, 14);
        assertTrue(first.equals(second));
        //End position in InstructorExample.json is (3,2)
        Position endCorrect = new Position(3, 2);
        assertTrue(instructorTestGrid.getEnd().equals(endCorrect));
        //Start position in InstructorExample.json is (0,0)
        assertFalse(instructorTestGrid.getStart().equals(endCorrect));
    }

}