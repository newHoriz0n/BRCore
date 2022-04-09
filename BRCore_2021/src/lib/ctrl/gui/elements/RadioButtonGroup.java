package lib.ctrl.gui.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class RadioButtonGroup extends Element {

	private List<Button> buttons;
	private int aktivID;

	public RadioButtonGroup() {
		this.buttons = new ArrayList<>();
		this.aktivID = -1;
	}

	public void addButton(Button b) {
		this.buttons.add(b);
		if (buttons.size() == 1) {
			aktivID = 0;
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g);
			if (i == aktivID) {
				g.setColor(Color.GREEN);
				g.drawRect(buttons.get(i).getPosX() - 1, buttons.get(i).getPosY() - 1, buttons.get(i).getBoundingBoxWidth() + 2,
						buttons.get(i).getBoundingBoxHeight() + 2);
			}
		}
	}

	public void handleMouseMove(int x, int y) {
		for (Button b : buttons) {
			b.handleMouseMove(x, y);
		}
	}

	public boolean handleMousePress(int x, int y, int mouseButton) {
		boolean press = false;
		for (Button button : buttons) {
			if (button.handleMousePress(x, y, mouseButton)) {
				press = true;
			}
		}
		return press;
	}

	public boolean handleMouseRelease(int x, int y, int mouseButton) {
		boolean press = false;
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).handleMouseRelease(x, y, mouseButton)) {
				press = true;
				// Setze Aktivierung
				aktivID = i;
			}
		}
		return press;
	}

	public Button getActiveButton() {
		return buttons.get(aktivID);
	}

}
