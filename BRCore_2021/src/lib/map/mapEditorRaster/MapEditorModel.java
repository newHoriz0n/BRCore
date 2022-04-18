package lib.map.mapEditorRaster;

import java.util.ArrayList;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;
import lib.map.FeldTyp;
import lib.map.Map;

public class MapEditorModel implements Sendbares {

	private Map map;

	private int[] aktFeld;
	private int pen;

	/**
	 * 
	 * @param breite
	 * @param hoehe
	 * @param feldTypen:
	 *            Welt wird mit Feldtyp[0] initialisiert.
	 */
	public MapEditorModel() {
		this.aktFeld = new int[2];
		this.pen = 0;
	}

	public void setMap(Map m) {
		this.map = m;
	}

	public void setFeldTypen(List<FeldTyp> feldTypen) {
		this.map.setFeldTypen(feldTypen);
	}

	public void initWelt(int breite, int hoehe) {
		this.map.initWelt(breite, hoehe);
	}

	public Integer[][] getFelder() {
		return map.getFelder();
	}

	public int getBreite() {
		return map.getFelder().length;
	}

	public int getHoehe() {
		return map.getFelder()[0].length;
	}

	public void writeFeldTyp(int x, int y, int typ) {
		if (x >= 0 && x < getBreite() && y >= 0 && y < getHoehe()) {
			map.writeFeldTyp(x, y, typ);
		}
	}

	public void setAktFeld(int x, int y) {
		this.aktFeld[0] = x;
		this.aktFeld[1] = y;
	}

	public int[] getAktFeld() {
		return aktFeld;
	}

	public void setPen(int p) {
		this.pen = p;
	}

	public int getPen() {
		return pen;
	}

	public List<FeldTyp> getFeldTypen() {
		return map.getFeldTypen();
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<>();
		ses.add(new SendEigenschaft("Map", EObjektTyp.OBJECT, map));
		return ses;
	}

	@Override
	public String getBezeichner() {
		return "MapEditorModel";
	}

	public Sendbares getMap() {
		return map;
	}

	public void setMapName(String name) {
		this.map.setMapName(name);
	}

	public String getMapName() {
	return this.map.getMapName();
	}

}
