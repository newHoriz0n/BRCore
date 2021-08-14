package configs.easyStrategy.game;

import java.util.Timer;
import java.util.TimerTask;

import configs.easyStrategy.game.Ressource.RessourcenTyp;
import lib.math.Vektor3D;
import lib.model.OV_Model;

public class EasyStrategy extends OV_Model {

	private int spielerZahl;
	private boolean geladen;

	public enum ES_State {
		STANDARD, TRUPPE_PLATZIEREN
	};

	private ES_State state = ES_State.STANDARD;
	
	
	
	// Truppe platzieren
	private Truppe truppeZuPlatzieren; 
	private Stadt startStadtDerTruppe;

	// private List<Spieler> spieler;

	public EasyStrategy(int spielerZahl) {
		this.spielerZahl = spielerZahl;
		
		ES_UpdateThread est = new ES_UpdateThread();
		est.start();
		
	}

	public void updateES() {
		ov.updateObjekte();
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
		addTruppe(requestTruppe("Römer", 550, 0, 0));
	}

	private void addTruppe(Truppe t) {
		ov.addKreis(t, "Truppen");
	}

	public void remove(Truppe t) {
		ov.removeKreis(t, "Truppen");
	}

	public Truppe requestTruppe(String name, double posX, double posY, int spielerID) {
		Truppe t = new Truppe(name, posX, posY, spielerID, oc);
		t.setName("T" + t.getObjectID());
		return t;
	}

	private void loadStaedte() {
		addStadt("Feindstadt", 1100, 0, 0);
		addStadt("Hauptstadt", 100, 0, 1);
	}

	private void addStadt(String name, double posX, double posY, int spielerID) {
		Stadt s = new Stadt(name, posX, posY, spielerID, oc);
		ov.addKreis(s, "Staedte");
	}

	@Override
	protected void notifyControllerSet() {
		delayedLoadObjects();
	}

	public void truppeEntsenden(Stadt s, Truppe t) {
		setState(ES_State.TRUPPE_PLATZIEREN);
		truppeZuPlatzieren = t;
		startStadtDerTruppe = s;
	}
	
	/**
	 * Setzt Position der truppeZuPlatzieren
	 */
	public void truppePlatzieren(int posX, int posY) {
		truppeZuPlatzieren.setZiel(new Vektor3D(posX, posY, 0));
		addTruppe(truppeZuPlatzieren);		
		startStadtDerTruppe.truppeEntsenden(truppeZuPlatzieren);
		truppeZuPlatzieren = null;
		oc.updateGUIs();
		setState(ES_State.STANDARD);
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

}
