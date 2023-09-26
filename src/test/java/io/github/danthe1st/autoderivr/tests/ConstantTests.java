package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
	
	@Test
	void testEquality() {
		assertEquals(Constant.ZERO, Constant.ZERO);
		assertEquals(Constant.ZERO, new Constant(0));
		assertEquals(Constant.ZERO, new Constant(-0.));
		assertEquals(new Constant(-0.), new Constant(0));
		assertNotEquals(new Constant(-1), Constant.ONE);
		assertFalse(Constant.ONE.equals(null));
		assertFalse(Constant.ONE.equals(new Object()));
	}
	
	@Test
	void testHashCode() {
		assertEquals(Constant.ZERO.hashCode(), new Constant(-0.).hashCode());
		assertEquals(Double.hashCode(1), Constant.ONE.hashCode());
	}
}
