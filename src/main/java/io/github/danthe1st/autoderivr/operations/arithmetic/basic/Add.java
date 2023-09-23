package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public record Add(Node left, Node right) implements BinaryFunction {
	
	@Override
	public Node derivative(Variable variable) {
		return new Add(left().derivative(variable), right().derivative(variable));
	}
	
	@Override
	public double calculate(double left, double right) {
		return left + right;
	}
	
	@Override
	public String toString() {
		return StringConverter.reduceOrConvertInfix("+", this);
	}
	
	@Override
	public Node reduce() {
		return Reducer.reduceCommutativeWithNeutralValue(this, Constant.ZERO, Add::new);
	}
	
}