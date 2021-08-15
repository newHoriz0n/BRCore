package configs.easyStrategy.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import configs.easyStrategy.game.Ressource.RessourcenTyp;
import configs.easyStrategy.game.ki.ES_KI;
import configs.easyStrategy.gui.GUI_Ctrl_Stadtansicht;
import configs.easyStrategy.gui.GUI_Ctrl_Truppenansicht;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.math.Vektor3D;
import lib.model.KreisObjekt;
import lib.model.OV_Model;
import lib.model.phx.Collision;

public class EasyStrategy extends OV_Model {

	private int spielerZahl;
	private boolean geladen;

	private Random r = new Random();

	public enum ES_State {
		STANDARD, TRUPPE_AUSSENDEN, TRUPPE_BEWEGEN
	};

	private ES_State state = ES_State.STANDARD;

	private ES_KI ki;

	// Truppe platzieren
	private Truppe focusTruppe;
	private Stadt focusStadt;

	// private List<Spieler> spieler;

	public EasyStrategy(int spielerZahl) {
		this.spielerZahl = spielerZahl;
		this.ki = new ES_KI();
		ES_UpdateThread est = new ES_UpdateThread();
		est.start();

	}

	public void updateES() {
		ov.updateObjekte();
		checkKampf();

		calcRessourcenVerteilung();

		ki.calc(this, 0);
	}

	private void calcRessourcenVerteilung() {
		for (KreisObjekt o : ov.getKreisVonKategorie("Ressourcen")) {
			Ressource r = (Ressource) o;
			for (KreisObjekt os : ov.getKreisVonKategorie("Staedte")) {
				Stadt s = (Stadt) os;
				r.abbauen(s.sammleRessource(r));
				// TODO: Rest fair verteilen
			}
		}
	}

	private void checkKampf() {
		List<Collision> cols = ov.checkRelevantCollision(Truppe.getKreisGruppe(), Truppe.getKreisGruppe());

		for (Collision c : cols) {
			calcKampf((Truppe) c.getK1(), (Truppe) c.getK2());
		}

	}

	private void calcKampf(Truppe k1, Truppe k2) {
		if (k1.getSpielerID() != k2.getSpielerID()) {
			int z = r.nextInt(2);
			if (z == 0) {
				k1.die();
			} else {
				k2.die();
			}
		}
	}

	private void delayedLoadObjects() {
		if (oc != null) {
			loadSpieler();
			loadStaedte();
			loadTruppen();
			loadRessourcen();
		}
	}

	private void loadSpieler() {
		// TODO Auto-generated method stub

	}

	private void loadRessourcen() {
		addRessource(RessourcenTyp.WALD, 150, 300, 400);
	}

	private void addRessource(RessourcenTyp typ, int posX, int posY, int anzahl) {
		Ressource r = new Ressource(typ, posX, posY, anzahl, oc);
		ov.addKreis(r, "Ressourcen");
	}

	private void loadTruppen() {
		addTruppe(requestTruppe("Römer", 550, 0, 0, null, 1000));
	}

	private void addTruppe(Truppe t) {
		ov.addKreis(t, "Truppen");
	}

	public void remove(Truppe t) {
		ov.removeKreis(t, "Truppen");
	}

	public Truppe requestTruppe(String name, double posX, double posY, int spielerID, Stadt stadt, int anzahl) {
		Truppe t = new Truppe(name, posX, posY, spielerID, anzahl, oc);
		t.setName("T" + t.getObjectID());
		if (stadt != null) {
			if (stadt.verwendeKaempfer(anzahl)) {
				return t;
			} else {
				return null;
			}
		}
		return t;
	}

	private void loadStaedte() {
		addStadt("Feindstadt", 1100, 0, 0, 10, 10, 2);
		addStadt("Hauptstadt", 100, 0, 1, 10, 10, 2);
	}

	public Stadt requestStadt(String name, double posX, double posY, int spielerID) {
		for (KreisObjekt os : ov.getKreisVonKategorie("Staedte")) {
			if (os.calcDistanzZu(posX, posY) < os.getRadius() + 50) {
				return null;
			}
		}
		return addStadt(name, posX, posY, spielerID, 0, 0, 0);
	}

	private Stadt addStadt(String name, double posX, double posY, int spielerID, int material, int arbeiter, int kaempfer) {
		Stadt s = new Stadt(name, posX, posY, spielerID, oc);
		s.setWerte(arbeiter, kaempfer, material);
		ov.addKreis(s, "Staedte");
		return s;
	}

	@Override
	protected void notifyControllerSet() {
		delayedLoadObjects();
	}

	public void truppeEntsenden(Stadt s, Truppe t) {
		setState(ES_State.TRUPPE_AUSSENDEN);
		focusTruppe = t;
		focusStadt = s;
	}

	/**
	 * Setzt Position der truppeZuPlatzieren
	 */
	public void truppePlatzieren(int posX, int posY) {
		if (focusTruppe != null) {
			focusTruppe.setZiel(new Vektor3D(posX, posY, 0));
			addTruppe(focusTruppe);
			focusStadt.truppeEntsenden(focusTruppe);
			focusTruppe = null;

			oc.updateGUIs();
			setState(ES_State.STANDARD);
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Setzt das Ziel der fokusierten Truppe auf x,y
	 * 
	 * @param posX
	 * @param posY
	 */
	public void truppeBewegen(int posX, int posY) {
		focusTruppe.setZiel(new Vektor3D(posX, posY, 0));
	}

	public void setFocusTruppe(Truppe t) {
		this.focusTruppe = t;
	}

	public Truppe getFocusTruppe() {
		return focusTruppe;
	}

	public void setState(ES_State status) {
		this.state = status;
	}

	public ES_State getState() {
		return state;
	}

	class ES_UpdateThread extends Thread {

		public ES_UpdateThread() {

			Timer t = new Timer();
			TimerTask tt = new TimerTask() {

				@Override
				public void run() {
					updateES();
				}
			};
			t.scheduleAtFixedRate(tt, 0, 1000);
		}

	}

	public List<KreisObjekt> getStaedteVonSpieler(int spielerID) {
		List<KreisObjekt> staedte = new ArrayList<>();
		if (ov != null) {
			for (KreisObjekt k : ov.getKreisVonKategorie("Staedte")) {
				if (((Stadt) k).getSpielerID() == spielerID) {
					staedte.add(k);
				}
			}
		}
		return staedte;
	}

	public void stationiereTruppeInStadt(Stadt s, Truppe t) {
		s.stationiereTruppe(t);
		t.stationiereTruppe();

		OV_GUI_Controller sc = new GUI_Ctrl_Stadtansicht(oc, s, this);
		sc.setHintergrundFarbe(Color.BLACK);
		oc.addOverLayGC(sc);
		focusTruppe = null;
		focusStadt = s;
	}

}
