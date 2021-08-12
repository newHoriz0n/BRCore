package lib.ctrl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.Button;
import lib.ctrl.gui.ButtonRound;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;
import lib.model.OV_Model;
import lib.model.ObjektVerwaltung;
import lib.model.listener.EUpdateTopic;
import lib.model.listener.UpdateListener;
import lib.view.OV_ViewContainer;

public class OV_Controller implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, UpdateListener {

	private OV_Model m;
	private ObjektVerwaltung ov;
	private OV_ViewContainer v;

	private boolean[] tasten = new boolean[256];

	private long lastMoveUpdate;
	private long moveUpdateRate = 10;
	private int[] aktRealMausPos;

	private HashMap<String, OV_KeyHandler> keyHandler;
	private HashMap<String, OV_MouseHandler> mouseHandler;

	// GUI Controller
	private OV_GUI_Controller main_gc;
	private ArrayList<OV_GUI_Controller> overlay_gcs = new ArrayList<>();

	// Debug
	private boolean showMouseCoords;

	public OV_Controller(OV_Model m) {
		this.m = m;
		this.ov = m.getObjektVerwaltung();
		this.keyHandler = new HashMap<>();
		this.mouseHandler = new HashMap<>();
		this.aktRealMausPos = new int[2];

		this.main_gc = new OV_GUI_Controller("Main", 0, 0, 0, 0);

	}

	public void setViewer(OV_ViewContainer v) {
		this.v = v;
	}

	public OV_ViewContainer getViewer() {
		return v;
	}

	public void addKeyHandler(OV_KeyHandler l, String id) {
		this.keyHandler.put(id, l);
	}

	public void addMouseHandler(OV_MouseHandler l, String id) {
		this.mouseHandler.put(id, l);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (tasten.length > e.getKeyCode()) {
			tasten[e.getKeyCode()] = true;
		}
		for (String id : keyHandler.keySet()) {
			keyHandler.get(id).handleUpdate(tasten);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (tasten.length > e.getKeyCode()) {
			tasten[e.getKeyCode()] = false;
		}
		for (String id : keyHandler.keySet()) {
			keyHandler.get(id).handleUpdate(tasten);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// MOUSE WHEEL

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	// MOUSE MOVE

	@Override
	public void mouseDragged(MouseEvent e) {

		if (System.currentTimeMillis() - lastMoveUpdate > moveUpdateRate) {
			aktRealMausPos = getRealVonScreenKoords(e.getX(), e.getY());
			lastMoveUpdate = System.currentTimeMillis();
			for (OV_GUI_Controller c : overlay_gcs) {
				if (c.handleMouseMove(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton())) {
					return;
				}
			}
			main_gc.handleMouseMove(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton());
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (System.currentTimeMillis() - lastMoveUpdate > moveUpdateRate) {
			aktRealMausPos = getRealVonScreenKoords(e.getX(), e.getY());
			lastMoveUpdate = System.currentTimeMillis();
			for (String id : mouseHandler.keySet()) {
				mouseHandler.get(id).handleMouseUpdate(this, v);
			}
			for (OV_GUI_Controller c : overlay_gcs) {
				if (c.handleMouseMove(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], 0)) {
					return;
				}
			}
			main_gc.handleMouseMove(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], 0);
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
		for (OV_GUI_Controller c : overlay_gcs) {
			if (c.handleMousePress(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton())) {
				return;
			}
		}
		main_gc.setSize(v.getWidth(), v.getHeight());
		main_gc.handleMousePress(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (OV_GUI_Controller c : overlay_gcs) {
			if (c.handleMouseRelease(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton())) {
				return;
			}
		}
		main_gc.setSize(v.getWidth(), v.getHeight());
		main_gc.handleMouseRelease(e.getX(), e.getY(), aktRealMausPos[0], aktRealMausPos[1], e.getButton());
	}

	public int[] getRealVonScreenKoords(int screenX, int screenY) {
		if (v != null) {
			double[] offset = v.getOffset();
			return new int[] { (int) (screenX - offset[0]), (int) (screenY - offset[1]) };
		}
		return new int[] { -1, -1 };
	}

	public int[] getAktRealMausPos() {
		return aktRealMausPos;
	}

	@Override
	public void handleOVUpdate(EUpdateTopic topic) {
		if (topic.equals(EUpdateTopic.RELEVANZEN)) {

			List<Button> bs = new ArrayList<>();
			for (KreisObjekt k : ov.getDirektSichtbareKreise()) {
				ButtonRound b = new ButtonRound((int) k.getPosX(), (int) k.getPosY(), (int) k.getRadius());
				b.setAktionLinks(new Aktion() {

					@Override
					public void run() {
						k.handleEvent(EEventTyp.MAUSKLICK_LINKS);
					}
				});
				b.setAktionRechts(new Aktion() {

					@Override
					public void run() {
						k.handleEvent(EEventTyp.MAUSKLICK_RECHTS);
					}
				});
				bs.add(b);
			}
			main_gc.setCurrentButtons(bs);
		}
	}

	public void draw(Graphics2D g2d) {
		main_gc.drawGUIController(g2d); // Zeichnet Buttons
		if (showMouseCoords) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("" + aktRealMausPos[0] + "," + aktRealMausPos[1], aktRealMausPos[0], aktRealMausPos[1]);
		}
	}

	public void drawOverlayGUIs(Graphics2D g2d) {
		for (OV_GUI_Controller c : overlay_gcs) {
			c.drawGUIController(g2d);
		}
	}

	/**
	 * Debug Funktion zur Anzeige der Mauskoordinaten
	 * 
	 * @param enabled
	 */
	public void showMouseCoords(boolean enabled) {
		showMouseCoords = enabled;
	}

	public void addOverLayGC(OV_GUI_Controller gc) {
		overlay_gcs.add(gc);
		Collections.sort(overlay_gcs);
	}

	public void removeOverlayGC(String id) {
		for (int i = overlay_gcs.size() - 1; i >= 0; i--) {
			if (overlay_gcs.get(i).getTitel().equals(id)) {
				overlay_gcs.remove(i);
			}
		}
	}

}
