

import peasy.PeasyCam;


//
//
// MultiView (advanced version)
//
// N x N Camera Views of the same scene, using N x N separate pgraphics.
//
//

final int NX = 3;
final int NY = 2;
PeasyCam[] cameras = new PeasyCam[NX * NY];

public void settings() {
  size(1280, 720, P2D); // 2D
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
      PGraphics pg = createGraphics(cw, ch, P3D);
      cameras[id] = new PeasyCam(this, pg, 400);
      cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
    }
  }
  
}


public void draw(){  
  // render scene once per camera/viewport
  for(int i = 0; i < cameras.length; i++){
    displayScene(cameras[i], i);
  }

  background(0);
  for(int i = 0; i < cameras.length; i++){
    int[] viewport =  cameras[i].getViewport();
    image(cameras[i].getCanvas(), viewport[0], viewport[1], viewport[2], viewport[3]);
  }
}


public void displayScene(PeasyCam cam, int ID){
  
  PGraphics pg = cam.getCanvas();
  
  int[] viewport = cam.getViewport();
  int w = viewport[2];
  int h = viewport[3];

  pg.beginDraw();
  pg.resetMatrix();
  
  // modelview - using camera state
  cam.feed();
  
  // projection - using camera viewport
  pg.perspective(60 * PI/180, w/(float)h, 1, 5000);

  // clear background (scissors makes sure we only clear the region we own)
  pg.background(24);
  pg.stroke(0);
  pg.strokeWeight(0.3f);
  
  // scene objects
  pg.pushMatrix();
  pg.translate(-100, 0, 0);
  pg.fill(0,96,255);
  pg.box(100);
  pg.popMatrix();

  pg.pushMatrix();
  pg.translate(100, 0, 0);
  pg.rotateX(PI/2);
  float c = 255 * ID/(float) (cameras.length-1);
  pg.fill(255, 255-c/2, 255-c);
  pg.sphere(80);
  pg.popMatrix();
  
  // screen-aligned 2D HUD
  cam.beginHUD();
  pg.rectMode(CORNER);
  pg.fill(0);
  pg.rect(0, 0, 60, 23);
  pg.fill(255,128,0);
  pg.text("cam "+ID, 10, 15);
  cam.endHUD();
  
  pg.endDraw();
}
