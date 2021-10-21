package configs.randomBall;

import configs.randomBall.game.RandomBall;
import lib.ctrl.OV_Controller;
import lib.exe.OV_MainFrame;
import lib.view.Betrachter_FreierVogel;
import lib.view.OV_ViewContainer;

public class RandomBallMain {

	public static void main(String[] args) {

		RandomBall rb = new RandomBall();
		Betrachter_FreierVogel b = new Betrachter_FreierVogel();
		rb.setBetrachter(b);

		RB_GUI_Ctrl_Main gc_main = new RB_GUI_Ctrl_Main(rb);
		b.setViewPort(gc_main.getWidth() / 2, gc_main.getHeight() / 2);

		OV_Controller oc = new OV_Controller(rb, gc_main);
		oc.showMouseCoords(true);

		rb.setController(oc);

		OV_ViewContainer v = new OV_ViewContainer(rb.getObjektVerwaltung(), oc);
		oc.setViewContainer(v);

		OV_MainFrame mf = new OV_MainFrame(v);

		mf.requestFocus();
	}

}
