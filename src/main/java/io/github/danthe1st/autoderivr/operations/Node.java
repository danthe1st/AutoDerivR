package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

/**
 * Represents a computation/function.
 *
 * @implSpec Implementations must be immutable.
 * @see Variable
 */
public interface Node {
	/**
	 * Evaluates this node at a specific point in the variable space.
	 * @param variableValues a mapping that must map all variables to values
	 * @return the result of the computation with respect to the given variable values
	 */
	double evaluate(Map<Variable, Double> variableValues);
	
	/**
	 * Symbolically calculates the derivative of this {@link Node} with respect to a given variable.
	 *
	 * It is possible to calculate higher-order derivatives by chaining this method.
	 * @param variable the variable to calculate the derivative of
	 * @return A {@link Node} representing the derivative of the current function/computation.
	 */
	Node derivative(Variable variable);
	
	/**
	 * Attempt basic simplifications on this computation/function.
	 * @implSpec If this function is valid (e.g. no division by 0), this method must not change the behaviour of the computation.
	 * Calling this function repeatedly must lead to a fix point in finite time.
	 *
	 * @return the simplified function
	 */
	default Node reduce() {
		return this;
	}
}
