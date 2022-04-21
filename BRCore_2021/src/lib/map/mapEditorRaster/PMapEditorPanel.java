package lib.map.mapEditorRaster;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller_Light;
import lib.ctrl.gui.elements.ButtonRect;
import lib.ctrl.gui.elements.RadioButtonGroup;
import lib.map.MapObjekt;
import lib.view.PanelViewer;

public class PMapEditorPanel extends JPanel implements OV_EventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapEditorModel m;
	private int feldgroesse = 30;

	private PanelViewer pv = new PanelViewer(this);
	private OV_GUI_Controller_Light c;

	// Feldeigenschaft
	private FObjektEigenschaftsFenster oef;
	private int[] fokussiertesFeld;

	public PMapEditorPanel(MapEditorModel m) {

		this.m = m;
		this.c = new OV_GUI_Controller_Light(0, 0, 50, 500, this);

		// INIT Feldtyp Buttons
		RadioButtonGroup rbg = new RadioButtonGroup();

		for (int i = 0; i < m.getFeldTypen().size(); i++) {
			ButtonRect b = new ButtonRect(5, 5 + i * 50, 40, 40);
			b.setText(m.getFeldTypen().get(i).getKuerzel());
			final int index = i;
			b.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					m.setPen(index);
				}
			});
			rbg.addButton(b);

		}

		c.addElement(rbg);

		setDoubleBuffered(true);

		// GUI Controller
		addMouseListener(c);
		addMouseMotionListener(c);

		// Panel Viewer
		addComponentListener(pv);
		addMouseListener(pv);
		addMouseMotionListener(pv);
		pv.addEventListener(this);
		pv.aufKoordinateZentrieren(m.getBreite() * feldgroesse / 2, m.getHoehe() * feldgroesse / 2);

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		AffineTransform at = new AffineTransform();
		at.translate(pv.getOffX(), pv.getOffY());
		g2d.setTransform(at);

		// DRAW HERE
		// ////////////////////////////////////////////////////////////////////

		// Berechne Feld über dem Maus gerade ist.
		calcAktFeld();
		int[] aktFeld = m.getAktFeld();

		// Zeichne Felder
		for (int i = 0; i < m.getBreite(); i++) {
			for (int j = 0; j < m.getHoehe(); j++) {
				// RAHMEN
				if (i == aktFeld[0] && j == aktFeld[1]) {
					g2d.setColor(Color.RED);
				} else {
					g2d.setColor(Color.GRAY);
				}
				g2d.drawRect(i * feldgroesse, j * feldgroesse, feldgroesse, feldgroesse);

				// INHALT
				g.setColor(m.getFeldTypen().get(m.getFelder()[i][j]).getFarbe());
				g2d.fillRect(i * feldgroesse + 2, j * feldgroesse + 2, feldgroesse - 3, feldgroesse - 3);

			}
		}

		// Objekte
		g2d.setFont(new Font("Arial", Font.PLAIN, 12));
		for (MapObjekt o : m.getMap().getMapObjekte()) {
			Color bg = m.getFeldTypen().get(m.getFelder()[o.getPosition()[0].intValue()][o.getPosition()[1].intValue()]).getFarbe();
			int red = 255 - bg.getRed();
			int green = 255 - bg.getGreen();
			int blue = 255 - bg.getBlue();
			g2d.setColor(new Color(red, green, blue));
			g2d.drawString("*", (int) (o.getPosition()[0] * feldgroesse) + 5, (int) (o.getPosition()[1] * feldgroesse) + 15);
		}

		// Fokussiertes Feld
		if (fokussiertesFeld != null) {
			g2d.setColor(Color.RED);
			g2d.drawRect(fokussiertesFeld[0] * feldgroesse, fokussiertesFeld[1] * feldgroesse, feldgroesse, feldgroesse);
		}

		////////////////////////////////////////////////////////////////////////////////

		// GUI

		at.setToIdentity();
		g2d.setTransform(at);
		c.drawGUI(g2d);

	}

	public void calcAktFeld() {
		int[] mousePos = pv.getRealKoords();
		m.setAktFeld(mousePos[0] / feldgroesse, mousePos[1] / feldgroesse);
	}

	@Override
	public void handleEvent(OV_EEventTyp e) {

		int realX = pv.getRealKoords()[0] / feldgroesse;
		int realY = pv.getRealKoords()[1] / feldgroesse;

		if (e.equals(OV_EEventTyp.MAUSKLICK_LINKS) || e.equals(OV_EEventTyp.MAUSDRAG_LINKS)) {
			// Typ ändern
			m.writeFeldTyp(realX, realY, m.getPen());
		} else if (e.equals(OV_EEventTyp.MAUSKLICK_RECHTS)) {

			if (realX >= 0 && realX < m.getBreite() && realY >= 0 && realY < m.getHoehe()) {
				this.fokussiertesFeld = new int[] { realX, realY };
				this.oef = new FObjektEigenschaftsFenster(this);
				this.oef.setAktObjekt(m.getMap().getOrCreateObjektVonPosition(realX, realY));
				this.oef.requestFocus();
			} else {
				this.fokussiertesFeld = null;
				this.oef.dispose();
				this.oef = null;
			}
		}

		repaint();
	}

	public void closeObjektEigenschaftsFenster() {
		this.oef = null;
	}

}
