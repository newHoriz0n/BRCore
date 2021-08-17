package configs.easyStrategy.game;

public class RessourcenAbbau {

	private Ressource r;	// 
	private double einsatz;	// in %
	
	public RessourcenAbbau(Ressource r) {
		this.r = r;
		this.einsatz = 1;
	}
	
	public void setEinsatz(double einsatz) {
		this.einsatz = einsatz;
	}
	
	public double getEinsatz() {
		return einsatz;
	}
	
	public Ressource getRessource() {
		return r;
	}
	
	
	
}
