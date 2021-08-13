package lib.model;

public class ObjectIDSingleton {

	private static long id;
	
	private ObjectIDSingleton() {
		
	}
	
	public static long getNextID() {
		id++;
		return id;
	}
}
