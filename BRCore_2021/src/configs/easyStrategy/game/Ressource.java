package configs.easyStrategy.game;

import java.awt.Color;

import configs.easyStrategy.game.EasyStrategy.ES_State;
import configs.easyStrategy.gui.ES_GUI_Ctrl_Ressourcenansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.model.KreisObjekt;

public class Ressource extends KreisObjekt {

	private RessourcenTyp typ;
	private double anzahl;

	public enum RessourcenTyp {
		WASSER, WALD
	};

	public Ressource(RessourcenTyp typ, int posX, int posY, int anzahl, OV_Controller oc) {
		super(posX, posY, 0, getColorVonTyp(typ), getColorVonTyp(typ));
		this.typ = typ;
		this.anzahl = anzahl;
		updateRadius();

		Ressource me = this;

		// Öffne Ressourcenansicht
		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new ES_GUI_Ctrl_Ressourcenansicht("Ressourcendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
				((EasyStrategy) oc.getModel()).setState(ES_State.STANDARD);
			}
		});

		// Setze Abbau
		setEventAktion(EEventTyp.MAUSKLICK_RECHTS, new Aktion() {

			@Override
			public void run() {
				if (((EasyStrategy) oc.getModel()).getState() == ES_State.STADT_ABBAUEN) {
					((EasyStrategy) oc.getModel()).toggleRessourcenAbbau(me, ((EasyStrategy) oc.getModel()).getFocusStadt());
					((EasyStrategy) oc.getModel()).setState(ES_State.STANDARD);
				}
			}
		});

	}

	@Override
	protected void update(long dt) {
		if (typ == RessourcenTyp.WALD) {
			anzahl = anzahl * Math.pow(1.00001, (double) dt / 1000.0);
			updateRadius();
		}
	}

	public void abbauen(double anzahl) {
		this.anzahl -= anzahl;
		updateRadius();
	}

	public RessourcenTyp getTyp() {
		return typ;
	}

	public void updateRadius() {
		radius = (int) (Math.sqrt(anzahl) + 5);
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

	public double getAnzahl() {
		return anzahl;
	}

}
