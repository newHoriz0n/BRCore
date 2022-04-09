package configs.mapEditor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FMapEditorStarter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FMapEditorStarter() {
		
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
				FMapEditorNeueMapSettings n = new FMapEditorNeueMapSettings();
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
	
	public static void main(String [] args) {
		FMapEditorStarter mes = new FMapEditorStarter();
		mes.requestFocus();
	}

	
}