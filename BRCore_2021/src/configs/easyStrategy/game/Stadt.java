package configs.easyStrategy.game;

public class Stadt {

	private String name;
	private int posX, posY, radius;
	private int spielerID;

	public Stadt(String name, int posX, int posY, int spielerID) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.spielerID = spielerID;
		this.radius = 30;
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
