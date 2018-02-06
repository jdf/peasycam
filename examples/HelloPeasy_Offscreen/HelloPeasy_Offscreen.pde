import peasy.PeasyCam;

PeasyCam cam;
PGraphics canvas;

void setup()
{
  size(500, 500, P2D);
  cam = new PeasyCam(this, 400);

  canvas = createGraphics(width, height, P3D);
}

void draw()
{
  // draw a simple rotating cube around a sphere onto an offscreen canvas
  canvas.beginDraw();
  canvas.background(55);

  canvas.pushMatrix();

  canvas.rotateX(radians(frameCount % 360));
  canvas.rotateZ(radians(frameCount % 360));

  canvas.noStroke();
  canvas.fill(20, 20, 20);
  canvas.box(100);

  canvas.fill(150, 255, 255);
  canvas.sphere(60);

  canvas.popMatrix();
  canvas.endDraw();

  // apply view matrix of peasy to canvas
  cam.getState().apply(canvas);

  // draw canvas onto onscreen
  image(canvas, 0, 0);
}