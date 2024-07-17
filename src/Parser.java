import java.util.List;

public class Parser {
	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Expr parse() {
		System.out.println("Parsing...");
		return comparison();
	}

	/**
	 * Grammar production
	 * comparison = term (("<" | "<=" | ">=" | ">") term)* ;
	 */
	private Expr comparison() {
		Expr expr = term();

		if (isCurrentToken(
			TokenType.LESS,
			TokenType.LESS_EQUAL,
			TokenType.GREATER_EQUAL,
			TokenType.GREATER
		)) {
			Token operator = getCurrentTokenAndAdvance();
			Expr right = term();

			return new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	/**
	 * Grammar production
	 * term = factor (("+" | "-") factor)* ;
	 */
	private Expr term() {
		Expr expr = factor();

		if (isCurrentToken(TokenType.PLUS, TokenType.MINUS)) {
			Token operator = getCurrentTokenAndAdvance();
			Expr right = factor();

			return new Expr.Binary(expr, operator, right);
		}
		
		return expr;
	}

	/**
	 * Grammar production
	 * factor = unary (("*" | "/") unary)* ;
	 */
	private Expr factor() {
		Expr unary = unary();

		if (isCurrentToken(TokenType.STAR, TokenType.SLASH)) {
			Token operator = getCurrentTokenAndAdvance();	
			Expr right = unary();

			return new Expr.Binary(unary, operator, right);
		}

		return unary;
	}

	/**
	 * Grammar production
	 * unary = ("-" | "!") unary | primary ;
	 */
	private Expr unary() {
		if (isCurrentToken(TokenType.MINUS, TokenType.BANG)) {
			Token operator = getCurrentTokenAndAdvance();
			Expr right = unary();
			
			return new Expr.Unary(operator, right);
		}

		return primary();
	}

	/**
	 * Grammar production
	 * primary = STRING | NUMBER | "true" | "false" | "nil" | "(" expression ")" ;
	 */
	private Expr primary() {
		if (isCurrentToken(TokenType.STRING) || isCurrentToken(TokenType.NUMBER)) {
			Expr literal = new Expr.Literal(getCurrentTokenAndAdvance().getLiteral());

			return literal;
		}

		if (isCurrentToken(TokenType.FALSE)) {
			Expr literal = new Expr.Literal(false);
			getCurrentTokenAndAdvance();

			return literal;
		}

		if (isCurrentToken(TokenType.TRUE)) {
			Expr literal = new Expr.Literal(true);
			getCurrentTokenAndAdvance();

			return literal;
		}

		if (isCurrentToken(TokenType.NIL)) {
			Expr literal = new Expr.Literal(null);
			getCurrentTokenAndAdvance();

			return literal;
		}

		return new Expr.Literal(null);
	}

	private boolean isCurrentToken(TokenType...types) {
		for (TokenType type : types) {
			if (type == getCurrentToken().getType()) {
				return true;
			}
		}

		return false;
	}

	private Token getCurrentTokenAndAdvance() {
		if (!hasSeenAllTokens()) {
			current++;
		}

		return getPreviousToken(); 
	}

	private boolean hasSeenAllTokens() {
		if (getCurrentToken().getType() == TokenType.EOF) {
			return true;
		}

		return false;
	}


	private Token getPreviousToken() {
		return tokens.get(current - 1);
	}

	private Token getCurrentToken() {
		return tokens.get(current);
	}
}

