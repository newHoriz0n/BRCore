package configs.easyStrategy;

import configs.easyStrategy.game.EasyStrategy;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.view.FocusOnObject_Betrachter;
import lib.view.OV_ViewContainer;

public class EasyStrategyMain {

	public static void main(String[] args) {
		EasyStrategy es = new EasyStrategy(2);
		FocusOnObject_Betrachter b = new FocusOnObject_Betrachter();
		es.setBetrachter(b);

		OV_Controller oc = new OV_Controller(es);
		oc.showMouseCoords(true);
		
		OV_ViewContainer v = new OV_ViewContainer(es.getObjektVerwaltung(), oc);
		oc.setViewer(v);

		OV_MainFrame mf = new OV_MainFrame(v);

		mf.requestFocus();
	}

}
