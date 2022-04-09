package lib.view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;

/**
 * 
 * @author paulb
 *
 *         Bietet Funktionalitäten zum Verschieben der Ansicht in einem JPanel
 */

public class PanelViewer implements ComponentListener, MouseMotionListener, MouseListener {

	private JPanel panel;

	private int centerX;
	private int centerY;

	private int offX;
	private int offY;

	// MouseControls
	private int aktButton;
	private int firstMousePressX;
	private int firstMousePressY;
	private int lastMouseX;
	private int lastMouseY;
	private long lastMouseUpdate;
	private long mouseUpdateRate = 1; // [ms]

	private List<OV_EventHandler> eventListener;

	public PanelViewer(JPanel p) {
		this.panel = p;
		this.eventListener = new ArrayList<OV_EventHandler>();
	}

	public void addEventListener(OV_EventHandler e) {
		this.eventListener.add(e);
	}

	/**
	 * Setzt posX und posY so, dass die übergebene Koordinate in der Mitte des
	 * Bildes dargestellt wird.
	 * 
	 * @param centerX
	 * @param centerY
	 */
	public void aufKoordinateZentrieren(int centerX, int centerY) {

		this.centerX = centerX;
		this.centerY = centerY;

		offX = (panel.getWidth() / 2) - centerX;
		offY = (panel.getHeight() / 2) - centerY;

	}

	public int getOffX() {
		return offX;
	}

	public int getOffY() {
		return offY;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		aufKoordinateZentrieren(centerX, centerY);
		panel.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

		// Bildausschnitt verschieben
		if (aktButton == 3) {
			this.offX += arg0.getX() - lastMouseX;
			this.offY += arg0.getY() - lastMouseY;
		}

		// Alte Mausposition neu setzen
		lastMouseX = arg0.getX();
		lastMouseY = arg0.getY();
		// Neu zeichnen
		if (System.currentTimeMillis() - lastMouseUpdate > mouseUpdateRate) {
			lastMouseUpdate = System.currentTimeMillis();
			if (aktButton == 1) {
				for (OV_EventHandler eh : eventListener) {
					eh.handleEvent(OV_EEventTyp.MAUSDRAG_LINKS);
				}
			}
			if (aktButton == 3) {
				for (OV_EventHandler eh : eventListener) {
					eh.handleEvent(OV_EEventTyp.MAUSDRAG_RECHTS);
				}
			}
			panel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.lastMouseX = arg0.getX();
		this.lastMouseY = arg0.getY();

		if (System.currentTimeMillis() - lastMouseUpdate > mouseUpdateRate) {
			lastMouseUpdate = System.currentTimeMillis();
			panel.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		aktButton = e.getButton();
		this.firstMousePressX = e.getX();
		this.firstMousePressY = e.getY();
		this.lastMouseX = e.getX();
		this.lastMouseY = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (aktButton == 1) {
			for (OV_EventHandler eh : eventListener) {
				eh.handleEvent(OV_EEventTyp.MAUSKLICK_LINKS);
			}
		}
		if (aktButton == 3) {
			for (OV_EventHandler eh : eventListener) {
				eh.handleEvent(OV_EEventTyp.MAUSKLICK_RECHTS);
			}
		}

		this.aktButton = -1;
	}

	public int[] getRealKoords() {
		return new int[] { lastMouseX - offX, lastMouseY - offY };
	}

}
