package configs.billard.table;

import java.awt.Color;

import lib.math.Vektor3D;
import lib.model.KreisObjekt;
import lib.model.phx.CollidableCircle;

public class Kugel extends KreisObjekt implements CollidableCircle {

	private Vektor3D speed;

	private double masse = 170; // [g]
	private static double radius = 14.3; // [2 * mm ]
	
	
	public Kugel(double x, double y, int zahl) {
		super(x, y, radius, Color.RED, Color.BLACK);
		this.speed = new Vektor3D();
	}

	public void schiessen(Vektor3D schuss, double startDistance) {
		this.speed.set(schuss.calcXYAngle(), startDistance);
		posX += this.speed.getX();
		posY += this.speed.getY();
		this.speed.set(schuss);
	}

	@Override
	protected void update(long dt) {

		posX += speed.getX() * (double) dt / 1000.0;
		posY += speed.getY() * (double) dt / 1000.0;
	}


	@Override
	public double getCenterX() {
		return posX;
	}

	@Override
	public void setCenterX(double x) {
		this.posX = x;
	}

	@Override
	public double getCenterY() {
		return posY;
	}

	@Override
	public void setCenterY(double y) {
		this.posY = y;
	}

	@Override
	public double getSpeedX() {
		return speed.getX();
	}

	@Override
	public void setSpeedX(double vx) {
		this.speed.setX(vx);
	}

	@Override
	public double getSpeedY() {
		return speed.getY();
	}

	@Override
	public void setSpeedY(double vy) {
		this.speed.setY(vy);
	}

	@Override
	public double getMass() {
		return masse;
	}


}
