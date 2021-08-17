package configs.easyStrategy.game.kampf;

import java.util.List;
import java.util.Random;

import configs.easyStrategy.game.Truppe;

public class Kampf {

	private static Random r = new Random();

	public static void calcKampf(Truppe t1, Truppe t2) {

		// TODO: Schnittfläche verwenden: http://walter.bislins.ch/blog/index.asp?page=Schnittfl%E4che+zweier+Kreise+berechnen
		
		List<Einheit> klein;
		List<Einheit> gross;

		List<Einheit> t1Einheiten = t1.getEinheiten();
		List<Einheit> t2Einheiten = t2.getEinheiten();

		int fT1 = Truppe.getKampfOberflaeche(t1Einheiten);
		int fT2 = Truppe.getKampfOberflaeche(t2Einheiten);

		if (fT1 < fT2) {
			klein = t1Einheiten;
			gross = t2Einheiten;
		} else {
			klein = t2Einheiten;
			gross = t1Einheiten;
		}

		for (int i = klein.size() - 1; i >= 0; i--) {
			if (gross.size() > 0) {
				int index = r.nextInt(gross.size());
				int sieger = entscheideIndividualKampf(gross.get(index),klein.get(i));
				if(sieger == 0) {
					klein.remove(i);
				}else {
					gross.remove(index);
				}
			}
		}
		
		t1.setEinheiten(t1Einheiten);
		t2.setEinheiten(t2Einheiten);

	}

	private static int entscheideIndividualKampf(Einheit e1, Einheit e2) {
		// TODO: Angriffsstärken && Größen berücksichtigen
		return r.nextInt(2);
	}

}
