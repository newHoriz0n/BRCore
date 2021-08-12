package configs.easyStrategy.game;

import java.awt.Color;

public class Spieler {

	public static Color getSpielerFarbe(int id) {
		switch (id) {
		case 0:
			return Color.RED;
		default:
			return Color.GRAY;
		}
	}

}
