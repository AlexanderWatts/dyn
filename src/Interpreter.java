
public class Interpreter implements Expr.Visitor<Object> {
	public Object evaluate(Expr root) {
		return root.accept(this);
	}

	@Override
	public Object visit(Expr.Binary expr) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public Object visit(Expr.Grouping expr) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public Object visit(Expr.Unary expr) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public Object visit(Expr.Literal expr) {
		return expr.getValue();
	}

}

