package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import java.util.Objects;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;

public record Subtract(Node left, Node right) implements BinaryFunction {
	
	public Subtract {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
	
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
		
		if(reduced instanceof Subtract(Node l, Node r) && l.equals(r)){
			return Constant.ZERO;
		}
		
		return reduced;
	}
}