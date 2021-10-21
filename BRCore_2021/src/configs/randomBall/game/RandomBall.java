package configs.randomBall.game;

import java.util.List;
import java.util.Random;

import lib.math.Vektor3D;
import lib.model.KreisObjekt;
import lib.model.OV_Model;
import lib.model.phx.Collision;

public class RandomBall extends OV_Model {

	private Spielfeld feld;
	private Ball b;
	private double schussspeed = 300;

	private Random r = new Random();

	// GameSettings
	private boolean useSpielerSpeedForSchuss;

	public RandomBall() {

		loadSpielfeld();
		loadBall();
		loadSpieler(2, 10, 100);

	}

	private void loadSpieler(int teams, int spielerProTeam, double speed) {
		for (int i = 0; i < teams; i++) {
			for (int j = 0; j < spielerProTeam; j++) {
				Spieler s = new Spieler(r.nextDouble() * (feld.getLaenge() - (feld.getLaenge() * 0.1) + feld.getLaenge() * 0.05),
						r.nextDouble() * (feld.getBreite() - (feld.getBreite() * 0.1) + feld.getBreite() * 0.05), i, speed);
				s.setGruppe(i);
				ov.addKreis(s, "Spieler");
			}
		}
		((Ball) ov.getKreisVonKategorie("Ball").get(0)).setBesitzer((Spieler) ov.getKreisVonKategorie("Spieler").get(0));
	}

	public void updateRB() {
		ov.updateObjekte();

		// Wandkollisionen
		List<KreisObjekt> ks = ov.getKreisVonKategorie("Spieler");
		for (KreisObjekt s : ks) {
			((Spieler) s).checkWandCollision(feld.getLaenge(), feld.getBreite());
		}
		List<KreisObjekt> bs = ov.getKreisVonKategorie("Ball");
		for (KreisObjekt s : bs) {
			((Ball) s).checkWandCollision(feld.getLaenge(), feld.getBreite());
		}

		// Spielerkollisionen
		for (KreisObjekt k1 : ks) {
			for (KreisObjekt k2 : ks) {
				if (k1 != k2) {
					Collision.calcCollisionCircleCircle((Spieler) k1, (Spieler) k2);
				}
			}
		}

		// Check Ballaufnahme
		if (b.getBesitzer() == null) {
			for (KreisObjekt ko : ks) {
				double d = ko.calcDistanzZu(b.getPosX(), b.getPosY());
				if (d < b.getRadius() + ko.getRadius()) {
					b.setBesitzer((Spieler) ko);
					break;
				}
			}
		}
	}

	public Spielfeld getFeld() {
		return feld;
	}

	private void loadBall() {
		Ball ball = new Ball(feld.getLaenge() / 2, feld.getBreite() / 2);
		ov.addKreis(ball, "Ball");

		this.b = ball;

	}

	private void loadSpielfeld() {
		this.feld = new Spielfeld(1000, 650);
	}

	@Override
	protected void notifyControllerSet() {
	}

	@Override
	public void update() {
		updateRB();
	}

	public void schiesse(int realMouseX, int realMouseY) {
		if (b.getBesitzer() != null) {
			Vektor3D mausrichtung = new Vektor3D(realMouseX - b.getBesitzer().getPosX(), realMouseY - b.getBesitzer().getPosY(), 0);

			Vektor3D gesamtSchuss = new Vektor3D(mausrichtung.calcXYAngle(), schussspeed);

			if (useSpielerSpeedForSchuss) {
				gesamtSchuss.add(b.getBesitzer().getSpeed());
			}

			b.setBesitzer(null);
			b.schiessen(gesamtSchuss, 15);
		}
	}

}
