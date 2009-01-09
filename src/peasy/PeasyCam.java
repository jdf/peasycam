/*
   The PeasyCam Processing library, which provides an easy-peasy
   camera for 3D sketching.
  
   Copyright 2008 Jonathan Feinberg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package peasy;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import peasy.org.apache.commons.math.geometry.Rotation;
import peasy.org.apache.commons.math.geometry.Vector3D;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * 
 * @author Jonathan Feinberg
 */
public class PeasyCam
{
	private static final Vector3D LOOK = Vector3D.minusK;
	private static final Vector3D UP = Vector3D.minusJ;

	private static enum Constraint
	{
		X, Y
	}

	private final PApplet p;

	private final double startDistance;
	private final Vector3D startCenter;

	private PeasyMouseListener mouseListener = null;

	private double distance;
	private Vector3D center;
	private Rotation rotation;

	private Constraint dragConstraint = null;

	public final String VERSION = "0.1.1";

	public PeasyCam(final PApplet parent, final double distance)
	{
		this(parent, 0, 0, 0, distance);
	}

	public PeasyCam(final PApplet parent, final double lookAtX, final double lookAtY,
			final double lookAtZ, final double distance)
	{
		this.p = parent;
		this.startCenter = this.center = new Vector3D(lookAtX, lookAtY, lookAtZ);
		this.startDistance = this.distance = distance;
		this.rotation = new Rotation();
		setMouseControlled(true);
		feed();
	}

	public void setMouseControlled(final boolean isMouseControlled)
	{
		if (isMouseControlled)
		{
			if (mouseListener != null)
			{
				PApplet.println("PeasyCam is already listening to mouse.");
				return;
			}
			mouseListener = new PeasyMouseListener();
			p.registerMouseEvent(mouseListener);
			p.registerKeyEvent(mouseListener);
		}
		else
		{
			if (mouseListener == null)
			{
				PApplet.println("PeasyCam is not listening to mouse.");
				return;
			}
			p.unregisterMouseEvent(mouseListener);
			p.unregisterKeyEvent(mouseListener);
			mouseListener = null;
		}
	}

	public String version()
	{
		return VERSION;
	}

	protected class PeasyMouseListener
	{
		public void keyEvent(final KeyEvent e)
		{
			if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_SHIFT)
				dragConstraint = null;
		}

		public void mouseEvent(final MouseEvent e)
		{
			if (e.getID() == MouseEvent.MOUSE_CLICKED && e.getClickCount() == 2)
			{
				reset();
			}
			else if (e.getID() == MouseEvent.MOUSE_RELEASED)
			{
				dragConstraint = null;
			}
			else if (e.getID() == MouseEvent.MOUSE_DRAGGED)
			{
				float dx = p.mouseX - p.pmouseX;
				float dy = p.mouseY - p.pmouseY;

				if (e.isShiftDown())
				{
					if (dragConstraint == null && Math.abs(dx - dy) > 1)
					{
						dragConstraint = Math.abs(dx) > Math.abs(dy) ? Constraint.X
								: Constraint.Y;
					}
				}
				else
				{
					dragConstraint = null;
				}

				final int b = p.mouseButton;
				if (b == PConstants.CENTER || (b == PConstants.LEFT && e.isMetaDown()))
					mousePan(dx, dy);
				else if (b == PConstants.LEFT)
					mouseRotate(dx, dy);
				else if (b == PConstants.RIGHT)
					mouseZoom(dy);
			}
		}

		public void reset()
		{
			center = startCenter;
			distance = startDistance;
			rotation = new Rotation();
			feed();
		}

		private void mouseZoom(final double delta)
		{
			setDistance(distance + delta * Math.sqrt(distance * .2));
		}

		private void mousePan(final float dxMouse, final float dyMouse)
		{
			final double panScale = Math.sqrt(distance * .005);
			pan(dragConstraint == Constraint.Y ? 0 : -dxMouse * panScale,
					dragConstraint == Constraint.X ? 0 : dyMouse * panScale);
		}

		private void mouseRotate(final float dx, final float dy)
		{
			final Vector3D u = LOOK.scalarMultiply(distance).negate();
			final double rotationScale = Math.sqrt(distance * .05);

			if (dragConstraint != Constraint.Y)
			{
				final Vector3D vx = u.add(new Vector3D(dx * rotationScale, 0, 0));
				rotateY(Vector3D.angle(u, vx) * (dx < 0 ? -1 : 1));
			}

			if (dragConstraint != Constraint.X)
			{
				final Vector3D vy = u.add(new Vector3D(0, dy * rotationScale, 0));
				final double yAngle = Vector3D.angle(u, vy) * (dy < 0 ? -1 : 1);
				rotateX(yAngle);
			}
		}
	}

	public void lookAt(final double x, final double y, final double z)
	{
		center = new Vector3D(x, y, z);
	}

	public void setDistance(final double distance)
	{
		this.distance = distance;
		if (this.distance < .01)
			this.distance = .01;
		feed();
	}

	public void feed()
	{
		final Vector3D pos = rotation.applyTo(LOOK).scalarMultiply(distance).add(center);
		final Vector3D rup = rotation.applyTo(UP);
		p.camera((float) pos.getX(), (float) pos.getY(), (float) pos.getZ(),
				(float) center.getX(), (float) center.getY(), (float) center.getZ(),
				(float) rup.getX(), (float) rup.getY(), (float) rup.getZ());
	}

	public void pan(final double dx, final double dy)
	{
		center = center.add(rotation.applyTo(new Vector3D(dx, dy, 0)));
		feed();
	}

	public void rotateX(final double yAngle)
	{
		rotation = rotation.applyTo(new Rotation(Vector3D.plusI, yAngle));
		feed();
	}

	public void rotateY(final double angle)
	{
		rotation = rotation.applyTo(new Rotation(Vector3D.plusJ, angle));
		feed();
	}

	public void rotateZ(final double angle)
	{
		rotation = rotation.applyTo(new Rotation(Vector3D.plusK, angle));
		feed();
	}

}
