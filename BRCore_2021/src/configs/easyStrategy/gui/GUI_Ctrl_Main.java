package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.RessourcenAbbau;
import configs.easyStrategy.game.EasyStrategy.ES_State;
import configs.easyStrategy.game.Truppe;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class GUI_Ctrl_Main extends OV_GUI_Controller {

	public GUI_Ctrl_Main(OV_Model m) {
		super(0, "Main", 0, 0, 0, 0, m);
	}

	@Override
	protected List<Button> loadButtons() {
		return new ArrayList<Button>();
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return null;
	}

	@Override
	public void handleFreeMouseRelease(int mouseX, int mouseY, int button) {
		if (button == 1) {
			if (((EasyStrategy) m).getState().equals(ES_State.TRUPPE_AUSSENDEN)) {
				((EasyStrategy) m).truppePlatzieren(mouseX, mouseY);
			}
		} else if (button == 3) {
			if (((EasyStrategy) m).getState().equals(ES_State.TRUPPE_STEUERN)) {
				((EasyStrategy) m).truppeBewegen(mouseX, mouseY);

			}
		}

	}

	@Override
	protected void drawGUIBackground(Graphics2D g2d) {
		super.drawGUIBackground(g2d);

		// Markierte Truppe
		Truppe t = ((EasyStrategy) m).getFocusTruppe();
		if (t != null) {
			g2d.setColor(Color.GREEN);
			g2d.drawOval((int) (t.getPosX() - t.getRadius() - 3), (int) (t.getPosY() - t.getRadius() - 3), (int) (2 * (t.getRadius() + 3)),
					(int) (2 * (t.getRadius() + 3)));
		}

		// Markierte Stadt
		if (((EasyStrategy) m).getFocusStadt() != null) {
			// Ressourcen
			for (RessourcenAbbau a : ((EasyStrategy) m).getFocusStadt().getAbbau()) {
				g2d.setColor(Color.GREEN);
				g2d.drawOval((int) (a.getRessource().getPosX() - a.getRessource().getRadius() - 3),
						(int) (a.getRessource().getPosY() - a.getRessource().getRadius() - 3), (int) ((a.getRessource().getRadius() + 3) * 2),
						(int) ((a.getRessource().getRadius() + 3) * 2));
			}
		}

	}

	@Override
	public void updateGUICtrl() {
	}

}
