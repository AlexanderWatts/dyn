import java.util.ArrayList;
import java.util.List;

public class Parser {
	private static class ParseError extends RuntimeException {

	}

	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Stmt> parse() {
		List<Stmt> statements = new ArrayList<>();

		while (!isAtEnd()) {
			statements.add(declaration());
		}

		return statements;
	}

	private Stmt declaration() {
		if (match(TokenType.VAR)) {
			return varDecl();
		}

		return statement();
	}

	private Stmt varDecl() {
		Token identifier = checkAndAdvance(TokenType.IDENTIFIER, "Expect variable name");

		Expr initialiser = null;

		if (match(TokenType.EQUAL)) {
			initialiser = expression();
		}

		checkAndAdvance(TokenType.SEMICOLON, "Expect ';' after variable declaration");
		return new Stmt.Var(identifier, initialiser);
	}

	private Stmt statement() {
		if (match(TokenType.PRINT)) {
			return printStmt();
		}

		return exprStmt();
	}

	private Stmt printStmt() {
		Expr expr = expression();
		checkAndAdvance(TokenType.SEMICOLON, "Expect ';' after expression");

		return new Stmt.Print(expr);
	}

	private Stmt exprStmt() {
		Expr expr = expression();	
		checkAndAdvance(TokenType.SEMICOLON, "Expect ';' after expression");

		return new Stmt.Expression(expr);
	}

	public Expr expression() {
		return equality();
	}

	/**
	 * Equality grammar production
	 * equality = comparison (("==" | "!=") comparison)* ;
	 */
	private Expr equality() {
		Expr expr = comparison();

		if (match(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL)) {
			Token operator = getPreviousToken();
			Expr right = comparison();

			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	/**
	 * Comparison grammar production
	 * comparison = term (("<" | "<=" | ">" | ">=") term)* ;
	 */
	private Expr comparison() {
		Expr expr = term();

		if (match(
			TokenType.LESS,
			TokenType.LESS_EQUAL,
			TokenType.GREATER,
			TokenType.GREATER_EQUAL
		)) {
			Token operator = getPreviousToken();
			Expr right = term();

			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	/**
	 * Term grammar production
	 * term = factor (("+" | "-") factor)* ;
	 */
	private Expr term() {
		Expr expr = factor();

		if (match(TokenType.PLUS, TokenType.MINUS)) {
			Token operator = getPreviousToken();
			Expr right = factor();

			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
		
	}

	/**
	 * Factor grammar production
	 * factor = unary (("*" | "/") unary)* ;
	 */
	private Expr factor() {
		Expr expr = unary();
 
		if (match(TokenType.STAR, TokenType.SLASH)) {
			Token operator = getPreviousToken();
			Expr right = unary();

			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	/**
	 * Unary grammar production
	 * unary = ("-" | "!") unary | primary ;
	 */
	private Expr unary() {
		if (match(TokenType.MINUS, TokenType.BANG)) {
			Token operator = getPreviousToken();
			Expr right = unary();

			return new Expr.Unary(operator, right);
		}

		return primary();
	}

	/**
	 * Primary grammar production
	 * primary = STRING | NUMBER | "true" | "false" | "nil" | "(" expression ")" ;
	 */
	private Expr primary() {
		if (match(TokenType.IDENTIFIER)) {
			return new Expr.Variable(getPreviousToken());
		}

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
			checkAndAdvance(TokenType.RIGHT_PAREN, "Expected ')' after expression");

			return new Expr.Grouping(expr);
		}
		
		throw error(getCurrentToken(), "Expression expected");
	}
 
	private Token checkAndAdvance(TokenType tokenType, String errorMessage) {
		if (check(tokenType)) {
			return getCurrentTokenAndAdvance();
		}

		throw error(getCurrentToken(), errorMessage); 
	}

	private ParseError error(Token token, String errorMessage) {
		Dyn.error(token, errorMessage);	
		return new ParseError();
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

