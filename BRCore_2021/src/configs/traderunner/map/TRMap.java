package configs.traderunner.map;

import java.util.ArrayList;
import java.util.List;

import lib.map.FeldTyp;
import lib.map.Map;
import lib.map.MapObjekt;

public class TRMap {

	private int[][] felder;

	private List<FeldTyp> feldTypen = new ArrayList<>();
	
	private int[] spielerStart;
	private List<int[]> shopPoses;
	private List<int[]> botStarts;

	public TRMap(Map m) {
		
		this.felder = new int[m.getBreite()][m.getHoehe()];
		this.feldTypen = m.getFeldTypen();
		
		this.shopPoses = new ArrayList<>();
		this.botStarts = new ArrayList<>();
		// System.out.println(m.getBreite() + "x" + m.getHoehe());

		// String out = "";

		for (int y = 0; y < m.getHoehe(); y++) {
			for (int x = 0; x < m.getBreite(); x++) {
				// out += "" + m.getFelder()[x][y];
				int typ = m.getFelder()[x][y];

				if (typ == 0 || typ == 1) {
					felder[x][y] = typ;
				} else {
					if (typ == 2) {
						spielerStart = new int[] { x, y };
					} else if (typ == 3) {
						this.shopPoses.add(new int[] { x, y });
					} else if (typ == 4) {
						this.botStarts.add(new int[] { x, y });
					}
				}
			}
			// out += "\n";
		}

		// System.out.println(out);
		
		// SHOPS		
		for (MapObjekt	 o : m.getMapObjekte()) {
			if(o.getTyp().equals("Shop")) {
				System.out.println(o.getEigenschaften().get("Name"));
			}
		}

	}
	
	public List<int[]> getBotStarts() {
		return botStarts;
	}

	public int[][] getFelder() {
		return felder;
	}

	public List<int[]> getShopPositionen() {
		return shopPoses;
	}

	public int[] getSpielerStart() {
		return spielerStart;
	}
	
	public int getBreite() {
		return felder.length;
	}
	
	public int getHoehe() {
		return felder[0].length;
	}
	
	public List<FeldTyp> getFeldTypen() {
		return feldTypen;
	}

}
