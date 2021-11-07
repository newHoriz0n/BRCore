package configs.billard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.billard.table.Billard;
import configs.billard.table.Tisch;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;
import lib.model.phx.Hindernis;

public class B_GUI_Ctrl_Main extends OV_GUI_Controller {

	private Color fRasen = new Color(0, 150, 50);

	public B_GUI_Ctrl_Main(OV_Model m) {
		super(0, "Main", 0, 0, (int) ((Billard) m).getFeld().getLaenge(), (int) ((Billard) m).getFeld().getBreite(), m);
	}

	@Override
	public void updateGUICtrl() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleFreeMouseRelease(int realMouseX, int realMouseY, int button) {
		((Billard) m).schiesse(realMouseX, realMouseY);
	}

	@Override
	public void handleFreeMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
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
	public void drawBackground(Graphics2D g2d) {
		Tisch f = ((Billard) m).getFeld();
		g2d.setColor(fRasen);
		g2d.fillRect((int) (-f.getBandenstaerke() / 2), (int) (-f.getBandenstaerke() / 2), (int) (f.getLaenge() + (f.getBandenstaerke())),
				(int) (f.getBreite() + (f.getBandenstaerke())));

		// Hindernisse
		for (Hindernis h : ((Billard) m).getHindernisse()) {
			h.draw(g2d);
			// h.getForm().getExpandedPolygon(15).drawPolygon2D(g2d);

		}

	}

}
