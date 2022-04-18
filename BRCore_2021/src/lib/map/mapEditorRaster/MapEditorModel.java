package lib.map.mapEditorRaster;

import java.util.ArrayList;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.PBFileReadWriter;
import lib.io.RawSendbares;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;

public class MapEditorModel implements Sendbares {

	private Integer[][] felder;

	private int[] aktFeld;

	private List<FeldTyp> feldTypen;
	private int pen;

	/**
	 * 
	 * @param breite
	 * @param hoehe
	 * @param feldTypen:
	 *            Welt wird mit Feldtyp[0] initialisiert.
	 */
	public MapEditorModel(List<FeldTyp> feldTypen) {
		this.aktFeld = new int[2];
		this.feldTypen = feldTypen;
		this.pen = 0;
	}

	public void initWelt(int breite, int hoehe) {
		this.felder = new Integer[breite][hoehe];
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				felder[i][j] = 0;
			}

		}
	}

	public Integer[][] getFelder() {
		return felder;
	}

	public int getBreite() {
		return felder.length;
	}

	public int getHoehe() {
		return felder[0].length;
	}

	public void writeFeldTyp(int x, int y, int typ) {
		if (x >= 0 && x < felder.length && y >= 0 && y < felder[0].length) {
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

	public void setPen(int p) {
		this.pen = p;
	}

	public int getPen() {
		return pen;
	}

	public List<FeldTyp> getFeldTypen() {
		return feldTypen;
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<>();

		SendEigenschaft seTypen = new SendEigenschaft("FeldTypen", EObjektTyp.LIST, feldTypen);
		ses.add(seTypen);

		SendEigenschaft seFelder = new SendEigenschaft("Felder", EObjektTyp.MATRIX, felder);
		ses.add(seFelder);


		return ses;
	}

	@Override
	public String getName() {
		return "MapEditorModel";
	}

	public static MapEditorModel createFromSendbarem(Sendbares s) {
		
		List<SendEigenschaft> props = s.getProperties();
		
		
		List<FeldTyp> feldTypen = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object> typen = (List<Object>) props.get(0).getValue();
	
		for (Object o : typen) {
			Sendbares rs = Sendbares.extractObject((String)o);
			feldTypen.add(new FeldTyp(rs));
		}
		
		MapEditorModel mem  = new MapEditorModel(feldTypen);		
		
		Object [][] felder = (Object[][]) props.get(1).getValue();
		
		mem.initWelt(felder[0].length, felder.length);
		
		for(int i = 0; i < felder.length; i++) {
			for(int j = 0; j < felder[0].length; j++) {
//				System.out.println(felder[i][j]);
				int typ = Integer.parseInt((String)felder[i][j]);
//				System.out.println(typ);
				mem.writeFeldTyp(j, i, typ);
			}
		}
		
		return mem;
		
	}

}
