package configs.randomBall.game;

import java.awt.Color;
import java.awt.Graphics2D;

import lib.math.Vektor3D;
import lib.model.KreisObjekt;
import lib.model.ObjektVerwaltung.ObjectVerwaltungSettingValues;
import lib.view.Betrachter;

public class Ball extends KreisObjekt {

	private Spieler besitzer;

	private Vektor3D speed;

	public Ball(double x, double y, double ballRadius) {
		super(x, y, ballRadius, Color.WHITE, Color.BLACK);
		this.speed = new Vektor3D();
	}

	public void setBesitzer(Spieler besitzer) {
		this.besitzer = besitzer;
	}

	public void schiessen(Vektor3D schuss, double startDistance) {
		this.speed.set(schuss.calcXYAngle(), startDistance);
		posX += this.speed.getX();
		posY += this.speed.getY();
		this.speed.set(schuss);
	}

	@Override
	public void draw(Graphics2D g2d, Betrachter b, double screenRadius, ObjectVerwaltungSettingValues bullseyeSetting) {

		if (besitzer != null) {
			g2d.setColor(Color.GREEN);
			g2d.drawOval((int) (posX - 20), (int) (posY - 20), (int) (2 * 20), (int) (2 * 20));
		} else {
			super.draw(g2d, b, screenRadius, bullseyeSetting);
		}
	}

	@Override
	protected void update(long dt) {
		if (besitzer != null) {
			posX = besitzer.getPosX();
			posY = besitzer.getPosY();
		} else {
			posX += speed.getX() * (double) dt / 1000.0;
			posY += speed.getY() * (double) dt / 1000.0;
		}
	}

	public void checkWandCollision(double feldlaenge, double feldbreite) {
		if (besitzer == null) {
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
	
	public Spieler getBesitzer() {
		return besitzer;
	}

}
