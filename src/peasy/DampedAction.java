package peasy;

/**
 * Based on a damned clever and aesthetic idea by David Bollinger.
 * 
 * http://www.davebollinger.com/works/p5/catmouse/CatMouse.pde.txt
 * 
 * @author jdf
 *
 */
abstract public class DampedAction
{
	private final PeasyCam p;
	private double velocity, dampening;

	public DampedAction(final PeasyCam p)
	{
		this(p, 0.16);
	}

	public DampedAction(final PeasyCam p, double friction)
	{
		this.p = p;
		this.velocity = 0;
		this.dampening = 1.0 - friction;
		p.getApplet().registerDraw(this);
	}

	public void impulse(double impulse)
	{
		velocity += impulse;
	}

	public void draw()
	{
		if (velocity == 0)
			return;
		velocity *= dampening;
		if (Math.abs(velocity) < .001)
		{
			velocity = 0;
		}
		else
		{
			behave(velocity);
			p.feed();
		}
	}

	abstract protected void behave(final double position);
}
