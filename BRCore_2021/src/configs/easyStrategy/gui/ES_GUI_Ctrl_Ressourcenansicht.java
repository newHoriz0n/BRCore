package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.Ressource;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class ES_GUI_Ctrl_Ressourcenansicht extends OV_GUI_Controller {

	private Ressource r;

	public ES_GUI_Ctrl_Ressourcenansicht(String titel, int posX, int posY, int width, int height, Ressource r, OV_Model m) {
		super(1, titel, posX, posY, width, height, m);

		this.r = r;
	}

	@Override
	protected void drawGUIBackground(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(r.getTyp().toString(), 10, 30);
		DecimalFormat df = new DecimalFormat("###.##");
		g2d.drawString("" + df.format(r.getAnzahl()), 10, 60);
	}

	@Override
	protected List<Button> loadButtons() {
		return new ArrayList<>();
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

	@Override
	public void handleFreeMouseRelease(int mouseX, int mouseY, int button) {
	}

	@Override
	public void updateGUICtrl() {
	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleFreeMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		// TODO Auto-generated method stub
		
	}

}
