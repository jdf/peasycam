import peasy.*;

PeasyCam cam;

void setup() {
  size(600, 600, P3D);
  
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(100);
  cam.setMaximumDistance(500);
  
  // Reassign some drag handlers in order to free the left-click-mouse-drag for other uses
  PeasyDragHandler orbitDH = cam.getRotateDragHandler(); // get the RotateDragHandler
  cam.setCenterDragHandler(orbitDH);                     // set it to the Center/Wheel drag
  PeasyDragHandler panDH = cam.getPanDragHandler();      // get the PanDragHandler
  cam.setRightDragHandler(panDH);                        // set it to the right-button mouse drag
  cam.setLeftDragHandler(null);                          // sets no left-drag Handler
}
void draw() {
  background(0);
  fill(255, 0, 0);
  box(30);
  pushMatrix();
  translate(0, 0, 20);
  fill(0, 0, 255);
  box(5);
  popMatrix();
}
