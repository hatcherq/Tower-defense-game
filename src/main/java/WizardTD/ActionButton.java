package WizardTD;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class ActionButton {
    public float x, y, width, height;
    public String label;
    private boolean mousePressed;
    private char hotkey;
    public String name;
    private boolean yellowOn;

    /**
     * Contain information about action buttons 
     * @param x the position x to draw
     * @param y the position y to draw
     * @param width the width of the shape 
     * @param height the heiht of the shape
     * @param label string name shows on the shape 
     * @param hotkey key to press to set that button to yellow 
     * @param name name of the button
     */
    public ActionButton(float x, float y, float width, float height, String label, char hotkey, String name){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.hotkey = hotkey;
        this.mousePressed = false;
        // this.touch = false;
        this.name = name;
        this.yellowOn = false;
    }

    /**
     * use to diplay button on the screen 
     * @param applet used to draw the shape and fill it to grey or yellow 
     */
    public void display(PApplet applet){
        applet.stroke(0);//set stroke colour to black
        applet.strokeWeight(2);//stroke weight of 2 pixels.

        if (yellowOn) {
            applet.fill(255, 255, 0); // yellow
        }else if (isMouseOver(applet)) {
            applet.fill(200); // Gray when mouse over or touch
        } else {
            applet.noFill(); // no color
        }

        applet.rect(x,y,width,height);
        applet.fill(0);//filled with black color
        applet.textSize(20);
        applet.textAlign(PApplet.CENTER, PApplet.CENTER);// set text at the center of the canvas 
        applet.text(label, x + width / 2, y + height / 2);
        applet.textAlign(PApplet.LEFT);
        applet.textSize(12);
        applet.text(name, x + 45, y + 15);
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isMouseOver(PApplet applet){
        boolean in = applet.mouseX > x && applet.mouseX < x + width && applet.mouseY > y && applet.mouseY < y + height;
        return in;
    }

    public char getHotKey(){
        return hotkey; 
    }

    public void setYellowOn(boolean yellowOn){
        this.yellowOn = yellowOn;
    }

    public boolean getYellowOn(){
        return yellowOn;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}
