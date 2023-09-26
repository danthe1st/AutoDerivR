package io.github.danthe1st.autoderivr.tests.arithmetic.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;

/**
 * @see Divide
 */
class DivideTests {
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(1, new Divide(x, Constant.ONE).evaluate(Map.of(x, 1.)));
		assertEquals(2, new Divide(x, Constant.ONE).evaluate(Map.of(x, 2.)));
		assertEquals(2. / 3, new Divide(x, y).evaluate(Map.of(x, 2., y, 3.)), 0.000001);
	}
	
	@Test
	void testDerivative() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(
				new Divide(
						new Subtract(new Multiply(Constant.ONE, y), new Multiply(x, Constant.ZERO)),
						new Multiply(y, y)
				), new Divide(x, y).derivative(x)
		);
		assertEquals(
				new Divide(
						y,
						new Multiply(y, y)
				), new Divide(x, y).derivative(x).reduce()
		);
		assertEquals(
				new Divide(
						new Subtract(
								new Multiply(Constant.ZERO, y),
								new Multiply(x, Constant.ONE)
						), new Multiply(y, y)
				),
				new Divide(x, y).derivative(y)
		);
		assertEquals(
				new Divide(
						new Subtract(Constant.ZERO, x),
						new Multiply(y, y)
				),
				new Divide(x, y).derivative(y).reduce()
		);
		
		assertEquals(
				new Divide(
						new Subtract(new Multiply(Constant.ONE, Constant.ONE), new Multiply(x, Constant.ZERO)),
						new Multiply(Constant.ONE, Constant.ONE)
				),
				new Divide(x, Constant.ONE).derivative(x)
		);
		assertEquals(
				Constant.ONE,
				new Divide(x, Constant.ONE).derivative(x).reduce()
		);
	}
	
	@Test
	void testStringRepresentation() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals("x/y", new Divide(x, y).toString());
		assertEquals("x", new Divide(x, Constant.ONE).toString());
		assertEquals("1.0/x", new Divide(Constant.ONE, x).toString());
		assertEquals("x/(y+1.0)", new Divide(x, new Add(y, Constant.ONE)).toString());
	}
	
	@Test
	void testReduce() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		assertEquals(new Divide(x, y), new Divide(x, y).reduce());
		assertEquals(new Divide(x, new Constant(2)), new Divide(x, new Constant(2)).reduce());
		assertEquals(x, new Divide(x, Constant.ONE).reduce());
		assertEquals(new Divide(Constant.ONE, x), new Divide(Constant.ONE, x).reduce());
		assertEquals(Constant.ZERO, new Divide(Constant.ZERO, x).reduce());
		assertEquals(Constant.ONE, new Divide(x, x).reduce());
	}
}
