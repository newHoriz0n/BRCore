package configs.randomBall.game;

import java.awt.Color;
import java.util.Random;

import lib.math.Vektor3D;
import lib.model.KreisObjekt;

public class Spieler extends KreisObjekt {

	private Vektor3D speed;

	public Spieler(double x, double y, int team, double speed) {
		super(x, y, 12, getTeamFarbe(team), Color.WHITE);

		loadRandomBewegung(speed);

	}

	/**
	 * 
	 * @param speed:
	 *            [px / s]
	 */
	private void loadRandomBewegung(double speed) {
		Random r = new Random();
		this.speed = new Vektor3D(r.nextDouble() * Math.PI * 2, speed);
	}

	private static Color getTeamFarbe(int team) {

		switch (team) {
		case 0:
			return Color.BLUE;

		default:
			return Color.RED;
		}

	}

	@Override
	protected void update(long dt) {

		double factor = (double) dt / 1000.0;
		this.posX += speed.getX() * factor;
		this.posY += speed.getY() * factor;

	}

	public void checkWandCollision(double feldlaenge, double feldbreite) {
		// Links und Rechts
		if (posX < 0) {
			posX = 0;
			speed.setX(speed.getX() * -1);
		} else if (posX > feldlaenge) {
			posX = feldlaenge;
			speed.setX(speed.getX() * -1);
		}
		// Oben und Unten
		if (posY < 0) {
			posY = 0;
			speed.setY(speed.getY() * -1);
		} else if (posY > feldbreite) {
			posY = feldbreite;
			speed.setY(speed.getY() * -1);
		}

	}

}
