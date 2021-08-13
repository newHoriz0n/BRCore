package lib.ctrl.gui;

import java.util.ArrayList;
import java.util.List;

import lib.ctrl.gui.elements.Button;

public class OV_Main_GUI_Controller extends OV_GUI_Controller{

	public OV_Main_GUI_Controller(String titel, int posX, int posY, int width, int height) {
		super(0, titel, posX, posY, width, height);
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

}
