package WizardTD;


import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class ManaTest {
    private Mana mana;
    private ActionButton doubleSpeedButton;
    private ActionButton stopButton;
    private PApplet applet;

    @BeforeEach
    public void setUp() {
        // Initialize Mana with some initial values
        mana = new Mana(100, 200, 10, 5, 1.2f, 1.5f, 5);

        // Create mock ActionButton instances
        doubleSpeedButton = new ActionButton(0, 0, 0, 0, "", 'F', "");
        stopButton = new ActionButton(0, 0, 0, 0, "", 'P', "");

        applet = new PApplet();
    }

    @Test
    public void testInitialValues() {
        assertEquals(100, mana.getCurrentMana(), 0.01);
        assertEquals(200, mana.getManaCap(), 0.01);
        assertEquals(10, mana.getInitialCost());
    }

    @Test
    public void testChangeCost() {
        mana.changeCost();
        assertEquals(90, mana.getCurrentMana(), 0.01);
        assertEquals(15, mana.getInitialCost());
        assertEquals(240, mana.getManaCap(), 0.01);
    }

    @Test
    public void testSetCurrentMana() {
        float expectedMana = (float)50.0; // Set the expected mana value

        // Call the setCurrentMana method to set the mana value
        mana.setCurrentMana(expectedMana);

        // Verify that the currentMana has been set to the expected value
        assertEquals(expectedMana, mana.getCurrentMana(), 0.001);
    }

    @Test
    public void testSetInitialManaGainedPerSecond() {
        float expectedManaGainedPerSecond = (float)5.0; // Set the expected mana gained per second value

        // Call the setinitialManaGainedPerSecond method to set the value
        mana.setinitialManaGainedPerSecond(expectedManaGainedPerSecond);

        // Verify that the initialManaGainedPerSecond has been set to the expected value
        assertEquals(expectedManaGainedPerSecond, mana.getinitialManaGainedPerSecond(), 0.001);
    }


    @Test
    public void testUpdateManaWithStopOn() {
        PApplet applet = new PApplet();
        // Create an instance of your class and initialize variables
        stopButton.setYellowOn(true); // Stop is ON
        doubleSpeedButton.setYellowOn(false);
        boolean pause = false;
        float frameRate = (float)60.0; // Adjust as necessary
        float currentMana = (float)100.0; // Initial currentMana

        mana.setCurrentMana(currentMana); // Set initial currentMana

        // Call the method that updates currentMana
        mana.changeMana(doubleSpeedButton, stopButton,pause , 10);

        // Verify that currentMana remains unchanged (no change when stop is ON)
        assertEquals(currentMana, mana.getCurrentMana(), frameRate);
    }

    @Test
    public void testUpdateManaWithDoubleSpeedOn() {
        stopButton.setYellowOn(false); // Stop is ON
        doubleSpeedButton.setYellowOn(true);
        boolean pause = false;
        float initialManaGainedPerSecond = mana.getinitialManaGainedPerSecond();
        float frameRate = (float)60.0;
        float currentMana =  mana.getCurrentMana();

        // Calculate the expected currentMana value based on double speed
        float expectedMana = currentMana + (2 * initialManaGainedPerSecond) / frameRate;

        // Call the method that updates currentMana
        mana.changeMana(doubleSpeedButton, stopButton, pause , frameRate);

        // Verify that currentMana has been correctly updated
        assertEquals(expectedMana, mana.getCurrentMana(), 0.001);
    }

    @Test
    public void testUpdateManaWithDoubleSpeedOff() {
        stopButton.setYellowOn(false); // Stop is ON
        doubleSpeedButton.setYellowOn(false);
        boolean pause = false;
        float initialManaGainedPerSecond = mana.getinitialManaGainedPerSecond();
        float frameRate = (float)60.0;
        float currentMana = mana.getCurrentMana();


        // Call the method that updates currentMana
        mana.changeMana(doubleSpeedButton, stopButton, pause, frameRate);

        // Calculate the expected currentMana value based on regular speed
        float expectedMana = currentMana + initialManaGainedPerSecond / frameRate;

        // Verify that currentMana has been correctly updated
        assertEquals(expectedMana, mana.getCurrentMana(), 0.001);
    }

    @Test
    public void testUpdateManaWithExceedingManaCap() {
        stopButton.setYellowOn(false); // Stop is ON
        doubleSpeedButton.setYellowOn(false);
        boolean pause = false;
        float manaCap = mana.getManaCap();
        float frameRate = (float)60.0;
        mana.setCurrentMana(manaCap + 5);

    
        // Call the method that updates currentMana
        mana.changeMana(doubleSpeedButton, stopButton, pause, frameRate);

        // Verify that currentMana has been capped to the manaCap value
        assertEquals(manaCap, mana.getCurrentMana(), 0.001);
    }

}
