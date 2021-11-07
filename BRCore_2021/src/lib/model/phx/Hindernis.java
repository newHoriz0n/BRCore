package lib.model.phx;

import java.awt.Color;
import java.awt.Graphics2D;

import lib.math.Polygon2D;

public class Hindernis {

	private Polygon2D form;
	private Color farbe;

	public Hindernis(double[] xs, double[] ys) {
		this.form = new Polygon2D(xs, ys);
		this.farbe = Color.BLACK;
	}

	public Polygon2D getForm() {
		return form;
	}

	public void draw(Graphics2D g2d) {

		g2d.setColor(farbe);
		g2d.fillPolygon(form.getIntXs(), form.getIntYs(), form.getXs().length);

	}

	public void setHintergrundFarbe(Color c) {
		farbe = c;
	}

}
