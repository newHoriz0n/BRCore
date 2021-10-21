package configs.randomBall.game;

public class Tor {

	private double breite;
	private double xMitte;
	private double yMitte;
	private double orientierung;

	private double netzrasterabstand;
	private double tiefe;
	
	/**
	 * 
	 * @param posX
	 * @param posYmitte
	 */
	public Tor(double posXmitte, double posYmitte, double breite, double orientierung) {
		this.breite = breite;
		this.xMitte = posXmitte;
		this.yMitte = posYmitte;
		this.orientierung = orientierung;
	}

	public double getBreite() {
		return breite;
	}

	public double getOrientierung() {
		return orientierung;
	}

	public double getxMitte() {
		return xMitte;
	}

	public double getyMitte() {
		return yMitte;
	}

}
