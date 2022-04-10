package lib.io;

import java.util.List;

/**
 * Interface für Objekte die in File geschrieben werden können
 * 
 * @author paulb
 *
 */
public interface Sendbares {

	public abstract List<SendEigenschaft> getProperties();

	public abstract String getName();

	@SuppressWarnings("unchecked")
	public default String toSendString() {

		String out = "<";
		out += getName() + ":";

		List<SendEigenschaft> eigenschaften = getProperties();

		for (int i = 0; i < eigenschaften.size(); i++) {

			out += "<";
			out += eigenschaften.get(i).getBezeichner() + ":";
			out += eigenschaften.get(i).getTyp().toString() + ":";

			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.INTEGER)) {
				out += ((Integer) (eigenschaften.get(i).getValue())).toString();
			}
			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.DOUBLE)) {
				out += ((Double) (eigenschaften.get(i).getValue())).toString();
			}
			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.LONG)) {
				out += ((Long) (eigenschaften.get(i).getValue())).toString();
			}
			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.STRING)) {
				out += ((String) (eigenschaften.get(i).getValue())).toString();
			}

			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.MATRIX)) {
				Object[][] value = (Object[][]) eigenschaften.get(i).getValue();
				if (value.length > 0) {
					out += "<";
					for (int j = 0; j < value.length; j++) {
						out += "<";
						if (value[0].length > 0) {
							for (int k = 0; k < value[0].length; k++) {
								out += "<" + getString(value[j][k]) + ">";
							}
						}
						out += ">";
					}
					out += ">";
				}
			}

			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.LIST)) {
				List<Object> value = (List<Object>) eigenschaften.get(i).getValue();
				out += "<";
				for (int j = 0; j < value.size(); j++) {
					out += "<" + getString(value.get(j)) + ">";
				}
				out += ">";
			}

			out += ">";

		}

		return out;

	}

	public static String getString(Object value) {

		if (value.getClass().equals(Integer.class)) {
			return ((Integer) (value)).toString();
		}
		if (value.getClass().equals(Double.class)) {
			return ((Double) (value)).toString();
		}
		if (value.getClass().equals(Long.class)) {
			return ((Long) (value)).toString();
		}
		if (value.getClass().equals(String.class)) {
			return ((String) (value)).toString();
		}
		if (value instanceof Sendbares) {
			return ((Sendbares) (value)).toSendString();
		}
		return "?";
	}

}
