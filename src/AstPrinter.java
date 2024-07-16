
public class AstPrinter implements Expr.Visitor<String> {
	public String print(Expr root) {
		return root.accept(this);
	}

	@Override
	public String visit(Expr.Binary expr) {
		Token operator = expr.getOperator();
		Expr left = expr.getLeft();
		Expr right = expr.getRight();

		return parenthesise(operator.getLexeme(), left, right);
	}

	@Override
	public String visit(Expr.Grouping expr) {
		Expr expression = expr.getExpression();

		return parenthesise("group", expression);
	}

	@Override
	public String visit(Expr.Unary expr) {
		Token operator = expr.getOperator();
		Expr right = expr.getRight();

		return parenthesise(operator.getLexeme(), right);
	}

	@Override
	public String visit(Expr.Literal expr) {
		return expr
			.getValue()
			.toString();
	}

	public String parenthesise(String lexeme, Expr...exprs) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("(").append(lexeme);

		for (Expr expr : exprs) {
			stringBuilder.append(" ");
			stringBuilder.append(expr.accept(this));
		}

		stringBuilder.append(")");

		return stringBuilder.toString();
	}
}

