package lib.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;

public class FeldTyp implements Sendbares {

	private String bezeichnung;
	private String kuerzel;
	private String bildURL;
	private Color farbe;
	private String[] eigenschaften;

	public FeldTyp(String bezeichnung, String kuerzel, Color farbe) {
		this.bezeichnung = bezeichnung;
		this.kuerzel = kuerzel;
		this.bildURL = "";
		this.farbe = farbe;
		this.eigenschaften = new String [0];
	}

	public FeldTyp(Sendbares rs) {
		this.bezeichnung = (String) rs.getProperties().get(0).getValue();
		this.kuerzel = (String) rs.getProperties().get(1).getValue();
		this.bildURL = (String) rs.getProperties().get(2).getValue();
		this.farbe = new Color(Integer.parseInt((String) rs.getProperties().get(3).getValue()));
		
		List<String> eigs = new ArrayList<>();
		for (Object o : (Object[])rs.getProperties().get(4).getValue()) {
			eigs.add((String)o);
		}
		
		this.eigenschaften = new String [eigs.size()] ;
		for (int i = 0; i < eigenschaften.length; i++) {
			eigenschaften[i] = eigs.get(i);
		}
	}

	public void setEigenschaften(String[] eigs) {
		this.eigenschaften = eigs;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public String getBild() {
		return bildURL;
	}

	public Color getFarbe() {
		return farbe;
	}

	public String getKuerzel() {
		return kuerzel;
	}
	
	public String[] getEigenschaften() {
		return eigenschaften;
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<>();

		SendEigenschaft seBezeichnung = new SendEigenschaft("Bezeichnung", EObjektTyp.STRING, bezeichnung);
		ses.add(seBezeichnung);

		SendEigenschaft seKuerzel = new SendEigenschaft("Kuerzel", EObjektTyp.STRING, kuerzel);
		ses.add(seKuerzel);

		SendEigenschaft seBild = new SendEigenschaft("Bild", EObjektTyp.STRING, bildURL);
		ses.add(seBild);

		SendEigenschaft seFarbe = new SendEigenschaft("Farbe", EObjektTyp.STRING, "" + farbe.getRGB());
		ses.add(seFarbe);

		SendEigenschaft seEigenschaften = new SendEigenschaft("Eigenschaften", EObjektTyp.ARRAY, eigenschaften);
		ses.add(seEigenschaften);

		return ses;

	}

	@Override
	public String getBezeichner() {
		return "FeldTyp";
	}

}
