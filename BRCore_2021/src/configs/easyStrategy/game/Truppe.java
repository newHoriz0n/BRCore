package configs.easyStrategy.game;

import java.awt.Color;

import configs.easyStrategy.gui.GUI_Ctrl_Truppenansicht;
import lib.ctrl.EEventTyp;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.math.Vektor3D;
import lib.model.KreisObjekt;

public class Truppe extends KreisObjekt{

	private String name;
	private int spielerID;
	
	private Vektor3D ziel;
	
	public Truppe(String name, double posX, double posY, int spielerID, OV_Controller oc) {
		super(posX, posY, 10, Spieler.getSpielerFarbe(spielerID), Spieler.getSpielerFarbe(spielerID));
		this.name = name;
		this.spielerID = spielerID;
		
		Truppe me = this;

		setEventAktion(EEventTyp.MAUSKLICK_LINKS, new Aktion() {

			@Override
			public void run() {
				OV_GUI_Controller sc = new GUI_Ctrl_Truppenansicht("Truppendetailansicht", oc.getViewer().getWidth() - 300, 0, 300,
						oc.getViewer().getHeight(), me, oc.getModel());
				sc.setHintergrundFarbe(Color.BLACK);
				oc.addOverLayGC(sc);
			}
		});

		
	}

	@Override
	public void update(long dt) {
		System.out.println(dt);
		// Essen
		// Moral
		// Bewegen
		Vektor3D speed = calcSpeed();
		posX += speed.getX();
		posY += speed.getY();
	}
	
	private Vektor3D calcSpeed() {
		if(ziel != null) {
			
			Vektor3D dif = new Vektor3D(ziel);
			dif.add(new Vektor3D(posX, posY, 0).scale(-1));
			return new Vektor3D(dif.calcXYAngle(), 1);
		}else {
			return new Vektor3D();
		}
	}
	
	public void setZiel(Vektor3D ziel) {
		this.ziel = ziel;
	}
	
	public String getName() {
		return name;
	}

	public int getSpielerID() {
		return spielerID;
	}

	public void setName(String string) {
		this.name = string;
	}
	

}
