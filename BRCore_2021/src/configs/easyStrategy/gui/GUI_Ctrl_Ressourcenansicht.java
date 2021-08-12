package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import configs.easyStrategy.game.Ressource;
import lib.ctrl.gui.OV_GUI_Controller;

public class GUI_Ctrl_Ressourcenansicht extends OV_GUI_Controller {

	private Ressource r;

	public GUI_Ctrl_Ressourcenansicht(String titel, int posX, int posY, int width, int height, Ressource r) {
		super(titel, posX, posY, width, height);

		this.r = r;
	}

	@Override
	protected void drawGUIContent(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(r.getTyp().toString(), 10, 30);
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
