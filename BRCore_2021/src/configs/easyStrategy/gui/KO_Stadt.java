package configs.easyStrategy.gui;

import java.awt.Color;

import configs.easyStrategy.game.Spieler;
import configs.easyStrategy.game.Stadt;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;
import lib.model.ObjektVerwaltung;

public class KO_Stadt extends KreisObjekt {

	private Stadt stadt;
	private OV_Controller oc;

	public KO_Stadt(Stadt s) {

		super(s.getPosX(), s.getPosY(), s.getRadius(), Color.GRAY, Spieler.getSpielerFarbe(s.getSpielerID()));

		this.stadt = s;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new OV_GUI_Controller("Stadtdetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}
	
	public void setController(OV_Controller oc) {
		this.oc = oc;
	}

}
