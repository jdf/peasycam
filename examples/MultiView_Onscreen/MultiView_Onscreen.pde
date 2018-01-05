

import peasy.PeasyCam;

import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;


//
//
// MultiView (advanced version)
//
// N x N Camera Views of the same scene, on only one PGraphics.
//
// In this demo only one render-target is used -> the primary PGraphics3D ... this.g
// Each Camera still has its own mouse-handler. 
// Only the viewport-position/dimension is used to build the camera state.
//
// For rendering, some OpenGL instructions (viewport, scissors) are used to
// render the scene to its actual camera viewport position/size.
//
//

final int NX = 3;
final int NY = 2;
PeasyCam[] cameras = new PeasyCam[NX * NY];

public void settings() {
  size(1280, 720, P3D); // 3D
  smooth(8);
}

public void setup() {

  int gap = 5;
  
  // tiling size
  int tilex = floor((width  - gap) / NX);
  int tiley = floor((height - gap) / NY);
  
  // viewport offset ... corrected gap due to floor()
  int offx = (width  - (tilex * NX - gap)) / 2;
  int offy = (height - (tiley * NY - gap)) / 2;
  
  // viewport dimension
  int cw = tilex - gap;
  int ch = tiley - gap;
  
  // create new viewport for each camera
  for(int y = 0; y < NY; y++){
    for(int x = 0; x < NX; x++){
      int id = y * NX + x;
      int cx = offx + x * tilex;
      int cy = offy + y * tiley;
      cameras[id] = new PeasyCam(this, 400);
      cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
    }
  }
  
}


public void draw(){
  // clear background once, for the whole window
  setGLGraphicsViewport(0, 0, width, height);
  background(0);
  
  // render scene once per camera/viewport
  for(int i = 0; i < cameras.length; i++){
    pushStyle();
    pushMatrix();
    displayScene(cameras[i], i);
    popMatrix();
    popStyle();
  }
  // setGLGraphicsViewport(0, 0, width, height);
}


// some OpenGL instructions to set our custom viewport
//   https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glViewport.xhtml
//   https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glScissor.xhtml
void setGLGraphicsViewport(int x, int y, int w, int h){
  PGraphics3D pg = (PGraphics3D) this.g;
  PJOGL pgl = (PJOGL) pg.beginPGL();  pg.endPGL();
  
  pgl.enable(PGL.SCISSOR_TEST);
  pgl.scissor (x,y,w,h);
  pgl.viewport(x,y,w,h);
}


public void displayScene(PeasyCam cam, int ID){
  
  int[] viewport = cam.getViewport();
  int w = viewport[2];
  int h = viewport[3];
  int x = viewport[0];
  int y = viewport[1];
  int y_inv =  height - y - h; // inverted y-axis

  // scissors-test and viewport transformation
  setGLGraphicsViewport(x, y_inv, w, h);
  
  // modelview - using camera state
  cam.feed();

  // projection - using camera viewport
  perspective(60 * PI/180, w/(float)h, 1, 5000);

  // clear background (scissors makes sure we only clear the region we own)
  background(24);  
  stroke(0);
  strokeWeight(1);
  
  // scene objects
  pushMatrix();
  translate(-100, 0, 0);
  fill(0,96,255);
  box(100);
  popMatrix();

  pushMatrix();
  translate(100, 0, 0);
  rotateX(PI/2);
  float c = 255 * ID/(float) (cameras.length-1);
  fill(255-c/2, 255, 255-c);
  sphere(80);
  popMatrix();
  
  // screen-aligned 2D HUD
  cam.beginHUD();
  rectMode(CORNER);
  fill(0);
  rect(0, 0, 60, 23);
  fill(255,128,0);
  text("cam "+ID, 10, 15);
  cam.endHUD();
}
