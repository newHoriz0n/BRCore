package configs.easyStrategy.game;

public class Truppe {

	private String name;
	private int posX, posY, radius;
	private int spielerID;

	public Truppe(String name, int posX, int posY, int spielerID) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.spielerID = spielerID;
		this.radius = 10;
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
