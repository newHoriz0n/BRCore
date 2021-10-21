package lib.view;

import java.awt.Color;
import java.awt.Graphics2D;

import lib.model.KreisObjekt;

public class Betrachter_FocusObject implements Betrachter {
	
	private KreisObjekt fokusObjekt;

	public Betrachter_FocusObject() {

	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
	}

	public void setFocusObject(KreisObjekt ko) {
		this.fokusObjekt = ko;
	}

	@Override
	public double getX() {
		if (fokusObjekt != null) {
			return fokusObjekt.getPosX();
		}
		return 0;
	}

	@Override
	public double getY() {
		if (fokusObjekt != null) {
			return fokusObjekt.getPosY();
		}
		return 0;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		int r = 50;
		if (fokusObjekt != null) {
			r = (int) (fokusObjekt.getRadius() + 3);
		}
		g.drawOval((int) (getX() - r), (int) (getY() - r), 2 * r, 2 * r);
	}

	@Override
	public void drawFixed(Graphics2D g, int x, int y) {
		g.setColor(Color.GREEN);
		int r = 50;
		if (fokusObjekt != null) {
			r = (int) (fokusObjekt.getRadius() + 3);
		}
		g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
	}

}
