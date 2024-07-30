import java.util.HashMap;
import java.util.Map;

public class Environment {
	private final Environment parent;

	private Map<String, Object> values = new HashMap<>();

	public Environment() {
		parent = null;
	}

	public Environment(Environment parent) {
		this.parent = parent;
	}

	public void define(String name, Object value) {
		values.put(name, value);
	}

	public void assign(Token name, Object value) {
		if (values.containsKey(name.getLexeme())) {
			values.put(name.getLexeme(), value);
			return;
		}

		if (parent != null) {
			parent.assign(name, value);
			return;
		}

		throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'");
	}

	public Object get(Token name) {
		if (values.containsKey(name.getLexeme())) {
			return values.get(name.getLexeme());
		}

		if (parent != null) {
			return parent.get(name);
		}

		throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'");
	}

	public Environment getParent() {
		return parent;
	}

	public Map<String, Object> getValues() {
		return values;
	}
}

