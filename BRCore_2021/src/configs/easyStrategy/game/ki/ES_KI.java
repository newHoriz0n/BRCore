package configs.easyStrategy.game.ki;

import java.util.List;
import java.util.Random;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.stadt.Stadt;
import lib.model.KreisObjekt;

public class ES_KI {

	private Random r = new Random();

	private long lastStrategieUpdate; // Einmal Pro Sekunde

	private double ausbildung_standard = 0.2; // Anteil Kaempfer im Normalzustand

	private EasyStrategy es;

	private int spielerID;

	public ES_KI(EasyStrategy es, int spielerID) {
		this.es = es;
		this.spielerID = spielerID;
		List<KreisObjekt> staedte = es.getStaedteVonSpieler(spielerID);
		for (KreisObjekt k : staedte) {
			Stadt s = (Stadt) k;
			es.calcBesteRessourcenFuerStadt(s);
		}
	}

	public void calc() {
		if (System.currentTimeMillis() - lastStrategieUpdate > 1000) {
			calcStrategieUpdate(es, spielerID);
		}
	}

	public void calcStrategieUpdate(EasyStrategy es, int spielerID) {
		lastStrategieUpdate = System.currentTimeMillis();

		// Städte
		List<KreisObjekt> staedte = es.getStaedteVonSpieler(spielerID);
		for (KreisObjekt k : staedte) {
			Stadt s = (Stadt) k;
			// Ausbildung
			if (s.getKaempfer() + s.getAzubis() < ausbildung_standard * s.getArbeiter()) {
				s.ausbildungStarten(1);
			}

		}
	}

}
