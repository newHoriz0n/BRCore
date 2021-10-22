package lib.model.phx;

public class FixCollisionCircle implements CollidableCircle {

	private double posX;
	private double posY;
	private double radius;

	
	
	public FixCollisionCircle(double posX, double posY, double radius) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
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
		return 0;
	}

	@Override
	public void setSpeedX(double vx) {
		return;
	}

	@Override
	public double getSpeedY() {
		return 0;
	}

	@Override
	public void setSpeedY(double vy) {
		return;
	}

	@Override
	public double getMass() {
		return Double.MAX_VALUE;
	}

	@Override
	public double getRadius() {
		return radius;
	}

}
