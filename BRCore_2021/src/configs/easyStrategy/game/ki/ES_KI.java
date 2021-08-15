package configs.easyStrategy.game.ki;

import java.util.List;
import java.util.Random;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.Stadt;
import lib.model.KreisObjekt;

public class ES_KI {

	private Random r = new Random();

	private long lastStrategieUpdate; // Einmal Pro Sekunde

	private double ausbildung_standard = 0.2; // Anteil Kaempfer im Normalzustand

	public void calc(EasyStrategy es, int spielerID) {
		if (System.currentTimeMillis() - lastStrategieUpdate > 1000) {
			calcStrategieUpdate(es, spielerID);
		}
	}

	public void calcStrategieUpdate(EasyStrategy es, int spielerID) {
		lastStrategieUpdate = System.currentTimeMillis();
		List<KreisObjekt> staedte = es.getStaedteVonSpieler(spielerID);
		for (KreisObjekt k : staedte) {
			Stadt s = (Stadt) k;
			if (s.getKaempfer() + s.getAzubis() < ausbildung_standard * s.getArbeiter()) {
				s.ausbildungStarten(1);
			}
		}
	}

}
