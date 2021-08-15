package lib.model.phx;

import lib.model.KreisObjekt;

public class Collision {

	private KreisObjekt k1;
	private KreisObjekt k2;

	public Collision(KreisObjekt k1, KreisObjekt k2) {
		this.k1 = k1;
		this.k2 = k2;
	}

	public KreisObjekt getK1() {
		return k1;
	}

	public KreisObjekt getK2() {
		return k2;
	}

}
