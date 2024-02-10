package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.*;

public class WaveTest {
    private Wave wave;
    private PImage[] monsterImages;
    private JSONObject waveObj;

    @BeforeEach
    public void setUp() {
        // Create a sample JSONObject for testing
        waveObj = new JSONObject();
        waveObj.setFloat("duration", (float)10.0);
        waveObj.setFloat("pre_wave_pause", (float)5.0);

        JSONArray monsters = new JSONArray();
        JSONObject monsterObj1 = new JSONObject();
        monsterObj1.setString("type", "gremlin");
        monsterObj1.setInt("hp", 100);
        monsterObj1.setFloat("speed", (float)2.0);
        monsterObj1.setFloat("armour", (float)0.5);
        monsterObj1.setInt("mana_gained_on_kill", 10);
        monsterObj1.setInt("quantity", 3);
        monsters.setJSONObject(0, monsterObj1);

        JSONObject monsterObj2 = new JSONObject();
        monsterObj2.setString("type", "worm");
        monsterObj2.setInt("hp", 50);
        monsterObj2.setFloat("speed", (float)3.0);
        monsterObj2.setFloat("armour", (float)0.3);
        monsterObj2.setInt("mana_gained_on_kill", 5);
        monsterObj2.setInt("quantity", 2);
        monsters.setJSONObject(1, monsterObj2);

        JSONObject monsterObj3 = new JSONObject();
        monsterObj3.setString("type", "beetle");
        monsterObj3.setInt("hp", 50);
        monsterObj3.setFloat("speed", (float)3.0);
        monsterObj3.setFloat("armour", (float)0.3);
        monsterObj3.setInt("mana_gained_on_kill", 5);
        monsterObj3.setInt("quantity", 2);
        monsters.setJSONObject(2, monsterObj3);


        waveObj.setJSONArray("monsters", monsters);

        // Create an array of PImages for testing
        monsterImages = new PImage[8];

        // Initialize the wave object
        wave = new Wave(waveObj, monsterImages);
    }

    @Test
    public void testGetPreWavePause() {
        assertEquals(5.0, wave.getPreWavePause(), 0.01);
    }

    @Test
    public void testGetDuration() {
        assertEquals(10.0, wave.getDuration(), 0.01);
    }

    public void testGetMonsters() {
        List<Monster> monsters = wave.getMonsters();
        assertEquals(5, monsters.size()); // Verify the size of the list (3 gremlins and 2 worms)
    }

    @Test
    public void testSetMonsters() {
        List<Monster> newMonsters = new ArrayList<>();
        newMonsters.add(new Monster("beetle", 80, (float)2.5, (float)0.4, 15, 4, null, 0, 40));
        wave.setMonsters(newMonsters);
        
        List<Monster> updatedMonsters = wave.getMonsters();

        assertEquals(1, updatedMonsters.size()); // Verify the size of the updated list (1 beetle)
    }

    @Test
    public void testFixedTotalMonster() {
        int totalMonsters = wave.fixedTotalMonster();
        assertEquals(7,  totalMonsters);
    }

    @Test
    public void testGetSpawnSpeed() {
        float SpawnSpeed = wave.getDuration()/wave.fixedTotalMonster();
        assertEquals(SpawnSpeed, wave.getSpawnSpeed(), 0.01);
    }

    @Test
    public void testGetFixedSpawnSpeed() {
        float FixedSpawnSpeed = wave.getDuration()/wave.fixedTotalMonster();
        assertEquals(FixedSpawnSpeed, wave.getFixedSpawnSpeed(), 0.01);
    }

    @Test
    public void testSetSpawnSpeed() {
        wave.setSpawnSpeed((float)3.0);
        assertEquals(3.0, wave.getSpawnSpeed(), 0.01); // Verify that the spawn speed was correctly set
    }
}
