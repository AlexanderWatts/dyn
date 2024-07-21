
public class RuntimeError extends RuntimeException {
	private final Token token;

	public RuntimeError(Token token, String errorMessage) {
		super(errorMessage);
		this.token = token;
	}

	public Token getToken() {
		return token;
	}
}

