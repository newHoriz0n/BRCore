package configs.billard.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.math.Vektor3D;
import lib.model.KreisObjekt;
import lib.model.OV_Model;
import lib.model.phx.Collision;
import lib.model.phx.Hindernis;

public class Billard extends OV_Model {

	private Tisch tisch;

	private List<Kugel> kugeln;

	private Random r = new Random();

	// GameSettings
	private double TISCHLAENGE = 2540; // [mm];

	public Billard() {

		loadTisch();
		loadBaelle();

	}

	private void loadTisch() {
		this.tisch = new Tisch(TISCHLAENGE / 2, TISCHLAENGE / 4);
	}

	private void loadBaelle() {
		this.kugeln = new ArrayList<>();

		// Random
		for (int i = 0; i < 1; i++) {
			Kugel k = new Kugel(r.nextDouble() * (tisch.getLaenge() - 100) + 50, r.nextDouble() * (tisch.getBreite() - 100) + 50, 0);
			k.schiessen(new Vektor3D(r.nextDouble() * Math.PI * 2, 200), 0);
			kugeln.add(k);
			ov.addKreis(k, "Kugel");
		}

	}

	public void updateBillard() {
		ov.updateObjekte();

		// Wandkollisionen
		List<KreisObjekt> ks = ov.getKreisVonKategorie("Kugel");
		boolean kollision = true;
		while (kollision == true) {
			kollision = false;
			for (KreisObjekt k : ks) {
				for (Hindernis h : tisch.getBanden()) {
					if (Collision.calcCollisionCircleFixPolygon((Kugel) k, h.getForm())) {
//						kollision = true;
					}
				}
			}

			// Kugelkollisionen
			for (KreisObjekt k1 : ks) {
				for (KreisObjekt k2 : ks) {
					if (k1 != k2) {
						if (Collision.calcCollisionCircleCircle((Kugel) k1, (Kugel) k2)) {
//							kollision = true;
						}
					}
				}
			}
		}

		// TODO: Kugel in Tasche

	}

	public Tisch getFeld() {
		return tisch;
	}

	@Override
	protected void notifyControllerSet() {
	}

	@Override
	public void update() {
		updateBillard();
	}

	public void schiesse(int realMouseX, int realMouseY) {

	}

	public List<Hindernis> getHindernisse() {
		return tisch.getBanden();
	}

}
