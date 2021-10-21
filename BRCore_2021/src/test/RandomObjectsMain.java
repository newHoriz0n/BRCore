package test;

import lib.ctrl.OV_KeyHandler;
import lib.ctrl.OV_MouseHandler;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.view.Betrachter;
import lib.view.Betrachter_FocusPerson;
import lib.view.OV_ViewContainer;

public class RandomObjectsMain {

	public static void main(String[] args) {

		RandomObjectsModel m = new RandomObjectsModel();
		Betrachter b = new Betrachter_FocusPerson(50000, 50000);
		m.setBetrachter(b);

		RandomObjectsGUICtrl ctrl = new RandomObjectsGUICtrl(m);
		
		OV_Controller tc = new OV_Controller(m, ctrl);
		tc.addKeyHandler((OV_KeyHandler) b, "Betrachter");
		tc.addMouseHandler((OV_MouseHandler) b, "Betrachter");

		m.setController(tc);
		
		OV_ViewContainer v = new OV_ViewContainer(m.getObjektVerwaltung(), tc);
		tc.setViewContainer(v);

		OV_MainFrame mf = new OV_MainFrame(v);
		mf.addKeyListener(tc);

		mf.requestFocus();
	}

}
