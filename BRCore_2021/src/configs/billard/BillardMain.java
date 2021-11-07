package configs.billard;

import configs.billard.table.Billard;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.view.Betrachter_FreierVogel;
import lib.view.OV_ViewContainer;

public class BillardMain {

	public static void main(String[] args) {

		Billard bi = new Billard();
		Betrachter_FreierVogel b = new Betrachter_FreierVogel();
		bi.setBetrachter(b);

		B_GUI_Ctrl_Main gc_main = new B_GUI_Ctrl_Main(bi);
		b.setViewPort(gc_main.getWidth() / 2, gc_main.getHeight() / 2);

		OV_Controller oc = new OV_Controller(bi, gc_main);
		oc.showMouseCoords(true);

		bi.setController(oc);

		OV_ViewContainer v = new OV_ViewContainer(bi.getObjektVerwaltung(), oc);
		oc.setViewContainer(v);

		OV_MainFrame mf = new OV_MainFrame(v);

		mf.requestFocus();
	}

}
