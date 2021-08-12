package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import lib.ctrl.OV_KeyHandler;
import lib.ctrl.OV_MouseHandler;
import lib.ctrl.gui.Aktion;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.model.KreisObjekt;
import lib.model.ObjektVerwaltung;
import lib.model.listener.EUpdateTopic;
import lib.view.Betrachter;
import lib.view.FocusOnPerson_Betrachter;
import lib.view.OV_ViewContainer;

public class RandomObjectsMain {

	public static void main(String[] args) {

		Betrachter b = new FocusOnPerson_Betrachter(50000, 50000);
		ObjektVerwaltung ov = new ObjektVerwaltung(b);

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
			KreisObjekt k = new KreisObjekt(r.nextInt(700000), r.nextInt(700000), 5 + r.nextInt(100),
					new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), 0),
					new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));

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
			
			ov.addKreis(k);
			
		}

		System.out.println("Erzeug - Dauer: " + (System.currentTimeMillis() - start));

		// Berechne Relevante
		ov.calcRelevanzen();

		OV_Controller tc = new OV_Controller(ov);
		tc.addKeyHandler((OV_KeyHandler) b, "Betrachter");
		tc.addMouseHandler((OV_MouseHandler) b, "Betrachter");

		ov.addUpdateListener(EUpdateTopic.RELEVANZEN, tc);

		OV_ViewContainer v = new OV_ViewContainer(ov, tc);
		tc.setViewer(v);

		OV_MainFrame mf = new OV_MainFrame(ov, v);
		mf.addKeyListener(tc);

		mf.requestFocus();
	}

}
