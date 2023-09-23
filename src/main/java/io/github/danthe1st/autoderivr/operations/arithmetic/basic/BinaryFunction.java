package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import java.util.Map;

import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

sealed interface BinaryFunction extends Node permits Add, Subtract, Multiply, Divide {
	
	Node left();
	
	Node right();
	
	double calculate(double left, double right);
	
	@Override
	default double evaluate(Map<Variable, Double> variableValues) {
		return calculate(left().evaluate(variableValues), right().evaluate(variableValues));
	}
}
