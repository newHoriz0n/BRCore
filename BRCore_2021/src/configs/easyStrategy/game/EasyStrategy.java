package configs.easyStrategy.game;

import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.gui.KO_Stadt;
import lib.model.KreisObjekt;
import lib.model.OV_Model;

public class EasyStrategy extends OV_Model {

	private int spielerZahl;
	private boolean geladen;

	// private List<Spieler> spieler;
	private List<Stadt> staedte;
	// private List<Truppe> truppen;
	// private List<Ressource> ressourcen;

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
		// TODO Auto-generated method stub

	}

	private void loadTruppen() {
		// TODO Auto-generated method stub

	}

	private void loadStaedte() {
		this.staedte = new ArrayList<>();
		addStadt("Feindstadt", 1100, 0, 0);
		addStadt("Hauptstadt", 100, 0, 1);
	}

	private void addStadt(String name, int posX, int posY, int spielerID) {
		Stadt s = new Stadt(name, posX, posY, spielerID);
		KreisObjekt k = new KO_Stadt(s, oc);
		staedte.add(s);
		ov.addKreis(k);
	}

	@Override
	protected void notifyControllerSet() {
		delayedLoadObjects();
	}

}
