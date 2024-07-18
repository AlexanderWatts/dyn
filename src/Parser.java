import java.util.List;

public class Parser {
	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Expr parse() {
		System.out.println("Parsing...");
		return expression();
	}

	public Expr expression() {
		return primary();
	}

	/**
	 * Primary grammar production
	 * primary = STRING | NUMBER | "true" | "false" | "nil" | "(" expression ")" ;
	 */
	private Expr primary() {
		if (match(TokenType.STRING, TokenType.NUMBER)) {
			return new Expr.Literal(getPreviousToken().getLiteral());
		}

		if (match(TokenType.TRUE)) {
			return new Expr.Literal(true);
		}

		if (match(TokenType.FALSE)) {
			return new Expr.Literal(false);
		}

		if (match(TokenType.NIL)) {
			return new Expr.Literal(null);
		}

		if (match(TokenType.LEFT_PAREN)) {
			Expr expr = expression();

			System.out.println("current " + getCurrentToken());
			System.out.println("prev " + getPreviousToken());

			if (!check(TokenType.RIGHT_PAREN)) {
				throw new Error("Expression missing ')'");	
			}

			return new Expr.Grouping(expr);
		}
		
		throw new Error("Invalid expression");
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

