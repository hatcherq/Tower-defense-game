package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;//drawing 2D graphics and shapes.
import java.awt.geom.AffineTransform;//transforming (scaling, rotating, translating, etc.) 2D graphics and shapes.
import java.awt.image.BufferedImage;//working with images, allowing pixel-level access and manipulation.

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static final int[] ROW_OFFSETS = { -1, 0, 1, 0 };
    public static final int[] COL_OFFSETS = { 0, 1, 0, -1 };
    public static final char[] DIRECTIONS = { 'U', 'R', 'D', 'L' };

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;

    public static final int FPS = 60;

    public Random random = new Random();

    // Config
    public String configPath;
    public String layout;
    public String[] mapLayout;
    public String[] newMapLayout;

    // Images
    public PImage grassImg;
    public PImage shrubImg;
    public PImage wizardHouseImg;
    public PImage[] pathImages = new PImage[10];
    public PImage[] monsterImages = new PImage[11];
    public PImage[] towerImages = new PImage[6];
    public PImage fireballImg;
    public PImage iceBallImg;

    // manaCap
    public float currentMana;
    public float manaCap;
    public Mana mana;
    public int initialManaGainedPerSecond;

    // mana
    public int mana_pool_spell_initial_cost;
    public int mana_pool_spell_cost_increase_per_use;
    public float mana_pool_spell_cap_multiplier;
    public float mana_pool_spell_mana_gained_multiplier;
    public boolean pause;

    // game button
    public List<ActionButton> actionButtons;

    // Waves
    public JSONArray waveArray;
    public List<Wave> waveList;// save all the wave list in the config
    public int currentWaveNum; // indicate the current wave number to display the monster
    public int displayWaveNum;// wave number thats shown on the screen 
    public boolean wavePaused ;//check when the prewave finish 
    public float DurationWavePause;//both prewave and duration 
    public boolean firstWave; //to check when is the first wave 
    public boolean waveFinish; // check when the wave finish, immediately add a monster when it finish 
    public boolean win;
    public boolean lost;

    // Monster
    public JSONArray monsterArray; // store monster information
    public boolean MonsterOut;// allow monster to come out 
    public float PositionX;// random initial positionX 
    public float PositionY; // random initial positionY
    public ArrayList<Monster> displayMonsterList;// list to display multiple monsters
    public HashMap<Integer, float[]> initialPositions;
    public int totalmonsters;

    // Point for house
    public int[] WHouseP = new int[2];

    // tower
    public int initialTowerRange;
    public float initialTowerFiringSpeed;
    public float initialTowerDamage;
    public int towerCost;
    public List<Tower> TowerList ; // to store all the tower
    public List<Fireball> fireballList;
    public boolean towerExist; // check if the current position have tower 


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);

    }

    /**
     * Load all resources such as images. Initialise the elements such as the
     * player, enemies and map elements.
     */

    @Override
    public void setup() {
        frameRate(FPS);

        // Read JSON File
        JSONObject config = loadJSONObject(configPath);
        if (config == null) {
            // Handle the case where the JSON file couldn't be loaded
            System.out.println("Error loading JSON configuration file.");
            return; // Terminate the sketch
        }
        // Get Layout: level2.txt
        layout = config.getString("layout");
        mapLayout = loadStrings(layout);

        //Get WaveArray in conf
        waveArray = config.getJSONArray("waves");

        newMapLayout = UpDateMap();

        currentWaveNum = 0;
        displayWaveNum = 0;
        wavePaused = true;
        DurationWavePause = 0;
        firstWave = true;
        waveFinish = false;
        win = false;
        lost = false;
        totalmonsters = 0;
        

        //Monster
        MonsterOut = false;
        displayMonsterList = new ArrayList<Monster>();// list to display multiple monsters

        // Get mana
        currentMana = config.getFloat("initial_mana");
        manaCap = config.getFloat("initial_mana_cap");
        initialManaGainedPerSecond = config.getInt("initial_mana_gained_per_second");
        mana_pool_spell_initial_cost = config.getInt("mana_pool_spell_initial_cost");
        mana_pool_spell_cost_increase_per_use = config.getInt("mana_pool_spell_cost_increase_per_use");
        mana_pool_spell_cap_multiplier = config.getFloat("mana_pool_spell_cap_multiplier");
        mana_pool_spell_mana_gained_multiplier = config.getFloat("mana_pool_spell_mana_gained_multiplier");

        // get tower information
        initialTowerRange = config.getInt("initial_tower_range");
        initialTowerFiringSpeed = config.getFloat("initial_tower_firing_speed");
        initialTowerDamage = config.getFloat("initial_tower_damage");
        towerCost = config.getInt("tower_cost");
        TowerList = new ArrayList<Tower>(); // to store all the tower
        fireballList = new ArrayList<Fireball>();
        
        //Check if tower already exist in the current mouse place
        towerExist = false;

        // load images
        grassImg = loadImage("src/main/resources/WizardTD/grass.png");
        shrubImg = loadImage("src/main/resources/WizardTD/shrub.png");
        pathImages[0] = loadImage("src/main/resources/WizardTD/path0.png");// -
        pathImages[1] = loadImage("src/main/resources/WizardTD/path1.png");//
        pathImages[2] = loadImage("src/main/resources/WizardTD/path2.png");// T
        pathImages[3] = loadImage("src/main/resources/WizardTD/path3.png");// +
        pathImages[4] = rotateImageByDegrees(pathImages[0], 90); // |
        pathImages[5] = rotateImageByDegrees(pathImages[2], 90);// -|
        pathImages[6] = rotateImageByDegrees(pathImages[1], 270);
        pathImages[7] = rotateImageByDegrees(pathImages[2], 180);
        pathImages[8] = rotateImageByDegrees(pathImages[1], 90);
        pathImages[9] = rotateImageByDegrees(pathImages[1], 180);
        wizardHouseImg = loadImage("src/main/resources/WizardTD/wizard_house.png");
        monsterImages[0] = loadImage("src/main/resources/WizardTD/gremlin.png");
        monsterImages[1] = loadImage("src/main/resources/WizardTD/gremlin1.png");
        monsterImages[2] = loadImage("src/main/resources/WizardTD/gremlin2.png");
        monsterImages[3] = loadImage("src/main/resources/WizardTD/gremlin3.png");
        monsterImages[4] = loadImage("src/main/resources/WizardTD/gremlin4.png");
        monsterImages[5] = loadImage("src/main/resources/WizardTD/gremlin5.png");
        monsterImages[6] = loadImage("src/main/resources/WizardTD/worm.png");
        monsterImages[7] = loadImage("src/main/resources/WizardTD/beetle.png");
        monsterImages[8] = loadImage("src/main/resources/WizardTD/beetleFreeze.png");
        monsterImages[9] = loadImage("src/main/resources/WizardTD/wormFreeze.png");
        monsterImages[10] = loadImage("src/main/resources/WizardTD/gremlinFreeze.png");
        towerImages[0] = loadImage("src/main/resources/WizardTD/tower0.png");
        towerImages[1] = loadImage("src/main/resources/WizardTD/tower1.png");
        towerImages[2] = loadImage("src/main/resources/WizardTD/tower2.png");
        towerImages[3] = loadImage("src/main/resources/WizardTD/towerIce0.png");
        towerImages[4] = loadImage("src/main/resources/WizardTD/towerIce1.png");
        towerImages[5] = loadImage("src/main/resources/WizardTD/towerIce2.png");
        fireballImg = loadImage("src/main/resources/WizardTD/fireball.png");
        iceBallImg = loadImage("src/main/resources/WizardTD/IceBall.png");


        // Initialize and create action buttons
        actionButtons = new ArrayList<>();
        float buttonHeight = 40;
        float buttonWidth = 40;
        float buttonX = WIDTH - 110;
        float buttonY = TOPBAR + 10;

        
        // create mana instance
        mana = new Mana(currentMana, manaCap,mana_pool_spell_initial_cost,mana_pool_spell_cost_increase_per_use,mana_pool_spell_mana_gained_multiplier,mana_pool_spell_mana_gained_multiplier, initialManaGainedPerSecond );
        pause = false;

        // action buttons
        actionButtons.add(new ActionButton(buttonX, buttonY, buttonWidth, buttonHeight, "FF", 'f', "2 x speed"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 50, buttonWidth, buttonHeight, "P", 'p', "PAUSE"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 100, buttonWidth, buttonHeight, "T", 't', "Build\ntower"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 150, buttonWidth, buttonHeight, "U1", '1', "Upgrade\nrange"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 200, buttonWidth, buttonHeight, "U2", '2', "Upgrade\nspeed"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 250, buttonWidth, buttonHeight, "U3", '3', "Upgrade\ndamage"));
        actionButtons.add(new ActionButton(buttonX, buttonY + 300, buttonWidth, buttonHeight, "M", 'm',"Mana pool\ncost:" + mana.getInitialCost()));
        actionButtons.add(new ActionButton(buttonX, buttonY + 350, buttonWidth, buttonHeight, "ICE", 'i',"Ice\nTower"));

        // get starting point
        initialPositions = FindStartingPoint();

        // create wave instance
        waveList = loadWavesMonsterFromConfig(waveArray);

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {

        // display the map
        drawMap();

        // loop through towerlist to display every tower
        for (Tower tower : TowerList) {
            tower.display(this, CELLSIZE);
        }


        //pause is used when the user lose the game or win the game
        if(!pause && !actionButtons.get(1).getYellowOn()){

            // update Monster Position
            Iterator<Monster> iterator = displayMonsterList.iterator();

            // loop through monster using iterator
            while (iterator.hasNext()) {
                Monster mon = iterator.next();

                //return list of direction from Monster direction method
                List<String> path = monsterDirection(mon);
                mon.display(this);


                // loop through monster direction, change monster's moving direction
                mon.update(path, actionButtons.get(0), actionButtons.get(1));
                
                //check if the monster is freezed and change their image 
                if(mon.getFreeze()){
                    mon.freezeTime(frameRate);
                    mon.setSpeed(0);
                    //Set unfreeze to freeze image
                    //gremlin
                    if(mon.getMonsterImage() == monsterImages[0]){
                        mon.setMonsterImage(monsterImages[10]);
                    //worm
                    }else if(mon.getMonsterImage() == monsterImages[6]){
                        mon.setMonsterImage(monsterImages[9]);
                    //beetle
                    }else if(mon.getMonsterImage() == monsterImages[7]){
                        mon.setMonsterImage(monsterImages[8]);
                    }

                }else{
                    //change freeze image back to unfreeze
                    float Initialspeed = mon.getFixedSpeed();
                    mon.setSpeed(Initialspeed);
                     //gremlin
                    if(mon.getMonsterImage() == monsterImages[10]){
                        mon.setMonsterImage(monsterImages[0]);
                    //worm
                    }else if(mon.getMonsterImage() == monsterImages[9]){
                        mon.setMonsterImage(monsterImages[6]);
                    //beetle
                    }else if(mon.getMonsterImage() == monsterImages[8]){
                        mon.setMonsterImage(monsterImages[7]);
                    }
                }


                // set monster back to initial position when it reach house
                if (mon.getPositionX() >= WHouseP[0] && mon.getPositionX() <= WHouseP[0] + 48 && mon.getPositionY() >= WHouseP[1] && mon.getPositionY() <= WHouseP[1] + 48) {
                    
                    //set monster back to initial position
                    mon.restart();

                    //when mana greater than monster hp, reduce it 
                    if(mana.getCurrentMana() - mon.getCurrentHp() > 0){
                        mana.setCurrentMana(mana.getCurrentMana() - mon.getCurrentHp());//mana hp reduce when monster touch house
                    }else{
                        //when mana hp is less than monster hp set the hp to 0 to avoid it exceed the length 
                        mana.setCurrentMana(0);
                    }

                    //set pause true when mana reach 0 
                    if(mana.getCurrentMana() == 0){
                        pause = true;
                    }

                }

                //remove monster when fireball hit it 
                if(mon.getCurrentHp() <= 0){

                    //when monster is germelin
                    if(mon.getMonsterImage() == monsterImages[0]||mon.getMonsterImage() == monsterImages[1] ||mon.getMonsterImage() == monsterImages[2]||mon.getMonsterImage() == monsterImages[3]||mon.getMonsterImage() == monsterImages[4]||mon.getMonsterImage() == monsterImages[5] || mon.getMonsterImage() == monsterImages[10]){
                        
                        boolean MonsterDie = mon.deathAnimation(monsterImages);

                        mon.setSpeed(0);//stop the monster when it die

                        if(MonsterDie){
                            iterator.remove();//remove the monster
                            totalmonsters -= 1;

                            //if the current mana is less thna mana cap then add the monster gain on kil
                            if(mana.getCurrentMana() < mana.getManaCap()){
                                mana.setCurrentMana(mana.getCurrentMana() + mon.getManaGainedOnKill());
                            }
                        }

                    }else{ 
                        //other monsters except for germelin
                        iterator.remove();
                        totalmonsters -= 1;
                        if(mana.getCurrentMana() < mana.getManaCap()){
                            mana.setCurrentMana(mana.getCurrentMana() + mon.getManaGainedOnKill());
                        }
                    }
                }
            }

            //loop through tower list
            for (Tower tower : TowerList) {
                
                //if mouse on tower, shows the yellow circle
                if (tower.getPositionX() - CELLSIZE <= mouseX && tower.getPositionX() + CELLSIZE >= mouseX && tower.getPositionY() + CELLSIZE >= mouseY && tower.getPositionY() - CELLSIZE <= mouseY) {
                    tower.displayYellowRange(this);
                }

                //get fire ball list from tower
                fireballList = tower.getFireBallList();
                tower.isMonsterInRange(displayMonsterList, millis());//check if monster inside the yellow circle
                
                //loop through the fireball in fireball list
                for(Fireball fireball: fireballList){
                    fireball.display(this);//draw fireball
                    fireball.move(actionButtons.get(0), actionButtons.get(1));//make fireball move
                    if ((int)fireball.getX() == (int)fireball.getTarget().getPositionX() + 7 && (int)fireball.getY() == (int)fireball.getTarget().getPositionY() + 8){
                        tower.getFireBallList().remove(0);//remove the fireball when it hit the monster
                        fireball.getTarget().setHit(true);//set hit to true
                        fireball.decreaseHP(fireball.getTarget().getArmour());

                        //set freeze to true when it is ice ball 
                        if(fireball.getFireBallImage() == iceBallImg){
                            fireball.getTarget().setFreeze(true);
                        }

                        break;

                    }else if(fireball.getTarget() == null){
                        tower.getFireBallList().remove(0);
                        break;
                    }
                }
            }

        }else{
            for (Monster mon: displayMonsterList ){
                mon.display(this);
            }

        }

        // draw board
        // top bar
        strokeWeight(0);
        fill(196, 164, 132);
        stroke(196, 164, 132);
        rect(0, 0, WIDTH, TOPBAR);

        // side bar
        strokeWeight(0);
        fill(196, 164, 132);
        stroke(196, 164, 132);
        rect(CELLSIZE * BOARD_WIDTH, TOPBAR, SIDEBAR, CELLSIZE * BOARD_WIDTH);

        // Draw Mana Bar
        strokeWeight(0);
        mana.display(this, actionButtons.get(0), actionButtons.get(1), pause, frameRate);

        //display upgrade cost of the tower 
        for (Tower tower : TowerList) {
            if (tower.getPositionX() - CELLSIZE <= mouseX && tower.getPositionX() + CELLSIZE >= mouseX && tower.getPositionY() + CELLSIZE >= mouseY && tower.getPositionY() - CELLSIZE <= mouseY) {
                tower.DisplayCost(this,actionButtons.get(3), actionButtons.get(4), actionButtons.get(5));
            }
        }

        // display the button
        drawButton();

        // display the wave time
        drawWaveTime();

        //If the pause button is not on 
        if(!actionButtons.get(1).getYellowOn()){
            drawMonster();
        }

        // draw hover
        displayHovers();

        //draw wizard house
        image(wizardHouseImg, WHouseP[0], WHouseP[1] - 16, 48, 48);

        //set lose condition
        if(pause && !win){
            textSize(50);
            fill(0,0,255);
            textAlign(LEFT, BASELINE);
            text("YOU LOST", WIDTH/2-120, HEIGHT/2-60);
            textSize(25);
            text("Press 'r' to restart", WIDTH/2-100, HEIGHT/2-40);
            lost = true;
        }

        //set win conditon
        if (totalmonsters == 0){
            pause = true;
            textSize(50);
            fill(0,255,0);
            textAlign(LEFT, BASELINE);
            text("YOU WIN", WIDTH/2-120, HEIGHT/2+50);
            win = true;
            }
    }

    /**
     * display the grass, path and shrub on the screen by looping through conf
     */
    public void drawMap() {
        // Map
        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout[i].length(); j++) {
                char tile = mapLayout[i].charAt(j);
                float x = j * CELLSIZE;
                float y = i * CELLSIZE + TOPBAR;
                if (tile == ' ' || tile == 'W') {
                    image(grassImg, x, y, CELLSIZE, CELLSIZE);
                    if (tile == 'W') {
                        WHouseP[0] = (int) x;
                        WHouseP[1] = (int) y;
                    }
                } else if (tile == 'X') {
                    int index = checkPath(mapLayout, i, j);
                    ;
                    image(pathImages[index], x, y, CELLSIZE, CELLSIZE);
                } else if (tile == 'S') {
                    image(shrubImg, x, y, CELLSIZE, CELLSIZE);
                }
            }
        }
    }

    /**
     * check the surrounding by counting number of path around it to decide which path to use
     * @param mapLayout, path from config , add into string list
     * @param i, loop through row
     * @param j, loop through column
     * @return return number of index to pick which type of path to use
     */
    public int checkPath(String[] mapLayout, int i, int j) {
        int indexNum = 0;
        char top = ' ';
        char down = ' ';
        char left = ' ';
        char right = ' ';

        if (i - 1 >= 0) {
            top = mapLayout[i - 1].charAt(j);
        }
        if (i + 1 < mapLayout.length) {
            down = mapLayout[i + 1].charAt(j);
        }

        if (j - 1 >= 0) {
            left = mapLayout[i].charAt(j - 1);
        }

        if (j + 1 < mapLayout[i].length()) {
            right = mapLayout[i].charAt(j + 1);
        }

        if (top == 'X' && left == 'X' && right == 'X' && down == 'X') {
            indexNum = 3;
        } else if (left == 'X' && right == 'X' && down == 'X') {
            indexNum = 2;
        } else if (top == 'X' && down == 'X' && left == 'X') {
            indexNum = 5;
        } else if (top == 'X' && left == 'X' && right == 'X') {
            indexNum = 7;
        } else if (top == 'X' && left == 'X') {
            indexNum = 8;
        } else if (left == 'X' && down == 'X') {
            indexNum = 1;
        } else if (right == 'X' && down == 'X') {
            indexNum = 6;
        } else if (top == 'X' && right == 'X') {
            indexNum = 9;
        } else if (top == 'X') {
            indexNum = 4;
        } else if (down == 'X') {
            indexNum = 4;
        } 

        return indexNum;
    }

    /**
     * // add space to the first and last row as well as fist and last column
     * @return new map that include extra row or column around the initial path, so 20x20 become 22x22
     */
    public String[] UpDateMap() {
        // Create a new array to hold the updated mapLayout
        String[] updatedMapLayout = new String[mapLayout.length + 2];

        // Add an extra empty string to the beginning and end of each row
        for (int i = 0; i < mapLayout.length; i++) {
            updatedMapLayout[i + 1] = " " + mapLayout[i] + " ";
        }

        // Create an empty string for the first and last row
        int rowLength = updatedMapLayout[1].length();
        StringBuilder emptyRow = new StringBuilder();
        for (int i = 0; i < rowLength; i++) {
            emptyRow.append(" ");
        }

        // Assign the empty strings for the first and last row
        updatedMapLayout[0] = emptyRow.toString();
        updatedMapLayout[updatedMapLayout.length - 1] = emptyRow.toString();
        
        return updatedMapLayout;

    }

    /**
     * use to draw button on the screen. Called in draw()
     */
    public void drawButton() {
        // Draw button
        for (ActionButton button : actionButtons) {
            button.display(this);
        }
    }

    /**
     * involve countdown and add wavenumber
     */
    public void drawWaveTime() {
            if (DurationWavePause <= 0 && firstWave) {
                DurationWavePause = waveList.get(currentWaveNum).getPreWavePause();
                firstWave = false;
                waveFinish = true;

            } else if (DurationWavePause <= 0 && !firstWave) {
                if (displayWaveNum < waveList.size() - 1) {
                    displayWaveNum++;
                    waveFinish = true;
                    DurationWavePause = waveList.get(currentWaveNum).getDuration()+ waveList.get(displayWaveNum).getPreWavePause();
                }
            }

        // change speed of the countdown when double and pause button is involve
        if (displayWaveNum <= (waveList.size()) - 1) {// starts 0
            
            if (DurationWavePause >= 0) {
                waveList.get(currentWaveNum).display(this, displayWaveNum + 1, DurationWavePause);
                
                //pause button
                if (actionButtons.get(1).getYellowOn() == true || pause) {
                    DurationWavePause += 0;
                //double button
                } else if (actionButtons.get(0).getYellowOn() == true ) {
                    DurationWavePause -= (1.0 / frameRate * 2);
                //no button involve
                } else {
                    DurationWavePause -= 1.0 / frameRate;
                }

            }

        }

        // check when prewave start
        if ((int) DurationWavePause >= waveList.get(displayWaveNum).getPreWavePause()) {
            wavePaused = false;// duration start
        } else if (displayWaveNum == waveList.size() - 1 && (int) DurationWavePause == 0) {
            wavePaused = false;
        } else {
            wavePaused = true;// prewave start
        }

        // increase currentWaveNum when display prewave start
        if ((int) DurationWavePause == waveList.get(displayWaveNum).getPreWavePause() - 1 && currentWaveNum != displayWaveNum) {
            currentWaveNum++;
        }
    }

    /**
     * get information of wave and monster in the config
     * @param waveArray, wave array in the config
     * @return, a arrayList of wave that involves monster list
     */
    public ArrayList<Wave> loadWavesMonsterFromConfig(JSONArray waveArray) {
        ArrayList<Wave> WavesList = new ArrayList<>();

        //Loop through wave array
        for (int i = 0; i < waveArray.size(); i++) {

            JSONObject waveObj = waveArray.getJSONObject(i);

            //get monster object
            JSONArray monsterArray = waveObj.getJSONArray("monsters");

            //loop through monster object to get all the quantity and add to the totalmonsters variable
            for (int j = 0; j < monsterArray.size(); j++) {
                JSONObject monsterObj = monsterArray.getJSONObject(j);
                int quantity = monsterObj.getInt("quantity");
                totalmonsters += quantity;
            }

            WavesList.add(new Wave(waveObj, monsterImages));

        }
        return WavesList;

    }

    /**
     * find the initial position for monster
     * @return, a hashmap: integer use to pick random position and float list to store the initial position
     */
    public HashMap<Integer, float[]> FindStartingPoint() {
        int num = 0;// Store initial position in hash map
        float InitialPositionX = 0;
        float InitialPositionY = 0;

        // Create a HashMap to store the initial positions
        HashMap<Integer, float[]> initialPositions = new HashMap<>();
        char tile;

        // check the first and last row
        for (int j = 0; j < mapLayout[0].length(); j++) {
            tile = mapLayout[0].charAt(j);// first row
            if (tile == 'X') {
                InitialPositionX = j * CELLSIZE + 8;
                InitialPositionY = TOPBAR - 24; // above the maze
                float[] position = { InitialPositionX, InitialPositionY };
                initialPositions.put(num, position);
                num++;
            }

            tile = mapLayout[mapLayout.length - 1].charAt(j);// last row
            if (tile == 'X') {
                InitialPositionX = j * CELLSIZE + 8;
                InitialPositionY = mapLayout.length * CELLSIZE + 48;// below the maze
                float[] position = { InitialPositionX, InitialPositionY };
                initialPositions.put(num, position);
                num++;
            }
        }

        // Check the first and last column
        for (int k = 0; k < mapLayout.length; k++) {
            tile = mapLayout[k].charAt(1);// first column
            if (tile == 'X') {
                InitialPositionX = 0 - 24 ;
                InitialPositionY = k * CELLSIZE + TOPBAR + 8;
                float[] position = { InitialPositionX, InitialPositionY };
                initialPositions.put(num, position);
                num++;
            }

            tile = mapLayout[k].charAt(mapLayout[0].length() - 1);// last column
            if (tile == 'X') {
                InitialPositionX = (mapLayout.length) * CELLSIZE + 8;
                InitialPositionY = k * CELLSIZE + TOPBAR + 8;
                float[] position = { InitialPositionX, InitialPositionY };
                initialPositions.put(num, position);
                num++;
            }
        }
        return initialPositions;
    }

    /**
     * This method decide the spawn speed of the monster and add monster instance to the monster list
     */
    public void drawMonster() {
        //only add monster when the screen is not pause 
        if(actionButtons.get(1).getYellowOn() != true || !pause){
            int randomTypeMonster = 0;

            Wave currentWave = waveList.get(currentWaveNum);//get current wave list

            List<Monster> waveMonsters = currentWave.getMonsters();// type of monsters in current wave

            randomTypeMonster = random.nextInt(waveMonsters.size());// pick an index to decide type of monster

            // set spawnSpeed
            float SpawnSpeed = currentWave.getSpawnSpeed();
            if (actionButtons.get(1).getYellowOn() && !pause) {
                SpawnSpeed = 2;// let SpawnSpeed be greater than 1 to stop generate monster
            }

            if (actionButtons.get(0).getYellowOn() && !pause) {// double speed
                SpawnSpeed -= (1.0 / frameRate * 2);
            } else {//normal speed
                SpawnSpeed -= (1.0 / frameRate);
            }

            //set spawn speed incase the pause or double involve
            currentWave.setSpawnSpeed(SpawnSpeed);

            // set initial position
            int randomIndex = random.nextInt(initialPositions.size());//ensure the random number is within the initial point list's size
            float[] randomPosition = initialPositions.get(randomIndex);//pick random initial point
            PositionX = randomPosition[0]; // get starting positionX
            PositionY = randomPosition[1]; // get starting positionY
            
            if (currentWave.getMonsters().get(randomTypeMonster).quantityGenerate > 0 && SpawnSpeed <= 0 && !wavePaused){
                Monster monsterInstance = new Monster(waveMonsters.get(randomTypeMonster).getType(),
                        waveMonsters.get(randomTypeMonster).getHp(), 
                        waveMonsters.get(randomTypeMonster).getSpeed(),
                        waveMonsters.get(randomTypeMonster).getArmour(),
                        waveMonsters.get(randomTypeMonster).getManaGainedOnKill(),
                        waveMonsters.get(randomTypeMonster).getQuantity(),
                        waveMonsters.get(randomTypeMonster).getMonsterImage(),
                        PositionX,
                        PositionY);
                
                //Add monster instance to the displayMonsterList
                displayMonsterList.add(monsterInstance);

                //update the quantity of the monster after I generate it 
                currentWave.getMonsters().get(randomTypeMonster).updateQuantity();
                
                // set SpawnSpeed back to original fix spawn speed
                currentWave.setSpawnSpeed(currentWave.getFixedSpawnSpeed());
            }
        }
    }

    /**
     * 
     * @param monster, the current monster from the monster list
     * @return, return a list of monster direction
     */
    public List<String> monsterDirection(Monster monster) {
        List<String> path = new ArrayList<>();
        if (!wavePaused) {
            MonsterOut = true;
        }

        if (MonsterOut) {
            float initialX = monster.getInitialPositionX() / CELLSIZE;
            float initialY = monster.getInitialPositionY() / CELLSIZE;
            path = solveMaze(newMapLayout, (int)initialX, (int)initialY); // call solve maze to get a list of moving direction
        }
        return path;
    }

    /**
     * solve the maze using BFS
     * @param maze, new update maze
     * @param X, initial x point
     * @param Y, initial y point 
     * @return, a list of monster direction e.g up, down, left, right
    */
    public List<String> solveMaze(String[] maze, int X, int Y) {
        int rows = maze.length;
        int cols = maze[0].length();

        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        // Find all start positions
        List<Point> starts = new ArrayList<>();
        if (X == 0){
            starts.add(new Point(Y, X, new ArrayList<>())); // Set starting point to row 0, col 1
        }else{
            starts.add(new Point(Y, X + 1, new ArrayList<>())); // Set starting point to row 0, col 1
        }


        for (Point start : starts) {
            queue.offer(start);

            if (start.getrow() >= 0 && start.getrow() < 20 && start.getcol() >= 0 && start.getcol() < 20) {
                visited[start.getrow()][start.getcol()] = true;
            }

        }

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (maze[current.getrow()].charAt(current.getcol()) == 'W') {
                return current.getMovements();
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.getrow() + ROW_OFFSETS[i];
                int newCol = current.getcol() + COL_OFFSETS[i];

                if (isValidMove(maze, rows, cols, newRow, newCol) && !visited[newRow][newCol]) {
                    List<String> newMovements = new ArrayList<>(current.getMovements());
                    newMovements.add(String.valueOf(DIRECTIONS[i]));
                    queue.offer(new Point(newRow, newCol, newMovements));
                    visited[newRow][newCol] = true;
                }
            }
        }

        // Goal not reachable, return an empty list
        return null;
    }

    /**
     *  checks if the given row and col are within the bounds of the maze (between 0 and the maximum row and column indices) and whether the character at that position in the maze is either 'X' or 'W'. If all these conditions are met, the move is considered valid
     * @param maze the map 
     * @param rows is the number of rows in the maze.
     * @param cols is the number of columns in the maze.
     * @param row is the current row being checked in the isValidMove method.
     * @param col is the current column being checked in the isValidMove method.
     * @return boolean to tell if the movemnet is valid or not
     */
    public boolean isValidMove(String[] maze, int rows, int cols, int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols
                && (maze[row].charAt(col) == 'X' || maze[row].charAt(col) == 'W');
    }

    /**
     * display the hover of tower button, mana pool and ice tower
     */
    public void displayHovers() {
        if (actionButtons.get(2).isMouseOver(this)) {
            String text = "cost: " + towerCost;
            fill(255, 255, 255);
            stroke(0);
            strokeWeight(1);
            rect(actionButtons.get(2).getX() - 80, actionButtons.get(2).getY(), 70, 20);
            fill(0);
            textSize(12);
            text(text, actionButtons.get(2).getX() - 70, actionButtons.get(2).getY() + 15);
        }else if(actionButtons.get(6).isMouseOver(this)){
            String text = "cost: " + mana.getInitialCost();
            fill(255, 255, 255);
            stroke(0);
            strokeWeight(1);
            rect(actionButtons.get(6).getX() - 80, actionButtons.get(6).getY(), 70, 20);
            fill(0);
            textSize(12);
            text(text, actionButtons.get(6).getX() - 70, actionButtons.get(6).getY() + 15);

        }else if(actionButtons.get(7).isMouseOver(this)){
            String text = "cost: " + towerCost;
            fill(255, 255, 255);
            stroke(0);
            strokeWeight(1);
            rect(actionButtons.get(7).getX() - 80, actionButtons.get(7).getY(), 70, 20);
            fill(0);
            textSize(12);
            text(text, actionButtons.get(7).getX() - 70, actionButtons.get(7).getY() + 15);
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed() {
        if (key == 'f') {
            actionButtons.get(0).setYellowOn(!actionButtons.get(0).getYellowOn());
        }

        if (key == 'p') {
            actionButtons.get(1).setYellowOn(!actionButtons.get(1).getYellowOn());
        }

        if (key == 't') {
            if(!actionButtons.get(7).getYellowOn()){
                actionButtons.get(2).setYellowOn(!actionButtons.get(2).getYellowOn());
            }
        }

        if (key == '1') {
            actionButtons.get(3).setYellowOn(!actionButtons.get(3).getYellowOn());
        }
        if (key == '2') {
            actionButtons.get(4).setYellowOn(!actionButtons.get(4).getYellowOn());
        }
        if (key == '3') {
            actionButtons.get(5).setYellowOn(!actionButtons.get(5).getYellowOn());
        }

        if (key == 'm') {
            if(mana.getCurrentMana() > mana.getInitialCost()){
                mana.changeCost();
                for(Monster mon: displayMonsterList){
                    mon.changeManaGainOnKill(mana_pool_spell_mana_gained_multiplier);
                    mana_pool_spell_mana_gained_multiplier += 0.1;
                    mana.setinitialManaGainedPerSecond(initialManaGainedPerSecond*mana_pool_spell_mana_gained_multiplier);
                    
                }
            }
        }

        if (key == 'i'){
            if(!actionButtons.get(2).getYellowOn()){
                actionButtons.get(7).setYellowOn(!actionButtons.get(7).getYellowOn());
            }
        }

        if(key == 'r' && lost){
            setup();
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {
    }

    /**
     * set the button to yellow when the mouse click it. It been used in mouse press method
     */
    public void setButtonYellow(){
        for (ActionButton button : actionButtons) {
            if(button != actionButtons.get(6)){
                //if tower button is select when ice tower is not select
                if(button == actionButtons.get(2) && !actionButtons.get(7).getYellowOn()){
                    if (button.isMouseOver(this)) {
                        button.setMousePressed(true); // Set the button to pressed
                    }

                    if (button.isMousePressed()) {
                        button.setYellowOn(!button.getYellowOn());
                    }
                //ice tower is selected when tower is not selected
                }else if( button == actionButtons.get(7) && !actionButtons.get(2).getYellowOn()){
                    if (button.isMouseOver(this)) {
                        button.setMousePressed(true); // Set the button to pressed
                    }

                    if (button.isMousePressed()) {
                        button.setYellowOn(!button.getYellowOn());
                    }
                }else if( button != actionButtons.get(2) && button != actionButtons.get(7)){
                    if (button.isMouseOver(this)) {
                        button.setMousePressed(true); // Set the button to pressed
                    }

                    if (button.isMousePressed()) {
                        button.setYellowOn(!button.getYellowOn());
                    }
                }
                
            }else if(button == actionButtons.get(6)){
                if (button.isMouseOver(this)) {
                    if(mana.getCurrentMana() > mana.getInitialCost()){
                        mana.changeCost();
                        for(Monster mon: displayMonsterList){
                            mon.changeManaGainOnKill(mana_pool_spell_mana_gained_multiplier);
                            mana_pool_spell_mana_gained_multiplier += 0.1;
                            mana.setinitialManaGainedPerSecond(initialManaGainedPerSecond*mana_pool_spell_mana_gained_multiplier);
                            
                        }
                    }
                }
            }
        }
    }

    /**
     * add tower to the tower list and display them on the screen when mouse click on grass 
     * @param e mouse 
     */
    public void setTower(MouseEvent e){
        //draw tower
        if ((actionButtons.get(2).getYellowOn() || actionButtons.get(7).getYellowOn()) && !pause) {
            int x = e.getX() / CELLSIZE;
            int y = (e.getY() - TOPBAR) / CELLSIZE;
            if (x < 20 && x >= 0 && y < 20 && y >= 0) {
                if (mapLayout[y].charAt(x) == ' ') {
                    for(Tower tower: TowerList){
                        if(tower.getPositionX() <= mouseX && mouseX <= tower.getPositionX() + 32 && tower.getPositionY() <= mouseY && mouseY <= tower.getPositionY() +32){
                            towerExist = true;
                        }
                    }
                    if(mana.getCurrentMana() - towerCost >= 0 && !towerExist){
                        if(actionButtons.get(2).getYellowOn()){
                            TowerList.add(new Tower(towerCost, initialTowerRange, initialTowerFiringSpeed, initialTowerDamage,
                            x * CELLSIZE, y * CELLSIZE + TOPBAR, towerImages[0], fireballImg, towerImages, true, false));
                            mana.setCurrentMana((mana.getCurrentMana() - towerCost));
                        }
                        if(actionButtons.get(7).getYellowOn()){
                            TowerList.add(new Tower(towerCost, initialTowerRange, initialTowerFiringSpeed/2, initialTowerDamage/2,
                            x * CELLSIZE, y * CELLSIZE + TOPBAR, towerImages[3], iceBallImg, towerImages, false, true));
                        mana.setCurrentMana((mana.getCurrentMana() - towerCost));
                        }
                    }
                    towerExist = false;

                }
            }
        }

    }

    /**
     * upgrade the tower's speed, range and damage
     */
    public void upgradeTower(){
         // Iterate over your list of towers and check if the mouse click is over a tower
        for (Tower tower : TowerList) {
            if (mouseX >= tower.getPositionX() && mouseX <= tower.getPositionX() + CELLSIZE &&
                mouseY >= tower.getPositionY() && mouseY <= tower.getPositionY() + CELLSIZE) {
                
                    // upgrade range
                    if(mana.getCurrentMana()> tower.getRangeCost()){
                        if(actionButtons.get(3).getYellowOn()){
                            tower.upgradeRange();
                            float current_mana = mana.getCurrentMana() - tower.getRangeCost();
                            mana.setCurrentMana(current_mana);
                        }
                    }


                    //upgrade speed
                    if(mana.getCurrentMana()> tower.getSpeedCost()){
                        if(actionButtons.get(4).getYellowOn()){
                            tower.upgradeSpeed();
                            mana.setCurrentMana(mana.getCurrentMana() - tower.getSpeedCost());
                        }
                    }

                    //upgrade damage
                    if(mana.getCurrentMana()> tower.getDamageCost()){
                        if(actionButtons.get(5).getYellowOn()){
                            tower.upgradeDamage();
                            mana.setCurrentMana(mana.getCurrentMana() - tower.getDamageCost());
                        }
                    }
               
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        setButtonYellow();
        setTower(e);
        upgradeTower();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ActionButton button : actionButtons) {
            button.setMousePressed(false); // Set the button to not pressed
        }
    }

    /*
     * @Override
     * public void mouseDragged(MouseEvent e) {
     * }
     */

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source:
     * https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * 
     * @param pimg  The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        // BufferedImage rotated = new BufferedImage(newWidth, newHeight,
        // BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
