package lib.model.phx;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import lib.math.Polygon2D;
import lib.model.KreisObjekt;

public class Collision {

	private KreisObjekt k1;
	private KreisObjekt k2;

	public Collision(KreisObjekt k1, KreisObjekt k2) {
		this.k1 = k1;
		this.k2 = k2;
	}

	public KreisObjekt getK1() {
		return k1;
	}

	public KreisObjekt getK2() {
		return k2;
	}

	static double EPSILON = 0.0001;

	public static boolean calcCollisionCircleCircle(CollidableCircle p_c1, CollidableCircle p_c2) { // l_n: Normalenvektor (Verbindung zwischen den
																									// beiden Kugeln)
		double l_nx = p_c2.getCenterX() - p_c1.getCenterX();
		double l_ny = p_c2.getCenterY() - p_c1.getCenterY();

		// Abstand der beiden Kugeln
		double l_dist = Math.sqrt(l_nx * l_nx + l_ny * l_ny);

		// Die Kugeln dürfen sich nicht an derselben Stelle befinden,
		// weil sie sonst nicht entlang der nicht-existenten Normalen
		// auseinandergezogen werden können.
		// Dieser Fall sollte nur sehr selten eintreten (z.B. wenn eine
		// neue Kugel genau an der Stelle einer existenten Kugel erstellt wird).
		if (l_dist < EPSILON) {
			p_c2.setCenterX(p_c2.getCenterX() + p_c2.getRadius());
			l_nx += p_c2.getRadius();
			l_dist = Math.sqrt(l_nx * l_nx + l_ny * l_ny);
		}

		// Kugeln kollidieren, wenn der Abstand kleiner gleich der Summe
		// der Radien beider Kugeln ist.
		if (l_dist <= p_c1.getRadius() + p_c2.getRadius()) { // Normalenvektor wird normalisiert: |l_n| = 1
			l_nx /= l_dist;
			l_ny /= l_dist;

			// Tangentialvektor (senkrecht zu Normalenvektor, zwischen beiden Kugeln)
			double l_tx = l_ny;
			double l_ty = -l_nx;

			// Summe der Massen beider Kugeln
			double l_sm1m2 = p_c1.getMass() + p_c2.getMass();

			// Überlappung der beiden Kugeln
			double l_overlap = (p_c1.getRadius() + p_c2.getRadius()) - l_dist;

			// Verschieben der beiden Kugeln entlang der Normalen,
			// so dass sie sich nicht mehr überlappen!
			// Die Massen werden berücksichtigt. Schwerere Kreise werden
			// weniger weit verschoben.
			l_overlap += 2; /* fight penetration */
			l_overlap *= 1.001; /* fight penetration */
			p_c1.setCenterX(p_c1.getCenterX() - l_nx * l_overlap * (p_c2.getMass() / l_sm1m2));
			p_c1.setCenterY(p_c1.getCenterY() - l_ny * l_overlap * (p_c2.getMass() / l_sm1m2));
			p_c2.setCenterX(p_c2.getCenterX() + l_nx * l_overlap * (p_c1.getMass() / l_sm1m2));
			p_c2.setCenterY(p_c2.getCenterY() + l_ny * l_overlap * (p_c1.getMass() / l_sm1m2));

			// Zerlegung der Geschwindigkeitsvektoren in Normalen- und
			// Tangentialanteil: v=sn*n+st*t, wobei
			// v Vektor, n Normalenvektor, t Tagentialvektor und
			// sn, st zwei skalare Werte sind.
			// Es gilt: v*n = sn*(n*n)+st*(t*n) = sn, da t*n=0 und n*n = 1
			// Es gilt: v*t = sn*(n*t)+st*(t*t) = st, da t*n=0 und t*t = 1
			// Also ist: sn = v*n und st=v*t

			// Ball 1: Zerlegung des Geschwindigkeitsvektors in n- und t-Anteil
			double l_sn1 = l_nx * p_c1.getSpeedX() + l_ny * p_c1.getSpeedY();
			double l_st1 = l_tx * p_c1.getSpeedX() + l_ty * p_c1.getSpeedY();

			double l_n1x = l_nx * l_sn1; // Normalenvektor-Anteil von p_c1.vx
			double l_n1y = l_ny * l_sn1; // Normalenvektor-Anteil von p_c1.vy

			double l_t1x = l_tx * l_st1; // Tangentialvektor-Anteil von p_c1.vx
			double l_t1y = l_ty * l_st1; // Tangentialvektor-Anteil von p_c1.vy

			// Ball 2: Zerlegung des Geschwindigkeitsvektors in n- und t-Anteil
			double l_sn2 = l_nx * p_c2.getSpeedX() + l_ny * p_c2.getSpeedY();
			double l_st2 = l_tx * p_c2.getSpeedX() + l_ty * p_c2.getSpeedY();

			double l_n2x = l_nx * l_sn2; // Normalenvektor-Anteil von p_c2.vx
			double l_n2y = l_ny * l_sn2; // Normalenvektor-Anteil von p_c2.vy

			double l_t2x = l_tx * l_st2; // Tangentialvektor-Anteil von p_c2.vx
			double l_t2y = l_ty * l_st2; // Tangentialvektor-Anteil von p_c2.vy

			// Der Impulserhaltungssatz
			// m1*v1 + m2*v2 = m1*v1' + m2*v2'
			// (wobei m1, m2 = Massen der Körper
			// und v1, v2, v1', v2' die Geschwindigkeiten)
			// und der Energieerhaltungssatz
			// 0,5*m1*v1² + 0,5*m2*v2² = 0,5*m1*v1'² + 0,5*m2*v2'²
			// führen nach einfachen mathematischen Umformungen zu
			// folgenden Beziehungen (für den eindimensionalen Fall):
			// v1' = 2*(m1*v1+m2*v2)/(m1+m2) - v1
			// v2' = 2*(m1*v1+m2*v2)/(m1+m2) - v2
			// 2*(m1*v1+m2*v2)/(m1+m2) ist die Geschwindigkeit des
			// gemeinsamen Schwerpunktes
			// (center of gravity).
			// Im zweidimensionalen Fall gilt, dass die Kollision entlang
			// der Normalen erfolgt. Die tangentialen Anteile der der
			// Bewegungsrichtungen werden unverändert übernommen.

			double l_vcgx = 2 * (p_c1.getMass() * l_n1x + p_c2.getMass() * l_n2x) / l_sm1m2;
			double l_vcgy = 2 * (p_c1.getMass() * l_n1y + p_c2.getMass() * l_n2y) / l_sm1m2;

			p_c1.setSpeedX(l_vcgx - l_n1x + l_t1x);
			p_c1.setSpeedY(l_vcgy - l_n1y + l_t1y);
			p_c2.setSpeedX(l_vcgx - l_n2x + l_t2x);
			p_c2.setSpeedY(l_vcgy - l_n2y + l_t2y);

			//// Alternative Berechnung:
			// Differenz der Massen beide Kugeln
			// let l_dm2m1 = p_c2.m - p_c1.m;
			// p_c1.vx = (l_n2x*p_c2.m*2-l_n1x*l_dm2m1)/l_sm1m2 + l_t1x;
			// p_c1.vy = (l_n2y*p_c2.m*2-l_n1y*l_dm2m1)/l_sm1m2 + l_t1y;
			// p_c2.vx = (l_n1x*p_c1.m*2+l_n2x*l_dm2m1)/l_sm1m2 + l_t2x;
			// p_c2.vy =(l_n1y*p_c1.m*2+l_n2y*l_dm2m1)/l_sm1m2 + l_t2y;

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	public static boolean calcCollisionCircleFixPolygon(CollidableCircle c, Polygon2D p) {

		// Berechne Kollisionspolygon als Polygon mit Kreisen an den Ecken mit Radius
		// des CC und Strecken zwischen den Kreisen mit Abstand Radius des CC vom
		// Ursprungspolygon

		List<CollidableCircle> eckKreise = new ArrayList<CollidableCircle>();
		for (int i = 0; i < p.getXs().length; i++) {
			eckKreise.add(new FixCollisionCircle(p.getXs()[i], p.getIntYs()[i], c.getRadius()));
		}

		boolean kollision = false;
		
		// Berechne Kollisionen zu Eckkreisen
		for (CollidableCircle e : eckKreise) {
			if(calcCollisionCircleCircle(CollidableCircle.getCCMinimized(c), e)) {
				kollision = true;
				break;
			}
		}
		
		if(p.getExpandedPolygon(c.getRadius()).contains(c.getCenterX(), c.getCenterY())) {
			return true;
		}
		
		// Berechne Kollisionen zu Kanten
		List<Line2D.Double> kanten = p.getExpandedPolygon(c.getRadius()).getSegments();

		
		// TODO: Bestimme Abstand von Kreismittelpunkt zu jedem Segment

		return false;
	}

}
