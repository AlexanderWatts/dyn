package scanner;

import java.util.ArrayList;
import java.util.List;

import token.Token;

public class Scanner {
	private List<Token> tokens = new ArrayList<>();
	private final String source;

	public Scanner(String source) {
		this.source = source;
	}

	public List<Token> scan() {
		return tokens;
	}

	public void printTokens() {
		System.out.println("Scanned tokens---");

		for (Token token : tokens) {
			System.out.println(token.toString());	
		}

		System.out.println("---");
	}

	@Override
	public String toString() {
		return this.source;
	}
}

