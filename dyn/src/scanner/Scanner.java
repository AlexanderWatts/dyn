package src.scanner;

import java.util.ArrayList;
import java.util.List;

import src.Dyn;
import src.token.TokenType;
import src.token.Token;

public class Scanner {
	private List<Token> tokens = new ArrayList<>();
	private final String source;
	private int start = 0;
	private int current = 0;
	private int line = 1;

	public Scanner(String source) {
		this.source = source;
	}

	public List<Token> scan() {
		while(!isAtEndOfFile()) {
			start = current;

			analyseCharacter();
		}

		addToken(TokenType.EOF);
		return tokens;
	}

	private void analyseCharacter() {
		char currentCharacter = getCurrentCharacterAndAdvance();

		switch (currentCharacter) {
			case '(': addToken(TokenType.LEFT_PAREN); break;
			case ')': addToken(TokenType.RIGHT_PAREN); break;
			case '{': addToken(TokenType.LEFT_BRACE); break;
			case '}': addToken(TokenType.RIGHT_BRACE); break;
			case ',': addToken(TokenType.COMMA); break;
			case '.': addToken(TokenType.DOT); break;
			case '*': addToken(TokenType.STAR); break;
			case '+': addToken(TokenType.PLUS); break;
			case '-': addToken(TokenType.MINUS); break;
			case ';': addToken(TokenType.SEMICOLON); break;
			default: 
				Dyn.error(line, "Unexpected character");
			break;
		}
	}

	private char getCurrentCharacterAndAdvance() {
		return source.charAt(current++);
	}

	private boolean isAtEndOfFile() {
		return current >= source.length();	
	}

	public void printTokens() {
		System.out.println("Scanned tokens---");

		for (Token token : tokens) {
			System.out.println(token.toString());	
		}

		System.out.println("---");
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		String lexeme = source.substring(start, current);
		tokens.add(new Token(type, lexeme, literal, line));
	}

	@Override
	public String toString() {
		return this.source;
	}
}

