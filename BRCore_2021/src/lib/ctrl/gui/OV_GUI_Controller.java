package lib.ctrl.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import lib.ctrl.gui.elements.Button;

public abstract class OV_GUI_Controller implements Comparable<OV_GUI_Controller> {

	// Verwaltet Buttons

	private int gruppe;
	
	private String titel;

	private boolean visible;
	private boolean enabled;
	private boolean active;

	private int zIndex;
	protected int posX, posY, width, height;
	private Color farbeHintergrund;

	private Object ButtonLock = new Object();
	private List<Button> buttons;

	public OV_GUI_Controller(int gruppe, String titel, int posX, int posY, int width, int height) {
		this.gruppe = gruppe;
		this.titel = titel;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.buttons = loadButtons();
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height);
	}

	public void setHintergrundFarbe(Color f) {
		this.farbeHintergrund = f;
	}

	public void setCurrentButtons(List<Button> bs) {
		synchronized (ButtonLock) {
			for (Button b : bs) {
				if (!buttons.contains(b)) {
					buttons.add(b);
				}
			}
			for (int i = buttons.size() - 1; i >= 0; i--) {
				if (!bs.contains(buttons.get(i))) {
					buttons.remove(i);
				}
			}
		}
	}

	public boolean handleMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
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
			for (Button b : buttons) {
				b.handleMouseMove(internMausCoords[0], internMausCoords[1]);
			}
			return true;
		}
		return false;
	}

	public boolean handleMousePress(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			for (Button b : buttons) {
				b.handleMousePress(aktRealMausPosX, aktRealMausPosY, button);
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
			for (Button b : buttons) {
				b.handleMousePress(internMausCoords[0], internMausCoords[1], button);
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
			for (Button b : buttons) {
				b.handleMouseRelease(aktRealMausPosX, aktRealMausPosY, button);
			}
			return true;
		}
		return false;
	}

	public boolean handleMouseReleaseIntern(int aktScreenMouseX, int aktScreenMouseY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			int[] internMausCoords = getGUICoordsVonScreenCoords(aktScreenMouseX, aktScreenMouseY);
			for (Button b : buttons) {
				b.handleMouseRelease(internMausCoords[0], internMausCoords[1], button);
			}
			return true;
		}
		return false;
	}

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
			drawGUIContent(g);
			for (Button b : buttons) {
				b.draw(g);
			}
			if (transform) {
				g.translate(-posX, -posY);
			}

		}
	}

	protected void drawGUIContent(Graphics2D g2d) {
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

	protected abstract int[] getGUICoordsVonScreenCoords(int screenX, int screenY);
	

}
