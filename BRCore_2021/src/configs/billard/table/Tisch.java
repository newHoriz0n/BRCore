package configs.billard.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lib.model.phx.Hindernis;

public class Tisch {

	private double laenge;
	private double breite;

	private List<Hindernis> banden;

	private Color cBande;

	// TODO: Lochmaﬂe vorne / hinten unterschiedlich
	// Die Maﬂe der Mitteltaschen betragen vorne 135 mm bis 145 mm und hinten 110 mm
	// bis 120 mm, w‰hrend die der Ecktaschen vorne 125 mm bis 135 mm und hinten 105
	// mm bis 115 mm betragen.

	private double lochdurchmesser = 60;
	private double bandenstaerke = 60;

	/**
	 * 
	 * @param laenge
	 * @param breite
	 */
	public Tisch(double laenge, double breite) {
		this.laenge = laenge;
		this.breite = breite;

		setBandenfarbe(new Color(60, 35, 0));
		loadBanden();
	}

	/**
	 * Initialisiert die Banden abh‰ngig von Feldmaﬂen, Bandenstaerke und
	 * Lochdurchmesser
	 */
	private void loadBanden() {
		this.banden = new ArrayList<>();
		// Oben Links
		banden.add(new Hindernis(
				new double[] { (lochdurchmesser * Math.sqrt(2) / 2) - bandenstaerke, (laenge / 2) - (lochdurchmesser / 2),
						(laenge / 2) - (lochdurchmesser / 2), lochdurchmesser * Math.sqrt(2) / 2 },
				new double[] { -bandenstaerke, -bandenstaerke, 0, 0 }));
		// Oben Rechts
		banden.add(new Hindernis(
				new double[] { (laenge / 2) + (lochdurchmesser / 2), (laenge) - (lochdurchmesser * Math.sqrt(2) / 2) + bandenstaerke,
						laenge - (lochdurchmesser * Math.sqrt(2) / 2), (laenge / 2) + (lochdurchmesser / 2) },
				new double[] { -bandenstaerke, -bandenstaerke, 0, 0 }));
		// Rechts
		banden.add(new Hindernis(new double[] { laenge, laenge + bandenstaerke, laenge + bandenstaerke, laenge },
				new double[] { lochdurchmesser * Math.sqrt(2) / 2, (lochdurchmesser * Math.sqrt(2) / 2) - bandenstaerke,
						breite - (lochdurchmesser * Math.sqrt(2) / 2) + bandenstaerke, breite - (lochdurchmesser * Math.sqrt(2) / 2) }));
		// Unten Rechts
		banden.add(new Hindernis(
				new double[] { (laenge / 2) + (lochdurchmesser / 2), laenge - (lochdurchmesser * Math.sqrt(2) / 2),
						laenge - (lochdurchmesser * Math.sqrt(2) / 2) + bandenstaerke, (laenge / 2) + (lochdurchmesser / 2) },
				new double[] { breite, breite, breite + bandenstaerke, breite + bandenstaerke }));
		// Unten Links
		banden.add(new Hindernis(
				new double[] { (lochdurchmesser * Math.sqrt(2) / 2), (laenge / 2) - (lochdurchmesser / 2), (laenge / 2) - (lochdurchmesser / 2),
						(lochdurchmesser * Math.sqrt(2) / 2) - bandenstaerke },
				new double[] { breite, breite, breite + bandenstaerke, breite + bandenstaerke }));
		// Links
		banden.add(new Hindernis(new double[] { -bandenstaerke, 0, 0, -bandenstaerke },
				new double[] { (lochdurchmesser * Math.sqrt(2) / 2) - bandenstaerke, (lochdurchmesser * Math.sqrt(2) / 2),
						breite - (lochdurchmesser * Math.sqrt(2) / 2), breite - (lochdurchmesser * Math.sqrt(2) / 2) + bandenstaerke }));

		for (Hindernis h : banden) {
			h.setHintergrundFarbe(cBande);
		}

	}

	public double getLaenge() {
		return laenge;
	}

	public double getBreite() {
		return breite;
	}

	public List<Hindernis> getBanden() {
		return banden;
	}

	public double getBandenstaerke() {
		return bandenstaerke;
	}

	public double getLochdurchmesser() {
		return lochdurchmesser;
	}

	public void setBandenfarbe(Color c) {
		this.cBande = c;
	}

	public Color getBandenfarbe() {
		return cBande;
	}

}
