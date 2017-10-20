package test;

import peasy.*;
import processing.core.PApplet;
import processing.opengl.PGraphics2D;


public class Peasycam_testHUD extends PApplet {
  


  // camera control
  PeasyCam peasycam;
  
  PGraphics2D pg_screen;
 
  public void settings() {
    size(800, 600, P3D);
    smooth(8);
  }
  
  public void setup() {
//    surface.setResizable(true);
    
    // default FoV is 60
    perspective(80 * DEG_TO_RAD, width/(float)height, 1, 5000);

    // camera
    peasycam = new PeasyCam(this, 300);
    
    
    // just some mask for HUD display test
    pg_screen = (PGraphics2D)createGraphics(width, height, P2D);
    pg_screen.smooth(0);
    pg_screen.beginDraw();
    {
        pg_screen.clear();
        pg_screen.beginShape();
        pg_screen.fill(0xFF000000); pg_screen.vertex(0, 0);
        pg_screen.fill(0xFFFF0000); pg_screen.vertex(width, 0);
        pg_screen.fill(0xFFFFFFFF); pg_screen.vertex(width, height);
        pg_screen.fill(0xFF00FF00); pg_screen.vertex(0, height);
        pg_screen.endShape();
        pg_screen.blendMode(REPLACE);
        pg_screen.noStroke();
        pg_screen.fill(0,0);
        pg_screen.rect(20, 20, width-40, height-40);
    }
    pg_screen.endDraw();
  }
  
  public void draw(){
    
    // in case of surface resizing (happens asynchronous) this has no effect in setup
    // perspective(120 * DEG_TO_RAD, width/(float)height, 1, 5000);
    // peasycam.feed();
    
    
    // 3D scene
	ambientLight(128, 128, 128);
    pointLight(255, 128, 64, -200, -200, 10);
    pointLight(64, 128, 255, +200, +200, 10);
    
    background(16);
    
    rectMode(CENTER);
    strokeWeight(1);
    stroke(0);
    fill(128);
    rect(0, 0, 400, 400);
    
    strokeWeight(2);
    stroke(255, 64,  0); line(0,0,0,100,0,0);
    stroke( 32,255, 32); line(0,0,0,0,100,0);
    stroke(  0, 64,255); line(0,0,0,0,0,100);
    
    translate(80,80,80);
    noStroke();
    fill(128);
    box(50);
    
    
    // screen-aligned 2D HUD
    peasycam.beginHUD();
    image(pg_screen, 0, 0);
    peasycam.endHUD();
  
  }
  

  
  public static void main(String args[]) {
    PApplet.main(new String[] { Peasycam_testHUD.class.getName() });
  }
  
}

