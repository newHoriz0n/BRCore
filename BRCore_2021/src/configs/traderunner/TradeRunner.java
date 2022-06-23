package configs.traderunner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import configs.traderunner.bot.TraderBot;
import configs.traderunner.map.TRMap;
import lib.ctrl.OV_EEventTyp;
import lib.ctrl.OV_EventHandler;
import lib.io.PBFileReadWriter;
import lib.io.Sendbares;
import lib.map.Map;

public class TradeRunner implements KeyListener {

	private TRMap map;
	private Trader spieler;
	private List<Trader> bots;

	List<OV_EventHandler> eventhandler = new ArrayList<OV_EventHandler>();
	
	// Keys
	private boolean[] keyPressed = new boolean[256];

	
	// GameSettings
	private double sichtweite = 1.5;
	
	public TradeRunner() {

		loadMap();
		this.spieler = new Trader(map.getSpielerStart()[0], map.getSpielerStart()[1]);
		loadBots();
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			
			@Override
			public void run() {
				update();
			}
		};
		t.schedule(tt, 0, 1000);

	}
	
	private void loadBots() {
		this.bots = new ArrayList<>();
		for (int [] pos : map.getBotStarts()) {
			bots.add(new TraderBot(pos[0], pos[1]));
		}
	}

	private void loadMap() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(PBFileReadWriter.createAbsPfad(this.getClass(), "")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MapEditorDaten", "med");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(new JFrame("Lade Map"));
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}

		File file = chooser.getSelectedFile();
		if (file != null && file.exists()) {
			List<String> infos = PBFileReadWriter.getLines(chooser.getSelectedFile().getPath());

			this.map = new TRMap(Map.createFromSendbarem(Sendbares.extractObject(infos.get(0))));
		}
	}
	
	public void addUpdateListener(OV_EventHandler e) {
		this.eventhandler.add(e);
	}

	public TRMap getMap() {
		return map;
	}

	public int[] getSpielerPosition() {
		return spieler.getPosition();
	}

	public List<Trader> getBots() {
		return bots;
	}
	
	public void update() {
		
		// CALC BOTS
		for (Trader t : bots) {
			// calc Moves
			t.update();
			// Update Positionen
			if(map.getFelder()[((TraderBot)t).getDesiredNextPosition()[0]][((TraderBot)t).getDesiredNextPosition()[1]] != 1) {
				t.setPosition(((TraderBot)t).getDesiredNextPosition()[0], ((TraderBot)t).getDesiredNextPosition()[1]);
			} else {
				((TraderBot)t).hitWall();
			}
			
		}
		
		
		// Update GUI
		for (OV_EventHandler eh : eventhandler) {
			eh.handleEvent(OV_EEventTyp.DEFAULT);
		}
		
		// 
	}
	
	public static void main(String[] args) {

		TradeRunner tr = new TradeRunner();

		FTradeRunner ftr = new FTradeRunner(tr);
		ftr.requestFocus();

	}

	// Key Handling

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
		
		// Spielerbewegung
		int dx = 0;
		int dy = 0;
		switch (e.getKeyCode()) {
		case 37:
			// Links
			dx = -1;
			break;
		case 38:
			// Hoch
			dy = -1;
			break;
		case 39:
			// Rechts
			dx = +1;
			break;
		case 40:
			// Runter
			dy = +1;
			break;
			
		default:
			break;
		}
		
		int [] neuePos = new int [] {spieler.getPosition()[0], spieler.getPosition()[1]};
		neuePos[0] += dx;
		neuePos[1] += dy;
		
		if(map.getFelder()[neuePos[0]][neuePos[1]] != 1) {
			spieler.setPosition(neuePos[0], neuePos[1]);
			for (OV_EventHandler eh : eventhandler) {
				eh.handleEvent(OV_EEventTyp.DEFAULT);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public double getSichtRadius() {
		return sichtweite;
	}
	
}
