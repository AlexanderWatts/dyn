import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
	public static void main(String[] args) {
		defineAst("./src/", "Expr", Arrays.asList(
			"Assign: Token name, Expr value",
			"Binary : Expr left, Token operator, Expr right",
			"Grouping : Expr expression",
			"Unary : Token operator, Expr right",
			"Logical : Expr left, Token operator, Expr right",
			"Literal : Object value",
			"Variable: Token name"
		));

		defineAst("./src/", "Stmt", Arrays.asList(
			"Block : List<Stmt> statements",
			"Expression : Expr expression",
			"If : Expr condition, Stmt thenBranch, Stmt elseBranch",
			"While : Expr condition, Stmt body",
			"Print : Expr expression",
			"Var : Token name, Expr initialiser"
		));
	}

	private static void defineAst(String outputDir, String baseName, List<String> types) {
		String path = outputDir + baseName + ".java";
		
		try (PrintWriter printWriter = new PrintWriter(path, "UTF-8")) {
			printWriter.println();
			printWriter.println("import java.util.List;");
			printWriter.println();

			printWriter.println("/***");
			printWriter.println(" * This class is automatically generated from tools/GenerateAST.java");
			printWriter.println(" * Note: The following could be hand written but the code structure is");
			printWriter.println(" * identical for both expressions and statements and can help save time");
			printWriter.println("*/");
			printWriter.println("public abstract class " + baseName + " {");
			
			defineVisitor(printWriter, baseName, types);

			printWriter.println();
			printWriter.println("	public abstract <R> R accept(Visitor<R> visitor);");
			
			for (String type : types) {
				String className = type.split(":")[0].trim();
				String fields = type.split(":")[1].trim();
				
				defineType(printWriter, baseName, className, fields);
			}

			printWriter.println("}");
		} catch (Exception error) {
			System.out.println(error);
		}
	}

	private static void defineVisitor(PrintWriter printWriter, String baseName, List<String> types) {
		printWriter.println("	public interface Visitor<R> {");
		
		for (String type : types) {
			String typeName = type.split(":")[0].trim();
			printWriter.println("		public R visit(" + typeName + " " + baseName.toLowerCase() + ");");
		}

		printWriter.println("	}");
	}

	private static void defineType(PrintWriter printWriter, String baseName, String className, String fields) {
		String[] fieldList = fields.split(", ");

		printWriter.println();	
		printWriter.println("	public static class " + className + " extends " + baseName + " {");	

		for (String field : fieldList) {
			printWriter.println("		private final " + field + ";");
		}

		printWriter.println();	
		printWriter.println("		public " + className + "(" + fields + ") {");

		for (String field : fieldList) {
			String name = field.split(" ")[1];
			printWriter.println("			this." + name + " = " + name + ";");
		}

		printWriter.println("		}");

		printWriter.println();

		for (String field : fieldList) {
			String type = field.split(" ")[0];
			String name = field.split(" ")[1];

			printWriter.println("		public " + type + " get" + name.substring(0, 1).toUpperCase() + name.substring(1) + "() {");
			printWriter.println("			return this." + name + ";");
			printWriter.println("		}");

			printWriter.println();
		}

		printWriter.println("		@Override");
		printWriter.println("		public <R> R accept(Visitor<R> visitor) {");
		printWriter.println("			return visitor.visit(this);");	
		printWriter.println("		}");
		printWriter.println();

		printWriter.println("		@Override");
		printWriter.println("		public String toString() {");
		printWriter.println("			StringBuilder stringBuilder = new StringBuilder();");
		printWriter.println();

		printWriter.println("			stringBuilder.append(\"" + baseName + " " + className + " " + "\");");

		for (String field : fieldList) {
			String name = field.split(" ")[1];
			printWriter.println("			stringBuilder.append(this." + name + ");");
			printWriter.println("			stringBuilder.append(\" \");");
		}

		printWriter.println();
		printWriter.println("			return stringBuilder.toString();");	
		printWriter.println("		}");
		printWriter.println();

		printWriter.println("	}");
	}
}

