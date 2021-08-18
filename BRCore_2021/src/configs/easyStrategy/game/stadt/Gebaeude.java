package configs.easyStrategy.game.stadt;

public class Gebaeude {

	public enum Typ {
		BEFESTIGUNG, KASERNE;
	}

	public static boolean checkKosten(Typ typ, Stadt s) {
		return s.getMaterial() > getKosten(typ, s.getGebaeudeLevel().get(typ));
	}

	public static int getKosten(Typ typ, int stufe) {
		if (typ.equals(Typ.BEFESTIGUNG)) {
			return stufe * 10;
		} else if (typ.equals(Typ.KASERNE)) {
			return 10;
		}
		return 0;
	}

	/**
	 * 
	 * @param typ
	 * @param stufe
	 * @return [Arbeitskraft*Sekunden]
	 */
	public static double getBauAufwand(Typ typ, int stufe) {
		if (typ.equals(Typ.BEFESTIGUNG)) {
			return stufe * 1000;
		} else if (typ.equals(Typ.KASERNE)) {
			return 1000;
		}
		return 0;
	}

}
