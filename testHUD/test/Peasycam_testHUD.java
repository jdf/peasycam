package test;

import peasy.*;
import processing.core.PApplet;


public class Peasycam_testHUD extends PApplet {
  

  PeasyCam peasycam;
  
  public void settings() {
    size(800, 600, P3D);
    smooth(8);
  }
  
  public void setup() {
    // surface.setResizable(true);
    
    // default FoV is 60
    perspective(90 * DEG_TO_RAD, width/(float)height, 1, 5000);

    // camera
    peasycam = new PeasyCam(this, 300);
  }
  
  public void draw(){
    
    // in case of surface resizing (happens asynchronous) this has no effect in setup
	// perspective(90 * DEG_TO_RAD, width/(float)height, 1, 5000);
    // peasycam.feed();
    
    // 3D scene
	ambientLight(128, 128, 128);
    pointLight(255, 128, 64, -200, -200, 10);
    pointLight(64, 128, 255, +200, +200, 10);
    
    background(32);
    
    rectMode(CENTER);
    noStroke();
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

    int wh = 100;
    rectMode(CORNER);
    noStroke();
    fill(0xFFFF0000); rect(       0,        0, wh, wh);
    fill(0xFF00FF00); rect(width-wh,        0, wh, wh, 30);
    fill(0xFF0000FF); rect(width-wh,height-wh, wh, wh);
    fill(0xFFFFFFFF); rect(       0,height-wh, wh, wh, 30);
    
    peasycam.endHUD();
  
  }
  

  
  public static void main(String args[]) {
    PApplet.main(new String[] { Peasycam_testHUD.class.getName() });
  }
  
}

