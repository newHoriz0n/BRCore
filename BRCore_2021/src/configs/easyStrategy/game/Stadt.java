package configs.easyStrategy.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import configs.easyStrategy.gui.GUI_Ctrl_Stadtansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class Stadt extends KreisObjekt {

	private String name;
	private int spielerID;

	private double material;
	private double arbeiter;
	private int azubis; // Weder Arbeiter noch Kaempfer
	private int kaempfer;

//	private int deltaMaterial;
//	private int deltaArbeiter;
//	private int deltaKaempfer;

	private List<Truppe> stationierteTruppen;

	private Random r = new Random();

	private long ausbildungsDauer = 10000;
	private long lastAusbildungsStart;

	public Stadt(String name, double posX, double posY, int spielerID, OV_Controller oc) {
		super(posX, posY, 30, Color.LIGHT_GRAY, Spieler.getSpielerFarbe(spielerID));
		this.name = name;
		this.spielerID = spielerID;
		this.stationierteTruppen = new ArrayList<Truppe>();

		Stadt me = this;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Stadtansicht(oc, me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

	}

	@Override
	protected void update(long dt) {

		calcBevoelkerungswachstum(dt);
		calcAusbildung();

	}

	private void calcAusbildung() {
		if (azubis > 0) {
			if (System.currentTimeMillis() - lastAusbildungsStart > ausbildungsDauer) {
				ausbildungAbschliessen();
			}
		}
	}

	private void calcBevoelkerungswachstum(long dt) {
		double neu = (arbeiter + azubis + kaempfer) * Math.pow(1.001, (double) dt * r.nextDouble() / 1000.0) - kaempfer - azubis;
		arbeiter = neu;
	}

	public void setWerte(int arbeiter, int kaempfer, int material) {
		this.arbeiter = arbeiter;
		this.kaempfer = kaempfer;
		this.material = material;
	}

	public boolean ausbildungStarten(int anzahl) {
		if (anzahl < material && anzahl < arbeiter) {
			if (azubis == 0) {
				lastAusbildungsStart = System.currentTimeMillis();
			}
			arbeiter -= anzahl;
			material -= anzahl;
			azubis += anzahl;
			return true;
		}
		return false;
	}

	public void ausbildungAbschliessen() {
		this.azubis -= 1;
		this.kaempfer += 1;
		lastAusbildungsStart = System.currentTimeMillis();

	}

	public void ausbildungAbbrechen(int anzahl) {
		if (azubis > 0) {
			arbeiter += anzahl;
			material += anzahl;
			azubis -= anzahl;
		}
	}

	public double getAusbildungsFortschritt() {
		if (azubis > 0) {
			double wert = Math.max(0, Math.min(1, (double) (System.currentTimeMillis() - lastAusbildungsStart) / (double) ausbildungsDauer));
			return wert;
		}
		return 0;
	}

	public void stationiereTruppe(Truppe t) {
		this.stationierteTruppen.add(t);
	}

	public void truppeAufloesen(Truppe t) {
		kaempfer += t.getKaempfer();
		arbeiter += t.getArbeiter();
		material += t.getMaterial();
		this.stationierteTruppen.remove(t);
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

	public double getArbeiter() {
		return arbeiter;
	}

	public int getAzubis() {
		return azubis;
	}

	public int getKaempfer() {
		return kaempfer;
	}

	public double getMaterial() {
		return material;
	}

	public double sammleRessource(Ressource r) {
		double dist = r.calcDistanzZu(posX, posY);
		double wert = Math.min(arbeiter / dist, r.getAnzahl());
		material += wert;
		return wert;
	}

	public boolean verwendeKaempfer(int anzahl) {
		if (kaempfer >= anzahl) {
			kaempfer -= anzahl;
			return true;
		}
		return false;
	}

	public void addKaempfer(int anzahl) {
		kaempfer += anzahl;
	}

	public boolean verwendeArbeiter(int anzahl) {
		if (arbeiter >= anzahl) {
			arbeiter -= anzahl;
			return true;
		}
		return false;
	}

	public void addArbeiter(int anzahl) {
		arbeiter += anzahl;
	}

	public boolean verwendeMaterial(int anzahl) {
		if (material >= anzahl) {
			material -= anzahl;
			return true;
		}
		return false;
	}

	public void addMaterial(int anzahl) {
		material += anzahl;
	}

}
