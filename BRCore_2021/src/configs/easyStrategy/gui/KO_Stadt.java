package configs.easyStrategy.gui;

import java.awt.Color;

import configs.easyStrategy.game.Spieler;
import configs.easyStrategy.game.Stadt;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class KO_Stadt extends KreisObjekt {

	public KO_Stadt(Stadt s, OV_Controller oc) {

		super(s.getPosX(), s.getPosY(), s.getRadius(), Color.LIGHT_GRAY, Spieler.getSpielerFarbe(s.getSpielerID()));

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Stadtansicht("Stadtdetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), s);
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}

}
