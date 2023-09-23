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

/**
 * @see Exponentials
 */
class ExponentialTests {
	
	@Test
	void testStringRepresentation() {
		assertEquals("pow[2.0](3.0)", Exponentials.power(new Constant(2), new Constant(3)).toString());
		assertEquals("log[2.0](3.0)", Exponentials.log(new Constant(2), new Constant(3)).toString());
	}
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		assertEquals(8, Exponentials.power(new Constant(2), new Constant(3)).evaluate(Collections.emptyMap()));
		assertEquals(4, Exponentials.power(new Constant(2), x).evaluate(Map.of(x, 2.0)));
		
		assertEquals(3, Exponentials.log(new Constant(2), new Constant(8)).evaluate(Collections.emptyMap()));
		assertEquals(2, Exponentials.log(new Constant(2), x).evaluate(Map.of(x, 4.0)));
	}
	
	@Test
	void testPowerDerivative() {
		Variable x = new Variable("x");
		Constant two = new Constant(2);
		Constant e = new Constant(Math.E);
		assertEquals(
				// (2^x)ln(2)
				new Multiply(
						Exponentials.power(two, x),
						Exponentials.log(e, two)
				).toString(),
				// (2^x)'
				Exponentials.power(two, x).derivative(x).toString()
		);
		assertEquals(
				// e^x=(e^x)ln(e)
				new Multiply(
						Exponentials.power(e, x),
						Exponentials.log(e, e)
				).toString(),
				// (e^x)'
				Exponentials.power(e, x).derivative(x).toString()
		);
		
	}
	
	@Test
	void testLogDerivative() {
		Variable x = new Variable("x");
		Constant two = new Constant(2);
		Constant e = new Constant(Math.E);
		
		assertEquals(
				// 1/(x*ln(2))=1/((2^log2(x))*ln(2))
				new Divide(
						Constant.ONE,
						new Multiply(
								Exponentials.power(two, Exponentials.log(two, x)),
								Exponentials.log(e, two)
						)
				).toString(),
				// log2(x)'
				Exponentials.log(two, x).derivative(x).toString()
		);
		
		assertEquals(
				// 1/x=1/(x*ln(e))=1/((2^ln(x))*ln(e))
				new Divide(
						Constant.ONE,
						new Multiply(
								Exponentials.power(e, Exponentials.log(e, x)),
								Exponentials.log(e, e)
						)
				).toString(),
				// ln(x)'
				Exponentials.log(e, x).derivative(x).toString()
		);
	}
}
