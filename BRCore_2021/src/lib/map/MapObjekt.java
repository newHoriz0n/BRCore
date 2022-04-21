package lib.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lib.io.EObjektTyp;
import lib.io.SendEigenschaft;
import lib.io.Sendbares;

public class MapObjekt implements Sendbares {

	private String bezeichner;
	private Double[] position;
	private HashMap<String, String> eigenschaften;

	public MapObjekt(String bezeichner, double[] position, String[] eigenschaften) {
		this.bezeichner = bezeichner;
		this.position = new Double[] { position[0], position[1] };
		this.eigenschaften = new HashMap<>();
		for (String s : eigenschaften) {
			this.eigenschaften.put(s, "");
		}
	}

	@SuppressWarnings("unchecked")
	public MapObjekt(Sendbares rs) {
		
		this.bezeichner = (String) rs.getProperties().get(0).getValue();
		
		this.position = new Double[2];
		List<Double> ds = new ArrayList<>();
		for (Object o : (Object[])rs.getProperties().get(1).getValue()) {
			ds.add(Double.parseDouble((String)o));
		}
		for (int i = 0; i < position.length; i++) {
			position[i] = ds.get(i);
		}
		
		this.eigenschaften = (HashMap<String, String>)rs.getProperties().get(2).getValue();
	}

	public Double[] getPosition() {
		return position;
	}

	public HashMap<String, String> getEigenschaften() {
		return eigenschaften;
	}

	public void setEigenschaft(String key, String value) {
		eigenschaften.replace(key, value);
	}

	public String getTyp() {
		return bezeichner;
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		List<SendEigenschaft> ses = new ArrayList<SendEigenschaft>();

		ses.add(new SendEigenschaft("Bezeichner", EObjektTyp.STRING, bezeichner));
		ses.add(new SendEigenschaft("Position", EObjektTyp.ARRAY, position));
		ses.add(new SendEigenschaft("Eigenschaften", EObjektTyp.HASHMAP_STR_STR, eigenschaften));

		return ses;
	}

	@Override
	public String getBezeichner() {
		return "MapObjekt";
	}

}
