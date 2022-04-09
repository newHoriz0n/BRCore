package lib.ctrl.gui.elements;

import java.awt.Graphics2D;

public abstract class Element {

	public abstract void handleMouseMove(int x, int y);

	public abstract boolean handleMousePress(int x, int y, int button);

	public abstract boolean handleMouseRelease(int x, int y, int button);

	public abstract void draw(Graphics2D g);

}
