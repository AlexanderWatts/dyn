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
			default: break;
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
		tokens.add(new Token(type, "", literal, line));
	}

	@Override
	public String toString() {
		return this.source;
	}
}

