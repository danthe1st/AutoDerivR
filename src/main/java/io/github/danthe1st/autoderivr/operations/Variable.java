package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

public record Variable(String identifier) implements Node {
	
	@Override
	public double calculateDouble(Map<Variable, Double> variableValues) {
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
