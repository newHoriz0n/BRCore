package lib.math;

public class Vektor3D {

	private double x, y, z;

	public Vektor3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vektor3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * X = cos(dir) Y = sin(dir) Z = 0
	 * 
	 * @param direction
	 */
	public Vektor3D(double direction, double length) {
		this.x = Math.cos(direction) * length;
		this.y = Math.sin(direction) * length;
		this.z = 0;
	}

	public Vektor3D(Vektor3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void set(double direction, double length) {
		this.x = Math.cos(direction) * length;
		this.y = Math.sin(direction) * length;
		this.z = 0;
	}

	public void set(Vektor3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public double calcScaleProduct(Vektor3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public Vektor3D calcCrossProduct(Vektor3D v) {
		return new Vektor3D(y * v.z, z * v.x, x * v.y);
	}

	public Vektor3D calcMirror() {
		return new Vektor3D(-x, -y, -z);
	}

	public Vektor3D update(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vektor3D update(Vektor3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}

	public Vektor3D scale(double d) {
		this.x *= d;
		this.y *= d;
		this.z *= d;
		return this;
	}

	public Vektor3D mult(Vektor3D v) {
		this.x *= v.x;
		this.y *= v.y;
		this.z *= v.z;
		return this;
	}

	public Vektor3D add(Vektor3D v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}

	/**
	 * Berechnet Orthogonale auf X Y Projektion des Vektors
	 * 
	 * @return
	 */
	public Vektor3D calcOrthogonaleInXYPlaneImUhrzeigersinn() {
		return new Vektor3D(y, -x, 0);
	}

	public Vektor3D calcOrthogonaleInXYPlaneGegenUhrzeigersinn() {
		return new Vektor3D(-y, x, 0);
	}

	public double calcAbsValue() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}

	/**
	 * 0 := x=1, y=0, CCW
	 * 
	 * @return
	 */
	public double calcXYAngle() {
		double ret = Math.atan2(y, x);
		return ret;
	}

	public String toString() {
		return "[" + x + "," + y + "," + z + "]";
	}

	public Vektor3D norm() {
		double l = calcAbsValue();
		x = x / l;
		y = y / l;
		z = z / l;
		return this;
	}

	public Vektor3D rotateXY(double d) {
		double temp_y = x;
		x = -y;
		y = temp_y;
		return this;
	}

}
