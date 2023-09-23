package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;

/**
 * @see Variable
 */
class VariableTests {
	@Test
	void testCalculateVariableNotFound() {
		Variable x = new Variable("x");
		Map<Variable, Double> empty = Collections.emptyMap();
		assertThrows(IllegalStateException.class, () -> x.evaluate(empty));
		
		Map<Variable, Double> yMap = Map.of(new Variable("y"), 0.0);
		assertThrows(IllegalStateException.class, () -> x.evaluate(yMap));
	}
	
	@Test
	void testCalculateVariable() {
		Variable x = new Variable("x");
		assertEquals(13.37, x.evaluate(Map.of(x, 13.37)));
		assertEquals(37, x.evaluate(Map.of(new Variable("y"), 13.0, x, 37.0)));
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		assertEquals(Constant.ONE, x.derivative(x));
		assertEquals(Constant.ONE, x.derivative(new Variable("x")));
		assertEquals(Constant.ZERO, x.derivative(new Variable("y")));
	}
	
	@Test
	void testStringRepresentation() {
		assertEquals("abc", new Variable("abc").toString());
	}
}
