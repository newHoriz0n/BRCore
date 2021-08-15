package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class GUI_Ctrl_Main_Overlay extends OV_GUI_Controller {

	public GUI_Ctrl_Main_Overlay(OV_Model m) {
		super(0, "Main Overlay",0, 0, 0, 0, m);
	}

	@Override
	public void updateGUICtrl() {
	}

	@Override
	public void handleFreeMouseRelease(int realMouseX, int realMouseY, int button) {
	}

	@Override
	protected List<Button> loadButtons() {
		return new ArrayList<Button>();
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	protected void drawGUIStaticOverlay(Graphics2D g2d) {
		// State:
		g2d.setColor(Color.RED);
		g2d.drawString("" +  ((EasyStrategy) m).getState(), 10, 30);
	}	

}
