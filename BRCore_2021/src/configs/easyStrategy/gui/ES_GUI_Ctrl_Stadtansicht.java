package configs.easyStrategy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.game.Truppe;
import configs.easyStrategy.game.EasyStrategy.ES_State;
import configs.easyStrategy.game.stadt.Gebaeude;
import configs.easyStrategy.game.stadt.Stadt;
import lib.ctrl.OV_Controller;
import lib.ctrl.gui.Aktion;
import lib.ctrl.gui.OV_GUI_Controller;
import lib.ctrl.gui.elements.Button;
import lib.model.OV_Model;

public class ES_GUI_Ctrl_Stadtansicht extends OV_GUI_Controller {

	private Stadt s;

	private int offYtruppenButtons = 380;
	private List<Button> truppenButtons = new ArrayList<>();
	private int offYGebaudeButtons = 160;
	private List<Button> gebaeudeButtons = new ArrayList<>();

	private int bauFortschrittX;
	private int bauFortschrittY;
	private int bauFortschrittWidth;
	private int bauFortschrittHeight;

	public ES_GUI_Ctrl_Stadtansicht(OV_Controller oc, Stadt s, OV_Model m) {
		super(1, "Stadtansicht", oc.getViewer().getWidth() - 300, 0, 300, oc.getViewer().getHeight(), m);
		this.s = s;

		updateGebaeudeButtons();
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

		// Gebäude
		g2d.setColor(Color.WHITE);
		g2d.drawLine(0, offYGebaudeButtons, width, offYGebaudeButtons);
		g2d.drawLine(0, offYGebaudeButtons + 2, width, offYGebaudeButtons + 2);
		g2d.drawString("Gebäude:", 10, offYGebaudeButtons + 28);

		// Truppen
		g2d.setColor(Color.WHITE);
		g2d.drawLine(0, offYtruppenButtons - 30, width, offYtruppenButtons - 30);
		g2d.drawLine(0, offYtruppenButtons - 28, width, offYtruppenButtons - 28);
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

		// Bau
		if (s.isInBau()) {
			g2d.setColor(Color.GREEN);
			g2d.fillRect(bauFortschrittX, bauFortschrittY, (int) (bauFortschrittWidth * s.getBauFortschritt()), bauFortschrittHeight);
		}
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

		// Add Ressourcenabbau
		Button addRessourcenAbbau = new ES_BlackRectButton(100, 60, 30, 20);
		addRessourcenAbbau.setText("...");
		addRessourcenAbbau.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				((EasyStrategy) m).setState(ES_State.STADT_ABBAUEN);
			}
		});
		buttons.add(addRessourcenAbbau);

		return buttons;
	}

	private void updateGebaeudeButtons() {
		removeButtons(gebaeudeButtons);
		gebaeudeButtons.clear();

		// Gebäude
		int posX = 0;
		int posY = 200;
		for (Gebaeude.Typ typ : s.getGebaeudeLevel().keySet()) {
			final Button gebButton = new ES_BlackRectButton(10 + posX * 70, posY, 60, 60);
			gebButton.setText(typ.toString().substring(0, 3) + "  [" + s.getGebaeudeLevel().get(typ) + "]");
			gebButton.setAktionLinks(new Aktion() {

				@Override
				public void run() {
					if (s.upgradeGebaeude(typ)) {
						bauFortschrittX = gebButton.getPosX() + 2;
						bauFortschrittY = gebButton.getPosY() + 50;
						bauFortschrittWidth = 56;
						bauFortschrittHeight = 8;
					}
				}
			});
			gebaeudeButtons.add(gebButton);

			posX++;
			if (posX >= 4) {
				posX = 0;
				posY += 70;
			}

		}

		addButtons(gebaeudeButtons);

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
					if (s.verwendeArbeiter(1)) {
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
					if (t.arbeiterVerwenden(1)) {
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
					if (s.verwendeMaterial(1)) {
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
					if (t.materialVerwenden(1)) {
						s.addMaterial(1);
					}
				}
			});
			truppenButtons.add(truppMaterialWeniger);

			offY += 60;
		}

		// Add Truppen
		Button addTruppe = new ES_BlackRectButton(10, offY + 30, 30, 30);
		addTruppe.setText("+");
		addTruppe.setAktionLinks(new Aktion() {

			@Override
			public void run() {
				if (s.getKaempfer() >= 1) {
					s.stationiereTruppe(((EasyStrategy) m).requestTruppe("", s.getPosX(), s.getPosY(), s.getSpielerID(), s, 1, 0, 0));
					updateTruppenButtons();
				}
			}

		});
		truppenButtons.add(addTruppe);

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
		updateGebaeudeButtons();
	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleFreeMouseMove(int aktScreenMouseX, int aktScreenMouseY, int aktRealMausPosX, int aktRealMausPosY, int button) {
		// TODO Auto-generated method stub
		
	}

}
