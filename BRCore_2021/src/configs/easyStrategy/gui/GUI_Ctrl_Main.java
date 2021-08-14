package configs.easyStrategy.gui;

import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.EasyStrategy.ES_State;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class GUI_Ctrl_Main extends OV_GUI_Controller {

	public GUI_Ctrl_Main(OV_Model m) {
		super(0, "Main", 0, 0, 0, 0, m);
		// TODO Auto-generated constructor stub
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
	public void handleFreeMouseRelease(int mouseX, int mouseY, int button) {

		if (((EasyStrategy) m).getState().equals(ES_State.TRUPPE_PLATZIEREN)) {
			((EasyStrategy) m).truppePlatzieren(mouseX, mouseY);
		}

	}

	@Override
	public void updateGUICtrl() {	}

}
