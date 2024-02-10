package WizardTD;


import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class ActionButtonTest {
    ActionButton button;
    PApplet applet;

    @BeforeEach
    public void setUp(){
        button = new ActionButton(10, 20, 100, 30, "TestButton", 'T', "Button1");
        applet = new PApplet();
    }
    @Test
    public void TestDisplay(){ 
        assertEquals(10, button.getX());
        assertEquals(20, button.getY());
        assertEquals(100, button.width);
        assertEquals(30, button.height);
        assertEquals("TestButton", button.label);
        assertEquals('T', button.getHotKey());
        assertEquals("Button1", button.name);
    }

    @Test
    public void TestIsMouseOver(){
        applet.mouseX = 15;
        applet.mouseY = 25;
        assertTrue(button.isMouseOver(applet));
    }

    @Test
    public void TestIsMouseOver2(){
        applet.mouseX = 5;
        applet.mouseY = 15;
        assertFalse(button.isMouseOver(applet));
    }

    @Test
    public void TestSetYellowOn(){
        button.setYellowOn(true);
        assertTrue(button.getYellowOn());
        button.setYellowOn(false);
        assertFalse(button.getYellowOn());
    }

    @Test
    public void TestMousePress(){
        button.setMousePressed(true);
        assertTrue(button.isMousePressed());
        button.setMousePressed(false);
        assertFalse(button.isMousePressed());

    }

    @Test
    public void TestHotKey(){
        assertEquals('T', button.getHotKey());
    }

     @Test
    public void TestXY(){
        assertEquals(10, button.getX());
        assertEquals(20, button.getY());

    }

    
}
