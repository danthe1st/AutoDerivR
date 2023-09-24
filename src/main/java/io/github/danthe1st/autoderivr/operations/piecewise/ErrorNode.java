package io.github.danthe1st.autoderivr.operations.piecewise;

import java.util.Map;

import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public enum ErrorNode implements Node {
	INSTANCE;
	
	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Node derivative(Variable variable) {
		return this;
	}
	
	@Override
	public String toString() {
		return "<ERROR>";
	}
}