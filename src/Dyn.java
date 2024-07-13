import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dyn {
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

		System.out.println("Output:");
		System.out.println(source);
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
		}
	}
}

