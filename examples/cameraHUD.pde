/* PeasyCam provides a dead-simple mouse-driven camera for Processing.
 * full documentation at http://mrfeinberg.com/peasycam/
 */
 
import peasy.*;

PeasyCam cam;

void setup() {
  size(200, 200, P3D);
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(50);
  cam.setMaximumDistance(500);
}

void draw() {
  rotateX(-.5);
  rotateY(-.5);
  background(0);
  fill(255, 0, 0);
  box(30);
  pushMatrix();
  translate(0, 0, 20);
  fill(0, 0, 255);
  box(5);
  popMatrix();
  camera.beginHUD(); // start drawing relative to the camera view
  fill(255);
  rect(20, 10, 120, 30);
  fill(0);
  text(str(frameRate), 30, 30);
  camera.endHUD();  // and don't forget to stop/close with this!
}
