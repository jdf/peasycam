package examples.Resizeable_Offscreen;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.opengl.PGraphics3D;

public class Resizeable_Offscreen extends PApplet {

	//
	// Resizeable Window example, using an offscreen rendertarget.
	// 

	PeasyCam cam;

	// offscreen render target
	PGraphics3D pg;

	int window_w = 0;
	int window_h = 0;

	public void settings() {
		size(800, 600, P2D);
		smooth(8);
	}

	public void setup() {
		surface.setResizable(true);

		int w = 2 * width / 3 - 10;
		int h = height - 20;
		pg = (PGraphics3D)createGraphics(w, h, P3D);

		cam = new PeasyCam(this, pg, 400);
	}

	public void handleResize() {

		int w = 2 * width / 3 - 10;
		int h = height - 20;

		// check if window got resized
		if (window_w != width || window_h != height) {

			// Unfortunately processing has no easy, official way to resize a PGraphics.
			// The following is a workaround to resize a PGraphics while still keeping
			// the reference.
			int smooth = pg.smooth;
			pg.dispose();
			pg.removeCache(pg);
			pg.setPrimary(false);
			pg.setParent(this);
			pg.setSize(w, h);
			pg.initialized = false;
			pg.smooth = smooth;

			// Alternatively we could create a new PGraphics instead and pass it to
			// the camera,...  but PeasyCam.g is final atm.
			// TODO: discuss final PeasyCam.g

			// pg.dispose(); 
			// pg = (PGraphics3D) createGraphics(w, h, P3D);
			// cam.setCanvas(pg);

		}

		// update new window dimension
		window_w = width;
		window_h = height;

		// update camera viewport
		cam.setViewport(10, 10, w, h);
		// apply camera feed
		cam.feed();
	}

	public void draw() {

		handleResize();

		// render offscreen
		pg.beginDraw();
		{
			pg.perspective(60 * DEG_TO_RAD, pg.width / (float)pg.height, 1, 20000);
			pg.rotateX(-.5f);
			pg.rotateY(-.5f);
			pg.lights();
			pg.scale(10);
			pg.strokeWeight(1 / 10f);
			pg.background(16);
			pg.fill(255, 128, 0);
			pg.box(30);
			pg.pushMatrix();
			pg.translate(0, 0, 20);
			pg.fill(0, 96, 255);
			pg.box(5);
			pg.popMatrix();
	
			cam.beginHUD();
			{
				pg.fill(0, 128);
				pg.rect(0, 0, 150, 30);
				pg.fill(255);
				pg.text("Distance: " + nf((float)cam.getDistance(), 1, 2), 10, 18);
			}
			cam.endHUD();
		}
		pg.endDraw();

		
		// render onscreen (the primary graphics is 2D)
		background(48);
		
		// offscreen result
		int[] vp = cam.getViewport();
		image(pg, vp[0], vp[1], vp[2], vp[3]);

		// text
		fill(255);
		int tx = vp[0] + vp[2] + 15 ;
		int ty = 30;
		text("FrameRate: " + nfc(frameRate, 2), tx, ty);
	}

	
	
	public static void main(String args[]) {
		PApplet.main(new String[] { Resizeable_Offscreen.class.getName() });
	}

}