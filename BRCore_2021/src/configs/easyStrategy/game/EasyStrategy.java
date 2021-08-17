package configs.easyStrategy.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import configs.easyStrategy.game.Ressource.RessourcenTyp;
import configs.easyStrategy.game.kampf.Kampf;
import configs.easyStrategy.game.ki.ES_KI;
import configs.easyStrategy.game.stadt.Stadt;
import configs.easyStrategy.gui.GUI_Ctrl_Stadtansicht;
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
		STANDARD, TRUPPE_AUSSENDEN, TRUPPE_STEUERN, STADT_ABBAUEN
	};

	private ES_State state = ES_State.STANDARD;

	private ES_KI ki;

	// Truppe platzieren
	private Truppe focusTruppe;
	private Stadt focusStadt;

	// private List<Spieler> spieler;

	public EasyStrategy(int spielerZahl) {
		this.spielerZahl = spielerZahl;
		ES_UpdateThread est = new ES_UpdateThread();
		est.start();
	}

	public void updateES() {
		ov.updateObjekte();
		checkKampf();

		if (ki != null) {
			ki.calc();
		}
	}

	private void checkKampf() {
		List<Collision> cols = ov.checkRelevantCollision(Truppe.getKreisGruppe(), Truppe.getKreisGruppe());

		for (Collision c : cols) {
			Kampf.calcKampf((Truppe) c.getK1(), (Truppe) c.getK2());
			// Check besiegt
			if (((Truppe) c.getK1()).getKaempfer() <= 0) {
				((Truppe) c.getK2()).erobereTruppe((Truppe) ((Truppe) c.getK1()).die());
			}
			if (((Truppe) c.getK2()).getKaempfer() <= 0) {
				((Truppe) c.getK1()).erobereTruppe((Truppe) ((Truppe) c.getK2()).die());
			}
		}

	}

	private void delayedLoadObjects() {
		if (oc != null) {
			loadRessourcen();
			loadSpieler();
			loadStaedte();
			loadTruppen();

			this.ki = new ES_KI(this, 0);

		}
	}

	private void loadSpieler() {
		// TODO Auto-generated method stub

	}

	private void loadRessourcen() {
		addRessource(RessourcenTyp.WALD, 150, 300, 400);
		addRessource(RessourcenTyp.WALD, 1150, 300, 400);
	}

	private void addRessource(RessourcenTyp typ, int posX, int posY, int anzahl) {
		Ressource r = new Ressource(typ, posX, posY, anzahl, oc);
		ov.addKreis(r, "Ressourcen");
	}

	public void calcBesteRessourcenFuerStadt(Stadt s) {

		// Ressourcenabbau
		List<KreisObjekt> ressourcen = getRessourcenQuellen();
		if (ressourcen != null) {
			HashMap<RessourcenTyp, Ressource> beste = new HashMap<>();
			for (KreisObjekt o : ressourcen) {
				Ressource r = (Ressource) o;
				if (beste.containsKey(r.getTyp())) {
					double dNeu = r.calcDistanzZu(s.getPosX(), s.getPosY());
					double dBeste = beste.get(r.getTyp()).calcDistanzZu(s.getPosX(), s.getPosY());
					if (dNeu < dBeste) {
						beste.replace(r.getTyp(), r);
					}
				} else {
					beste.put(r.getTyp(), r);
				}
			}
			for (RessourcenTyp t : beste.keySet()) {
				setRessourcenAbbau(beste.get(t), s);
			}
		}

	}

	private void loadTruppen() {
		addTruppe(requestTruppe("Römer", 750, 0, 0, null, 1000, 10, 5));
		addTruppe(requestTruppe("Römer", 450, 0, 1, null, 100, 10, 5));
	}

	private void addTruppe(Truppe t) {
		ov.addKreis(t, "Truppen");
	}

	public void remove(Truppe t) {
		ov.removeKreis(t, "Truppen");
	}

	public Truppe requestTruppe(String name, double posX, double posY, int spielerID, Stadt stadt, int kaempfer, int arbeiter, int material) {
		Truppe t = new Truppe(name, posX, posY, spielerID, kaempfer, oc);
		t.setName("T" + t.getObjectID());
		t.addArbeiter(arbeiter);
		t.addMaterial(material);
		if (stadt != null) {
			if (stadt.verwendeKaempfer(kaempfer)) {
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

		calcStartRessourcen();
	}

	private void calcStartRessourcen() {
		List<KreisObjekt> staedte = ov.getKreisVonKategorie("Staedte");
		for (KreisObjekt k : staedte) {
			Stadt s = (Stadt) k;

			calcBesteRessourcenFuerStadt(s);
		}
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
		this.focusStadt = null;
	}

	public Truppe getFocusTruppe() {
		return focusTruppe;
	}

	public Stadt getFocusStadt() {
		return focusStadt;
	}

	public void setFocusStadt(Stadt s) {
		focusStadt = s;
		focusTruppe = null;
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

	public void setRessourcenAbbau(Ressource r, Stadt s) {
		if (s != null && r != null) {
			s.addRessourcenAbbau(r);
		}
	}

	public void removeRessourcenAbbau(Ressource r, Stadt s) {
		if (s != null) {
			s.removeRessourcenAbbau(r);
		}
	}

	public void toggleRessourcenAbbau(Ressource r, Stadt s) {
		if (s != null) {
			s.toggleRessourcenAbbau(r);
		}
	}

	public List<KreisObjekt> getRessourcenQuellen() {
		if (ov != null) {
			return ov.getKreisVonKategorie("Ressourcen");
		}
		return null;
	}

}
