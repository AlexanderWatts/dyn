import java.util.List;

public class Parser {
	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public void parse() {
		System.out.println("Parsing...");
	}

	private boolean match(TokenType... tokenTypes) {

		for (TokenType tokenType : tokenTypes) {
			if (check(tokenType)) {
				getCurrentTokenAndAdvance();
				return true;
			}
		}

		return false;
	}

	private boolean check(TokenType tokenType) {
		if (isAtEnd()) {
			return false;
		}

		return getCurrentToken().getType() == tokenType;
	}

	private Token getCurrentTokenAndAdvance() {
		if (!isAtEnd()) {
			current++;
		}

		return getPreviousToken();
	}

	private Token getPreviousToken() {
		return tokens.get(current - 1);
	}

	private boolean isAtEnd() {
		return getCurrentToken().getType() == TokenType.EOF;
	}


	private Token getCurrentToken() {
		return tokens.get(current);
	}
}

