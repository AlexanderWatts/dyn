import java.util.List;

public interface DynCallable {
	int arity();
	Object call(Interpreter interpreter, List<Object> arguments);
}

