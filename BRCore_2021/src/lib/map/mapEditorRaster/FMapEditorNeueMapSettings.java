package lib.map.mapEditorRaster;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class FMapEditorNeueMapSettings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FMapEditorNeueMapSettings(MapEditorModel mem) {

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Map Settings");

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		// SETTINGS
		// Name
		JLabel lName = new JLabel("Name");
		gbc.gridy = 0;
		gbc.gridy = 0;
		add(lName, gbc);
		JTextField tName = new JTextField("New Map");
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		add(tName, gbc);
		// Breite
		JLabel lBreite = new JLabel("Breite");
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		add(lBreite, gbc);
		JSpinner sBreite = new JSpinner(new SpinnerNumberModel(10, 10, 100, 1));
		gbc.gridx = 1;
		add(sBreite, gbc);
		// Hoehe
		JLabel lHoehe = new JLabel("Höhe");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lHoehe, gbc);
		JSpinner sHoehe = new JSpinner(new SpinnerNumberModel(10, 10, 100, 1));
		gbc.gridx = 1;
		add(sHoehe, gbc);

		// MAP ERSTELLEN
		JButton neueMap = new JButton("Map erstellen");
		gbc.gridx = 0;
		gbc.gridy = 4;
		neueMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mem.setMapName(tName.getText());
				mem.initWelt((int) sBreite.getValue(), (int) sHoehe.getValue());
				FMapEditor m = new FMapEditor(mem);
				m.requestFocus();
				dispose();
			}
		});

		add(neueMap, gbc);

		// ABBRECHEN
		JButton bAbbrechen = new JButton("Abbrechen");
		bAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FMapEditorStarter s = new FMapEditorStarter(mem);
				s.requestFocus();
				dispose();
			}
		});

		gbc.gridx = 1;
		add(bAbbrechen, gbc);

		pack();
		setSize(400, 300);

	}

}
