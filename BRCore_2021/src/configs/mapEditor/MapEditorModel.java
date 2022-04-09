package configs.mapEditor;

public class MapEditorModel {

	private FeldTyp[][] felder;

	private int[] aktFeld;

	private FeldTyp pen;

	public MapEditorModel(int breite, int hoehe) {
		this.felder = new FeldTyp[breite][hoehe];
		this.aktFeld = new int[2];
		this.pen = FeldTyp.WAND;
		initWelt();

	}

	private void initWelt() {
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				if (i == 0 || j == 0 || i == felder.length - 1 || j == felder[0].length - 1) {
					felder[i][j] = FeldTyp.WAND;
				} else {
					felder[i][j] = FeldTyp.FREI;
				}
			}

		}
	}

	public FeldTyp[][] getFelder() {
		return felder;
	}

	public int getBreite() {
		return felder.length;
	}

	public int getHoehe() {
		return felder[0].length;
	}

	public void writeFeldTyp(int x, int y, FeldTyp typ) {
		if (x > 0 && x < felder.length - 1 && y > 0 && y < felder[0].length - 1) {
			felder[x][y] = typ;
		}
	}

	public void setAktFeld(int x, int y) {
		this.aktFeld[0] = x;
		this.aktFeld[1] = y;
	}

	public int[] getAktFeld() {
		return aktFeld;
	}

	public void erzeugeSaveFile() {
		// TODO Auto-generated method stub

	}

	public enum FeldTyp {
		FREI, WAND;
	}

	public void setPen(FeldTyp p) {
		this.pen = p;
	}

	public FeldTyp getPen() {
		return pen;
	}

}
