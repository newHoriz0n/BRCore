package lib.map.mapEditorRaster;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

import lib.io.PBFileReadWriter;

public class FMapEditor extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FMapEditor(MapEditorModel m) {
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle(m.getMapName());
		
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
				System.out.println("Serialisiere...");
				try {
					PBFileReadWriter.writeStringToFile(m.getMap().toSendString(), m.getMapName() + ".med");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Serialisierung abgeschlossen.");
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}
			
		});
		
//		setUndecorated(true);
	}
	
}
