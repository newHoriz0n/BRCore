package configs.easyStrategy.game;

import java.util.ArrayList;
import java.util.List;

import lib.model.OV_Model;

public class EasyStrategy extends OV_Model {

	private int spielerZahl;

	// private List<Spieler> spieler;
	private List<Stadt> staedte;
	// private List<Truppe> truppen;
	// private List<Ressource> ressourcen;


	public EasyStrategy(int spielerZahl) {
		this.spielerZahl = spielerZahl;
		loadSpieler();
		loadStaedte();
		loadTruppen();
		loadRessourcen();
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
		addStadt("Hauptstadt", 1000, 0, 0);
		// focusedObjekt = staedte.get(0);
	}

	private void addStadt(String name, int posX, int posY, int spielerID) {
		Stadt s = new Stadt(name, posX, posY, spielerID);
//		KreisObjekt k = new KO_Stadt(s, this);
		staedte.add(s);
//		ov.addKreis(k);
	}



}
