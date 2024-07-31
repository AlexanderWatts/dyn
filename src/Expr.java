
import java.util.List;

/***
 * This class is automatically generated from tools/GenerateAST.java
 * Note: The following could be hand written but the code structure is
 * identical for both expressions and statements and can help save time
*/
public abstract class Expr {
	public interface Visitor<R> {
		public R visit(Assign expr);
		public R visit(Binary expr);
		public R visit(Grouping expr);
		public R visit(Unary expr);
		public R visit(Logical expr);
		public R visit(Literal expr);
		public R visit(Variable expr);
	}

	public abstract <R> R accept(Visitor<R> visitor);

	public static class Assign extends Expr {
		private final Token name;
		private final Expr value;

		public Assign(Token name, Expr value) {
			this.name = name;
			this.value = value;
		}

		public Token getName() {
			return this.name;
		}

		public Expr getValue() {
			return this.value;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.name);
			stringBuilder.append(" ");
			stringBuilder.append(this.value);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Binary extends Expr {
		private final Expr left;
		private final Token operator;
		private final Expr right;

		public Binary(Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		public Expr getLeft() {
			return this.left;
		}

		public Token getOperator() {
			return this.operator;
		}

		public Expr getRight() {
			return this.right;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.left);
			stringBuilder.append(" ");
			stringBuilder.append(this.operator);
			stringBuilder.append(" ");
			stringBuilder.append(this.right);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Grouping extends Expr {
		private final Expr expression;

		public Grouping(Expr expression) {
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

	public static class Unary extends Expr {
		private final Token operator;
		private final Expr right;

		public Unary(Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}

		public Token getOperator() {
			return this.operator;
		}

		public Expr getRight() {
			return this.right;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.operator);
			stringBuilder.append(" ");
			stringBuilder.append(this.right);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Logical extends Expr {
		private final Expr left;
		private final Token operator;
		private final Expr right;

		public Logical(Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		public Expr getLeft() {
			return this.left;
		}

		public Token getOperator() {
			return this.operator;
		}

		public Expr getRight() {
			return this.right;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.left);
			stringBuilder.append(" ");
			stringBuilder.append(this.operator);
			stringBuilder.append(" ");
			stringBuilder.append(this.right);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Literal extends Expr {
		private final Object value;

		public Literal(Object value) {
			this.value = value;
		}

		public Object getValue() {
			return this.value;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.value);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Variable extends Expr {
		private final Token name;

		public Variable(Token name) {
			this.name = name;
		}

		public Token getName() {
			return this.name;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(this.name);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}
}
