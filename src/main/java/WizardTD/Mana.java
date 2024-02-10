package WizardTD;

import javax.swing.Action;

import processing.core.PApplet;

public class Mana {
    private float currentMana;  // Current mana
    private float manaCap;  // Mana cap
    private int mana_pool_spell_initial_cost;
    private int mana_pool_spell_cost_increase_per_use;
    private float mana_pool_spell_cap_multiplier;
    private float mana_pool_spell_mana_gained_multiplier;
    private float initialManaGainedPerSecond;

    /**
     * contain mana information
     * @param currentMana current hp the mana have 
     * @param manaCap max amount of hp the mana can have 
     * @param mana_pool_spell_initial_cost initial cost of the spell, in mana
     * @param mana_pool_spell_cost_increase_per_use How much the spell’s cost should increase after each use.
     * @param mana_pool_spell_cap_multiplier The multiplier to use for increasing the mana cap when the spell is cast
     * @param mana_pool_spell_mana_gained_multiplier The use for increasing mana gained from killing monsters and the mana trickle when the spell is cast. 
     * @param initialManaGainedPerSecond amount of mana you can gain per second
     */
    public Mana(float currentMana, float manaCap, int mana_pool_spell_initial_cost, int mana_pool_spell_cost_increase_per_use, float mana_pool_spell_cap_multiplier,  float mana_pool_spell_mana_gained_multiplier, float initialManaGainedPerSecond){
        this.currentMana = currentMana;
        this.manaCap = manaCap;
        this.mana_pool_spell_initial_cost = mana_pool_spell_initial_cost;
        this.mana_pool_spell_cost_increase_per_use = mana_pool_spell_cost_increase_per_use;
        this.mana_pool_spell_cap_multiplier = mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_mana_gained_multiplier = mana_pool_spell_mana_gained_multiplier;
        this.initialManaGainedPerSecond = initialManaGainedPerSecond;
    }

    /**
     * use to display the mana and set the amount of mana you can gain per second when actionbuttons are involved
     * @param applet use to draw the mana bar 
     * @param doubleSpeed when double speed is set on, the amount of mana increase is double as well
     * @param stop when pause button is set, 0 amount of mana is increased
     * @param pause set true when player lose the game. The mana gain per second will become 0 as well. 
     */
    public void display(PApplet applet,ActionButton doubleSpeed, ActionButton stop, boolean pause, float frameRate){
       changeMana(doubleSpeed, stop, pause, frameRate);
        applet.textAlign(applet.UP, applet.UP);
        applet.stroke(0);  // Set the outline color to black
        applet.strokeWeight(2);  // Set the outline weight
        applet.fill(255,255,255);//white
        applet.rect(375, 10, 350, 20);  // Draw the mana bar 
        applet.fill(0, 255, 255);  // Aqua color for the mana bar
        applet.rect(375, 10, (currentMana/manaCap)*350, 20); 
        applet.fill(0,0,0);//black
        applet.textSize(16);
        applet.text("MANA: ", 320, 25);
        String show = (int)currentMana + "/" + (int)manaCap;;
        applet.text(show,475, 27);
    }

    public void changeMana(ActionButton doubleSpeed, ActionButton stop, boolean pause, float frameRate){
         if(stop.getYellowOn() == true || pause){
            currentMana += 0;
        }else if (doubleSpeed.getYellowOn() == true){
            if(currentMana < manaCap){
                currentMana = currentMana+( 2 * initialManaGainedPerSecond)/frameRate;
            }
        }else{
            if(currentMana < manaCap){
                currentMana = currentMana+initialManaGainedPerSecond/frameRate;
            }
        }

        //when current mana is greater than manaCap, set the current mana as mana cap to avoid exceed length
        if(currentMana >= manaCap){
            currentMana = manaCap;
        }
    }

    public void setinitialManaGainedPerSecond(float initialManaGainedPerSecond){
        this.initialManaGainedPerSecond = initialManaGainedPerSecond;
    }

    public float getinitialManaGainedPerSecond(){
        return initialManaGainedPerSecond;
    }


    public void setCurrentMana(float currentMana){
        this.currentMana = currentMana;
    }

    public float getCurrentMana(){
        return currentMana;
    }

    public float getManaCap(){
        return manaCap;
    }

    public int getInitialCost(){
        return mana_pool_spell_initial_cost;
    }

    /**
     * change features of mana when upgrade it 
     */
    public void changeCost(){
        currentMana -= mana_pool_spell_initial_cost; //decrease the current mana to upgrade mana 
        mana_pool_spell_initial_cost += mana_pool_spell_cost_increase_per_use; // initial cost increase
        manaCap = manaCap * mana_pool_spell_cap_multiplier; //increase mana cap
    }
}

