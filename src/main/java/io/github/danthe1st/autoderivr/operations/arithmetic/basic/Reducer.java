package io.github.danthe1st.autoderivr.operations.arithmetic.basic;

import java.util.function.BinaryOperator;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;

class Reducer {
	private Reducer() {
		
	}
	
	static Node reduceWithRightNeutralValue(BinaryFunction f, Constant neutral, BinaryOperator<Node> creator) {
		return reduce(f, neutral, creator, false, true);
	}
	
	static Node reduceCommutativeWithNeutralValue(BinaryFunction f, Constant neutral, BinaryOperator<Node> creator) {
		return reduce(f, neutral, creator, true, true);
	}
	
	private static Node reduce(BinaryFunction f, Constant neutral, BinaryOperator<Node> creator, boolean leftNeutral, boolean rightNeutral) {
		Node left = f.left().reduce();
		Node right = f.right().reduce();
		
		if(leftNeutral && neutral.equals(left)){
			return right;
		}
		if(rightNeutral && neutral.equals(right)){
			return left;
		}
		
		return creator.apply(left, right);
	}
}
