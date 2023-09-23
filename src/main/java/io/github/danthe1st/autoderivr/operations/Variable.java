package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

/**
 * Represents a variable in a function.
 * If multiple variables have the same name, they are treated as equal.
 * @param identifier the name of the variable.
 */
public record Variable(String identifier) implements Node {
	
	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		Double value = variableValues.get(this);
		if(value == null){
			throw new IllegalStateException("variable not found");
		}
		return value;
	}
	
	@Override
	public Node derivative(Variable variable) {
		return this.equals(variable) ? Constant.ONE : Constant.ZERO;
	}
	
	@Override
	public String toString() {
		return identifier();
	}
}
