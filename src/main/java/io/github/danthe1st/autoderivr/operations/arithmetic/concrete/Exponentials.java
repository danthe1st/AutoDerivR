package io.github.danthe1st.autoderivr.operations.arithmetic.concrete;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.piecewise.ErrorNode;

public class Exponentials {
	
	private Exponentials() {
		
	}
	
	public static UnaryFunction exp(Constant base, Node exponent) {
		return new UnaryFunction(
				"exp[" + base + "]", exponent, arg -> Math.pow(base.value(), arg),
				argument -> new Multiply(exp(base, argument), log(new Constant(Math.E), base))
		);
	}
	
	public static Node log(Constant base, Node value) {
		return new UnaryFunction(
				"log[" + base + "]", value, arg -> Math.log(arg) / Math.log(base.value()),
				argument -> Inverser.inverseDerivative(exp(base, argument), log(base, argument))
		);
	}
	
	public static Node pow(Node base, Constant exponent) {
		if(isZero(exponent)){
			return new Constant(1);
		}
		return new UnaryFunction(
				"pow[" + exponent + "]", base, arg -> Math.pow(arg, exponent.value()),
				argument -> new Multiply(pow(base, new Constant(exponent.value() - 1)), exponent)
		);
	}
	
	public static Node root(Constant index, Node radicant) {
		if(isZero(index)){
			return ErrorNode.INSTANCE;
		}
		return pow(radicant, new Constant(1 / index.value()));
	}
	
	private static boolean isZero(Constant constant) {
		return constant.value() == 0. || constant.value() == -0.;
	}
}
