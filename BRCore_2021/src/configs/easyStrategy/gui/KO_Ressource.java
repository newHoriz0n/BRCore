package configs.easyStrategy.gui;

import java.awt.Color;

import configs.easyStrategy.game.Ressource;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class KO_Ressource extends KreisObjekt {

	public KO_Ressource(Ressource r, OV_Controller oc) {

		super(r.getPosX(), r.getPosY(), r.getRadius(), Ressource.getColorVonTyp(r.getTyp()), Ressource.getColorVonTyp(r.getTyp()));

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Ressourcenansicht("Ressourcendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), r);
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}

}
