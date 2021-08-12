package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import configs.easyStrategy.game.Stadt;
import lib.ctrl.gui.OV_GUI_Controller;

public class GUI_Ctrl_Stadtansicht extends OV_GUI_Controller {

	private Stadt s;

	public GUI_Ctrl_Stadtansicht(String titel, int posX, int posY, int width, int height, Stadt s) {
		super(titel, posX, posY, width, height);

		this.s = s;
	}

	@Override
	protected void drawGUIContent(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(s.getName(), 10, 30);
	}

	@Override
	protected void loadButtons() {
		// TODO Auto-generated method stub

	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

}
