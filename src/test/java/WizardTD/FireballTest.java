package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PImage;

public class FireballTest {
    private Fireball fireball;
    private Monster target;
    private PImage fireballImg;
    private PImage mockImage;
    private ActionButton doubleSpeedButton;
    private ActionButton stopButton;

    @BeforeEach
    public void setUp() {
        mockImage = new PImage();

        // Initialize a Monster with some initial values
        target = new Monster("Germelin", 100, 5, 2, 10, 3, mockImage, 0, 0);
        fireballImg = new PImage(/* Initialize PImage parameters here */);
        fireball = new Fireball(0, 0, target, fireballImg, 10); // Adjust initial values accordingly
        doubleSpeedButton = new ActionButton(0, 0, 0, 0, "", 'F', "");
        stopButton = new ActionButton(0, 0, 0, 0, "", 'P', "");
    }

    @Test
    public void testGetFreeze() {
        assertFalse(fireball.getFreeze());
    }

    @Test
    public void testMove() {
        // You can add test cases for the move method here
        // Test different scenarios, such as with and without double speed or pause
    }

    @Test
    public void testGetFireBallImage() {
        assertEquals(fireballImg, fireball.getFireBallImage());
    }

    @Test
    public void testSetFireBallImage() {
        PImage newFireballImg = new PImage(/* Initialize a new PImage for testing */);
        fireball.setFireBallImage(newFireballImg);
        assertEquals(newFireballImg, fireball.getFireBallImage());
    }

    @Test
    public void testGetX() {
        assertEquals(0, fireball.getX(), 0.001); // Adjust the expected value and delta accordingly
    }

    @Test
    public void testGetY() {
        assertEquals(0, fireball.getY(), 0.001); // Adjust the expected value and delta accordingly
    }

    @Test
    public void testSetTarget() {
        Monster newTarget = new Monster("worm", 50, 5, 1, 50, 2, mockImage, 0, 0);
        fireball.setTarget(newTarget);
        assertEquals(newTarget, fireball.getTarget());
    }

    @Test
    public void testMoveWithoutDoubleSpeedAndPause() {
        
        // Set the target for the fireball
        fireball.setTarget(target);
        
        // Calculate the expected position after calling move
        float initialX = fireball.getX();
        float initialY = fireball.getY();
        float targetX = target.getPositionX() + 7;  // Adjust as necessary
        float targetY = target.getPositionY() + 8;  // Adjust as necessary
        double dx = targetX - initialX;
        double dy = targetY - initialY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Call the move method with double speed OFF and pause OFF
        doubleSpeedButton.setYellowOn(false);
        stopButton.setYellowOn(false);
        fireball.move(doubleSpeedButton, stopButton);
        
        // Calculate the expected position after the move method
        float expectedX, expectedY;
        if (distance <= 5.0) {
            expectedX = targetX;
            expectedY = targetY;
        } else {
            double normalizedDX = dx / distance;
            double normalizedDY = dy / distance;
            expectedX = (float) (initialX + normalizedDX * 5.0);
            expectedY = (float) (initialY + normalizedDY * 5.0);
        }
        
        // Assert that the fireball's position has been updated correctly
        assertEquals(expectedX, fireball.getX(), 0.001); // Adjust delta (tolerance) as needed
        assertEquals(expectedY, fireball.getY(), 0.001); // Adjust delta (tolerance) as needed
    }

    @Test
    public void testMoveWithDoubleSpeedAndPause() {
        // Save the initial position of the fireball
        float initialX = fireball.getX();
        float initialY = fireball.getY();
        
        // Create ActionButton instances with double speed and pause ON
        doubleSpeedButton.setYellowOn(true);
        stopButton.setYellowOn(true);
        
        // Call the move method
        fireball.move(doubleSpeedButton, stopButton);
        
        // Verify that the fireball's position remains unchanged
        assertEquals(initialX, fireball.getX(), 0.001); // Adjust delta (tolerance) as needed
        assertEquals(initialY, fireball.getY(), 0.001); // Adjust delta (tolerance) as needed
    }

    @Test
    public void testMoveWithDoubleSpeedAndPauseOff() {
       
        // Set the target for the fireball
        fireball.setTarget(target);
        
        // Calculate the expected position after calling move with double speed ON and pause OFF
        float initialX = fireball.getX();
        float initialY = fireball.getY();
        float targetX = target.getPositionX() + 7;  // Adjust as necessary
        float targetY = target.getPositionY() + 8;  // Adjust as necessary
        double dx = targetX - initialX;
        double dy = targetY - initialY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double speed = 10.0; // Double speed
        
        float expectedX, expectedY;
        if (distance <= speed) {
            expectedX = targetX;
            expectedY = targetY;
        } else {
            double normalizedDX = dx / distance;
            double normalizedDY = dy / distance;
            expectedX = (float) (initialX + normalizedDX * speed);
            expectedY = (float) (initialY + normalizedDY * speed);
        }
        
        // Call the move method with double speed ON and pause OFF
        doubleSpeedButton.setYellowOn(true);
        stopButton.setYellowOn(false);
        fireball.move(doubleSpeedButton, stopButton);
        
        // Verify that the fireball's position is updated correctly with double speed
        assertEquals(expectedX, fireball.getX(), 0.001); // Adjust delta (tolerance) as needed
        assertEquals(expectedY, fireball.getY(), 0.001); // Adjust delta (tolerance) as needed
    }

    @Test
    public void testDecreaseHPWhenMonsterIsHit() {
        float initialTowerDamage = (float)10.0;
        float armorMultiplier = (float)0.5; 
        
        // Set the target for the fireball
        fireball.setTarget(target);

        target.setHit(true);
        
        // Call the decreaseHP method
        fireball.decreaseHP(armorMultiplier);
        
        // Verify that the target monster's HP is reduced correctly
        float expectedHP = 100 - (initialTowerDamage * armorMultiplier);
        assertEquals(expectedHP, target.getCurrentHp(), 0.001); // Adjust delta (tolerance) as needed
    }

     public void testDecreaseHPWhenMonsterIsNotHit() {
        float armorMultiplier = (float)0.5; 
        
        // Set the target for the fireball
        fireball.setTarget(target);

        target.setHit(false);
        
        // Call the decreaseHP method
        fireball.decreaseHP(armorMultiplier);
        
        // Verify that the target monster's HP is reduced correctly
        assertEquals(100, target.getCurrentHp(), 0.001); // Adjust delta (tolerance) as needed
    }

    @Test
    public void testDecreaseHPWithTargetHitAndLowRemainingHP() {
        // Create a target monster with low initial HP
        Monster target = new Monster("Germelin", 1, 5, 2, 10, 3, mockImage, 0, 0);
        float initialTowerDamage = (float)100.0;
        float armorMultiplier = (float)0.5;

        Fireball fireball = new Fireball((float)0.0, (float)0.0, target, null, initialTowerDamage);

        // Set the target as hit (true) and call decreaseHP
        target.setHit(true);
        // Set the target's current HP to a value lower than the damage
        target.setCurrentHp(initialTowerDamage * armorMultiplier - 10);
        fireball.decreaseHP(armorMultiplier);

        // Verify that the target's HP is set to 0 (it should not go below 0)
        assertEquals(0.0, target.getCurrentHp(), 0.001);
    }




}
