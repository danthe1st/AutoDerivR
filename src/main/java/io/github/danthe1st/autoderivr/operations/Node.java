package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;

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
	
	// utility methods
	
	default Node add(Node other) {
		return new Add(this, other);
	}
	
	default Node add(double other) {
		return add(new Constant(other));
	}
	
	default Node subtract(Node other) {
		return new Subtract(this, other);
	}
	
	default Node subtract(double other) {
		return subtract(new Constant(other));
	}
	
	default Node negate() {
		return Constant.ZERO.subtract(this);
	}
	
	default Node multiply(Node other) {
		return new Multiply(this, other);
	}
	
	default Node multiply(double other) {
		return multiply(new Constant(other));
	}
	
	default Node square() {
		return multiply(this);
	}
	
	default Node divide(Node other) {
		return new Divide(this, other);
	}
	
	default Node divide(double other) {
		return divide(new Constant(other));
	}
}
