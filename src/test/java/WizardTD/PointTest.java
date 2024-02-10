package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PointTest {

    @Test
    public void testGetRow() {
        List<String> movements = new ArrayList<>();
        Point point = new Point(3, 4, movements);
        assertEquals(3, point.getrow());
    }

    @Test
    public void testGetCol() {
        List<String> movements = new ArrayList<>();
        Point point = new Point(3, 4, movements);
        assertEquals(4, point.getcol());
    }

    @Test
    public void testGetMovements() {
        List<String> movements = new ArrayList<>();
        movements.add("UP");
        movements.add("LEFT");
        Point point = new Point(3, 4, movements);
        List<String> resultMovements = point.getMovements();
        assertEquals(movements, resultMovements);
    }
}
