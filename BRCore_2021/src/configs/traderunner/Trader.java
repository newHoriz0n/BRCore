package configs.traderunner;

public class Trader {

	protected String name;
	protected int[] position;

	public Trader(int x, int y) {
		this.position = new int[] { x, y };
	}
	
	public void update() {}
	
	public void setPosition(int x, int y) {
		this.position[0] = x;
		this.position[1] = y;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public String getName() {
		return name;
	}
}
