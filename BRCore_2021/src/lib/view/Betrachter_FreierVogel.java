package lib.view;

import java.awt.Graphics2D;

public class Betrachter_FreierVogel implements Betrachter {

	private int posX, posY;

	public Betrachter_FreierVogel() {
		this.posX = 0;
		this.posY = 0;
	}

	/** Setzt die Mitte des Bildes
	 * 
	 * @param posX
	 * @param posY
	 */
	public void setViewPort(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public void update(long dt) {
	}

	@Override
	public double getX() {
		return posX;
	}

	@Override
	public double getY() {
		return posY;
	}

	@Override
	public void draw(Graphics2D g) {
		
	}

	@Override
	public void drawFixed(Graphics2D g2d, int x, int y) {
	}

}
