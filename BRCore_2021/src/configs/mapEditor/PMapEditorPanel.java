package configs.mapEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import configs.mapEditor.MapEditorModel.FeldTyp;
import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller_Light;
import lib.ctrl.gui.elements.ButtonRect;
import lib.ctrl.gui.elements.RadioButtonGroup;
import lib.view.PanelViewer;

public class PMapEditorPanel extends JPanel implements OV_EventHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapEditorModel m;
	private int feldgroesse = 30;

	private PanelViewer pv = new PanelViewer(this);
	private OV_GUI_Controller_Light c;

	public PMapEditorPanel(MapEditorModel m) {

		this.m = m;
		this.c = new OV_GUI_Controller_Light(0, 0, 50, 500, this);
		
		RadioButtonGroup rbg = new RadioButtonGroup();
		
		ButtonRect bWand = new ButtonRect(5, 5, 40, 40);
		bWand.setText("[#]");
		bWand.setAktionLinks(new Aktion() {
			
			@Override
			public void run() {
				m.setPen(FeldTyp.WAND);
			}
		});
		rbg.addButton(bWand);

		ButtonRect bFrei = new ButtonRect(5, 55, 40, 40);
		bFrei.setText("[ ]");
		bFrei.setAktionLinks(new Aktion() {
			
			@Override
			public void run() {
				m.setPen(FeldTyp.FREI);
			}
		});
		rbg.addButton(bFrei);
		
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
				if (m.getFelder()[i][j] == FeldTyp.WAND) {
					g.setColor(Color.DARK_GRAY);
					g2d.fillRect(i * feldgroesse + 2, j * feldgroesse + 2, feldgroesse - 3, feldgroesse - 3);
				}
			}
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
		if(e.equals(OV_EEventTyp.MAUSKLICK_LINKS) || e.equals(OV_EEventTyp.MAUSDRAG_LINKS)) {
			m.writeFeldTyp(pv.getRealKoords()[0] / feldgroesse, pv.getRealKoords()[1] / feldgroesse, m.getPen());
		}
		repaint();
	}

}
