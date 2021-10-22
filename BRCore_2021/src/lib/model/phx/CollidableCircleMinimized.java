package lib.model.phx;

public class CollidableCircleMinimized implements CollidableCircle {

	private CollidableCircle c;

	public CollidableCircleMinimized(CollidableCircle c) {
		this.c = c;
	}

	@Override
	public double getCenterX() {
		return c.getCenterX();
	}

	@Override
	public void setCenterX(double x) {
		c.setCenterX(x);
	}

	@Override
	public double getCenterY() {
		return c.getCenterY();
	}

	@Override
	public void setCenterY(double y) {
		c.setCenterY(y);
	}

	@Override
	public double getSpeedX() {
		return c.getSpeedX();
	}

	@Override
	public void setSpeedX(double vx) {
		c.setSpeedX(vx);
	}

	@Override
	public double getSpeedY() {
		return c.getSpeedY();
	}

	@Override
	public void setSpeedY(double vy) {
		c.setSpeedY(vy);
	}

	@Override
	public double getMass() {
		return c.getMass();
	}

	@Override
	public double getRadius() {
		return 0;
	}

}
