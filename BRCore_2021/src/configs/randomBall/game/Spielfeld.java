package configs.randomBall.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Spielfeld {

	private double laenge;
	private double breite;

	private Tor tor;

	/**
	 * 
	 * @param laenge [m]
	 * @param breite [m]
	 */
	public Spielfeld(double laenge, double breite) {
		this.laenge = laenge;
		this.breite = breite;
	}
	
	public double getLaenge() {
		return laenge;
	}

	public double getBreite() {
		return breite;
	}
	
	/**
	 * 0,0 = Oben links des Spielfelds
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		
		g2d.setColor(Color.WHITE);
		g2d.drawRect(0, 0, (int)laenge, (int)breite);
		
	}
	
}
