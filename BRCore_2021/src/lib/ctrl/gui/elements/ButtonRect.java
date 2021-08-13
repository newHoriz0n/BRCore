package lib.ctrl.gui.elements;

import java.awt.Graphics2D;

public class ButtonRect extends Button {

	private int posX, posY, width, height;

	public ButtonRect(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean checkMouseOver(int x, int y) {
		return (x >= posX && x <= posX + width && y >= posY && y <= posY + height);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(fHintergrund);
		g.fillRect(posX, posY, width, height);
		if (mouseOver) {
			g.setColor(fRahmenHover);
			g.drawRect(posX, posY, width, height);
			if (mouseHoldLinks) {
				g.setColor(fRahmenLinks);
				g.drawRect(posX, posY, width, height);
			}
			if (mouseHoldRechts) {
				g.setColor(fRahmenRechts);
				g.drawRect(posX, posY, width, height);
			}
		} else {
			g.setColor(fRahmen);
			g.drawRect(posX, posY, width, height);			
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ButtonRect other = (ButtonRect) obj;
		if (height != other.height)
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	
	

}
