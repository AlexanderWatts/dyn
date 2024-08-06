import java.util.List;

public class DynFunction implements DynCallable {
	private final Stmt.Function declaration;

	public DynFunction(Stmt.Function declaration) {
		this.declaration = declaration;	
	}

	@Override
	public int arity() {
		return declaration.getParams().size();
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		Environment enviroment = new Environment(interpreter.getGlobals());	

		for (int i = 0; i < arguments.size(); i++) {
			enviroment.define(declaration.getParams().get(i).getLexeme(), arguments.get(i));	
		}

		System.out.println("Env " + enviroment.getValues());

		interpreter.executeBlock(declaration.getBody(), enviroment);
		return null;
	}


	@Override
	public String toString() {
		return "<fn " + declaration.getName().getLexeme() + ">";
	}

}

