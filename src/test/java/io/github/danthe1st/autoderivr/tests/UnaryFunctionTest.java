package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
				identityFactory.apply(new Constant(2).multiply(x)).derivative(x)
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
		
		UnaryFunction f = new UnaryFunction("f", new Constant(3).multiply(x), arg -> 3 * arg, arg -> new Constant(3));
		assertEquals("f(3.0*x)", f.toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		assertEquals(
				identityFactory.apply(x),
				identityFactory.apply(Constant.ONE.multiply(x)).reduce()
		);
		
		UnaryFunction xSquare = identityFactory.apply(x.square());
		assertEquals(
				xSquare,
				xSquare.reduce()
		);
	}
	
	@Test
	void testEquality() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertFalse(identityFactory.apply(x).equals(null));
		assertFalse(identityFactory.apply(x).equals(new Object()));
		assertEquals(identityFactory.apply(x), identityFactory.apply(x));
		assertNotEquals(identityFactory.apply(x), identityFactory.apply(y));
		UnaryFunction function = new UnaryFunction("a", x, createOperatorUsingVariable(x), a -> Constant.ZERO);
		assertEquals(function, function);
		assertEquals(new UnaryFunction("a", x, createOperatorUsingVariable(x), a -> Constant.ZERO), function);
		assertNotEquals(new UnaryFunction("b", x, createOperatorUsingVariable(x), a -> Constant.ZERO), function);
		assertNotEquals(new UnaryFunction("a", y, createOperatorUsingVariable(x), a -> Constant.ZERO), function);
		assertNotEquals(new UnaryFunction("a", x, createDifferentOperatorUsingVariable(x), a -> Constant.ZERO), function);
		assertEquals(createUnaryFunctionWithAnonymousClass(true), createUnaryFunctionWithAnonymousClass(true));
		assertNotEquals(createUnaryFunctionWithAnonymousClass(false), createUnaryFunctionWithAnonymousClass(false));
	}
	
	@Test
	void testHashCode() {
		Variable x = new Variable("x");
		assertEquals(identityFactory.apply(x).hashCode(), identityFactory.apply(x).hashCode());
	}
	
	private DoubleUnaryOperator createOperatorUsingVariable(Object o) {
		return d -> d + o.toString().length();
	}
	
	private DoubleUnaryOperator createDifferentOperatorUsingVariable(Object o) {
		return d -> d + o.toString().length() + 1;
	}
	
	private UnaryFunction createUnaryFunctionWithAnonymousClass(boolean equalsIsTrue) {
		DoubleUnaryOperator op = new DoubleUnaryOperator() {
			
			@Override
			public double applyAsDouble(double operand) {
				return 0;
			}
			
			@Override
			public boolean equals(Object obj) {
				return equalsIsTrue;
			}
		};
		return new UnaryFunction("a", Constant.ZERO, op, n -> n);
	}
}
