package configs.easyStrategy;

import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.model.ObjektVerwaltung;
import lib.view.FocusOnObject_Betrachter;
import lib.view.FocusOnPerson_Betrachter;
import lib.view.OV_ViewContainer;

public class EasyStrategyMain {

	public static void main (String [] args ) {
		
		FocusOnPerson_Betrachter b = new FocusOnPerson_Betrachter(1000, 1000);
		
		ObjektVerwaltung ov = new ObjektVerwaltung(b);
		
		OV_Controller oc = new OV_Controller(ov);
		
		OV_ViewContainer v = new OV_ViewContainer(ov, oc);
		
		OV_MainFrame mf = new OV_MainFrame(ov, v);
		
		mf.requestFocus();
	}
	
}
