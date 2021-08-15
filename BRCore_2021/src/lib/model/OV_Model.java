package lib.model;

import lib.ctrl.OV_Controller;
import lib.model.listener.EUpdateTopic;
import lib.view.Betrachter;

public abstract class OV_Model {

	protected ObjektVerwaltung ov;
	protected OV_Controller oc;
	
	public OV_Model() {
		this.ov = new ObjektVerwaltung();
	}
	
	public void setBetrachter(Betrachter b) {
		this.ov.setBetrachter(b);
	}

	public ObjektVerwaltung getObjektVerwaltung() {
		return ov;
	}
	
	public void setController(OV_Controller oc) {
		this.oc = oc;
		ov.addUpdateListener(EUpdateTopic.RELEVANZEN, oc);
		notifyControllerSet();
	}
	
	protected abstract void notifyControllerSet();


}
