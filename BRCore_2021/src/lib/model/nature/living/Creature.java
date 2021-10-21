package lib.model.nature.living;

import java.awt.Color;

import lib.model.KreisObjekt;

public class Creature extends KreisObjekt {

	public Creature(double x, double y, int rad, Color hintergrundFarbe, Color rahmenFarbe) {
		super(x, y, rad, hintergrundFarbe, rahmenFarbe);
	}

	@Override
	protected void update(long dt) {
		// TODO Auto-generated method stub
		
	}
	
}
