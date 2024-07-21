
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
				
				throw new RuntimeError(expr.getOperator(), "Operands must be two numbers or two strings");
			}
			case TokenType.MINUS: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left - (double) right;
			}
			case TokenType.STAR: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left * (double) right;
			}
			case TokenType.SLASH: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left / (double) right;
			}
			case TokenType.GREATER_EQUAL: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left >= (double) right;
			}
			case TokenType.GREATER: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left > (double) right;
			}
			case TokenType.LESS: {
				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left < (double) right;
			}
			case TokenType.LESS_EQUAL: {
				checkNumberOperands(expr.getOperator(), left, right);
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
				checkNumberOperand(expr.getOperator(), right);
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

	private void checkNumberOperands(Token token, Object left, Object right) {
		if (left instanceof Double && right instanceof Double) {
			return;
		}

		throw new RuntimeError(token, "Operands must be numbers");
	}

	private void checkNumberOperand(Token token, Object operand) {
		if (operand instanceof Double) {
			return;
		}

		throw new RuntimeError(token, "Operand must be a number");
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

