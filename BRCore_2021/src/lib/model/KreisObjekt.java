package lib.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import lib.ctrl.EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.ctrl.gui.Aktion;
import lib.model.ObjektVerwaltung.ObjectVerwaltungSettingValues;
import lib.view.Betrachter;

public class KreisObjekt implements Comparable<KreisObjekt>, OV_EventHandler {

	private int radius;
	private double posX;
	private double posY;

	private double ausrichtung;

	private Color farbeHintergrund;
	private Color farbeRahmen;
	private BufferedImage bild;

	private double sichtbarkeit = 0; // [0,1]

	private double centerDistanz = 0;
	private double randDistanz = 0;

	private int ohneBullsEyeAnzeigDistanz = 2000;

	private HashMap<EEventTyp, Aktion> eventAktionen = new HashMap<>();

	public KreisObjekt(int x, int y, int rad, Color hintergrundFarbe, Color rahmenFarbe) {
		this.posX = x;
		this.posY = y;
		this.radius = rad;
		this.farbeHintergrund = hintergrundFarbe;
		this.farbeRahmen = rahmenFarbe;
	}

	public int getRadius() {
		return radius;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setBild(BufferedImage img) {
		this.bild = img;
	}

	public void setAusrichtung(double richtung) {
		this.ausrichtung = richtung;
	}

	public void setEventAktion(EEventTyp e, Aktion a) {
		eventAktionen.put(e, a);
	}

	/**
	 * 
	 * @param b
	 * @param metereologischeSichtweite
	 * @param sichtAufloesung
	 * @param
	 * @return 0: irrelevant, 1: metereologisch sichtbar, 3: im Screen sichtbar
	 */
	public int calcRelevanz(Betrachter b, double metereologischeSichtweite, double sichtAufloesung, double screenRadius,
			ObjectVerwaltungSettingValues bullseyeSetting) {

		// TODO: Berücksichtige Bewegungsgeschwindigkeit in screenRadius

		if (b != null) {
			if (posX > b.getX() - metereologischeSichtweite && posX < b.getX() + metereologischeSichtweite
					&& posY > b.getY() - metereologischeSichtweite && posY < b.getY() + metereologischeSichtweite) {

				calcDistanzZu(b.getX(), b.getY());

				if (bullseyeSetting.equals(ObjectVerwaltungSettingValues.BULLSEYE_ON)) {

					this.sichtbarkeit = 1 - ((randDistanz - screenRadius) / metereologischeSichtweite);

					if (sichtbarkeit > 0) {
						if (calcSichtwinkelTan() > sichtAufloesung) {
							if (randDistanz < screenRadius - 100) { // Nur im HauptScreen sichtbar
								return 3;
							} else if (randDistanz < screenRadius + 100) { // In Haupt und Entfernt sichtbar
								return 2;
							}
							return 1; // Nur entfernt sichtbar
						}
					}
				} else {
					if (randDistanz < ohneBullsEyeAnzeigDistanz) { // Nur im HauptScreen sichtbar
						return 3;
					}
				}

			}
		}

		return 0;

	}

	public void draw(Graphics2D g, Betrachter b, double screenRadius, ObjectVerwaltungSettingValues bullseyeSetting) {

		boolean draw = false;
		if (bullseyeSetting.equals(ObjectVerwaltungSettingValues.BULLSEYE_ON)) {
			if (randDistanz < screenRadius) {
				draw = true;
			}
		} else {
			if (randDistanz < ohneBullsEyeAnzeigDistanz) {
				draw = true;
			}
		}
		if (draw) {
			g.setColor(farbeHintergrund);
			g.fillOval((int) (posX - radius), (int) (posY - radius), (int) (radius * 2), (int) (radius * 2));
			if (bild != null) {
				AffineTransform at = new AffineTransform();
				at.translate(posX - radius, posY - radius);
				at.rotate(ausrichtung, radius, radius);
				at.scale(radius * 2 / (double) bild.getWidth(), radius * 2 / (double) bild.getHeight());
				g.drawImage(bild, at, null);
			}
			g.setColor(farbeRahmen);
			g.drawOval((int) (posX - radius), (int) (posY - radius), (int) (radius * 2), (int) (radius * 2));
		}
	}

	public void drawEntfernt(Graphics2D g, Betrachter b, double screenRadius, double entferntleistenHoehe) {
		double dx = posX - b.getX();
		double dy = posY - b.getY();
		double r = calcErscheinungsGroesse(b, screenRadius);

		if (centerDistanz > screenRadius - entferntleistenHoehe / 2) {

			g.setColor(farbeHintergrund);
			g.fillOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r), (int) (r * 2), (int) (r * 2));

			g.setColor(farbeRahmen);
			g.drawOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r), (int) (r * 2), (int) (r * 2));

			double sFaktor = 1 - Math.max(0, Math.min(1, sichtbarkeit));

			// Nebel
			g.setColor(new Color(255, 255, 255, (int) (255 * sFaktor)));
			g.fillOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerDistanz - r), (int) (r * 2), (int) (r * 2));

		}
	}

	/**
	 * 
	 * @param b
	 * @param screenRadius
	 * @return Radius des Kreises erscheinend auf ScreenRadius
	 */
	public double calcErscheinungsGroesse(Betrachter b, double screenRadius) {
		calcDistanzZu(b.getX(), b.getY());
		return (double) radius / (double) (centerDistanz + radius) * screenRadius;
	}

	public double calcSichtwinkelTan() {
		return (radius * 2) / randDistanz;
	}

	public void calcDistanzZu(double x, double y) {
		this.centerDistanz = Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2));
		this.randDistanz = Math.max(0, centerDistanz - radius);
	}

	@Override
	public int compareTo(KreisObjekt o) {
		if (randDistanz < o.randDistanz) {
			return 1;
		} else if (randDistanz > o.randDistanz) {
			return -1;
		} else {
			return 0;
		}
	}

	public double getAusrichtung() {
		return ausrichtung;
	}

	@Override
	public void handleEvent(EEventTyp e) {
		if (eventAktionen.containsKey(e)) {
			eventAktionen.get(e).run();
		}
	}

}
