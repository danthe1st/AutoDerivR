package io.github.danthe1st.autoderivr.operations.arithmetic.concrete;

import java.util.function.UnaryOperator;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;

class Inverser {
	
	private Inverser() {
		
	}
	
	static Node inverseDerivative(UnaryFunction original, Node inverse) {
		return inverseDerivative(original.atomicDeriver(), inverse);
	}
	
	static Node inverseDerivative(UnaryOperator<Node> derivative, Node inverse) {
		return new Divide(Constant.ONE, derivative.apply(inverse));
	}
	
}
