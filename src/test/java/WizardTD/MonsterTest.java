package WizardTD;


import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterTest {
    private Monster monster;
    private PImage mockImage;
    private ActionButton doubleTimeButton;
    private ActionButton pauseButton;

    @BeforeEach
    public void setUp() {
        // Create a mock PImage
        mockImage = new PImage();

        // Initialize a Monster with some initial values
        monster = new Monster("Germelin", 100, 5, 2, 10, 3, mockImage, 0, 0);
        
        doubleTimeButton = new ActionButton(0, 0, 0, 0, "", 'D', "");
        pauseButton = new ActionButton(0, 0, 0, 0, "", 'P', "");
    }

    @Test
    public void testInitialValues() {
        assertEquals("Germelin", monster.getType());
        assertEquals(100, monster.getHp(), 0.01);
        assertEquals(5, monster.getSpeed(), 0.01);
        assertEquals(2, monster.getArmour(), 0.01);
        assertEquals(10, monster.getManaGainedOnKill(), 0.01);
        assertEquals(100, monster.getCurrentHp(), 0.01);
        assertEquals(mockImage, monster.getMonsterImage());
        assertEquals(3, monster.getQuantity());
        assertFalse(monster.getFreeze());
    }

    @Test
    public void testSetSpeed() {
        monster.setSpeed(8);
        assertEquals(8, monster.getSpeed(), 0.01);
    }

    @Test
    public void testSetMonsterImage() {
        PImage newImage = new PImage();
        monster.setMonsterImage(newImage);
        assertEquals(newImage, monster.getMonsterImage());
    }

    @Test
    public void testDeathAnimation() {
        PImage[] monsterImages = new PImage[5];
        for (int i = 0; i < 5; i++) {
            monsterImages[i] = new PImage();
        }

        boolean isMonsterDead = false;
        for (int i = 0; i < 16; i++) {
            isMonsterDead = monster.deathAnimation(monsterImages);
        }

        assertTrue(isMonsterDead);
    }

    @Test
    public void testRestart() {
        Monster monster1 = new Monster("Germelin", 100, 5, 2, 10, 3, mockImage, 0, 0);
        monster1.setPositionX(19);
        monster.setPositionY(15);
        monster1.setIndex(2);
        monster1.setCountTo32(15);

        monster1.restart();

        assertEquals(monster1.getInitialPositionX(), monster1.getPositionX(), 0.01);
        assertEquals(monster1.getInitialPositionY(), monster1.getPositionY(), 0.01);
        assertEquals(0, monster1.getIndex());
        assertEquals(0, monster1.getCountTo32(), 0.01);
    }

    @Test
    public void testFreezeTime() {
        monster.setIceDuration(2);
        
        monster.freezeTime(60); // Assuming frame rate is 60
        assertTrue(monster.getFreeze());
        assertEquals(0.017, monster.getIceCount(), 0.01);

        monster.freezeTime((float)0.4); // Assuming frame rate is 60
        assertFalse(monster.getFreeze());
    }

    public void testchangeManaGainOnKill(){
        monster.changeManaGainOnKill(2);
        assertEquals(30, monster.getManaGainedOnKill());
    }


    @Test
    public void testUpdateWithoutDoubleTimeOrPause() {
        // Create a path for the monster
        List<String> path = new ArrayList<>();
        path.add("R");
        // path.add("U");

        // Set the monster's initial position
        monster.setPositionX(10);
        monster.setPositionY(10);

        // Ensure the initial state of the monster
        assertEquals(0, monster.getIndex());
        assertEquals(0, monster.getCountTo32());

        // Simulate the update process
        monster.update(path, doubleTimeButton, pauseButton);
        float speed = monster.getSpeed();

        // Check the updated position
        assertEquals(15, monster.getPositionX(), 0.01);
        assertEquals(10 , monster.getPositionY(), 0.01);
    }

    @Test
    public void testUpdateWithDoubleTime() {
        // Create a path for the monster
        List<String> path = new ArrayList<>();
        path.add("U");
        path.add("L");
        path.add("R");
        path.add("D");

        // Set the monster's initial position
        monster.setPositionX(10);
        monster.setPositionY(10);

        // Ensure the initial state of the monster
        assertEquals(0, monster.getIndex());
        assertEquals(0, monster.getCountTo32());

        // Enable the DoubleTime button
        doubleTimeButton.setYellowOn(true);

        // Simulate the update process
        monster.update(path, doubleTimeButton, pauseButton);

        // Check the updated position with double speed
        assertEquals(10, monster.getPositionX(), 0.01);
        assertEquals(0, monster.getPositionY(), 0.01);
    }


    @Test
    public void testUpdateWithPause() {
        // Create a path for the monster
        List<String> path = new ArrayList<>();
        path.add("U");
        path.add("R");
        path.add("D");

        // Set the monster's initial position
        monster.setPositionX(10);
        monster.setPositionY(10);

        // Ensure the initial state of the monster
        assertEquals(0, monster.getIndex());
        assertEquals(0, monster.getCountTo32());

        // Enable the Pause button
        pauseButton.setYellowOn(true);

        // Simulate the update process with pause
        monster.update(path, doubleTimeButton, pauseButton);

        // Check that the monster doesn't move
        assertEquals(10, monster.getPositionX(), 0.01);
        assertEquals(10, monster.getPositionY(), 0.01);

    }

    @Test
    public void testUpdateQuantity() {
        monster.updateQuantity(); // Call the method to reduce quantityGenerate by 1
        assertEquals(2, monster.getquantityGenerate()); 
    }

    @Test
    public void testGetQuantityGenerate() {
        assertEquals(3, monster.getquantityGenerate()); 
    }

    @Test
    public void testChangeManaGainOnKill() {
        float initialManaGainedOnKill = monster.getManaGainedOnKill();
        float gainMultiplier = 1.5f;

        // Call the method to change mana gain
        monster.changeManaGainOnKill(gainMultiplier);

        // Verify that the manaGainedOnKill was updated correctly
        float expectedManaGainedOnKill = initialManaGainedOnKill * gainMultiplier;
        assertEquals(expectedManaGainedOnKill, monster.getManaGainedOnKill(), 0.01); // Use a delta for float comparison
    }

    @Test
    public void testFreeze(){
        monster.setFreeze(false);
        assertFalse(monster.getFreeze());
    }

    @Test
    public void testGetCurrentDeathFrame() {
        // Initially, the current death frame should be 0
        assertEquals(0, monster.getCurrentDeathFrame());

        // Set the current death frame to a specific value
        monster.setCurrentDeathFrame(3);

        // Check if the current death frame matches the value we set
        assertEquals(3, monster.getCurrentDeathFrame());
    }

    @Test
    public void testGetFixedSpeed() {
        // Create a Monster instance
        Monster monster = new Monster("TestMonster", 100.0f, 2.0f, 5.0f, 10.0f, 1, null, 0.0f, 0.0f);

        // Initially, the fixedSpeed should match the speed value
        assertEquals(2.0f, monster.getFixedSpeed(), 0.01);

        // Change the speed value
        monster.setSpeed(3.0f);

        // Check if the fixedSpeed still matches the initial value
        assertEquals(2.0f, monster.getFixedSpeed(), 0.01);
    }



}
