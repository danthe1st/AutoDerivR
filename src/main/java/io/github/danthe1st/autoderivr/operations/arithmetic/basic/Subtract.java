package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public record Subtract(Node left, Node right) implements BinaryFunction {
	
	@Override
	public Node derivative(Variable variable) {
		return new Subtract(left().derivative(variable), right().derivative(variable));
	}
	
	@Override
	public double calculate(double left, double right) {
		return left - right;
	}
	
	@Override
	public String toString() {
		return StringConverter.reduceOrConvertInfix("-", this);
	}
	
	@Override
	public Node reduce() {
		Node reduced = Reducer.reduceWithRightNeutralValue(this, Constant.ZERO, Subtract::new);
		
		if(reduced instanceof Subtract(Node left, Node right) && left.equals(right)){
			return Constant.ZERO;
		}
		
		return reduced;
	}
}