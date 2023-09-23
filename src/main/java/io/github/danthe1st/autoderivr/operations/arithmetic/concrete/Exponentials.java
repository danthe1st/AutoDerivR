package io.github.danthe1st.autoderivr.operations.arithmetic.concrete;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;

public class Exponentials {
	
	private Exponentials() {
		
	}
	
	public static UnaryFunction power(Constant base, Node exponent) {
		return new UnaryFunction(
				"pow[" + base + "]", exponent, arg -> Math.pow(base.value(), arg),
				argument -> new Multiply(power(base, argument), log(new Constant(Math.E), base))
		);
	}
	
	public static Node log(Constant base, Node value) {
		return new UnaryFunction(
				"log[" + base + "]", value, arg -> Math.log(arg) / Math.log(base.value()),
				argument -> Inverser.inverseDerivative(power(base, argument), log(base, argument))
		);
	}
}
