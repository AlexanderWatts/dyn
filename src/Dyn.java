import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Dyn {
	private static boolean hadError = false;

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.exit(64);
		} else if (args.length == 1) {
			runFile(args[0]);
		} else {
			repl();
		}
	}

	/**
	 * Run dyn from file
	 *
	 * @throws IOException
	 */
	private static void runFile(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String source = new String(bytes, Charset.defaultCharset());

		run(source);

		if (hadError) {
			System.exit(65);
		}
	}

	/**
	 * Run dyn interactively
	 * REPL (Read Evaluate Print Loop)
	 *
	 * Use Ctl-c or Ctl-d to exit
	 *
	 * @throws IOException
	 */
	private static void repl() throws IOException {
		System.out.println("Dyn REPL");

		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(input);

		for (;;) {
			System.out.print("> ");
			String line = bufferedReader.readLine();
			System.out.println(line);
			
			if (line == null) {
				break;
			}

			run(line);
		}
	}

	private static void run(String source) {
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scan();

		scanner.printTokens();
		
		Parser parser = new Parser(tokens);
		parser.parse();
	}

	public static void error(int line, String message) {
		report(line, "", message);	
	}

	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error " + where + ": " + message);

		hadError = true;
	}

	private static void astPrinterTest() {
		Expr.Literal literal = new Expr.Literal(123);

		Expr.Grouping grouping = new Expr.Grouping(new Expr.Literal("Hello"));

		Expr.Unary unary = new Expr.Unary(
			new Token(TokenType.BANG, "!", null, 1),
			new Expr.Literal(true)
		);
		
		Expr.Binary binary = new Expr.Binary(
			new Expr.Binary(
				new Expr.Literal(1),
				new Token(TokenType.MINUS, "-", null, 1),
				new Expr.Literal(1)
			),
			new Token(TokenType.PLUS, "+", null, 1),
			new Expr.Literal(2)
		);

		AstPrinter astPrinter = new AstPrinter();

		System.out.println(astPrinter.print(literal));
		System.out.println(astPrinter.print(binary));
		System.out.println(astPrinter.print(grouping));
		System.out.println(astPrinter.print(unary));
	}
}

