package lib.ctrl.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public abstract class OV_GUI_Controller implements Comparable<OV_GUI_Controller> {

	// Verwaltet Buttons

	protected OV_Model m;

	private int gruppe;

	private String titel;

	private boolean visible;
	private boolean enabled;
	private boolean active;

	private int zIndex;
	protected int posX, posY, width, height;
	private Color farbeHintergrund;

	private Object ButtonLock = new Object();

	protected List<Button> buttons;
	private List<Button> tempButtons;

	private boolean freeMousePress = true; // Maus hat bei Mauspress keinen Button gedrückt.

	public OV_GUI_Controller(int gruppe, String titel, int posX, int posY, int width, int height, OV_Model m) {
		this.m = m;
		this.gruppe = gruppe;
		this.titel = titel;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.buttons = loadButtons();
		this.tempButtons = new ArrayList<>(buttons);
	}

	/**
	 * Methode zur definition von Aktionen, die bei Aktualisierung der GUI
	 * ausgeführt werden sollen.
	 */
	public abstract void updateGUICtrl();

	public boolean isMouseOver(int mouseX, int mouseY) {
		return (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height);
	}

	public void setHintergrundFarbe(Color f) {
		this.farbeHintergrund = f;
	}

	public boolean handleMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			updateTempButtons();
			for (Button b : buttons) {
				b.handleMouseMove(aktRealMausPosX, aktRealMausPosY);
			}
			return true;
		}
		return false;
	}

	public boolean handleMouseMoveIntern(int aktScreenMouseX, int aktScreenMouseY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			int[] internMausCoords = getGUICoordsVonScreenCoords(aktScreenMouseX, aktScreenMouseY);
			updateTempButtons();
			for (Button b : buttons) {
				b.handleMouseMove(internMausCoords[0], internMausCoords[1]);
			}
			return true;
		}
		return false;
	}

	public boolean handleMousePress(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			updateTempButtons();
			freeMousePress = true;
			for (Button b : buttons) {
				if (b.handleMousePress(aktRealMausPosX, aktRealMausPosY, button)) {
					freeMousePress = false;
				}
			}
			active = true;
			return true;
		} else {
			active = false;
			return false;
		}
	}

	public boolean handleMousePressIntern(int aktScreenMouseX, int aktScreenMouseY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			int[] internMausCoords = getGUICoordsVonScreenCoords(aktScreenMouseX, aktScreenMouseY);
			updateTempButtons();
			freeMousePress = true;
			for (Button b : buttons) {
				if (b.handleMousePress(internMausCoords[0], internMausCoords[1], button)) {
					freeMousePress = false;
				}
			}
			active = true;
			return true;
		} else {
			active = false;
			return false;
		}
	}

	public boolean handleMouseRelease(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			updateTempButtons();
			for (Button b : buttons) {
				b.handleMouseRelease(aktRealMausPosX, aktRealMausPosY, button);
			}
			if (freeMousePress) {
				handleFreeMouseRelease(aktRealMausPosX, aktRealMausPosY, button);
			}
			return true;
		}
		return false;

	}

	public boolean handleMouseReleaseIntern(int aktScreenMouseX, int aktScreenMouseY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			int[] internMausCoords = getGUICoordsVonScreenCoords(aktScreenMouseX, aktScreenMouseY);
			updateTempButtons();
			for (Button b : buttons) {
				b.handleMouseRelease(internMausCoords[0], internMausCoords[1], button);
			}
			if (freeMousePress) {
				handleFreeMouseRelease(internMausCoords[0], internMausCoords[1], button);
			}
			return true;
		}
		return false;
	}

	public abstract void handleFreeMouseRelease(int realMouseX, int realMouseY, int button);

	/**
	 * Use drawGUIContent() um eigene Inhalte zu zeichnen
	 * 
	 * @param g
	 * @param transform
	 */
	public void drawGUIController(Graphics2D g, boolean transform) {
		synchronized (ButtonLock) {

			if (farbeHintergrund != null) {
				g.setColor(farbeHintergrund);
				g.fillRect(posX, posY, width, height);
			}

			if (transform) {
				g.translate(posX, posY);
			}
			drawGUIBackground(g);

			updateTempButtons();
			for (Button b : buttons) {
				b.draw(g);
			}

			drawGUIOverlay(g);

			if (transform) {
				g.translate(-posX, -posY);
			}

			drawGUIStaticOverlay(g);

		}
	}

	/**
	 * Zeichnet ohne Transformierung drüber (Geht nur für OverlayGUIs, nicht für
	 * MAIN!)
	 * 
	 * @param g
	 */
	protected void drawGUIStaticOverlay(Graphics2D g2d) {
	}

	protected void drawGUIBackground(Graphics2D g2d) {
	}

	protected void drawGUIOverlay(Graphics2D g2d) {
	}

	public int getGruppe() {
		return gruppe;
	}

	public void setZIndex(int z) {
		this.zIndex = z;
	}

	public int getZIndex() {
		return zIndex;
	}

	public String getTitel() {
		return titel;
	}

	@Override
	public int compareTo(OV_GUI_Controller o) {
		if (zIndex > o.getZIndex()) {
			return 1;
		}
		return -1;
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	protected abstract List<Button> loadButtons();

	public void updateTempButtons() {
		synchronized (ButtonLock) {
			buttons.clear();
			buttons.addAll(tempButtons);
		}
	}

	public void addButton(Button b) {
		synchronized (ButtonLock) {
			this.tempButtons.add(b);
		}
	}

	public void removeButton(Button b) {
		synchronized (ButtonLock) {
			this.tempButtons.remove(b);
		}
	}

	public void removeButtons(List<Button> removeList) {
		synchronized (ButtonLock) {
			tempButtons.removeAll(removeList);
		}
	}

	public void addButtons(List<Button> addList) {
		synchronized (ButtonLock) {
			tempButtons.addAll(addList);
		}

	}

	public void setCurrentButtons(List<Button> bs) {
		synchronized (ButtonLock) {
			for (Button b : bs) {
				if (!tempButtons.contains(b)) {
					tempButtons.add(b);
				}
			}
			for (int i = buttons.size() - 1; i >= 0; i--) {
				if (!bs.contains(tempButtons.get(i))) {
					tempButtons.remove(i);
				}
			}
		}
	}

	protected abstract int[] getGUICoordsVonScreenCoords(int screenX, int screenY);

	public OV_Model getModel() {
		return m;
	}

}
