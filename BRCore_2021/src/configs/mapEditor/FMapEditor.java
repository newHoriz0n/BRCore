package configs.mapEditor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FMapEditor extends JFrame {

	private MapEditorModel m;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FMapEditor(int breite, int hoehe) {
		
		this.m = new MapEditorModel(breite, hoehe);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		
		// MainPanel
		PMapEditorPanel panel = new PMapEditorPanel(m); 
		setContentPane(panel);
		
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
//				System.out.println("Serialisiere...");
				m.erzeugeSaveFile();
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}
			
		});
		
//		setUndecorated(true);
	}
	
}
