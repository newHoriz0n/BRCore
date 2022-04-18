package lib.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface für Objekte die in File geschrieben werden können
 * 
 * @author paulb
 *
 */
public interface Sendbares {

	public abstract List<SendEigenschaft> getProperties();

	public abstract String getBezeichner();

	@SuppressWarnings("unchecked")
	public default String toSendString() {

		String out = "<";
		out += EObjektTyp.OBJECT.toString() + ":";
		out += getBezeichner() + ":";

		List<SendEigenschaft> eigenschaften = getProperties();

		for (int i = 0; i < eigenschaften.size(); i++) {

			if (eigenschaften.get(i).getTyp().equals(EObjektTyp.OBJECT)) {
				out += getString(eigenschaften.get(i).getValue());
			} else {

				out += "<";
				out += eigenschaften.get(i).getTyp().toString() + ":";
				out += eigenschaften.get(i).getBezeichner() + ":";

				if (eigenschaften.get(i).getTyp().equals(EObjektTyp.INTEGER)) {
					out += ((Integer) (eigenschaften.get(i).getValue())).toString();
				} else if (eigenschaften.get(i).getTyp().equals(EObjektTyp.DOUBLE)) {
					out += ((Double) (eigenschaften.get(i).getValue())).toString();
				} else if (eigenschaften.get(i).getTyp().equals(EObjektTyp.LONG)) {
					out += ((Long) (eigenschaften.get(i).getValue())).toString();
				} else if (eigenschaften.get(i).getTyp().equals(EObjektTyp.STRING)) {
					out += ((String) (eigenschaften.get(i).getValue())).toString();
				} else

				if (eigenschaften.get(i).getTyp().equals(EObjektTyp.MATRIX)) {
					Object[][] value = (Object[][]) eigenschaften.get(i).getValue();
					if (value.length > 0) {
						out += "<";
						for (int k = 0; k < value[0].length; k++) {
							out += "<";
							if (value[0].length > 0) {
								for (int j = 0; j < value.length; j++) {
									out += getString(value[j][k]);
								}
							}
							out += ">";
						}
						out += ">";
					}
				} else

				if (eigenschaften.get(i).getTyp().equals(EObjektTyp.LIST)) {
					List<Object> value = (List<Object>) eigenschaften.get(i).getValue();
					// out += "<";
					for (int j = 0; j < value.size(); j++) {
						out += getString(value.get(j));
					}
					// out += ">";
				}

				out += ">";
			
			}

		}

		out += ">";

		return out;

	}

	public static String getString(Object value) {

		if (value.getClass().equals(Integer.class)) {
			return "<" + ((Integer) (value)).toString() + ">";
		}
		if (value.getClass().equals(Double.class)) {
			return "<" + ((Double) (value)).toString() + ">";
		}
		if (value.getClass().equals(Long.class)) {
			return "<" + ((Long) (value)).toString() + ">";
		}
		if (value.getClass().equals(String.class)) {
			return "<" + ((String) (value)).toString() + ">";
		}
		if (value instanceof Sendbares) {
			return ((Sendbares) (value)).toSendString();
		}
		return "?";
	}

	public static Sendbares extractObject(String s) {

		List<SendEigenschaft> ses = new ArrayList<>();

		int tiefe = 0;
		int abschnitt = 0;
		String bezeichner = "";
		String inhalt = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '<') {
				tiefe++;
			}
			if (tiefe >= 2) {
				inhalt += s.charAt(i);
			}
			if (s.charAt(i) == '>') {
				tiefe--;
				if (tiefe == 1) {
					ses.add(extractSendEigenschaft(inhalt));
					inhalt = "";
				}
			} else {
				if (tiefe == 1) {
					if (s.charAt(i) != ':') {
						if (abschnitt == 1) {
							bezeichner += s.charAt(i);
						}
					} else {
						abschnitt++;
					}
				}
			}

		}

		return new RawSendbares(bezeichner, ses);

	}

	public static SendEigenschaft extractSendEigenschaft(String s) {

		int index = 0;
		String typ = "";
		String bezeichner = "";
		Object o = null;

		do {
			if (s.charAt(index) == '<') {
				// Lese Typ:
				while (s.charAt(++index) != ':') {
					typ += s.charAt(index);
				}
				// Lese Bezeichner:
				while (s.charAt(++index) != ':') {
					bezeichner += s.charAt(index);
				}
				// Lese Inhalt:
				if (typ.equals(EObjektTyp.OBJECT.toString())) {
					String inhalt = "";
					int klammern = 0;
					while (++index < s.length() - 1) {
						inhalt += s.charAt(index);
						if (s.charAt(index) == '<') {
							klammern++;
						} else if (s.charAt(index) == '>') {
							klammern--;
						}
						if (klammern == -1) {
							break;
						}
					}
					// System.out.println(inhalt);
					o = new SendEigenschaft(bezeichner, EObjektTyp.OBJECT, new RawSendbares(bezeichner, extractObject(inhalt).getProperties()));
				} else if (typ.equals(EObjektTyp.MATRIX.toString())) {
					String inhalt = "";
					int klammern = 0;
					while (++index < s.length() - 1) {
						inhalt += s.charAt(index);
						if (s.charAt(index) == '<') {
							klammern++;
						} else if (s.charAt(index) == '>') {
							klammern--;
						}
						if (klammern == 0) {
							break;
						}
					}
					// System.out.println(inhalt);
					o = extractMatrix(inhalt);

				} else if (typ.equals(EObjektTyp.LIST.toString())) {
					String inhalt = "";
					int klammern = 0;
					while (++index < s.length() - 1) {
						inhalt += s.charAt(index);
						if (s.charAt(index) == '<') {
							klammern++;
						} else if (s.charAt(index) == '>') {
							klammern--;
						}
						if (klammern == -1) {
							break;
						}
					}
					// System.out.println(inhalt);
					o = extractListe(inhalt);
				} else if (typ.equals(EObjektTyp.STRING.toString())) {
					String inhalt = "";
					int klammern = 0;
					while (++index < s.length() - 1) {
						inhalt += s.charAt(index);
						if (s.charAt(index) == '<') {
							klammern++;
						} else if (s.charAt(index) == '>') {
							klammern--;
						}
						if (klammern == -1) {
							break;
						}
					}
					// System.out.println(inhalt);
					o = inhalt;
				}

			}
			index++;
		} while (index < s.length());

		return new SendEigenschaft(bezeichner, EObjektTyp.valueOf(typ), o);

	}

	public static List<String> extractListe(String inhalt) {
		int tiefe = 0;
		List<String> os = new ArrayList<>();
		String current = "";
		for (int i = 0; i < inhalt.length(); i++) {
			current += inhalt.charAt(i);
			if (inhalt.charAt(i) == '<') {
				tiefe++;
			} else if (inhalt.charAt(i) == '>') {
				tiefe--;
				System.out.println(tiefe);
				if (tiefe == 0) {
					os.add(new String(current));
					current = "";
				}
			}
		}
		return os;
	}

	public static Object[][] extractMatrix(String inhalt) {

		Object[][] matrix = null;
		int tiefe = 0;
		List<Object> os = new ArrayList<>();
		List<Object[]> oas = new ArrayList<>();
		String current = "";
		for (int i = 0; i < inhalt.length(); i++) {
			if (inhalt.charAt(i) == '<') {
				tiefe++;
			} else if (inhalt.charAt(i) == '>') {
				if (tiefe == 3) {
					os.add(new String(current));
					current = "";
				} else if (tiefe == 2) {
					oas.add(os.toArray());
					os.clear();
				} else if (tiefe == 1) {
					matrix = new Object[oas.size()][];
					for (int j = 0; j < oas.size(); j++) {
						matrix[j] = oas.get(j);
					}
				}
				tiefe--;
			} else {
				current += inhalt.charAt(i);
			}

		}

		return matrix;

	}

}
