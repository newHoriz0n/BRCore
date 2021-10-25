package lib.math;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Polygon2D {

	private double[] xs;
	private double[] ys;

	private int[] intXs;
	private int[] intYs;

	/**
	 * 
	 * @param xs
	 *            in Reihenfolge im Uhrzeigersinn
	 * @param ys
	 *            in Reihenfolge im Uhrzeigersinn
	 */
	public Polygon2D(double[] xs, double[] ys) {
		if (xs.length != ys.length || xs.length < 3) {
			throw new IllegalArgumentException();
		}

		this.xs = xs;
		this.ys = ys;

		this.intXs = new int[xs.length];
		this.intYs = new int[xs.length];

		updateIntPoints();

	}

	private void updateIntPoints() {
		for (int i = 0; i < xs.length; i++) {
			intXs[i] = (int) xs[i];
			intYs[i] = (int) ys[i];
		}
	}

	public double[] getXs() {
		return xs;
	}

	public double[] getYs() {
		return ys;
	}

	public List<Line2D.Double> getSegments() {
		List<Line2D.Double> segments = new ArrayList<>();

		for (int i = 0; i < xs.length; i++) {
			int endIndex = (i + 1) % xs.length;
			segments.add(new Line2D.Double(xs[i], ys[i], xs[endIndex], ys[endIndex]));
		}

		return segments;

	}

	public Polygon2D getExpandedPolygon(double radius) {

		double[] exXs = new double[xs.length * 2];
		double[] exYs = new double[ys.length * 2];

		int index = 0;
		for (Line2D.Double l : getSegments()) {
			double dx = l.getX2() - l.getX1();
			double dy = l.getY2() - l.getY1();
			double n = Math.sqrt(dx * dx + dy * dy);

			dx = dx / n * radius;
			dy = dy / n * radius;

			double odx = dy;
			double ody = -dx;

			exXs[index] = l.getX1() + odx;
			exXs[index + 1] = l.getX2() + odx;
			exYs[index] = l.getY1() + ody;
			exYs[index + 1] = l.getY2() + ody;
			index += 2;
		}

		return new Polygon2D(exXs, exYs);

	}

	public boolean contains(double x, double y) {

		// System.out.println("---------------------------------");
		for (Line2D.Double l : getSegments()) {
			double al = Math.atan2(l.getY2() - l.getY1(), l.getX2() - l.getX1());
			double ap = Math.atan2(y - l.getY1(), x - l.getX1());
			double s = Math.sin(al - ap);
			// System.out.println("l: [" + l.x1 + "," + l.y1 + "," + l.x2 + "," + l.y2 +
			// "]");
			// System.out.println("p: [" + x + "," + y + "]");
			// System.out.println("s: " + s);
			if (s > 0) {
				return false;
			}
		}
		return true;
	}

	public int[] getIntXs() {
		return intXs;
	}

	public int[] getIntYs() {
		return intYs;
	}

	public void drawPolygon2D(Graphics2D g2d) {

		for (int i = 0; i < xs.length; i++) {
			int endIndex = (i + 1) % xs.length;
			g2d.drawLine((int) xs[i], (int) ys[i], (int) xs[endIndex], (int) ys[endIndex]);
		}

	}

	public int length() {
		return xs.length;
	}

}
