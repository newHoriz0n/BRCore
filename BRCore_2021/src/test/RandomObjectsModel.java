package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import lib.ctrl.EEventTyp;
import lib.ctrl.gui.Aktion;
import lib.model.KreisObjekt;
import lib.model.OV_Model;

public class RandomObjectsModel extends OV_Model {

	public RandomObjectsModel() {

		Random r = new Random();

		long start = System.currentTimeMillis();

		// Generiere Kreise

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("gfx/smiley.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 1000000; i++) {
			KreisObjekt k = new RandomObject(r.nextInt(700000), r.nextInt(700000), 5 + r.nextInt(100),
					new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), 0), new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));

			k.setBild(img);
			k.setAusrichtung(r.nextDouble() * 2 * Math.PI);
			k.setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

				@Override
				public void run() {
					k.setAusrichtung(k.getAusrichtung() + 0.1);
				}
			});
			k.setEventAktion(EEventTyp.MAUSKLICK_RECHTS, new Aktion() {

				@Override
				public void run() {
					k.setAusrichtung(k.getAusrichtung() - 0.1);
				}
			});

			ov.addKreis(k, "global");

		}
	
		System.out.println("Erzeug - Dauer: " + (System.currentTimeMillis() - start));

	}

	@Override
	protected void notifyControllerSet() {	}

}
