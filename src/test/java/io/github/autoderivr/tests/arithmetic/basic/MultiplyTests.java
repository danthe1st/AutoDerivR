package io.github.autoderivr.tests.arithmetic.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;

/**
 * @see Multiply
 */
class MultiplyTests {
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(1, new Multiply(x, Constant.ONE).evaluate(Map.of(x, 1.)));
		assertEquals(2, new Multiply(x, Constant.ONE).evaluate(Map.of(x, 2.)));
		assertEquals(6, new Multiply(x, y).evaluate(Map.of(x, 2., y, 3.)));
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(y, new Multiply(x, y).derivative(x).reduce());
		assertEquals(new Add(new Multiply(x, Constant.ZERO), new Multiply(Constant.ONE, y)), new Multiply(x, y).derivative(x));
		assertEquals(x, new Multiply(x, y).derivative(y).reduce());
		assertEquals(new Add(new Multiply(x, Constant.ONE), new Multiply(Constant.ZERO, y)), new Multiply(x, y).derivative(y));
		
		assertEquals(new Add(new Multiply(new Constant(3), Constant.ONE), new Multiply(Constant.ZERO, x)), new Multiply(new Constant(3), x).derivative(x));
		assertEquals(new Constant(3), new Multiply(new Constant(3), x).derivative(x).reduce());
	}
	
	@Test
	void testStringRepresentation() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals("x*y", new Multiply(x, y).toString());
		assertEquals("0.0", new Multiply(x, Constant.ZERO).toString());
		assertEquals("x", new Multiply(x, Constant.ONE).toString());
		assertEquals("x", new Multiply(Constant.ONE, x).toString());
		assertEquals("x*(y+1.0)", new Multiply(x, new Add(y, Constant.ONE)).toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Multiply(x, y), new Multiply(x, y).reduce());
		assertEquals(Constant.ZERO, new Multiply(x, Constant.ZERO).reduce());
		assertEquals(Constant.ZERO, new Multiply(Constant.ZERO, x).reduce());
		assertEquals(x, new Multiply(x, Constant.ONE).reduce());
		assertEquals(x, new Multiply(Constant.ONE, x).reduce());
	}
}
