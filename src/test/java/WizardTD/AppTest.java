package WizardTD;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.data.JSONArray;

public class AppTest {
    private String[] mapLayout = {
            "   X    X    X  ",
            " XXXXXXXXX XXX  ",
            " X X   X XXXXX  ",
            "            X   ",
            "            XXW ",
        };
    private App app;
    private Mana mana;
    private List<ActionButton> actionButtons;
    private Monster monster;

    @BeforeEach
    public void setUp() {
        app = new App();
        mana = new Mana(app.currentMana, app.manaCap, app.mana_pool_spell_initial_cost, app.mana_pool_spell_cost_increase_per_use, app.mana_pool_spell_cap_multiplier, app.mana_pool_spell_mana_gained_multiplier, app.initialManaGainedPerSecond);
        monster = new Monster("Germelin", 100, 5, 2, 10, 3, null, 0, 0);
        actionButtons = new ArrayList<>();
        actionButtons.add(new ActionButton(10, 10, 20, 20, "FF", 'f', "2 x speed"));
        actionButtons.add(new ActionButton(10, 10 + 50, 20, 20, "P", 'p', "PAUSE"));
        actionButtons.add(new ActionButton(10, 10 + 100, 20, 20, "T", 't', "Build\ntower"));
        actionButtons.add(new ActionButton(10, 10 + 150, 20, 20, "U1", '1', "Upgrade\nrange"));
        actionButtons.add(new ActionButton(10, 10 + 200, 20, 20, "U2", '2', "Upgrade\nspeed"));
        actionButtons.add(new ActionButton(10, 10 + 250, 20, 20, "U3", '3', "Upgrade\ndamage"));
        actionButtons.add(new ActionButton(10, 10 + 300, 20, 20, "M", 'm',"Mana pool\ncost:" + mana.getInitialCost()));
        actionButtons.add(new ActionButton(10, 10 + 350, 20, 20, "ICE", 'i',"Ice\nTower"));


    }

    @Test
    public void testCheckPath() {
       
        // Test cases with different 'X' character positions
        assertEquals(0, app.checkPath(mapLayout, 1, 4)); 
        assertEquals(1, app.checkPath(mapLayout, 1, 9)); 
        assertEquals(2, app.checkPath(mapLayout, 1, 7));
        assertEquals(3, app.checkPath(mapLayout, 1, 3));
        assertEquals(4, app.checkPath(mapLayout, 0, 3)); 
        assertEquals(5, app.checkPath(mapLayout, 1, 13)); 
        assertEquals(6, app.checkPath(mapLayout, 1, 1)); 
        assertEquals(7, app.checkPath(mapLayout, 1, 8)); // Test for top, down, left, and right
        assertEquals(8, app.checkPath(mapLayout, 2, 13)); // Test for top and left
        assertEquals(9, app.checkPath(mapLayout, 4, 12)); // Test for top and right
    }

    @Test
    public void testUpDateMap() {
        
        // Call the UpDateMap method
        app.mapLayout =  mapLayout;
        String[] updatedMapLayout = app.UpDateMap();
        // Perform assertions based on the expected updatedMapLayout
        // The expected updatedMapLayout should have extra spaces at the beginning and end of each row
        assertEquals(" " + mapLayout[0] + " ", updatedMapLayout[1]);
        assertEquals(" " + mapLayout[1] + " ", updatedMapLayout[2]);
        assertEquals(" " + mapLayout[2] + " ", updatedMapLayout[3]);
        assertEquals(" " + mapLayout[3] + " ", updatedMapLayout[4]);
        assertEquals(" " + mapLayout[4] + " ", updatedMapLayout[5]);
        assertEquals("                  ", updatedMapLayout[0]);
        assertEquals("                  ", updatedMapLayout[updatedMapLayout.length - 1]);
    }

   public void testSolveMaze() {
        int X = 3;
        int Y = 0;

        List<String> result = app.solveMaze(mapLayout, X, Y);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertArrayEquals(new String[]{"D", "R", "R", "R", "R","R","R","R","D", "R", "R", "R", "R", "D", "D", "R", "R"} ,result.toArray());
    }

    @Test
    public void testIsValidMove() {
        int rows = 3;
        int cols = 4;

        assertTrue(app.isValidMove(mapLayout, rows, cols, 1, 1));
        assertFalse(app.isValidMove(mapLayout, rows, cols, 0, 0));
    }

}
