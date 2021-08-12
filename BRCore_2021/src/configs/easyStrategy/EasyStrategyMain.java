package configs.easyStrategy;

import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.model.ObjektVerwaltung;
import lib.view.FocusOnObject_Betrachter;
import lib.view.FocusOnPerson_Betrachter;
import lib.view.OV_ViewContainer;

public class EasyStrategyMain {

	public static void main (String [] args ) {
		
		FocusOnObject_Betrachter b = new FocusOnObject_Betrachter();
		
		ObjektVerwaltung ov = new ObjektVerwaltung(b);
		
		OV_Controller oc = new OV_Controller(ov);
		oc.showMouseCoords(true);
		
		OV_ViewContainer v = new OV_ViewContainer(ov, oc);
		oc.setViewer(v);
		
		OV_MainFrame mf = new OV_MainFrame(ov, v);
		
		mf.requestFocus();
	}
	
}
