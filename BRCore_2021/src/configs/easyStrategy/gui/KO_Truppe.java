package configs.easyStrategy.gui;

import java.awt.Color;

import configs.easyStrategy.game.Spieler;
import configs.easyStrategy.game.Truppe;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class KO_Truppe extends KreisObjekt {

	public KO_Truppe(Truppe t, OV_Controller oc) {

		super(t.getPosX(), t.getPosY(), t.getRadius(), Color.YELLOW, Spieler.getSpielerFarbe(t.getSpielerID()));

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Truppenansicht("Truppendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), t);
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}

}
