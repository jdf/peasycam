import peasy.*;

PeasyCam cam;

void setup() {
  size(400,400,P3D);
  cam = new PeasyCam(this, 100);
  cam.rotateX(.5);
  cam.rotateY(.5);
}
void draw() {
  background(0);
  fill(255,0,0);
  box(30);
  pushMatrix();
  translate(0,0,-20);
  fill(0,0,255);
  box(5);
  popMatrix();
}


