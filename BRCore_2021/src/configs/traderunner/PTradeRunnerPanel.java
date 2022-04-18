package configs.traderunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

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

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		int[] spielerPos = tr.getSpielerPosition();
		pv.aufKoordinateZentrieren(spielerPos[0] * feldgroesse + feldgroesse / 2, spielerPos[1] * feldgroesse + feldgroesse / 2);

		AffineTransform at = new AffineTransform();
		at.translate(pv.getOffX(), pv.getOffY());
		g2d.setTransform(at);

		// DRAW HERE
		// ////////////////////////////////////////////////////////////////////

		// Berechne Feld auf dem Spieler gerade ist.

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

		// GUI

		at.setToIdentity();
		g2d.setTransform(at);

		// c.drawGUI(g2d);

	}

	@Override
	public void handleEvent(OV_EEventTyp e) {
		repaint();
	}

}
