package configs.easyStrategy.game;

import java.awt.Color;

public class Ressource {

	private RessourcenTyp typ;
	private int posX, posY;
	private int anzahl;

	public enum RessourcenTyp {
		WASSER, WALD
	};

	public Ressource(RessourcenTyp typ, int posX, int posY, int anzahl) {

		this.typ = typ;
		this.posX = posX;
		this.posY = posY;
		this.anzahl = anzahl;
	}

	public void abbauen(int anzahl) {
		this.anzahl -= anzahl;
	}

	public RessourcenTyp getTyp() {
		return typ;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getRadius() {
		return (int) (Math.sqrt(anzahl)) + 5;
	}

	public static Color getColorVonTyp(RessourcenTyp typ) {
		switch (typ) {
		case WASSER:
			return Color.BLUE;
		case WALD:
			return new Color(0, 155, 50);
		default:
			return Color.BLACK;
		}
	}

}
