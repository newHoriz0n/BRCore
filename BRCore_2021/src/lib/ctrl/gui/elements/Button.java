package lib.ctrl.gui.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import lib.ctrl.gui.Aktion;

public abstract class Button {

	private static long hoverTimeOut = 10; // [ms] Zeit zwischen letzter Bewegung und MouseOverCheck
	private long lastMove;

	protected boolean mouseOver;
	protected boolean mouseHoldLinks;
	protected boolean mouseHoldRechts;

	protected Aktion aktionLinks;
	protected Aktion aktionRechts;

	protected Color fHintergrund = new Color(0, 0, 0, 0);
	protected Color fRahmen = new Color(0, 0, 0, 0);
	protected Color fRahmenHover = Color.ORANGE;
	protected Color fRahmenLinks = Color.RED;
	protected Color fRahmenRechts = Color.BLUE;

	public void setAktionLinks(Aktion a) {
		this.aktionLinks = a;
	}

	public void setAktionRechts(Aktion a) {
		this.aktionRechts = a;
	}

	public void handleMouseMove(int x, int y) {
		if (System.currentTimeMillis() - lastMove > hoverTimeOut) {
			lastMove = System.currentTimeMillis();
			if (checkMouseOver(x, y)) {
				mouseOver = true;
				return;
			}
			mouseOver = false;
			mouseHoldLinks = false;
			mouseHoldRechts = false;
		}
	}

	public void handleMousePress(int x, int y, int button) {
		if (checkMouseOver(x, y)) {
			if (button == 1) {
				mouseHoldLinks = true;
			}
			if (button == 3) {
				mouseHoldRechts = true;
			}
			return;
		}
		mouseHoldLinks = false;
		mouseHoldRechts = false;
	}

	public void handleMouseRelease(int x, int y, int mouseButton) {
		if (checkMouseOver(x, y)) {
			if (mouseButton == 1) {
				if (aktionLinks != null) {
					aktionLinks.run();
				}
				mouseHoldLinks = false;
			}
			if (mouseButton == 3) {
				if (aktionRechts != null) {
					aktionRechts.run();
				}
				mouseHoldRechts = false;
			}
		}
	}

	public void setHintergrundFarbe(Color fHintergrund) {
		this.fHintergrund = fHintergrund;
	}

	public void setRahmenFarbe(Color fRahmen) {
		this.fRahmen = fRahmen;
	}

	public void setRahmenHoverFarbe(Color fRahmenHover) {
		this.fRahmenHover = fRahmenHover;
	}

	public void setRahmenLinksDruckFarbe(Color fRahmenLinks) {
		this.fRahmenLinks = fRahmenLinks;
	}

	public void setRahmenRechtsDruckFarbe(Color fRahmenRechts) {
		this.fRahmenRechts = fRahmenRechts;
	}

	public abstract boolean checkMouseOver(int x, int y);

	public abstract void draw(Graphics2D g);

}
