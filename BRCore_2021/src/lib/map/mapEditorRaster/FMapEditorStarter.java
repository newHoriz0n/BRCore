package lib.map.mapEditorRaster;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

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
		add(ladeMap, gbc);

		pack();
		setSize(400, 300);

	}

	public static void main(String[] args) {

		FeldTyp ffrei = new FeldTyp("Frei", "[ ]", Color.WHITE);
		FeldTyp fwand = new FeldTyp("Wand", "[#]", Color.DARK_GRAY);
		List<FeldTyp> feldTypen = new ArrayList<>();
		feldTypen.add(ffrei);
		feldTypen.add(fwand);

		MapEditorModel mem = new MapEditorModel(feldTypen);

		FMapEditorStarter mes = new FMapEditorStarter(mem);
		mes.requestFocus();
	}

}