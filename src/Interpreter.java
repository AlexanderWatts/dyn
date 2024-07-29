import java.util.List;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {
	private final Environment environment = new Environment();

	public void interpret(List<Stmt> statements) {
		try {
			for (Stmt stmt : statements) {
				execute(stmt);
			}
		} catch (RuntimeError runtimeError) {
			Dyn.runtimeError(runtimeError);
		}
	}

	private void execute(Stmt stmt) {
		stmt.accept(this);
	}

	private Object evaluate(Expr root) {
		return root.accept(this);
	}

	@Override
	public Void visit(Stmt.Var stmt) {
		Object value = null;

		if (stmt.getInitialiser() != null) {
			value = evaluate(stmt.getInitialiser());
		}

		environment.define(stmt.getName().getLexeme(), value);

		return null;
	}

	@Override
	public Object visit(Expr.Assign expr) {
		Object value = evaluate(expr.getValue());
		environment.assign(expr.getName(), value);

		return value;
	}

	public Object visit(Expr.Variable stmt) {
		return environment.get(stmt.getName());
	}

	@Override
	public Void visit(Stmt.Expression stmt) {
		evaluate(stmt.getExpression());
		return null;
	}

	@Override
	public Void visit(Stmt.Print stmt) {
		Object expression = evaluate(stmt.getExpression());
		System.out.println(generateString(expression));

		return null;
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

				if (left instanceof String && right == null) {
					return (String) left;
				}

				if (left == null && right instanceof String) {
					return (String) right;
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
				if (left instanceof String && right instanceof Double) {
					return ((String) left).length() >= (double) right;
				}

				if (left instanceof Double && right instanceof String) {
					return (double) left >= ((String) right).length() ;
				}

				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left >= (double) right;
			}
			case TokenType.GREATER: {
				if (left instanceof String && right instanceof Double) {
					return ((String) left).length() > (double) right;
				}

				if (left instanceof Double && right instanceof String) {
					return (double) left > ((String) right).length() ;
				}

				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left > (double) right;
			}
			case TokenType.LESS: {
				if (left instanceof String && right instanceof Double) {
					return ((String) left).length() < (double) right;
				}

				if (left instanceof Double && right instanceof String) {
					return (double) left < ((String) right).length() ;
				}

				checkNumberOperands(expr.getOperator(), left, right);
				return (double) left < (double) right;
			}
			case TokenType.LESS_EQUAL: {
				if (left instanceof String && right instanceof Double) {
					return ((String) left).length() <= (double) right;
				}

				if (left instanceof Double && right instanceof String) {
					return (double) left <= ((String) right).length() ;
				}

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

	private String generateString(Object object) {
		if (object == null) {
			return "nil";
		}

		return object.toString();
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

