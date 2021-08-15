package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.Stadt;
import configs.easyStrategy.game.Truppe;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class GUI_Ctrl_Stadtansicht extends OV_GUI_Controller {

	private Stadt s;

	private int offYtruppenButtons = 270;
	private List<Button> truppenButtons = new ArrayList<>();

	public GUI_Ctrl_Stadtansicht(String titel, int posX, int posY, int width, int height, Stadt s, OV_Model m) {
		super(1, titel, posX, posY, width, height, m);
		this.s = s;

		updateTruppenButtons();
	}

	@Override
	protected void drawGUIBackground(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);

		// Titel
		g2d.drawString(s.getName(), 10, 30);
		g2d.drawLine(0, 40, width, 40);

		// Status
		DecimalFormat df = new DecimalFormat("##.###");
		g2d.drawRect(10, 60, 20, 20);
		g2d.drawString("" + df.format(s.getMaterial()), 40, 75);
		g2d.drawString("M", 15, 75);
		g2d.drawRect(10, 90, 20, 20);
		g2d.drawString("" + df.format(s.getArbeiter()), 40, 105);
		g2d.drawString("A", 15, 105);
		g2d.drawRect(10, 120, 20, 20);
		g2d.drawString("" + s.getKaempfer(), 40, 135);
		g2d.drawString("K", 15, 135);

		// Truppen
		g2d.setColor(Color.WHITE);
		g2d.drawLine(0, 180, width, 180);
		g2d.drawLine(0, 182, width, 182);
		int offY = offYtruppenButtons;
		g2d.drawString("Truppen:", 10, offY);
		for (Truppe t : s.getStationierteTruppen()) {
			offY += 30;
			g2d.drawLine(0, offY - 17, width, offY - 17);
			g2d.drawString(t.getName(), 10, offY);
			g2d.drawString("" + t.getKaempfer(), 40, offY);
			g2d.drawString("A:" + t.getArbeiter(), 170, offY);
			g2d.drawString("M:" + t.getMaterial(), 170, offY + 30);
			g2d.drawLine(0, offY + 43, width, offY + 43);
			offY += 30;
		}

	}

	@Override
	protected void drawGUIOverlay(Graphics2D g2d) {

		// Ausbilden
		g2d.setColor(Color.GREEN);
		g2d.fillRect(122, 92, 7, (int) (46 * s.getAusbildungsFortschritt()));
		g2d.setColor(Color.WHITE);
		g2d.drawString("(+" + s.getAzubis() + ")", 65, 135);
	}

	@Override
	protected List<Button> loadButtons() {
		List<Button> buttons = new ArrayList<>();

		// Add Kaempfer
		Button addKaemfer = new ES_BlackRectButton(100, 90, 30, 50);
		addKaemfer.setText("K+");
		addKaemfer.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				s.ausbildungStarten(1);
			}
		});
		buttons.add(addKaemfer);

		Button redAzubis = new ES_BlackRectButton(60, 120, 38, 20);
		redAzubis.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				s.ausbildungAbbrechen(1);
			}
		});
		buttons.add(redAzubis);

		// Add Truppen
		Button addTruppe = new ES_BlackRectButton(10, 200, 30, 30);
		addTruppe.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				if (s.getKaempfer() >= 1) {
					s.stationiereTruppe(((EasyStrategy) m).requestTruppe("", s.getPosX(), s.getPosY(), s.getSpielerID(), s, 1));
					updateTruppenButtons();
				}
			}

		});
		buttons.add(addTruppe);

		return buttons;
	}

	private void updateTruppenButtons() {
		removeButtons(truppenButtons);
		truppenButtons.clear();

		int offY = offYtruppenButtons - 12;
		for (Truppe t : s.getStationierteTruppen()) {

			// Truppe aufstocken
			Button truppAufstocken = new ES_BlackRectButton(100, offY + 30, 20, 20);
			truppAufstocken.setText("+");
			truppAufstocken.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if (s.verwendeKaempfer(1)) {
						t.addKaempfer(1);
					}
				}
			});
			truppenButtons.add(truppAufstocken);

			// Truppe reduzieren
			Button truppReduzieren = new ES_BlackRectButton(130, offY + 30, 20, 20);
			truppReduzieren.setText("-");
			truppReduzieren.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if (t.verkeinern(1)) {
						s.addKaempfer(1);
						if (t.getKaempfer() == 0) {
							s.truppeAufloesen(t);
							updateTruppenButtons();
						}
					}

				}
			});
			truppenButtons.add(truppReduzieren);

			// Truppe aussenden
			Button truppEnts = new ES_BlackRectButton(100, offY + 60, 20, 20);
			truppEnts.setText("->");
			truppEnts.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					((EasyStrategy) m).truppeEntsenden(s, t);
					updateTruppenButtons();
				}
			});
			truppenButtons.add(truppEnts);

			// Truppe auflösen
			Button truppAufl = new ES_BlackRectButton(130, offY + 60, 20, 20);
			truppAufl.setText("X");
			truppAufl.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					s.truppeAufloesen(t);
					updateTruppenButtons();
				}
			});
			truppenButtons.add(truppAufl);
			
			// Truppe mehr Arbeiter
			Button truppArbeiterMehr = new ES_BlackRectButton(220, offY + 30, 20, 20);
			truppArbeiterMehr.setText("+");
			truppArbeiterMehr.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if(s.verwendeArbeiter(1)) {
						t.addArbeiter(1);
					}
				}
			});
			truppenButtons.add(truppArbeiterMehr);
			
			// Truppe weniger Arbeiter
			Button truppArbeiterWeniger = new ES_BlackRectButton(250, offY + 30, 20, 20);
			truppArbeiterWeniger.setText("-");
			truppArbeiterWeniger.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if(t.arbeiterVerwenden(1)) {
						s.addArbeiter(1);
					}
				}
			});
			truppenButtons.add(truppArbeiterWeniger);
			
			// Truppe mehr Material
			Button truppMaterialMehr = new ES_BlackRectButton(220, offY + 60, 20, 20);
			truppMaterialMehr.setText("+");
			truppMaterialMehr.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if(s.verwendeMaterial(1)) {
						t.addMaterial(1);
					}
				}
			});
			truppenButtons.add(truppMaterialMehr);
			
			// Truppe weniger Material
			Button truppMaterialWeniger = new ES_BlackRectButton(250, offY + 60, 20, 20);
			truppMaterialWeniger.setText("-");
			truppMaterialWeniger.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if(t.materialVerwenden(1)) {
						s.addMaterial(1);
					}
				}
			});
			truppenButtons.add(truppMaterialWeniger);

			offY += 60;
		}
		addButtons(truppenButtons);
	}

	@Override
	protected int[] getGUICoordsVonScreenCoords(int screenX, int screenY) {
		return new int[] { screenX - posX, screenY - posY };
	}

	@Override
	public void handleFreeMouseRelease(int mouseX, int mouseY, int button) {
	}

	@Override
	public void updateGUICtrl() {
		updateTruppenButtons();
	}

}
