import java.util.HashMap;
import java.util.Map;

public class Environment {
	private Map<String, Object> values = new HashMap<>();

	public void define(String name, Object value) {
		values.put(name, value);
	}

	public Object get(Token name) {
		if (values.containsKey(name.getLexeme())) {
			return values.get(name.getLexeme());
		}

		throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'");
	}

	public Map<String, Object> getValues() {
		return values;
	}
}

