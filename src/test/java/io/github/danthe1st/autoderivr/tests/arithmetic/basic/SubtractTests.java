package io.github.danthe1st.autoderivr.tests.arithmetic.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;

/**
 * @see Subtract
 */
class SubtractTests {
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(0, new Subtract(x, Constant.ONE).evaluate(Map.of(x, 1.)));
		assertEquals(1, new Subtract(x, Constant.ONE).evaluate(Map.of(x, 2.)));
		assertEquals(-1, new Subtract(x, y).evaluate(Map.of(x, 2., y, 3.)));
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Subtract(Constant.ONE, Constant.ZERO), new Subtract(x, y).derivative(x));
		assertEquals(new Subtract(Constant.ZERO, Constant.ONE), new Subtract(x, y).derivative(y));
		
		assertEquals(new Subtract(Constant.ONE, Constant.ZERO), new Subtract(x, Constant.ONE).derivative(x));
	}
	
	@Test
	void testStringRepresentation() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals("x-y", new Subtract(x, y).toString());
		assertEquals("x-1.0", new Subtract(x, Constant.ONE).toString());
		assertEquals("x", new Subtract(x, Constant.ZERO).toString());
		assertEquals("0.0-x", new Subtract(Constant.ZERO, x).toString());
		assertEquals("x-(y+1.0)", new Subtract(x, new Add(y, Constant.ONE)).toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Subtract(x, y), new Subtract(x, y).reduce());
		assertEquals(new Subtract(x, Constant.ONE), new Subtract(x, Constant.ONE).reduce());
		assertEquals(x, new Subtract(x, Constant.ZERO).reduce());
		assertEquals(new Subtract(Constant.ZERO, x), new Subtract(Constant.ZERO, x).reduce());
		assertEquals(Constant.ZERO, new Subtract(x, x).reduce());
	}
}
