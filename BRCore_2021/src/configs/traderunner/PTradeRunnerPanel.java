package configs.traderunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.view.PanelViewer;

public class PTradeRunnerPanel extends JPanel implements OV_EventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TradeRunner tr;

	private int feldgroesse = 50;

	private PanelViewer pv;

	public PTradeRunnerPanel(TradeRunner tr) {
		this.tr = tr;

		tr.addUpdateListener(this);

		setDoubleBuffered(true);

		this.pv = new PanelViewer(this);

		addComponentListener(pv);

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Berechne Feld auf dem Spieler gerade ist.
		int[] spielerPos = tr.getSpielerPosition();
		int spielerScreenX = spielerPos[0] * feldgroesse + feldgroesse / 2;
		int spielerScreenY = spielerPos[1] * feldgroesse + feldgroesse / 2;

		pv.aufKoordinateZentrieren(spielerScreenX, spielerScreenY);

		AffineTransform at = new AffineTransform();
		at.translate(pv.getOffX(), pv.getOffY());
		g2d.setTransform(at);

		// DRAW HERE
		// ////////////////////////////////////////////////////////////////////

		// Sichtradius
		int sichtradius = (int) ((tr.getSichtRadius() + 0.5) * feldgroesse);
		g2d.setClip(new Ellipse2D.Double(spielerScreenX - (sichtradius), spielerScreenY - (sichtradius), 2 * sichtradius, 2 * sichtradius));


		// Zeichne Felder
		for (int i = 0; i < tr.getMap().getBreite(); i++) {
			for (int j = 0; j < tr.getMap().getHoehe(); j++) {
				// RAHMEN
				g2d.setColor(Color.GRAY);
				g2d.drawRect(i * feldgroesse, j * feldgroesse, feldgroesse, feldgroesse);
				// INHALT
				g.setColor(tr.getMap().getFeldTypen().get(tr.getMap().getFelder()[i][j]).getFarbe());
				g2d.fillRect(i * feldgroesse + 2, j * feldgroesse + 2, feldgroesse - 3, feldgroesse - 3);
			}
		}

		// Zeichne Bots
		g2d.setColor(Color.RED);
		for (Trader t : tr.getBots()) {
			g2d.fillOval(t.getPosition()[0] * feldgroesse + 2, t.getPosition()[1] * feldgroesse + 2, feldgroesse - 3, feldgroesse - 3);
		}

		// Zeichne Spieler
		g2d.setColor(Color.GREEN);
		g2d.fillOval(spielerPos[0] * feldgroesse + 2, spielerPos[1] * feldgroesse + 2, feldgroesse - 3, feldgroesse - 3);

		////////////////////////////////////////////////////////////////////////////////


		at.setToIdentity();
		g2d.setTransform(at);
		g2d.setClip(null);
		
		// GUI ///////////////////
		
		// Spieler Stats
		g2d.setColor(Color.BLACK);
		g2d.drawString("Test", 100, 100);
		
		
		
		
	}

	@Override
	public void handleEvent(OV_EEventTyp e) {
		repaint();
	}

}
