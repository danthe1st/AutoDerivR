package io.github.danthe1st.autoderivr.operations.piecewise;

import java.util.Map;
import java.util.Objects;

import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

/**
 * A piecewise defined computation/function.
 *
 * The derivative of a piecewise defined function at any border point ({@code condition.left()=condition.right()}) is not defined.
 *
 * This function will evaluate in the following way:
 * {@snippet :
 *  smaller() iff condition.left() < condition.right()
 *  greater() iff condition.left() > condition.right()
 *  equal()   iff condition.left() = condition.right()
 * }
 *
 * @param condition the condition determining which side to compute
 * @param smaller a computation to execute iff {@code condition.left() < condition.right()}
 * @param greater a computation to execute iff {@code condition.left() > condition.right()}
 * @param equal a computation to execute iff {@code condition.left() = condition.right()}
 */
public record Piecewise(
		Comparison condition,
		Node smaller, Node greater,
		Node equal) implements Node {
	
	public Piecewise {
		Objects.requireNonNull(condition);
		Objects.requireNonNull(smaller);
		Objects.requireNonNull(greater);
		Objects.requireNonNull(equal);
	}
	
	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		double result = condition.left().evaluate(variableValues);
		double borderPoint = condition.right().evaluate(variableValues);
		if(result < borderPoint){
			return smaller.evaluate(variableValues);
		}
		if(result > borderPoint){
			return greater.evaluate(variableValues);
		}
		return equal.evaluate(variableValues);
	}
	
	@Override
	public Node derivative(Variable variable) {
		return new Piecewise(condition, smaller.derivative(variable), greater.derivative(variable), ErrorNode.INSTANCE);
	}
	
	@Override
	public String toString() {
		return "{if " + condition.left() + "<" + condition.right() + " then " + smaller + " else if " + condition.left() + ">" + condition.right() + " then " + greater + " else " + equal + "}";
	}
	
}
