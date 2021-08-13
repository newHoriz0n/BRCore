package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.Truppe;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;

public class GUI_Ctrl_Truppenansicht extends OV_GUI_Controller {

	private Truppe t;

	public GUI_Ctrl_Truppenansicht(String titel, int posX, int posY, int width, int height, Truppe t) {
		super(1, titel, posX, posY, width, height);

		this.t = t;
	}

	@Override
	protected void drawGUIContent(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(t.getName(), 10, 30);
	}

	@Override
	protected List<Button> loadButtons() {
		return new ArrayList<Button>();
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

}
