package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.Truppe;
import configs.easyStrategy.game.stadt.Stadt;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class ES_GUI_Ctrl_Truppenansicht extends OV_GUI_Controller {

	private Truppe t;

	public ES_GUI_Ctrl_Truppenansicht(String titel, int posX, int posY, int width, int height, Truppe t, OV_Model m) {
		super(1, titel, posX, posY, width, height, m);

		this.t = t;
	}

	@Override
	protected void drawGUIBackground(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(t.getName(), 10, 30);
		DecimalFormat df = new DecimalFormat("###.##");
		g2d.drawString("K: " + df.format(t.getKaempfer()), 10, 60);
		g2d.drawString("A: " + df.format(t.getArbeiter()), 10, 90);
		g2d.drawString("M: " + df.format(t.getMaterial()), 10, 120);
	}

	@Override
	protected List<Button> loadButtons() {

		List<Button> buttons = new ArrayList<>();

		// Add Kaempfer
		Button gruendeStadt = new ES_BlackRectButton(10, 500, 250, 50);
		gruendeStadt.setText("Stadt gr?nden");
		gruendeStadt.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				if (t.getMaterial() >= 10) {
					Stadt s = ((EasyStrategy) m).requestStadt("S", t.getPosX(), t.getPosY(), t.getSpielerID());
					if (s != null) {
						t.materialVerwenden(10);
						((EasyStrategy) m).stationiereTruppeInStadt(s, t);
					}
				}

			}
		});
		buttons.add(gruendeStadt);

		return buttons;
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
