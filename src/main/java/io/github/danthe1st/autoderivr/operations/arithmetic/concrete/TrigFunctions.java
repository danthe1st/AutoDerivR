package io.github.danthe1st.autoderivr.operations.arithmetic.concrete;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;

public class TrigFunctions {
	private TrigFunctions() {
		
	}
	
	public static UnaryFunction sin(Node argument) {
		return new UnaryFunction("sin", argument, Math::sin, TrigFunctions::cos);
	}
	
	public static UnaryFunction cos(Node argument) {
		return new UnaryFunction("cos", argument, Math::cos, arg -> new Subtract(Constant.ZERO, sin(arg)));
	}
	
	public static UnaryFunction tan(Node argument) {
		return new UnaryFunction("tan", argument, Math::tan, arg -> new Add(new Multiply(tan(arg), tan(arg)), Constant.ONE));
	}
	
	public static Node arcsin(Node argument) {
		return new UnaryFunction("arcsin", argument, Math::asin, arg -> Inverser.inverseDerivative(sin(arg), arcsin(arg)));
	}
	
	public static Node arccos(Node argument) {
		return new UnaryFunction("arccos", argument, Math::acos, arg -> Inverser.inverseDerivative(cos(arg), arccos(arg)));
	}
	
	public static Node arctan(Node argument) {
		return new UnaryFunction("arctan", argument, Math::atan, arg -> Inverser.inverseDerivative(a -> new Add(new Multiply(tan(a), tan(a)), Constant.ONE), arctan(arg)));
	}
}
