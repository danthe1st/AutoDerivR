package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public record Multiply(Node left, Node right) implements BinaryFunction {
	
	@Override
	public Node derivative(Variable variable) {
		return new Add(
				new Multiply(left(), right().derivative(variable)),
				new Multiply(left().derivative(variable), right())
		);
	}
	
	@Override
	public double calculate(double left, double right) {
		return left * right;
	}
	
	@Override
	public String toString() {
		return StringConverter.reduceOrConvertInfix("*", this);
	}
	
	@Override
	public Node reduce() {
		Node reduced = Reducer.reduceCommutativeWithNeutralValue(this, Constant.ONE, Multiply::new);
		if(reduced instanceof Multiply(Node l, Node r) && (Constant.ZERO.equals(l) || Constant.ZERO.equals(r))){
			return Constant.ZERO;
		}
		return reduced;
	}
}
