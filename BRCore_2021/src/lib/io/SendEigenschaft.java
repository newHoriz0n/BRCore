package lib.io;

public class SendEigenschaft {

	private String bezeichner;
	private EObjektTyp typ;
	private Object value;

	public SendEigenschaft(String bezeichner, EObjektTyp typ, Object value) {
		this.bezeichner = bezeichner;
		this.typ = typ;
		this.value = value;
	}

	public String getBezeichner() {
		return bezeichner;
	}

	public EObjektTyp getTyp() {
		return typ;
	}

	public Object getValue() {
		return value;
	}
}
