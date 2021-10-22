package test;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class RandomObjectsGUICtrl extends OV_GUI_Controller {

	public RandomObjectsGUICtrl(OV_Model m) {
		super(0, "Main", 0, 0, 0, 0, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Button> loadButtons() {
		return new ArrayList<>();
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleFreeMouseRelease(int realMouseX, int realMouseY, int button) {	}

	@Override
	public void updateGUICtrl() {	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleFreeMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		// TODO Auto-generated method stub
		
	}
	
	

}
