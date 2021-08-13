package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.Stadt;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.ctrl.gui.elements.ButtonRect;

public class GUI_Ctrl_Stadtansicht extends OV_GUI_Controller {

	private Stadt s;

	public GUI_Ctrl_Stadtansicht(String titel, int posX, int posY, int width, int height, Stadt s) {
		super(1, titel, posX, posY, width, height);

		this.s = s;
	}

	@Override
	protected void drawGUIContent(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawString(s.getName(), 10, 30);
	}

	@Override
	protected List<Button> loadButtons() {
		List<Button> buttons = new ArrayList<>();

		Button addTruppe = new ButtonRect(10, 100, 30, 30);
		addTruppe.setRahmenFarbe(Color.WHITE);
		addTruppe.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				s.truppeBilden();
			}
		});
		buttons.add(addTruppe);

		return buttons;
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

}
