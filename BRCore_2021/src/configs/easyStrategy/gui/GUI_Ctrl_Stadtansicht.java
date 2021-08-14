package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.Stadt;
import configs.easyStrategy.game.Truppe;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class GUI_Ctrl_Stadtansicht extends OV_GUI_Controller {

	private Stadt s;

	private int offYtruppenButtons = 170;
	private List<Button> truppenButtons = new ArrayList<>();

	public GUI_Ctrl_Stadtansicht(String titel, int posX, int posY, int width, int height, Stadt s, OV_Model m) {
		super(1, titel, posX, posY, width, height, m);
		this.s = s;

		updateTruppenButtons();
	}

	@Override
	protected void drawGUIContent(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(s.getName(), 10, 30);

		int offY = offYtruppenButtons;
		g2d.drawString("Truppen:", 10, offY);
		for (Truppe t : s.getStationierteTruppen()) {
			offY += 30;
			g2d.drawString(t.getName(), 10, offY);

		}

	}

	@Override
	protected List<Button> loadButtons() {
		List<Button> buttons = new ArrayList<>();

		// Add Truppen
		Button addTruppe = new ES_BlackRectButton(10, 100, 30, 30);
		addTruppe.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				s.stationiereTruppe(((EasyStrategy) m).requestTruppe("", s.getPosX(), s.getPosY(), s.getSpielerID()));
				updateTruppenButtons();
			}

		});
		buttons.add(addTruppe);

		
		return buttons;
	}

	private void updateTruppenButtons() {
		removeButtons(truppenButtons);
		truppenButtons.clear();

		int offY = offYtruppenButtons - 12;
		for (Truppe t : s.getStationierteTruppen()) {
			
			// Truppe auflösen
			Button truppAufl = new ES_BlackRectButton(100, offY + 30, 20, 20);
			truppAufl.setText("-");
			truppAufl.setAktionLinks(new Aktion() {
				
				@Override
				public void run()  {
					s.truppeAufloesen(t);
					updateTruppenButtons();
				}
			});
			truppenButtons.add(truppAufl);
			
			Button truppEnts = new ES_BlackRectButton(130, offY + 30, 20, 20);
			truppEnts.setText("->");
			truppEnts.setAktionLinks(new Aktion() {
				
				@Override
				public void run()  {
					((EasyStrategy) m).truppeEntsenden(s,t);
					updateTruppenButtons();
				}
			});
			truppenButtons.add(truppEnts);			
			
			offY += 30;
		}
		addButtons(truppenButtons);
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

	@Override
	public void handleFreeMouseRelease(int mouseX, int mouseY, int button) {	}

	@Override
	public void updateGUICtrl() {
		updateTruppenButtons();		
	}

}
