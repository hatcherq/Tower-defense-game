package WizardTD;

import javax.swing.Action;

import processing.core.PApplet;
import processing.core.PImage;

public class Fireball {
    private float x;
    private float y;
    private Monster target;
    private PImage fireballImg;
    private float initial_tower_damage;
    private boolean freeze;

    /**
     * Contain feature of fireball
     * @param x start position of fireball
     * @param y start position of fireball
     * @param target its target monster 
     * @param fireballImg pass in fireball image 
     * @param initial_tower_damage initial damage of fireball towards monster 
     */
    public Fireball(float x, float y ,Monster target, PImage fireballImg, float initial_tower_damage){
        this.x = x;
        this.y = y;
        this.target = target;
        this.fireballImg = fireballImg;
        this.initial_tower_damage = initial_tower_damage;
        this.freeze = false;
    
    }

    public boolean getFreeze(){
        return freeze;
    }

    /**
     * Calculate the movement of fireball by using distance formular and unit vector. 
     * The unit vectors (normalized direction vectors) are used to control the movement of a fireball. 
     * The purpose of using unit vectors in this context is to ensure that the fireball moves at a consistent speed in the direction of its target while preserving the direction of movement.
     * the target position been add 7 and 8 to ensure the fireball hits the middle of the monsters 
     * @param doubleSpeed increase fireball speed when double speed button is on
     * @param pauseButton stop the fireball when pause button is on
     */
    public void move(ActionButton doubleSpeed, ActionButton pauseButton) {
        if (target != null && !pauseButton.getYellowOn()) {
            float dx = target.getPositionX() + 7 - x;  // Calculate the change in x
            float dy = target.getPositionY() + 8 - y;  // Calculate the change in y
            double distance = Math.sqrt(dx * dx + dy * dy);//calculate the distance between fireball's initial position and monster's initial position
            float speed;//the speed of the fireball 
            
            //ensure the distance is positive 
            if (distance > 0) {

                // Calculate the normalized direction
                double normalizedDX = dx / distance;
                double normalizedDY = dy / distance;
                
                // float speed = 0;
                
                //if double speed is set, the speed will be 10 picels per frame
                if(doubleSpeed.getYellowOn()){
                    speed = 10.0f;
                }else{
                    speed = 5.0f;  // Speed set to 5 pixels per frame
                }
        
                // Calculate the remaining distance to the target
                double remainingDistance = distance - speed;
        
                // Update the fireball's position based on the normalized direction and speed
                if (remainingDistance <= 0) {
                    // If the fireball is very close or past the target, move it exactly to the target's position
                    x = target.getPositionX() + 7;
                    y = target.getPositionY() + 8;
                } else {
                    x += (float) (normalizedDX * speed);
                    y += (float) (normalizedDY * speed);
                }
            }
        }
        
    }

    public PImage getFireBallImage(){
        return fireballImg;
    }

    public void setFireBallImage(PImage fireballImg){
        this.fireballImg = fireballImg;
    }

    public float getX(){
        return x;
    }

    public void display(PApplet applet){
        applet.image(fireballImg, x, y);
    }

    public float getY(){
        return y;
    }

    public void setTarget(Monster monster){
        this.target = monster;
    }

    public Monster getTarget(){
        return target;
    }

    /**
     * decrease monster hp when the fireball hit the monster 
     * @param armour percentage multiplier to damage received by this monster, higher means high damage, lower means low damage
     */
    public void decreaseHP(float armour){
        if(target.getHit() == true){
            target.setHit(false);//set back to false after decrease its hp

            if(target.getCurrentHp() <= (initial_tower_damage * armour) ){
                target.setCurrentHp(0); //set the hp to 0 if the current hp is lower then the damage to avoid the hp exceed the range 
            }else{
                target.setCurrentHp(target.getCurrentHp() - (initial_tower_damage * armour));
            }
        }
        
    }
}
