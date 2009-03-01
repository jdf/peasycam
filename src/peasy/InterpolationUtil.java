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

import peasy.org.apache.commons.math.geometry.Rotation;
import peasy.org.apache.commons.math.geometry.Vector3D;

public class InterpolationUtil
{
	static public Rotation slerp(final Rotation a, final Rotation b, final double t)
	{
		final double cosTheta = a.getQ0() * b.getQ0() + a.getQ1() * b.getQ1() + a.getQ2()
				* b.getQ2() + a.getQ3() * b.getQ3();
		final double theta = Math.acos(cosTheta);
		final double sinTheta = Math.sin(theta);

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

	static public double smooth(final double a, final double b, final double t)
	{
		final double smooth = (t * t * (3 - 2 * t));
		return (b * smooth) + (a * (1 - smooth));

	}

	static public Vector3D smooth(final Vector3D a, final Vector3D b, final double t)
	{
		return new Vector3D(smooth(a.getX(), b.getX(), t), smooth(a.getY(), b.getY(), t),
				smooth(a.getZ(), b.getZ(), t));
	}

	static public double linear(final double a, final double b, final double t)
	{
		return a + (b - a) * t;
	}

	static public Vector3D linear(final Vector3D a, final Vector3D b, final double t)
	{
		return new Vector3D(linear(a.getX(), b.getX(), t), linear(a.getY(), b.getY(), t),
				linear(a.getZ(), b.getZ(), t));
	}
}
