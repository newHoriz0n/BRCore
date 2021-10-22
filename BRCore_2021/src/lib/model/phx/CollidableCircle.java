package lib.model.phx;

public interface CollidableCircle {

	public double getCenterX();

	public void setCenterX(double x);

	public double getCenterY();

	public void setCenterY(double y);

	public double getSpeedX();

	public void setSpeedX(double vx);

	public double getSpeedY();

	public void setSpeedY(double vy);

	public double getMass();

	public double getRadius();

	public static CollidableCircleMinimized getCCMinimized(CollidableCircle c) {
		return new CollidableCircleMinimized(c);
	}
	
}
