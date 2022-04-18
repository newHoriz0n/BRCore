package configs.traderunner.bot;

import java.util.Random;

import configs.traderunner.Trader;

public class TraderBot extends Trader {

	private int dir; // 0: links, 1: hoch, 2: rechts, 3: runter
	private int[] desiredNextPosition;

	private Random r = new Random();

	public TraderBot(int x, int y) {
		super(x, y);

		this.desiredNextPosition = new int[2];
		this.dir = r.nextInt(4);
	}

	@Override
	public void update() {
		calcNextMove();
	}

	public void calcNextMove() {
		int dx = 0;
		int dy = 0;
		switch (dir) {
		case 0:
			dx = -1;
			dy = 0;
			break;
		case 1:
			dx = 0;
			dy = -1;
			break;
		case 2:
			dx = +1;
			dy = 0;
			break;
		case 3:
			dx = 0;
			dy = +1;
			break;
		default:
			break;
		}

		desiredNextPosition[0] = position[0] + dx;
		desiredNextPosition[1] = position[1] + dy;

	}

	public int[] getDesiredNextPosition() {
		return desiredNextPosition;
	}

	public void hitWall() {
		this.dir = r.nextInt(4);
	}
}
