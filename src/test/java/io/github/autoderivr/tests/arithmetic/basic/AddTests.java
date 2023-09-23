package io.github.autoderivr.tests.arithmetic.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;

class AddTests {
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(2, new Add(x, Constant.ONE).calculateDouble(Map.of(x, 1.)));
		assertEquals(3, new Add(x, Constant.ONE).calculateDouble(Map.of(x, 2.)));
		assertEquals(5, new Add(x, y).calculateDouble(Map.of(x, 2., y, 3.)));
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Add(Constant.ONE, Constant.ZERO), new Add(x, y).derivative(x));
		assertEquals(new Add(Constant.ZERO, Constant.ONE), new Add(x, y).derivative(y));
	}
	
	@Test
	void testStringRepresentation() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals("x+y", new Add(x, y).toString());
		assertEquals("x+1.0", new Add(x, Constant.ONE).toString());
		assertEquals("x", new Add(x, Constant.ZERO).toString());
		assertEquals("x", new Add(Constant.ZERO, x).toString());
		assertEquals("x+(y+1.0)", new Add(x, new Add(y, Constant.ONE)).toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Add(x, y), new Add(x, y).reduce());
		assertEquals(new Add(x, Constant.ONE), new Add(x, Constant.ONE).reduce());
		assertEquals(x, new Add(x, Constant.ZERO).reduce());
		assertEquals(x, new Add(Constant.ZERO, x).reduce());
	}
}
