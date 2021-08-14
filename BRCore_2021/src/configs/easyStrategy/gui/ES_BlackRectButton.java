package configs.easyStrategy.gui;

import java.awt.Color;

import lib.ctrl.gui.elements.ButtonRect;

public class ES_BlackRectButton extends ButtonRect {

	public ES_BlackRectButton(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		setHintergrundFarbe(Color.BLACK);
		setRahmenFarbe(Color.WHITE);
		setTextFarbe(Color.WHITE);
	}

}
