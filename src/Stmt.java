
import java.util.List;

/***
 * This class is automatically generated from tools/GenerateAST.java
 * Note: The following could be hand written but the code structure is
 * identical for both expressions and statements and can help save time
*/
public abstract class Stmt {
	public interface Visitor<R> {
		public R visit(Block stmt);
		public R visit(Expression stmt);
		public R visit(Function stmt);
		public R visit(If stmt);
		public R visit(While stmt);
		public R visit(Print stmt);
		public R visit(Return stmt);
		public R visit(Var stmt);
	}

	public abstract <R> R accept(Visitor<R> visitor);

	public static class Block extends Stmt {
		private final List<Stmt> statements;

		public Block(List<Stmt> statements) {
			this.statements = statements;
		}

		public List<Stmt> getStatements() {
			return this.statements;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Stmt Block ");
			stringBuilder.append(this.statements);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

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

			stringBuilder.append("Stmt Expression ");
			stringBuilder.append(this.expression);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Function extends Stmt {
		private final Token name;
		private final List<Token> params;
		private final List<Stmt> body;

		public Function(Token name, List<Token> params, List<Stmt> body) {
			this.name = name;
			this.params = params;
			this.body = body;
		}

		public Token getName() {
			return this.name;
		}

		public List<Token> getParams() {
			return this.params;
		}

		public List<Stmt> getBody() {
			return this.body;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Stmt Function ");
			stringBuilder.append(this.name);
			stringBuilder.append(" ");
			stringBuilder.append(this.params);
			stringBuilder.append(" ");
			stringBuilder.append(this.body);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class If extends Stmt {
		private final Expr condition;
		private final Stmt thenBranch;
		private final Stmt elseBranch;

		public If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
			this.condition = condition;
			this.thenBranch = thenBranch;
			this.elseBranch = elseBranch;
		}

		public Expr getCondition() {
			return this.condition;
		}

		public Stmt getThenBranch() {
			return this.thenBranch;
		}

		public Stmt getElseBranch() {
			return this.elseBranch;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Stmt If ");
			stringBuilder.append(this.condition);
			stringBuilder.append(" ");
			stringBuilder.append(this.thenBranch);
			stringBuilder.append(" ");
			stringBuilder.append(this.elseBranch);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class While extends Stmt {
		private final Expr condition;
		private final Stmt body;

		public While(Expr condition, Stmt body) {
			this.condition = condition;
			this.body = body;
		}

		public Expr getCondition() {
			return this.condition;
		}

		public Stmt getBody() {
			return this.body;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Stmt While ");
			stringBuilder.append(this.condition);
			stringBuilder.append(" ");
			stringBuilder.append(this.body);
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

			stringBuilder.append("Stmt Print ");
			stringBuilder.append(this.expression);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Return extends Stmt {
		private final Token keyword;
		private final Expr expression;

		public Return(Token keyword, Expr expression) {
			this.keyword = keyword;
			this.expression = expression;
		}

		public Token getKeyword() {
			return this.keyword;
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

			stringBuilder.append("Stmt Return ");
			stringBuilder.append(this.keyword);
			stringBuilder.append(" ");
			stringBuilder.append(this.expression);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}

	public static class Var extends Stmt {
		private final Token name;
		private final Expr initialiser;

		public Var(Token name, Expr initialiser) {
			this.name = name;
			this.initialiser = initialiser;
		}

		public Token getName() {
			return this.name;
		}

		public Expr getInitialiser() {
			return this.initialiser;
		}

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Stmt Var ");
			stringBuilder.append(this.name);
			stringBuilder.append(" ");
			stringBuilder.append(this.initialiser);
			stringBuilder.append(" ");

			return stringBuilder.toString();
		}

	}
}
