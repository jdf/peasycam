
import peasy.PeasyCam;


PeasyCam cam;

public void settings() {
  size(800, 600, P3D);
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
  fill(255, 0, 0);
  box(30);
  pushMatrix();
  translate(0, 0, 20);
  fill(0, 0, 255);
  box(5);
  popMatrix();
}
