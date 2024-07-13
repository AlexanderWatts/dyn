package scanner;

public class Scanner {
	private final String source;

	public Scanner(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return this.source;
	}
}

