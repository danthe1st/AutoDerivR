package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public record Divide(Node left, Node right) implements BinaryFunction {
	
	@Override
	public Node derivative(Variable variable) {
		return new Divide(
				new Subtract(
						new Multiply(left().derivative(variable), right()),
						new Multiply(left(), right().derivative(variable))
				),
				new Multiply(right(), right())
		);
	}
	
	@Override
	public double calculate(double left, double right) {
		return left / right;
	}
	
	@Override
	public String toString() {
		return StringConverter.reduceOrConvertInfix("/", this);
	}
	
	@Override
	public Node reduce() {
		return Reducer.reduceWithRightNeutralValue(this, Constant.ONE, Divide::new);
	}
	
}
