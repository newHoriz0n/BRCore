package configs.randomBall;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.randomBall.game.RandomBall;
import configs.randomBall.game.Spielfeld;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;
import lib.model.phx.Hindernis;

public class RB_GUI_Ctrl_Main extends OV_GUI_Controller {

	private Color fRasen = new Color(0, 150, 50);

	public RB_GUI_Ctrl_Main(OV_Model m) {
		super(0, "Main", 0, 0, (int) ((RandomBall) m).getFeld().getLaenge(), (int) ((RandomBall) m).getFeld().getBreite(), m);
	}

	@Override
	public void updateGUICtrl() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleFreeMouseRelease(int realMouseX, int realMouseY, int button) {
		((RandomBall) m).schiesse(realMouseX, realMouseY);
	}

	@Override
	public void handleFreeMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {	}

	
	
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
		Spielfeld f = ((RandomBall) m).getFeld();
		g2d.setColor(fRasen);
		g2d.fillRect(-10, -10, (int) (f.getLaenge() + 20), (int) (f.getBreite() + 20));
		// Linien
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRect(0, 0, (int) (f.getLaenge()), (int) (f.getBreite()));
		g2d.setStroke(new BasicStroke());

		// Hindernisse
		for (Hindernis h : ((RandomBall) m).getHindernisse()) {
			h.draw(g2d);
//			h.getForm().getExpandedPolygon(15).drawPolygon2D(g2d);
			
		}
		
		
	}

}
