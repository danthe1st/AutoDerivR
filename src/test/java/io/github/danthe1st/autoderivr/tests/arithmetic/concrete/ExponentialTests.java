package io.github.danthe1st.autoderivr.tests.arithmetic.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.concrete.Exponentials;
import io.github.danthe1st.autoderivr.operations.piecewise.ErrorNode;

/**
 * @see Exponentials
 */
class ExponentialTests {
	
	@Test
	void testStringRepresentation() {
		assertEquals("exp[2.0](3.0)", Exponentials.exp(new Constant(2), new Constant(3)).toString());
		assertEquals("pow[3.0](2.0)", Exponentials.pow(new Constant(2), new Constant(3)).toString());
		assertEquals("log[2.0](3.0)", Exponentials.log(new Constant(2), new Constant(3)).toString());
	}
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		
		assertEquals(8, Exponentials.exp(new Constant(2), new Constant(3)).evaluate(Collections.emptyMap()));
		assertEquals(4, Exponentials.exp(new Constant(2), x).evaluate(Map.of(x, 2.0)));
		
		assertEquals(3, Exponentials.log(new Constant(2), new Constant(8)).evaluate(Collections.emptyMap()));
		assertEquals(2, Exponentials.log(new Constant(2), x).evaluate(Map.of(x, 4.0)));
		
		assertEquals(8, Exponentials.pow(new Constant(2), new Constant(3)).evaluate(Collections.emptyMap()));
		assertEquals(9, Exponentials.pow(x, new Constant(2)).evaluate(Map.of(x, 3.0)));
		
		assertEquals(2, Exponentials.root(new Constant(3), new Constant(8)).evaluate(Collections.emptyMap()));
		assertEquals(3, Exponentials.root(new Constant(2), x).evaluate(Map.of(x, 9.0)));
		
		assertEquals(ErrorNode.INSTANCE, Exponentials.root(Constant.ZERO, x));
		assertEquals(ErrorNode.INSTANCE, Exponentials.root(new Constant(-0.), x));
		
	}
	
	@Test
	void testExpDerivative() {
		Variable x = new Variable("x");
		Constant two = new Constant(2);
		Constant e = new Constant(Math.E);
		assertEquals(
				// (2^x)ln(2)
				Exponentials.exp(two, x)
					.multiply(Exponentials.log(e, two))
					.toString(),
				// (2^x)'
				Exponentials.exp(two, x).derivative(x).toString()
		);
		assertEquals(
				// e^x=(e^x)ln(e)
				Exponentials.exp(e, x)
					.multiply(Exponentials.log(e, e))
					.toString(),
				// (e^x)'
				Exponentials.exp(e, x).derivative(x).toString()
		);
	}
	
	@Test
	void testLogDerivative() {
		Variable x = new Variable("x");
		Constant two = new Constant(2);
		Constant e = new Constant(Math.E);
		
		assertEquals(
				// 1/(x*ln(2))=1/((2^log2(x))*ln(2))
				Constant.ONE
					.divide(
							Exponentials.exp(two, Exponentials.log(two, x))
								.multiply(Exponentials.log(e, two))
					).toString(),
				// log2(x)'
				Exponentials.log(two, x).derivative(x).toString()
		);
		
		assertEquals(
				// 1/x=1/(x*ln(e))=1/((2^ln(x))*ln(e))
				new Divide(
						Constant.ONE,
						new Multiply(
								Exponentials.exp(e, Exponentials.log(e, x)),
								Exponentials.log(e, e)
						)
				).toString(),
				// ln(x)'
				Exponentials.log(e, x).derivative(x).toString()
		);
	}
	
	@Test
	void testPowDerivative() {
		Variable x = new Variable("x");
		assertEquals(Constant.ZERO, Exponentials.pow(x, Constant.ZERO).derivative(x));
		assertEquals(Constant.ONE, Exponentials.pow(x, Constant.ONE).derivative(x));
		assertEquals(
				// (2^x)*3
				Exponentials.pow(x, new Constant(2))
					.multiply(3)
					.toString(),
				// (x^3)'
				Exponentials.pow(x, new Constant(3)).derivative(x).toString()
		);
	}
	
	@Test
	void testRootDerivative() {
		Variable x = new Variable("x");
		assertEquals(Constant.ONE, Exponentials.root(Constant.ONE, x).derivative(x));
		assertEquals(
				// (2^x)*3
				Exponentials.pow(x, new Constant(-0.5))
					.multiply(0.5)
					.toString(),
				// (x^3)'
				Exponentials.root(new Constant(2), x).derivative(x).toString()
		);
	}
}
