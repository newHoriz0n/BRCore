package lib.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.ctrl.gui.Aktion;
import lib.model.ObjektVerwaltung.ObjectVerwaltungSettingValues;
import lib.view.Betrachter;

public abstract class KreisObjekt implements Comparable<KreisObjekt>, OV_EventHandler {

	private long objectID;
	protected int gruppe;
	private boolean alive;
	private boolean clickable = true;

	protected double posX;
	protected double posY;
	private double ausrichtung;
	protected double radius;

	private Color farbeHintergrund;
	private Color farbeRahmen;
	private BufferedImage bild;

	private double sichtbarkeit = 0; // [0,1]

	private double centerBetrachterDistanz = 0;
	private double randBetrachterDistanz = 0;

	private int ohneBullsEyeAnzeigDistanz = 2000;

	private HashMap<OV_EEventTyp, Aktion> eventAktionen = new HashMap<>();

	private long lastUpdate;

	public KreisObjekt(double x, double y, double rad, Color hintergrundFarbe, Color rahmenFarbe) {
		this.posX = x;
		this.posY = y;
		this.radius = rad;
		this.farbeHintergrund = hintergrundFarbe;
		this.farbeRahmen = rahmenFarbe;
		this.objectID = ObjectIDSingleton.getNextID();
		this.lastUpdate = System.currentTimeMillis();
		this.alive = true;
	}

	public void updateKO() {
		long dt = System.currentTimeMillis() - lastUpdate;
		lastUpdate = System.currentTimeMillis();
		update(dt);
	}

	public void setGruppe(int gruppe) {
		this.gruppe = gruppe;
	}

	/**
	 * Legt fest, ob das KreisObjekt als Button reagiert.
	 * 
	 * @param enabled
	 */
	public void setClickable(boolean enabled) {
		this.clickable = enabled;
	}

	/**
	 * 
	 * @param dt:
	 *            Zeit seit letzem Update in [ms];
	 */
	protected abstract void update(long dt);

	public long getObjectID() {
		return objectID;
	}

	public double getRadius() {
		return radius;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosition(double x, double y) {
		this.posX = x;
		this.posY = y;
	}

	public void setBild(BufferedImage img) {
		this.bild = img;
	}

	public void setAusrichtung(double richtung) {
		this.ausrichtung = richtung;
	}

	public void setEventAktion(OV_EEventTyp e, Aktion a) {
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

		if (b != null && alive) {
			if (posX > b.getX() - metereologischeSichtweite && posX < b.getX() + metereologischeSichtweite
					&& posY > b.getY() - metereologischeSichtweite && posY < b.getY() + metereologischeSichtweite) {

				calcBetrachterDistanz(b.getX(), b.getY());

				if (bullseyeSetting.equals(ObjectVerwaltungSettingValues.BULLSEYE_ON)) {

					this.sichtbarkeit = 1 - ((randBetrachterDistanz - screenRadius) / metereologischeSichtweite);

					if (sichtbarkeit > 0) {
						if (calcSichtwinkelTan() > sichtAufloesung) {
							if (randBetrachterDistanz < screenRadius - 100) { // Nur im HauptScreen sichtbar
								return 3;
							} else if (randBetrachterDistanz < screenRadius + 100) { // In Haupt und Entfernt sichtbar
								return 2;
							}
							return 1; // Nur entfernt sichtbar
						}
					}
				} else {
					if (randBetrachterDistanz < ohneBullsEyeAnzeigDistanz) { // Nur im HauptScreen sichtbar
						return 3;
					}
				}

			}
		}

		return 0;

	}

	public void draw(Graphics2D g, Betrachter b, double screenRadius, ObjectVerwaltungSettingValues bullseyeSetting) {

		if (alive) {
			boolean draw = false;
			if (bullseyeSetting.equals(ObjectVerwaltungSettingValues.BULLSEYE_ON)) {
				if (randBetrachterDistanz < screenRadius) {
					draw = true;
				}
			} else {
				if (randBetrachterDistanz < ohneBullsEyeAnzeigDistanz) {
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

	}

	public void drawEntfernt(Graphics2D g, Betrachter b, double screenRadius, double entferntleistenHoehe) {
		double dx = posX - b.getX();
		double dy = posY - b.getY();
		double r = calcErscheinungsGroesse(b, screenRadius);

		if (centerBetrachterDistanz > screenRadius - entferntleistenHoehe / 2) {

			g.setColor(farbeHintergrund);
			g.fillOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r), (int) (r * 2), (int) (r * 2));

			g.setColor(farbeRahmen);
			g.drawOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r), (int) (r * 2), (int) (r * 2));

			double sFaktor = 1 - Math.max(0, Math.min(1, sichtbarkeit));

			// Nebel
			g.setColor(new Color(255, 255, 255, (int) (255 * sFaktor)));
			g.fillOval((int) (b.getX() + dx * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r),
					(int) (b.getY() + dy * (screenRadius - entferntleistenHoehe / 2) / centerBetrachterDistanz - r), (int) (r * 2), (int) (r * 2));

		}
	}

	/**
	 * 
	 * @param b
	 * @param screenRadius
	 * @return Radius des Kreises erscheinend auf ScreenRadius
	 */
	public double calcErscheinungsGroesse(Betrachter b, double screenRadius) {
		calcBetrachterDistanz(b.getX(), b.getY());
		return (double) radius / (double) (centerBetrachterDistanz + radius) * screenRadius;
	}

	public double calcSichtwinkelTan() {
		return (radius * 2) / randBetrachterDistanz;
	}

	/**
	 * Berechnet die euklidische Distanz in der x-y Ebene des Kreisobjekts zur
	 * übergebenen Position. Der Radius wird dabei nicht berücksichtigt!
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double calcDistanzZu(double x, double y) {
		return Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2));
	}

	private void calcBetrachterDistanz(double x, double y) {
		this.centerBetrachterDistanz = Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2));
		this.randBetrachterDistanz = Math.max(0, centerBetrachterDistanz - radius);
	}

	@Override
	public int compareTo(KreisObjekt o) {
		if (randBetrachterDistanz < o.randBetrachterDistanz) {
			return 1;
		} else if (randBetrachterDistanz > o.randBetrachterDistanz) {
			return -1;
		} else {
			return 0;
		}
	}

	public double getAusrichtung() {
		return ausrichtung;
	}

	public int getGruppe() {
		return gruppe;
	}

	@Override
	public void handleEvent(OV_EEventTyp e) {
		if (eventAktionen.containsKey(e)) {
			eventAktionen.get(e).run();
		}
	}

	public KreisObjekt die() {
		this.alive = false;
		return this;
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (objectID ^ (objectID >>> 32));
		long temp;
		temp = Double.doubleToLongBits(posX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(posY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KreisObjekt other = (KreisObjekt) obj;
		if (objectID != other.objectID)
			return false;
		if (Double.doubleToLongBits(posX) != Double.doubleToLongBits(other.posX))
			return false;
		if (Double.doubleToLongBits(posY) != Double.doubleToLongBits(other.posY))
			return false;
		return true;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setHintergrundFarbe(Color c) {
		this.farbeHintergrund = c;
	}

}
