package lib.ctrl.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class OV_GUI_Controller implements Comparable<OV_GUI_Controller> {

	// Verwaltet Buttons

	private String titel;

	private boolean active;

	private int zIndex;
	private int posX, posY, width, height;
	private Color farbeHintergrund;

	private Object ButtonLock = new Object();
	private List<Button> buttons = new ArrayList<>();

	public OV_GUI_Controller(String titel, int posX, int posY, int width, int height) {
		this.titel = titel;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
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

	public boolean handleMouseRelease(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		if (isMouseOver(aktScreenMouseX, aktScreenMouseY)) {
			for (Button b : buttons) {
				b.handleMouseRelease(aktRealMausPosX, aktRealMausPosY, button);
			}
			return true;
		}
		return false;
	}

	public void drawGUIController(Graphics2D g) {
		synchronized (ButtonLock) {
			if (farbeHintergrund != null) {
				g.setColor(farbeHintergrund);
				g.fillRect(posX, posY, width, height);
			}
			for (Button b : buttons) {
				b.draw(g);
			}
		}
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

}
