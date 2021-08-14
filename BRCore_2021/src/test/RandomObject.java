package test;

import java.awt.Color;

import lib.model.KreisObjekt;

public class RandomObject extends KreisObjekt{

	public RandomObject(double x, double y, int rad, Color hintergrundFarbe, Color rahmenFarbe) {
		super(x, y, rad, hintergrundFarbe, rahmenFarbe);
	}

	@Override
	protected void update(long dt) {	}

}
