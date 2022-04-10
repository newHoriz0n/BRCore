package lib.map.mapEditorRaster;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;

public class FeldTyp implements Sendbares{

	private String bezeichnung;
	private String kuerzel;
	private String bildURL;
	private Color farbe;

	public FeldTyp(String bezeichnung, String kuerzel, Color farbe) {
		this.bezeichnung = bezeichnung;
		this.kuerzel = kuerzel;
		this.bildURL = "";
		this.farbe = farbe;
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

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<>();
		
		SendEigenschaft seBezeichnung = new SendEigenschaft("Bezeichnung", EObjektTyp.STRING, bezeichnung);
		ses.add(seBezeichnung);
		
		SendEigenschaft seKuerzel = new SendEigenschaft("Kuerzel", EObjektTyp.STRING, kuerzel);
		ses.add(seKuerzel);
		
		SendEigenschaft seBild = new SendEigenschaft("Bild", EObjektTyp.STRING, bildURL);
		ses.add(seBild);
		
		SendEigenschaft seFarbe = new SendEigenschaft("Farbe", EObjektTyp.STRING, "" + farbe.toString());
		ses.add(seFarbe);
		
		return ses;
		
	}

	@Override
	public String getName() {
		return "FeldTyp";
	}

}
