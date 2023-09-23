package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.Variable;

class StringConverter {
	private StringConverter() {
	}
	
	static String reduceOrConvertInfix(String operator, BinaryFunction fun) {
		Node reduced = fun.reduce();
		if(reduced.equals(fun)){
			return addParanthesisIfNecessary(fun.left()) + operator + addParanthesisIfNecessary(fun.right());
		}
		return reduced.toString();
	}
	
	private static String addParanthesisIfNecessary(Node node) {
		if(node instanceof Constant || node instanceof Variable || node instanceof UnaryFunction){
			return String.valueOf(node);
		}
		return "(" + node + ")";
	}
}
