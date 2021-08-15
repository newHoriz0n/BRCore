package configs.easyStrategy;

import configs.easyStrategy.game.EasyStrategy;
import configs.easyStrategy.gui.GUI_Ctrl_Main;
import configs.easyStrategy.gui.GUI_Ctrl_Main_Overlay;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.view.Betrachter_FreierVogel;
import lib.view.OV_ViewContainer;

public class EasyStrategyMain {

	public static void main(String[] args) {
		EasyStrategy es = new EasyStrategy(2);
		Betrachter_FreierVogel b = new Betrachter_FreierVogel();
		es.setBetrachter(b);

		GUI_Ctrl_Main gc_main = new GUI_Ctrl_Main(es);
		
		OV_Controller oc = new OV_Controller(es, gc_main);
		oc.showMouseCoords(true);
		
		es.setController(oc);
		
		oc.addOverLayGC(new GUI_Ctrl_Main_Overlay(es));
		
		OV_ViewContainer v = new OV_ViewContainer(es.getObjektVerwaltung(), oc);
		oc.setViewer(v);

		OV_MainFrame mf = new OV_MainFrame(v);

		mf.requestFocus();
	}

}
