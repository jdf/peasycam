package peasy;

import peasy.org.apache.commons.math.geometry.Rotation;

public class RotationUtil
{
	static public Rotation slerp(final Rotation a, final Rotation b, final double t)
	{
		double cosTheta = a.getQ0() * b.getQ0() + a.getQ1() * b.getQ1() + a.getQ2()
				* b.getQ2() + a.getQ3() * b.getQ3();
		double theta = Math.acos(cosTheta);
		double sinTheta = Math.sin(theta);

		double w1, w2;
		if (sinTheta > 0.001f)
		{
			w1 = Math.sin((1.0f - t) * theta) / sinTheta;
			w2 = Math.sin(t * theta) / sinTheta;
		}
		else
		{
			w1 = 1.0 - t;
			w2 = t;
		}
		return new Rotation(w1 * a.getQ0() + w2 * b.getQ0(), w1 * a.getQ1() + w2
				* b.getQ1(), w1 * a.getQ2() + w2 * b.getQ2(), w1 * a.getQ3() + w2
				* b.getQ3(), true);
	}

}
