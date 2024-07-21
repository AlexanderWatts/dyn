
public class Interpreter implements Expr.Visitor<Object> {
	public Object evaluate(Expr root) {
		return root.accept(this);
	}

	@Override
	public Object visit(Expr.Binary expr) {
		Object left = evaluate(expr.getLeft());
		Object right = evaluate(expr.getRight());

		switch (expr.getOperator().getType()) {
			case TokenType.PLUS: {
				if (left instanceof String && right instanceof String) {
					return (String) left + (String) right;
				}

				if (left instanceof Double && right instanceof Double) {
					return (double) left + (double) right;
				}
				
				break;
			}
			case TokenType.MINUS: {
				return (double) left - (double) right;
			}
			case TokenType.STAR: {
				return (double) left * (double) right;
			}
			case TokenType.SLASH: {
				return (double) left / (double) right;
			}
			case TokenType.GREATER_EQUAL: {
				return (double) left >= (double) right;
			}
			case TokenType.GREATER: {
				return (double) left > (double) right;
			}
			case TokenType.LESS: {
				return (double) left < (double) right;
			}
			case TokenType.LESS_EQUAL: {
				return (double) left <= (double) right;
			}
			case TokenType.EQUAL_EQUAL: {
				return isEqual(left, right);
			}
			case TokenType.BANG_EQUAL: {
				return !isEqual(left, right);
			}
		}

		return null;
	}

	@Override
	public Object visit(Expr.Grouping expr) {
		return evaluate(expr.getExpression());
	}

	@Override
	public Object visit(Expr.Unary expr) {
		Object right = evaluate(expr.getRight());
		
		switch (expr.getOperator().getType()) {
			case TokenType.MINUS: {
				return -(double) right;
			}
			case TokenType.BANG: {
				return !isTruthy(right);
			}
		}

		return null;
	}

	@Override
	public Object visit(Expr.Literal expr) {
		return expr.getValue();
	}

	private boolean isEqual(Object a, Object b) {
		if (a == null && b == null) {
			return true;
		}

		if (a == null) {
			return false;
		}

		return a.equals(b);
	}

	private boolean isTruthy(Object object) {
		if (object == null) {
			return false;
		}

		if (object instanceof Boolean) {
			return (boolean)object;
		}

		return true;
	}

}

