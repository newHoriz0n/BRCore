package lib.io;

import java.util.List;

public class RawSendbares implements Sendbares {

	private String name;
	private List<SendEigenschaft> eigenschaften;

	public RawSendbares(String name, List<SendEigenschaft> eigenschaften) {
		this.name = name;
		this.eigenschaften = eigenschaften;
	}

	@Override
	public List<SendEigenschaft> getProperties() {
		return eigenschaften;
	}

	@Override
	public String getBezeichner() {
		return name;
	}

}
