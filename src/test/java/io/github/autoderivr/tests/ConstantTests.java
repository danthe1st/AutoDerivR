package io.github.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;

/**
 * @see Constant
 */
class ConstantTests {
	@Test
	void testConstants() {
		assertEquals(0, Constant.ZERO.value());
		assertEquals(1, Constant.ONE.value());
		
		assertEquals(0, Constant.ZERO.evaluate(Collections.emptyMap()));
		assertEquals(1, Constant.ONE.evaluate(Map.of(new Variable("x"), 123.0)));
	}
	
	@Test
	void testDerivative() {
		assertEquals(Constant.ZERO, Constant.ZERO.derivative(new Variable("x")));
		assertEquals(Constant.ZERO, Constant.ONE.derivative(new Variable("a")));
		assertEquals(Constant.ZERO, new Constant(123).derivative(new Variable("y")));
	}
	
	@Test
	void testStringForm() {
		assertEquals("0.0", Constant.ZERO.toString());
		assertEquals("1.0", Constant.ONE.toString());
		assertEquals("13.37", new Constant(13.37).toString());
	}
}
