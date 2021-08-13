package configs.easyStrategy.game;

import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.Ressource.RessourcenTyp;
import lib.model.KreisObjekt;
import lib.model.OV_Model;

public class EasyStrategy extends OV_Model {

	private int spielerZahl;
	private boolean geladen;

	// private List<Spieler> spieler;

	public EasyStrategy(int spielerZahl) {
		this.spielerZahl = spielerZahl;
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
		requestTruppe("Römer", 550, 0, 0);
	}

	private void addTruppe(Truppe t) {
		ov.addKreis(t, "Truppen");
	}

	public Truppe requestTruppe(String name, double posX, double posY, int spielerID) {
		Truppe t = new Truppe(name, posX, posY, spielerID, oc);
		addTruppe(t);
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

}
