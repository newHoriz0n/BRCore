package lib.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import javax.swing.JPanel;

import lib.ctrl.OV_Controller;
import lib.model.ObjektVerwaltung;

public class OV_ViewContainer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObjektVerwaltung ov;
	private OV_Controller oc;
	
	private double [] offset = new double [2];

	public OV_ViewContainer(ObjektVerwaltung ov, OV_Controller oc) {
		this.ov = ov;
		this.oc = oc;
		
		addMouseListener(oc);
		addMouseMotionListener(oc);
		addMouseWheelListener(oc);
		
		ov.setView(this);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		offset[0] = getWidth() / 2 - ov.getBetrachter().getX();
		offset[1] = getHeight() / 2 - ov.getBetrachter().getY();
		
		AffineTransform at = new AffineTransform();
		at.translate(offset[0], offset[1]);
		g2d.transform(at);	// Ab hier transformiertes Zeichnen
		
		oc.drawMainBackground(g2d);
		ov.draw(g2d);	// Objekte zeichnen
		oc.draw(g2d);	// GUI zeichnen
		
		try {
			at.invert();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		g2d.transform(at);	// Ende des transformierten Zeichnens

		oc.drawOverlayGUIs(g2d);
		ov.getBetrachter().drawFixed(g2d, getWidth() / 2, getHeight() / 2);

	}
	
	/**
	 * Versatz des Viewports des Betrachters zur Mitte des Bildschirms
	 * @return
	 */
	public double[] getScreenCenterViewOffset() {
		return offset;
	}
	
}
