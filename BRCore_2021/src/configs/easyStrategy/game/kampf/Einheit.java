package configs.easyStrategy.game.kampf;

public class Einheit {
	
	private int oberflaeche;	// Wie viele Angriffsfläche wird geboten
	private int angriffsbreite;		// Wie viel Platz nimmt Einheit bei Angriff ein
	
	public Einheit(int groesse) {
		this.oberflaeche = groesse;
	}
	
	public int getGroesse() {
		return oberflaeche;
	}
	

}
