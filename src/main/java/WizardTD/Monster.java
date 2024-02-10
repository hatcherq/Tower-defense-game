

// Source code is decompiled from a .class file using FernFlower decompiler.
package WizardTD;

import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Monster {
   private String type;
   private float hp;
   private float currentHp;
   private float speed;
   private float armour;
   private float manaGainedOnKill;
   private int quantity;
   private float PositionX;
   private float PositionY;
   private PImage monsterImage;
   private float initialPositionX;
   private float initialPositionY;
   public int quantityGenerate;
   public int index;
   public float AmountOfSpeedIncrease;
   private boolean hit;
   private int currentDeathFrame;
   private int deathFrame;
   private float fixedSpeed;
   private float iceCount;
   private float iceDuration;
   private boolean freeze;
   private float countTo32;


    /**
     * Contain informations of monster
     * @param type the type of monster it pass in, germelin, worm or beetle 
     * @param hp total amount of hp 
     * @param speed speed of the monster 
     * @param armour monster's armour
     * @param manaGainedOnKill amount of mana will gain when the monster is been killed 
     * @param quantity amount of that type monster in the current wave 
     * @param monsterImage monster image that will be display 
     * @param PositionX monster's position x 
     * @param PositionY monster's position y 
     */
   public Monster(String type, float hp, float speed, float armour, float manaGainedOnKill, int quantity, PImage monsterImage, float PositionX, float PositionY) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.currentHp = hp;
        this.monsterImage = monsterImage;
        this.quantity = quantity;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
        this.initialPositionX = PositionX;
        this.initialPositionY = PositionY;
        this.index = 0;
        this.quantityGenerate = quantity;
        this.AmountOfSpeedIncrease = 0.0F;
        this.hit = false;
        this.currentDeathFrame = 0;// index to show the current death frame 
        this.deathFrame = 0; // count total amount 
        this.fixedSpeed = speed;
        this.iceCount = 0;
        this.iceDuration = 1;
        this.freeze = false;
        this.countTo32 = 0;

   }

    public float getFixedSpeed(){
        return fixedSpeed;
    }
    
    public String getType() {
        return this.type;
    }

    public float getHp() {
        return this.hp;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getArmour() {
        return this.armour;
    }

    public float getManaGainedOnKill() {
        return this.manaGainedOnKill;
    }

    public float getCurrentHp() {
        return this.currentHp;
    }

    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    public float getPositionX() {
        return this.PositionX;
    }

    public float getPositionY() {
        return this.PositionY;
    }

    public void setMonsterImage(PImage monsterImage) {
        this.monsterImage = monsterImage;
    }

    public PImage getMonsterImage() {
        return this.monsterImage;
    }


    public int getQuantity() {
        return this.quantity;
    }

    public boolean getFreeze(){
            return freeze;
    }

    public void setFreeze(boolean freeze){
            this.freeze = freeze;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index; 
    }

    public float getCountTo32(){
        return countTo32;
    }
    public void setCountTo32(float countTo32){
        this.countTo32 = countTo32;
    }

    public void setPositionX(float PositionX){
        this.PositionX = PositionX;

    }

    public void setPositionY(float PositionY){
        this.PositionY = PositionY;

    }

    public void setIceDuration(float iceDuration){
        this.iceDuration = iceDuration;
    }

    public float getIceCount(){
        return iceCount;
    }

    /**
     * display monster on the screen
     * @param pApplet used to draw the monster image and its hp bar 
     */

    public void display(PApplet pApplet) {
        if (this.getMonsterImage() != null) {
            pApplet.image(this.monsterImage, this.PositionX, this.PositionY, 18.0F, 18.0F);//draw image 
            pApplet.noStroke();//no stroke
            float redBarWidth = 30.0F;
            float proportion = this.currentHp / this.hp;
            float greenBarWidth= redBarWidth * proportion;
            
            //draw hp bar 
            pApplet.fill(255.0F, 0.0F, 0.0F);
            pApplet.rect(this.PositionX - 5.0F, this.PositionY - 10.0F, redBarWidth, 3.0F);
            pApplet.fill(0.0F, 255.0F, 0.0F);
            pApplet.rect(this.PositionX - 5.0F, this.PositionY - 10.0F, greenBarWidth, 3.0F);
        }

    }

    public int getCurrentDeathFrame() {
        return this.currentDeathFrame;
    }

    /**
     * display death animation of germelin when it dead
     * @param monsterImages all monster images involves beetle, worm gremelin and their freeze images
     * @return the boolean expression to know when monster die. The monster been removed when all death animation been displayed 
     */
    public boolean deathAnimation(PImage[] monsterImages) {
        deathFrame++; //count number of frame 
        if (1 <= deathFrame && deathFrame <= 4) {
            monsterImage = monsterImages[1];
        } else if (5 <= this.deathFrame && this.deathFrame <= 8) {
            monsterImage = monsterImages[2];
        } else if (9 <= this.deathFrame && this.deathFrame <= 12) {
            monsterImage = monsterImages[3];
        } else if (13 <= this.deathFrame && this.deathFrame <= 16) {
            monsterImage = monsterImages[4];
        }

        if (deathFrame == 16) {
            deathFrame = 0;//set back to 0 when it reach max 
            return true;
        } else {
            return false;//set false so the monster won't be remove 
        }
    }

    public float getInitialPositionX() {
        return this.initialPositionX;
    }

    public float getInitialPositionY() {
        return this.initialPositionY;
    }

    /**
     * reduce one quantity when monster been generate 
     */
    public void updateQuantity() {
        quantityGenerate -= 1 ;
    }

    public int getquantityGenerate(){
        return quantityGenerate;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean getHit() {
        return this.hit;
    }

    /**
     * loop through monster direction list that's generate by using bfs. The index is used to loop through the list, it will increase when countTo32 greate or equal to 32 pixel 
     * @param path list of directions, up , down, left and right 
     * @param DoubleTime to check if the double time button is set to yellow 
     * @param pause to check if pause button is set to yellow 
     */
    public void update(List<String> path, ActionButton DoubleTime, ActionButton pause){
        String direction = " ";//direction of the monster 

        //ensure the index won't get out of range 
        if (index < path.size()) {
            direction = path.get(index);
        }

        //set speed
        float MoveSpeed = speed;
        //double time means double the speed 
        if (DoubleTime.getYellowOn()){
            MoveSpeed = speed * 2;
        }
        //when 
        if(pause.getYellowOn()){
            MoveSpeed = 0;
        }
        
        if ((countTo32 + MoveSpeed) > 32){
            MoveSpeed = 32 - countTo32;
            countTo32 += MoveSpeed;
        }else{
            countTo32 += MoveSpeed;
        }

        if (direction.equals("U")) {
            this.PositionY -= MoveSpeed;
        } else if (direction.equals("D")) {
            this.PositionY += MoveSpeed;
        } else if (direction.equals("L")) {
            this.PositionX -= MoveSpeed;
        } else if (direction.equals("R")) {
            this.PositionX += MoveSpeed;
        }

        if (countTo32 >= 32){
            index += 1;
            countTo32 = 0;
        }
    }


    public void changeManaGainOnKill(float GainedOnKill) {
        this.manaGainedOnKill *= GainedOnKill;
    }

    /**
     * restart the monster position when it reach the house
     */
    public void restart() {
        PositionX = this.initialPositionX;
        PositionY = this.initialPositionY;
        index = 0;
        countTo32 = 0;
    }


    /**
     * when the monster is freeze, start the freeze count time
     * @param frameRate the framerate is use to add iceCount very frame
     */
    public void freezeTime(float frameRate){
       iceCount += 1/frameRate; 
       if(iceCount > iceDuration){
            freeze = false;
            iceCount = 0;
       }else{
        freeze =true;
       }
    }

    public void setCurrentDeathFrame(int currentDeathFrame){
        this.currentDeathFrame = currentDeathFrame;
    }

    
}


