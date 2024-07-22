
import java.util.List;

/***
 * This class is automatically generated from tools/GenerateAST.java
 * Note: The following could be hand written but the code structure is
 * identical for both expressions and statements and can help save time
*/
public abstract class Stmt {
	public interface Visitor<R> {
		public R visit(Expression stmt);
		public R visit(Print stmt);
	}

	public abstract <R> R accept(Visitor<R> visitor);

	public static class Expression extends Stmt {
		private final Expr expression;

		public Expression(Expr expression) {
			this.expression = expression;
		}

		public Expr getExpression() {
			return this.expression;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.expression);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Print extends Stmt {
		private final Expr expression;

		public Print(Expr expression) {
			this.expression = expression;
		}

		public Expr getExpression() {
			return this.expression;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.expression);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}
}
