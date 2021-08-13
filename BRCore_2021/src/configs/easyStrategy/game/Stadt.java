package configs.easyStrategy.game;

import java.util.ArrayList;
import java.util.List;

public class Stadt {

	private String name;
	private int posX, posY, radius;
	private int spielerID;
	
	private int arbeiter;
	private int kaempfer;
	private int material;
	
	private List<Truppe> stationierteTruppen;

	public Stadt(String name, int posX, int posY, int spielerID) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.spielerID = spielerID;
		this.radius = 30;
		this.stationierteTruppen = new ArrayList<Truppe>();
	}
	
	public void setWerte(int arbeiter, int kaempfer, int material) {
		this.arbeiter = arbeiter;
		this.kaempfer = kaempfer;
		this.material = material;
	}
	
	public void ausbilden(int anzahl) {
		if(anzahl > material || anzahl > arbeiter) {
			throw new IllegalArgumentException();
		}
		arbeiter -= anzahl;
		material -= anzahl;
		kaempfer += anzahl;
	}
	
	public void truppeBilden() {
		this.stationierteTruppen.add(new Truppe("Truppe " + stationierteTruppen.size(), posX, posY, spielerID));
	}
	
	public List<Truppe> getStationierteTruppen() {
		return stationierteTruppen;
	}

	public String getName() {
		return name;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getRadius() {
		return radius;
	}

	public int getSpielerID() {
		return spielerID;
	}

}
