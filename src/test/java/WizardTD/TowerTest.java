package WizardTD;


import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class TowerTest {
    private Tower tower;
    private PImage[] testTowerImages;
    private PImage testFireballImg;
    private PImage testImage;
    private List<Monster> monsters;

    @BeforeEach
    public void setUp() {
        // Initialize Tower with test data
        testFireballImg = new PImage();
        testTowerImages = new PImage[6];
        testImage = new PImage();
        monsters = new ArrayList<>();
        tower = new Tower(50, 100, (float)1.5, 100, 50, 50, testImage, testFireballImg, testTowerImages, true, false);
        
    }

    @Test
    public void testGetRangeCost(){
        assertEquals(20, tower.getRangeCost());
    }

    @Test
    public void testGetSpeedCost(){
        assertEquals(20, tower.getSpeedCost());
    }

    @Test
    public void testGetDamageCost(){
        assertEquals(20, tower.getDamageCost());
    }

    @Test
    public void testGetPositionX(){
        assertEquals(50, tower.getPositionX());
    }

    @Test
    public void testGetPositionY(){
        assertEquals(50, tower.getPositionY());
    }

    @Test
    public void testGetTowerCost() {
        assertEquals(50, tower.getTowerCost());
    }

    @Test
    public void testGetInitialTowerRange() {
        assertEquals(100, tower.getInitialTowerRange());
    }

    @Test
    public void testGetInitialTowerFiringSpeed() {
        assertEquals(1/1.5, tower.getInitialTowerFiringSpeed(), 0.001);
    }

    @Test
    public void testGetInitialTowerDamage() {
        assertEquals(100, tower.getInitialTowerDamage());
    }

    @Test
    public void testUpgradeRange() {
        tower.upgradeRange();
        assertEquals(132, tower.getInitialTowerRange());
    }

    @Test
    public void testUpgradeSpeed() {
        tower.upgradeSpeed();
        assertEquals(0.5, tower.getInitialTowerFiringSpeed());
    }

    @Test
    public void testUpgradeDamage() {
        tower.upgradeDamage();
        assertEquals(150, tower.getTowerDamage());
    }

    @Test
    public void testGetFireBallList() {
        List<Fireball> fireballList = tower.getFireBallList();

        // Verify that the returned list is not null
        assertNotNull(fireballList);

        // Verify that the list is initially empty
        assertEquals(0, fireballList.size());

        Fireball mockFireball = new Fireball(0, 0, null, null, 0.0f);
        fireballList.add(mockFireball);

        // Now, the list should have one Fireball
        assertEquals(1, fireballList.size());
    }


    @Test
    public void testSetTower() {
        // Create a new test PImage for the tower image
        PImage newTowerImage = new PImage();

        // Set the tower image using the setTower method
        tower.setTower(newTowerImage);

        // Verify that the tower image has been correctly set
        assertSame(newTowerImage, tower.getTower());
    }

    @Test
    public void testChangeTowerImage_Fireball() {
        // Set up the Tower as a fireball tower
        tower.isFireBall = true;
        tower.RangeLevel = 2;
        tower.SpeedLevel = 2;
        tower.DamageLevel = 2;

        // Call the changeTowerImage method
        tower.changeTowerImage();

        // Verify that the tower image has been correctly changed
        assertSame(testTowerImages[2], tower.getTower());
    }

     @Test
    public void testChangeTowerImage_Iceball1() {
        // Set up the Tower as an iceball tower
        tower.isIceBall = true;
        tower.RangeLevel = 1;
        tower.SpeedLevel = 1;
        tower.DamageLevel = 1;

        // Call the changeTowerImage method
        tower.changeTowerImage();

        // Verify that the tower image has been correctly changed
        assertSame(testTowerImages[4], tower.getTower());
    }

    
    @Test
    public void testChangeTowerImage_fireball1() {
        // Set up the Tower as a generic tower
        tower.isFireBall = true;
        tower.RangeLevel = 2;
        tower.SpeedLevel = 2;
        tower.DamageLevel = 2;

        // Call the changeTowerImage method
        tower.changeTowerImage();
        assertSame(testTowerImages[1], tower.getTower());

    }

    @Test
    public void testChangeTowerImage_Iceball2() {
        // Set up the Tower as an iceball tower
        tower.isIceBall = true;
        tower.RangeLevel = 2;
        tower.SpeedLevel = 2;
        tower.DamageLevel = 2;

        // Call the changeTowerImage method
        tower.changeTowerImage();

        // Verify that the tower image has been correctly changed
        assertSame(testTowerImages[5], tower.getTower());
    }

    @Test
    public void testChangeTowerImage_fireball2() {
        // Set up the Tower as a generic tower
        tower.isFireBall = true;
        tower.RangeLevel = 2;
        tower.SpeedLevel = 2;
        tower.DamageLevel = 2;

        // Call the changeTowerImage method
        tower.changeTowerImage();
        assertSame(testTowerImages[2], tower.getTower());
    }

    @Test
    public void testIsMonsterInRangeWithNoMonsters() {
        // Test when there are no monsters in range
        tower.isMonsterInRange(monsters, 0);
        assertEquals(0, tower.getFireBallList().size());
    }

    @Test
    public void testIsMonsterisoutOfRange() {
        // Test when there is one monster in range
        Monster monster = new Monster("Germelin", 100, 5, 2, 10, 3, null, 0, 0);
        monster.setPositionX(0);
        monster.setPositionY(0);
        monsters.add(monster);

        tower.isMonsterInRange(monsters, 0);
        assertEquals(0, tower.getFireBallList().size());
    }
    public void testIsMonsterisInRange() {
        // Test when there is one monster in range
        Monster monster = new Monster("Germelin", 100, 5, 2, 10, 3, null, 0, 0);
        monster.setPositionX(tower.getPositionX() + tower.getInitialTowerRange()/2);
        monster.setPositionY(tower.getPositionY() + tower.getInitialTowerRange()/2);
        monsters.add(monster);
        tower.isFireBall = true;

        tower.isMonsterInRange(monsters, 1000);
        assertEquals(1, tower.getFireBallList().size());
    }

    @Test
    public void testIsMonsterInRangeWithMultipleMonstersInRange() {
        Monster monster1 = new Monster("Germelin", 100, 5, 2, 10, 3, null, 0, 0);
        monster1.setPositionX(tower.getPositionX() + tower.getInitialTowerRange()/2);
        monster1.setPositionY(tower.getPositionY() + tower.getInitialTowerRange()/2);

        Monster monster2 = new Monster("worm", 100, 5, 2, 10, 3, null, 0, 0);
        monster2.setPositionX(tower.getPositionX() + tower.getInitialTowerRange()/3);
        monster2.setPositionY(tower.getPositionY() + tower.getInitialTowerRange()/3);

        monsters.add(monster1);
        monsters.add(monster2);

        tower.isFireBall = false;
        tower.isMonsterInRange(monsters, 1000);
        assertEquals(1, tower.getFireBallList().size());
    }

   

}
