package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.EasyStrategy.ES_State;
import configs.easyStrategy.game.Truppe;
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
		if (button == 1) {
			if (((EasyStrategy) m).getState().equals(ES_State.TRUPPE_PLATZIEREN)) {
				((EasyStrategy) m).truppePlatzieren(mouseX, mouseY);
			}
		} else if (button == 3) {
			if (((EasyStrategy) m).getState().equals(ES_State.TRUPPE_BEWEGEN)) {
				((EasyStrategy) m).truppeBewegen(mouseX, mouseY);

			}
		}

	}

	@Override
	protected void drawGUIBackground(Graphics2D g2d) {
		super.drawGUIBackground(g2d);
		
		Truppe t = ((EasyStrategy) m).getFocusTruppe();
		
		if(t != null) {
			g2d.setColor(Color.GREEN);
			g2d.drawOval((int) (t.getPosX() - t.getRadius() - 3), (int) (t.getPosY() - t.getRadius() - 3), (int) (2 * (t.getRadius() + 3)), (int) (2 * (t.getRadius() + 3)));
		}
		
	}
	
	@Override
	public void updateGUICtrl() {
	}

}
