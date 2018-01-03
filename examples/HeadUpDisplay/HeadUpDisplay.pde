
import peasy.PeasyCam;


//
// screen-aligned, orthographic HUD-scope
//

PeasyCam cam;

public void settings() {
  size(800, 600, P3D);
  smooth(8);
}

public void setup() {
  cam = new PeasyCam(this, 400);
}

public void draw() {
  rotateX(-.5f);
  rotateY(-.5f);
  lights();
  scale(10);
  strokeWeight(1 / 10f);
  background(0);
  fill(96, 255, 0);
  box(30);
  pushMatrix();
  translate(0, 0, 20);
  fill(0, 96, 255);
  box(5);
  popMatrix();

  cam.beginHUD();
  fill(0, 128);
  rect(0, 0, 70, 30);
  fill(255);
  text("" + nfc(frameRate, 2), 10, 18);
  cam.endHUD();
}
