package WizardTD;

import java.util.ArrayList;
import java.util.List;


import processing.core.PApplet;
import processing.core.PImage;


public class Tower {
    private int towerCost;
    private int initialTowerRange;
    private float initialTowerFiringSpeed;
    private float initialTowerDamage;
    private float towerDamage; 
    private int positionX;
    private int positionY;
    private PImage CurrentTowerImage;
    private PImage fireballImg;
    public int RangeLevel;
    public int SpeedLevel;
    public int DamageLevel;
    private List<Fireball> fireballs;  // List to store fireballs
    private float startTime;
    private PImage[] towerImages;
    private int RangeCost;
    private int SpeedCost;
    private int DamageCost;
    private float FireBallPerSecond;// number of fireballs in one second 
    public boolean isFireBall;
    public boolean isIceBall;
    

    /**
     * constructor of towers
     * @param towerCost, cost of tower
     * @param initialTowerRange, initial range
     * @param initialTowerFiringSpeed fire ball start speed 
     * @param initialTowerDamage tower starting damage
     * @param positionX tower position X
     * @param positionY tower position Y
     * @param CurrentTowerImage current tower image show on the screen 
     * @param fireballImg either fire ball or ice ball image
     * @param towerImages all tower images 
     * @param isFireBall check if it is fireball
     * @param isIceBall check if it is ice ball
     */
    public Tower(int towerCost, int initialTowerRange, float initialTowerFiringSpeed, float initialTowerDamage,
        int positionX, int positionY, PImage CurrentTowerImage, PImage fireballImg, PImage[] towerImages, boolean isFireBall, boolean isIceBall) {
        this.towerCost = towerCost;
        this.initialTowerRange = initialTowerRange;
        this.FireBallPerSecond = initialTowerFiringSpeed;
        this.initialTowerFiringSpeed = 1/FireBallPerSecond;
        this.initialTowerDamage = initialTowerDamage;
        this.positionX = positionX;
        this.positionY = positionY;
        this.CurrentTowerImage = CurrentTowerImage;
        this.RangeLevel = 0;
        this.SpeedLevel = 0;
        this.DamageLevel = 0; 
        this.fireballs = new ArrayList<>();
        this.fireballImg = fireballImg;
        this.startTime = 0;
        this.towerImages = towerImages;
        this.RangeCost = 20;
        this.SpeedCost = 20;
        this.DamageCost = 20;
        this.towerDamage = initialTowerDamage;
        this.isIceBall = isIceBall;
        this.isFireBall = isFireBall;

    }

    public int getRangeCost(){
        return RangeCost;
    }

    public int getSpeedCost(){
        return SpeedCost;
    }

    public int getDamageCost(){
        return DamageCost;
    }


    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getTowerCost() {
        return towerCost;
    }

    public int getInitialTowerRange() {
        return initialTowerRange;
    }

    public float getInitialTowerFiringSpeed() {
        return initialTowerFiringSpeed;
    }

    public float getInitialTowerDamage() {
        return initialTowerDamage;
    }

    public PImage getTower() {
        return CurrentTowerImage;
    }

    public void setTower(PImage tower){
        this.CurrentTowerImage = tower;
    }

    /**
     * display tower on map
     * @param applet passed in to draw
     * @param CELLSIZE size of cell
     */ 
    public void display(PApplet applet, float CELLSIZE) {
        changeTowerImage();
        applet.image(CurrentTowerImage, positionX, positionY, CELLSIZE, CELLSIZE);
        if(CurrentTowerImage == towerImages[2] || CurrentTowerImage == towerImages[5]){
            //range level show
            for(int i = 0; i<RangeLevel-2;i++){
                applet.noFill();
                applet.stroke(255,0,0);
                applet.strokeWeight(1);
                applet.ellipse(positionX+i*8,positionY+2,5,5);
            }

            // display speed level
            applet.noFill();
            applet.stroke(0,0,255);
            applet.strokeWeight(SpeedLevel-3+1);
            applet.rect(positionX+5,positionY+5,20,20);

            // display damage level
            for(int j = 0; j<DamageLevel-2;j++){
                applet.fill(255,0,0);
                applet.textSize(10);
                applet.text("x",positionX+j*8,positionY+30);
            }
        }else if(CurrentTowerImage == towerImages[1] || CurrentTowerImage == towerImages[4]){

            //damage level show
            for(int j = 0; j<DamageLevel-1;j++){
                applet.fill(255,0,0);
                applet.textSize(10);
                applet.text("x",positionX+j*8,positionY+30);
            }

            //speed level show
            applet.noFill();
            applet.stroke(0,0,255);
            applet.strokeWeight(SpeedLevel-2+1);
            applet.rect(positionX+5,positionY+5,20,20);

            //range level show
            for(int i = 0; i<RangeLevel-1;i++){
                applet.noFill();
                applet.stroke(255,0,0);
                applet.strokeWeight(1);
                applet.ellipse(positionX+i*8,positionY+2,5,5);
            }
        }else{
            //range level
            for(int i = 0; i<RangeLevel;i++){
                applet.noFill();
                applet.stroke(255,0,0);
                applet.strokeWeight(1);
                applet.ellipse(positionX+i*8,positionY+2,5,5);
            }

            //speed level
            applet.noFill();
            applet.stroke(0,0,255);
            applet.strokeWeight(SpeedLevel-1+1);
            applet.rect(positionX+5,positionY+5,20,20);

            //damage level
            for(int j = 0; j<DamageLevel;j++){
                applet.fill(255,0,0);
                applet.textSize(10);
                applet.text("x",positionX+j*8,positionY+30);
            }
        }
    }

    /**
     * 
     * @param applet pass in to draw the ellipse
     */
    public void displayYellowRange(PApplet applet) {
        applet.strokeWeight(1);
        applet.stroke(255, 255, 0); // Set outline color to yellow (R, G, B values)
        applet.noFill(); // Disable fill
        applet.ellipse(positionX + 16, positionY + 16 , initialTowerRange * 2, initialTowerRange * 2);
    }

    //Display the cost
    /**
     * used to display the cost when the mouse touch the tower
     * @param applet use to draw the table and display the text
     * @param U1 upgrade range button
     * @param U2 upgrade speed button
     * @param U3 upgrade damage button
     */
    public void DisplayCost(PApplet applet, ActionButton U1, ActionButton U2, ActionButton U3){
        int barPosition = 450;
        int BarWidth = 100;

        //upgrade cost
        applet.stroke(0);
        applet.strokeWeight(1);
        applet.fill(255);
        applet.rect(650, barPosition, BarWidth, 20);

        applet.textSize(12);
        applet.fill(0);
        applet.textAlign(applet.LEFT, applet.TOP);
        applet.text("Upgrade cost", 660, barPosition+2);
        int total = 0;

        //each cost
        String Displaymessage = "";
        int BarHeight = 10;

        if (U1.getYellowOn()){
            RangeCost = 20 + RangeLevel*10;
            total += RangeCost;
            Displaymessage =  Displaymessage + "range:  " + Integer.toString(RangeCost) + "\n";
            BarHeight += 20;

        }

        if (U2.getYellowOn()){
            SpeedCost = 20 + SpeedLevel*10;
            total += SpeedCost;
            Displaymessage =  Displaymessage + "speed:  " + Integer.toString(SpeedCost) + "\n";
            BarHeight += 20;
        }

        if (U3.getYellowOn()){
            DamageCost = 20 + DamageLevel*10;
            total += DamageCost;
            Displaymessage =  Displaymessage + "damage:  " + Integer.toString(DamageCost) + "\n";
            BarHeight += 20;
            
        }

        applet.stroke(0);
        applet.strokeWeight(1);
        applet.fill(255);
        applet.rect(650, barPosition+20, BarWidth, BarHeight);

        applet.textSize(12);
        applet.fill(0);
        applet.textAlign(applet.LEFT, applet.TOP);
        applet.text(Displaymessage, 660, barPosition+20+2);
        

        //total cost
        // int total = RangeCost + SpeedCost + DamageCost;
        Displaymessage = "Total:    " + Integer.toString(total);
        applet.stroke(0);
        applet.strokeWeight(1);
        applet.fill(255);
        applet.rect(650, barPosition+20+BarHeight, BarWidth, 20);

        applet.textSize(12);
        applet.fill(0);
        applet.textAlign(applet.LEFT, applet.TOP);
        applet.text(Displaymessage, 660, barPosition+20+BarHeight+2);


    }

    /**
     * use to upgrade range
     */
    public void upgradeRange() {
        initialTowerRange += 32;
        RangeLevel += 1;
    }

    /**
     * use to upgrade speed
     */
    public void upgradeSpeed(){
        FireBallPerSecond += 0.5;
        SpeedLevel += 1;
        initialTowerFiringSpeed = 1/FireBallPerSecond;
    }

    /**
     * use to upgrade damage 
     */
    public void upgradeDamage(){
        towerDamage += initialTowerDamage/2;
        DamageLevel += 1;
    }

    public float getTowerDamage(){
        return towerDamage;
    }

    /**
     * 
     * @return a list of fireball 
     */
    public List<Fireball> getFireBallList(){
        return fireballs;
    }

    /**
     * This method is used to find the closest monster through the use of distance formula and unit vector. 
     * The start time variable appears to represent the time when the tower last fired at a monster. It's used to calculate the time elapsed since the last tower attack. When the elapsed time exceeds a certain threshold (initialTowerFiringSpeed), the tower is allowed to attack again. In essence, startTime is used to control the rate at which the tower can fire.
     * @param monsters pass in monster list to find the target monster 
     * @param currentTime is used to determine if enough time has elapsed since the last tower attack
     */
    public void isMonsterInRange(List<Monster> monsters, float currentTime) {
        float closestDistance = Float.MAX_VALUE;  // Initialize with a very large value
        Monster closestMonster = null;//set closest monster to null 
        currentTime = currentTime /1000; //change millisecond to second 

        if((currentTime - startTime) >= initialTowerFiringSpeed){

            //loop through monster list to calculate each distance
            for (Monster monster : monsters) {
                float distance = PApplet.dist(positionX + 16, positionY + 16, monster.getPositionX(), monster.getPositionY());//pick center of the monster

                if (distance <= initialTowerRange) {

                    // Check if this monster is the closest so far
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestMonster = monster;
                    }
                }
            }

            if (closestMonster != null) {
                // Create a fireball targeting the closest monster
                if(isFireBall){
                    fireballs.add(new Fireball(positionX + 16, positionY + 16, closestMonster, fireballImg, towerDamage));
                }else{
                    fireballs.add(new Fireball(positionX + 16, positionY + 16, closestMonster, fireballImg, towerDamage));
                }
                
                startTime = currentTime;//set startTime to current when the fireball shoots
               
            }
        }
    }

    /**
     * change towerImage when upgrade speed, range and damage levels. The boolean isFireBall and isIceBall is used to determine which image to change. These two vairbales determine the type of tower 
     */
    public void changeTowerImage(){
        //fireball tower 
        if(isFireBall){
            //level 2
            if(RangeLevel >= 2 && SpeedLevel >= 2 && DamageLevel >= 2){
                setTower(towerImages[2]);
            //level 1
            }else if (RangeLevel >= 1 && DamageLevel >= 1 && SpeedLevel >= 1){
                setTower(towerImages[1]);
            }

        }
        
        //ice ball tower 
        if(isIceBall){

            
            //level 2
            if(RangeLevel >= 2 && SpeedLevel >= 2 && DamageLevel >= 2){
                setTower(towerImages[5]);
            }
            //level 1 
            else if (RangeLevel >= 1 && DamageLevel >= 1 && SpeedLevel >= 1){
                setTower(towerImages[4]);

            }
            
            
        }
        
    }

}
