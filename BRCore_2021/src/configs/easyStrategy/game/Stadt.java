package configs.easyStrategy.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.gui.GUI_Ctrl_Stadtansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class Stadt extends KreisObjekt {

	private String name;
	private int spielerID;

	private int arbeiter;
	private int kaempfer;
	private int material;

	private List<Truppe> stationierteTruppen;

	public Stadt(String name, double posX, double posY, int spielerID, OV_Controller oc) {
		super(posX, posY, 30, Color.LIGHT_GRAY, Spieler.getSpielerFarbe(spielerID));
		this.name = name;
		this.spielerID = spielerID;
		this.stationierteTruppen = new ArrayList<Truppe>();

		Stadt me = this;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Stadtansicht("Stadtdetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}

	@Override
	protected void update(long dt) {	}

	public void setWerte(int arbeiter, int kaempfer, int material) {
		this.arbeiter = arbeiter;
		this.kaempfer = kaempfer;
		this.material = material;
	}

	public void ausbilden(int anzahl) {
		if (anzahl > material || anzahl > arbeiter) {
			throw new IllegalArgumentException();
		}
		arbeiter -= anzahl;
		material -= anzahl;
		kaempfer += anzahl;
	}

	public void stationiereTruppe(Truppe t) {
		this.stationierteTruppen.add(t);
	}

	public void truppeAufloesen(Truppe t) {
		this.stationierteTruppen.remove(t);
		// TODO: Truppeneinheiten in Stadt einquartieren.
	}

	public void truppeEntsenden(Truppe t) {
		this.stationierteTruppen.remove(t);
	}

	public List<Truppe> getStationierteTruppen() {
		return stationierteTruppen;
	}

	public String getName() {
		return name;
	}

	public int getSpielerID() {
		return spielerID;
	}

}
