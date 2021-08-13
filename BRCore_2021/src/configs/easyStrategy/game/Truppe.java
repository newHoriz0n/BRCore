package configs.easyStrategy.game;

import java.awt.Color;

import configs.easyStrategy.gui.GUI_Ctrl_Truppenansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class Truppe extends KreisObjekt{

	private String name;
	private int spielerID;

	public Truppe(String name, double posX, double posY, int spielerID, OV_Controller oc) {
		super(posX, posY, 10, Color.YELLOW, Spieler.getSpielerFarbe(spielerID));
		this.name = name;
		this.spielerID = spielerID;
		
		Truppe me = this;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Truppenansicht("Truppendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

		
	}

	public String getName() {
		return name;
	}

	public int getSpielerID() {
		return spielerID;
	}

}
