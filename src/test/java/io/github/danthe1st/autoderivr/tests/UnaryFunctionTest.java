package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.UnaryFunction;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;

/**
 * @see UnaryFunction
 */
class UnaryFunctionTest {
	
	private Function<Node, UnaryFunction> identityFactory = UnaryFunction.factory("id", DoubleUnaryOperator.identity(), arg -> Constant.ONE);
	
	@Test
	void testCalculate() {
		assertEquals(1, identityFactory.apply(Constant.ONE).evaluate(Collections.emptyMap()));
		Variable x = new Variable("x");
		assertEquals(12.34, identityFactory.apply(x).evaluate(Map.of(x, 12.34)));
		
		UnaryFunction f = new UnaryFunction("f", new Multiply(new Constant(2), x), arg -> arg + 1, arg -> Constant.ONE);
		assertEquals(1, f.evaluate(Map.of(x, 0.)));
		assertEquals(3, f.evaluate(Map.of(x, 1.)));
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		assertEquals(Constant.ONE, identityFactory.apply(x).derivative(x));
		assertEquals(
				new Constant(2),
				identityFactory.apply(new Multiply(new Constant(2), x)).derivative(x)
		);
		
		UnaryFunction f = new UnaryFunction("f", new Multiply(new Constant(2), x), arg -> arg + 1, arg -> Constant.ONE);
		assertEquals(new Constant(2), f.derivative(x));
	}
	
	@Test
	void testStringRepresentation() {
		Variable x = new Variable("x");
		assertEquals("id(1.0)", identityFactory.apply(Constant.ONE).toString());
		assertEquals("id(x)", identityFactory.apply(x).toString());
		assertEquals("id(2.0*x)", identityFactory.apply(new Multiply(new Constant(2.0), x)).toString());
		
		UnaryFunction f = new UnaryFunction("f", new Multiply(new Constant(3), x), arg -> 3 * arg, arg -> new Constant(3));
		assertEquals("f(3.0*x)", f.toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		assertEquals(
				identityFactory.apply(x),
				identityFactory.apply(new Multiply(Constant.ONE, x)).reduce()
		);
		
		UnaryFunction xSquare = identityFactory.apply(new Multiply(x, x));
		assertEquals(
				xSquare,
				xSquare.reduce()
		);
	}
}
