package configs.easyStrategy.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy.ES_State;
import configs.easyStrategy.game.kampf.Einheit;
import configs.easyStrategy.gui.ES_GUI_Ctrl_Truppenansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.math.Vektor3D;
import lib.model.KreisObjekt;

public class Truppe extends KreisObjekt {

	private String name;
	private int spielerID;

	private int kaempfer;
	private int material;
	private int arbeiter;

	private Vektor3D ziel;

	public Truppe(String name, double posX, double posY, int spielerID, int anzahl, OV_Controller oc) {
		super(posX, posY, 0, Spieler.getSpielerFarbe(spielerID), Spieler.getSpielerFarbe(spielerID));
		this.name = name;
		this.spielerID = spielerID;
		this.kaempfer = anzahl;
		this.gruppe = getKreisGruppe();

		updateRadius();

		Truppe me = this;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new ES_GUI_Ctrl_Truppenansicht("Truppendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
				((EasyStrategy) oc.getModel()).setFocusTruppe(me);
				((EasyStrategy) oc.getModel()).setState(ES_State.TRUPPE_STEUERN);
				;
			}
		});

	}

	private void updateRadius() {
		this.radius = (int) (Math.sqrt(kaempfer * 10));
	}

	@Override
	public void update(long dt) {
		// Essen
		// Moral
		// Bewegen
		if (ziel != null) {
			Vektor3D speed = calcSpeed().scale(dt / 1000.0);
			if (this.calcDistanzZu(ziel.getX(), ziel.getY()) < speed.calcAbsValue()) {
				posX = ziel.getX();
				posY = ziel.getY();
				ziel = null;
			} else {
				posX += speed.getX();
				posY += speed.getY();
			}
		}
	}

	public void stationiereTruppe() {
		this.ziel = null;
	}

	public void addMaterial(int anzahl) {
		this.material += anzahl;
	}

	public boolean materialVerwenden(int anzahl) {
		if (this.material >= anzahl) {
			this.material -= anzahl;
			return true;
		}
		return false;
	}

	public void addArbeiter(int anzahl) {
		this.arbeiter += anzahl;
	}

	public boolean arbeiterVerwenden(int anzahl) {
		if (this.arbeiter >= anzahl) {
			this.arbeiter -= anzahl;
			return true;
		}
		return false;
	}

	public void addKaempfer(int anzahl) {
		this.kaempfer += anzahl;
		updateRadius();
	}

	public boolean verkeinern(int anzahl) {
		if (this.kaempfer >= anzahl) {
			this.kaempfer -= anzahl;
			updateRadius();
			return true;
		}
		return false;
	}

	private Vektor3D calcSpeed() {
		if (ziel != null) {
			Vektor3D dif = new Vektor3D(ziel);
			dif.add(new Vektor3D(posX, posY, 0).scale(-1));
			return new Vektor3D(dif.calcXYAngle(), 5);
		} else {
			return new Vektor3D();
		}
	}

	public void setZiel(Vektor3D ziel) {
		this.ziel = ziel;
	}

	public String getName() {
		return name;
	}

	public int getSpielerID() {
		return spielerID;
	}

	public void setName(String string) {
		this.name = string;
	}

	public static int getKreisGruppe() {
		return 1;
	}

	public int getKaempfer() {
		return kaempfer;
	}

	public int getArbeiter() {
		return arbeiter;
	}

	public int getMaterial() {
		return material;
	}

	public List<Einheit> getEinheiten() {
		List<Einheit> es = new ArrayList<>();
		for (int i = 0; i < kaempfer; i++) {
			es.add(new Einheit(3));
		}
		return es;
	}

	public void setEinheiten(List<Einheit> einheiten) {
		kaempfer = einheiten.size();
		updateRadius();
	}

	public static int getKampfOberflaeche(List<Einheit> einheiten) {
		int flaeche = 0;
		for (Einheit e : einheiten) {
			flaeche += e.getGroesse();
		}
		return flaeche;
	}

	public void erobereTruppe(Truppe t) {
		this.arbeiter += t.getArbeiter();
		this.material += t.getMaterial();
	}

}
