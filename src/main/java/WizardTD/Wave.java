package WizardTD;

import java.util.ArrayList;
import java.util.List;

import jogamp.graph.font.typecast.ot.Fixed;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Wave {
    private float preWavePause;
    private float duration; 
    private List<Monster> MonsterInWave;  // List of monsters for this wave
    private float fixedDuration;
    private float SpawnSpeed; 
    private float FixedSpawnSpeed;
    private int fixedTotalMonster;

    /**
     * get wave and monster information
     * @param waveObj wave object in the config 
     * @param monsterImages all monster images
     */
    public Wave(JSONObject waveObj, PImage[] monsterImages) {
        this.duration = waveObj.getFloat("duration");
        this.preWavePause = waveObj.getFloat("pre_wave_pause");
        List<Monster> MonsterInWave=new ArrayList<>();
        JSONArray monsterArray = waveObj.getJSONArray("monsters");

        //loop trhough monster array 
        for (int i = 0; i < monsterArray.size(); i++) {
            JSONObject monsterObj = monsterArray.getJSONObject(i);
            String type = monsterObj.getString("type");
            int hp = monsterObj.getInt("hp");
            float speed = monsterObj.getFloat("speed");
            float armour = monsterObj.getFloat("armour");
            int manaGainedOnKill = monsterObj.getInt("mana_gained_on_kill");
            int quantity = monsterObj.getInt("quantity");

            PImage monsterImage = null;
            // Set monster image based on type (assuming monsterImages is an array)
            if (type.equals("gremlin")) {
                monsterImage = monsterImages[0];
            } else if (type.equals("worm")) {
                monsterImage = monsterImages[6];
            } else if (type.equals("beetle")) {
                monsterImage = monsterImages[7];
            }

            // Create Monster object and add to the monsters list
            Monster monster = new Monster(type, hp, speed, armour, manaGainedOnKill, quantity, monsterImage, 0, 40);
            MonsterInWave.add(monster);
        }
        this.MonsterInWave = MonsterInWave;
        this.fixedDuration = duration;
        this.fixedTotalMonster = fixedTotalMonster();
        this.SpawnSpeed = this.fixedDuration/this.fixedTotalMonster;
        this.FixedSpawnSpeed = this.fixedDuration/this.fixedTotalMonster;
    }

    public float getPreWavePause() {
        return preWavePause;
    }

    public float getDuration() {
        return duration;
    }

    /**
     * display wave number and count down on screen
     * @param applet, pass in to draw 
     * @param waveNum, wave number show on the screen
     * @param preWavePause, wave count down
     */
    public void display(PApplet applet,  int waveNum ,float preWavePause){
        applet.noStroke();  // Remove outline
        applet.fill(196,164,132);// Fill with the background color to "erase" previous text
        applet.rect(10, 5, 200, 30);// Draw a rectangle to cover the previous text area
        String show = "Wave " + waveNum + " starts: " + (int)preWavePause;
        applet.textSize(18);
        applet.fill(0); 
        applet.text(show, 20, 25);
    }

    // This method should return the monsters for this wave
    public List<Monster> getMonsters() {
        return MonsterInWave;
    } 

    // This method should set the monsters for this wave
    public void setMonsters(List<Monster> MonsterInWave) {
        this.MonsterInWave = MonsterInWave;
    }

    /**
     *get total amount of monster in a wave. Add different type of monster quantity togetehr
     * @return total amount of monsters in the current wave (one wave)
     */
    public int fixedTotalMonster(){
        int fixedTotalMonster = 0;
        for(Monster monster: MonsterInWave){
            fixedTotalMonster += monster.getQuantity();
        }
        return fixedTotalMonster;
    }

    public void setSpawnSpeed(float SpawnSpeed){
        this.SpawnSpeed = SpawnSpeed;
    }

    public float getSpawnSpeed(){
        return SpawnSpeed;
    }

    public float getFixedSpawnSpeed(){
        return FixedSpawnSpeed;
    }
    
}
