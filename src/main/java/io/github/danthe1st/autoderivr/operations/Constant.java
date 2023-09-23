package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

/**
 * A constant value in a computation.
 * @param value the value of the constant
 */
public record Constant(double value) implements Node {
	
	public static Constant ZERO = new Constant(0);
	public static Constant ONE = new Constant(1);
	
	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return value();
	}
	
	@Override
	public Node derivative(Variable variable) {
		return ZERO;
	}
	
	@Override
	public String toString() {
		return "" + value;
	}
}
