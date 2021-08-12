package lib.view;

import java.awt.Graphics2D;

/**
 * Abstrakte Klasse zum Setzen des ViewPorts
 * @author paulb
 *
 */
public interface Betrachter {
	
	public void update(long dt);

	public double getX();

	public double getY();

	public void draw(Graphics2D g);

	/**
	 * Zeichne Betrachter auf ScreenPosition X,Y - Alternative zu draw(). -> Zeichnet Betrachter NACH dem Shiften der Zeichenebene -> Verhindert Ruckeln
	 * @param g2d
	 * @param x,y: Position des Objektes auf Screen -> Muss für Drawposition übernommen werden!
	 */
	public void drawFixed(Graphics2D g2d, int x, int y);
}
