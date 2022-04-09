package lib.ctrl.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lib.ctrl.gui.elements.Element;

/**
 *
 * Einfacher Handler für Buttons ohne Offset
 * 
 * @author paulb
 *
 */
public class OV_GUI_Controller_Light implements MouseListener, MouseMotionListener {

	private List<Element> buttons;
	private int x, y, width, height;

	private Color cBackground, cRahmen;

	private JPanel parent;

	public OV_GUI_Controller_Light(int x, int y, int width, int height, JPanel parent) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.buttons = new ArrayList<>();
		this.cBackground = new Color(250, 250, 250);
		this.cRahmen = Color.BLACK;

		this.parent = parent;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		for (Element e : buttons) {
			e.handleMouseMove(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		for (Element e : buttons) {
			e.handleMouseMove(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		for (Element e : buttons) {
			e.handleMousePress(arg0.getX(), arg0.getY(), arg0.getButton());
		}
		parent.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		for (Element e : buttons) {
			e.handleMouseRelease(arg0.getX(), arg0.getY(), arg0.getButton());
		}
		parent.repaint();
	}

	public void addElement(Element e) {
		this.buttons.add(e);
	}

	public void drawGUI(Graphics2D g) {
		g.setColor(cBackground);
		g.fillRect(x, y, width, height);
		g.setColor(cRahmen);
		g.drawRect(x, y, width, height);

		for (Element e : buttons) {
			e.draw(g);
		}

	}

}
