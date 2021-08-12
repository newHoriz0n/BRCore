package lib.exe;

import javax.swing.JFrame;
import lib.view.OV_ViewContainer;

public class OV_MainFrame extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OV_MainFrame(OV_ViewContainer v) {
		add(v);
		
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		System.out.println("Init MainFrame finished");
	}

	
}
