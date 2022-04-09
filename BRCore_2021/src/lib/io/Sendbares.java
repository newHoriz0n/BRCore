package lib.io;

import java.util.HashMap;

/**
 * Interface für Objekte die in File geschrieben werden können
 * @author paulb
 *
 */
public interface Sendbares {

	public abstract HashMap<String, Object> getProperties();
	
	public abstract String getName();
	
	public default String toSendString() {
		
		String out = "<";
		out += getName() + ":";
		HashMap<String, Object> properties = getProperties();
		for (String key : properties.keySet()) {
			
			out += "<";
			out += key + ":";
			
			if(properties.get(key) instanceof Integer) {
				out += "INTEGER:" + properties.get(key).toString();
			} else if(properties.get(key) instanceof Double) {
				out += "DOUBLE:" + properties.get(key).toString();
			} else if(properties.get(key) instanceof Long) {
				out += "LONG:" + properties.get(key).toString();
			} else if(properties.get(key) instanceof String) {
				out += "DOUBLE:" + properties.get(key);
			} else if(properties.get(key) instanceof Sendbares) {
				out += "OBJECT:" + ((Sendbares)(properties.get(key))).toSendString();
			}
			
			out+= ">";
		}
		return out;
		
	}
	
}
