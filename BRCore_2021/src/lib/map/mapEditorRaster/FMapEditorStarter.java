package lib.map.mapEditorRaster;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import lib.io.PBFileReadWriter;
import lib.io.Sendbares;
import lib.map.FeldTyp;
import lib.map.Map;

public class FMapEditorStarter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FMapEditorStarter(MapEditorModel mem) {

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		// BUTTONS
		JButton neueMap = new JButton("Neue Map");
		gbc.gridx = 0;
		gbc.gridy = 1;
		neueMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FMapEditorNeueMapSettings n = new FMapEditorNeueMapSettings(mem);
				n.requestFocus();
				dispose();
			}
		});
		add(neueMap, gbc);

		JButton ladeMap = new JButton("# Map laden...");
		gbc.gridx = 1;
		ladeMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
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
					
					Map map = Map.createFromSendbarem(Sendbares.extractObject(infos.get(0)));
					
					MapEditorModel mem = new MapEditorModel();
					mem.setMap(map);
					FMapEditor m = new FMapEditor(mem);
					m.requestFocus();
					dispose();
				}	
				
				
				
			}
		});
		add(ladeMap, gbc);

		pack();
		setSize(400, 300);

	}

	public static void main(String[] args) {

		// Beispiel zur Initialisierung des Map Editors
		
		FeldTyp ffrei = new FeldTyp("Frei", "[ ]", Color.WHITE);
		ffrei.setEigenschaften(new String [] {"Hoehe" , "Besitzer", "Bewuchs"});
		
		FeldTyp fwand = new FeldTyp("Wand", "[#]", Color.DARK_GRAY);
		List<FeldTyp> feldTypen = new ArrayList<>();
		feldTypen.add(ffrei);
		feldTypen.add(fwand);

		Map m = new Map();
		m.setFeldTypen(feldTypen);
		
		MapEditorModel mem = new MapEditorModel();
		mem.setMap(m);
		
		FMapEditorStarter mes = new FMapEditorStarter(mem);
		mes.requestFocus();
	}

}