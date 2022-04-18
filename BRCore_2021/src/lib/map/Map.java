package lib.map;

import java.util.ArrayList;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;

public class Map implements Sendbares {

	private String name;
	private Integer[][] felder;
	private List<FeldTyp> feldTypen;

	public Map() {
		this.felder = new Integer[0][0];
		this.feldTypen = new ArrayList<FeldTyp>();
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<>();

		SendEigenschaft seName = new SendEigenschaft("Name", EObjektTyp.STRING, name);
		ses.add(seName);
		
		SendEigenschaft seTypen = new SendEigenschaft("FeldTypen", EObjektTyp.LIST, feldTypen);
		ses.add(seTypen);

		SendEigenschaft seFelder = new SendEigenschaft("Felder", EObjektTyp.MATRIX, felder);
		ses.add(seFelder);

		return ses;
	}

	@Override
	public String getBezeichner() {
		return "Map";
	}

	public Integer[][] getFelder() {
		return felder;
	}

	public List<FeldTyp> getFeldTypen() {
		return feldTypen;
	}

	public void setFeldTypen(List<FeldTyp> feldTypen2) {
		this.feldTypen = feldTypen2;
	}

	public void initWelt(int breite, int hoehe) {
		this.felder = new Integer[breite][hoehe];
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				felder[i][j] = 0;
			}

		}
	}

	public void writeFeldTyp(int x, int y, int typ) {
		felder[x][y] = typ;
	}

	public void setMapName(String name) {
		this.name = name;
	}
	
	public String getMapName() {
		return this.name;
	}

	public static Map createFromSendbarem(Sendbares s) {

		Map m = new Map();

		List<SendEigenschaft> props = s.getProperties();

		m.setMapName((String)props.get(0).getValue());

		List<FeldTyp> feldTypen = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object> typen = (List<Object>) props.get(1).getValue();
		for (Object o : typen) {
			Sendbares rs = Sendbares.extractObject((String) o);
			feldTypen.add(new FeldTyp(rs));
		}

		m.setFeldTypen(feldTypen);

		Object[][] felder = (Object[][]) props.get(2).getValue();

		m.initWelt(felder[0].length, felder.length);

		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				// System.out.println(felder[i][j]);
				int typ = Integer.parseInt((String) felder[i][j]);
				// System.out.println(typ);
				m.writeFeldTyp(j, i, typ);
			}
		}

		return m;		
		
	}

	public int getBreite() {
		return felder.length;
	}

	public int getHoehe() {
		return felder[0].length;
	}
	
	

}
